package com.example.demolisting.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.demolisting.R
import com.example.demolisting.utlity.DataPreferences
import org.json.JSONObject

/* Newly added */
class OneButtonAppDialog(var context: Context, val type: String = "", val msg: String = "") {

    var oneButtonDialogListener: OneButtonDialogListener? = null
    private var errorMessage = ""
    private var buttonText = "OK"

    fun setErrorMessage(errorMessage: String) {
        this.errorMessage = errorMessage
    }

    fun setWebViewErrorDialogListener(webViewErrorDialogListener: OneButtonDialogListener?) {
        oneButtonDialogListener = webViewErrorDialogListener
    }

    fun showDialog() {
        val dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_one_button_app)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        val btn_cancel = dialog.findViewById<Button>(R.id.btn_okay)
        val tvDesc = dialog.findViewById<TextView>(R.id.tvDesc)
        val popupLayout = dialog.findViewById<LinearLayout>(R.id.popupLayout)

        val commonMessagezs = DataPreferences(context).getCommonMessage()
        var error_message = ""
        try {
            if (commonMessagezs != "null") {
                val jsonObject = JSONObject(commonMessagezs)
                val _data = jsonObject.optJSONObject("data")!!
                error_message = _data.optString("i18n.fnb.network.lost.msg", "")
            }
        } catch (e: java.lang.Exception) {
        }
        tvDesc.text = error_message

        btn_cancel.text = buttonText
        btn_cancel.setOnClickListener {
            if (oneButtonDialogListener != null) {
                oneButtonDialogListener!!.onCancel()
            }
            dialog.dismiss()
        }
        val lWindowParams = WindowManager.LayoutParams()
        lWindowParams.copyFrom(dialog.window!!.attributes)
        lWindowParams.width =
            WindowManager.LayoutParams.MATCH_PARENT // this is where the magic happens
        lWindowParams.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog.show() // I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?
        dialog.window!!.attributes = lWindowParams

        tvDesc.text = msg
        when (type) {
            "TABLE", "ROOM" -> {
                popupLayout.setBackgroundColor(context.resources.getColor(R.color.popup_back_color1))
                btn_cancel.setBackgroundColor(context.resources.getColor(R.color.popup_color_button1))
            }

            "GYM" -> {
                popupLayout.setBackgroundColor(context.resources.getColor(R.color.popup_back_color2))
                btn_cancel.setBackgroundColor(context.resources.getColor(R.color.popup_color_button2))
            }

            "SPA" -> {
                popupLayout.setBackgroundColor(context.resources.getColor(R.color.popup_back_color3))
                btn_cancel.setBackgroundColor(context.resources.getColor(R.color.popup_color_button3))
            }
        }
    }

    interface OneButtonDialogListener {
        fun onCancel()
    }
}