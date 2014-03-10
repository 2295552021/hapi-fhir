















package ca.uhn.fhir.model.dstu.resource;

import java.util.*;
import ca.uhn.fhir.model.api.*;
import ca.uhn.fhir.model.api.annotation.*;
import ca.uhn.fhir.model.primitive.*;
import ca.uhn.fhir.model.dstu.composite.*;
import ca.uhn.fhir.model.dstu.valueset.*;

/**
 * HAPI/FHIR <b>Location</b> Resource
 * (Details and position information for a physical place)
 *
 * <p>
 * <b>Definition:</b>
 * Details and position information for a physical place where services are provided  and resources and participants may be stored, found, contained or accommodated
 * </p> 
 *
 * <p>
 * <b>Requirements:</b>
 * 
 * </p> 
 */
@ResourceDef(name="Location", profile="http://hl7.org/fhir/profiles/Location", id="location")
public class Location extends BaseResource implements IResource {

	/**
	 * Search parameter constant for <b>identifier</b>
	 * <p>
	 * Description: <b></b><br/>
	 * Type: <b>token</b><br/>
	 * Path: <b>Location.identifier</b><br/>
	 * </p>
	 */
	public static final String SP_IDENTIFIER = "identifier";

	/**
	 * Search parameter constant for <b>name</b>
	 * <p>
	 * Description: <b>A (portion of the) name of the location</b><br/>
	 * Type: <b>string</b><br/>
	 * Path: <b>Location.name</b><br/>
	 * </p>
	 */
	public static final String SP_NAME = "name";

	/**
	 * Search parameter constant for <b>type</b>
	 * <p>
	 * Description: <b>A code for the type of location</b><br/>
	 * Type: <b>token</b><br/>
	 * Path: <b>Location.type</b><br/>
	 * </p>
	 */
	public static final String SP_TYPE = "type";

	/**
	 * Search parameter constant for <b>address</b>
	 * <p>
	 * Description: <b>A (part of the) address of the location</b><br/>
	 * Type: <b>string</b><br/>
	 * Path: <b>Location.address</b><br/>
	 * </p>
	 */
	public static final String SP_ADDRESS = "address";

	/**
	 * Search parameter constant for <b>status</b>
	 * <p>
	 * Description: <b>Searches for locations with a specific kind of status</b><br/>
	 * Type: <b>token</b><br/>
	 * Path: <b>Location.status</b><br/>
	 * </p>
	 */
	public static final String SP_STATUS = "status";

	/**
	 * Search parameter constant for <b>partof</b>
	 * <p>
	 * Description: <b>The location of which this location is a part</b><br/>
	 * Type: <b>reference</b><br/>
	 * Path: <b>Location.partOf</b><br/>
	 * </p>
	 */
	public static final String SP_PARTOF = "partof";

	/**
	 * Search parameter constant for <b>near</b>
	 * <p>
	 * Description: <b>The coordinates expressed as [lat],[long] (using KML, see notes) to find locations near to (servers may search using a square rather than a circle for efficiency)</b><br/>
	 * Type: <b>token</b><br/>
	 * Path: <b></b><br/>
	 * </p>
	 */
	public static final String SP_NEAR = "near";

	/**
	 * Search parameter constant for <b>near-distance</b>
	 * <p>
	 * Description: <b>A distance quantity to limit the near search to locations within a specific distance</b><br/>
	 * Type: <b>token</b><br/>
	 * Path: <b></b><br/>
	 * </p>
	 */
	public static final String SP_NEAR_DISTANCE = "near-distance";


	@Child(name="identifier", type=IdentifierDt.class, order=0, min=0, max=1)	
	@Description(
		shortDefinition="Unique code or number identifying the location to its users",
		formalDefinition="Unique code or number identifying the location to its users"
	)
	private IdentifierDt myIdentifier;
	
	@Child(name="name", type=StringDt.class, order=1, min=0, max=1)	
	@Description(
		shortDefinition="Name of the location as used by humans",
		formalDefinition="Name of the location as used by humans. Does not need to be unique."
	)
	private StringDt myName;
	
