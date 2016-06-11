package FoodTruckPackage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class Events {
	
	private int eventID;
	private String location;
	private LocalDateTime dateTime;
	private String notes;
	private int truckID;
	
	public Events() {}
	public Events(String location, LocalDateTime dateTime, String notes){
		this.location = location;
		this.dateTime = dateTime;
		this.notes = notes;
	}

	public int getEventID() {
		return this.eventID;
	}
	
	public void setEventID(int newEventID) {
		this.eventID = newEventID;
	}
	
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String newLocation) {
		this.location = newLocation;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(LocalDateTime newDateTime) {
		this.dateTime = newDateTime;
	}
	
	public String getNotes() {
		return this.notes;
	}
	
	public void setNotes(String newNotes) {
		this.notes = newNotes;
	}
	
	public int getTruckID() {
		return this.truckID;
	}
	
	public void setTruckID(int truckID) {
		this.truckID = truckID;
	}
	
	public static Comparator<Events> eventDateComp = new Comparator<Events>() {

		public int compare(Events event1, Events event2) {
			LocalDateTime dt1 = event1.getDateTime().truncatedTo(ChronoUnit.DAYS);
			LocalDateTime dt2 = event2.getDateTime().truncatedTo(ChronoUnit.DAYS);
			
			int i = dt1.compareTo(dt2);
			if (i != 0) return i;
			
			
			LocalDateTime time1 = event1.getDateTime().truncatedTo(ChronoUnit.MINUTES);
			LocalDateTime time2 = event2.getDateTime().truncatedTo(ChronoUnit.MINUTES);

		   //ascending order
		   return time1.compareTo(time2);

		   //descending order
		   //return truckName2.compareTo(truckName1);
	    }
	};
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Date: 'MM-dd-yyyy ', Time: 'hh:mm a");
		String formattedDateTime = dateTime.format(formatter);
		return "Location: " + location + ", " + formattedDateTime + ", Notes: " + notes;
	}
}
