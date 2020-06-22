package com.example.cityparcel.serviceHome;

public class FindParcelNode {

    private String title;
    private String destination;
    private String price;

    public FindParcelNode(String title, String destination, String price) {
        this.title = title;
        this.destination = destination;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle() {
        this.title = title;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination() {
        this.destination = destination;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice() {
        this.price = price;
    }
}
