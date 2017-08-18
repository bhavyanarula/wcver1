package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.AddressBean;

public class AddressDao {

	public static int createNewAddress(Connection con, String ngoUid, String street, String area, int pincode, String city, String state ) throws SQLException
	{
		int result = -1;
		PreparedStatement stmt = 
				con.prepareStatement("insert into ngo_address_table(add_text, add_master_code_fk, add_ngo_code_fk)"
				+" values (?, (select add_code_pk from address_master_table where add_area=? and add_pincode=? and add_city=? and add_state=?), ?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, street);
		stmt.setString(2, area);
		stmt.setInt(3, pincode);
		stmt.setString(4, city);
		stmt.setString(5, state);
		stmt.setString(6, ngoUid);
		stmt.execute();
		ResultSet rsGetAutoId = stmt.getGeneratedKeys();
		if (rsGetAutoId.next())
			result = rsGetAutoId.getInt(1);
		stmt.close();
		return result;
	}
	
	public static void deleteAddress(Connection conn, int code) throws SQLException{
		Statement stmt = conn.createStatement();
		stmt.execute("delete from ngo_address_table where add_code_pk="+code);
		stmt.close();
	}
	public static Integer getAddressMasterCode(Connection con, AddressBean addBean) throws SQLException{
		PreparedStatement pStmt = con.prepareStatement("select add_code_pk, add_state, add_city, add_pincode, add_area from address_master_table"
				+ " where  add_state = ? and add_city = ? and add_pincode = ? and add_area = ?");
		pStmt.setString(1, addBean.getState());
		pStmt.setString(2, addBean.getCity());
		pStmt.setInt(3, addBean.getPincode());
		pStmt.setString(4, addBean.getArea());
		ResultSet rs = pStmt.executeQuery();
		if(rs.next()){
			addBean.setCode(rs.getInt("add_code_pk"));
		}
		rs.close();
		pStmt.close();
		return addBean.getCode();
	}
}
