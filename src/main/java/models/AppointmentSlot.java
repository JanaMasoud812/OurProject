package models;

import java.util.List;

public  abstract class AppointmentSlot {
	
	protected String date;
	protected String time;
	protected boolean isAvailable;
	
	public AppointmentSlot(String date, String time , boolean isAvailable) {
		this.date=date;
		this.time=time;
		this.isAvailable= isAvailable;
	}
	
	public abstract String getDate();
	public abstract void setDate(String date);
	public abstract boolean getAvailable();
	public abstract void setAvailable(boolean isAvailable) ;
	public abstract void  setTime(String time) ;
	public abstract String getTime();
	public abstract List<AppointmentSlot> viewAvailableSlots(List<AppointmentSlot> slots);	
	

}
