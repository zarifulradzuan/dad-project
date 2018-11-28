package model;


public class User{
	private String userID;
	private String userName;
	private String password;
	private String email;
	public User() {}
	public User(String userID, String userName, String password, String email) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.email = email;
	}
	
	public void setUser(User user) {
		this.userID = user.userID;
		this.userName = user.userName;
		this.password = user.password;
		this.email = user.email;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
