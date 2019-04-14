package data.persist.db;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import data.City;
import data.persist.CityHandler;

public class DBCityHandler implements CityHandler{
	List<City> cityList = new ArrayList<>();
	String DB_URL = "jdbc:mysql://localhost/chechar_travels";
	String USER = "ashwini";
	String PASSWORD = "ashwini";
	Connection conn;
	Statement stmt;
	ResultSet rs;

	private Map<String, Integer > cityToIdMap = new HashMap<>();
	
	private void initializer() throws SQLException{
		conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		stmt = conn.createStatement();
	}
	
	@Override
	public List<City> getAllCities() {
		try {
			initializer();
			rs = stmt.executeQuery("SELECT * FROM CITIES");
			while(rs.next()){
				cityList.add(new City(rs.getString(1), String.valueOf(rs.getInt(2))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cityList;
	}

	@Override
	public List<City> getAllCities(City city) {
		List<City> citiesList = getAllCities(); 
		citiesList = citiesList.stream().filter(c -> {return !((c.cityName).equals(city.cityName));}).collect(Collectors.toList());
		return citiesList;
	}

	private void readAllCityId() throws SQLException{
		initializer();
		rs = stmt.executeQuery("SELECT * FROM CITIES");
		while(rs.next()){
			cityToIdMap.put(rs.getString(1), Integer.valueOf(rs.getString(2)));
		}
	}
	
	@Override
	public int getCityId(City city) {
		try {
			readAllCityId();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Optional<Integer> cityIdOptional = cityToIdMap.entrySet().stream().filter(e -> city.cityName.equals(e.getKey())).map(e -> e.getValue()).findFirst();
		return cityIdOptional.get();
	}
}

class DBCityHandlerTest{
	public static void main(String args[]){
		List<City> cities = new ArrayList<>();
		cities = new DBCityHandler().getAllCities(new City("Mumbai"));
		cities.forEach(e -> System.out.println(e.cityName));
	}
}