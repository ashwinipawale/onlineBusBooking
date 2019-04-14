package ui.reservation.traveller;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.ErrorTextMessage;
import ui.reservation.TravelOptionModel;
import ui.reservation.payment.BookingPreview;

public class TravellerDetailsPage {
	int bookedSeats;
	public void viewSelectedTravelOption(Tab reservationTab, TravelOptionModel travelOptionModel, int availSeats){
		String source = travelOptionModel.source.get();
		String dest = travelOptionModel.destination.get();
		String travelDate = travelOptionModel.getDate();
		String departureTime = travelOptionModel.time.get();
		
		GridPane selectedTravelOptionGrid = new GridPane();
		
		reservationTab.setContent(selectedTravelOptionGrid);
		
		selectedTravelOptionGrid.setHgap(5); //horizontal gap in pixels => that's what you are asking for
		selectedTravelOptionGrid.setVgap(5); //vertical gap in pixels
		selectedTravelOptionGrid.setPadding(new Insets(10, 10, 10, 10));
		
		Text selectedTrvelInfoText = new Text("Selected travel option info: ");
		selectedTrvelInfoText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		selectedTravelOptionGrid.add(selectedTrvelInfoText, 0, 0,2, 1);

		Label sourceLabel = new Label("Source Station: ");
		selectedTravelOptionGrid.add(sourceLabel , 0, 1, 1, 1);
		Text sourceText = new Text(source);
		selectedTravelOptionGrid.add(sourceText, 1, 1, 1, 1);

		Label destLabel = new Label("Destination: ");
		selectedTravelOptionGrid.add(destLabel , 2, 1, 1, 1);
		Text destText = new Text(dest);
		selectedTravelOptionGrid.add(destText, 3, 1, 1, 1);
		
		Label travelDateLabel = new Label("Travel Date: ");
		selectedTravelOptionGrid.add(travelDateLabel , 0,3, 1, 1);
		Text travelDateText = new Text(travelDate.toString());
		selectedTravelOptionGrid.add(travelDateText, 1, 3, 1, 1);
		
		Label depatureTimeLabel = new Label("Travel Time: ");
		selectedTravelOptionGrid.add(depatureTimeLabel , 2, 3, 1, 1);
		Text departureTimeText = new Text(departureTime.toString());
		selectedTravelOptionGrid.add(departureTimeText, 3, 3, 1, 1);

		TravellerDetailsTableview travellerPersonalDetailsTable = new TravellerDetailsTableview();
		selectedTravelOptionGrid.add(travellerPersonalDetailsTable, 0, 6, 3, 1);
		travellerPersonalDetailsTable.setVisible(false);
		
		ComboBox<Integer> seatsSelectCombo = new ComboBox<>();
		Stream<Integer> numOfSeatsStream = null;
		if(availSeats >= 5){
			numOfSeatsStream = Stream.generate(new Supplier<Integer>(){
				int num=0;
				 public Integer get() {
					num++;
					return new Integer(num);
				}
				}).limit(5);
		}
		else {
			numOfSeatsStream = Stream.generate(new Supplier<Integer>(){
				int num=0;
				 public Integer get() {
					num++;
					return new Integer(num);
				}
				}).limit(availSeats);
		}
		
		seatsSelectCombo.getItems().addAll(numOfSeatsStream.collect(Collectors.toList()));
		Label seatsLabel = new Label("No. of seats: ");
		selectedTravelOptionGrid.add(seatsLabel , 0, 4, 1, 1);
		selectedTravelOptionGrid.add(seatsSelectCombo, 1, 4, 1, 1);
		
		Button nextButton = new Button("Next");
		selectedTravelOptionGrid.add(nextButton, 4, 6, 1, 1);
		nextButton.setVisible(false);

		seatsSelectCombo.setOnAction(e -> {
			bookedSeats = seatsSelectCombo.getSelectionModel().getSelectedItem();
			travellerPersonalDetailsTable.personalDetailsTableView(bookedSeats);
			travellerPersonalDetailsTable.setVisible(true);
			nextButton.setVisible(true);
			travellerPersonalDetailsTable.setFixedCellSize(25);
			travellerPersonalDetailsTable.prefHeightProperty().bind(travellerPersonalDetailsTable.fixedCellSizeProperty().multiply(Bindings.size(travellerPersonalDetailsTable.getItems()).add(1.7)));
			travellerPersonalDetailsTable.minHeightProperty().bind(travellerPersonalDetailsTable.prefHeightProperty());
			travellerPersonalDetailsTable.maxHeightProperty().bind(travellerPersonalDetailsTable.prefHeightProperty());
			});
				
		ErrorTextMessage errorMessage = new ErrorTextMessage("Please enter valid traveller details");
		selectedTravelOptionGrid.add(errorMessage, 0, 5, 2, 1);
		errorMessage.setVisible(false);
		
		nextButton.setOnAction(e -> {
			ObservableList<TravellerModel> travellerdata = travellerPersonalDetailsTable.getItems();
			if(new TravellerDetailsValidator().validatetravellerData(travellerdata)){
				selectedTravelOptionGrid.setVisible(false);
				BookingPreview bookingPreview = new BookingPreview();
				bookingPreview.showBookingPreview(reservationTab, travelOptionModel, travellerdata, bookedSeats);
			}
			else errorMessage.setVisible(true);

		});
	}
}

