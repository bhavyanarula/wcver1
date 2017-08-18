package action;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import beans.AddressBean;
import beans.CauseBean;
import beans.NgoBean;
import config.DBConnection;
import dao.AddressDao;
import dao.AddressMasterDao;
import dao.CauseDao;
import dao.NgoDao;
import dao.PhotoDao;
import dao.RegistrationDao;
import dao.UserDao;
import security.SecurityUtil;
import util.Constants;
import util.ImageUtil;
import util.ResultConstants;

public class RegistrationAction extends ActionSupport implements SessionAware {

	SessionMap<String, Object> sessionMap;
	private HashMap<Integer, String> ngoCauseList = new HashMap<Integer, String>();
	private String selectedState;
	private String selectedCity;
	private String area;
	private String selectedPincode;
	private String password;
	private String mode = "create";
	private String token;

	private String ajaxResponseDummyMsg;

	private Map<String, String> stateList = new LinkedHashMap<String, String>();
	private Map<String, String> cityList = new LinkedHashMap<String, String>();
	private Map<String, String> areaList = new LinkedHashMap<String, String>();
	private Map<String, String> pincodeList = new LinkedHashMap<String, String>();
	private NgoBean ngoBean = new NgoBean();
	private AddressBean addressBean = new AddressBean();
	private CauseBean causeBean = new CauseBean();
	private String repassword;
	private int currentAcPairCode;

	private String submit;
	private String pageHeading;

	private File imgFile;
	private String imgFileContentType;
	private String imgFileFileName;
	private String selectedCause;
	private int ngoNoOfVolunteers;
	private String pageOwnerCode;
	// private String ngoBean.alias;

	private List<NgoBean> autoGenNgos = new ArrayList<NgoBean>();
	private String fillInId;
	private NgoBean fillInBean = new NgoBean();
	private String query;
	private String autoGenId;
	private String alreadySetNgoId;
	private String alreadySetNgoName;
	
	
	
