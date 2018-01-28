package com.praharen.subbook;


import java.io.Serializable;

/**
 * Created by wyatt on 25/01/18.
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

    public float getCost() {
        return cost;
    }

    public String toString() {
        return "$" + Float.toString(this.cost);
    }
}
