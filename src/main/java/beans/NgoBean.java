package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NgoBean {

	private String alias;
	private String uid;
	private String ngoEmail;
	private String ngoName;
	private String ngoDescription;
	private String ngoLogoUrl;
	private String ngoPhone;
	private Integer noOfVolunteers = new Integer("0");
	private Integer noOfAppreciations = new Integer("0");
	private String type = "user";
	
	private List<AddressBean> ngoAddressBeanList;
	private List<CauseBean> ngoCauseBeanList;
	private List<EventBean> ngoEventBeanList;
	private List<PhotoBean> listOfSlideshowPhotos = new ArrayList<PhotoBean>();
	public NgoBean()
	{
		
	}
	
	public NgoBean(String ngoUid, String ngoEmail, String ngoName, String ngoDescription,String ngoLogoPId,  String ngoPhone, List<AddressBean> ngoAddressBeanList, List<CauseBean> ngoCauseBeanList, String alias) {
		super();
		this.uid = ngoUid;
		this.ngoEmail = ngoEmail;
		this.ngoName = ngoName;
		this.ngoDescription = ngoDescription;
		this.ngoLogoUrl = ngoLogoPId;
		this.ngoPhone = ngoPhone;
		this.ngoEmail = ngoEmail;
		this.ngoAddressBeanList=ngoAddressBeanList;
		this.ngoCauseBeanList=ngoCauseBeanList;
		this.setAlias(alias);
		
	}
	public String getNgoEmail() {
		return ngoEmail;
	}
	public String getNgoName() {
		return ngoName;
	}
	public void setNgoName(String ngoName) {
		this.ngoName = ngoName;
	}
	public String getNgoDescription() {
		return ngoDescription;
	}
	public void setNgoDescription(String ngoDescription) {
		this.ngoDescription = ngoDescription;
	}
	public String getNgoLogoUrl() {
		return ngoLogoUrl;
	}
	public void setNgoLogoUrl(String ngoLogoUrl) {
		this.ngoLogoUrl = ngoLogoUrl;
	}
	public String getNgoPhone() {
		return ngoPhone;
	}
	public void setNgoPhone(String ngoPhone) {
		this.ngoPhone = ngoPhone;
	}
	
	public void setNgoEmail(String ngoEmail) {
		this.ngoEmail = ngoEmail;
	}

	public Integer getNoOfVolunteers() {
		return noOfVolunteers;
	}

	public void setNoOfVolunteers(Integer noOfVolunteers) {
		this.noOfVolunteers = noOfVolunteers;
	}

	public Integer getNoOfAppreciations() {
		return noOfAppreciations;
	}

	public void setNoOfAppreciations(Integer noOfAppreciations) {
		this.noOfAppreciations = noOfAppreciations;
	}

	public List<AddressBean> getNgoAddressBeanList() {
		return ngoAddressBeanList;
	}

	public void setNgoAddressBeanList(List<AddressBean> ngoAddressBeanList) {
		this.ngoAddressBeanList = ngoAddressBeanList;
	}

	public List<CauseBean> getNgoCauseBeanList() {
		return ngoCauseBeanList;
	}

	public void setNgoCauseBeanList(List<CauseBean> ngoCauseBeanList) {
		this.ngoCauseBeanList = ngoCauseBeanList;
	}

	public void setNgoEventBeanList(List<EventBean> eventList) {
		// TODO Auto-generated method stub
		this.ngoEventBeanList = eventList;
	}
	public List<EventBean> getNgoEventBeanList()
	{
		return ngoEventBeanList;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<PhotoBean> getListOfSlideshowPhotos() {
		return listOfSlideshowPhotos;
	}

	public void setListOfSlideshowPhotos(List<PhotoBean> listOfSlideshowPhotos) {
		this.listOfSlideshowPhotos = listOfSlideshowPhotos;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	/*@Override
	public boolean equals(Object otherBean){
		
		boolean result = false;
		try {
			result = ((NgoBean)otherBean).getUid().equals(getUid());
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public int hashCode(){
		return getUid().hashCode();
	}*/
	
}
