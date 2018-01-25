package com.praharen.subbook;

/**
 * Created by wyatt on 25/01/18.
 */

public class Price {

    private int cost;

    Price (int cost) {
        this.cost = cost;
    }

    public String toString() {
        return "$" + Integer.toString(this.cost);
    }
}
