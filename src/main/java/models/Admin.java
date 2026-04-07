package models;

public abstract class Admin {
	
	protected String username;
	protected String password;
	protected String email;
	protected int id;
	
	public Admin (String username, String password, String email, int id) {
		
		this.username = username;
		this.password = password;
		this.email = email;
		this.id = id;
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
	public abstract boolean isLoggedIn();
	public abstract void setEmail(String email);
	public abstract String getEmail();
    public abstract void setId(int id);
    public abstract int getId();
    public abstract void cancelUserBooking(Booking booking );
    public abstract String modifyUserBooking(Booking booking, String newTime );
}
