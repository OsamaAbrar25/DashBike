package com.example.osama.dashbike.Models;

public class BikesInfo {
    private String id, bike_model,txt2, bike_model_id;
    private String imageurl;

    public BikesInfo(String id, String bike_model, String txt2, String imageurl, String bike_model_id){
        setId(id);
        setBike_model(bike_model);
        setTxt2(txt2);
        setImageUrl(imageurl);
        setBike_model_id(bike_model_id);
    }

    public String getId(){
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

    public String getTxt2() {
        return txt2;
    }

    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }

    public String getImageUrl() {
        return imageurl;
    }

    public void setImageUrl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBike_model_id() {
        return bike_model_id;
    }

    public void setBike_model_id(String bike_model_id) {
        this.bike_model_id = bike_model_id;
    }

}
