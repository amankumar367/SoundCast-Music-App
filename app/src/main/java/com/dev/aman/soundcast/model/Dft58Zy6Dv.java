package com.dev.aman.soundcast.model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Dft58Zy6Dv implements Parcelable {
  @SerializedName("read")
  @Expose
  private Boolean read;
  @SerializedName("write")
  @Expose
  private Boolean write;

    protected Dft58Zy6Dv(Parcel in) {
        byte tmpRead = in.readByte();
        read = tmpRead == 0 ? null : tmpRead == 1;
        byte tmpWrite = in.readByte();
        write = tmpWrite == 0 ? null : tmpWrite == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (read == null ? 0 : read ? 1 : 2));
        dest.writeByte((byte) (write == null ? 0 : write ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Dft58Zy6Dv> CREATOR = new Creator<Dft58Zy6Dv>() {
        @Override
        public Dft58Zy6Dv createFromParcel(Parcel in) {
            return new Dft58Zy6Dv(in);
        }

        @Override
        public Dft58Zy6Dv[] newArray(int size) {
            return new Dft58Zy6Dv[size];
        }
    };

    public void setRead(Boolean read){
   this.read=read;
  }
  public Boolean getRead(){
   return read;
  }
  public void setWrite(Boolean write){
   this.write=write;
  }
  public Boolean getWrite(){
   return write;
  }
}