package action;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import beans.EventBean;
import beans.NgoBean;
import beans.VolunteerBean;
import config.DBConnection;
import dao.EventDao;
import dao.NgoDao;
import dao.PhotoDao;
import dao.VerificationDao;
import dao.VolunteerDao;
import security.SecurityUtil;
import util.Constants;
import util.ImageUtil;
import util.MailUtil;
import util.ResultConstants;

public class VolunteerAction extends ActionSupport {
	private VolunteerBean volunteerBean;
	private int eventId;
	private String pageOwnerCode;
	private String submit;
	private File imgFile;
	private String imgFileContentType;
	private String imgFileFileName;
	
	public String saveVolunteerInfo(){
		Connection conn =  null;
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			int pId = saveImage(conn, volunteerBean.getEmail(), volunteerBean.getGender());
			String vId = VolunteerDao.createNew(conn, volunteerBean, pId);
			if(VolunteerDao.newApplication(conn, eventId, vId))
			{
				addActionMessage("Application sent, please check your email for further communications!");
				String strEmail = MailUtil.readMailHTML("welcomeVolunteer");
				int randCode = (int)(Math.random()*1000000);
				String passCode = SecurityUtil.encrypt(""+randCode);
				passCode = passCode.substring(0,8);
				VerificationDao.generateNewPasscode(conn, vId, passCode, "v");
				strEmail = strEmail.replaceAll("%#verificationLink#%", Constants.ROOTURL+"verifyVolunteer.action?userCode="+vId+"&eventId="+eventId+"&passcode="+passCode);
				strEmail = strEmail.replaceAll("%#volunteerName#%",volunteerBean.getName());
				EventBean eventBean  =  EventDao.getEventBean(conn, eventId);
				strEmail = strEmail.replaceAll("%#eventName#%", eventBean.getName());
				ArrayList<String> selectables = new ArrayList<String>();
				selectables.add(Constants.NGOBEAN_NAME);
				selectables.add(Constants.NGOBEAN_PHONE);
				selectables.add(Constants.NGOBEAN_EMAIL);
				selectables.add(Constants.NGOBEAN_ALIAS);
				NgoBean ngoBean = NgoDao.getNgoBeanFromId(conn, eventBean.getOrganizer(), selectables);
				strEmail = strEmail.replaceAll("%#ngoEmail#%", ngoBean.getNgoEmail());
				strEmail = strEmail.replaceAll("%#ngoPhone#%", ngoBean.getNgoPhone());
				strEmail = strEmail.replaceAll("%#ngoName#%", 
						"<a href=\""+Constants.ROOTURL+ngoBean.getAlias()+"\" title=\""+ngoBean.getNgoName()+"\">"+ngoBean.getNgoName()+"</a>");
				MailUtil.sendMimeMessage(volunteerBean.getEmail(), "Volunteer Application", strEmail);
				conn.commit();
				return ResultConstants.SUCCESS;
			}
			else
			{
				addActionError("You have already applied for this event! We will take further communications over email!");
				return ResultConstants.FAILURE;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException sqe) {
				sqe.printStackTrace();
			}
		}
		 catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException sqe) {
				e.printStackTrace();
			}
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ResultConstants.FAILURE;
	}
	private int saveImage(Connection con, String id, String gender) throws SQLException {
		int pId = 0;
		pId = gender.equalsIgnoreCase("female")?3:2;
		try {
			String filePath = ImageUtil.getDestinationPath("volunteer", null, null);
			String destPath = Constants.IMAGES_ROOTPATH+filePath;
			String pFPath = Constants.DB_IMAGES_ROOTPATH+filePath;
			String pFExt = ImageUtil.getExtension(imgFileContentType);
			ImageUtil.saveImage(imgFile, id, destPath, pFExt);
			pId = PhotoDao.createNew(con, id, pFPath, pFExt, "volunteer", id);
		} catch (NullPointerException npe) {
			
		}
		return pId;
	}
	public String volunteerEmailValidationAction(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String email = request.getParameter("volunteerBean.email");
		Integer eventId = Integer.parseInt(request.getParameter("eventId"));
		try(Connection conn = DBConnection.getConnection()) {
			if (!VolunteerDao.checkVolunteerAlreadyExists(conn, eventId, email))
				return ResultConstants.SUCCESS;
			else
				return ResultConstants.FAILURE;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public VolunteerBean getVolunteerBean() {
		return volunteerBean;
	}

	public void setVolunteerBean(VolunteerBean volunteerBean) {
		this.volunteerBean = volunteerBean;
	}
	public String getPageOwnerCode() {
		return pageOwnerCode;
	}

	public void setPageOwnerCode(String pageOwnerCode) {
		this.pageOwnerCode = pageOwnerCode;
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
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
}
