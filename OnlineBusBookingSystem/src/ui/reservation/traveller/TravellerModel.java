package ui.reservation.traveller;

import data.Gender;
import data.Traveller;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ComboBox;

public class TravellerModel {
	SimpleStringProperty firstName;
	SimpleStringProperty lastName;
	SimpleStringProperty age;
	SimpleStringProperty gender;
	
	public TravellerModel(Traveller traveller){
		this.firstName = new SimpleStringProperty(traveller.firstName);
		this.lastName = new SimpleStringProperty(traveller.lastName);
		this.age = new SimpleStringProperty(new Integer(traveller.age).toString());
		this.gender = new SimpleStringProperty(traveller.gender.name());
	}

	public String getFirstName() {
		return firstName.get()==null ? " " : firstName.get() ;
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);;
	}

	public String getLastName() {
		return lastName.get()==null ? " " : lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);;
	}

	public String getAge() {
		return (age.get() == null) ? " " : age.get();
	}

	public void setAge(String age) {
		if(!age.equals(new Integer(0))){
			this.age.set(age);
		}else this.age.set(new Integer(0).toString());
	}

	public String getGender() {
		return gender.get();
	}

	public void setGender(String gender) {
		this.gender.set(gender); 
	}
}

