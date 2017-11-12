package com.facebook.coolpay.rs.json.payment;
/**
 * @author KGiove
 */
import java.math.BigDecimal;

//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public class Payment {

	private BigDecimal amount;
	private String currency;
	private String status;
	private String recipient_id;
	private String id;	

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecipient_id() {
		return recipient_id;
	}

	public void setRecipient_id(String recipient_id) {
		this.recipient_id = recipient_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public String toString() {
		return "Payment [amount=" + amount + ", currency=" + currency + ", status=" + status + ", recipientId="
				+ recipient_id + ", id=" + id + "]";
	}
}
