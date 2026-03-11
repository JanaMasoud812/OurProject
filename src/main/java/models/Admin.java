package models;

public abstract class Admin {
	
	private String username;
	private String password;
	
	public Admin (String username, String password) {
		
		this.username = username;
		this.password = password;
	}
	
	
	public abstract boolean login (String username, String password);
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
