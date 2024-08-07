package com.example.e_storegenesis.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView


object Extensions {

    fun TextView.setColoredText(text: String) {
        val spannableString = SpannableString(text)
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#FF9800")),
            6,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this.text = spannableString
    }

    @SuppressLint("DefaultLocale")
    fun Double.roundValues(): Double {
        return String.format("%.2f", this).toDouble()
    }
}