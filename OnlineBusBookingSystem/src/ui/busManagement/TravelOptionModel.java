package ui.busManagement;

import data.TravelOption;
import javafx.beans.property.SimpleStringProperty;

public class TravelOptionModel {
	public SimpleStringProperty source;	
	public SimpleStringProperty destination;
	public SimpleStringProperty day;
	public SimpleStringProperty time;
	public SimpleStringProperty seats;
	public SimpleStringProperty travelOptionId;
	
	TravelOptionModel(TravelOption travelOption, String totalSeats){
		this.source = new SimpleStringProperty(travelOption.source.cityName);
		this.destination = new SimpleStringProperty(travelOption.destination.cityName);
		this.day = new SimpleStringProperty(travelOption.day.toString());
		this.time = new SimpleStringProperty(travelOption.time.toString());
		this.seats = new SimpleStringProperty(totalSeats);
		this.travelOptionId = new SimpleStringProperty(travelOption.travelOptionId);
	}
	
	public void setSource(String source) {
		this.source.set(source);
	}
	
	public String getSource() {
		return source.get();
	}

	public String getDay() {
		return day.get();
	}

	public void setDay(String day) {
		this.day.set(day);;
	}

	public String getTime() {
		return time.get();
	}

	public void setTime(String time) {
		this.time.set(time);;
	}

	public String getSeats() {
		return seats.get();
	}

	public void setSeats(String seats) {
		this.seats.get();
	}

	public String getDestination() {
		return destination.get();
	}

	public void setDestination(String destination) {
		this.destination.set(destination);
	}
	
	public String getTravelOptionId() {
		return travelOptionId.get();
	}

	public void setTravelOptionId(String travelOptionId) {
		this.travelOptionId.get();
	}
}
