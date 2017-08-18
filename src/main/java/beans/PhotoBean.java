package beans;

public class PhotoBean {

	private String id;
	private String url;
	private String name;
	private String extension;
	private boolean thumb;
	private String path;
	
	public PhotoBean(){
		
	}
	
	public PhotoBean(String id, String url) {
		super();
		this.id = id;
		this.url = url;
	}
	
	
	public PhotoBean(String id, String url, String name, String extension, boolean thumb, String path) {
		super();
		this.id = id;
		this.url = url;
		this.name = name;
		this.extension = extension;
		this.thumb = thumb;
		this.path = path;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public boolean isThumb() {
		return thumb;
	}
	public void setThumb(boolean thumb) {
		this.thumb = thumb;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
