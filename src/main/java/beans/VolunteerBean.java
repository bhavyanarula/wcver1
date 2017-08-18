package beans;


public class VolunteerBean {
	
	private String id;
	private String name;
	private String email;
	private Integer age;
	private String contact;
	private String photoUrl;
	private String gender;
	
	public VolunteerBean(){}
	
	public VolunteerBean(String id, String name, String email, String contact, Integer age,  String gender, String photoUrl) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.age = age;
		this.contact = contact;
		this.setGender(gender);
		this.photoUrl = photoUrl;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
