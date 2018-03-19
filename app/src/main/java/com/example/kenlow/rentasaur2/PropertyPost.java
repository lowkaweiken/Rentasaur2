package com.example.kenlow.rentasaur2;

/**
 * Created by Ken Low on 12-Mar-18.
 */

public class PropertyPost {

    private String user_id;
    private String image_url;
    private String desc;
    private String address_line_1;
    private String address_line_2;
    private String extra_info;



    private String monthly_rental;

    public PropertyPost(){


    }


    public PropertyPost(String user_id, String image_url, String desc, String image_thumb, String address_line_1, String address_line_2,
    String extra_info, String monthly_rental) {
        this.user_id = user_id;
        this.image_url = image_url;
        this.desc = desc;
        this.image_thumb = image_thumb;
        this.address_line_1 = address_line_1;
        this.address_line_2 = address_line_2;
        this.extra_info = extra_info;
        this.monthly_rental = monthly_rental;
    }

    public String image_thumb;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public String getAddress_line_1() {
        return address_line_1;
    }

    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public String getAddress_line_2() {
        return address_line_2;
    }

    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    public String getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(String extra_info) {
        this.extra_info = extra_info;
    }

    public String getMonthly_rental() {
        return monthly_rental;
    }

    public void setMonthly_rental(String monthly_rental) {
        this.monthly_rental = monthly_rental;
    }

}