	@Child(name="description", type=StringDt.class, order=2, min=0, max=1)	
	@Description(
		shortDefinition="Description of the Location, which helps in finding or referencing the place",
		formalDefinition="Description of the Location, which helps in finding or referencing the place"
	)
	private StringDt myDescription;
	
	@Child(name="type", type=CodeableConceptDt.class, order=3, min=0, max=1)	
	@Description(
		shortDefinition="Indicates the type of function performed at the location",
		formalDefinition="Indicates the type of function performed at the location"
	)
	private BoundCodeableConceptDt<LocationTypeEnum> myType;
	
	@Child(name="telecom", type=ContactDt.class, order=4, min=0, max=Child.MAX_UNLIMITED)	
	@Description(
		shortDefinition="Contact details of the location",
		formalDefinition="The contact details of communication devices available at the location. This can include phone numbers, fax numbers, mobile numbers, email addresses and web sites"
	)
	private List<ContactDt> myTelecom;
	
	@Child(name="address", type=AddressDt.class, order=5, min=0, max=1)	
	@Description(
		shortDefinition="Physical location",
		formalDefinition=""
	)
	private AddressDt myAddress;
	
	@Child(name="physicalType", type=CodeableConceptDt.class, order=6, min=0, max=1)	
	@Description(
		shortDefinition="Physical form of the location",
		formalDefinition="Physical form of the location, e.g. building, room, vehicle, road"
	)
	private CodeableConceptDt myPhysicalType;
	
	@Child(name="position", order=7, min=0, max=1)	
	@Description(
		shortDefinition="The absolute geographic location",
		formalDefinition="The absolute geographic location of the Location, expressed in a KML compatible manner (see notes below for KML)"
	)
	private Position myPosition;
	
	@Child(name="managingOrganization", order=8, min=0, max=1, type={
		Organization.class,
	})
	@Description(
		shortDefinition="The organization that is responsible for the provisioning and upkeep of the location",
		formalDefinition=""
	)
	private ResourceReferenceDt myManagingOrganization;
	
	@Child(name="status", type=CodeDt.class, order=9, min=0, max=1)	
	@Description(
		shortDefinition="active | suspended | inactive",
		formalDefinition=""
	)
	private BoundCodeDt<LocationStatusEnum> myStatus;
	
	@Child(name="partOf", order=10, min=0, max=1, type={
		Location.class,
	})
	@Description(
		shortDefinition="Another Location which this Location is physically part of",
		formalDefinition=""
	)
	private ResourceReferenceDt myPartOf;
	
	@Child(name="mode", type=CodeDt.class, order=11, min=0, max=1)	
	@Description(
		shortDefinition="instance | kind",
		formalDefinition="Indicates whether a resource instance represents a specific location or a class of locations"
	)
	private BoundCodeDt<LocationModeEnum> myMode;
	

	@Override
	public boolean isEmpty() {
		return super.isBaseEmpty() && ca.uhn.fhir.util.ElementUtil.isEmpty(  myIdentifier,  myName,  myDescription,  myType,  myTelecom,  myAddress,  myPhysicalType,  myPosition,  myManagingOrganization,  myStatus,  myPartOf,  myMode);
	}

	/**
	 * Gets the value(s) for <b>identifier</b> (Unique code or number identifying the location to its users).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * Unique code or number identifying the location to its users
     * </p> 
	 */
	public IdentifierDt getIdentifier() {  
		if (myIdentifier == null) {
			myIdentifier = new IdentifierDt();
		}
		return myIdentifier;
	}

	/**
	 * Sets the value(s) for <b>identifier</b> (Unique code or number identifying the location to its users)
	 *
     * <p>
     * <b>Definition:</b>
     * Unique code or number identifying the location to its users
     * </p> 
	 */
	public void setIdentifier(IdentifierDt theValue) {
		myIdentifier = theValue;
	}


  
	/**
	 * Gets the value(s) for <b>name</b> (Name of the location as used by humans).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * Name of the location as used by humans. Does not need to be unique.
     * </p> 
	 */
	public StringDt getName() {  
		if (myName == null) {
			myName = new StringDt();
		}
		return myName;
	}

