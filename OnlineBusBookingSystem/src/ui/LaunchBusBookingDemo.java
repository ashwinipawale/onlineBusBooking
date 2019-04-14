package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ui.busManagement.BusManagementTab;
import ui.reservation.ReservationTab;

public class LaunchBusBookingDemo extends Application {
	public static void main(String[] args)  {
		System.out.println("Launching JavaFx application");
		launch(args);
		System.out.println("I AM DONE");
	}
	
	public void init(){
		System.out.println("Inside Init method");
	}
	
	@Override
	public void start(Stage travelOptionStage) throws Exception{
		travelOptionStage.setTitle("Bus Booking Service");
		FlowPane rootNode = new FlowPane();
		Scene travelOptionScene = new Scene(rootNode, 700, 500);
		
		travelOptionStage.setScene(travelOptionScene);
		Tab reservationTab = new ReservationTab("Reservation");
		Tab contactTab = new Tab("Contact");
		Tab busManagementTab = new BusManagementTab("Bus Management");
		
		Tab[] tabs = new Tab[]{reservationTab, contactTab, busManagementTab};
		
		TabPane tabPane = new TabPane(tabs);
        
		rootNode.getChildren().addAll(tabPane);
		
		travelOptionStage.setOnCloseRequest(e -> {
            //Platform.exit();
            //System.exit(0);
        });
		
		travelOptionStage.show();
	}
	
	public void stop(){
		System.out.println("Inside stop method");
	}
}
