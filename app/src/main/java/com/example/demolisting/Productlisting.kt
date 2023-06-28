package com.example.demolisting

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demolisting.adapter.ProductListingAdapter
import com.example.demolisting.dialog.LoadingDialog
import com.example.demolisting.dialog.OneButtonAppDialog
import com.example.demolisting.model.Productmodel
import com.example.demolisting.repository.Apiclient
import com.example.demolisting.repository.OkHttpHelper
import com.example.demolisting.utlity.ConnectionDetector
import com.example.demolisting.utlity.ConstantFood
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class Productlisting:AppCompatActivity() {
    lateinit var rv_product_list:RecyclerView
    lateinit var productListingAdapter: ProductListingAdapter
    private val progressDialog: LoadingDialog by lazy { LoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productlisting)
        rv_product_list=findViewById(R.id.rv_product_list)
        getproductdetails()
    }

    private fun getproductdetails() {

        if (!ConnectionDetector.isConnectingToInternet(this)) {
            val errorDialog = OneButtonAppDialog(this)
            errorDialog.setErrorMessage(ConstantFood.ALERT_MSG_ERROR_WEBVIEW)
            errorDialog.showDialog()
            return
        }
        progressDialog.showDialog()
        val url = Apiclient.BASE_URL +"products?limit=10"
        OkHttpHelper.GETDATA(url,
            object : OkHttpHelper.OKHttpHelperResponse {
                @SuppressLint("LongLogTag", "SetTextI18n")
                override fun onResponse(s: String) {
                    CoroutineScope(Dispatchers.Main).launch {
                        progressDialog.hideDialog()

                        try {
                            if (s != null) {
                                val JSONArrayMain = JSONArray(s.toString())
                                val gson = Gson()
                                val typeToken2: TypeToken<ArrayList<Productmodel>> =
                                    object : TypeToken<ArrayList<Productmodel>>() {}
                                val itemlist: ArrayList<Productmodel> = gson.fromJson(JSONArrayMain.toString(), typeToken2.type)
                                productListingAdapter = ProductListingAdapter(this@Productlisting,
                                    itemlist)
                                val layoutManager = LinearLayoutManager(this@Productlisting)
                                rv_product_list.layoutManager = layoutManager
                                rv_product_list.layoutManager = LinearLayoutManager(this@Productlisting, LinearLayoutManager.VERTICAL, false)
                                rv_product_list.itemAnimator = DefaultItemAnimator()
                                rv_product_list.adapter = productListingAdapter



                            }

                        } catch (e: Exception) {
                        }
                    }

                }

            })
    }
}