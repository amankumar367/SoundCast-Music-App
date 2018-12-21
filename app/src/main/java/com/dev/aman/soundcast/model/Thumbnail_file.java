package com.dev.aman.soundcast.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Thumbnail_file{
  @SerializedName("__type")
  @Expose
  private String __type;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("url")
  @Expose
  private String url;
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