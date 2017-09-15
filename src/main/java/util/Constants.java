package util;

public class Constants {
	//public static final String ROOTURL = "http://localhost:8080/WelfareCommunity/";
	public static final String ROOTURL = "https://welfarecommunity.org/";
	//public static final String ROOTPATH = "D:/Workspaces/welfarecommunity/WelfareCommunity/WebContent";
	//public static final String ROOTPATH = System.getenv("OPENSHIFT_DATA_DIR");
	public static final String ROOTPATH = "/home/jboss/data/";
	public static final String IMAGES_ROOTPATH = ROOTPATH+"/images";
	public static final String DB_IMAGES_ROOTPATH = "uploads/images";
	
	public static final String NGOBEAN_NAME = "ngo_name";
	public static final String NGOBEAN_DESCRIPTION = "ngo_description";
	public static final String NGOBEAN_LOGO_P_ID = "ngo_logo_p_id";
	public static final String NGOBEAN_PHONE = "ngo_phone_number";
	public static final String NGOBEAN_NO_OF_VOLUNTEERS = "ngo_no_of_volunteers";
	public static final String NGOBEAN_EMAIL = "ngo_email";
	public static final String NGOBEAN_ALIAS = "ngo_alias";
	public static final String NGOBEAN_NO_OF_APPRECIATIONS = "ngo_no_of_appreciations";
	public static final String NGOBEAN_TYPE = "ngo_type";
	public static final String NGOBEAN_UID = "ngo_uid";
	public static final String NGOBEAN_ADDRESS_LIST = "address_list";
	public static final String NGOBEAN_CAUSE_LIST = "cause_list";
	
	public static final String EVENTBEAN_STATUS_CREATE = "create";
	public static final String EVENTBEAN_STATUS_OPEN = "open";
	public static final String EVENTBEAN_STATUS_ACTIVE = "active";
	public static final String EVENTBEAN_STATUS_CLOSED = "closed";
	
	public static final String EVENTBEAN_ID = "evt_code_pk";
	public static final String EVENTBEAN_NAME = "evt_name";
	public static final String EVENTBEAN_DETAILS = "evt_details";
	public static final String EVENTBEAN_CALENDAR = "evt_date";
	public static final String EVENTBEAN_TIME = "evt_time";
	public static final String EVENTBEAN_ADDRESS = "evt_address_code_fk";
	public static final String EVENTBEAN_VENUE = "evt_venue";
	public static final String EVENTBEAN_ORGANIZER = "evt_organizer_code_fk";
	public static final String EVENTBEAN_IMAGE_URL = "evt_dp_p_id";
	public static final String EVENTBEAN_WORK_REQ = "evt_work_req";
	public static final String EVENTBEAN_STATUS= "evt_status";
	public static final String EVENTBEAN_PHOTOS_URLS = "evt_photos";
	
	
	public static final String APPLICATION_STATUS_WAITING = "Waiting";
	public static final String APPLICATION_STATUS_ACCEPTED = "Accepted";
	public static final String APPLICATION_STATUS_REJECTED = "Rejected";
	public static final String APPLICATION_STATUS_UNVERIFIED = "Not Verified";
	
	public static final String PHOTO_ALBUM_ALL = "All";
	public static final String PHOTO_ALBUM_SLIDESHOW = "Slideshow";
	public static final String PHOTO_ALBUM_EVENTS = "Events";
	
	public static final String HTML_FILE_WELCOME_NGO = ROOTPATH+"/emails/Welcome.html";
	public static final String HTML_FILE_FORGOT_PASSWORD = ROOTPATH+"/emails/ForgotPassword.html";
	public static final String HTML_FILE_WELCOME_VOLUNTEER = ROOTPATH+"/emails/WelcomeVolunteer.html";
	public static final String HTML_FILE_APPLICATION_ACCEPT = ROOTPATH+"/emails/ApplicationAccept.html";
	public static final String HTML_FILE_APPLICATION_REJECT = ROOTPATH+"/emails/ApplicationReject.html";
	public static final String HTML_FILE_AUTO_MAIL = ROOTPATH+"/emails/Auto.html";
	public static final int FILE_UPLOAD_MAX_SIZE_MB = 1;
	public static final int LIST_SIZE_PER_PAGE = 10;
	public static final int SESSION_TIMEOUT_MS = 600000;
}
