package FoodTruckPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class EventsDAO {
	
	private Connect database;
	
	public EventsDAO() {
		database = new Connect();
	}
	
	public boolean insert(Events event){
		String insertQuery = "INSERT INTO FoodTruckTracker.Events (location, dateTime, notes, truckID) VALUES (?,?,?,?)";
		try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(insertQuery)){
			prepState.setString(1, event.getLocation());
			
			Timestamp timestamp = Timestamp.valueOf(event.getDateTime());
			prepState.setTimestamp(2, timestamp);
			
			prepState.setString(3, event.getNotes());
			prepState.setInt(4, event.getTruckID());
			prepState.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ArrayList<Events> getEventsFromTruck(int truckID){
		ArrayList<Events> eventArray = new ArrayList<Events>();
		String query = "SELECT eventID, location, dateTime, notes, truckID FROM FoodTruckTracker.Events WHERE truckID = ?";
		try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(query)){
			prepState.setInt(1, truckID);
			try(ResultSet rs = prepState.executeQuery()){
				while (rs.next()){
					Events event = new Events();
					event.setEventID(rs.getInt("eventID"));
					event.setLocation(rs.getString("location"));
					
					Timestamp timestamp = rs.getTimestamp("dateTime");
					if (timestamp != null)
					    event.setDateTime(timestamp.toLocalDateTime());
					
					event.setNotes(rs.getString("notes"));
					event.setTruckID(rs.getInt("truckID"));
					
					eventArray.add(event);
				}
				Collections.sort(eventArray, Events.eventDateComp);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return eventArray;
	}
	
	public ArrayList<Events> getAllEvents(){
		ArrayList<Events> eventArray = new ArrayList<Events>();
		String query = "SELECT eventID, location, dateTime, notes, truckID FROM FoodTruckTracker.Events";
		try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(query)){
			try(ResultSet rs = prepState.executeQuery()){
				while (rs.next()){
					Events event = new Events();
					event.setEventID(rs.getInt("eventID"));
					event.setLocation(rs.getString("location"));
					
					Timestamp timestamp = rs.getTimestamp("dateTime");
					if (timestamp != null)
					    event.setDateTime(timestamp.toLocalDateTime());
					
					event.setNotes(rs.getString("notes"));
					event.setTruckID(rs.getInt("truckID"));
					
					eventArray.add(event);
				}
				Collections.sort(eventArray, Events.eventDateComp);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return eventArray;
	}
	
	public boolean update(boolean yes, String newInput, int menuID){
    	String update = "";
    	if (yes){
    		update = "UPDATE FoodTruckTracker.Events SET location = ? WHERE eventID = ?";
    	}
    	else{
    		update = "UPDATE FoodTruckTracker.Events SET notes = ? WHERE eventID = ?";
    	}
    	
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(update)){
    		prepState.setString(1, newInput);
    		prepState.setInt(2, menuID);
    		prepState.executeUpdate();
    	}
    	catch (SQLException e){
    		System.out.println(e);
    		return false;
    	}
    	return true;
    }
	
	public boolean update(LocalDateTime dateTime, int menuID){
    	String update = "UPDATE FoodTruckTracker.Events SET dateTime = ? WHERE eventID = ?";
    	try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(update)){
    		
    		Timestamp timestamp = Timestamp.valueOf(dateTime);
			prepState.setTimestamp(1, timestamp);
    		
    		prepState.setInt(2, menuID);
    		prepState.executeUpdate();
    	}
    	catch (SQLException e){
    		System.out.println(e);
    		return false;
    	}
    	return true;
    }
	
	public boolean delete(Events event){
		String deleteQuery = "DELETE FROM FoodTruckTracker.Events WHERE eventID = ?";
		try(Connection connect = database.connectToDB();
    			PreparedStatement prepState = connect.prepareStatement(deleteQuery)){
			prepState.setInt(1, event.getEventID());
			prepState.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
