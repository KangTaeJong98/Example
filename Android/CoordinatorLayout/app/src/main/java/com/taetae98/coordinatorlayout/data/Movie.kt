package com.taetae98.coordinatorlayout.data

import android.os.Parcel
import android.os.Parcelable

data class Movie(
        val id: Long = 0L,
        val imageUrl: String = "",
        val title: String = "",
        val description: String = "",
        var isFavorite: Boolean = false
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
            parcel.readInt() == 1
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(imageUrl)
        dest.writeString(title)
        dest.writeString(description)
        dest.writeInt(if(isFavorite) 1 else 0)
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}