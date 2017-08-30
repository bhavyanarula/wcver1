
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 *
 * @author Bhavya
 */
public class DBConnection {
	
	private static DataSource datasource;
	
	static{
		PoolProperties p = new PoolProperties();
		
		
        //p.setUrl("jdbc:mysql://localhost:3306/welfarecommunity");
		p.setUrl("jdbc:mysql://mysql:3306/wcver1");
        
        //p.setUsername("root");
		p.setUsername("userR0L");
        //p.setPassword("root");
		p.setPassword("3phUsMcYiM0N2OFM");
		
		p.setDriverClassName("com.mysql.jdbc.Driver");
        
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
          "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        
        
        datasource = new DataSource();
        datasource.setPoolProperties(p); 
	}
    public static Connection getConnection() throws SQLException
    {
    	Connection con = null;
    	 try {
             con = datasource.getConnection();
    	 }
    	 catch(Exception e){
    		 e.printStackTrace();
    		 System.out.println("error create connection");
    	 }
        
        return con;
    }
}
