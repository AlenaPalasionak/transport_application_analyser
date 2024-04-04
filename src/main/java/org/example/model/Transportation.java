package org.example.model;

public class Transportation {
    private final String carrierName;
    private final String clientName;
    private final String date;
    private final String price;
    private final String driver;

    public Transportation(String carrierName, String clientName, String date, String price, String driver) {
        this.carrierName = carrierName;
        this.clientName = clientName;
        this.date = date;
        this.price = price;
        this.driver = driver;
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

    public String getDriver() {
        return driver;
    }
}
