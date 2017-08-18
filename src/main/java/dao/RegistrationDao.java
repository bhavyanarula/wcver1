package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import config.DBConnection;

public class RegistrationDao {

	public static boolean checkDuplicateAlias(Connection con, String alias) throws SQLException {
		// TODO Auto-generated method stub
		boolean result= false;
		PreparedStatement stmt = con.prepareStatement("select ngo_alias from ngo_alias_table where ngo_alias=?");
		stmt.setString(1, alias);
		ResultSet rs = stmt.executeQuery();
		if(rs.next())
			result= true;
		else
			result= false;
		rs.close();
		stmt.close();
		return result;
	}

	public static boolean checkDuplicateEmail(Connection con, String ngoEmail) throws SQLException {
		// TODO Auto-generated method stub
		boolean result= false;
		Statement stmt=con.createStatement();
		ResultSet rs = stmt.executeQuery("select ngo_email from ngos_table where ngo_email='"+ngoEmail+"'");
		if(rs.next())
			result= true;
		else
			result= false;
		rs.close();
		stmt.close();
		return result;
	}
	   

}
