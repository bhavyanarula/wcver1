package interceptors;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.dispatcher.Parameter;
import org.apache.struts2.dispatcher.Parameter.Request;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import beans.NgoBean;
import config.DBConnection;
import dao.NgoDao;

public class OwnerAuthInterceptor  extends AbstractInterceptor {
        @Override
        public String intercept(ActionInvocation invocation) throws Exception {
        		System.out.println("Owner Auth called");
        		final ActionContext context = invocation.getInvocationContext();
                Map<String, Object> sessionMap = invocation.getInvocationContext().getSession();
                Map<String, Parameter> params = (Map<String, Parameter>)context.get(ActionContext.PARAMETERS);
                String role = "";
                String result = "";
                Map<String, Object> parametersCopy = new HashMap<String, Object>();
                parametersCopy.putAll(params);
                if(!sessionMap.isEmpty()){
                	String userCode = (String)sessionMap.get("userCode");
                	if(userCode==null || "".equals(userCode))
                    	return "IllegalAccess";
                    else{
                    	
                    	try(Connection conn = DBConnection.getConnection()) {
                    		if(!((NgoBean)sessionMap.get("pageOwnerBean")).getUid().equalsIgnoreCase(userCode)
                    				|| Boolean.parseBoolean(""+sessionMap.get("isUserModified")))
                    		{
                    			NgoBean sessionBean = NgoDao.getSessionNgoBeanFromId(conn, userCode);
                    			sessionMap.put("pageOwnerBean", sessionBean);
                    			sessionMap.put("isUserModified", false);
                    		}
                    		 parametersCopy.put("pageOwnerCode", userCode);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	result = invocation.invoke();
                    }
                	context.put(ActionContext.PARAMETERS, parametersCopy);
                }
                else
                	return "IllegalAccess";
                return result;
        }
}