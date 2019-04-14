package data.persist;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;

import data.City;
import data.TravelOption;

public interface TravelOptionHandler_Admin {
	
	public boolean addTravelOption(DayOfWeek day, City source, City dest, LocalTime time) throws SQLException;
		
	public boolean removeTravelOption(String travelOptionId) throws SQLException;
	
	public Map<TravelOption, Integer> fetchAllTravelOptions() throws SQLException;
		
	public void closeDBConnection();
}
