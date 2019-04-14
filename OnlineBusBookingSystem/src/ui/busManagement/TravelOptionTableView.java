package ui.busManagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;
import data.TravelOption;
import data.processors.BusManagementProcessor;
import data.processors.BusManagementProcessorImpl;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TravelOptionTableView extends TableView<TravelOptionModel>{

	private ObservableList<TravelOptionModel> traveloptiondata = FXCollections.observableArrayList(new ArrayList<>());
	BusManagementProcessor busManagementProcessor = new BusManagementProcessorImpl();
	
	public TravelOptionTableView(){
		createView();
	}
	
	private void createView(){
		this.setEditable(false);
		TableColumn<TravelOptionModel, String> dayCol = new TableColumn<>("Day");
		TableColumn<TravelOptionModel, String> srcCol = new TableColumn<>("Source");
		TableColumn<TravelOptionModel, String> destCol = new TableColumn<>("Destination");
		TableColumn<TravelOptionModel, String> timeCol = new TableColumn<>("Time");
		TableColumn<TravelOptionModel, String> seatsCol = new TableColumn<>("Seats");

		dayCol.setCellValueFactory(new PropertyValueFactory<TravelOptionModel, String>("day"));
		srcCol.setCellValueFactory(new PropertyValueFactory<TravelOptionModel, String>("source"));
		destCol.setCellValueFactory(new PropertyValueFactory<TravelOptionModel, String>("destination"));
		timeCol.setCellValueFactory(new PropertyValueFactory<TravelOptionModel, String>("time"));
		seatsCol.setCellValueFactory(new PropertyValueFactory<TravelOptionModel, String>("seats"));
		
		this.setItems(traveloptiondata);
		this.getColumns().addAll(srcCol, destCol, dayCol, timeCol, seatsCol);
	}
	
	public void createTravelOptionsTable(){
		traveloptiondata.clear();
		Set<Entry<TravelOption, Integer>> entryset = new HashSet<>();
		try {
			entryset = busManagementProcessor.fetchAllTravelOptions().entrySet();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		entryset.stream().map(e -> new TravelOptionModel(e.getKey(), e.getValue().toString())).
									forEach(o->traveloptiondata.add(o));		
		this.setFixedCellSize(25);
		this.prefHeightProperty().bind(this.fixedCellSizeProperty().multiply(Bindings.size(this.getItems()).add(1.7)));
		this.minHeightProperty().bind(this.prefHeightProperty());
		this.maxHeightProperty().bind(this.prefHeightProperty());
	}
}

