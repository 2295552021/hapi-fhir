package ca.uhn.fhir.context;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import ca.uhn.fhir.model.api.IPrimitiveDatatype;
import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.model.dstu.resource.Profile;
import ca.uhn.fhir.model.dstu.resource.Profile.ExtensionDefn;
import ca.uhn.fhir.model.dstu.resource.Profile.Structure;
import ca.uhn.fhir.model.dstu.resource.Profile.StructureElement;
import ca.uhn.fhir.model.dstu.resource.Profile.StructureElementDefinitionType;
import ca.uhn.fhir.model.dstu.valueset.DataTypeEnum;
import ca.uhn.fhir.model.dstu.valueset.SlicingRulesEnum;

public class RuntimeResourceDefinition extends BaseRuntimeElementCompositeDefinition<IResource> {

	private String myResourceProfile;

	public RuntimeResourceDefinition(Class<? extends IResource> theClass, ResourceDef theResourceAnnotation) {
		super(theResourceAnnotation.name(), theClass);
		myResourceProfile = theResourceAnnotation.profile();
	}

	public String getResourceProfile() {
		return myResourceProfile;
	}

	@Override
	public ca.uhn.fhir.context.BaseRuntimeElementDefinition.ChildTypeEnum getChildType() {
		return ChildTypeEnum.RESOURCE;
	}

	public synchronized Profile toProfile() {
		Profile retVal = new Profile();
		RuntimeResourceDefinition def = this;

		// Scan for extensions
		scanForExtensions(retVal, def);
		Collections.sort(retVal.getExtensionDefn(), new Comparator<ExtensionDefn>() {
			@Override
			public int compare(ExtensionDefn theO1, ExtensionDefn theO2) {
				return theO1.getCode().compareTo(theO2.getCode());
			}
		});

		// Scan for children
		retVal.setName(getName());
		Structure struct = retVal.addStructure();
		LinkedList<String> path = new LinkedList<String>();

		StructureElement element = struct.addElement();
		element.getDefinition().setMin(1);
		element.getDefinition().setMax("1");

		fillProfile(struct, element, def, path, null);

		retVal.getStructure().get(0).getElement().get(0).getDefinition().addType().getCode().setValue("Resource");

		return retVal;
	}

	private Map<RuntimeChildDeclaredExtensionDefinition, String> myExtensionDefToCode = new HashMap<RuntimeChildDeclaredExtensionDefinition, String>();

	private void scanForExtensions(Profile theProfile, BaseRuntimeElementDefinition<?> def) {
		BaseRuntimeElementCompositeDefinition<?> cdef = ((BaseRuntimeElementCompositeDefinition<?>) def);

		for (RuntimeChildDeclaredExtensionDefinition nextChild : cdef.getExtensions()) {
			if (myExtensionDefToCode.containsKey(nextChild)) {
				continue;
			}

			if (nextChild.isDefinedLocally() == false) {
				continue;
			}

			ExtensionDefn defn = theProfile.addExtensionDefn();
			String code = null;
			if (nextChild.getExtensionUrl().contains("#") && !nextChild.getExtensionUrl().endsWith("#")) {
				code = nextChild.getExtensionUrl().substring(nextChild.getExtensionUrl().indexOf('#') + 1);
			} else {
				throw new ConfigurationException("Locally defined extension has no '#[code]' part in extension URL: " + nextChild.getExtensionUrl());
			}

			defn.setCode(code);
			if (myExtensionDefToCode.values().contains(code)) {
				throw new IllegalStateException("Duplicate extension code: " + code);
			}
			myExtensionDefToCode.put(nextChild, code);

			if (nextChild.getChildType() != null && IPrimitiveDatatype.class.isAssignableFrom(nextChild.getChildType())) {
				RuntimePrimitiveDatatypeDefinition pdef = (RuntimePrimitiveDatatypeDefinition) nextChild.getSingleChildOrThrow();
				defn.getDefinition().addType().setCode(DataTypeEnum.VALUESET_BINDER.fromCodeString(pdef.getName()));
			} else {
				RuntimeResourceBlockDefinition pdef = (RuntimeResourceBlockDefinition) nextChild.getSingleChildOrThrow();
				scanForExtensions(theProfile, pdef);

				for (RuntimeChildDeclaredExtensionDefinition nextChildExt : pdef.getExtensions()) {
					StructureElementDefinitionType type = defn.getDefinition().addType();
					type.setCode(DataTypeEnum.EXTENSION);
					type.setProfile("#" + myExtensionDefToCode.get(nextChildExt));
				}

			}
		}
	}

