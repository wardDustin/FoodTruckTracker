package FoodTruckPackage;

import java.util.Calendar;

import org.joda.time.LocalDateTime;

public class Events {

	private String location;
	private LocalDateTime date = new LocalDateTime();
	
	public Events(String location, LocalDateTime date){
		this.location =  location;
		this.date = date;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "\nLocation = " + location + ", Date and Time = " + date;
	}
	
	public static void main(String[] args){
		LocalDateTime dt = new LocalDateTime();
		Calendar calendar = Calendar.getInstance();
		calendar.set(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(), dt.getMinuteOfHour());
		System.out.println(calendar);
		
	}

	
}
