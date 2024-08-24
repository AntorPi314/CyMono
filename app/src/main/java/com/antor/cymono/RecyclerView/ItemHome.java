package com.antor.cymono.RecyclerView;

public class ItemHome {

    String name;
    String email;
    int image;

    String channelName;
    String channelDes;
    String channelTime;
    String imageURL;

    public ItemHome(String channelName, String channelDes, String channelTime, String imageURL) {
        this.channelName = channelName;
        this.channelDes = channelDes;
        this.channelTime = channelTime;
        this.imageURL = imageURL;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getChannelDes() {
        return channelDes;
    }

    public String getChannelTime() {
        return channelTime;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setChannelDes(String channelDes) {
        this.channelDes = channelDes;
    }

    public void setChannelTime(String channelTime) {
        this.channelTime = channelTime;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
