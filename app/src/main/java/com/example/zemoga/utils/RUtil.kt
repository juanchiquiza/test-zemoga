package com.example.zemoga.utils

import android.annotation.SuppressLint
import com.example.zemoga.LealApplication

@SuppressLint("DefaultLocale")
class RUtil {

    companion object {
        val context = LealApplication.getInstance()

        fun rString(resId: Int): String {
            return LealApplication.getInstance().getString(resId)
        }
    }
}