package com.facebook.coolpay.json.payment;
/**
 * @author KGiove
 */
public class PaymentPayload{

    private Payment payment;

    public PaymentPayload() {
    }

    public PaymentPayload(Payment payment) {
        this.payment = payment;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
