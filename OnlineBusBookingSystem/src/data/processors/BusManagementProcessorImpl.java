package data.processors;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import data.City;
import data.TravelOption;
import data.persist.TravelOptionHandler_Admin;
import data.persist.DataFactory;

public class BusManagementProcessorImpl implements BusManagementProcessor{
	TravelOptionHandler_Admin travelOptionHandler_Admin = DataFactory.getravelOptionHandler_Admin();

	/*@Override
	public boolean initializeDBConnection() {
		// TODO Auto-generated method stub
		return false;
	}*/
	
	@Override
	public boolean addTravelOption(DayOfWeek day, City source, City dest, LocalTime time) throws SQLException {
		return travelOptionHandler_Admin.addTravelOption(day, source, dest, time);
	}

	@Override
	public boolean removeTravelOption(String travelOptionId) throws SQLException {
		return travelOptionHandler_Admin.removeTravelOption(travelOptionId);
	}

	@Override
	public Map<TravelOption, Integer> fetchAllTravelOptions() throws SQLException{
		return travelOptionHandler_Admin.fetchAllTravelOptions();
	}	
	
	@Override
	public void closeDBConnection() {
		// TODO Auto-generated method stub	
	}
}
