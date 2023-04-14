package com.example.zemoga.views.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.zemoga.R
import com.example.zemoga.databinding.ContentToolbarBinding

import com.example.zemoga.utils.DialogUtils.Companion.showLoadingDialog

abstract class BaseActivity: AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
    }

    fun setupToolbar(binding: ContentToolbarBinding, title: String) {
        binding.titleToolbar.text = title
    }

    fun showLoading() {
        hideLoading()
        progressDialog = showLoadingDialog(this)
    }

    fun hideLoading() {
         if (progressDialog != null && (progressDialog?.isShowing == true)) {
            progressDialog?.cancel()
        }
    }

    fun showDialogOnError(message: String) {
        val builder = AlertDialog.Builder(this)
        val view = View.inflate(this, R.layout.dialog_on_error, null)

        builder.setView(view)
        val alertDialog = builder.create()
        alertDialog.setCancelable(true)

        val titleDialog = view.findViewById<TextView>(R.id.lblMessageOnError)
        titleDialog.text = message
        val imgClose = view.findViewById<ImageView>(R.id.imgClose)
        val btnClose = view.findViewById<ImageView>(R.id.btnClose)
        imgClose.setOnClickListener { alertDialog.dismiss() }
        btnClose.setOnClickListener { alertDialog.dismiss() }
        alertDialog.show()
    }
}