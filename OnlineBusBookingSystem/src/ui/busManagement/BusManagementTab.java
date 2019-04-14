package ui.busManagement;

import java.time.DayOfWeek;
import java.time.LocalTime;
import data.City;
import data.processors.BusManagementProcessor;
import data.processors.BusManagementProcessorImpl;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import ui.busManagement.TravelOptionTableView;

public class BusManagementTab extends Tab{
	BusManagementProcessor busManagementProcessor = new BusManagementProcessorImpl();
	String travelOptionId, source, destination, day;
	int hour = 0; int minute = 0;;
	
	public BusManagementTab(String tabName){
		super(tabName);
		createUI();
	}

	private void createUI() {
		GridPane busManagementGrid = new GridPane();
		
		this.setContent(busManagementGrid);
		busManagementGrid.setHgap(5); 
		busManagementGrid.setVgap(5); 
		busManagementGrid.setPadding(new Insets(10, 10, 10, 10));
		
		Text editTravelOptionsText = new Text("Edit Travel Options: ");
		editTravelOptionsText.setFont(Font.font("Arial", FontWeight.BOLD , 16));
		busManagementGrid.add(editTravelOptionsText, 0, 1, 3, 1);
		
		Button addTravelOptionButton = new Button("Add");
		busManagementGrid.add(addTravelOptionButton, 0, 2, 1, 1);
		
		Button modifyDeleteButton = new Button("Modify/Delete");
		busManagementGrid.add(modifyDeleteButton, 1, 2, 1, 1);	
		
		VBox addTravelOptionsVBox = new VBox();
		addTravelOptionsVBox.setVisible(false);
		createAddTravelOptionUI(busManagementGrid, addTravelOptionsVBox);

		VBox modifyDeleteVBox = new VBox();
		createEditTravelOptionUI(busManagementGrid, modifyDeleteVBox);
		modifyDeleteVBox.setVisible(false);
		
		addTravelOptionButton.setOnAction(e -> {
			showAddUI(addTravelOptionsVBox, modifyDeleteVBox);
			});
		
		modifyDeleteButton.setOnAction(e -> {
			showModifyDeleteUI(addTravelOptionsVBox, modifyDeleteVBox);
		});
	}
	
	public void showAddUI(VBox addTravelOptionsVBox, VBox modifyDeleteVBox){
		addTravelOptionsVBox.setVisible(true);
		modifyDeleteVBox.setVisible(false);
	}
	
	public void createAddTravelOptionUI(GridPane busManagementGrid, VBox addTravelOptionsVBox){
    	Label sourceLabel = new Label("Source :");
    	TextField sourceTextField = new TextField();
    	Label destLabel = new Label("Destination :");
    	TextField destTextField = new TextField();
    	Label seatsLabel = new Label("Seats :");
    	TextField seatsTextField = new TextField();
		HBox HBox1 = new HBox(5, sourceLabel, sourceTextField, destLabel, destTextField, seatsLabel, seatsTextField);
		
    	Label travelDayLabel = new Label("Travel Day :");
    	TextField travelDayTextField = new TextField();
    	Label departTimeLabel = new Label("Depart Time :");
    	TextField departTimeTextField = new TextField();
		HBox HBox2 = new HBox(5, travelDayLabel, travelDayTextField, departTimeLabel, departTimeTextField);
		
		Button okButton = new Button("OK");		
		Button cancelButton = new Button("Cancel");			
		HBox HBox3 = new HBox(5, okButton, cancelButton);

		addTravelOptionsVBox.getChildren().addAll(HBox1, HBox2, HBox3);
		addTravelOptionsVBox.setMinHeight(10);
		addTravelOptionsVBox.setMinWidth(20);
		busManagementGrid.add(addTravelOptionsVBox, 0, 3, 6, 3);
		
		okButton.setOnAction(e -> {
			try {
				busManagementProcessor.addTravelOption(DayOfWeek.valueOf(travelDayTextField.getText()), new City(sourceTextField.getText()),
						new City(destTextField.getText()), LocalTime.of(hour, minute));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});	
	}
	
	public void showModifyDeleteUI(VBox addTravelOptionsVBox, VBox modifyDeleteVBox){
		addTravelOptionsVBox.setVisible(false);
		modifyDeleteVBox.setVisible(true);
	}
	
	public void createEditTravelOptionUI(GridPane busManagementGrid, VBox modifyDeleteVBox){
		TravelOptionTableView travelOptionTable = new TravelOptionTableView();
		try {
			travelOptionTable.createTravelOptionsTable();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		Button modityButton = new Button("Modify");
		
		Button deleteButton = new Button("Delete");
		
		HBox HBox4 = new HBox(5, modityButton, deleteButton);
		
		modifyDeleteVBox.getChildren().addAll(HBox4, travelOptionTable);
		busManagementGrid.add(modifyDeleteVBox, 0, 4, 5, 5);
		
		deleteButton.setOnAction(e -> {
			if(travelOptionTable.getSelectionModel().getSelectedItem() != null){
        		TravelOptionModel selectedTravelOptionModel = travelOptionTable.getSelectionModel().getSelectedItem();
				try {
					modifyDeleteVBox.setVisible(false);
					if(busManagementProcessor.removeTravelOption(selectedTravelOptionModel.getTravelOptionId())){
						travelOptionTable.createTravelOptionsTable();	
						modifyDeleteVBox.setVisible(true);
					}
				} catch (Exception e1) {
				}
			}
		});
		
		modityButton.setOnAction(e -> {
			if(travelOptionTable.getSelectionModel().getSelectedItem() != null){
				travelOptionTable.setEditable(true);
        		TravelOptionModel selectedTravelOptionModel = travelOptionTable.getSelectionModel().getSelectedItem();
        		
			}
		});
	}
}
