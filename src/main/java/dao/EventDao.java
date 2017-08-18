package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import beans.AddressBean;
import beans.EventBean;
import beans.VolunteerBean;
import util.Constants;
import util.ImageUtil;

public class EventDao {

	public static int createNewEvent(Connection conn, EventBean eventBean, String ngoUid) throws SQLException {
		int eventId = 0;
		eventBean.getAddressBean().setCode(AddressDao.getAddressMasterCode(conn, eventBean.getAddressBean()));
		PreparedStatement pStmt = conn.prepareStatement("insert into events_table("
				+ "evt_name, evt_details, evt_date, evt_time, evt_address_code_fk, evt_organizer_code_fk, evt_status, evt_venue) "
				+ "values(?,?,?,?,?,?,'create', ?)", Statement.RETURN_GENERATED_KEYS );
		pStmt.setString(1, eventBean.getName());
		pStmt.setString(2, eventBean.getDetails());
		Date d = new Date(eventBean.getCalendar().getTimeInMillis());
		pStmt.setDate(3, d);
		pStmt.setString(4, eventBean.getEvtTime());
		pStmt.setInt(5, eventBean.getAddressBean().getCode());
		pStmt.setString(6, ngoUid);
		pStmt.setString(7, eventBean.getAddressBean().getStreet());
		pStmt.execute();
		ResultSet rsGetAutoId = pStmt.getGeneratedKeys();
		if (rsGetAutoId.next())
			eventId = rsGetAutoId.getInt(1);
		rsGetAutoId.close();
		pStmt.close();
		
		return eventId;
	}
	public static int updateEventChanges(Connection conn, EventBean eventBean) throws SQLException {
		// TODO Auto-generated method stub
		int eventId = 0;
		PreparedStatement pStmt = conn.prepareStatement("update events_table set "
				+ "evt_name=?, evt_details=?, evt_date=?, evt_time=?, evt_address_code_fk=?, evt_venue=? where evt_code_pk=? and evt_organizer_code_fk=?");
		pStmt.setString(1, eventBean.getName());
		pStmt.setString(2, eventBean.getDetails());
		Date d = new Date(eventBean.getCalendar().getTimeInMillis());
		pStmt.setDate(3, d);
		pStmt.setString(4, eventBean.getEvtTime());
		pStmt.setInt(5, AddressDao.getAddressMasterCode(conn, eventBean.getAddressBean()));
		pStmt.setString(6, eventBean.getAddressBean().getStreet());
		pStmt.setInt(7, eventBean.getId());
		pStmt.setString(8, eventBean.getOrganizer());
		pStmt.executeUpdate();
		eventId = eventBean.getId();
		pStmt.close();
		EventDao.promoteEventState(conn, eventBean.getOrganizer(), eventId, Constants.EVENTBEAN_STATUS_CREATE);
		return eventId;
	}

	public static EventBean getEventBean(Connection conn, int id) throws SQLException {
		EventBean eventBean = null;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select evt_code_pk, evt_name, evt_date, evt_address_code_fk,"
				+ "evt_details, evt_time, evt_venue, evt_organizer_code_fk, evt_dp_p_id, evt_status, evt_work_req, "
				+ "add_area, add_city, add_state, add_pincode from events_table et join address_master_table amt on et.evt_address_code_fk = "
				+ "amt.add_code_pk where evt_code_pk="+id);
		while (rs.next()) {
			java.sql.Date d  = rs.getDate("evt_date");
			Calendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(d.getTime());
			AddressBean evtAddressBean = new AddressBean(rs.getInt("evt_address_code_fk"), rs.getString("add_area"), rs.getString("add_city"),
					rs.getString("add_state"), rs.getInt("add_pincode"), rs.getString("evt_venue"));
			evtAddressBean.setStreet(rs.getString("evt_venue"));
			eventBean = new EventBean(rs.getInt("evt_code_pk"), rs.getString("evt_name"),
					rs.getString("evt_details"), calendar, evtAddressBean,
					rs.getString("evt_time"), rs.getString("evt_organizer_code_fk"),
					PhotoDao.getUrlFromPhotoId(conn, rs.getInt("evt_dp_p_id"), false), rs.getString("evt_work_req"), rs.getString("evt_status"));
			eventBean.setListOfEventPhotos(PhotoDao.getListOfPhotoURLs(conn, ""+id,"all", "event", 0 , 20, true));
		}
		rs.close();
		stmt.close();

