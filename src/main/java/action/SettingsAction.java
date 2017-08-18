package action;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import beans.AddressBean;
import beans.CauseBean;
import config.DBConnection;
import dao.AddressDao;
import dao.AddressMasterDao;
import dao.CauseDao;
import dao.NgoDao;
import dao.PhotoDao;
import dao.UserDao;
import util.Constants;
import util.ImageUtil;
import util.ResultConstants;

public class SettingsAction extends ActionSupport implements SessionAware {
	SessionMap<String, Object> sessionMap;
	
	private List<AddressBean> addressBeanList;
	private List<CauseBean> causeBeanList;
	
	private int currentAddressCode;
	private String[] deleteCauseCodeArray;
	private Map<String, String> stateList = new LinkedHashMap<String, String>();
	private Map<String, String> cityList = new HashMap<String, String>();
	private Map<String, String> areaList = new HashMap<String, String>();
	private Map<String, String> pincodeList = new HashMap<String, String>();
	private String selectedState;
	private String selectedCity;
	private String area;
	private String selectedPincode;
	private Map<Integer, String> causeList = new HashMap<Integer, String>();
	
	private AddressBean addressBean = new AddressBean();
	private AddressBean newAddressBean = new AddressBean();
	private Integer newCauseCode = new Integer("-1");
	private String newCauseName = "";
	
	private String ajaxResponseDummyMsg;
	
	private File imgFile;
	private String imgFileContentType;
	private String imgFileFileName;
	
	private String oldPassword;
	private String newPassword;