	/**
	 * Sets the value(s) for <b>name</b> (Name of the location as used by humans)
	 *
     * <p>
     * <b>Definition:</b>
     * Name of the location as used by humans. Does not need to be unique.
     * </p> 
	 */
	public void setName(StringDt theValue) {
		myName = theValue;
	}


 	/**
	 * Sets the value for <b>name</b> (Name of the location as used by humans)
	 *
     * <p>
     * <b>Definition:</b>
     * Name of the location as used by humans. Does not need to be unique.
     * </p> 
	 */
	public void setName( String theString) {
		myName = new StringDt(theString); 
	}

 
	/**
	 * Gets the value(s) for <b>description</b> (Description of the Location, which helps in finding or referencing the place).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * Description of the Location, which helps in finding or referencing the place
     * </p> 
	 */
	public StringDt getDescription() {  
		if (myDescription == null) {
			myDescription = new StringDt();
		}
		return myDescription;
	}

	/**
	 * Sets the value(s) for <b>description</b> (Description of the Location, which helps in finding or referencing the place)
	 *
     * <p>
     * <b>Definition:</b>
     * Description of the Location, which helps in finding or referencing the place
     * </p> 
	 */
	public void setDescription(StringDt theValue) {
		myDescription = theValue;
	}


 	/**
	 * Sets the value for <b>description</b> (Description of the Location, which helps in finding or referencing the place)
	 *
     * <p>
     * <b>Definition:</b>
     * Description of the Location, which helps in finding or referencing the place
     * </p> 
	 */
	public void setDescription( String theString) {
		myDescription = new StringDt(theString); 
	}

 
	/**
	 * Gets the value(s) for <b>type</b> (Indicates the type of function performed at the location).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * Indicates the type of function performed at the location
     * </p> 
	 */
	public BoundCodeableConceptDt<LocationTypeEnum> getType() {  
		if (myType == null) {
			myType = new BoundCodeableConceptDt<LocationTypeEnum>(LocationTypeEnum.VALUESET_BINDER);
		}
		return myType;
	}

	/**
	 * Sets the value(s) for <b>type</b> (Indicates the type of function performed at the location)
	 *
     * <p>
     * <b>Definition:</b>
     * Indicates the type of function performed at the location
     * </p> 
	 */
	public void setType(BoundCodeableConceptDt<LocationTypeEnum> theValue) {
		myType = theValue;
	}


	/**
	 * Sets the value(s) for <b>type</b> (Indicates the type of function performed at the location)
	 *
     * <p>
     * <b>Definition:</b>
     * Indicates the type of function performed at the location
     * </p> 
	 */
	public void setType(LocationTypeEnum theValue) {
		getType().setValueAsEnum(theValue);
	}

  
	/**
	 * Gets the value(s) for <b>telecom</b> (Contact details of the location).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * The contact details of communication devices available at the location. This can include phone numbers, fax numbers, mobile numbers, email addresses and web sites
     * </p> 
	 */
	public List<ContactDt> getTelecom() {  
		if (myTelecom == null) {
			myTelecom = new ArrayList<ContactDt>();
		}
		return myTelecom;
	}

	/**
	 * Sets the value(s) for <b>telecom</b> (Contact details of the location)
	 *
     * <p>
     * <b>Definition:</b>
     * The contact details of communication devices available at the location. This can include phone numbers, fax numbers, mobile numbers, email addresses and web sites
     * </p> 
	 */
	public void setTelecom(List<ContactDt> theValue) {
		myTelecom = theValue;
	}

	/**
	 * Adds and returns a new value for <b>telecom</b> (Contact details of the location)
	 *
     * <p>
     * <b>Definition:</b>
     * The contact details of communication devices available at the location. This can include phone numbers, fax numbers, mobile numbers, email addresses and web sites
     * </p> 
	 */
	public ContactDt addTelecom() {
		ContactDt newType = new ContactDt();
		getTelecom().add(newType);
		return newType; 
	}

  
	/**
	 * Gets the value(s) for <b>address</b> (Physical location).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * 
     * </p> 
	 */
	public AddressDt getAddress() {  
		if (myAddress == null) {
			myAddress = new AddressDt();
		}
		return myAddress;
	}

