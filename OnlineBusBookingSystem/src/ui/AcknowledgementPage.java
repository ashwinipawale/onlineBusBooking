package ui;

import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AcknowledgementPage {
	AcknowledgementPage(Tab reservationTab){
		printMessage(reservationTab);
	}
	
	public void printMessage(Tab reservationTab){
		GridPane messagePane = new GridPane();
		reservationTab.setContent(messagePane);

		Text text = new Text("Thank you!");
		text.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		messagePane.add(text, 1, 1);
	}
}
