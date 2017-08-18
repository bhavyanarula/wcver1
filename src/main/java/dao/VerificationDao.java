package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import config.DBConnection;
import util.Constants;

public class VerificationDao {

	public static String validatePasscode(Connection con, String userCode, String passcode, String type) throws SQLException {
		String result = "";
		PreparedStatement stmt = con.prepareStatement("select * from passcode_table where pt_user_code_fk=? and pt_type=? order by id desc");
		stmt.setString(1, userCode);
		stmt.setString(2, type);
		ResultSet rs = stmt.executeQuery();
		boolean passHasNotExpired = false;
		if(rs.next()){
			//first check if already verified
			if(rs.getString("pt_user_passcode").equals(passcode)){
				/*java.sql.Date validityDate = rs.getDate("pt_validity");
				java.util.Date todaysDate = new java.util.Date(System.currentTimeMillis());*/
				Calendar today = new GregorianCalendar();
				today.set(Calendar.MINUTE, 0);
				today.set(Calendar.HOUR, 0);
				today.set(Calendar.SECOND, 0);
				Calendar validityDate = new GregorianCalendar();
				validityDate.setTime(rs.getDate("pt_validity"));
				validityDate.set(Calendar.MINUTE, 59);
				validityDate.set(Calendar.HOUR, 23);
				validityDate.set(Calendar.SECOND, 59);
				if(today.before(validityDate))
				{
					result = "success";
					deletePasscode(con, userCode, type);
				}
				else
					result = "passcodeExpired";
			}
			else
				result = "invalidLink";
		}
		else
			result = "userDoesNotExist";
		rs.close();
		stmt.close();
		return result;
	}

	private static void deletePasscode(Connection con, String userCode, String type) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("delete from passcode_table where pt_user_code_fk=? and pt_type=?");
		stmt.setString(1, userCode);
		stmt.setString(2, type);
		stmt.execute();
		stmt.close();
	}

	public static void generateNewPasscode(Connection con, String uid, String passcode, String type) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("insert into passcode_table(pt_user_code_fk, pt_user_passcode, pt_validity, pt_type) values(?,?,?,?)");
		stmt.setString(1, uid);
		stmt.setString(2, passcode);
		java.sql.Date validityDate = new java.sql.Date(System.currentTimeMillis());
		Calendar c = new GregorianCalendar();
		c.setTime(validityDate);
		c.add(Calendar.DATE, 1);
		validityDate.setTime(c.getTimeInMillis());
		stmt.setDate(3, validityDate);
		stmt.setString(4, type);
		stmt.execute();
		stmt.close();
	}
	public static void updatePasscode(Connection con, String uid, String passcode, String type) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("update passcode_table set pt_user_passcode = ?, pt_validity =? where pt_user_code_fk=? "
				+ "and pt_type = ?");
		
		stmt.setString(1, passcode);
		java.sql.Date validityDate = new java.sql.Date(System.currentTimeMillis());
		Calendar c = new GregorianCalendar();
		c.setTime(validityDate);
		c.add(Calendar.DATE, 1);
		validityDate.setTime(c.getTimeInMillis());
		stmt.setDate(2, validityDate);
		stmt.setString(3, uid);
		stmt.setString(4, type);
		stmt.executeUpdate();
		stmt.close();
	}

}
