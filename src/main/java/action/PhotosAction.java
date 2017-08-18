package action;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import beans.EventBean;
import beans.PhotoBean;
import config.DBConnection;
import dao.EventDao;
import dao.PhotoDao;
import util.Constants;
import util.ImageUtil;
import util.ResultConstants;

public class PhotosAction extends ActionSupport implements SessionAware {

	SessionMap<String, Object> sessionMap;
	List<PhotoBean> listOfPhotos = new ArrayList<PhotoBean>();
	private File[] imgFile;
	private String[] imgFileContentType;
	private String[] imgFileFileName;
	private int photoId;
	private String header;
	private String from;
	private int eventId;
	private String[] addToSlideshow;
	private String[] removeFromSlideshow;
	private String deletePhotosIdArray;
	private String album = "All";
	private String pageOwnerCode;
	private String ajaxResponseDummyMsg; 
	private String imgPageNo = "0";
	private boolean endReached;
	



	public String openPhotosPage(){
		try(Connection con = DBConnection.getConnection()) {
			if(getFrom().equals("ngo"))
			{
				setHeader("Photos");
			}
			if(getFrom().equals("event"))
			{
				ArrayList<String> selectables = new ArrayList<String>();
				selectables.add(Constants.EVENTBEAN_NAME);
				selectables.add(Constants.EVENTBEAN_CALENDAR);
				EventBean evtBean = EventDao.getEventBean(con, eventId, selectables);
				setHeader("Photos for the event "+evtBean.getName());
			}
			if(getFrom().equals("slideshow"))
			{
				setHeader("Add/Remove photos to slideshow on the profile:");
			}
			if(getFrom().equals("all"))
			{
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}
	public String listPhotosAction() {
		String ngoUid = null;
		ngoUid = getPageOwnerCode();
		int pageNo = Integer.parseInt(imgPageNo);
		int count = 8;
		try(Connection con = DBConnection.getConnection()) {
			if(getFrom().equals("ngo"))
			{
				listOfPhotos = PhotoDao.getListOfPhotoURLs(con, ngoUid, album, getFrom(), pageNo*count, count, true);
			}
			if(getFrom().equals("event"))
			{
				listOfPhotos = PhotoDao.getListOfPhotoURLs(con, ""+eventId ,"all", getFrom(), pageNo*count, count, true);
			}
			if(getFrom().equals("slideshow"))
			{
				listOfPhotos = PhotoDao.getListOfPhotoURLs(con, ngoUid,"slideshow", "ngo", pageNo*count, count, true);
			}
			if(getFrom().equals("all"))
			{
				listOfPhotos = PhotoDao.getListOfPhotoURLs(con, "","all", "all", pageNo*count, count, true);
			}
			setEndReached(listOfPhotos.size() == 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}

	public String uploadPhotosAction()
	{
		String destPath="", owner = "", category ="";
		String ngoUid = (String)sessionMap.get("userCode");
		
		String  pFPath = "";
		if(from.equals("ngo"))
		{
			String filePath = ImageUtil.getDestinationPath("ngo", ngoUid, null);
			destPath = Constants.IMAGES_ROOTPATH+filePath;
			pFPath=Constants.DB_IMAGES_ROOTPATH+filePath;
			owner = "'"+ngoUid+"'";
			category = "ngoOthers";
		}
		if(from.equals("event"))
		{
			String filePath = ImageUtil.getDestinationPath("event", ngoUid, ""+eventId);
			destPath = Constants.IMAGES_ROOTPATH+filePath;
			pFPath = Constants.DB_IMAGES_ROOTPATH+filePath;
			owner = "'"+eventId+"'";
			category = "event";
		}
	    try{
	    	if(null!=imgFile)
			{
				Connection con = DBConnection.getConnection();
		    	for (int i = 0; i < imgFile.length; i++) {
			     	imgFileFileName[i]=imgFileFileName[i].substring(0,imgFileFileName[i].length()-4);
			     	ImageUtil.saveImage(imgFile[i], imgFileFileName[i], destPath, ImageUtil.getExtension(imgFileContentType[i]));
					Statement stmt = con.createStatement();
					stmt.execute("insert into photo_table (p_file_name, p_file_path, p_file_extension, p_category, p_owner_id) "
							+ ""
							+ "values ('"+imgFileFileName[i]+"','"+pFPath+"','"+ImageUtil.getExtension(imgFileContentType[i])+"','"+category+"',"+owner+")");
					stmt.close();
		    	}
		    	con.close();
			}
	      }
	    catch(SQLException e)
	    {
	    	 e.printStackTrace();
	    }
        catch(NullPointerException npe)
	    {
	    	  npe.printStackTrace();
	    }
		return ResultConstants.SUCCESS;
	}
	public String ajaxDeletePhotoAction()
	{
		try(Connection con = DBConnection.getConnection()) {
			String[] arr = deletePhotosIdArray.split(",");
			PhotoDao.deletePhotos(con, arr);
			ajaxResponseDummyMsg = "Successfully deleted!";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}
	public String ajaxAddToSlideshowAction(){
		try(Connection conn = DBConnection.getConnection()) {
			PhotoDao.addToSlideshow(conn, addToSlideshow, (String)sessionMap.get("userCode"));
			ajaxResponseDummyMsg = "Selected images are added to your Profile Slideshow!";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}
	public String ajaxRemoveFromSlideshowAction(){
		try(Connection conn = DBConnection.getConnection()) {
			PhotoDao.removeFromSlideshow(conn, removeFromSlideshow, (String)sessionMap.get("userCode"));
			ajaxResponseDummyMsg = "Selected images are removed from your Profile Slideshow!";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}
	

	public String getPageOwnerCode() {
		return pageOwnerCode;
	}

	public void setPageOwnerCode(String pageOwnerCode) {
		this.pageOwnerCode = pageOwnerCode;
	}
	public static String getDestinationPath(String category, String ngoUid, String eventId){
		String rootpath = Constants.ROOTPATH+"/images/";
		return category.startsWith("ngo")?rootpath+ngoUid+"/":category.startsWith("event")?rootpath+ngoUid+"/"+eventId:rootpath+"volunteers"+"/";
	}
	
	public List<PhotoBean> getListOfPhotos() {
		return listOfPhotos;
	}

	public void setListOfPhotos(List<PhotoBean> listOfPhotos) {
		this.listOfPhotos = listOfPhotos;
	}

	@Override
	public void setSession(Map<String, Object> sessionMap) {
		// TODO Auto-generated method stub
		this.sessionMap = (SessionMap) sessionMap;
	}

	public File[] getImgFile() {
		return imgFile;
	}

	public void setImgFile(File[] imgFile) {
		this.imgFile = imgFile;
	}

	public String[] getImgFileContentType() {
		return imgFileContentType;
	}

	public void setImgFileContentType(String[] imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}

	public String[] getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String[] imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String[] getAddToSlideshow() {
		return addToSlideshow;
	}

	public void setAddToSlideshow(String[] addToSlideshow) {
		this.addToSlideshow = addToSlideshow;
	}

	public String getDeletePhotosIdArray() {
		return deletePhotosIdArray;
	}

	public void setDeletePhotosIdArray(String deletePhotosIdArray) {
		this.deletePhotosIdArray = deletePhotosIdArray;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String[] getRemoveFromSlideshow() {
		return removeFromSlideshow;
	}

	public void setRemoveFromSlideshow(String[] removeFromSlideshow) {
		this.removeFromSlideshow = removeFromSlideshow;
	}
	public String getImgPageNo() {
		return imgPageNo;
	}
	public void setImgPageNo(String imgPageNo) {
		this.imgPageNo = imgPageNo;
	}
	public boolean isEndReached() {
		return endReached;
	}
	public void setEndReached(boolean endReached) {
		this.endReached = endReached;
	}
	public String getAjaxResponseDummyMsg() {
		return ajaxResponseDummyMsg;
	}
	public void setAjaxResponseDummyMsg(String ajaxResponseDummyMsg) {
		this.ajaxResponseDummyMsg = ajaxResponseDummyMsg;
	}
}
