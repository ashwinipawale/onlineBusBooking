package data.persist.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import data.persist.DayHandler;

public class DBDayHandler implements DayHandler{
	String DB_URL = "jdbc:mysql://localhost/chechar_travels";
	String USER = "ashwini";
	String PASSWORD = "ashwini";
	Connection conn;
	Statement stmt;
	ResultSet rs;

	private Map<String, Integer> dayToIdMap = new HashMap<>();
	
	private void initializer() throws SQLException{
		conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		stmt = conn.createStatement();
	}
	
	private void readAllDayId()  throws SQLException {
		initializer();
		rs = stmt.executeQuery("SELECT travel_day, day_id FROM DAYS");
		while(rs.next()){
			dayToIdMap.put(rs.getString(1), rs.getInt(2));
		}
	}
	@Override
	public int getDayId(DayOfWeek day) throws SQLException {	
		readAllDayId();
		return dayToIdMap.get(day.name().toUpperCase());
	}

}
