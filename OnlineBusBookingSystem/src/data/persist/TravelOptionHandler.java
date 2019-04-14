package data.persist;

import java.time.DayOfWeek;
import java.util.List;

import data.City;
import data.TravelOption;

public interface TravelOptionHandler {
	
	public List<TravelOption> getTravelOptions(City src, City dest, DayOfWeek day);
	
	public int getTotalSeats(String traveloptionId);
	
	public TravelOption getTravelOption(String travelOptionId);
	
}
