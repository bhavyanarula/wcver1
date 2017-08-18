package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import beans.PhotoBean;
import util.Constants;
import util.ImageUtil;

public class PhotoDao {
	public static String getUrlFromPhotoId(Connection con, int id, boolean thumb) throws SQLException
	{
		Statement stmt=null;
		ResultSet rs=null;
		String url="";
		stmt=con.createStatement();
		rs=stmt.executeQuery("SELECT * from photo_table where p_id="+id);
		if(rs.next())
			url = rs.getString("p_file_path")+"/"+rs.getString("p_file_name")+(thumb?"_thumb":"")+rs.getString("p_file_extension");
		rs.close();
		stmt.close();
		return url;
	}

	public static void updatePhoto(Connection con, String ownerId, String pFExt, String catg) throws SQLException  {
		Statement stmt=con.createStatement();
		stmt.execute("update photo_table set p_file_extension='"+pFExt+"' where p_owner_id ='"+ownerId+"' and p_category='"+catg+"'");
		stmt.close();
	}
	public static List<PhotoBean> getListOfPhotoURLs(Connection con, String id, String selection, String type, int start, int count, boolean thumb)
	{
		List<PhotoBean> listOfPhotos = new ArrayList<PhotoBean>();
		String limit="";
		if(count>0)
			limit = "order by p_id limit "+start+","+count;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = null;
			if(selection.equalsIgnoreCase("all")){
				if(type.equalsIgnoreCase("ngo")){
					rs = stmt.executeQuery("(select p_id, p_file_name, p_file_path, p_file_extension, p_category, p_owner_id"
							+ " from photo_table pt where p_owner_id='"+id+"' and p_category<>'ngoLogo' and p_category<>'Slideshow') "
									+ "union "
							+ "(select p_id, p_file_name, p_file_path, p_file_extension, p_category, p_owner_id"
							+ " from photo_table pt join events_table et on pt.p_owner_id = cast(et.evt_code_pk as CHAR(32)) "
							+ "where et.evt_organizer_code_fk = '"+id+"' and p_category<>'eventDp' and p_category<>'AU') "+limit);
				}
				if(type.equalsIgnoreCase("event")){
					rs = stmt.executeQuery("select p_id, p_file_name, p_file_path, p_file_extension, p_category, p_owner_id"
							+ " from photo_table pt where p_owner_id= cast('"+id+"' as char(32)) and p_category='event' "+limit);
				}
				if(type.equalsIgnoreCase("all")){
					rs = stmt.executeQuery("select p_id, p_file_name, p_file_path, p_file_extension, p_category, p_owner_id"
							+ " from photo_table pt where p_category not like 'default%' and p_category<>'Slideshow' "+limit);
				}
			}
			if(selection.equalsIgnoreCase("Slideshow"))
				rs = stmt.executeQuery("select p_id, p_file_name, p_file_path, p_file_extension, p_category, p_owner_id "
						+ "from photo_table pt where p_owner_id=cast('"+id+"' as char(32)) and p_category ='Slideshow' "+limit);
			if(selection.equalsIgnoreCase("Events"))
				rs = stmt.executeQuery("select p_id, p_file_name, p_file_path, p_file_extension, p_category, p_owner_id"
							+ " from photo_table pt join events_table et on pt.p_owner_id =  cast(et.evt_code_pk as CHAR(32)) "
							+ "where et.evt_organizer_code_fk = '"+id+"'and p_category<>'eventDp' "+limit);
			while(rs.next())
			{
				PhotoBean pBean = new PhotoBean(""+rs.getInt("p_id"), (rs.getString("p_file_path")+"/"+rs.getString("p_file_name")+(thumb?"_thumb":"")+rs.getString("p_file_extension")));
				listOfPhotos.add(pBean);
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return listOfPhotos;
	}
	public static int createNew(Connection con, String pFName, String pFPath, String pFExt, String pType, String ownerUid) throws SQLException
	{
		int pId = 0;
		Statement stmt = con.createStatement();
		stmt.execute(
				"insert into photo_table(p_file_name, p_file_path, p_file_extension, p_category, p_owner_id) values('"
						+ pFName
						+ "','"
						+ pFPath
						+ "','"
						+ pFExt
						+ "','"+pType+"','"
						+ ownerUid
						+ "')", Statement.RETURN_GENERATED_KEYS);
		ResultSet rsGetAutoId = stmt.getGeneratedKeys();
		if (rsGetAutoId.next())
			pId = rsGetAutoId.getInt(1);
		return pId;
	}
	public static String getLogoFileURL(Connection con, String type, String id) throws SQLException
	{
		String path = "";
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		if(type.equals("ngo"))
			rs = stmt.executeQuery("select ngo_uid, ngo_logo_p_id from ngos_table where ngo_uid='"+id+"'");
		else
			rs = stmt.executeQuery("select evt_code_pk, evt_dp_p_id from events_table where evt_code_pk='"+id+"'");
		if(rs.next()){
			path = getUrlFromPhotoId(con, rs.getInt(2), false);
		}
		return path;
	}

	public static void deletePhotos(Connection con, String[] deletePhotosIdArray) throws SQLException {
		Statement stmt = con.createStatement();
		StringBuffer ids = new StringBuffer("");
		for(String strCurrId : deletePhotosIdArray)
		{
			ids.append(strCurrId+",");
		}
		ids = new StringBuffer(ids.substring(0, ids.length()-1));
		String sql = "SELECT distinct(t2.p_id), t1.p_file_name, t1.p_file_path, t1.p_file_extension from photo_table t1 "
				+ "JOIN photo_table t2 ON t1.`p_file_name` = t2.`p_file_name` and t1.p_file_path = t2.p_file_path and t1.p_file_extension = t2.p_file_extension"
				+ " WHERE t1.p_id in ("+ids+")";
		ResultSet rs =  stmt.executeQuery(sql);
		ArrayList<String> deleteFiles = new ArrayList<String>();
		String deleteIds = "";
		while(rs.next()){
			String path = Constants.ROOTPATH + rs.getString("t1.p_file_path").replace("uploads", "");
			String strFile1 = path+"/"+rs.getString("t1.p_file_name")+rs.getString("t1.p_file_extension");
			String strFile2 = path+"/"+rs.getString("t1.p_file_name")+"_thumb"+rs.getString("t1.p_file_extension");
			deleteIds += rs.getInt("t2.p_id") + ",";
			if(!deleteFiles.contains(strFile1)){
				deleteFiles.add(strFile1);
				deleteFiles.add(strFile2);
			}
		}
		rs.close();
		stmt.close();
		Iterator<String> iter = deleteFiles.iterator();
		while(iter.hasNext()){
			ImageUtil.deleteImageFile(iter.next());
		}
		stmt = con.createStatement();
		stmt.execute("delete from photo_table where p_id in ("+deleteIds.substring(0, deleteIds.length()-1)+")");
		stmt.close();
	}
	public static void deleteAboutUsPhoto(Connection con, String toBeDeletedCode) throws SQLException{
		PreparedStatement stmt = con.prepareStatement("delete from photo_table where p_owner_id=? and p_category='AU'");
		stmt.setString(1, toBeDeletedCode);
		stmt.execute();
	}
	public static void addToSlideshow(Connection conn, String[] imgsArr, String ownerId) throws SQLException{
		StringBuffer statement = new StringBuffer("");
		for(String strCurrId : imgsArr)
		{
			statement.append(strCurrId+",");
		}
		statement = new StringBuffer(statement.substring(0, statement.length()-1));
		PreparedStatement stmt = conn.prepareStatement("insert into photo_table(p_file_name, p_file_path, p_file_extension, p_category, p_owner_id) "
		+ "select p_file_name,  p_file_path, p_file_extension, ?, ? from photo_table where p_id in("+statement.toString()+")");
		stmt.setString(1, "Slideshow");
		stmt.setString(2, ownerId);
		stmt.executeUpdate();
	}
	public static void removeFromSlideshow(Connection conn, String[] imgsArr, String ownerId) throws SQLException{
		StringBuffer statement = new StringBuffer("");
		for(String strCurrId : imgsArr)
		{
			statement.append(strCurrId+",");
		}
		statement = new StringBuffer(statement.substring(0, statement.length()-1));
		PreparedStatement stmt = conn.prepareStatement("delete from photo_table where p_owner_id=? and p_id in("+statement.toString()+")");
		stmt.setString(1, ownerId);
		stmt.execute();
	}
	
	public static String getAboutUsPhotoExt(Connection con, String toBeDeletedCode) throws SQLException {
		PreparedStatement stmt = null;
		String url = "";
		stmt =  con.prepareStatement("select p_file_extension from photo_table where p_owner_id=? and p_category=?");
		stmt.setString(1, toBeDeletedCode);
		stmt.setString(2, "AU");
		ResultSet rs = stmt.executeQuery();
		if(rs.next())
			url = rs.getString("p_file_extension");
		stmt.close();
		return url;
	}
}
