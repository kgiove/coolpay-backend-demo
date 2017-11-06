package com.facebook.coolpay.json.recipient;
/**
 * @author KGiove
 */
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
