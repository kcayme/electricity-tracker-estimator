package com.example.electricitips

import android.os.Parcel
import android.os.Parcelable

// Parcelable constructor and override methods are needed to make object Appliance passable to other activities/fragments
class Appliance(var imgId: Int, var name: String, var modelCode: String, var type: String, var rating: Float, var duration: Float, var frequency: String) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imgId)
        parcel.writeString(name)
        parcel.writeString(modelCode)
        parcel.writeString(type)
        parcel.writeFloat(rating)
        parcel.writeFloat(duration)
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