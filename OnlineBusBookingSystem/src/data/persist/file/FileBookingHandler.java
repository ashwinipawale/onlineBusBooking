package data.persist.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.prism.shape.ShapeRep.InvalidationType;

import data.Booking;
import data.City;
import data.Gender;
import data.TravelOption;
import data.Traveller;
import data.persist.BookingHandler;

public class FileBookingHandler implements BookingHandler {
	final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
	List<Booking> allBookings;
	private String FILE = "C:\\Users\\rajar\\workspace\\OnlineBusBookingSystem\\resources\\bookings\\";
	/*
	 * @returns a list of all existing Booking
	 */
	private List<Booking> initialize(){
		if(allBookings == null){
			Path path = Paths.get(FILE);
			allBookings = Arrays.asList(path.toFile().list()).stream().map(file->readBookingImpl(file)).collect(Collectors.toList());
		}
		return allBookings;	
	}
	
	/*
	 * booking - Booking 
	 * @returns true, false based on whether successful completion of storeBooking
	 * @see data.persist.BookingHandler#storeBooking(data.Booking)
	 */
	@Override
	public boolean storeBooking(Booking booking) {
		Path path = Paths.get(FILE.concat(booking.bookingId));
		try( FileOutputStream fileIn = new FileOutputStream(path.toFile());
				ObjectOutputStream out = new ObjectOutputStream(fileIn)){
			out.writeObject(booking);
				allBookings.add(booking);
				return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * bookingId - bookingId for the Booking to be fetched
	 * @returns Booking corresponding to bookingId
	 */
	@Override
	public Booking readBooking(String bookingId) {
		initialize();
		return allBookings.stream().filter(b->bookingId.equals(b.bookingId)).findFirst().orElse(null);
	}
	
	private Booking readBookingImpl(String bookingId){
		Path path = Paths.get(FILE.concat(bookingId));
		try( FileInputStream fileIn = new FileInputStream(path.toFile());
				ObjectInputStream in = new ObjectInputStream(fileIn)){
			return (Booking)in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 
	 * @see data.persist.BookingHandler#getAllBookings(java.lang.String, java.time.LocalDate)
	 */
	@Override
	public List<Booking> getAllBookings(String travelOptionId, LocalDate date) {
		initialize();
		return allBookings.stream().	
			filter(b->travelOptionId.equals(b.travelOption.travelOptionId) && date.equals(b.travelDate)).
			collect(Collectors.toList());
	}

	
	/*
	 * @returns a new bookingId by incrementing bookingId from the last Booking 
	 * @see data.persist.BookingHandler#bookingIdGenerator()
	 */
	@Override
	public String nextBookingId() {
		initialize();
		return ""+(allBookings.size() + 1);
	}
	
	public static void main(String [] args){
		FileBookingHandler handler = new FileBookingHandler();
		/*Booking b = new Booking();
		b.bookingId = handler.nextBookingId();
		b.travelDate = LocalDate.now();
		b.travelOption = new TravelOption("5", new City("Source"), new City("Dest"), DayOfWeek.FRIDAY, LocalTime.now());
		b.travellers = Arrays.asList(new Traveller("First", "last", 10, Gender.MALE));
		handler.storeBooking(b);
	
		System.out.println(handler.readBooking("1").travellers);
		
		System.out.println(handler.getAllBookings("5", LocalDate.now()));*/
	}
}