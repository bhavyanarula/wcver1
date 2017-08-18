package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import config.DBConnection;
import security.SecurityUtil;
public class UserDao {
	public static boolean validateByUid(Connection con, String uid, String pass) throws SQLException{
		return Boolean.getBoolean(validate(con, "usr_uid", uid, pass));
	}
	public static String validateByEmail(Connection con, String email, String pass) throws SQLException{
		return validate(con, "usr_email", email, pass);
	}
	public static String validate(Connection con, String validateBy, String a, String b) throws SQLException
	{
		String result="false";
		PreparedStatement stmt=con.prepareStatement("select usr_email, usr_password, usr_uid, usr_verified from users_table where "+validateBy+"=?");
		stmt.setString(1, a);
		b = SecurityUtil.encrypt(b);
		ResultSet rs=stmt.executeQuery();
		boolean emailExists = false;
		while(rs.next())
		{
			emailExists = true;
			if(rs.getString("usr_password").equals(b))
			{
				if(rs.getInt("usr_verified")==1)
					result = "true";
				else
					result = "userNotVerified";
				break;
			}
		}
		if(!emailExists)
			result = "emailDoesNotExist";
		return result;
		
	}

	public static String getUserPassword(Connection con, String ngoEmail) throws SQLException {
		// TODO Auto-generated method stub
		String result = "";
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select usr_email, usr_password from users_table where usr_email='"+ngoEmail+"'");
		if(rs.next())
			result= rs.getString("usr_password");
		else
			result= "";
		rs.close();
		stmt.close();
		return result;
	}
	public static void createUser(Connection con, String uid, String email, String password, String passcode) throws SQLException
	{
		password = SecurityUtil.encrypt(password);
		uid = SecurityUtil.encrypt(uid);
		PreparedStatement stmt = con.prepareStatement("insert into users_table(usr_uid, usr_email, usr_password, usr_type) values(?,?,?,?)");
		stmt.setString(1, uid);
		stmt.setString(2,  email);
		stmt.setString(3, password);
		stmt.setString(4, "n");
		stmt.execute();
		stmt.close();
		VerificationDao.generateNewPasscode(con, uid, passcode, "v");
			
	}
	public static void updateUser(Connection con, String uid, String password) throws SQLException
	{
		if(con==null)
		con = DBConnection.getConnection();
		Statement stmt = con.createStatement();
		password = SecurityUtil.encrypt(password);
		stmt.execute("update users_table set usr_password='"
				+ password + "' where usr_uid = '"
				+ uid + "'");
		stmt.close();
	}
	public static void setUserAsVerified(Connection con, String userCode) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("update users_table set usr_verified = 1 where usr_uid=?");
		stmt.setString(1, userCode);
		stmt.executeUpdate();
		stmt.close();
	}
	public static boolean getUserVerificationStatus(Connection con, String userCode) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("select usr_uid, usr_verified from users_table where usr_uid=?");
		boolean isVerified = false;
		stmt.setString(1, userCode);
		ResultSet rs = stmt.executeQuery();
		if(rs.next())
			isVerified = rs.getInt("usr_verified")==1;
		rs.close();
		stmt.close();
		return isVerified;
	}

}
