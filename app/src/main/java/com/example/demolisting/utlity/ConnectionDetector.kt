package com.example.demolisting.utlity

import android.content.Context
import android.net.ConnectivityManager

object ConnectionDetector {
    fun isConnectingToInternet(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}