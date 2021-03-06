package com.praharen.subbook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wyatt on 25/01/18.
 */

/*
 * Class: Subscription
 *
 * Class for representing a subscription
 *
 * Private:
 *  name, price, date, comment
 *
 * Public:
 *  constructor, set variables, get variables, string representation
 */
public class Subscription implements Serializable {

    private String name;
    private Price price;
    private Date date;
    private String comment;

    /*
     * Constructor for initializing class
     * without defining variables
     */
    public Subscription() {
        this.name = null;
        this.price = null;
        this.date = null;
    }

    /*
     * Constructor for subscription
     */
    public Subscription(String name, Price price, Date date, String comment) {
        setName(name);
        setPrice(price);
        setDate(date);
        setComment(comment);
    }

    /* setters */
    public void setName(String name)    { this.name = name; }
    public void setPrice(Price price)   { this.price = price; }
    public void setDate(Date date)      { this.date = date; }
    public void setComment(String comment) { this.comment = comment; }

    /* getters */
    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    /*
     * Subscription::toString
     *
     * return string representation of subscription class
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(this.date);
        return formattedDate + "\t\tName: " + this.name + "\t\tPrice: " + price;
    }

}
