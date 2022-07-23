package com.example.electricitips

import android.os.Parcel
import android.os.Parcelable

// Parcelable constructor and override methods are needed to make object Appliance passable to other activities/fragments
class Appliance(var imgId: Int, var name: String, var type: String, var rating: String, var duration: String, var frequency: String) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imgId)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(rating)
        parcel.writeString(duration)
        parcel.writeString(frequency)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Appliance> {
        override fun createFromParcel(parcel: Parcel): Appliance {
            return Appliance(parcel)
        }

        override fun newArray(size: Int): Array<Appliance?> {
            return arrayOfNulls(size)
        }
    }
}