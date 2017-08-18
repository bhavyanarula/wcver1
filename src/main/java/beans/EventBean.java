package beans;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
@Conversion(
	    conversions = {
	         @TypeConversion(key = "calendar", converter = "util.DateConverter")
	    }
	)
public class EventBean {
	private int id;
	private String name;
	private String details;
	private Calendar calendar = new GregorianCalendar();
	private String evtTime;
	private AddressBean addressBean;
	private String organizer;
	private String imageURL;
	private String workReq;
	private String status;
	private List<PhotoBean> listOfEventPhotos;
	
	public EventBean(int id, String name, String details,
			Calendar calendar, AddressBean address, String evtTime, String organizer, String imageURL, String workReq, String status) {
		this.id=id;
		this.name=name;
		this.details = details;
		this.calendar = calendar;
		this.evtTime  = evtTime;
		this.addressBean = address;
		this.organizer = organizer;
		this.imageURL = imageURL;
		this.workReq = workReq;
		this.status = status;
		// TODO Auto-generated constructor stub
	}
	public EventBean()
	{}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public Calendar getCalendar() {
		return calendar;
	}
	/*public void setCalendar(String calendar) {
		Integer year = new Integer(calendar.substring(0, calendar.indexOf("-")));
		calendar = calendar.substring(calendar.indexOf("-")+1);
		Integer month = new Integer(calendar.substring(0, calendar.indexOf("-")));
		calendar = calendar.substring(calendar.indexOf("-")+1);
		Integer day = new Integer(calendar);
		this.calendar = new GregorianCalendar (year, month-1, day);
	}*/
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
	public String getEvtTime() {
		return evtTime;
	}
	public void setEvtTime(String eTime) {
		this.evtTime = eTime;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public List<PhotoBean> getListOfEventPhotos() {
		return listOfEventPhotos;
	}
	public void setListOfEventPhotos(List<PhotoBean> listOfEventPhotos) {
		this.listOfEventPhotos = listOfEventPhotos;
	}
	public String getWorkReq() {
		return workReq;
	}
	public void setWorkReq(String workReq) {
		this.workReq = workReq;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public AddressBean getAddressBean() {
		return addressBean;
	}
	public void setAddressBean(AddressBean address) {
		this.addressBean = address;
	}
	/*@Override
	public boolean equals(Object otherBean){
		boolean result = false;
		try {
			result = ((EventBean)otherBean).getId() == getId();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public int hashCode(){
		return getId()+"".hashCode();
	}*/

}
