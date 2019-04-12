package com.example.osama.dashbike.Models;

public class BookingsInfo {
    private String bike_from, dob, duration, transaction_amt, ord_id, transaction_id, is_accepted,
            bike_model, client, dealer;

    public BookingsInfo(String bike_from, String dob, String duration, String transaction_amt, String ord_id, String transaction_id, String is_accepted, String bike_model, String client, String dealer) {
        this.bike_from = bike_from;
        this.dob = dob;
        this.duration = duration;
        this.transaction_amt = transaction_amt;
        this.ord_id = ord_id;
        this.transaction_id = transaction_id;
        this.is_accepted = is_accepted;
        this.bike_model = bike_model;
        this.client = client;
        this.dealer = dealer;
    }

    public String getBike_from() {
        return bike_from;
    }

    public void setBike_from(String bike_from) {
        this.bike_from = bike_from;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTransaction_amt() {
        return transaction_amt;
    }

    public void setTransaction_amt(String transaction_amt) {
        this.transaction_amt = transaction_amt;
    }

    public String getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(String ord_id) {
        this.ord_id = ord_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getIs_accepted() {
        return is_accepted;
    }

    public void setIs_accepted(String is_accepted) {
        this.is_accepted = is_accepted;
    }

    public String getBike_model() {
        return bike_model;
    }

    public void setBike_model(String bike_model) {
        this.bike_model = bike_model;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }
}