		return eventBean;
	}
	public static EventBean getEventBean(Connection conn, int id, List<String> selectables) throws SQLException {
		if(selectables.contains("all")){
			selectables.remove("all");
			selectables.add(Constants.EVENTBEAN_ID);
			selectables.add(Constants.EVENTBEAN_NAME);
			selectables.add(Constants.EVENTBEAN_DETAILS);
			selectables.add(Constants.EVENTBEAN_CALENDAR);
			selectables.add(Constants.EVENTBEAN_TIME);
			selectables.add(Constants.EVENTBEAN_ADDRESS);
			selectables.add(Constants.EVENTBEAN_ORGANIZER);
			selectables.add(Constants.EVENTBEAN_IMAGE_URL);
			selectables.add(Constants.EVENTBEAN_WORK_REQ);
			selectables.add(Constants.EVENTBEAN_STATUS);
			selectables.add(Constants.EVENTBEAN_PHOTOS_URLS);
		}
		//selectables = new ArrayList<String>(selectables);
		String joinQuery = "";
		if(!selectables.contains(Constants.EVENTBEAN_ID))
			selectables.add(Constants.EVENTBEAN_ID);
		if(selectables.contains(Constants.EVENTBEAN_ADDRESS)){
			
			selectables.remove(Constants.EVENTBEAN_ADDRESS);
			selectables.add(Constants.EVENTBEAN_VENUE); 
			selectables.add("amt.add_state");
			selectables.add("amt.add_pincode");
			selectables.add("amt.add_city");
			selectables.add("amt.add_area");
			joinQuery = "  join address_master_table amt on amt.add_code_pk="+Constants.EVENTBEAN_ADDRESS;
		}
		
		EventBean eventBean = new EventBean();
		Statement stmt = conn.createStatement();
		if(selectables.contains(Constants.EVENTBEAN_PHOTOS_URLS)){
			selectables.remove(Constants.EVENTBEAN_PHOTOS_URLS);
			eventBean.setListOfEventPhotos(PhotoDao.getListOfPhotoURLs(conn, ""+id,"all", "event", 0 , 20, true));
		}
		String selectString = "";
		selectString = selectables.toString();
		selectString = selectString.substring(1, selectString.length()-1);
			
		ResultSet rs = stmt.executeQuery("select "+selectString+" from events_table "+joinQuery+" where evt_code_pk =" + id);
		if (rs.next()) {
			
			
			eventBean.setId(id);
			if(selectables.contains(Constants.EVENTBEAN_NAME))
				eventBean.setName(rs.getString(Constants.EVENTBEAN_NAME));
			if(selectables.contains(Constants.EVENTBEAN_CALENDAR))
			{
				java.sql.Date d  = rs.getDate("evt_date");
				Calendar calendar = new GregorianCalendar();
				calendar.setTimeInMillis(d.getTime());
				eventBean.setCalendar(calendar);
			}
			if(selectables.contains(Constants.EVENTBEAN_DETAILS))
				eventBean.setDetails(rs.getString(Constants.EVENTBEAN_DETAILS));
			if(selectables.contains(Constants.EVENTBEAN_IMAGE_URL))
				eventBean.setImageURL(PhotoDao.getUrlFromPhotoId(conn, rs.getInt("evt_dp_p_id"), false));
			if(selectables.contains(Constants.EVENTBEAN_NAME))
				eventBean.setName(rs.getString(Constants.EVENTBEAN_NAME));
			if(selectables.contains(Constants.EVENTBEAN_ORGANIZER))
				eventBean.setOrganizer(rs.getString(Constants.EVENTBEAN_ORGANIZER));
			if(selectables.contains(Constants.EVENTBEAN_STATUS))
				eventBean.setStatus(rs.getString(Constants.EVENTBEAN_STATUS));
			if(selectables.contains(Constants.EVENTBEAN_TIME))
				eventBean.setEvtTime(rs.getString(Constants.EVENTBEAN_TIME));
			if(selectables.contains(Constants.EVENTBEAN_WORK_REQ))
				eventBean.setWorkReq(rs.getString(Constants.EVENTBEAN_WORK_REQ));
			if(selectables.contains(Constants.EVENTBEAN_ADDRESS)){
				eventBean.setAddressBean(new AddressBean(rs.getInt("evt_address_code_fk"), 
						rs.getString("add_area"),rs.getString("add_city"), rs.getString("add_state"), 
						rs.getInt("add_pincode"), rs.getString("evt_venue")));
			}
		}
		rs.close();
		stmt.close();

		return eventBean;
	}

	public static List<EventBean> getEventList(Connection conn, String ngoUid) throws SQLException {
		List<EventBean> eventBeanList = new LinkedList<EventBean>();
		Statement stmt = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery("select evt_code_pk, evt_name, evt_date, evt_address_code_fk,"
				+ "evt_details, evt_time, evt_venue, evt_organizer_code_fk, evt_dp_p_id, evt_status, "
				+ "add_area, add_city, add_state, add_pincode from events_table et join address_master_table amt on et.evt_address_code_fk = "
				+ "amt.add_code_pk where evt_organizer_code_fk ='" + ngoUid 
						+ "' and (evt_status='"+Constants.EVENTBEAN_STATUS_ACTIVE+"' or evt_status='"+Constants.EVENTBEAN_STATUS_OPEN+"')"
								+ " order by evt_date)");
		while (rs.next()) {
			java.sql.Date d  = rs.getDate("evt_date");
			Calendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(d.getTime());
			AddressBean evtAddressBean = new AddressBean(rs.getInt("evt_address_code_fk"), rs.getString("add_area"), rs.getString("add_city"),
					rs.getString("add_state"), rs.getInt("add_pincode"), rs.getString("evt_venue"));
			evtAddressBean.setStreet(rs.getString("evt_venue"));
			eventBeanList.add(new EventBean(rs.getInt("evt_code_pk"), rs.getString("evt_name"),
					rs.getString("evt_details"), calendar, evtAddressBean,
					rs.getString("evt_time"), rs.getString("evt_organizer_code_fk"),
					PhotoDao.getUrlFromPhotoId(conn, rs.getInt("evt_dp_p_id"), true),"", rs.getString("evt_status")));
		}
		rs.close();
		stmt.close();
		return eventBeanList;
	}

	public static void updateImageURL(Connection conn, int eventId, String pFName, String pFPath, String pFExt) throws SQLException  {
		Statement stmt = conn.createStatement();
		stmt.execute("insert into photo_table(p_file_name, p_file_path, p_file_extension, p_category, p_owner_id) values('"
				+ pFName + "','" + pFPath + "','" + pFExt + "','eventDp','" + eventId + "')",
				Statement.RETURN_GENERATED_KEYS);
		ResultSet rsGetAutoId = stmt.getGeneratedKeys();
		int pId = -1;
		if (rsGetAutoId.next())
			pId = rsGetAutoId.getInt(1);
		stmt.execute("update events_table set evt_dp_p_id=" + pId + " where evt_code_pk=" + eventId);
		rsGetAutoId.close();
		stmt.close();
	}

	public static void deleteEvent(Connection conn, int eventId, String folderPath) throws SQLException {
		
		ArrayList<File> filesToDelete = new ArrayList<File>();
		try {
			File imagesFolder = new File(folderPath);
			for(File item : imagesFolder.listFiles()){
				filesToDelete.add(item);
			}
			filesToDelete.add(imagesFolder);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement stmt = conn.prepareStatement("delete from photo_table where p_owner_id=? and p_category='event' ");
		stmt.setString(1, ""+eventId);
		stmt.execute();
		stmt.close();
		stmt = conn.prepareStatement("delete from events_table where evt_code_pk=?");
		stmt.setInt(1, eventId);
		stmt.execute();
		stmt.close();
		conn.commit();
		if(!filesToDelete.isEmpty()){
			for(File item : filesToDelete){
				item.delete();
			}
		}
	}

	public static List<EventBean> searchByLocation(Connection conn, String currentCity, int start, int count) throws SQLException {
		String cityCondition = "";
		if(!currentCity.equals(""))
			cityCondition = "add_city ='" + currentCity + "' and";
		List<EventBean> eventList = new ArrayList<EventBean>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select evt_code_pk, evt_name, evt_date, evt_address_code_fk,"
				+ "evt_details, evt_time, evt_venue, evt_organizer_code_fk, evt_dp_p_id, evt_status, "
				+ "add_area, add_city, add_state, add_pincode from events_table et join address_master_table amt on et.evt_address_code_fk = "
				+ "amt.add_code_pk where "+cityCondition+" (evt_status='"+Constants.EVENTBEAN_STATUS_ACTIVE+"' or evt_status='"+Constants.EVENTBEAN_STATUS_OPEN+"') "
						+ "order by evt_date limit "+start+","+count);
		while (rs.next()) {
			java.sql.Date d  = rs.getDate("evt_date");
			Calendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(d.getTime());
			AddressBean evtAddressBean = new AddressBean(rs.getInt("evt_address_code_fk"), rs.getString("add_area"), rs.getString("add_city"),
					rs.getString("add_state"), rs.getInt("add_pincode"), rs.getString("evt_venue"));
			evtAddressBean.setStreet(rs.getString("evt_venue"));
			eventList.add(new EventBean(rs.getInt("evt_code_pk"), rs.getString("evt_name"),
					rs.getString("evt_details"), calendar, evtAddressBean,
					rs.getString("evt_time"), rs.getString("evt_organizer_code_fk"),
					PhotoDao.getUrlFromPhotoId(conn, rs.getInt("evt_dp_p_id"), true),"", ""));
		}
		rs.close();
		stmt.close();
		return eventList;
	}

	public static void createNewWork(Connection conn, String organizerId, int eventId, String newWork) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.execute(
				"update events_table set evt_work_req = '" + newWork + "', evt_status = 'open' where evt_code_pk=" + eventId+" and evt_organizer_code_fk='"+organizerId+"'");
		stmt.close();
	}

	public static void promoteEventState(Connection conn, String organizerId, int eventId, String strNewState) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.execute("update events_table set evt_status = '" + strNewState + "' where evt_code_pk = " + eventId+" and evt_organizer_code_fk='"+organizerId+"'");
		stmt.close();
	}

	public static int getNoOfApplications(Connection conn, int eventId, String status) throws SQLException {
		int result = 0;
		Statement stmt = conn.createStatement();
		ResultSet rs=stmt.executeQuery("select count(e_v_uid) as c, e_v_status, e_v_event_id_fk from event_volunteer_table where e_v_status='"+status
				+"' and e_v_event_id_fk="+eventId);
		if(rs.next())
			result = rs.getInt("c");
		rs.close();
		stmt.close();
		return result;
	}
	public static List<VolunteerBean> getApplicants(Connection conn, int eventId, String status, int start, int count) throws SQLException {
		
		String strStatusWhere = "";
		if(status.equalsIgnoreCase("All"))
			strStatusWhere = " evolt.e_v_status <> '"+Constants.APPLICATION_STATUS_UNVERIFIED+"' and";
		else
			strStatusWhere = " evolt.e_v_status='"+status+"' and ";
		List<VolunteerBean> volAppList = new ArrayList<VolunteerBean>();
		Statement stmt = conn.createStatement();
		ResultSet rs=stmt.executeQuery("select * from volunteer_table vol "
				+ "join event_volunteer_table evolt on evolt.e_v_vol_id_fk = vol.v_uid "
				+ "where "+strStatusWhere+" evolt.e_v_event_id_fk="+eventId+" order by vol.v_name limit "+start*count+", "+count);
		while(rs.next())
		{
			String vId, vName, vEmail,  vContact, vImageUrl, vGender;
			vId = rs.getString(1);
			vEmail = rs.getString(2);
			vName = rs.getString(3);
			vContact = rs.getString(4);
			int vAge = rs.getInt(5);
			vImageUrl = PhotoDao.getUrlFromPhotoId(conn, rs.getInt(6), true);
			vGender = rs.getString(7);
			volAppList.add(new VolunteerBean(vId, vName, vEmail, vContact, vAge, vGender, vImageUrl));
		}
		rs.close();
		stmt.close();
		return volAppList;
	}

	public static void setApplicantStatus(Connection conn, String vId, int eventId, String strNewState) throws SQLException {
		vId = vId.replace(",", "','");
		Statement stmt = conn.createStatement();
		stmt.execute("update event_volunteer_table set e_v_status = '" + strNewState + 
				"' where e_v_event_id_fk = " + eventId+" and e_v_vol_id_fk in ('"+vId+"')");
		stmt.close();
	}

}
