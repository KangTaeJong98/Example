package com.taetae98.hilt.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SummonerEntity(
    @PrimaryKey
    val name: String = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: ""
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }

    companion object CREATOR : Parcelable.Creator<SummonerEntity> {
        override fun createFromParcel(parcel: Parcel): SummonerEntity {
            return SummonerEntity(parcel)
        }

        override fun newArray(size: Int): Array<SummonerEntity?> {
            return arrayOfNulls(size)
        }
    }
}