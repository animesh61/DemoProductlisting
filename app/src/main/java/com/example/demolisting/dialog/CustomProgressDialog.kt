package com.example.demolisting.dialog

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.demolisting.R


class CustomProgressDialog(var context: Context) {

    var dialog: Dialog? = null

    fun showDialog() {
        dialog = Dialog(context, R.style.CustomDialogTheme)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.animated_progress_dilaog)
        try {
            dialog?.show()
            val main_progress = dialog!!.findViewById<ImageView>(R.id.main_progress)
            Glide.with(context).load(R.drawable.loader).into(main_progress)
        } catch (e: WindowManager.BadTokenException) {
        }
    }

    fun dismissDialog() {
        if (dialog != null) {
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }
        }
    }
}