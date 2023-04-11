package com.example.zemoga.utils

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.zemoga.R

class DialogUtils {

    companion object {
        fun showLoadingDialog(context: Context): ProgressDialog {
            val progressDialog = ProgressDialog(context)
            try {
                progressDialog.show()
                if (progressDialog.window != null) {
                    progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
                progressDialog.setContentView(R.layout.progress_bar)
                progressDialog.setCancelable(false)
                progressDialog.setCanceledOnTouchOutside(false)
            } catch (e: Exception) {
            }
            return progressDialog
        }
    }
}