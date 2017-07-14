package com.hanson.geoclick.Model;

/**
 * Created by lily on 2017-06-19.
 */

public class PictureItem {
    private int id;
    private String country;
    private String city;
    private String latitude;
    private String longitude;
    private byte[] thumbnail;
    private String mainImg;

    public int get_id(){ return id; }
    public String get_country(){return country;}
    public String get_city(){return city;}
    public String get_latitude(){return latitude;}
    public String get_longitude(){return longitude;}
    public byte[] get_thumbnail(){return thumbnail;}
    public String get_mainImg(){return mainImg;}

    public PictureItem(int id, String country, String city, String lat, String lon,
                     byte[] thumbnail, String mainImg) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.latitude = lat;
        this.longitude = lon;
        this.thumbnail = thumbnail;
        this.mainImg = mainImg;
    }
}
