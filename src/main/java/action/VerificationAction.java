package action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.opensymphony.xwork2.ActionSupport;

import beans.NgoBean;
import config.DBConnection;
import dao.EventDao;
import dao.NgoDao;
import dao.UserDao;
import dao.VerificationDao;
import security.SecurityUtil;
import util.Constants;
import util.MailUtil;
import util.ResultConstants;

public class VerificationAction  extends ActionSupport {
	private String userCode;
	private String passcode;
	private String email;
	private String password;
	private Integer eventId;
	private Integer responseCode = new Integer("0");
	private String responseString;
	public String verifyNGO(){
		try(Connection conn = DBConnection.getConnection()) {
			String result = VerificationDao.validatePasscode(conn, userCode, passcode, "v");
			if(result.equals("success"))
			{
				UserDao.setUserAsVerified(conn, userCode);
				addActionMessage("Verification Successfull, Login to gain access to your account");
			}
			else if(result.equals("passcodeExpired"))
				addActionMessage("This verification email is not valid as the Verification link is expired. \n "
						+ "Please get your account reverified with this link:\n "
						+ "<a href=\"javascript:void(0)\" onclick = \"return openVerifyDiv()\">Resend Verification Email</a>");
			else if(result.equals("userDoesNotExist"))
				addActionMessage("This verification email is not valid or has already been verified!");
			else if(result.equals("invalidLink"))
				addActionMessage("This verification email is invalid. \n "
						+ "Please get your account reverified or create a new account");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "success";
	}
	public String verifyVolunteer(){
		try(Connection conn = DBConnection.getConnection()) {
			String result = VerificationDao.validatePasscode(conn, userCode, passcode, "v");
			if(result.equals("success"))
			{
				//UserDao.setUserAsVerified(conn, userCode);
				EventDao.setApplicantStatus(conn, userCode, eventId, Constants.APPLICATION_STATUS_WAITING);
				addActionMessage("Verification Successfull, You will be notified via email once your profile is selected. ");
			}
			else if(result.equals("passcodeExpired"))
				addActionMessage("This verification email is not valid as the Verification link is expired. \n "
						+ "Please get your account reverified with this link:\n "
						+ "<a href=\"javascript:void(0)\" onclick = \"return openVerifyDiv()\">Resend Verification Email</a>");
			else if(result.equals("userDoesNotExist"))
				addActionMessage("This verification email is not valid or has already been verified!");
			else if(result.equals("invalidLink"))
				addActionMessage("This verification email is invalid. \n "
						+ "Please get your account reverified or create a new account");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "success";
	}
	
	public String createNewPassword(){
		userCode = SecurityUtil.encrypt(email);
		try(Connection con = DBConnection.getConnection()) {
			String result = verifyOTP(con);
			if(result.equals("success"))
			{
				UserDao.updateUser(con, userCode, password);
				addActionMessage("New password is set, Login to gain access to your account");
			}
			else if(result.equals("passcodeExpired"))
				addActionError("This OTP is expired. \n "
						+ "Please regenerate your OTP");
			else if(result.equals("userDoesNotExist"))
				addActionError("This email is not valid ");
			else if(result.equals("invalidLink"))
				addActionError("Wrong OTP! Please regenerate your OTP");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	private String verifyOTP(Connection con) throws SQLException{
		
		return VerificationDao.validatePasscode(con, userCode, passcode, "fg");
	}
	public static String sendVerificationMail(String ngoUid, String name, String email){
		String verEmail = MailUtil.readMailHTML("welcomeNGO");
		verEmail = verEmail.replaceAll("%#ngoName#%", name);
		int randCode = (int)(Math.random()*1000000);
		String passCode = SecurityUtil.encrypt(""+randCode);
		passCode = passCode.substring(0,8);
		verEmail = verEmail.replaceAll("%#verificationLink#%", Constants.ROOTURL+"verifyNGO.action?userCode="+ngoUid+"&passcode="+passCode);
		verEmail = verEmail.replaceAll("%#resendLink#%", Constants.ROOTURL+"resendVerificationMail.action?userCode="+ngoUid);
		MailUtil.sendMimeMessage(email, "Verify your account with Welfare Community", verEmail);
		return passCode;
		
	}
	public static String sendForgotPasswordMail(String ngoUid, String name, String email){
		String verEmail = MailUtil.readMailHTML("forgotPassword");
		verEmail = verEmail.replaceAll("%#ngoName#%", name);
		int randCode = (int)(Math.random()*1000000);
		String passCode = SecurityUtil.encrypt(""+randCode);
		passCode = passCode.substring(0,8);
		verEmail = verEmail.replaceAll("%#OTP#%", passCode);
		MailUtil.sendMimeMessage(email, "Get access to your Welfare Community account", verEmail);
		return passCode;
		
	}
	public String resendVerificationMail(){
		String response = "";
		try(Connection con = DBConnection.getConnection()){
			if(!UserDao.getUserVerificationStatus(con, userCode))
			{
				String name ="";
				ArrayList selectables = new ArrayList();
				selectables.add(Constants.NGOBEAN_NAME);
				selectables.add(Constants.NGOBEAN_EMAIL);
				NgoBean ngoBean = NgoDao.getNgoBeanFromId(con, userCode, selectables);
				name = ngoBean.getNgoName(); 
				String passcode = sendVerificationMail(userCode, name, ngoBean.getNgoEmail());
				VerificationDao.updatePasscode(con, userCode, passcode, "v");
				return ResultConstants.SUCCESS;
			}
			else
				addActionError("Invalid Link! Either you are already verified or the link is invalid");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return "success";
	}
	public String sendOTP(){
		ArrayList<String> selectables = new ArrayList<String>();
		selectables.add(Constants.NGOBEAN_EMAIL);
		selectables.add(Constants.NGOBEAN_NAME);
		if(email!=null && !"".equals(email)){
			String uid = SecurityUtil.encrypt(email);
			try(Connection con = DBConnection.getConnection()) {
				NgoBean ngoBean = NgoDao.getNgoBeanFromId(con, uid, selectables);
				if(ngoBean.getUid().equals("")){
					responseString = "Account does not exists with this email";
					responseCode = 404;
				}
				else{
					responseCode = 200;
					String otp = sendForgotPasswordMail(uid, ngoBean.getNgoName(), ngoBean.getNgoEmail());
					VerificationDao.generateNewPasscode(con, uid, otp, "fg");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "success";
		} else {
			return ResultConstants.FAILURE;
		}
		
		
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public Integer getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseString() {
		return responseString;
	}
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
}
