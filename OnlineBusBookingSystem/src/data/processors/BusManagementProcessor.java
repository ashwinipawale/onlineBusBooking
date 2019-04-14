package data.processors;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import data.City;
import data.TravelOption;

public interface BusManagementProcessor {
	//public boolean initializeDBConnection();
	
	public boolean addTravelOption(DayOfWeek day, City source, City dest, LocalTime time) throws SQLException;
	
	public Map<TravelOption, Integer> fetchAllTravelOptions() throws SQLException;
	
	public boolean removeTravelOption(String travelOptionId) throws SQLException;
	
	public void closeDBConnection();

}
