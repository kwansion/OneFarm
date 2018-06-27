package com.example.rog.efarmer1;

public class adapterListDiscover {
    private int id;
    private String date;
    private String image;
    private String desc;
    private String pImg;
    private String pName;
    private String id1;
    private String noCmt1;
    private int noCmt;
    public adapterListDiscover(String date, String image, String pImg, String pName, String desc, int id, int noCmt) {
        this.date = date;
        this.image = image;
        this.pImg = pImg;
        this.pName = pName;
        this.id = id;
        this.desc = desc;
        this.noCmt = noCmt;
        id1 = Integer.toString(id);
        noCmt1 = Integer.toString(noCmt);
    }

    public String getId() {
        return id1;
    }
    public String getpImg(){
        return pImg;
    }
    public String getDate() {
        return date;
    }
    public String getpName(){
        return pName;
    }
    public String getDesc(){
        return desc;
    }
    public String getImage() {
        return image;
    }
    public String getNoCmt() { return noCmt1;}
}
