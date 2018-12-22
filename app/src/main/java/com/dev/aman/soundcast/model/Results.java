package com.dev.aman.soundcast.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Results implements Parcelable {
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("thumbnail_file")
    @Expose
    private Thumbnail_file thumbnail_file;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("music_file")
    @Expose
    private Music_file music_file;
    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    protected Results(Parcel in) {
        createdAt = in.readString();
        thumbnail = in.readString();
        link = in.readString();
        title = in.readString();
        objectId = in.readString();
        updatedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdAt);
        dest.writeString(thumbnail);
        dest.writeString(link);
        dest.writeString(title);
        dest.writeString(objectId);
        dest.writeString(updatedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setThumbnail_file(Thumbnail_file thumbnail_file) {
        this.thumbnail_file = thumbnail_file;
    }

    public Thumbnail_file getThumbnail_file() {
        return thumbnail_file;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setMusic_file(Music_file music_file) {
        this.music_file = music_file;
    }

    public Music_file getMusic_file() {
        return music_file;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}