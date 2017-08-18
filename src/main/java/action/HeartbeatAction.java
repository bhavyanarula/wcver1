package action;

import java.util.Map;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import util.ResultConstants;

public class HeartbeatAction  implements SessionAware {
	private SessionMap<String, Object> sessionMap;
	private boolean guest = true;
	
	public String beat(){
		getSessionMap();
		setGuest(!(sessionMap.containsKey("userCode")));
		System.out.println("beating"+guest);
		return ResultConstants.SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.sessionMap = (SessionMap<String, Object>)session;
	}

	public SessionMap<String, Object> getSessionMap() {
		return sessionMap;
	}

	public boolean isGuest() {
		return guest;
	}

	public void setGuest(boolean guest) {
		this.guest = guest;
	}

}
