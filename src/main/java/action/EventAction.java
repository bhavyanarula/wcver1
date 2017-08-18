package action;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import beans.AddressBean;
import beans.CauseBean;
import beans.EventBean;
import beans.NgoBean;
import beans.VolunteerBean;
import config.DBConnection;
import dao.AddressDao;
import dao.AddressMasterDao;
import dao.CauseDao;
import dao.EventDao;
import dao.NgoDao;
import dao.PhotoDao;
import util.Constants;
import util.ImageUtil;
import util.ResultConstants;

public class EventAction extends ActionSupport implements SessionAware{
	private EventBean eventBean = new EventBean();
	private int eventId;
	private String newWork;
	private String ajaxResponseDummyMsg;
	private List<EventBean> eventBeanList = new ArrayList<EventBean>(); 
	SessionMap<String, Object> sessionMap;
	private File imgFile;
	private String imgFileContentType;
	private String imgFileFileName;
	private String heading;
	private String submitButton;
	private String newEventFormAction;
	private String organizerName;
	
	private String token;
	private String pageOwnerCode; 
	
	private int noOfNewApplications;
	private int noOfAcceptedApplications;
	
	private String editEventDate;
	private String mode;
	
	private AddressBean addressBean = new AddressBean();
	private Map<String, String> stateList = new LinkedHashMap<String, String>();
	private Map<String, String> cityList = new LinkedHashMap<String, String>();
	private Map<String, String> areaList = new LinkedHashMap<String, String>();
	private Map<String, String> pincodeList = new LinkedHashMap<String, String>();
	private String selectedState;
	private String selectedCity;
	private String area;
	private String selectedPincode;
	
	private Integer start = new Integer("0");
	private boolean hasNext;
	private Integer eventMonth = new Integer("-1");
	private Integer eventYear = new Integer("-1");
	private String eventListHeading;
	private List<VolunteerBean> volunteerList = new ArrayList<VolunteerBean>();
	
	private String tourName;
	private String targetState;
	
