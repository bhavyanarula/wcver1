package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DBConnection;

public class AutoMailer {

	private static String ngoUid;
	private static int sno;
	private static String email; 
	
	public static void send(String city, int limit){
		File logFile = new File(Constants.ROOTPATH+"/logs/email-logs.csv");
		email = MailUtil.readMailHTML("autoMail");
		try(FileOutputStream fosLogs = new FileOutputStream(logFile, true)){
		try(Connection conn = DBConnection.getConnection()){
				
				System.out.println("Starting to send emails to NGOs of "+city+" city. \n Fetching NGO Details from DB....");
			PreparedStatement stmt = conn.prepareStatement("select ngo_name, ngo_alias, ngo_email, ngo_uid, ngo_type, amt.add_city from ngos_table nt "
					+ "join ngo_address_table nat on nat.add_ngo_code_fk = nt.ngo_uid "
						+ "join address_master_table amt on amt.add_code_pk = nat.add_master_code_fk where ngo_type='auto' and amt.add_city=? limit 0, "+limit);
				stmt.setString(1, city);
			ResultSet rs = stmt.executeQuery();
				System.out.println("Results fetched from DB....");
			/*
			 * %#ngoName#%
			 * %#ngoAlias#%
			 * %#ngoUid#%
			 * 
			 */
			while(rs.next()){
					sno++;
					String name = rs.getString("ngo_name");
					String emailId = rs.getString("ngo_email");
					ngoUid = rs.getString("ngo_uid");
					String email = "";
					email = AutoMailer.email;
					email = email.replaceAll("%#ngoName#%", name);
				email = email.replaceAll("%#ngoAlias#%", rs.getString("ngo_alias"));
					email = email.replaceAll("%#ngoUid#%", ngoUid);
					System.out.println("Email designed, sending mail to "+name+" @ "+emailId);
					MailUtil.sendMimeMessage(emailId, "A Network for Socially Responsible People", email);
					System.out.println("Email sent successfully...");
					System.out.println("******************************************************\n\n");
					fosLogs.write((sno+","+ngoUid+","+emailId+","+name+","+city+",1,-\n").getBytes());
				//MailUtil.sendMimeMessage(rs.getString("ngo_email"), "A Network for Socially Responsible People", email);
			}
			
		}
		catch(SQLException sqle){
			sqle.printStackTrace();
				fosLogs.write((sno+","+ngoUid+",-,-,"+city+",0,"+sqle.getMessage()+"\n").getBytes());
			
		}
			catch (Exception e) {
	        	 e.printStackTrace();
	        	 fosLogs.write((sno+","+ngoUid+",-,-,"+city+",0,"+e.getMessage()+"\n").getBytes());
	         }
         } catch (IOException e) {
        	 e.printStackTrace();
         }
	}
	
	public static void main(String args[]){
		//send();
	}
}
