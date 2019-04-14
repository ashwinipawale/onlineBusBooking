package ui.reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;
import data.City;
import data.TravelOption;
import data.processors.BookingProcessor;
import data.processors.BookingProcessorImpl;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TravelOptionTableView extends TableView<TravelOptionModel>{
	
	private ObservableList<TravelOptionModel> traveloptiondata = FXCollections.observableArrayList(new ArrayList<>());
	BookingProcessor bookingProcessor = new BookingProcessorImpl();
	
	public TravelOptionTableView(){
		createView();
	}
	
	private void createView(){

		this.setEditable(false);
		TableColumn<TravelOptionModel, String> srcCol = new TableColumn<>("Source");
		TableColumn<TravelOptionModel, String> destCol = new TableColumn<>("Destination");
		TableColumn<TravelOptionModel, String> dateCol = new TableColumn<>("Date");
		TableColumn<TravelOptionModel, String> timeCol = new TableColumn<>("Time");
		TableColumn<TravelOptionModel, String> seatsCol = new TableColumn<>("Seats");


		srcCol.setCellValueFactory(new PropertyValueFactory<TravelOptionModel, String>("source"));
		destCol.setCellValueFactory(new PropertyValueFactory<TravelOptionModel, String>("destination"));
		dateCol.setCellValueFactory(new PropertyValueFactory<TravelOptionModel, String>("date"));
		timeCol.setCellValueFactory(new PropertyValueFactory<TravelOptionModel, String>("time"));
		seatsCol.setCellValueFactory(new PropertyValueFactory<TravelOptionModel, String>("seats"));

		this.setItems(traveloptiondata);
		this.getColumns().addAll(srcCol, destCol, dateCol, timeCol, seatsCol);
		
	}
	
	public void createTravelOptionsTable(City src, City dest, LocalDate date){
		traveloptiondata.clear();
		Set<Entry<TravelOption, Integer>> entryset = bookingProcessor.getTravelOption(src, dest, date).
				entrySet();
		entryset.stream().filter(e -> {return !e.getValue().equals(Integer.valueOf(0)); }).map(e -> new TravelOptionModel(e.getKey(), date, e.getValue().toString())).forEach(o->traveloptiondata.add(o));
		this.setFixedCellSize(25);
		this.prefHeightProperty().bind(this.fixedCellSizeProperty().multiply(Bindings.size(this.getItems()).add(1.7)));
		this.minHeightProperty().bind(this.prefHeightProperty());
		this.maxHeightProperty().bind(this.prefHeightProperty());

	}
}
