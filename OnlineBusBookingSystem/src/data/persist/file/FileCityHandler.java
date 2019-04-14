package data.persist.file;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import data.City;
import data.persist.CityHandler;


public class FileCityHandler implements CityHandler {
	private String listOfCitiesFilePath = "C:\\Users\\rajar\\workspace\\OnlineBusBookingSystem\\resources\\listOfCities.txt";
	List<City> cityList = new ArrayList<>();
	public List<City> getAllCities() {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(listOfCitiesFilePath)))){
			String line; 
			  while ((line = reader.readLine()) != null) {
				  City city = new City(line);
				  cityList.add(city);
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return cityList;
	}

	@Override
	public List<City> getAllCities(City c) {
		List<City> sourceStationsList = getAllCities();
		sourceStationsList = sourceStationsList.stream().
				filter(city -> {return !(city.cityName.equals(c.cityName));}).collect(Collectors.toList());
		return sourceStationsList;
	}

	@Override
	public int getCityId(City city) {
		// TODO Auto-generated method stub
		return 0;
	}
}

class CityHandlerTest {
	public static void main(String args[]){
		FileCityHandler ch = new FileCityHandler();
		//List<City> list = ch.getAllCities();
		//list.forEach(x -> System.out.println(x));
		
		City city = new City("Mumbai");
		List<City> list = ch.getAllCities(city);
		list.forEach(x -> System.out.println(x));
	}
}
