package action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.EventBean;
import beans.NgoBean;
import beans.VolunteerBean;
import config.DBConnection;
import dao.EventDao;
import dao.NgoDao;
import dao.VerificationDao;
import dao.VolunteerDao;
import security.SecurityUtil;
import util.Constants;
import util.MailUtil;
import util.ResultConstants;

public class ApplicationAction {
	private EventBean eventBean;
	private int eventId;
	private String pageOwnerCode;
	private List<VolunteerBean> volAppList = new ArrayList<VolunteerBean>();
	private String vIdArr;
	private String appType = Constants.APPLICATION_STATUS_WAITING;
	private Integer start = new Integer("0");
	private boolean hasNext;
	
	public String showApplications(){
		
		try(Connection conn = DBConnection.getConnection();) {
			ArrayList<String> selectables = new ArrayList<String>();
			selectables.add(Constants.EVENTBEAN_NAME);
			selectables.add(Constants.EVENTBEAN_ORGANIZER);
			eventBean = EventDao.getEventBean(conn, eventId, selectables);
			volAppList = EventDao.getApplicants(conn, eventId, appType, start, 5);
			pageOwnerCode = eventBean.getOrganizer();
			//eventId = eventId;
			setHasNext(!(volAppList.size()<5));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}
	public String accept(){
		try(Connection conn = DBConnection.getConnection();) {
			EventDao.setApplicantStatus(conn, vIdArr, eventId, Constants.APPLICATION_STATUS_ACCEPTED);
			ArrayList<String> selectables = new ArrayList<String>();
			selectables.add(Constants.EVENTBEAN_NAME);
			selectables.add(Constants.EVENTBEAN_ORGANIZER);
			eventBean = EventDao.getEventBean(conn, eventId, selectables);
			String vId[] = vIdArr.split(",");
			for(String v : vId){
				VolunteerBean vb = VolunteerDao.getVolunteerBean(conn, v);
				String strEmail = MailUtil.readMailHTML("appAccept");
				strEmail = strEmail.replaceAll("%#volunteerName#%",vb.getName());
				strEmail = strEmail.replaceAll("%#eventName#%", eventBean.getName());
				selectables = new ArrayList<String>();
				selectables.add(Constants.NGOBEAN_NAME);
				selectables.add(Constants.NGOBEAN_PHONE);
				selectables.add(Constants.NGOBEAN_ALIAS);
				selectables.add(Constants.NGOBEAN_EMAIL);
				NgoBean ngoBean = NgoDao.getNgoBeanFromId(conn, eventBean.getOrganizer(), selectables);
				strEmail = strEmail.replaceAll("%#ngoEmail#%", ngoBean.getNgoEmail());
				strEmail = strEmail.replaceAll("%#ngoPhone#%", ngoBean.getNgoPhone());
				strEmail = strEmail.replaceAll("%#ngoName#%", 
						"<a href=\"https://welfarecommunity.org/"+ngoBean.getAlias()+"\" title=\""+ngoBean.getNgoName()+"\">"+ngoBean.getNgoName()+"</a>");
				MailUtil.sendMimeMessage(vb.getEmail(), "Volunteer Application at Welfare Community", strEmail);
				setPageOwnerCode(pageOwnerCode);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}
	public String reject(){
		try(Connection conn = DBConnection.getConnection();) {
			EventDao.setApplicantStatus(conn, vIdArr, eventId, Constants.APPLICATION_STATUS_REJECTED);
			ArrayList<String> selectables = new ArrayList<String>();
			selectables.add(Constants.EVENTBEAN_NAME);
			selectables.add(Constants.EVENTBEAN_ORGANIZER);
			eventBean = EventDao.getEventBean(conn, eventId, selectables);
			String vId[] = vIdArr.split(",");
			for(String v : vId){
				VolunteerBean vb = VolunteerDao.getVolunteerBean(conn, v);
				String strEmail = MailUtil.readMailHTML("appReject");
				strEmail = strEmail.replaceAll("%#volunteerName#%",vb.getName());
				strEmail = strEmail.replaceAll("%#eventName#%", eventBean.getName());
				selectables = new ArrayList<String>();
				selectables.add(Constants.NGOBEAN_NAME);
				selectables.add(Constants.NGOBEAN_PHONE);
				selectables.add(Constants.NGOBEAN_ALIAS);
				selectables.add(Constants.NGOBEAN_EMAIL);
				NgoBean ngoBean = NgoDao.getNgoBeanFromId(conn, eventBean.getOrganizer(), selectables);
				strEmail = strEmail.replaceAll("%#ngoEmail#%", ngoBean.getNgoEmail());
				strEmail = strEmail.replaceAll("%#ngoPhone#%", ngoBean.getNgoPhone());
				strEmail = strEmail.replaceAll("%#ngoName#%", 
						"<a href=\"https://welfarecommunity.org/"+ngoBean.getAlias()+"\" title=\""+ngoBean.getNgoName()+"\">"+ngoBean.getNgoName()+"</a>");
				MailUtil.sendMimeMessage(vb.getEmail(), "Volunteer Application at Welfare Community", strEmail);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
		
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getPageOwnerCode() {
		return pageOwnerCode;
	}

	public void setPageOwnerCode(String pageOwnerCode) {
		this.pageOwnerCode = pageOwnerCode;
	}

	public List<VolunteerBean> getVolAppList() {
		return volAppList;
	}

	public void setVolAppList(List<VolunteerBean> volAppList) {
		this.volAppList = volAppList;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public String getVIdArr() {
		return vIdArr;
	}
	public void setVIdArr(String VIdArr) {
		this.vIdArr = VIdArr;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public EventBean getEventBean() {
		return eventBean;
	}
	public void setEventBean(EventBean eventBean) {
		this.eventBean = eventBean;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
}
