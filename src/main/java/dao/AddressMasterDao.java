package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import config.DBConnection;

public class AddressMasterDao {

	public static LinkedHashMap<String, String> getListOfStates(Connection con) throws SQLException
	{
		LinkedHashMap<String,String> listOfStates=new LinkedHashMap<String,String>();
		Statement stmt=con.createStatement();
		
		ResultSet rs=stmt.executeQuery("select distinct add_state from address_master_table order by add_state");
		while(rs.next())
		{
				listOfStates.put(rs.getString("add_state"),rs.getString("add_state"));
		}
		return listOfStates;
	}
	public static LinkedHashMap<String, String> getListOfCities(Connection con, String selectedState) throws SQLException
	{
		LinkedHashMap<String,String> listOfCities=new LinkedHashMap<String,String>();
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select distinct add_city, add_state from address_master_table where add_state='"+selectedState+"' order by add_city");
		while(rs.next())
		{
				listOfCities.put(rs.getString("add_city"),rs.getString("add_city"));
		}
		return listOfCities;
	}
	public static HashMap<String, String> getListOfPincodes(Connection con, String selectedCity) throws SQLException
	{
		LinkedHashMap<String,String> listOfPincodes=new LinkedHashMap<String,String>();
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select distinct add_pincode, add_city from address_master_table where add_city='"+selectedCity+"' order by add_pincode");
		rs.next();
		while(rs.next())
		{
				listOfPincodes.put(""+rs.getInt("add_pincode"),""+rs.getInt("add_pincode"));
		}
		return listOfPincodes;
	}
	public static Map<String, String> getListOfAreas(Connection con, String selectedPincode) throws SQLException {
		LinkedHashMap<String,String> listOfAreas=new LinkedHashMap<String,String>();
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select distinct add_area, add_pincode from address_master_table where add_pincode="+selectedPincode+" order by add_area");
		while(rs.next())
		{
				listOfAreas.put(rs.getString("add_area"),rs.getString("add_area"));
		}
		return listOfAreas;
	}
}
