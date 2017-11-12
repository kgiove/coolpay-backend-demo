package com.facebook.coolpay.rs.json.recipient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author KGiove
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {

    private String name;

    private String id;

    public Recipient() {
    }

    public Recipient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
