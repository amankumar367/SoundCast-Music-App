package com.dev.aman.soundcast.model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Music_file implements Parcelable {
  @SerializedName("__type")
  @Expose
  private String __type;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("url")
  @Expose
  private String url;

    protected Music_file(Parcel in) {
        __type = in.readString();
        name = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(__type);
        dest.writeString(name);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Music_file> CREATOR = new Creator<Music_file>() {
        @Override
        public Music_file createFromParcel(Parcel in) {
            return new Music_file(in);
        }

        @Override
        public Music_file[] newArray(int size) {
            return new Music_file[size];
        }
    };

    public void set__type(String __type){
   this.__type=__type;
  }
  public String get__type(){
   return __type;
  }
  public void setName(String name){
   this.name=name;
  }
  public String getName(){
   return name;
  }
  public void setUrl(String url){
   this.url=url;
  }
  public String getUrl(){
   return url;
  }
}