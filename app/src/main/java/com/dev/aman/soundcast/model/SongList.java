package com.dev.aman.soundcast.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class SongList{
  @SerializedName("results")
  @Expose
  private List<Results> results;

  public void setResults(List<Results> results){
   this.results=results;
  }
  public List<Results> getResults(){
   return results;
  }
}