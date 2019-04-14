package data;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PaymentInfo  implements Serializable{
	public double amount;
	public LocalDateTime paymentDate;
	public PaymentMode paymentMode;
	public PaymentDetails paymentDetails;
	
}
