package ui.reservation.payment;

import data.processors.BookingProcessor;
import data.processors.BookingProcessorImpl;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.reservation.TravelOptionModel;
import ui.reservation.traveller.TravellerDetailsPage;
import ui.reservation.traveller.TravellerModel;

public class BookingPreview {
	BookingProcessor bookingProcessor = new BookingProcessorImpl();
	public void showBookingPreview(Tab reservationTab, TravelOptionModel travelOptionModel, ObservableList<TravellerModel> travellerdata,
			int bookedSeats){
		double ticketPrice = bookingProcessor.getTicketPrice(travelOptionModel.getSource(), travelOptionModel.getDestination());
		double totalTicketFare = ticketPrice * bookedSeats;
		String bookingId = bookingProcessor.nextBookingId();
	
		GridPane bookingPreviewGrid = new GridPane();
		reservationTab.setContent(bookingPreviewGrid);
		
		bookingPreviewGrid.setHgap(5); 
		bookingPreviewGrid.setVgap(5); 
		bookingPreviewGrid.setPadding(new Insets(10, 10, 10, 10));
		
		Text bookingPreviewText = new Text("Booking ID: " + bookingId + "  Booking Preview");
		bookingPreviewText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		bookingPreviewGrid.add(bookingPreviewText, 0, 0, 3, 1);
		
		Label sourceLabel = new Label("Source Station: ");
		bookingPreviewGrid.add(sourceLabel , 0, 1, 1, 1);
		Text sourceText = new Text(travelOptionModel.getSource());
		bookingPreviewGrid.add(sourceText, 1, 1, 1, 1);

		Label destLabel = new Label("Destination: ");
		bookingPreviewGrid.add(destLabel , 2, 1, 1, 1);
		Text destText = new Text(travelOptionModel.getDestination());
		bookingPreviewGrid.add(destText, 3, 1, 1, 1);
		
		Label travelDateLabel = new Label("Travel Date: ");
		bookingPreviewGrid.add(travelDateLabel , 0,3, 1, 1);
		Text travelDateText = new Text(travelOptionModel.getDate());
		bookingPreviewGrid.add(travelDateText, 1, 3, 1, 1);
		
		Label depatureTimeLabel = new Label("Travel Time: ");
		bookingPreviewGrid.add(depatureTimeLabel , 2, 3, 1, 1);
		Text departureTimeText = new Text(travelOptionModel.getTime());
		bookingPreviewGrid.add(departureTimeText, 3, 3, 1, 1);
		
		Label bookedSeatsLabel = new Label("Seats booked: ");
		bookingPreviewGrid.add(bookedSeatsLabel , 0, 4, 1, 1);
		Text bookedSeatsText = new Text(Integer.valueOf(bookedSeats).toString());
		bookingPreviewGrid.add(bookedSeatsText, 1, 4, 1, 1);
		
		Text passengerDetailsText = new Text("Passenger details");
		passengerDetailsText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		bookingPreviewGrid.add(passengerDetailsText, 0, 5, 2, 1);

		int rowIndex = 6;
		for(TravellerModel traveller : travellerdata){
			Text passengerText = new Text(traveller.getFirstName() + "   " + traveller.getLastName() + "     Age: " +
					traveller.getAge() + "    " + traveller.getGender() + "     " + "Ticket Fare: " + ticketPrice);
			bookingPreviewGrid.add(passengerText, 0, rowIndex, 3, 1);
			rowIndex++; 
		}
		
		Label totalPriceLabel = new Label("Total Price:  ");
		totalPriceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		bookingPreviewGrid.add(totalPriceLabel, 0, ++rowIndex, 1, 1);
		
		Text totalTicketFareText = new Text(Double.valueOf(totalTicketFare).toString());
		totalTicketFareText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		bookingPreviewGrid.add(totalTicketFareText, 1, rowIndex, 1, 1);
		
		Button confirmButton = new Button("Confirm");
		bookingPreviewGrid.add(confirmButton, 0, ++rowIndex, 1, 1);
		confirmButton.setOnAction(e -> {
			bookingPreviewGrid.setVisible(false);
			PaymentDetailsPage paymentDetails = new PaymentDetailsPage();
			paymentDetails.getPaymentDetails(reservationTab, travelOptionModel, travellerdata, totalTicketFare, bookingId);
		});
		
		Button cancelButton = new Button("Cancel");
		bookingPreviewGrid.add(cancelButton, 1, rowIndex, 1, 1);
		cancelButton.setOnAction(e -> {
			TravellerDetailsPage travellerDetailsPage = new TravellerDetailsPage();
    		travellerDetailsPage.viewSelectedTravelOption(reservationTab, travelOptionModel ,new Integer(travelOptionModel.seats.get()).intValue());
		});
		
	}
}
