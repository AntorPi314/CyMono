package com.antor.cymono.RecyclerView;

public class ItemC1 {

    String img_profile;
    String txt_name;
    String txt_info;
    String tet_post;
    String img1;

    public ItemC1() {
    }

    public ItemC1(String img_profile, String txt_name, String txt_info, String tet_post, String img1) {
        this.img_profile = img_profile;
        this.txt_name = txt_name;
        this.txt_info = txt_info;
        this.tet_post = tet_post;
        this.img1 = img1;
    }

    public String getImg_profile() {
        return img_profile;
    }

    public String getTxt_name() {
        return txt_name;
    }

    public String getTxt_info() {
        return txt_info;
    }

    public String getTet_post() {
        return tet_post;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg_profile(String img_profile) {
        this.img_profile = img_profile;
    }

    public void setTxt_name(String txt_name) {
        this.txt_name = txt_name;
    }

    public void setTxt_info(String txt_info) {
        this.txt_info = txt_info;
    }

    public void setTet_post(String tet_post) {
        this.tet_post = tet_post;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }
}
