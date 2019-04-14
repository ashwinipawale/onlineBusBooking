import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import data.Booking;
import data.CardPaymentDetails;
import data.CardType;
import data.City;
import data.OnlineBankingPaymentDetails;
import data.PaymentInfo;
import data.PaymentMode;
import data.TravelOption;
import data.Traveller;
import data.processors.BookingProcessor;
import data.processors.BookingProcessorImpl;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

public class TravellerDetailsTrial extends Application{
	public static void main(String args[]){
		System.out.println("Launching JavaFx application");
		launch(args);
	}
	
	public void init(){
		System.out.println("Inside Init method");
	}
	
	@Override
	public void start(Stage paymentStage) throws Exception{
		BookingProcessor bookingProcessor = new BookingProcessorImpl();
		CardPaymentDetails cardPaymentDetails = new CardPaymentDetails();
		OnlineBankingPaymentDetails paymentDetails = new OnlineBankingPaymentDetails();
		PaymentInfo paymentInfo = new PaymentInfo();
		Booking booking;
		List<Traveller> travellerList =  new ArrayList<>();
		TravelOption travelOption;
		paymentStage.setTitle("Payment Details");
		FlowPane paymentPane = new FlowPane();
		
		Scene paymentScene = new Scene(paymentPane,700,500);
		paymentStage.setScene(paymentScene);
		
		GridPane paymentGrid = new GridPane();
		
		paymentStage.show();
		
		paymentGrid.setHgap(5); 
		paymentGrid.setVgap(5); 
		paymentGrid.setPadding(new Insets(10, 10, 10, 10));
		
		Text amountToBePaidText = new Text("Amount to be paid: " + 1500);
		amountToBePaidText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		paymentGrid.add(amountToBePaidText, 0, 1, 2, 1);
		
		Label paymentTypeLabel = new Label("Select Payment Mode: ");
		paymentGrid.add(paymentTypeLabel, 0, 2, 2, 1);

		final ToggleGroup paymentOptionsGroup = new ToggleGroup();
		RadioButton onlineBankingButton = new RadioButton(PaymentMode.ONLINE_BANKING.name());
		onlineBankingButton.setToggleGroup(paymentOptionsGroup);
		onlineBankingButton.setSelected(true);
		RadioButton cashButton = new RadioButton(PaymentMode.CASH.name());
		cashButton.setToggleGroup(paymentOptionsGroup);
		RadioButton cardPaymentButton = new RadioButton(PaymentMode.CARD.name());
		cardPaymentButton.setToggleGroup(paymentOptionsGroup);
		HBox paymentOptionsHBox = new HBox(5, onlineBankingButton, cashButton, cardPaymentButton);
		paymentOptionsHBox.setPadding(new Insets(5, 12, 5, 5));
		paymentOptionsHBox.setSpacing(25);
		paymentGrid.add(paymentOptionsHBox, 0, 3, 10, 2);
		
		Button okButton = new Button("OK");
		paymentGrid.add(okButton, 1, 5, 1, 1);
		
		Button confirmButton = new Button("Confirm");
		paymentGrid.add(confirmButton, 1, 12, 1, 1);
		confirmButton.setVisible(false);
		
		Button cancelButton = new Button("Cancel");
		paymentGrid.add(cancelButton, 2, 12, 1, 1);
		cancelButton.setVisible(false);
		
		Text text = new Text("Please enter your bank details");
    	text.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    	paymentGrid.add(text, 0, 6, 3, 1);
    	text.setVisible(false);
    	Label bankNameLabel = new Label("Bank Name :");
    	Label accNumLabel = new Label("Account Num :");
    	TextField bankNameTextField = new TextField();
		TextField accNumTextField = new TextField();
		
		HBox hb1 = new HBox(1, text, bankNameLabel,  bankNameTextField, accNumLabel, accNumTextField);
		hb1.setVisible(false);
		hb1.setPadding(new Insets(3,3,3,3));
		hb1.setSpacing(20);
		paymentGrid.add(hb1, 0, 8, 5, 3);
		
		Text text1 = new Text("Please select Payment Type:");
		text1.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    	paymentGrid.add(text1, 0, 6, 3, 1);
    	text.setVisible(false);
    	Label cardnumLabel = new Label("cardnum :");
		TextField cardNumTextField = new TextField();
		
		final ToggleGroup cardTypeGroup = new ToggleGroup();
		RadioButton creditButton = new RadioButton(CardType.CREDIT.name());
		creditButton.setToggleGroup(cardTypeGroup);
		creditButton.setSelected(true);
		RadioButton debitButton = new RadioButton(CardType.DEBIT.name());
		debitButton.setToggleGroup(cardTypeGroup);		
		HBox cardTypeHBox = new HBox(5, creditButton, debitButton);
		cardTypeHBox.setPadding(new Insets(5, 12, 5, 5));
		cardTypeHBox.setSpacing(25);
		
		HBox hb2 = new HBox(1, text1, cardTypeHBox,  cardnumLabel, cardNumTextField);
		hb2.setVisible(false);
		hb2.setPadding(new Insets(3,3,3,3));
		hb2.setSpacing(20);
		paymentGrid.add(hb2, 0, 8, 5, 3);
		paymentPane.getChildren().addAll(paymentGrid);

    	okButton.setOnAction(e -> {
    		if(paymentOptionsGroup.getSelectedToggle() != null) {
				if(onlineBankingButton.isSelected()){
			    	paymentInfo.paymentMode = PaymentMode.ONLINE_BANKING;
                	hb1.setVisible(true);
                	text.setVisible(true);
            		confirmButton.setVisible(true);
            		cancelButton.setVisible(true);
            		
                	bankNameTextField.setOnAction(e1 -> {
                		if(!(bankNameTextField.getText() == null)){
                			if(bankNameTextField.getText().matches("^[a-zA-z]+$")){
                				paymentDetails.bankName = bankNameTextField.getText().trim();
                			}
                		}
                	});
                	accNumTextField.setOnAction(e2 -> {
                		if(!(accNumTextField.getText() == null)){
	                		if(accNumTextField.getText().matches("^[0-9]*$")){
	                    		paymentDetails.accountNum = accNumTextField.getText().trim();
	                		}
                		}
                	});
				}
				
				else if(cardPaymentButton.isSelected()){
					paymentInfo.paymentMode = PaymentMode.CARD;
					hb1.setVisible(false);
					hb2.setVisible(true);
					text.setVisible(false);
					text1.setVisible(true);
					/*Text text1 = new Text("Please select payment type");
					text1.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                	paymentGrid.add(text1, 0, 6, 2, 1);*/
                	
            		/*final ToggleGroup cardTypeGroup = new ToggleGroup();
            		RadioButton creditButton = new RadioButton(CardType.CREDIT.name());
            		creditButton.setToggleGroup(cardTypeGroup);
            		creditButton.setSelected(true);
            		RadioButton debitButton = new RadioButton(CardType.DEBIT.name());
            		debitButton.setToggleGroup(cardTypeGroup);
            		
            		HBox cardTypeHBox = new HBox(5, creditButton, debitButton);
            		cardTypeHBox.setPadding(new Insets(5, 12, 5, 5));
            		cardTypeHBox.setSpacing(25);
            		paymentGrid.add(cardTypeHBox, 0, 7, 10, 2);*/
            		
            		confirmButton.setVisible(true);
            		cancelButton.setVisible(true);
                	
            		if(creditButton.isSelected()){
            			cardPaymentDetails.cardtype = CardType.CREDIT;
	                	cardNumTextField.setOnAction(e2 -> {
	                		if(!(cardNumTextField.getText() == null)){
		                		if(cardNumTextField.getText().matches("^[0-9]*$")){
		                			cardPaymentDetails.cardNum = accNumTextField.getText().trim();
		                		}
	                		}
	                	});
            		}
            		
            		else if(debitButton.isSelected()){
            			cardPaymentDetails.cardtype = CardType.DEBIT;
	                	cardNumTextField.setOnAction(e2 -> {
	                		if(!(cardNumTextField.getText() == null)){
		                		if(cardNumTextField.getText().matches("^[0-9]*$")){
		                			cardPaymentDetails.cardNum = accNumTextField.getText().trim();
		                		}
	                		}
	                	});
            		}
				}
				
				else paymentInfo.paymentMode = PaymentMode.CASH;
    		}
		  });
		
	}
	
	public void stop(){
		System.out.println("Inside stop method");
	}
}
