package beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import config.DBConnection;

public class CauseBean {
	
 private int causeCode;
 private String causeName;
 private String causeIcon;
 public CauseBean(){}
	public CauseBean(int causeCode, String causeName, String causeIcon) {
		super();
		this.causeCode = causeCode;
		this.causeName = causeName;
		this.causeIcon = causeIcon;
	}
	public CauseBean(int code)
	{
		super();
		Connection con;
		Statement stmt;
		try {
			con = DBConnection.getConnection();
			stmt = con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from cause_table where cause_code_pk = "+code);
			while(rs.next())
			{
				this.causeCode = rs.getInt("cause_code_pk");
				this.causeName = rs.getString("cause_name");
				this.causeIcon = rs.getString("cause_icon");
			}
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}
	public int getCauseCode() {
		return causeCode;
	}
	public void setCauseCode(int fowCode) {
		this.causeCode = fowCode;
	}
	public String getCauseName() {
		return causeName;
	}
	public void setCauseName(String causeName) {
		this.causeName = causeName;
	}
	public String getCauseIcon() {
		return causeIcon;
	}
	public void setCauseIcon(String causeIcon) {
		this.causeIcon = causeIcon;
	}
	
	
}
