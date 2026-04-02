package models;

public class Booking {
	
	private String username;
	private String time;
	private String status;
	private int duration;
	private int participants;
	
	
	public String getUsername()
	{ return username;}
	public void setUsername(String username)
	{ this.username= username;}
	
	
	public String getTime()
	{ return time;}
	public void setTime(String time)
	{ this.time= time;}
	
	
	public String getStatus()
	{ return status;}
	public void setStatus(String status)
	{ this.status= status;}
	
	public int getDuration()
	{ return duration;}
	public void setDuration(int duration)
	{ this.duration= duration;}
	
	
	public int getParticipants()
	{ return participants;}
	public void setParticipants(int participants)
	{ this.participants= participants;}
	
	
	public Booking( String username , String time , String status , int duration , int participants)
	{
		this.username= username;
		this.time = time;
		this.status= status;
		this.duration=  duration;
		this.participants=participants;
		
	}
	
	public void confirmBooking()
	{
		this.status="Confirmed";
		
	}
	
	
	public void cancelBooking()
	{
		this.status="canceled";
	}
	
	
	public boolean isFull(int maxParticipants)
	{
		return participants >= maxParticipants;
	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	

}
