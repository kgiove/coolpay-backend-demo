package com.facebook.coolpay.json.recipient;

public class RecipientPayload {

    private Recipient recipient;

    public RecipientPayload() {
    }

    public RecipientPayload(Recipient recipient) {
        this.recipient = recipient;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
}
