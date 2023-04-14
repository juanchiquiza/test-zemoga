package com.example.zemoga.utils

import android.annotation.SuppressLint
import com.example.zemoga.ZemogaApplication

@SuppressLint("DefaultLocale")
class RUtil {

    companion object {
        val context = ZemogaApplication.getInstance()

        fun rString(resId: Int): String {
            return ZemogaApplication.getInstance().getString(resId)
        }
    }
}