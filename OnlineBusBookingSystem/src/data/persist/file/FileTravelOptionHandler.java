package data.persist.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import data.City;
import data.TravelOption;
import data.persist.TravelOptionHandler;

public class FileTravelOptionHandler implements TravelOptionHandler{
	private List<TravelOption> travelOptions;
	private Map<String, Integer> totalSeats;
	
	@Override
	public List<TravelOption> getTravelOptions(City src, City dest, DayOfWeek day) {
		readTravelOptions();
		return travelOptions.stream().
			filter(o->o.day == day && o.source.cityName.equals(src.cityName) && o.destination.cityName.equals(dest.cityName)).
			collect(Collectors.toList());
	}
	
	private void readTravelOptions(){
		if (travelOptions == null){
			travelOptions = new ArrayList<>();
			try(BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\Users\\rajar\\workspace\\OnlineBusBookingSystem\\resources\\travelOptions.txt")))){
				String line; 
				  while ((line = reader.readLine()) != null) {
					  String[] lineSplit = line.split("#");
					  String[] timestr = lineSplit[4].split(":");
					  int hr = Integer.parseInt(timestr[0]);
					  int min = Integer.parseInt(timestr[1]);
					  City source = new City(lineSplit[2]);
					  City destn = new City(lineSplit[3]);
					  travelOptions.add(new TravelOption(lineSplit[0], source, destn, DayOfWeek.valueOf(lineSplit[1]), LocalTime.of(hr, min)));
				  }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

	/*traveloptionId - TravelOption identity
	 * 
	 * @returns total number of seats in the TravelOption
	 * @throws IOException
	 */
	@Override
	public int getTotalSeats(String traveloptionId) {
		if (totalSeats == null){
			totalSeats = new HashMap<>();
			try(BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\Users\\rajar\\workspace\\OnlineBusBookingSystem\\resources\\totalSeats.txt")))){
				String line; 
				  while ((line = reader.readLine()) != null) {
					  String[] lineSplit = line.split("#");
					  totalSeats.put(lineSplit[0], Integer.parseInt(lineSplit[1]));
				  }
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		return totalSeats.containsKey(traveloptionId)? totalSeats.get(traveloptionId) : 0;
	}

	@Override
	public TravelOption getTravelOption(String travelOptionId) {
		return null;
	}
}


class TravelOptionHandlerTest{
	public static void main(String args[]){
		List<TravelOption> list = new ArrayList<>();
		list = new FileTravelOptionHandler().getTravelOptions(new City("Mumbai"), new City("Delhi"), DayOfWeek.TUESDAY);
		System.out.println(list);
	}
}