	private void fillProfile(Structure theStruct, StructureElement theElement, BaseRuntimeElementDefinition<?> def, LinkedList<String> path, BaseRuntimeDeclaredChildDefinition theChild) {

		fillBasics(theElement, def, path, theChild);

		fillExtensions(theStruct, path, def.getExtensionsNonModifier(), "extension", false);
		fillExtensions(theStruct, path, def.getExtensionsModifier(), "modifierExtension", true);

		if (def.getChildType() == ChildTypeEnum.RESOURCE) {
			StructureElement narrative = theStruct.addElement();
			narrative.setName("text");
			narrative.setPath(join(path, '.') + ".text");
			narrative.getDefinition().addType().setCode(DataTypeEnum.NARRATIVE);
			narrative.getDefinition().setIsModifier(false);
			narrative.getDefinition().setMin(0);
			narrative.getDefinition().setMax("1");
			
			StructureElement contained = theStruct.addElement();
			contained.setName("contained");
			contained.setPath(join(path, '.') + ".contained");
			contained.getDefinition().addType().getCode().setValue("Resource");
			contained.getDefinition().setIsModifier(false);
			contained.getDefinition().setMin(0);
			contained.getDefinition().setMax("1");
		}
		
		if (def instanceof BaseRuntimeElementCompositeDefinition) {
			BaseRuntimeElementCompositeDefinition<?> cdef = ((BaseRuntimeElementCompositeDefinition<?>) def);
			for (BaseRuntimeChildDefinition nextChild : cdef.getChildren()) {
				if (nextChild instanceof RuntimeChildUndeclaredExtensionDefinition) {
					continue;
				}

				BaseRuntimeDeclaredChildDefinition child = (BaseRuntimeDeclaredChildDefinition) nextChild;
				StructureElement elem = theStruct.addElement();
				fillMinAndMaxAndDefinitions(child, elem);

				if (child instanceof RuntimeChildResourceBlockDefinition) {
					RuntimeResourceBlockDefinition nextDef = (RuntimeResourceBlockDefinition) child.getSingleChildOrThrow();
					fillProfile(theStruct, elem, nextDef, path, child);
				} else if (child instanceof RuntimeChildDeclaredExtensionDefinition) {
					throw new IllegalStateException("Unexpected child type: " + child.getClass().getCanonicalName());
				} else if (child instanceof RuntimeChildCompositeDatatypeDefinition || child instanceof RuntimeChildPrimitiveDatatypeDefinition || child instanceof RuntimeChildChoiceDefinition || child instanceof RuntimeChildResourceDefinition) {
					Iterator<String> childNamesIter = child.getValidChildNames().iterator();
					BaseRuntimeElementDefinition<?> nextDef = child.getChildByName(childNamesIter.next());
					fillBasics(elem, nextDef, path, child);
					fillName(elem, nextDef);
					while (childNamesIter.hasNext()) {
						nextDef = child.getChildByName(childNamesIter.next());
						fillName(elem, nextDef);
					}
					path.pollLast();
				} else {
					throw new IllegalStateException("Unexpected child type: " + child.getClass().getCanonicalName());
				}

			}
		} else {
			throw new IllegalStateException("Unexpected child type: " + def.getClass().getCanonicalName());
		}

		path.pollLast();
	}

