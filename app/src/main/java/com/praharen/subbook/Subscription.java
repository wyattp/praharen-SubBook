package com.praharen.subbook;

import java.util.Date;

/**
 * Created by wyatt on 25/01/18.
 */

public class Subscription {

    private String name;
    private Price price;
    private Date date;

    public Subscription() {
        this.name = null;
        this.price = null;
        this.date = null;
    }

    public Subscription(String name, Price price, Date date) {
        setName(name);
        setPrice(price);
        setDate(date);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isValid() {
        return name != null && price != null && date != null;
    }

}
