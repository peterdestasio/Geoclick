package com.hanson.geoclick.Model;

/**
 * Created by pierluigidestasio on 2017-07-28.
 */

public class CityItem {

    private String name;
    private byte[] thumbnail;
    private Integer image_id;

    public Integer getImage_id() {
        return image_id;
    }

    public void setImage_id(Integer image_id) {
        this.image_id = image_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public CityItem(String name, byte[] thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public CityItem(String name, byte[] thumbnail, Integer image_id) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.image_id = image_id;
    }
}
