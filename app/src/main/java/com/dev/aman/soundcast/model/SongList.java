package com.dev.aman.soundcast.model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class SongList implements Parcelable {
  @SerializedName("results")
  @Expose
  private List<Results> results;

    protected SongList(Parcel in) {
        results = in.createTypedArrayList(Results.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SongList> CREATOR = new Creator<SongList>() {
        @Override
        public SongList createFromParcel(Parcel in) {
            return new SongList(in);
        }

        @Override
        public SongList[] newArray(int size) {
            return new SongList[size];
        }
    };

    public void setResults(List<Results> results){
   this.results=results;
  }
  public List<Results> getResults(){
   return results;
  }
}