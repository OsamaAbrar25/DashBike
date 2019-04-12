package com.example.osama.dashbike.Models;

public class BikesInfo {
    private String txt1,txt2;
    private String imageurl;

    public BikesInfo(String txt1, String txt2, String imageurl){
        setTxt1(txt1);
        setTxt2(txt2);
        setImageUrl(imageurl);
    }

    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
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

}
