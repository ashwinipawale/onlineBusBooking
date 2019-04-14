package ui.reservation.traveller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import data.Gender;
import data.Traveller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TravellerDetailsValidator {
	public boolean validatetravellerData(ObservableList<TravellerModel> travellerdata ){
		List<Traveller> travellerList = new ArrayList<>();

		for(TravellerModel traveller : travellerdata){
			String firstName = traveller.firstName.get();
			String lastName = traveller.lastName.get();
			Integer age = new Integer(traveller.age.get());
			Gender gender = Gender.valueOf(traveller.gender.get());
			if(!(firstName==null && lastName==null)){
				firstName = firstName.trim();
				lastName = lastName.trim();
				if(firstName.length()<15 && firstName.matches("^[a-zA-z]+$") && lastName.length()<15 && lastName.matches("^[a-zA-z]+$") 
				&& age>0 && age <= 100 && (gender.equals(Gender.MALE) || gender.equals(Gender.FEMALE))){
					return true;
				} 
			}
		}
		return false;
	}	
}

class TravellerDetailsValidatorTester{
	public static void main(String args[]){
		TravellerDetailsValidator tv = new TravellerDetailsValidator();
		ObservableList<TravellerModel> travellerdata = FXCollections.observableArrayList(new ArrayList<>());
		Traveller t = new Traveller("ashwini", "chechar", 32, Gender.FEMALE);
		TravellerModel tm = new TravellerModel(t );
		travellerdata.add(tm);
		System.out.println(tv.validatetravellerData(travellerdata));
		
	}
}
