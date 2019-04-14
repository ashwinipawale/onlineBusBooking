package data.persist.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import data.City;
import data.persist.DistanceHandler;
import ui.reservation.TravelOptionModel;

public class FileDistanceHandler implements DistanceHandler{

	String FILE = "C:\\Users\\rajar\\workspace\\OnlineBusBookingSystem\\resources\\distanceBetweenCities.txt";
	
	public void initialize(){
		
	}
	
	public double getDistance(String sourceCity, String destCity) {
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(FILE)))){
			String line; 
			  while ((line = reader.readLine()) != null) {
				  String[] lineSplit = line.split("#");
				  if(lineSplit[0].equalsIgnoreCase(sourceCity) && lineSplit[1].equalsIgnoreCase(destCity)){
					  return Double.parseDouble(lineSplit[2]);
				  }
			  }
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return 0.0;
	}
}

class FileDistanceHandlerTest{
	public static void main(String args[]){
		System.out.println(new FileDistanceHandler().getDistance("Mumbai", "Delhi"));
	}
}