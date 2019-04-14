package ui.reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

import data.City;
import data.processors.BookingProcessor;
import data.processors.BookingProcessorImpl;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import ui.ErrorTextMessage;
import ui.reservation.traveller.TravellerDetailsPage;

public class ReservationTab extends Tab{
	BookingProcessor bookingProcessor = new BookingProcessorImpl();

	public ReservationTab(String tabName){
		super(tabName);
		createUi();
	}
	
	/*
	 *
	 */
	private void createUi(){
		GridPane reservationGrid = new GridPane();
		List<City> citiesList = bookingProcessor.getAllCities();
		
		City[] sourceCitesList = new City[citiesList.size()];
		sourceCitesList = citiesList.toArray(sourceCitesList);
		
		ComboBox<City> sourceCitiesCombo = new ComboBox<>();
		sourceCitiesCombo.getItems().addAll(sourceCitesList);
		
		ComboBox<City> destinationCombo = new ComboBox<>();
		sourceCitiesCombo.setOnAction(e->{
			List<City> destList = bookingProcessor.getAllCities(sourceCitiesCombo.getSelectionModel().getSelectedItem());
			City[] destCitesList = new City[destList.size()];
			destCitesList = destList.toArray(destCitesList);
			destinationCombo.getItems().clear();
			destinationCombo.getItems().addAll(destCitesList);
		});
		
		this.setContent(reservationGrid);
		reservationGrid.setVgap(10);
        reservationGrid.setHgap(10);
        reservationGrid.setPadding(new Insets(5, 5, 5, 5));
        reservationGrid.add(new Label("Source Station: "), 0, 1);
        reservationGrid.add(sourceCitiesCombo, 1, 1, 2, 1);
        reservationGrid.add(new Label("Destination: "), 3, 1);
        reservationGrid.add(destinationCombo, 4, 1, 2, 1);
        
        DatePicker datePicker = new DatePicker(LocalDate.now());
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupCalendar = datePickerSkin.getPopupContent();
        
        reservationGrid.add(new Label("Select travel date: "), 0, 2, 1, 1);
        reservationGrid.add(popupCalendar, 1, 2, 3, 1);
        
        Button okButton = new Button("OK");
        reservationGrid.add(okButton, 1 , 4, 1 , 1);
        
        Button cancelButton = new Button("Cancel"); 
        reservationGrid.add(cancelButton, 2, 4, 1, 1);
        
        Button bookButton = new Button("Book");
		reservationGrid.add(bookButton, 5, 6, 1 , 1);

        TravelOptionTableView travelOptionTable = new TravelOptionTableView();
    	reservationGrid.add(travelOptionTable, 0, 6, 5, 10);
    	 
    	ErrorTextMessage CityNotSelErrorText = new ErrorTextMessage("Select city");;
    	reservationGrid.add(CityNotSelErrorText, 1, 5, 1, 1);
        okButton.setOnAction(e -> {
        	LocalDate selectedTravelDate = datePicker.getValue();
        	if(!(sourceCitiesCombo.getSelectionModel().isEmpty() && destinationCombo.getSelectionModel().isEmpty())){
        		City selectedSource = sourceCitiesCombo.getSelectionModel().getSelectedItem();
            	City selectedDestination = destinationCombo.getSelectionModel().getSelectedItem();
        		travelOptionTable.createTravelOptionsTable(selectedSource, selectedDestination, selectedTravelDate);
        	}
        	else CityNotSelErrorText.setVisible(true);
        });
        
    	bookButton.setOnAction(e -> {
        	if(travelOptionTable.getSelectionModel().getSelectedItem() != null){
        		TravelOptionModel selectedTravelOptionModel = travelOptionTable.getSelectionModel().getSelectedItem();
        		reservationGrid.setVisible(false);
        		TravellerDetailsPage travellerDetailsPage = new TravellerDetailsPage();
            	travellerDetailsPage.viewSelectedTravelOption(this, selectedTravelOptionModel ,new Integer(selectedTravelOptionModel.seats.get()).intValue());
            	}
        	});
        
	}
}
