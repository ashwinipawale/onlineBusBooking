package ui.reservation.traveller;

import java.util.ArrayList;

import data.Gender;
import data.Traveller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

 class TravellerDetailsTableview extends TableView<TravellerModel>{
	private ObservableList<TravellerModel> travellerdata = FXCollections.observableArrayList(new ArrayList<>());
	ObservableList<TravellerModel> prevTravellerData = FXCollections.observableArrayList(new ArrayList<>());
	
	public void personalDetailsTableView(int selectedSeats){
		
		travellerdata.clear();
		
		this.setEditable(true);
		
		for(int i=0; i< selectedSeats; i++){
			travellerdata.add(new TravellerModel(new Traveller(null,null, new Integer(0), Gender.MALE)));
		}
		 
		TableColumn<TravellerModel, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<TravellerModel, String>("firstName"));
		firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		firstNameCol.setOnEditCommit(
				(event)->{
					(event.getTableView().getItems().get(
	                        event.getTablePosition().getRow())
	                        ).setFirstName(event.getNewValue());
				}
	        );
		
		TableColumn<TravellerModel, String> lastNameCol = new TableColumn<>("Last Name");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<TravellerModel, String>("lastName"));
		lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameCol.setOnEditCommit(
	            new EventHandler<CellEditEvent<TravellerModel, String>>() {
	                @Override
	                public void handle(CellEditEvent<TravellerModel, String> t) {
	                    ((TravellerModel) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setLastName(t.getNewValue());
	                }
	            }
	        );
		
		TableColumn<TravellerModel, String> ageCol = new TableColumn<>("Age");
		ageCol.setCellValueFactory(new PropertyValueFactory<TravellerModel, String>("age"));
		ageCol.setCellFactory(TextFieldTableCell.forTableColumn());
		ageCol.setOnEditCommit(
	            new EventHandler<CellEditEvent<TravellerModel, String>>() {
	                @Override
	                public void handle(CellEditEvent<TravellerModel, String> t) {
	                    ((TravellerModel) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setAge(t.getNewValue());
	                }
	            }
	        );
		
		TableColumn<TravellerModel, String> genderCol = new TableColumn<>("Gender");
		Gender[] genderArray = Gender.values();
		genderCol.setCellValueFactory(new PropertyValueFactory<TravellerModel, String>("gender"));
		genderCol.setCellFactory(ComboBoxTableCell.forTableColumn(genderArray[0].toString(), genderArray[1].toString()));
		genderCol.setOnEditCommit(
	            new EventHandler<CellEditEvent<TravellerModel, String>>() {
	                @Override
	                public void handle(CellEditEvent<TravellerModel, String> t) {
	                    ((TravellerModel) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setGender(t.getNewValue());
	                }
	            }
	        );

		this.setItems(travellerdata);
		this.getColumns().clear();
		this.getColumns().addAll(firstNameCol, lastNameCol, ageCol, genderCol);		
	}
}