	public String openNewEventForm()
	{
		try(Connection conn = DBConnection.getConnection()) {
			selectedState = (selectedState == null || "".equals(selectedState)) ? "Andaman Nicobar"
					: selectedState;
			selectedCity = (selectedCity == null || "".equals(selectedCity)) ? "Car Nicobar"
					: selectedCity;
			selectedPincode = (selectedPincode == null || ""
					.equals(selectedPincode)) ? "744301" : selectedPincode;
			
			
			stateList = AddressMasterDao.getListOfStates(conn);
			cityList = AddressMasterDao.getListOfCities(conn, selectedState);
			pincodeList = AddressMasterDao.getListOfPincodes(conn, selectedCity);
			areaList = AddressMasterDao.getListOfAreas(conn, selectedPincode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultConstants.FAILURE;
		}
		setHeading("Create New Event:");
		setSubmitButton("Create Event");
		setMode("create");
		setNewEventFormAction("createNewEvent");
		return ResultConstants.SUCCESS;
	}
	public String createNewEvent()
	{
		String ngoUid = (String)(sessionMap.get("userCode"));
		setPageOwnerCode(ngoUid);
		try(Connection conn = DBConnection.getConnection())
		{
			conn.setAutoCommit(false);
			eventId = EventDao.createNewEvent(conn, eventBean, ngoUid);
			eventBean.setId(eventId);
			String filePath = ImageUtil.getDestinationPath("event", ngoUid, ""+eventId);
			String destPath = Constants.IMAGES_ROOTPATH+filePath;
			String pFPath = Constants.DB_IMAGES_ROOTPATH+filePath;
		    try{
		    	String pFExt = ImageUtil.getExtension(imgFileContentType);
		    	ImageUtil.saveImage(imgFile, "eventDp", destPath, pFExt);
		    	EventDao.updateImageURL(conn, eventId,"eventDp", pFPath, pFExt);
		    }
		    catch(NullPointerException npe)
		    {
		    } 
		    conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultConstants.FAILURE;
		}
		return ResultConstants.SUCCESS;
	}
	public String openEventPage()
	{
		try(Connection conn = DBConnection.getConnection()) {
			eventBean=EventDao.getEventBean(conn, eventId);
			ArrayList<String> selectables = new ArrayList<String>();
			selectables.add(Constants.NGOBEAN_NAME);
			if(eventBean.getOrganizer().equals(sessionMap.get("userCode")+"")){
				sessionMap.put("guest", false);
				sessionMap.put("owner", true);
				sessionMap.put("visitor", false);
			}
			NgoBean sessionBean = NgoDao.getSessionNgoBeanFromId(conn, eventBean.getOrganizer());
			sessionMap.put("pageOwnerBean", sessionBean);
			organizerName = NgoDao.getNgoBeanFromId(conn, eventBean.getOrganizer(), selectables).getNgoName(); 
			//check date and change state accordingly;
			if(eventBean.getStatus().equals(Constants.EVENTBEAN_STATUS_CREATE) || eventBean.getStatus().equals(Constants.EVENTBEAN_STATUS_OPEN)){
				Calendar today = new GregorianCalendar();
				today.set(Calendar.MINUTE, 0);
				today.set(Calendar.HOUR, 0);
				today.set(Calendar.SECOND, 0);
				Calendar eventDate = eventBean.getCalendar();
				eventDate.set(Calendar.MINUTE, 0);
				eventDate.set(Calendar.HOUR, 0);
				eventDate.set(Calendar.SECOND, 0);
			/*	long diffInMillies = today.getTimeInMillis() - eventDate.getTimeInMillis();
			    List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
			    Collections.reverse(units);
			    Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
			    long milliesRest = diffInMillies;
			    for ( TimeUnit unit : units ) {
			        long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
			        long diffInMilliesForUnit = unit.toMillis(diff);
			        milliesRest = milliesRest - diffInMilliesForUnit;
			        result.put(unit,diff);
			    
				if(Integer.parseInt(""+result.get(TimeUnit.DAYS))>=0)
				{
					EventDao.promoteEventState(conn, eventId, Constants.EVENTBEAN_STATUS_ACTIVE);
					eventBean.setStatus(Constants.EVENTBEAN_STATUS_ACTIVE);
				}*/
				if(today.after(eventDate))
				// || eventDate.compareTo(today)==0)
				{
					EventDao.promoteEventState(conn, (String)sessionMap.get("userCode"), eventId, Constants.EVENTBEAN_STATUS_CLOSED);
					eventBean.setStatus(Constants.EVENTBEAN_STATUS_CLOSED);
				}
				
			}
			setNoOfNewApplications(EventDao.getNoOfApplications(conn, eventId, Constants.APPLICATION_STATUS_WAITING));
			setNoOfAcceptedApplications(EventDao.getNoOfApplications(conn, eventId, Constants.APPLICATION_STATUS_ACCEPTED));
			setVolunteerList(EventDao.getApplicants(conn, eventId, Constants.APPLICATION_STATUS_ACCEPTED, 0, 20));
			setTourName("TOUR_EVENT");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResultConstants.PAGE_NOT_FOUND;
		}
		return ResultConstants.SUCCESS;
	}
	public String getListOfEvents()
	{
		String ngoUid = getPageOwnerCode();
		if(ngoUid==null)
		{
			ngoUid = "";
			setEventListHeading("NGO Events in WelfareCommunity");
		} else
			setEventListHeading("NGO Events");
		try(Connection conn = DBConnection.getConnection()) {
			boolean isOwner = ngoUid.equals((String)sessionMap.get("userCode"));
			eventBeanList = NgoDao.getListOfEvents(conn, ngoUid, isOwner, eventMonth, eventYear, start*5, 5);
			setHasNext(!(eventBeanList.size()<5));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}
	public String openEditEventPage()
	{
		setHeading("Edit Event Details:");
		setSubmitButton("Update Changes");
		setMode("edit");
		try(Connection conn = DBConnection.getConnection()) {
			eventBean = EventDao.getEventBean(conn, eventId);
			Calendar eventDate = eventBean.getCalendar();
			DecimalFormat mFormat= new DecimalFormat("00");
			String month = ""+(mFormat.format(Double.valueOf(Integer.parseInt(""+eventDate.get(Calendar.MONTH)))+1));
			String eDate = ""+(mFormat.format(Double.valueOf(Integer.parseInt(""+eventDate.get(Calendar.DATE)))));
			editEventDate = eventDate.get(Calendar.YEAR)+"-"+month+"-"+eDate;
			selectedState = eventBean.getAddressBean().getState(); 
			selectedCity = eventBean.getAddressBean().getCity();
			selectedPincode = ""+eventBean.getAddressBean().getPincode();
			
			stateList = AddressMasterDao.getListOfStates(conn);
			cityList = AddressMasterDao.getListOfCities(conn, selectedState);
			pincodeList = AddressMasterDao.getListOfPincodes(conn, selectedCity);
			areaList = AddressMasterDao.getListOfAreas(conn, selectedPincode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setNewEventFormAction("updateEventChanges");
		return ResultConstants.SUCCESS;
	}
	public String updateEventChanges() throws SQLException
	{
		Connection conn = DBConnection.getConnection();
		conn.setAutoCommit(false);
		eventBean.setOrganizer((String)sessionMap.get("userCode"));
		eventId = EventDao.updateEventChanges(conn, eventBean);
	    try {
	    	String ngoUid = (String)sessionMap.get("userCode");
	    	/*pageOwnerCode = ngoUid;*/
	    	String pFExt = ImageUtil.getExtension(imgFileContentType);
	    	String existingFileURL = PhotoDao.getLogoFileURL(conn, "event",""+eventId);
	    	File destFile =null;
	    	if(!existingFileURL.contains("defaults//default"))
	    	{
	    		destFile = new File(Constants.ROOTPATH+existingFileURL.substring(1));
	    		
	    		ImageUtil.saveOrReplaceImage(imgFile, destFile, "eventDp", Constants.IMAGES_ROOTPATH+ImageUtil.getDestinationPath("event", ngoUid, ""+eventId), pFExt);
	    	}
	    	else
	    		ImageUtil.saveImage(imgFile, "eventDp", 
	    				Constants.IMAGES_ROOTPATH+ImageUtil.getDestinationPath("event", ngoUid, ""+eventId),
	    				pFExt);
	    	EventDao.updateImageURL(conn, eventId,"eventDp", 
	    			Constants.DB_IMAGES_ROOTPATH+ImageUtil.getDestinationPath("event", ngoUid, ""+eventId),
	    			pFExt);
	    }
	    catch(NullPointerException npe)
	    {
	    } catch (SQLException e) {
			e.printStackTrace();
		}
	    conn.commit();
	    conn.setAutoCommit(true);
	    conn.close();
	    
		return ResultConstants.SUCCESS;
	}
	public String deleteEventAction()
	{
		try(Connection conn = DBConnection.getConnection()) {
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("select evt_code_pk, evt_organizer_code_fk from events_table where evt_code_pk = ?");
			stmt.setInt(1, eventId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				String folderPath = Constants.ROOTPATH+"/images/"+rs.getString("evt_organizer_code_fk")+"/events/"+rs.getInt("evt_code_pk")+"/";
				EventDao.deleteEvent(conn, eventId, folderPath);
			}
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setPageOwnerCode(pageOwnerCode);
		return ResultConstants.SUCCESS;
	}
	public String ajaxCreateNewWork(){
		try(Connection conn = DBConnection.getConnection()) {
			EventDao.createNewWork(conn, (String)sessionMap.get("userCode"), eventId, newWork);
			setNewWork(newWork);
			ajaxResponseDummyMsg = "Added Successfully!";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}
	public String ajaxPromoteEvent(){
		try(Connection conn = DBConnection.getConnection()) {
			EventDao.promoteEventState(conn, (String)sessionMap.get("userCode"), eventId, targetState);
			ajaxResponseDummyMsg = "Event is now "+targetState+"!";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		// TODO Auto-generated method stub
		this.sessionMap = (SessionMap) sessionMap;
	}
	public String getPageOwnerCode() {
		return pageOwnerCode;
	}
	public void setPageOwnerCode(String pageOwnerCode) {
		this.pageOwnerCode = pageOwnerCode;
	}
	public String getAjaxResponseDummyMsg() {
		return ajaxResponseDummyMsg;
	}
	public void setAjaxResponseDummyMsg(String jsonResponse) {
		this.ajaxResponseDummyMsg = jsonResponse;
	}
	public int getNoOfAcceptedApplications() {
		return noOfAcceptedApplications;
	}
	public void setNoOfAcceptedApplications(int noOfAcceptedApplications) {
		this.noOfAcceptedApplications = noOfAcceptedApplications;
	}
	public int getNoOfNewApplications() {
		return noOfNewApplications;
	}
	public void setNoOfNewApplications(int noOfNewApplications) {
		this.noOfNewApplications = noOfNewApplications;
	}
	public EventBean getEventBean() {
		return eventBean;
	}
	public void setEventBean(EventBean eventBean) {
		this.eventBean = eventBean;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public File getImgFile() {
		return imgFile;
	}
	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
	public String getImgFileContentType() {
		return imgFileContentType;
	}
	public Map<String, String> getStateList() {
		return stateList;
	}
	public void setStateList(Map<String, String> stateList) {
		this.stateList = stateList;
	}
	public Map<String, String> getCityList() {
		return cityList;
	}
	public void setCityList(Map<String, String> cityList) {
		this.cityList = cityList;
	}
	public Integer getEventMonth() {
		return eventMonth;
	}
	public void setEventMonth(Integer eventMonth) {
		this.eventMonth = eventMonth;
	}
	public Integer getEventYear() {
		return eventYear;
	}
	public void setEventYear(Integer eventYear) {
		this.eventYear = eventYear;
	}
	public Map<String, String> getAreaList() {
		return areaList;
	}
	public void setAreaList(Map<String, String> areaList) {
		this.areaList = areaList;
	}
	public Map<String, String> getPincodeList() {
		return pincodeList;
	}
	public void setPincodeList(Map<String, String> pincodeList) {
		this.pincodeList = pincodeList;
	}
	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}
	public String getImgFileFileName() {
		return imgFileFileName;
	}
	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}
	public List<EventBean> getEventBeanList() {
		return eventBeanList;
	}
	public void setEventBeanList(List<EventBean> eventBeanList) {
		this.eventBeanList = eventBeanList;
	}
	public String getSubmitButton() {
		return submitButton;
	}
	public void setSubmitButton(String submitButton) {
		this.submitButton = submitButton;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getNewEventFormAction() {
		return newEventFormAction;
	}
	public void setNewEventFormAction(String newEventFormAction) {
		this.newEventFormAction = newEventFormAction;
	}
	public String getOrganizerName() {
		return organizerName;
	}
	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}
	public String getNewWork() {
		return newWork;
	}
	public void setNewWork(String newWork) {
		this.newWork = newWork;
	}
	public String getEditEventDate() {
		return editEventDate;
	}
	public void setEditEventDate(String editEventDate) {
		this.editEventDate = editEventDate;
	}
	public AddressBean getAddressBean() {
		return addressBean;
	}
	public void setAddressBean(AddressBean addressBean) {
		this.addressBean = addressBean;
	}
	public String getSelectedState() {
		return selectedState;
	}
	public void setSelectedState(String selectedState) {
		this.selectedState = selectedState;
	}
	public String getSelectedCity() {
		return selectedCity;
	}
	public void setSelectedCity(String selectedCity) {
		this.selectedCity = selectedCity;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSelectedPincode() {
		return selectedPincode;
	}
	public void setSelectedPincode(String selectedPincode) {
		this.selectedPincode = selectedPincode;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public String getEventListHeading() {
		return eventListHeading;
	}
	public void setEventListHeading(String eventListHeading) {
		this.eventListHeading = eventListHeading;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public List<VolunteerBean> getVolunteerList() {
		return volunteerList;
	}
	public void setVolunteerList(List<VolunteerBean> volunteerList) {
		this.volunteerList = volunteerList;
	}
	public String getTourName() {
		return tourName;
	}
	public void setTourName(String tourName) {
		this.tourName = tourName;
	}
	public String getTargetState() {
		return targetState;
	}
	public void setTargetState(String targetState) {
		this.targetState = targetState;
	}

}
