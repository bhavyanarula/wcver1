package servlet; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import util.Constants;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
 
@WebServlet(name = "uploads",urlPatterns = {"/uploads/*"})
@MultipartConfig
public class Uploads extends HttpServlet {
 
 
  private static final long serialVersionUID = 2857847752169838915L;
  int BUFFER_LENGTH = 4096;
 
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
    PrintWriter out = response.getWriter();
    for (Part part : request.getParts()) {
        InputStream is = request.getPart(part.getName()).getInputStream();
        String fileName = getFileName(part);
        
        //FileOutputStream os = new FileOutputStream("D:/Workspaces/welfarecommunity/WelfareCommunity/WebContent/images/" + fileName);
        FileOutputStream os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + fileName);
        byte[] bytes = new byte[BUFFER_LENGTH];
        int read = 0;
        while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        is.close();
        os.close();
        out.println(fileName + " was uploaded to " + System.getenv("OPENSHIFT_DATA_DIR"));
    }
  }
 
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
    try {
	    String filePath = request.getRequestURI();
	    filePath = java.net.URLDecoder.decode(filePath, java.nio.charset.StandardCharsets.UTF_8.toString());
	    System.out.println("requested file ====  "+filePath);
	    //filePath = filePath.replace("/uploads/", "/WebContent/");
	    //int indxOfEvents = filePath.indexOf("/WelfareCommunity/event");
		int indxOfEvents = filePath.indexOf("/event/uploads");
	    if(indxOfEvents!=-1){
	    	//indxOfEvents+=17;
	    	filePath = filePath.substring(0, indxOfEvents)+filePath.substring(indxOfEvents+6);
			System.out.println("after event change ====  "+filePath);
	    }
    	
	    //File file = new File("D:/Workspaces/welfarecommunity/" + filePath);
	
	    System.out.println("file to be expected === "+Constants.ROOTPATH  + filePath.replace("/uploads/",""));
	    File file = new File(Constants.ROOTPATH + filePath.replace("/uploads/",""));
	    InputStream input = new FileInputStream(file);
 
	    response.setContentLength((int) file.length());
	    response.setContentType(new MimetypesFileTypeMap().getContentType(file));
 
	    OutputStream output = response.getOutputStream();
	    byte[] bytes = new byte[BUFFER_LENGTH];
	    int read = 0;
	    while ((read = input.read(bytes, 0, BUFFER_LENGTH)) != -1) {
	        output.write(bytes, 0, read);
	        output.flush();
	    }
 
			input.close();
			output.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		response.sendError(response.SC_NOT_FOUND);
	}
  }
 
  private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
          if (cd.trim().startsWith("filename")) {
            return cd.substring(cd.indexOf('=') + 1).trim()
                    .replace("\"", "");
          }
        }
        return null;
      }
}