	private void fillExtensions(Structure theStruct, LinkedList<String> path, List<RuntimeChildDeclaredExtensionDefinition> extList, String elementName, boolean theIsModifier) {
		if (extList.size() > 0) {
			StructureElement extSlice = theStruct.addElement();
			extSlice.setName(elementName);
			extSlice.setPath(join(path, '.') + '.' + elementName);
			extSlice.getSlicing().getDiscriminator().setValue("url");
			extSlice.getSlicing().setOrdered(false);
			extSlice.getSlicing().setRules(SlicingRulesEnum.OPEN);
			extSlice.getDefinition().addType().setCode(DataTypeEnum.EXTENSION);

			for (RuntimeChildDeclaredExtensionDefinition nextExt : extList) {
				StructureElement nextProfileExt = theStruct.addElement();
				nextProfileExt.getDefinition().setIsModifier(theIsModifier);
				nextProfileExt.setName(extSlice.getName());
				nextProfileExt.setPath(extSlice.getPath());
				fillMinAndMaxAndDefinitions(nextExt, nextProfileExt);
				StructureElementDefinitionType type = nextProfileExt.getDefinition().addType();
				type.setCode(DataTypeEnum.EXTENSION);
				if (nextExt.isDefinedLocally()) {
					type.setProfile(nextExt.getExtensionUrl().substring(nextExt.getExtensionUrl().indexOf('#')));
				} else {
					type.setProfile(nextExt.getExtensionUrl());
				}
			}
		} else {
			StructureElement extSlice = theStruct.addElement();
			extSlice.setName(elementName);
			extSlice.setPath(join(path, '.') + '.' + elementName);
			extSlice.getDefinition().setIsModifier(theIsModifier);
			extSlice.getDefinition().addType().setCode(DataTypeEnum.EXTENSION);
			extSlice.getDefinition().setMin(0);
			extSlice.getDefinition().setMax("*");
		}
	}

	private void fillName(StructureElement elem, BaseRuntimeElementDefinition<?> nextDef) {
		if (nextDef instanceof RuntimeResourceReferenceDefinition) {
			RuntimeResourceReferenceDefinition rr = (RuntimeResourceReferenceDefinition) nextDef;
			for (Class<? extends IResource> next : rr.getResourceTypes()) {
				RuntimeResourceDefinition resDef = rr.getDefinitionForResourceType(next);
				StructureElementDefinitionType type = elem.getDefinition().addType();
				type.getCode().setValue("ResourceReference");
				type.getProfile().setValueAsString(resDef.getResourceProfile());
			}

			return;
		}

		StructureElementDefinitionType type = elem.getDefinition().addType();
		String name = nextDef.getName();
		DataTypeEnum fromCodeString = DataTypeEnum.VALUESET_BINDER.fromCodeString(name);
		if (fromCodeString == null) {
			throw new ConfigurationException("Unknown type: " + name);
		}
		type.setCode(fromCodeString);
	}

	private void fillMinAndMaxAndDefinitions(BaseRuntimeDeclaredChildDefinition child, StructureElement elem) {
		elem.getDefinition().setMin(child.getMin());
		if (child.getMax() == Child.MAX_UNLIMITED) {
			elem.getDefinition().setMax("*");
		} else {
			elem.getDefinition().setMax(Integer.toString(child.getMax()));
		}

		if (isNotBlank(child.getShortDefinition())) {
			elem.getDefinition().getShort().setValue(child.getShortDefinition());
		}
		if (isNotBlank(child.getFormalDefinition())) {
			elem.getDefinition().getFormal().setValue(child.getFormalDefinition());
		}
	}

	private void fillBasics(StructureElement theElement, BaseRuntimeElementDefinition<?> def, LinkedList<String> path, BaseRuntimeDeclaredChildDefinition theChild) {
		if (path.isEmpty()) {
			path.add(def.getName());
			theElement.setName(def.getName());
		} else {
			path.add(WordUtils.uncapitalize(theChild.getElementName()));
			theElement.setName(theChild.getElementName());
		}
		theElement.setPath(StringUtils.join(path, '.'));
	}

}
