package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.impl.MultiPartWriter;

public class MailUtil {
	public static ClientResponse SendSimpleMessage(String toEmail, String toName, String subject, String msg) {
	    Client client = Client.create();
	    client.addFilter(new HTTPBasicAuthFilter("api",
	                "key-dfef560674b4ec55206662779a98bc22"));
	    WebResource webResource =
	        client.resource("https://api.mailgun.net/v3/admin.welfarecommunity.org/messages");
	    MultivaluedMapImpl formData = new MultivaluedMapImpl();
	    formData.add("from", "Welfare Community <postmaster@admin.welfarecommunity.org>");
	    formData.add("to", toName+" <"+toEmail+">");
	    formData.add("subject", subject);
	    formData.add("text", msg);
	    return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
	                                                post(ClientResponse.class, formData);
	}
	public static ClientResponse GetValidate() {
	       Client client = new Client();
	       client.addFilter(new HTTPBasicAuthFilter("api",
	                       "pubkey-5ogiflzbnjrljiky49qxsiozqef5jxp7"));
	       WebResource webResource =
	               client.resource("https://api.mailgun.net/v3" +
	                               "/address/validate");
	       MultivaluedMapImpl queryParams = new MultivaluedMapImpl();
	       queryParams.add("address", "foo@mailgun.net");
	       return webResource.queryParams(queryParams).get(ClientResponse.class);
	}
	public static ClientResponse sendMimeMessage(String toEmail,  String subject, String msg) {
	       Client client = Client.create();
	       ClientConfig cc = new DefaultClientConfig();

	       cc.getClasses().add(MultiPartWriter.class);
	       client = Client.create(cc);
	       client.addFilter(new HTTPBasicAuthFilter("api","key-dfef560674b4ec55206662779a98bc22"));
	       WebResource webResource =
	               client.resource("https://api.mailgun.net/v3/admin.welfarecommunity.org/"+"messages");
	       FormDataMultiPart form = new FormDataMultiPart();
	       form.field("from", "Welfare Community <postmaster@admin.welfarecommunity.org>");
	       form.field("to", toEmail);
	       form.field("subject", subject);
	       form.field("html", msg);
	       return webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, form);
	}
	public static String readMailHTML(String mailCode){
		String html = "";
		
		String fileName = "";
		if(mailCode.equalsIgnoreCase("welcomeNGO"))
			fileName = Constants.HTML_FILE_WELCOME_NGO;
		if(mailCode.equalsIgnoreCase("forgotPassword"))
			fileName = Constants.HTML_FILE_FORGOT_PASSWORD;
		if(mailCode.equalsIgnoreCase("welcomeVolunteer"))
			fileName = Constants.HTML_FILE_WELCOME_VOLUNTEER;
		if(mailCode.equalsIgnoreCase("appAccept"))
			fileName = Constants.HTML_FILE_APPLICATION_ACCEPT;
		if(mailCode.equalsIgnoreCase("appReject"))
			fileName = Constants.HTML_FILE_APPLICATION_REJECT;
		if(mailCode.equalsIgnoreCase("autoMail"))
			fileName = Constants.HTML_FILE_AUTO_MAIL;
		File htmlFile = new File(fileName);
		try(FileInputStream fis = new FileInputStream(htmlFile)){
			int c = 0;
			while((c=fis.read())!=-1){
				html+=(char)c;
			}
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		return html;
	}
	//public static void main(String arg[]){
	//	System.out.println(sendMimeMessage());
	//}
	    
}
