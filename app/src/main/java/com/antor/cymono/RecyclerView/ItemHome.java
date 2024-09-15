package com.antor.cymono.RecyclerView;

public class ItemHome {
    String channelPath;
    String channelName;
    String channelDes;
    String channelTime;
    String imageURL;

    public ItemHome(String channelPath, String channelName, String channelDes, String channelTime, String imageURL) {
        this.channelPath = channelPath;
        this.channelName = channelName;
        this.channelDes = channelDes;
        this.channelTime = channelTime;
        this.imageURL = imageURL;
    }

    public String getChannelPath() {
        return channelPath;
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

    public void setChannelPath(String channelPath) {
        this.channelPath = channelPath;
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
