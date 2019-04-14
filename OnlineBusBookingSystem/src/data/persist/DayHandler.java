package data.persist;

import java.sql.SQLException;
import java.time.DayOfWeek;

public interface DayHandler {
	public int getDayId(DayOfWeek day) throws SQLException;
}
