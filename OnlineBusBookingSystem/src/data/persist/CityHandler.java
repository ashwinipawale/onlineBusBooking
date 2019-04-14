package data.persist;

import java.util.List;

import data.City;

public interface CityHandler {
	public List<City> getAllCities();
	
	public List<City> getAllCities(City cityName);
	
	public int getCityId(City city);
	//add cities to the citiesList
	default boolean addCity(){
		return false;	
	}
	
	//remove cities from the citiesList
	default City removeCity(){
		return null;
	} 
}
