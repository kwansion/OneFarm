package com.example.rog.efarmer1;

public class adapterList {
    private int id;
    private String desc;
    private String image;
    private String eta;
    private String location;
    private String id1;
    public adapterList(String desc, String image, String eta, String location, int id) {
        this.desc = desc;
        this.image = image;
        this.eta = eta;
        this.location = location;
        this.id = id;
        id1 = Integer.toString(id);
    }

    public String getId() {
        return id1;
    }
    public String getEta(){
        return eta;
    }
    public String getDesc() {
        return desc;
    }

    public String getLocation(){
        return location;
    }
    public String getImage() {
        return image;
    }
}
