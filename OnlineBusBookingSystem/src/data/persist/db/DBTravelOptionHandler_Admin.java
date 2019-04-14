package data.persist.db;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import data.City;
import data.TravelOption;
import data.persist.CityHandler;
import data.persist.DataFactory;
import data.persist.DayHandler;
import data.persist.TravelOptionHandler_Admin;

public class DBTravelOptionHandler_Admin implements TravelOptionHandler_Admin{
	String DB_URL = "jdbc:mysql://localhost/chechar_travels";
	String USER = "ashwini";
	String PASSWORD = "ashwini";
	Connection conn;
	Statement stmt;
	ResultSet rs;

	private List<TravelOption> travelOptions;
	private Map<TravelOption, Integer> totalSeatsMap;
	CityHandler cityHandler = DataFactory.getCityHandler();
	DayHandler dayHandler = DataFactory.getDayHandler();
	
	private void initializer() throws SQLException{
		conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		stmt = conn.createStatement();
	}
	
	@Override
	public boolean addTravelOption(DayOfWeek day, City src, City dest, LocalTime time) throws SQLException {
		initializer();
		int sourceId = 0;
		int destId = 0;	
		int dayId = 0;	
		
		sourceId = Integer.valueOf(cityHandler.getCityId(src));
		destId = Integer.valueOf(cityHandler.getCityId(dest));
		dayId = dayHandler.getDayId(day);
			
	    stmt.executeUpdate("INSERT INTO TRAVELOPTIONS (TravelDay, Source, Destination, DepartTime) VALUES ('"+dayId+"', '"+sourceId+"', '"+destId+"', '"+time+"')");
		return false;
	} 

	@Override
	public Map<TravelOption, Integer> fetchAllTravelOptions() throws SQLException {
		readTravelOptions();	
		return totalSeatsMap;
	}
	
	private void readTravelOptions() throws SQLException{
		initializer();
		rs = stmt.executeQuery("SELECT O.ID, D.TRAVEL_DAY, C1.CITYNAME, C2.CITYNAME, O.DEPARTTIME, O.SEATS FROM TRAVELOPTIONS O"
								+ " JOIN DAYS D"
								+ " JOIN CITIES C1"
								+ " JOIN CITIES C2"
								+ " ON O.TRAVELDAY=D.DAY_ID AND O.SOURCE=C1.ID AND O.DESTINATION=C2.ID");
			travelOptions = new ArrayList<>();
			totalSeatsMap = new HashMap<>();
			while(rs.next()){
				String id = String.valueOf(rs.getInt(1));
				DayOfWeek day = DayOfWeek.valueOf(rs.getString(2));
				City source = new City(rs.getString(3));
				City dest = new City(rs.getString(4));
				String[] splitTime = rs.getString(5).split(":");
				int hour = Integer.parseInt(splitTime[0]);
				int minute = Integer.parseInt(splitTime[1]);
				LocalTime time = LocalTime.of(hour, minute);
				int seats = rs.getInt(6);
				travelOptions.add(new TravelOption(id,source, dest, day, time));
				totalSeatsMap = travelOptions.stream().collect(Collectors.toMap(k -> k, v -> seats));
			}	
	}
	
	@Override
	public boolean removeTravelOption(String travelOptionId) throws SQLException {
		stmt.executeUpdate("DELETE FROM TRAVELOPTIONS WHERE ID="+ travelOptionId +" ");
		return true;
	}

	@Override
	public void closeDBConnection() {
		// TODO Auto-generated method stub
		
	}
}

class DBBusManagementHandlerTest{
	public static void main(String args[]) throws SQLException{
		DBTravelOptionHandler_Admin dBTravelOptionHandler = new DBTravelOptionHandler_Admin();
		//busManager.addTravelOption(DayOfWeek.SUNDAY, new City("Delhi"), new City("Hyderabad"), LocalTime.of(9, 30));
		Set<Entry<TravelOption, Integer>> entryset = dBTravelOptionHandler.fetchAllTravelOptions().entrySet();
		for(Entry<TravelOption, Integer> entry : entryset){
			TravelOption travelOption = entry.getKey();
			int seats = entry.getValue();
			System.out.println(travelOption.travelOptionId + "  " + travelOption.day + "  " + travelOption.source
					+ "  " + travelOption.destination + "  " + travelOption.time + "  " + seats);
		}
		
		dBTravelOptionHandler.removeTravelOption(String.valueOf(1));
	}
}
