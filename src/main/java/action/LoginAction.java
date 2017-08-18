package action;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import beans.NgoBean;
import beans.UserBean;
import config.DBConnection;
import dao.NgoDao;
import dao.UserDao;
import security.SecurityUtil;
import util.ResultConstants;
import util.Constants;

public class LoginAction extends ActionSupport implements SessionAware{
	SessionMap<String,Object> sessionMap;
	private String pageOwnerCode;
	private UserBean loginUserBean;
	public UserBean getLoginUserBean() {
		return loginUserBean;
	}

	public void setLoginUserBean(UserBean userBean) {
		this.loginUserBean = userBean;
	}
	
	public String execute()
	{
		String response = "";
		try(Connection con = DBConnection.getConnection()) {
			response = UserDao.validateByEmail(con, loginUserBean.getEmail(), loginUserBean.getPassword());
		 
			if(response.equalsIgnoreCase("true"))
			{
				String uid = SecurityUtil.encrypt(loginUserBean.getEmail());
			    sessionMap.put("userCode", uid);
			    setPageOwnerCode(uid);
			    ArrayList selectables = new ArrayList();
			    selectables.add(Constants.NGOBEAN_NAME);
			    selectables.add(Constants.NGOBEAN_LOGO_P_ID);
			    NgoBean ngoBean = NgoDao.getNgoBeanFromId(con, uid, selectables);
			    sessionMap.put("username", ngoBean.getNgoName());
			    sessionMap.put("logoUrl", ngoBean.getNgoLogoUrl());
				return ResultConstants.SUCCESS;
			}
			else if(response.equalsIgnoreCase("false"))
			{
				addActionError("Password is not valid");
			}
			else if(response.equalsIgnoreCase("userNotVerified"))
			{
				addActionError("This account is not yet verified, Please check your email for verification.");
			}
			else if(response.equalsIgnoreCase("emailDoesNotExist"))
			{
				addActionError("There is no account registered with this email id!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.FAILURE;
	}
	public void setSession(Map sessionMap) {  
		this.sessionMap=(SessionMap)sessionMap;
	}  
	  
	public String logout(){  
	    sessionMap.invalidate();  
	    return ResultConstants.SUCCESS;  
	}

	public String getPageOwnerCode() {
		return pageOwnerCode;
	}

	public void setPageOwnerCode(String pageOwnerCode) {
		this.pageOwnerCode = pageOwnerCode;
	}

}
