package com.example.habits_tracker.application

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Habit(val title: String, val description: String,
            val priority: Int, val isGood: Boolean,
            val count: Int, val periodicity: Int) : Parcelable {
}