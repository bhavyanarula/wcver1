package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

public class ImageUtil {
	static {
        ImageIO.scanForPlugins();
	}

	public static boolean saveImage(File sourceFile, String name, String destPath, String ext){
		File destFile = new File(destPath);
		destFile.mkdirs();
		destFile = new File(destPath +"/"+ name + ext);
		try {
			BufferedImage img = ImageIO.read(sourceFile);
			BufferedImage compBI = Scalr.resize(img, 1200);
			File compFile = new File(destPath+"/"+ name + ext);
			ImageIO.write(compBI, ext.substring(1), compFile);
			BufferedImage thumbnail = Scalr.resize(img, 300);
			File thumbFile = new File(destPath+"/"+ name +"_thumb"+ ext);
			ImageIO.write(thumbnail, ext.substring(1), thumbFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean saveOrReplaceImage(File sourceFile, File destFile, String name, String destPath, String ext){
		
		if(destFile.exists())
		{
			try {
				FileUtils.forceDelete(destFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		saveImage(sourceFile, name, destPath, ext);
		return true;
	}
	public static String getDestinationPath(String category, String id, String secId){
		if(category.startsWith("ngo"))
			return "/"+id+"/";
		if(category.startsWith("event"))
			return "/"+id+"/events/"+secId;
		if(category.startsWith("volunteer"))
			return "/volunteers/";
		if(category.startsWith("aboutUs"))
			return "/"+id+"/aboutUs/";
		else
			return "/"+id;
		
	}
	
	public static String getExtension(String imgFileContentType){
		String extension = "";
		if (imgFileContentType.equals("image/bmp"))
			extension = ".bmp";
		if (imgFileContentType.equals("image/gif"))
			extension = ".gif";
		if (imgFileContentType.equals("image/jpeg"))
			extension = ".jpg";
		if (imgFileContentType.equals("image/png"))
			extension = ".png";
		return extension;
	}
	
	public static boolean deleteImageFile(String phyPath){
		return new File(phyPath).delete();
	}
}
