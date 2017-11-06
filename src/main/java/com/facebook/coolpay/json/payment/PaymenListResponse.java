package com.facebook.coolpay.json.payment;
/**
 * @author KGiove
 */
import java.util.List;

public class PaymenListResponse {

    private List<Payment> payments;

    public PaymenListResponse() {
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