	/**
	 * Sets the value(s) for <b>address</b> (Physical location)
	 *
     * <p>
     * <b>Definition:</b>
     * 
     * </p> 
	 */
	public void setAddress(AddressDt theValue) {
		myAddress = theValue;
	}


  
	/**
	 * Gets the value(s) for <b>physicalType</b> (Physical form of the location).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * Physical form of the location, e.g. building, room, vehicle, road
     * </p> 
	 */
	public CodeableConceptDt getPhysicalType() {  
		if (myPhysicalType == null) {
			myPhysicalType = new CodeableConceptDt();
		}
		return myPhysicalType;
	}

	/**
	 * Sets the value(s) for <b>physicalType</b> (Physical form of the location)
	 *
     * <p>
     * <b>Definition:</b>
     * Physical form of the location, e.g. building, room, vehicle, road
     * </p> 
	 */
	public void setPhysicalType(CodeableConceptDt theValue) {
		myPhysicalType = theValue;
	}


  
	/**
	 * Gets the value(s) for <b>position</b> (The absolute geographic location).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * The absolute geographic location of the Location, expressed in a KML compatible manner (see notes below for KML)
     * </p> 
	 */
	public Position getPosition() {  
		if (myPosition == null) {
			myPosition = new Position();
		}
		return myPosition;
	}

	/**
	 * Sets the value(s) for <b>position</b> (The absolute geographic location)
	 *
     * <p>
     * <b>Definition:</b>
     * The absolute geographic location of the Location, expressed in a KML compatible manner (see notes below for KML)
     * </p> 
	 */
	public void setPosition(Position theValue) {
		myPosition = theValue;
	}


  
	/**
	 * Gets the value(s) for <b>managingOrganization</b> (The organization that is responsible for the provisioning and upkeep of the location).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * 
     * </p> 
	 */
	public ResourceReferenceDt getManagingOrganization() {  
		if (myManagingOrganization == null) {
			myManagingOrganization = new ResourceReferenceDt();
		}
		return myManagingOrganization;
	}

	/**
	 * Sets the value(s) for <b>managingOrganization</b> (The organization that is responsible for the provisioning and upkeep of the location)
	 *
     * <p>
     * <b>Definition:</b>
     * 
     * </p> 
	 */
	public void setManagingOrganization(ResourceReferenceDt theValue) {
		myManagingOrganization = theValue;
	}


  
	/**
	 * Gets the value(s) for <b>status</b> (active | suspended | inactive).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * 
     * </p> 
	 */
	public BoundCodeDt<LocationStatusEnum> getStatus() {  
		if (myStatus == null) {
			myStatus = new BoundCodeDt<LocationStatusEnum>(LocationStatusEnum.VALUESET_BINDER);
		}
		return myStatus;
	}

	/**
	 * Sets the value(s) for <b>status</b> (active | suspended | inactive)
	 *
     * <p>
     * <b>Definition:</b>
     * 
     * </p> 
	 */
	public void setStatus(BoundCodeDt<LocationStatusEnum> theValue) {
		myStatus = theValue;
	}


	/**
	 * Sets the value(s) for <b>status</b> (active | suspended | inactive)
	 *
     * <p>
     * <b>Definition:</b>
     * 
     * </p> 
	 */
	public void setStatus(LocationStatusEnum theValue) {
		getStatus().setValueAsEnum(theValue);
	}

  
	/**
	 * Gets the value(s) for <b>partOf</b> (Another Location which this Location is physically part of).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * 
     * </p> 
	 */
	public ResourceReferenceDt getPartOf() {  
		if (myPartOf == null) {
			myPartOf = new ResourceReferenceDt();
		}
		return myPartOf;
	}

	/**
	 * Sets the value(s) for <b>partOf</b> (Another Location which this Location is physically part of)
	 *
     * <p>
     * <b>Definition:</b>
     * 
     * </p> 
	 */
	public void setPartOf(ResourceReferenceDt theValue) {
		myPartOf = theValue;
	}


  
	/**
	 * Gets the value(s) for <b>mode</b> (instance | kind).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * Indicates whether a resource instance represents a specific location or a class of locations
     * </p> 
	 */
	public BoundCodeDt<LocationModeEnum> getMode() {  
		if (myMode == null) {
			myMode = new BoundCodeDt<LocationModeEnum>(LocationModeEnum.VALUESET_BINDER);
		}
		return myMode;
	}

