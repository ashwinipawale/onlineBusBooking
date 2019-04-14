package ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ErrorTextMessage extends Text{
	
	public ErrorTextMessage(String errMessage){
		super(errMessage);
		this.setFont(Font.font("Arial", 12));
		this.setFill(Color.RED);
		this.setVisible(false);
	}
	
	/*public Text gettravellerDataErrorText(){
		
		this.setVisible(false);
		return this;
	}
	
	public Text getNoSeatsAvailableErrorText(){
		this.setFont(Font.font("Arial", 12));
		this.setFill(Color.RED);
		this.setVisible(false);
		return this;
	}*/
}
