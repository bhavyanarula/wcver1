package action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.AddressBean;
import beans.NgoBean;
import config.DBConnection;
import dao.NgoDao;
import util.Constants;
import util.ResultConstants;

public class ContactUsAction {
	private List<AddressBean> ngoAddressBeanList = new ArrayList<AddressBean>();
	private String pageOwnerCode;

	public List<AddressBean> getNgoAddressBeanList() {
		return ngoAddressBeanList;
	}

	public void setNgoAddressBeanList(List<AddressBean> addressBeanList) {
		this.ngoAddressBeanList = addressBeanList;
	}
	public String openContactUs(){
		
		try(Connection conn = DBConnection.getConnection()) {
			setNgoAddressBeanList(NgoDao.getAddressListOfNgo(conn, getPageOwnerCode()));
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
}
