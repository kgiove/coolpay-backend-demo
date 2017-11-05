package com.facebook.coolpay.json.recipient;

import java.util.List;

public class RecipientListResponse {

    private List<Recipient> recipients;

    public RecipientListResponse() {
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }
}