	// default method for registration action to perform default actions.
	public String execute() {

		try(Connection conn = DBConnection.getConnection()) {
			if(!mode.equals("edit")){
				selectedState = (selectedState == null || "".equals(selectedState)) ? "Andaman and Nicobar Islands"
						: selectedState;
				selectedCity = (selectedCity == null || "".equals(selectedCity)) ? "Car Nicobar"
						: selectedCity;
				selectedPincode = (selectedPincode == null || ""
						.equals(selectedPincode)) ? "744301" : selectedPincode;
				
				
				ngoCauseList = CauseDao.getCauseMasterList(conn);
				stateList = AddressMasterDao.getListOfStates(conn);
				cityList = AddressMasterDao.getListOfCities(conn, selectedState);
				pincodeList = AddressMasterDao.getListOfPincodes(conn, selectedCity);
				areaList = AddressMasterDao.getListOfAreas(conn, selectedPincode);
				pageHeading = "Become a member of Welfare Community!";
				if(!"".equals(alreadySetNgoId) && alreadySetNgoId!=null){
					File logFile = new File(Constants.ROOTPATH+"/logs/auto-visited.csv");
					try(FileOutputStream fosLogs = new FileOutputStream(logFile, true)){
						fosLogs.write((alreadySetNgoId+","+new Date(System.currentTimeMillis())+"\n").getBytes());
			         } catch (Exception e) {
			        	 e.printStackTrace();
				
			}
				}
			} else {
				pageHeading = "Edit your Profile!";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultConstants.FAILURE;
		}
		return ResultConstants.SUCCESS;
	}
	public String ajaxGetAutoGenNgoNames(){
		try(Connection conn = DBConnection.getConnection()) {
			setAutoGenNgos(NgoDao.getListOfAutoGenNgosNames(conn, query, 10));
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultConstants.FAILURE;
		}
		return ResultConstants.SUCCESS;
	}
	public String ajaxFillInAction(){
		ArrayList<String> selectables = new ArrayList<String>();
		selectables.add(Constants.NGOBEAN_NAME);
		selectables.add(Constants.NGOBEAN_ALIAS);
		selectables.add(Constants.NGOBEAN_DESCRIPTION);
		selectables.add(Constants.NGOBEAN_PHONE);
		selectables.add(Constants.NGOBEAN_EMAIL);
		selectables.add(Constants.NGOBEAN_ADDRESS_LIST);
		selectables.add(Constants.NGOBEAN_CAUSE_LIST);
		try(Connection conn = DBConnection.getConnection()) {
			setFillInBean(NgoDao.getNgoBeanFromId(conn, fillInId, selectables));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultConstants.FAILURE;
		}
		return ResultConstants.SUCCESS;
	}
	public String ajaxStateChangeAction() {
		cityList.clear();
		try(Connection conn = DBConnection.getConnection()) {
			cityList = AddressMasterDao.getListOfCities(conn, selectedState);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultConstants.FAILURE;
		}
		return ResultConstants.SUCCESS;
	}

	public String ajaxCityChangeAction() {
		pincodeList.clear();
		try(Connection conn = DBConnection.getConnection()) {
			pincodeList = AddressMasterDao.getListOfPincodes(conn, selectedCity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultConstants.FAILURE;
		}
		return ResultConstants.SUCCESS;
	}

	public String ajaxPincodeChangeAction() {
		areaList.clear();
		try(Connection conn = DBConnection.getConnection()) {
			areaList = AddressMasterDao.getListOfAreas(conn, selectedPincode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultConstants.FAILURE;
		}
		return ResultConstants.SUCCESS;
	}

	public String register() {
		String result = "";
		Connection con = null;
		
		if (submit.equals("Create Profile")) {
			setMode("create");

			try {
				con = DBConnection.getConnection();
				con.setAutoCommit(false);
				String ngoUid = SecurityUtil.encrypt(ngoBean.getNgoEmail());
				ngoBean.setNgoName(util.StringUtil.toSentanceCase(ngoBean.getNgoName().toLowerCase()));
				
				if(!"".equals(autoGenId) && autoGenId!=null){
					NgoDao.deleteAutoGenNgo(con, autoGenId);
					File logFile = new File(Constants.ROOTPATH+"/logs/auto-converted.csv");
					try(FileOutputStream fosLogs = new FileOutputStream(logFile, true)){
						fosLogs.write((autoGenId+","+new Date(System.currentTimeMillis())+"\n").getBytes());
			         } catch (Exception e) {
			        	 e.printStackTrace();
				
			         }
				
				}
				int pId = saveLogoFile(con, ngoUid);
				NgoDao.createNew(con, ngoUid, ngoBean.getNgoEmail(),ngoBean.getNgoName(), ngoBean.getNgoDescription(),
						ngoBean.getNgoPhone(), ngoBean.getNoOfVolunteers(), pId, ngoBean.getAlias());
				AddressDao.createNewAddress(con, ngoUid, addressBean.getStreet(), addressBean.getArea(), addressBean.getPincode(), addressBean.getCity(), addressBean.getState());
				CauseDao.createNewCause(con, ngoUid, causeBean.getCauseCode());
				
				
				String passcode = VerificationAction.sendVerificationMail(ngoUid, ngoBean.getNgoName(), ngoBean.getNgoEmail());
				UserDao.createUser(con, ngoBean.getNgoEmail(), ngoBean.getNgoEmail(), password, passcode);
				
				con.commit();
				//setPageOwnerCode(ngoUid);
				//sessionMap.put("userCode", ngoUid);
				addActionMessage("We have sent a verification email at "+ngoBean.getNgoEmail()+", Please verify account to login to your profile");
				result = ResultConstants.REGISTRATION_ACTION_CREATE_SUCCESS;
			} catch (SQLException sqle) {
				try {
					con.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sqle.printStackTrace();
				if (sqle.getErrorCode() == 1062)
					addActionError("This email already exists!");
				result = ResultConstants.REGISTRATION_ACTION_CREATE_FAILURE;

			} catch (Exception exc) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				exc.printStackTrace();
			} finally {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (submit.equals("Update Changes")){
			try {
				con = DBConnection.getConnection();
				con.setAutoCommit(false);
				String ngoUid = (String)sessionMap.get("userCode");
				NgoDao.updateNgo(con, ngoUid, ngoBean.getNgoName(), ngoBean.getNgoDescription(), ngoBean.getNgoPhone(), ngoBean.getNoOfVolunteers());
				con.commit();
				setPageOwnerCode(ngoUid);
				result = ResultConstants.REGISTRATION_ACTION_UPDATE_SUCCESS;
			} catch (SQLException sqle) {
				try {
					result = ResultConstants.REGISTRATION_ACTION_UPDATE_FAILURE;
					con.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sqle.printStackTrace();
			} finally {
				try {
					con.setAutoCommit(true);
					con.close();
					sessionMap.put("isUserModified", true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
			result = ResultConstants.REGISTRATION_ACTION_UPDATE_CANCEL;
		return result;
	}
	private int saveLogoFile(Connection con, String ngoUid) throws SQLException {
		int pId = 0;
		try {
			String filePath = ImageUtil.getDestinationPath("ngo", ngoUid, null);
			String destPath = Constants.IMAGES_ROOTPATH+filePath;
			String pFPath = Constants.DB_IMAGES_ROOTPATH+filePath;
			String pFExt = ImageUtil.getExtension(imgFileContentType);
			ImageUtil.saveImage(imgFile, "logo", destPath, pFExt);
			pId = PhotoDao.createNew(con, "logo", pFPath, pFExt, "ngologo", ngoUid);
		} catch (NullPointerException npe) {
			
		}
		return pId;
	}

	public String editProfile() {
		setMode("edit");
		pageHeading = "Edit your details:";
		try(Connection conn = DBConnection.getConnection()) {
			String ngoUid = (String) sessionMap.get("userCode");
			setNgoBean(NgoDao.getNgoBeanFromId(conn, ngoUid));
			setPassword(UserDao.getUserPassword(conn, ngoUid));
			execute();
		} catch (Exception sqle) {
			sqle.printStackTrace();
		}

		return ResultConstants.SUCCESS;
	}

	public String aliasNameValidationAction() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String alias = request.getParameter("ngoBean.alias");
		String oAlias = request.getParameter("notValidate");
		if(alias.equalsIgnoreCase(oAlias))
			ajaxResponseDummyMsg = "validAlias";
		else{
			try(Connection conn = DBConnection.getConnection()) {
				if (!RegistrationDao.checkDuplicateAlias(conn, alias))
					ajaxResponseDummyMsg = "validAlias";
				else
					ajaxResponseDummyMsg = "invalidAlias";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ResultConstants.SUCCESS;
	}

	public String emailValidationAction() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String em = request.getParameter("ngoBean.ngoEmail");
		String oEmail = request.getParameter("notValidate");
		
		if(em.equalsIgnoreCase(oEmail))
			ajaxResponseDummyMsg = "validEmail";
		else{
			try(Connection conn = DBConnection.getConnection()) {
				if (!RegistrationDao.checkDuplicateEmail(conn, em))
					ajaxResponseDummyMsg = "validEmail";
				else
					ajaxResponseDummyMsg = "invalidEmail";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ResultConstants.SUCCESS;
	}


	public String getAjaxResponseDummyMsg() {
		return ajaxResponseDummyMsg;
	}

	public void setAjaxResponseDummyMsg(String ajaxResponseDummyMsg) {
		this.ajaxResponseDummyMsg = ajaxResponseDummyMsg;
	}

	public String getPageHeading() {
		return pageHeading;
	}

	public void setPageHeading(String pageHeading) {
		this.pageHeading = pageHeading;
	}

	public int getNgoNoOfVolunteers() {
		return ngoNoOfVolunteers;
	}

	public void setNgoNoOfVolunteers(int ngoNoOfVolunteers) {
		this.ngoNoOfVolunteers = ngoNoOfVolunteers;
	}

	public String getPageOwnerCode() {
		return pageOwnerCode;
	}

	public void setPageOwnerCode(String pageOwnerCode) {
		this.pageOwnerCode = pageOwnerCode;
	}
	public HashMap<Integer, String> getNgoCauseList() {
		return ngoCauseList;
	}

	public void setNgoCauseList(HashMap<Integer, String> ngoCauseList) {
		this.ngoCauseList = ngoCauseList;
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

	public NgoBean getNgoBean() {
		return ngoBean;
	}

	public void setNgoBean(NgoBean ngoBean) {
		this.ngoBean = ngoBean;
	}

	public AddressBean getAddressBean() {
		return addressBean;
	}

	public void setAddressBean(AddressBean addressBean) {
		this.addressBean = addressBean;
	}

	public CauseBean getCauseBean() {
		return causeBean;
	}

	public void setCauseBean(CauseBean causeBean) {
		this.causeBean = causeBean;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public void setSession(Map sessionMap) {
		this.sessionMap = (SessionMap) sessionMap;
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

	public String getSelectedCause() {
		return selectedCause;
	}

	public void setSelectedCause(String selectedCause) {
		this.selectedCause = selectedCause;
	}


	public int getCurrentAcPairCode() {
		return currentAcPairCode;
	}

	public void setCurrentAcPairCode(int currentAcPairCode) {
		this.currentAcPairCode = currentAcPairCode;
	}
	public List<NgoBean> getAutoGenNgos() {
		return autoGenNgos;
	}
	public void setAutoGenNgos(List<NgoBean> autoGenNgos) {
		this.autoGenNgos = autoGenNgos;
	}

	public String getFillInId() {
		return fillInId;
	}

	public void setFillInId(String fillInId) {
		this.fillInId = fillInId;
	}

	public NgoBean getFillInBean() {
		return fillInBean;
	}

	public void setFillInBean(NgoBean fillInBean) {
		this.fillInBean = fillInBean;
	}
	public String getAutoGenId() {
		return autoGenId;
	}
	public void setAutoGenId(String autoGenId) {
		this.autoGenId = autoGenId;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAlreadySetNgoName() {
		return alreadySetNgoName;
	}
	public void setAlreadySetNgoName(String alreadySetNgoName) {
		this.alreadySetNgoName = alreadySetNgoName;
	}
	public String getAlreadySetNgoId() {
		return alreadySetNgoId;
	}
	public void setAlreadySetNgoId(String alreadySetNgoId) {
		this.alreadySetNgoId = alreadySetNgoId;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

}