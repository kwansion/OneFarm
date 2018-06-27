package com.example.rog.efarmer1;

public class graphAdapterList {
    private String price,price2;
    private String inc;
    private String pName;
    private String d1,d2,d3,d4,p1,p2,p3,p4;
    public graphAdapterList(String price, String price2, String pName, String d1,String d2,String d3,String d4,String p1,String p2,String p3,String p4)
    {
        this.price = price;
        this.price2=price2;
        this.pName = pName;
        this.d1=d1;
        this.d2=d2;
        this.d3=d3;
        this.d4=d4;
        this.p1=p1;
        this.p2=p2;
        this.p3=p3;
        this.p4=p4;
    }

    public String getPrice() {
        return price;
    }
    public String getPrice2() {
        return price2;
    }
    public String getName() {
        return pName;
    }
    public String getp1(){return p1;}
    public String getp2(){return p2;}
    public String getp3(){return p3;}
    public String getp4(){return p4;}
    public String getd1(){return d1;}
    public String getd2(){return d2;}
    public String getd3(){return d3;}
    public String getd4(){return d4;}
}
