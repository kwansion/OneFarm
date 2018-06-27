package com.example.rog.efarmer1;

public class commentAdapterList{
    private String date;
    private String desc;
    private String pImg;
    private String pName;
    public commentAdapterList(String date, String pImg, String pName, String desc) {
        this.date = date;
        this.pImg = pImg;
        this.pName = pName;
        this.desc = desc;
    }

    public String getpImg(){
        return pImg;
    }
    public String getDate() {
        return date;
    }
    public String getDesc(){
        return desc;
    }
    public String getName() {
        return pName;
    }
}