	/**
	 * Sets the value(s) for <b>mode</b> (instance | kind)
	 *
     * <p>
     * <b>Definition:</b>
     * Indicates whether a resource instance represents a specific location or a class of locations
     * </p> 
	 */
	public void setMode(BoundCodeDt<LocationModeEnum> theValue) {
		myMode = theValue;
	}


	/**
	 * Sets the value(s) for <b>mode</b> (instance | kind)
	 *
     * <p>
     * <b>Definition:</b>
     * Indicates whether a resource instance represents a specific location or a class of locations
     * </p> 
	 */
	public void setMode(LocationModeEnum theValue) {
		getMode().setValueAsEnum(theValue);
	}

  
	/**
	 * Block class for child element: <b>Location.position</b> (The absolute geographic location)
	 *
     * <p>
     * <b>Definition:</b>
     * The absolute geographic location of the Location, expressed in a KML compatible manner (see notes below for KML)
     * </p> 
	 */
	@Block(name="Location.position")	
	public static class Position extends BaseElement implements IResourceBlock {
	
	@Child(name="longitude", type=DecimalDt.class, order=0, min=1, max=1)	
	@Description(
		shortDefinition="Longitude as expressed in KML",
		formalDefinition="Longitude. The value domain and the interpretation are the same as for the text of the longitude element in KML (see notes below)"
	)
	private DecimalDt myLongitude;
	
	@Child(name="latitude", type=DecimalDt.class, order=1, min=1, max=1)	
	@Description(
		shortDefinition="Latitude as expressed in KML",
		formalDefinition="Latitude. The value domain and the interpretation are the same as for the text of the latitude element in KML (see notes below)"
	)
	private DecimalDt myLatitude;
	
	@Child(name="altitude", type=DecimalDt.class, order=2, min=0, max=1)	
	@Description(
		shortDefinition="Altitude as expressed in KML",
		formalDefinition="Altitude. The value domain and the interpretation are the same as for the text of the altitude element in KML (see notes below)"
	)
	private DecimalDt myAltitude;
	

	@Override
	public boolean isEmpty() {
		return super.isBaseEmpty() && ca.uhn.fhir.util.ElementUtil.isEmpty(  myLongitude,  myLatitude,  myAltitude);
	}

	/**
	 * Gets the value(s) for <b>longitude</b> (Longitude as expressed in KML).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * Longitude. The value domain and the interpretation are the same as for the text of the longitude element in KML (see notes below)
     * </p> 
	 */
	public DecimalDt getLongitude() {  
		if (myLongitude == null) {
			myLongitude = new DecimalDt();
		}
		return myLongitude;
	}

	/**
	 * Sets the value(s) for <b>longitude</b> (Longitude as expressed in KML)
	 *
     * <p>
     * <b>Definition:</b>
     * Longitude. The value domain and the interpretation are the same as for the text of the longitude element in KML (see notes below)
     * </p> 
	 */
	public void setLongitude(DecimalDt theValue) {
		myLongitude = theValue;
	}


 	/**
	 * Sets the value for <b>longitude</b> (Longitude as expressed in KML)
	 *
     * <p>
     * <b>Definition:</b>
     * Longitude. The value domain and the interpretation are the same as for the text of the longitude element in KML (see notes below)
     * </p> 
	 */
	public void setLongitude( java.math.BigDecimal theValue) {
		myLongitude = new DecimalDt(theValue); 
	}

	/**
	 * Sets the value for <b>longitude</b> (Longitude as expressed in KML)
	 *
     * <p>
     * <b>Definition:</b>
     * Longitude. The value domain and the interpretation are the same as for the text of the longitude element in KML (see notes below)
     * </p> 
	 */
	public void setLongitude( double theValue) {
		myLongitude = new DecimalDt(theValue); 
	}

