package action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import beans.AddressBean;
import beans.CauseBean;
import beans.NgoBean;
import config.DBConnection;
import dao.AddressMasterDao;
import dao.CauseDao;
import dao.NgoDao;
import util.Constants;
import util.ResultConstants;

public class SearchAction extends ActionSupport{
	
	private NgoBean ngoBean;
	private String searchType;
	private String bsQuery;
	private String asQuery;
	private String searchAction;
	private List<NgoBean> searchResults = new ArrayList<NgoBean>();
	private Integer start;
	private boolean hasNext;
	
	private HashMap<Integer, String> selectCauseList;
	
	private String selectedState;
	private String selectedCity;
	private String profileType;
	private Map<String, String> stateList = new LinkedHashMap<String, String>();
	private Map<String, String> cityList = new LinkedHashMap<String, String>();
	
	private String sName;
	private String sCauseList;
	private String sState;
	private String sCity;
	

	public String ajaxStateChangeSearchAction() {
		cityList.clear();
		cityList.put("All","All");
		try(Connection conn = DBConnection.getConnection()) {
			cityList.putAll(AddressMasterDao.getListOfCities(conn, selectedState));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultConstants.SUCCESS;
	}

	public String advanceSearch() throws UnsupportedEncodingException
	{
		try(Connection conn = DBConnection.getConnection()) {
			System.out.println("search query start at"+new Date(System.currentTimeMillis()));
			setStart(start);
			setSelectCauseList(CauseDao.getCauseMasterList(conn));
			selectedState = (selectedState == null || "".equals(selectedState)) ? "All"
					: selectedState;
			stateList.put("All","All");
			stateList.putAll(AddressMasterDao.getListOfStates(conn));
			cityList.put("All","All");
			if(!"All".equals(sState))
				cityList.putAll(AddressMasterDao.getListOfCities(conn, sState));
			setSearchAction(searchAction);
			
			if(sCauseList==null)
				sCauseList="";
			if(sState==null)
				sState="All";
			if(sCity==null)
				sCity="All";
			if(profileType==null)
				profileType="auto";
			
			if(searchAction.equals("as")){
				return "noresults";
			}
			List<String> resultList = new ArrayList<String>();
			if(!searchAction.equals("sbc1") && !searchAction.equals("sbn")){
				asQuery = "sCauseList="+sCauseList+"&searchAction="
						+searchAction+"&sState="+sState+"&sCity="+sCity+"&profileType="+profileType+"&start=";
			} else {
				setBsQuery(bsQuery);
				asQuery = "searchAction="+searchAction+"&bsQuery="+bsQuery+"&start=";
			}
			if(searchAction.equals("sbn")){
				if(bsQuery.equals("*"))
				{
					resultList = NgoDao.listAllUserNgos(conn, start*5, 5);
					profileType = "user";
				}
				else
					resultList = NgoDao.searchByName(conn, bsQuery, profileType.equalsIgnoreCase("user"), start*5, 5);
			}
			if(searchAction.equals("sbc1")){
				resultList = NgoDao.searchByCause(conn, bsQuery, profileType, start*5, 5);
			}
			if(searchAction.equals("sbc2")){
				resultList = NgoDao.searchByCause(conn, sCauseList, profileType, start*5, 5);
			}
			if(searchAction.equals("sbcl")){
				if(!sState.equals("All") && (sCity.equals("All") || sCity.equals("")))
					resultList = NgoDao.searchByCauseAndLocation(conn, sCauseList, sState, "state", profileType, start*5, 5); 
				if(!sState.equals("All") && !sCity.equals("All") && !sCity.equals(""))
					resultList = NgoDao.searchByCauseAndLocation(conn, sCauseList, sCity, "city", profileType, start*5, 5); 
			}
			if(searchAction.equals("sbl")){
				if(!sState.equals("All")  && (sCity.equals("All") || sCity.equals("")))
					resultList = NgoDao.searchByLocation(conn, "state", sState, profileType, start*5, 5); 
				
				if(!sState.equals("All")  && !sCity.equals("All") && !sCity.equals(""))
					resultList = NgoDao.searchByLocation(conn, "city", sCity, profileType, start*5, 5);
			}
			
			System.out.println("search query end at"+new Date(System.currentTimeMillis()));
			if(resultList.size()>0)
			{
				ArrayList<String> selectables = new ArrayList<String>();
				selectables.add(Constants.NGOBEAN_NAME);
				selectables.add(Constants.NGOBEAN_ALIAS);
				selectables.add(Constants.NGOBEAN_LOGO_P_ID);
				selectables.add(Constants.NGOBEAN_NO_OF_APPRECIATIONS);
				selectables.add(Constants.NGOBEAN_TYPE);
				selectables.add(Constants.NGOBEAN_ADDRESS_LIST);
				selectables.add(Constants.NGOBEAN_CAUSE_LIST);
				String array[] = new String[resultList.size()];
				for(int i = 0 ; i < array.length ; i++){
					array[i] = resultList.get(i);
				}
				searchResults = NgoDao.getNgosFromIds(conn, array, selectables);
				setHasNext(true);
				if(searchResults.size()<5)
					setHasNext(false);
				else
					setHasNext(true);
				
			}
			else
			{
				addActionError("No such result found");
				return "noresults";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException npe){
		}
		return ResultConstants.SUCCESS;
	}
	
	
	public NgoBean getNgoBean() {
		return ngoBean;
	}
	public void setNgoBean(NgoBean ngoBean) {
		this.ngoBean = ngoBean;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getBsQuery() {
		return bsQuery;
	}
	public void setBsQuery(String searchQuery) {
		this.bsQuery = searchQuery;
	}
	public Integer getNoOfPages() {
		return noOfPages;
	}
	public void setNoOfPages(Integer noOfPages) {
		this.noOfPages = noOfPages;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	private Integer noOfPages = null;
	
	public List<NgoBean> getSearchResults() {
		return searchResults;
	}
	public void setSearchResults(List<NgoBean> searchResults) {
		this.searchResults = searchResults;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public HashMap<Integer, String> getSelectCauseList() {
		return selectCauseList;
	}
	public void setSelectCauseList(HashMap<Integer, String> selectCauseList) {
		this.selectCauseList = selectCauseList;
	}
	public String getSelectedState() {
		return selectedState;
	}
	public void setSelectedState(String selectedState) {
		this.selectedState = selectedState;
	}
	public String getSelectedCity() {
		return selectedCity;
	}
	public void setSelectedCity(String selectedCity) {
		this.selectedCity = selectedCity;
	}
	public Map<String, String> getStateList() {
		return stateList;
	}
	public void setStateList(Map<String, String> stateList) {
		this.stateList = stateList;
	}
	public Map<String, String> getCityList() {
		return cityList;
	}
	public void setCityList(Map<String, String> cityList) {
		this.cityList = cityList;
	}
	public String getSName() {
		return sName;
	}
	public void setSName(String sName) {
		this.sName = sName;
	}
	public String getSCauseList() {
		return sCauseList;
	}
	public void setSCauseList(String sCauseList) {
		this.sCauseList = sCauseList;
	}
	public String getSState() {
		return sState;
	}
	public void setSState(String sState) {
		this.sState = sState;
	}
	public String getSCity() {
		return sCity;
	}
	public void setSCity(String sCity) {
		this.sCity = sCity;
	}
	public String getSearchAction() {
		return searchAction;
	}
	public void setSearchAction(String searchAction) {
		this.searchAction = searchAction;
	}
	public String getProfileType() {
		return profileType;
	}
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}
	public String getAsQuery() {
		return asQuery;
	}
	public void setAsQuery(String asQuery) {
		this.asQuery = asQuery;
	}

}
