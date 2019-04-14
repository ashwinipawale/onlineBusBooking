package data;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class TravelOption implements Serializable{
	public DayOfWeek day;
	public LocalTime time;
	public City source, destination;
	public String travelOptionId;
	
	public TravelOption(String id, City src, City dest, DayOfWeek d, LocalTime t){
		this.source = src;
		this.destination = dest;
		this.day = d;
		this.time = t;
		this.travelOptionId = id;
	}
	
	public TravelOption getInstance(){
		return this;
	}
	
	public String toString(){
		return String.format("Source: %s%nDestination: %s%nDay: %s%nDepartureTime: %s", 
				source, destination, day, time);
	}
}

class TravelOptionTest{
	public static void main(String args[]){
		City src = new City("Mumbai");
		City dest = new City("Delhi");
		DayOfWeek day = DayOfWeek.FRIDAY;
		String id = "to01";

		TravelOption t = new TravelOption(id, src, dest, day, LocalTime.of(10, 30));
		System.out.println(t);
	}
}