package action;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import beans.AboutUsBean;
import config.DBConnection;
import dao.AboutUsDao;
import dao.NgoDao;
import dao.PhotoDao;
import util.Constants;
import util.ImageUtil;
import util.ResultConstants;

public class AboutUsAction implements SessionAware {

	private SessionMap<String, Object> sessionMap;
	
	private List<AboutUsBean> aboutUsList = new ArrayList<AboutUsBean>();
	private AboutUsBean aboutUsBean = new AboutUsBean();
	private String newAboutUsHeading;
	private String newAboutUsContent;
	private int newAboutUsCode;
	private String newAboutUsPhoto;
	private long newAboutUsCreatedOn;
	private File imgFile;
	private String imgFileContentType;
	private String imgFileFileName;
	
	private String toBeUpdatedCode;
	private String toBeDeletedCode;
	private String toBePinnedCode;
	
	private String ajaxResponseDummyMsg;
	
	private String pageOwnerCode;
	
	private String defaultContent;
	
	public String openAboutUs()
	{
		
		try(Connection conn = DBConnection.getConnection()) {
			aboutUsList = AboutUsDao.getAboutUsList(conn, getPageOwnerCode(), 0 , 10);
			if(aboutUsList.size()==0)
			{
				ArrayList selectables = new ArrayList();
				selectables.add(Constants.NGOBEAN_DESCRIPTION);
				defaultContent = NgoDao.getNgoBeanFromId(conn, getPageOwnerCode(), selectables).getNgoDescription();
			}
			return ResultConstants.SUCCESS;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.FAILURE;
	}
	public String editAboutUs()
	{
		try(Connection conn = DBConnection.getConnection()) {
			//aboutUsList = AboutUsDao.getAboutUsList(conn, ""+sessionMap.get("userCode"));
			return ResultConstants.SUCCESS;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.FAILURE;
	}
	public String saveAboutUs()
	{
		try(Connection conn = DBConnection.getConnection()) {
			if(AboutUsDao.updateList(conn, aboutUsList))
			{
				return ResultConstants.SUCCESS;
			}
			else 
				return ResultConstants.FAILURE;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.FAILURE;
	}
	public String ajaxUpdateThisAboutUs()
	{
		try(Connection conn = DBConnection.getConnection()) {
			Calendar createdOn = new GregorianCalendar();
			setNewAboutUsCreatedOn(createdOn.getTimeInMillis());
			if(AboutUsDao.updateThisAboutUs(conn, toBeUpdatedCode, newAboutUsHeading, newAboutUsContent, createdOn, (String) sessionMap.get("userCode")))
			{
				if (imgFile!=null) {
					String filePath = ImageUtil.getDestinationPath("aboutUs", (String) sessionMap.get("userCode"),
							toBeUpdatedCode);
					String destPath = Constants.IMAGES_ROOTPATH + filePath;
					String pFPath = Constants.DB_IMAGES_ROOTPATH + filePath;
					String pFExt = ImageUtil.getExtension(imgFileContentType);
					File destFile = new File(destPath + "/" + toBeUpdatedCode + pFExt);
					ImageUtil.saveOrReplaceImage(imgFile, destFile, toBeUpdatedCode, destPath, pFExt);
					PhotoDao.updatePhoto(conn, toBeUpdatedCode, pFExt, "AU");
					newAboutUsPhoto = pFPath + "/" + toBeUpdatedCode + pFExt;
				}
				ajaxResponseDummyMsg = "Successfully Updated!";
				return ResultConstants.SUCCESS;
			}
			else 
			{
				ajaxResponseDummyMsg = "Failed to Update!";
				return ResultConstants.FAILURE;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.FAILURE;
	}
	public String ajaxDeleteThisAboutUs()
	{
		try(Connection conn = DBConnection.getConnection()) {
			if(AboutUsDao.deleteThisAboutUs(conn, toBeDeletedCode, (String)sessionMap.get("userCode")))
			{
				String filePath = ImageUtil.getDestinationPath("aboutUs", (String)sessionMap.get("userCode"), toBeDeletedCode);
				String destPath = Constants.IMAGES_ROOTPATH+filePath;
				ImageUtil.deleteImageFile(destPath+"/"+toBeDeletedCode+PhotoDao.getAboutUsPhotoExt(conn, toBeDeletedCode));
				PhotoDao.deleteAboutUsPhoto(conn, toBeDeletedCode);
				ajaxResponseDummyMsg = "Successfully Deleted!";
				return ResultConstants.SUCCESS;
			}
			else 
				return ResultConstants.FAILURE;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.FAILURE;
	}
	
	
	public String ajaxSaveNewAboutUs()
	{
		try(Connection conn = DBConnection.getConnection()) {
			Calendar createdOn = new GregorianCalendar();
			setNewAboutUsCreatedOn(createdOn.getTimeInMillis());
			int result = AboutUsDao.addNewAboutUs(conn, newAboutUsHeading, newAboutUsContent, ""+sessionMap.get("userCode"), createdOn);
			if(result!=-1)
			{
				setNewAboutUsCode(result);
				newAboutUsPhoto = saveImgFile(conn, ""+result);
				ajaxResponseDummyMsg = "Successfully Added!";
				aboutUsBean = new AboutUsBean(result, ""+sessionMap.get("userCode"), newAboutUsHeading, newAboutUsContent, newAboutUsPhoto, createdOn, false);
				return ResultConstants.SUCCESS;
			}
			else 
				return ResultConstants.FAILURE;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ResultConstants.FAILURE;
	}
	public String ajaxPinThisAboutUs()
	{
		try(Connection conn = DBConnection.getConnection()) {
			AboutUsDao.pinThisAboutUs(conn, toBePinnedCode, (short)1, ""+sessionMap.get("userCode"));
			ajaxResponseDummyMsg = "Post Pinned!";
			return ResultConstants.SUCCESS;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ResultConstants.FAILURE;
	}
	public String ajaxRemovePinThisAboutUs()
	{
		try(Connection conn = DBConnection.getConnection()) {
			AboutUsDao.pinThisAboutUs(conn, toBePinnedCode, (short)0, ""+sessionMap.get("userCode"));
			ajaxResponseDummyMsg = "Pin removed!";
			return ResultConstants.SUCCESS;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.FAILURE;
	}
	private String saveImgFile(Connection con, String auId) throws SQLException {
		String pFPath ="";
		String pFExt ="";
		try {
			String filePath = ImageUtil.getDestinationPath("aboutUs", (String)sessionMap.get("userCode"), auId);
			String destPath = Constants.IMAGES_ROOTPATH+filePath;
			pFPath = Constants.DB_IMAGES_ROOTPATH+filePath;
			pFExt = ImageUtil.getExtension(imgFileContentType);
			ImageUtil.saveImage(imgFile, auId, destPath, pFExt);
			int pId = PhotoDao.createNew(con, auId, pFPath, pFExt, "AU", auId);
		} catch (NullPointerException npe) {
			
		}
		return pFPath+auId+pFExt;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.sessionMap = (SessionMap<String, Object>)session;
	}
	public List<AboutUsBean> getAboutUsList() {
		return aboutUsList;
	}
	public void setAboutUsList(List<AboutUsBean> aboutUsList) {
		this.aboutUsList = aboutUsList;
	}
	public AboutUsBean getAboutUsBean() {
		return aboutUsBean;
	}
	public void setAboutUsBean(AboutUsBean aboutUsBean) {
		this.aboutUsBean = aboutUsBean;
	}
	public String getNewAboutUsHeading() {
		return newAboutUsHeading;
	}
	public void setNewAboutUsHeading(String newAboutUsHeading) {
		this.newAboutUsHeading = newAboutUsHeading;
	}
	public String getNewAboutUsContent() {
		return newAboutUsContent;
	}
	public void setNewAboutUsContent(String newAboutUsContent) {
		this.newAboutUsContent = newAboutUsContent;
	}
	public String getAjaxResponseDummyMsg() {
		return ajaxResponseDummyMsg;
	}
	public void setAjaxResponseDummyMsg(String ajaxResponseDummyMsg) {
		this.ajaxResponseDummyMsg = ajaxResponseDummyMsg;
	}
	public String getToBeUpdatedCode() {
		return toBeUpdatedCode;
	}
	public void setToBeUpdatedCode(String toBeUpdatedCode) {
		this.toBeUpdatedCode = toBeUpdatedCode;
	}
	public String getToBeDeletedCode() {
		return toBeDeletedCode;
	}
	public void setToBeDeletedCode(String toBeDeletedCode) {
		this.toBeDeletedCode = toBeDeletedCode;
	}
	public int getNewAboutUsCode() {
		return newAboutUsCode;
	}
	public void setNewAboutUsCode(int newAboutUsCode) {
		this.newAboutUsCode = newAboutUsCode;
	}
	public String getPageOwnerCode() {
		return pageOwnerCode;
	}
	public void setPageOwnerCode(String pageOwnerCode) {
		this.pageOwnerCode = pageOwnerCode;
	}
	public String getNewAboutUsPhoto() {
		return newAboutUsPhoto;
	}
	public void setNewAboutUsPhoto(String newAboutUsPhoto) {
		this.newAboutUsPhoto = newAboutUsPhoto;
	}
	public String getDefaultContent() {
		return defaultContent;
	}
	public void setDefaultContent(String defaultContent) {
		this.defaultContent = defaultContent;
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
	public long getNewAboutUsCreatedOn() {
		return newAboutUsCreatedOn;
	}
	public void setNewAboutUsCreatedOn(long newAboutUsCreatedOn) {
		this.newAboutUsCreatedOn = newAboutUsCreatedOn;
	}
	public String getToBePinnedCode() {
		return toBePinnedCode;
	}
	public void setToBePinnedCode(String toBePinnedCode) {
		this.toBePinnedCode = toBePinnedCode;
	}
	
}
