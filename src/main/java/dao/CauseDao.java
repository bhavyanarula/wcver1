package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import beans.CauseBean;
import beans.NgoBean;

public class CauseDao {
	 
	
	public static HashMap<Integer, String> getCauseMasterList(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<Integer, String> causeList=new HashMap<Integer, String>();
		NgoBean ngoBean=new NgoBean();
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery("select * from causes_master_table");
		while(rs.next())
		{	
			causeList.put(Integer.valueOf(rs.getInt("cause_code_pk")), rs.getString("cause_name"));
		}
		rs.close();
		stmt.close();
		return causeList;
	}
	public static List<CauseBean> getCauseBeanMasterList(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<CauseBean> causeList=new ArrayList<CauseBean>();
		NgoBean ngoBean=new NgoBean();
		Statement stmt=conn.createStatement();
		ResultSet rs=stmt.executeQuery("select * from causes_master_table");
		while(rs.next())
		{	
			causeList.add(new CauseBean(rs.getInt("cause_code_pk"), rs.getString("cause_name"), rs.getString("cause_icon")));
		}
		rs.close();
		stmt.close();
		return causeList;
	}
	public static String getCauseName(Connection con, int code) throws SQLException
	{
		String result = "";
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("select * from causes_master_table where cause_code_pk="+code);
		while(rs.next())
		{	
			result = rs.getString("cause_name");
		}
		rs.close();
		stmt.close();
		return result;
	}
	public static Integer createNewCause(Connection con, String ngoUid, int causeCode) throws SQLException {
		Integer result = new Integer("-1");
		Statement stmt=con.createStatement();
		try {
			stmt.execute("insert into ngo_causes_table(nct_ngo_uid_fk, nct_cause_code_fk)"
					+ " values ('"+ngoUid+"',"+causeCode+")");
			result = Integer.valueOf(causeCode);
		} catch (SQLIntegrityConstraintViolationException e) {
			
		}
		stmt.close();
		return result;
	}
	public static void deleteCause(Connection conn, String ngoUid, String[] currentCauseCode) throws SQLException {
		String codeString = "";
		for (int i = 0; i < currentCauseCode.length; i++) {
			codeString =  currentCauseCode[i] + "," + codeString;
		}
		Statement stmt = conn.createStatement();
		stmt.execute("delete from ngo_causes_table where nct_cause_code_fk in (" +codeString.substring(0, codeString.length()-1)+ ") "
				+ "and nct_ngo_uid_fk='"+ngoUid+"'");
		stmt.close();
	}

}
