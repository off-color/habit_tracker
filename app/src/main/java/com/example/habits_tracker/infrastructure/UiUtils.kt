package com.example.habits_tracker.infrastructure

import android.text.TextUtils
import android.widget.TextView
import com.example.habits_tracker.R

fun isTextFieldsFilled(vararg textViews: TextView): Boolean {
    if (textViews.all { !TextUtils.isEmpty(it.text) }) {
        return true
    }
    return false
}

fun showNoTextProvidedError(vararg textViews: TextView) {
    textViews.first { TextUtils.isEmpty(it.text) }.apply {
        error = context.getString(R.string.shouldBeFilled)
        requestFocus()
    }
}