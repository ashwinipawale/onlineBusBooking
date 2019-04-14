package data;

import java.io.Serializable;

public class City implements Serializable{
	public String cityName;
	public String cityId;
	
	public City(){
	}
	
	public City(String name){
		this.cityName = name;	
	}
	
	public City(String name, String id){
		this.cityName = name;
		this.cityId = id;
	}
	
	public String toString(){
		return this.cityName;
	}
}