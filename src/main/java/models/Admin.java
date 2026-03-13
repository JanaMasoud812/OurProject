package models;

public abstract class Admin {
	
	protected String username;
	protected String password;
	
	public Admin (String username, String password) {
		
		this.username = username;
		this.password = password;
	}
	
	
	public abstract String login (String username, String password);
	public abstract void logout();
	public abstract void viewSlots();
	public abstract void addSlots(String time);
	public abstract void removeSlots(String time);
	public abstract void modifySlots(String time, String status);
	public abstract void setUsername(String username);
	public abstract String getUsername();
	public abstract void setPassword(String password);
	public abstract String getPassword();
	
	

}
