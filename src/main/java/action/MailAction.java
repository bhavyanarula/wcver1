package action;

import util.AutoMailer;
import util.ResultConstants;

public class MailAction {

	private String password;
	private String city;
	private int limit;
	public String sendPromoMails(){
		if(password.equals("08-Jan-2014"))
			AutoMailer.send(city, limit);
		return ResultConstants.SUCCESS;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
