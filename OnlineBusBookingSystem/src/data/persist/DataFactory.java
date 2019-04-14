package data.persist;

import data.persist.db.DBCityHandler;
import data.persist.db.DBDayHandler;
import data.persist.db.DBTravelOptionHandler_Admin;
import data.persist.file.FileBookingHandler;
import data.persist.file.FileCityHandler;
import data.persist.file.FileTravelOptionHandler;

public class DataFactory {
	private static CityHandler cityHandler = new DBCityHandler();
	private static BookingHandler bookingHandler = new FileBookingHandler();
	private static TravelOptionHandler travelOptHandler = new FileTravelOptionHandler();
	private static TravelOptionHandler_Admin busManagementHandler = new DBTravelOptionHandler_Admin();
	private static DayHandler dayHandler;
	
	public static CityHandler getCityHandler(){
		return cityHandler;
	}
	
	public static BookingHandler getBookingHandler(){
		return bookingHandler;
	}
	
	public static TravelOptionHandler getTravelOptionHandler(){
		return travelOptHandler = (travelOptHandler == null ? new FileTravelOptionHandler() : travelOptHandler) ;
	}
	
	public static TravelOptionHandler_Admin getravelOptionHandler_Admin(){
		return busManagementHandler;
	}
	
	public static DayHandler getDayHandler(){
		return dayHandler = (dayHandler == null ? new DBDayHandler() : dayHandler) ;
	}
}
