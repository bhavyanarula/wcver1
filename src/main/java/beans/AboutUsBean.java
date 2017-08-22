package beans;

import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
public class AboutUsBean implements Comparable<AboutUsBean>{

	private int code;
	private String ngoUid;
	private String heading;
	private String content;
	private String photo;
	private Calendar createdOn = new GregorianCalendar();
	private boolean pinned = false;
	public AboutUsBean(int code, String ngoUid, String heading, String content, String photo, Calendar createdOn, boolean isPinned) {
		super();
		this.code = code;
		this.ngoUid = ngoUid;
		this.heading = heading;
		this.content = content;
		this.createdOn = createdOn;
		this.setPinned(isPinned);
		this.setPhoto(photo); 
	}
	public AboutUsBean() {
		// TODO Auto-generated constructor stub
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getNgoUid() {
		return ngoUid;
	}
	public void setNgoUid(String ngoUid) {
		this.ngoUid = ngoUid;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Calendar getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}
	public boolean isPinned() {
		return pinned;
	}
	public void setPinned(boolean isPinned) {
		this.pinned = isPinned;
	}
	public static final Comparator<AboutUsBean> DEFAULT_SORT = new Comparator<AboutUsBean>(){
        @Override
        public int compare(AboutUsBean o1, AboutUsBean o2) {
        	if(o1.isPinned() && o2.createdOn.before(System.currentTimeMillis()))
        		return 1;
        	if(o1.isPinned() && !o2.createdOn.before(System.currentTimeMillis()))
        		return 0;
        	if(o1.createdOn.after(o2.createdOn))
        		return 1;
        	return 0;
        }
    };
	@Override
	public int compareTo(AboutUsBean o) {
		return 0;
	}
}
