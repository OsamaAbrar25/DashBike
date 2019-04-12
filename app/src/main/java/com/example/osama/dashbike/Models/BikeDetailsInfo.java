package com.example.osama.dashbike.Models;

public class BikeDetailsInfo {
    private String id, bike_model, dealer, description, count, bike_rate_hr, bike_rate_h, bike_rate_f,
            bike_img, bike_isAvailable, thumbnail;

    public BikeDetailsInfo(String id, String bike_model, String dealer, String description, String count, String bike_rate_hr, String bike_rate_h, String bike_rate_f, String bike_img, String bike_isAvailable, String thumbnail) {
        this.id = id;
        this.bike_model = bike_model;
        this.dealer = dealer;
        this.description = description;
        this.count = count;
        this.bike_rate_hr = bike_rate_hr;
        this.bike_rate_h = bike_rate_h;
        this.bike_rate_f = bike_rate_f;
        this.bike_img = bike_img;
        this.bike_isAvailable = bike_isAvailable;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBike_model() {
        return bike_model;
    }

    public void setBike_model(String bike_model) {
        this.bike_model = bike_model;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getBike_rate_hr() {
        return bike_rate_hr;
    }

    public void setBike_rate_hr(String bike_rate_hr) {
        this.bike_rate_hr = bike_rate_hr;
    }

    public String getBike_rate_h() {
        return bike_rate_h;
    }

    public void setBike_rate_h(String bike_rate_h) {
        this.bike_rate_h = bike_rate_h;
    }

    public String getBike_rate_f() {
        return bike_rate_f;
    }

    public void setBike_rate_f(String bike_rate_f) {
        this.bike_rate_f = bike_rate_f;
    }

    public String getBike_img() {
        return bike_img;
    }

    public void setBike_img(String bike_img) {
        this.bike_img = bike_img;
    }

    public String getBike_isAvailable() {
        return bike_isAvailable;
    }

    public void setBike_isAvailable(String bike_isAvailable) {
        this.bike_isAvailable = bike_isAvailable;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
