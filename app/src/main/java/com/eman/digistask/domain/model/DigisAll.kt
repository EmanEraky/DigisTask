package com.eman.digistask.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DigisAll (

    @SerializedName("RSRP")
    val RSRP: String? = null,

    @SerializedName("RSRQ")
    val RSRQ: String? = null,

    @SerializedName("SINR")
    val SINR: String? = null,

    var time: String? = null,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(RSRP)
        parcel.writeString(RSRQ)
        parcel.writeString(SINR)
        parcel.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DigisAll> {
        override fun createFromParcel(parcel: Parcel): DigisAll {
            return DigisAll(parcel)
        }

        override fun newArray(size: Int): Array<DigisAll?> {
            return arrayOfNulls(size)
        }
    }

}