package com.example.demolisting.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.demolisting.R


class LoadingDialog(var context: Context) {

    var dialog: Dialog = Dialog(context)

    fun showDialog() {
        dialog.show()
    }

    fun hideDialog() {
        dialog.dismiss()
    }

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.progress_layout)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
    }
}