	/**
	 * Sets the value for <b>longitude</b> (Longitude as expressed in KML)
	 *
     * <p>
     * <b>Definition:</b>
     * Longitude. The value domain and the interpretation are the same as for the text of the longitude element in KML (see notes below)
     * </p> 
	 */
	public void setLongitude( long theValue) {
		myLongitude = new DecimalDt(theValue); 
	}

 
	/**
	 * Gets the value(s) for <b>latitude</b> (Latitude as expressed in KML).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * Latitude. The value domain and the interpretation are the same as for the text of the latitude element in KML (see notes below)
     * </p> 
	 */
	public DecimalDt getLatitude() {  
		if (myLatitude == null) {
			myLatitude = new DecimalDt();
		}
		return myLatitude;
	}

	/**
	 * Sets the value(s) for <b>latitude</b> (Latitude as expressed in KML)
	 *
     * <p>
     * <b>Definition:</b>
     * Latitude. The value domain and the interpretation are the same as for the text of the latitude element in KML (see notes below)
     * </p> 
	 */
	public void setLatitude(DecimalDt theValue) {
		myLatitude = theValue;
	}


 	/**
	 * Sets the value for <b>latitude</b> (Latitude as expressed in KML)
	 *
     * <p>
     * <b>Definition:</b>
     * Latitude. The value domain and the interpretation are the same as for the text of the latitude element in KML (see notes below)
     * </p> 
	 */
	public void setLatitude( java.math.BigDecimal theValue) {
		myLatitude = new DecimalDt(theValue); 
	}

	/**
	 * Sets the value for <b>latitude</b> (Latitude as expressed in KML)
	 *
     * <p>
     * <b>Definition:</b>
     * Latitude. The value domain and the interpretation are the same as for the text of the latitude element in KML (see notes below)
     * </p> 
	 */
	public void setLatitude( double theValue) {
		myLatitude = new DecimalDt(theValue); 
	}

	/**
	 * Sets the value for <b>latitude</b> (Latitude as expressed in KML)
	 *
     * <p>
     * <b>Definition:</b>
     * Latitude. The value domain and the interpretation are the same as for the text of the latitude element in KML (see notes below)
     * </p> 
	 */
	public void setLatitude( long theValue) {
		myLatitude = new DecimalDt(theValue); 
	}

 
	/**
	 * Gets the value(s) for <b>altitude</b> (Altitude as expressed in KML).
	 * creating it if it does
	 * not exist. Will not return <code>null</code>.
	 *
     * <p>
     * <b>Definition:</b>
     * Altitude. The value domain and the interpretation are the same as for the text of the altitude element in KML (see notes below)
     * </p> 
	 */
	public DecimalDt getAltitude() {  
		if (myAltitude == null) {
			myAltitude = new DecimalDt();
		}
		return myAltitude;
	}

	/**
	 * Sets the value(s) for <b>altitude</b> (Altitude as expressed in KML)
	 *
     * <p>
     * <b>Definition:</b>
     * Altitude. The value domain and the interpretation are the same as for the text of the altitude element in KML (see notes below)
     * </p> 
	 */
	public void setAltitude(DecimalDt theValue) {
		myAltitude = theValue;
	}


 	/**
	 * Sets the value for <b>altitude</b> (Altitude as expressed in KML)
	 *
     * <p>
     * <b>Definition:</b>
     * Altitude. The value domain and the interpretation are the same as for the text of the altitude element in KML (see notes below)
     * </p> 
	 */
	public void setAltitude( java.math.BigDecimal theValue) {
		myAltitude = new DecimalDt(theValue); 
	}

	/**
	 * Sets the value for <b>altitude</b> (Altitude as expressed in KML)
	 *
     * <p>
     * <b>Definition:</b>
     * Altitude. The value domain and the interpretation are the same as for the text of the altitude element in KML (see notes below)
     * </p> 
	 */
	public void setAltitude( double theValue) {
		myAltitude = new DecimalDt(theValue); 
	}

	/**
	 * Sets the value for <b>altitude</b> (Altitude as expressed in KML)
	 *
     * <p>
     * <b>Definition:</b>
     * Altitude. The value domain and the interpretation are the same as for the text of the altitude element in KML (see notes below)
     * </p> 
	 */
	public void setAltitude( long theValue) {
		myAltitude = new DecimalDt(theValue); 
	}

 

	}




}