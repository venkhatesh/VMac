package com.example.venkat.vmac;

/**
 * Created by venkat on 18/2/18.
 */

public class inventory {
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    String quantity;

    public inventory(String date, String quantity) {
        this.date = date;
        this.quantity = quantity;
    }
}
