package com.facebook.coolpay.rs.json.recipient;
/**
 * @author KGiove
 */
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
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
