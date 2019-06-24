package com.fly.tour.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description: <NewsType><br>
 * Author:      mxdl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class NewsType implements Parcelable {
    public int id;
    public String typename;
    public String addtime;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.typename);
        dest.writeString(this.addtime);
    }

    public NewsType() {
    }

    protected NewsType(Parcel in) {
        this.id = in.readInt();
        this.typename = in.readString();
        this.addtime = in.readString();
    }

    public static final Creator<NewsType> CREATOR = new Creator<NewsType>() {
        @Override
        public NewsType createFromParcel(Parcel source) {
            return new NewsType(source);
        }

        @Override
        public NewsType[] newArray(int size) {
            return new NewsType[size];
        }
    };

    @Override
    public String toString() {
        return "NewsType{" + "id=" + id + ", typename='" + typename + '\'' + ", addtime='" + addtime + '\'' + '}';
    }
}
