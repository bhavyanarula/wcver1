package interceptors;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class VisitorAuthInterceptor  extends AbstractInterceptor {
	private String pageOwnerCode;
        @Override
        public String intercept(ActionInvocation invocation) throws Exception {
        		System.out.println("Visitor Auth called");
                Map<String, Object> sessionMap = invocation.getInvocationContext().getSession();
                Map<String, Object> params = invocation.getInvocationContext().getParameters();
                String userCode = (String)sessionMap.get("userCode");
                String pageOwnerCode = "";
                String result = "";
                if(params.get("pageOwnerCode")!=null)
                	pageOwnerCode = ((String[])params.get("pageOwnerCode"))[0];
                if(pageOwnerCode.equals(userCode) || userCode==null || "".equals(userCode) || pageOwnerCode==null || "".equals(pageOwnerCode))
                	return "IllegalAccess";
                else{
                	sessionMap.put("guest", false);
					sessionMap.put("owner", false);
					sessionMap.put("visitor", true);
                	result = invocation.invoke();
                }
                return result;
        }
		public String getPageOwnerCode() {
			return pageOwnerCode;
		}
		public void setPageOwnerCode(String pageOwnerCode) {
			this.pageOwnerCode = pageOwnerCode;
		}
}