	private Integer newMasterCauseCode;
	
	
	public String openSettings(){
		String ngoUid = (String)sessionMap.get("userCode");
		setItemsForEditAddress(ngoUid);
		return ResultConstants.SUCCESS;
	}
	private void setItemsForEditAddress(String ngoUid){
		try(Connection conn = DBConnection.getConnection()) {
			setAddressBeanList(NgoDao.getAddressListOfNgo(conn, ngoUid));
			setCauseBeanList(NgoDao.getCauseListOfNgo(conn, ngoUid));
			selectedState = (selectedState == null || "".equals(selectedState)) ? "Andaman and Nicobar Islands"
					: selectedState;
			selectedCity = (selectedCity == null || "".equals(selectedCity)) ? "Car Nicobar"
					: selectedCity;
			selectedPincode = (selectedPincode == null || ""
					.equals(selectedPincode)) ? "744301" : selectedPincode;
			causeList = CauseDao.getCauseMasterList(conn);
			
			ArrayList<CauseBean> alNgoCauseList = (ArrayList<CauseBean>)NgoDao.getCauseListOfNgo(conn, ngoUid);
			for(CauseBean cb : alNgoCauseList){
				causeList.remove(cb.getCauseCode());
			}
			stateList = AddressMasterDao.getListOfStates(conn);
			cityList = AddressMasterDao.getListOfCities(conn, selectedState);
			pincodeList = AddressMasterDao.getListOfPincodes(conn, selectedCity);
			areaList = AddressMasterDao.getListOfAreas(conn, selectedPincode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String ajaxAddNewAddressAction() {
		String ngoUid = (String) sessionMap.get("userCode");
		int code;
		try(Connection conn = DBConnection.getConnection()) {
			code = AddressDao.createNewAddress(conn, ngoUid, addressBean.getStreet(), addressBean.getArea(), addressBean.getPincode(),
					addressBean.getCity(), addressBean.getState());
			newAddressBean = new AddressBean(code, addressBean.getArea(), addressBean.getCity(), 
					addressBean.getState(), addressBean.getPincode(), addressBean.getStreet());
			ajaxResponseDummyMsg = "Successfully added!";
			sessionMap.put("isUserModified", true);
		} catch (SQLException e) {
			e.printStackTrace();
			ajaxResponseDummyMsg = "Could not add, please refresh the page and try again!";
		}
		return ResultConstants.SUCCESS;
	}
	public String ajaxAddNewCauseAction() {
		String ngoUid = (String) sessionMap.get("userCode");
		try(Connection con = DBConnection.getConnection()) {
			newCauseCode = CauseDao.createNewCause(con, ngoUid, newMasterCauseCode);
			if(newCauseCode.equals(-1))
				ajaxResponseDummyMsg = "Already added!";
			else{
				newCauseName = CauseDao.getCauseName(con, newMasterCauseCode);
				ajaxResponseDummyMsg = "Successfully added!";
				sessionMap.put("isUserModified", true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ajaxResponseDummyMsg = "Could not add, please refresh the page and try again!";
		}
		return ResultConstants.SUCCESS;
	}
	public String ajaxDeleteAddressAction() {
		try(Connection conn = DBConnection.getConnection();) {
			AddressDao.deleteAddress(conn, getCurrentAddressCode());
			ajaxResponseDummyMsg = "Successfully deleted!";
			sessionMap.put("isUserModified", true);
			setCurrentAddressCode(currentAddressCode);
		} catch (SQLException e) {
			e.printStackTrace();
			ajaxResponseDummyMsg = "Could not delete, please refresh the page and try again!";
		}
		return ResultConstants.SUCCESS;
	}
	public String ajaxDeleteCauseAction() {
		try(Connection conn = DBConnection.getConnection()) {
			//for(String cCode : getDeleteCauseCodeArray())
			CauseDao.deleteCause(conn, (String)sessionMap.get("userCode"), getDeleteCauseCodeArray());
			ajaxResponseDummyMsg = "Successfully deleted!";
			sessionMap.put("isUserModified", true);
		} catch (SQLException e) {
			e.printStackTrace();
			ajaxResponseDummyMsg = "Could not delete, please refresh the page and try again!";
		}
		return ResultConstants.SUCCESS;
	}
	
	public String updateNgoLogo() throws SQLException{
		String ngoUid = (String)sessionMap.get("userCode");
		int pId = 0;
		String filePath = ImageUtil.getDestinationPath("ngo", ngoUid, null);
		String destPath = Constants.IMAGES_ROOTPATH+filePath;
		String pFPath = Constants.DB_IMAGES_ROOTPATH+filePath;
		String pFExt = ImageUtil.getExtension(imgFileContentType);
		
		try(Connection conn = DBConnection.getConnection()) {
			String existingLogo = PhotoDao.getLogoFileURL(conn, "ngo", ngoUid );
			if(!existingLogo.contains("defaults//default"))
			{
				String toBeReplacedFileUrl = Constants.IMAGES_ROOTPATH + existingLogo.substring(14);
				ImageUtil.saveOrReplaceImage(imgFile,new File(toBeReplacedFileUrl), "logo", destPath, pFExt);
				PhotoDao.updatePhoto(conn, ngoUid, pFExt, "ngoLogo");
			}
			else
			{
				ImageUtil.saveImage(imgFile,"logo", destPath, pFExt);
				
					pId = PhotoDao.createNew(conn, "logo", pFPath, pFExt, "ngologo", ngoUid);
					NgoDao.updateLogo(conn, ngoUid, pId);
				
			}
			sessionMap.put("isUserModified", true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}
	public void validate(){
		String ngoUid = (String)sessionMap.get("userCode");
		//if(!UserDao.validate(ngoUid, getOldPassword()))
			//addFieldError("oldPassword","Password is not valid");
	}
	public String updatePassword() throws SQLException{
		String ngoUid = (String)sessionMap.get("userCode");
		try(Connection conn = DBConnection.getConnection()) {
			if(!UserDao.validateByUid(conn, ngoUid, getOldPassword()))
			{
				addActionError("You have entered wrong current password");
				return "input";
			}
			else
				UserDao.updateUser(null, ngoUid, getNewPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}
	public String deleteAccountAction(){
		try(Connection conn = DBConnection.getConnection()) {
			conn.setAutoCommit(false);
			String ngoUid = (String)sessionMap.get("userCode");
			NgoDao.deleteNgo(conn, ngoUid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}
	
	
	
	public AddressBean getAddressBean() {
		return addressBean;
	}
	public void setAddressBean(AddressBean addressBean) {
		this.addressBean = addressBean;
	}
	public String getAjaxResponseDummyMsg() {
		return ajaxResponseDummyMsg;
	}
	public void setAjaxResponseDummyMsg(String ajaxResponseDummyMsg) {
		this.ajaxResponseDummyMsg = ajaxResponseDummyMsg;
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
	public String getSelectedState() {
		return selectedState;
	}
	public void setSelectedState(String selectedState) {
		this.selectedState = selectedState;
	}
	public String getSelectedCity() {
		return selectedCity;
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
	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}
	public String getImgFileFileName() {
		return imgFileFileName;
	}
	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
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
	public Map<Integer, String> getCauseList() {
		return causeList;
	}
	public void setCauseList(Map<Integer, String> causeList) {
		this.causeList = causeList;
	}
	public void setSession(Map sessionMap) {
		this.sessionMap = (SessionMap) sessionMap;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String password) {
		this.oldPassword = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public List<AddressBean> getAddressBeanList() {
		return addressBeanList;
	}
	public void setAddressBeanList(List<AddressBean> addressBeanList) {
		this.addressBeanList = addressBeanList;
	}
	public int getCurrentAddressCode() {
		return currentAddressCode;
	}
	public void setCurrentAddressCode(int currentAddressCode) {
		this.currentAddressCode = currentAddressCode;
	}
	public AddressBean getNewAddressBean() {
		return newAddressBean;
	}
	public void setNewAddressBean(AddressBean newAddressBean) {
		this.newAddressBean = newAddressBean;
	}
	public List<CauseBean> getCauseBeanList() {
		return causeBeanList;
	}
	public void setCauseBeanList(List<CauseBean> causeBeanList) {
		this.causeBeanList = causeBeanList;
	}
	public String[] getDeleteCauseCodeArray() {
		return deleteCauseCodeArray;
	}
	public void setDeleteCauseCodeArray(String[] deleteCauseCodeArray) {
		this.deleteCauseCodeArray = deleteCauseCodeArray;
	}
	public Integer getNewCauseCode() {
		return newCauseCode;
	}
	public void setNewCauseCode(Integer newCauseCode) {
		this.newCauseCode = newCauseCode;
	}
	public String getNewCauseName() {
		return newCauseName;
	}
	public void setNewCauseName(String newCauseName) {
		this.newCauseName = newCauseName;
	}
	public Integer getNewMasterCauseCode() {
		return newMasterCauseCode;
	}
	public void setNewMasterCauseCode(Integer newMasterCauseCode) {
		this.newMasterCauseCode = newMasterCauseCode;
	}
}
