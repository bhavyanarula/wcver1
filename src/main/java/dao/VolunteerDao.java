package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import beans.VolunteerBean;
import security.SecurityUtil;
import util.Constants;

public class VolunteerDao {

	public static String createNew(Connection conn, VolunteerBean volunteerBean, Integer pId) throws SQLException {
		// TODO Auto-generated method stub
		String id = SecurityUtil.encrypt(volunteerBean.getEmail());
		Statement stmt = conn.createStatement();
		try {
			stmt.execute("insert into volunteer_table() "
					+ "values('"+id+"','"+volunteerBean.getEmail()+"','"+volunteerBean.getName()
					+"','"+volunteerBean.getContact()+"',"+volunteerBean.getAge()+","+pId+",'"+volunteerBean.getGender()+"', 1)");
		} catch (SQLIntegrityConstraintViolationException e) {
			
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return id;
	}

	public static boolean newApplication(Connection conn, int eventId, String vId) throws SQLException {
		try {	
			Statement stmt = conn.createStatement();
			stmt.execute("insert into event_volunteer_table (e_v_event_id_fk, e_v_vol_id_fk, e_v_status) values("
					+eventId+", '"+vId+"', '"+Constants.APPLICATION_STATUS_UNVERIFIED+"')");
			stmt.close();
			return true;
			} catch (SQLIntegrityConstraintViolationException e) {
				return false;
		}
	}

	public static VolunteerBean getVolunteerBean(Connection conn, String vId) throws SQLException {
		// TODO Auto-generated method stub
		Statement stmt = conn.createStatement();
		VolunteerBean vBean = null;
		ResultSet rs = stmt.executeQuery("select v_email, v_uid, v_name from volunteer_table where v_uid = '"+vId+"'");
		if(rs.next())
			vBean = new VolunteerBean(vId, rs.getString("v_name"), rs.getString("v_email"), "", 0, "", "");
		rs.close();
		stmt.close();
		return vBean;
		
	}

	public static boolean checkVolunteerAlreadyExists(Connection conn, Integer eventId, String email) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select e_v_event_id_fk, e_v_vol_id_fk, v_email from event_volunteer_table "
				+ "join volunteer_table on v_uid = e_v_vol_id_fk "
				+ "where v_email='"+email+"' and e_v_event_id_fk="+eventId);
		if(rs.next())
			return true;
		else
			return false;
	}

}
