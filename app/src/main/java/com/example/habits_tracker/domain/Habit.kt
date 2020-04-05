package com.example.habits_tracker.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Habit(
    val title: String, val description: String,
    val priority: Int, val isGood: Boolean,
    val count: Int, val periodicity: Int
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}