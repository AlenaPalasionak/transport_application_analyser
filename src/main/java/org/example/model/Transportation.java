package org.example.model;



public class Transportation {
    private final String carrierName;
    private final String clientName;
    private final String date;
    private final String price;

    public Transportation(String carrierName, String clientName, String date, String price) {
        this.carrierName = carrierName;
        this.clientName = clientName;
        this.date = date;
        this.price = price;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public String getClientName() {
        return clientName;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }
}
