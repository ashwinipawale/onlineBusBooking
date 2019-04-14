package data;

import java.io.Serializable;

public class Traveller  implements Serializable{
	/**
	 * 
	 */
	public String firstName;
	public String lastName;
	public int age;
	public Gender gender;
	
	public Traveller(String fName, String lName, Integer age, Gender g){
		this.firstName = fName;
		this.lastName = lName;
		this.age = age;
		this.gender = g;
	}
	
	public String toString(){
		return String.format("Name: %s%nAge: %s%nGender: %s", (firstName+lastName), age, gender );
	}
}
