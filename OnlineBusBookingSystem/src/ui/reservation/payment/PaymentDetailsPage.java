 package ui.reservation.payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import data.*;
import data.processors.BookingProcessor;
import data.processors.BookingProcessorImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.reservation.TravelOptionModel;
import ui.reservation.traveller.TravellerModel;

public class PaymentDetailsPage {
	BookingProcessor bookingProcessor = new BookingProcessorImpl();
	PaymentDetails paymentDetails;
	PaymentInfo paymentInfo = new PaymentInfo();
	List<Traveller> travellerList =  new ArrayList<>();
	TravelOption travelOption;
	HBox selectedHbox = null;
	
	public void getPaymentDetails(Tab reservationTab, TravelOptionModel travelOptionModel, ObservableList<TravellerModel> travellerdata,
			double totalTicketFare, String bookingId){
		
		GridPane paymentGrid = new GridPane();
		
		reservationTab.setContent(paymentGrid);
		
		paymentGrid.setHgap(5); 
		paymentGrid.setVgap(5); 
		paymentGrid.setPadding(new Insets(10, 10, 10, 10));
		
		Text amountToBePaidText = new Text("Amount to be paid: " + totalTicketFare);
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
		
		Text bankDetailstext = new Text("Please enter your bank details");
    	bankDetailstext.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    	paymentGrid.add(bankDetailstext, 0, 6, 3, 1);
    	bankDetailstext.setVisible(false);
    	Label bankNameLabel = new Label("Bank Name :");
    	Label accNumLabel = new Label("Account Num :");
    	TextField bankNameTextField = new TextField();
		TextField accNumTextField = new TextField();
		
		HBox onlineBankingHBox = new HBox(1, bankDetailstext, bankNameLabel,  bankNameTextField, accNumLabel, accNumTextField);
		onlineBankingHBox.setVisible(false);
		onlineBankingHBox.setPadding(new Insets(3,3,3,3));
		onlineBankingHBox.setSpacing(20);
		paymentGrid.add(onlineBankingHBox, 0, 8, 5, 3);
		
		Text cardPaymentTypeText = new Text("Please select Payment Type:");
		cardPaymentTypeText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    	paymentGrid.add(cardPaymentTypeText, 0, 6, 3, 1);
    	cardPaymentTypeText.setVisible(false);
    	Label cardNumLabel = new Label("Card Num: ");
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
		
		HBox cardPaymentHBox = new HBox(1, cardPaymentTypeText, cardTypeHBox,  cardNumLabel, cardNumTextField);
		cardPaymentHBox.setVisible(false);
		cardPaymentHBox.setPadding(new Insets(3,3,3,3));
		cardPaymentHBox.setSpacing(20);
		paymentGrid.add(cardPaymentHBox, 0, 8, 5, 3);

    	okButton.setOnAction(e -> {
    		if(paymentOptionsGroup.getSelectedToggle() != null) {
				if(onlineBankingButton.isSelected()){
					OnlineBankingPaymentDetails onlineBankingPaymentDetails = new OnlineBankingPaymentDetails();
			    	paymentDetails = onlineBankingPaymentDetails;
					paymentInfo.paymentMode = PaymentMode.ONLINE_BANKING;
					
					if(selectedHbox != null){
						selectedHbox.setVisible(false);
					}
					selectedHbox = onlineBankingHBox;
					onlineBankingHBox.setVisible(true);
					confirmButton.setVisible(true);
            		cancelButton.setVisible(true);
                	bankNameTextField.setOnAction(e1 -> {
                		if(!(bankNameTextField.getText() == null)){
                			if(bankNameTextField.getText().matches("^[a-zA-z]+$")){
                				onlineBankingPaymentDetails.bankName = bankNameTextField.getText().trim();
                			}
                		}
                	});
                	accNumTextField.setOnAction(e2 -> {
                		if(!(accNumTextField.getText() == null)){
	                		if(accNumTextField.getText().matches("^[0-9]*$")){
	                			onlineBankingPaymentDetails.accountNum = accNumTextField.getText().trim();
	                		}
                		}
                	});
				}
				
				else if(cardPaymentButton.isSelected()){
					CardPaymentDetails cardPaymentDetails = new CardPaymentDetails();
			    	paymentDetails = cardPaymentDetails;
					paymentInfo.paymentMode = PaymentMode.CARD;
										
					if(selectedHbox != null){
						selectedHbox.setVisible(false);
					}
					selectedHbox = cardPaymentHBox;
					cardPaymentHBox.setVisible(true);
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
				
				else {
					paymentInfo.paymentMode = PaymentMode.CASH;
					cardPaymentHBox.setVisible(false);
					cardPaymentTypeText.setVisible(false);
                	onlineBankingHBox.setVisible(false);
                	bankDetailstext.setVisible(false);
				}
    		}
		  });
		
    	ProgressBar progressBar = new ProgressBar();
		paymentGrid.add(progressBar, 1, 14, 1, 1);
		progressBar.setVisible(false);
    	confirmButton.setOnAction(e -> { new Thread(new Runnable(){
    		public void run(){
    			progressBar.setVisible(true);
    			for(double i=0.1; i<=1.0; i++){
    				progressBar.setProgress(i);
    			}
    		}
    	}).start();
    		
    		try {
				Thread.sleep(5000);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    		paymentInfo.amount = totalTicketFare;
    		paymentInfo.paymentDate = LocalDateTime.now();
    		paymentInfo.paymentDetails = paymentDetails;
    		
    		
    		for(TravellerModel travellerModel : travellerdata){
    			travellerList.add(new Traveller(travellerModel.getFirstName(), travellerModel.getLastName(), Integer.valueOf(travellerModel.getAge()),
    					Gender.valueOf(travellerModel.getGender())));
    		}
    		
    		String travelDateModel = travelOptionModel.getDate();
    		String[] splitDateLine = travelDateModel.split("-");
    		int year = Integer.parseInt(splitDateLine[0]);
    		int month = Integer.parseInt(splitDateLine[1]);
    		int day = Integer.parseInt(splitDateLine[2]);
    		LocalDate travelDate = LocalDate.of(year, month, day);
    		
    		String travelTime = travelOptionModel.getTime();
    		String[] splitTimeLine = travelTime.split(":");
    		int hour = Integer.parseInt(splitTimeLine[0]);
    		int minute = Integer.parseInt(splitTimeLine[1]);

    		travelOption = new TravelOption(travelOptionModel.getTravelOptionId(), new City(travelOptionModel.getSource()),
    				new City(travelOptionModel.getDestination()), travelDate.getDayOfWeek(), LocalTime.of(hour, minute));
    		
    		Booking booking = new Booking(bookingId, travelOption, paymentInfo, travellerList, travelDate);
    		
    		
    		if(bookingProcessor.storeBooking(booking)){
    			Alert successfulAlert = new Alert(AlertType.CONFIRMATION);
    			successfulAlert.setTitle("Booking Successful!");
    			Optional<ButtonType> result = successfulAlert.showAndWait();
    			if(result.get() == ButtonType.OK){
    				paymentGrid.setVisible(false);
    				GridPane thankGrid = new GridPane();
    				reservationTab.setContent(thankGrid);
    				thankGrid.setVisible(true);
    				Label thankLabel = new Label("Thank you!");
    				thankLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    				thankGrid.add(thankLabel, 0, 1);
    			}
    		}
    		else {
	    		Alert ErroAlert = new Alert(AlertType.ERROR);
	    		ErroAlert.setTitle("Booking Failure!");
				Optional<ButtonType> result = ErroAlert.showAndWait();
				if(result.get() == ButtonType.OK){
					return;
				}
			}
    		
    		
    	});
    	
		
	}
}
