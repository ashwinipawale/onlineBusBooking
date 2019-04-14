package ui.reservation;

import java.time.LocalDate;

import data.TravelOption;
import javafx.beans.property.SimpleStringProperty;

public class TravelOptionModel {
	public SimpleStringProperty source;	
	public SimpleStringProperty destination;
	public SimpleStringProperty date;
	public SimpleStringProperty time;
	public SimpleStringProperty seats;
	public SimpleStringProperty travelOptionId;


	TravelOptionModel(TravelOption option, LocalDate travelDate, String seats){
		this.source = new SimpleStringProperty(option.source.cityName);
		this.destination = new SimpleStringProperty(option.destination.cityName);
		this.date = new SimpleStringProperty(travelDate.toString());
		this.time = new SimpleStringProperty(option.time.toString());
		this.seats = new SimpleStringProperty(seats);
		this.travelOptionId = new SimpleStringProperty(option.travelOptionId);
	}
	
	public void setSource(String source) {
		this.source.set(source);
	}
	
	public String getSource() {
		return source.get();
	}

	public String getDate() {
		return date.get();
	}

	public void setDate(String date) {
		this.date.set(date);;
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
