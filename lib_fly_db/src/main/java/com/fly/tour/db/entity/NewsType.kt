package com.fly.tour.db.entity

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

/**
 * Description: <NewsType><br>
 * Author:      mxdl<br>
 * Date:        2019/5/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class NewsType : Parcelable {
    var id: Int = 0
    var typename: String? = null
    var addtime: String? = null

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.id)
        dest.writeString(this.typename)
        dest.writeString(this.addtime)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.id = `in`.readInt()
        this.typename = `in`.readString()
        this.addtime = `in`.readString()
    }

    override fun toString(): String {
        return "NewsType{id=$id, typename='$typename', addtime='$addtime'}"
    }

    companion object {

        @SuppressLint("ParcelCreator")
        val CREATOR: Parcelable.Creator<NewsType> = object : Parcelable.Creator<NewsType> {
            override fun createFromParcel(source: Parcel): NewsType {
                return NewsType(source)
            }

            override fun newArray(size: Int): Array<NewsType?> {
                return arrayOfNulls(size)
            }
        }
    }
}
