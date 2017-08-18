package beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import config.DBConnection;

public class AddressBean {
	private int code;
	private String area;
	private String city;
	private String state;
	private int pincode;
	private String street;
	public AddressBean()
	{
		
	}
	
	public AddressBean(int code, String area, String city, String state,
			int pincode, String street) {
		super();
		this.code = code;
		this.area = area;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.street = street;
	}
	public AddressBean(int code)
	{
		Connection con = null;
		Statement stmt = null;
		try {
			con = DBConnection.getConnection();
			stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from address_master_table where add_code_pk = "+code);
			while(rs.next())
			{
				this.code = rs.getInt("add_code_pk");
				this.area = rs.getString("add_area");
				this.city = rs.getString("add_city");
				this.state = rs.getString("add_state");
				this.pincode = rs.getInt("add_pincode");
				//this.street = rs.getString("at.add_text");
			}
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
		}
		finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

}
