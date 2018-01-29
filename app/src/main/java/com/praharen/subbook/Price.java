package com.praharen.subbook;


import java.io.Serializable;

/**
 * Created by wyatt on 25/01/18.
 */

/*
 * Price class:
 *
 * for easy printing of price types and conversion
 * from float to int or int to float.
 *
 */
public class Price implements Serializable {

    private float cost;

    Price (float cost) {
        this.cost = cost;
    }
    Price (int cost) {
        this.cost = (float) cost;
    }
    Price (String cost) {
        this.cost = Float.parseFloat(cost);
    }

    /*
     * cost getter
     * return: float cost.
     */
    public float getCost() {
        return cost;
    }

    /*
     * cost to string
     *
     * return string in the form ${cost}
     */
    public String toString() {
        return "$" + Float.toString(this.cost);
    }
}
