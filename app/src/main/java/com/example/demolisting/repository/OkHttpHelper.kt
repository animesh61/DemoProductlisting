package com.example.demolisting.repository

import android.content.Context
import com.example.demolisting.dialog.CustomProgressDialog
import com.example.demolisting.dialog.LoadingDialog
import okhttp3.*
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit


object OkHttpHelper {

    val JSON = MediaType.parse("application/json; charset=utf-8")

    var client = OkHttpClient()
    // var progressDialog: CustomProgressDialog? = null
    lateinit var tokenExpireListeners: TokenExpireListeners

    //private var session_client: OkHttpClient? = null

    /*val certPinner: CertificatePinner = CertificatePinner.Builder()
        .add("oneapp.qa.smartler.in",
            "sha256/gJ4gdx1kXKlcVTLQFYleHn1cIi52tfBnDGtwJyP3qAg=")
        .build()

    var client = getUnsafeOkHttpClient()!!*/

    /* val hostname = "oneapp.qa.smartler.in"
     val certificatePinner = CertificatePinner.Builder()
             .add(hostname, "sha256/xZYBv8Poz+YPSdC/ufAuXMqIDfscwGjf4xWSdutFPFQ=")
             .add(hostname, "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=")
             .add(hostname, "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M=")
             .build()
     val client = OkHttpClient.Builder().certificatePinner(certificatePinner).build()*/
    //val client = getUnsafeOkHttpClient()!!

    fun GETInitialCall(url: String, context: Context, progressDialogNeedToShow: Boolean,
                       tokenExpireListeners: TokenExpireListeners, okHttpHelperResponse: OKHttpHelperResponse) {
        this.tokenExpireListeners = tokenExpireListeners
        //Log.d("url", url)
        var progressDialog: CustomProgressDialog? = null
        if (progressDialogNeedToShow) {
            progressDialog = CustomProgressDialog(context)
            progressDialog.showDialog()
        }
        val request = Request.Builder()
            .url(url)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                handleOnResponse(call, response, okHttpHelperResponse)

                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }

            }
        })
    }

    fun GET(url: String, context: Context, progressDialogNeedToShow: Boolean,
            okHttpHelperResponse: OKHttpHelperResponse) {
        //Log.d("url", url)
        var progressDialog: CustomProgressDialog? = null
        if (progressDialogNeedToShow) {
            progressDialog = CustomProgressDialog(context)
            progressDialog?.showDialog()
        }
        val request = Request.Builder()
            .url(url)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                handleOnResponse(call, response, okHttpHelperResponse)

                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }

            }
        })
    }

    /**
     *  Get Call Non secure
     */
    fun GETNonSecure(url: String, context: Context, progressDialogNeedToShow: Boolean, okHttpHelperResponse: OKHttpHelperResponseNonSec) {
        //Log.d("url", url)
        var progressDialog: CustomProgressDialog? = null
        if (progressDialogNeedToShow) {
            progressDialog = CustomProgressDialog(context)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                progressDialog?.showDialog()
            }
        }
        val request = Request.Builder()
            .url(url)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("", "")
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
                handleOnResponseNonSecure(call, response, okHttpHelperResponse)
            }
        })

    }

    /**
     *  Get Call with basic auth
     */
    fun GET(url: String, context: Context, progressDialogNeedToShow: Boolean, username: String, password: String, okHttpHelperResponse: OKHttpHelperResponse) {
        //Log.d("url", url)
        var progressDialog: CustomProgressDialog? = null
        if (progressDialogNeedToShow) {
            progressDialog = CustomProgressDialog(context)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                progressDialog?.showDialog()
            }
        }
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", getAuthBesic(username, password))
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                handleOnResponse(call, response, okHttpHelperResponse)

                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
            }
        })

    }

    /**
     *  Get Call with basic auth
     */
    fun GETNonDiloag(url: String, token: String,
                     okHttpHelperResponse: OKHttpHelperResponse) {

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", token)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
            }
            override fun onResponse(call: Call, response: Response) {
                handleOnResponse(call, response, okHttpHelperResponse)
            }
        })
    }
    fun GET(url: String, context: Context, progressDialogNeedToShow: Boolean, token: String,
            okHttpHelperResponse: OKHttpHelperResponse) {
        //Log.d("url", url)
        var progressDialog: CustomProgressDialog? = null
        if (progressDialogNeedToShow) {
            progressDialog = CustomProgressDialog(context)
            progressDialog.showDialog()
        }
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", token)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
                if (progressDialog != null) {
                    progressDialog.dialog?.dismiss()
                }
                //e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                handleOnResponse(call, response, okHttpHelperResponse)
                if (progressDialog != null) {
                    progressDialog.dialog?.dismiss()
                }
            }
        })
    }
    fun GETDATA(url: String, okHttpHelperResponse: OKHttpHelperResponse) {

        //this.tokenExpireListeners = tokenExpireListeners
        val request = Request.Builder()
            .url(url)
           // .addHeader("Authorization", token)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
            }

            override fun onResponse(call: Call, response: Response) {
                handleOnResponse(call, response, okHttpHelperResponse)
            }
        })
    }



//    fun GETTest(url: String, context: Context, tokenExpireListeners: TokenExpireListeners, progressDialogNeedToShow: Boolean, token: String, okHttpHelperResponse: OKHttpHelperResponse) {
//        //Log.d("url", url)
//        this.tokenExpireListeners = tokenExpireListeners
//        var progressDialog: CustomProgressDialog? = null
//        if (progressDialogNeedToShow) {
//            progressDialog = CustomProgressDialog(context)
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
//                progressDialog?.showDialog()
//            }
//        }
//        val request = Request.Builder()
//                .url(url)
//                .addHeader("Authorization", token)
//                .build()
//
//        client = OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build()
//        val call = client.newCall(request)
//
//        call.enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                okHttpHelperResponse.onResponse("")
//               // Log.d("stopDiloag", "its here_out_err")
//                if (progressDialog != null) {
//                    //Log.d("stopDiloag", "its here_in_err")
//                    progressDialog?.dismissDialog()
//                }
//                // e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//
//                handleOnResponse(call, response, okHttpHelperResponse)
//               // Log.d("stopDiloag", "its here_out_res")
//                if (progressDialog != null) {
//                //    Log.d("stopDiloag", "its here_in_res")
//                    progressDialog?.dismissDialog()
//                }
//            }
//        })
//    }
//
//    fun GETWithString(url: String, json: String, context: Context, progressDialogNeedToShow: Boolean, token: String, okHttpHelperResponse: OKHttpHelperResponse) {
//        //Log.d("url", url)
//        var progressDialog: CustomProgressDialog? = null
//        if (progressDialogNeedToShow) {
//            progressDialog = CustomProgressDialog(context)
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
//                progressDialog?.showDialog()
//            }
//        }
//        val request = Request.Builder()
//                .url(url)
//                .addHeader("Authorization", token)
//                .build()
//
//        client = OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build()
//        val call = client.newCall(request)
//
//        call.enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                okHttpHelperResponse.onResponse("")
//                if (progressDialog != null) {
//                    progressDialog?.dialog?.dismiss()
//                }
//                //e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//
////                var strResponse : String = response.body()!!.string()
////                Log.d("response", strResponse)
////                okHttpHelperResponse.onResponse(strResponse)
//                /*if(response.isSuccessful) {
//                    var strResponse: String = response.body()!!.string()
//                    Log.d("response", strResponse)
//                    okHttpHelperResponse.onResponse(strResponse)
//                }else{
//                    var strResponse: String = response.body()!!.string()
//                    Log.d("response", strResponse)
//                    Log.d("response", response.code().toString())
//                    okHttpHelperResponse.onResponse(response.code().toString())
//                }*/
//
//                handleOnResponse(call, response, okHttpHelperResponse)
//                if (progressDialog != null) {
//                    progressDialog?.dialog?.dismiss()
//                }
//
//            }
//        })
//
//    }

    /**
     *  Post call with params
     */
    fun POST(url: String, parameters: HashMap<String, String>, context: Context, progressDialogNeedToShow: Boolean, okHttpHelperResponse: OKHttpHelperResponse) {
        //Log.d("url", url)
        var progressDialog: CustomProgressDialog? = null
        if (progressDialogNeedToShow) {
            progressDialog = CustomProgressDialog(context)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                progressDialog?.showDialog()
            }
        }

        val builder = FormBody.Builder()
        val it = parameters.entries.iterator()
        while (it.hasNext()) {
            val pair = it.next() as Map.Entry<*, *>
            builder.add(pair.key.toString(), pair.value.toString())
        }

        val formBody = builder.build()
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
                //e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                val strResponse: String = response.body()!!.string()
                //Log.d("response", strResponse)
                okHttpHelperResponse.onResponse(strResponse)

                //handleOnResponse(call, response,  okHttpHelperResponse)
                if (response.isSuccessful) {
                    val strResponse: String = response.body()!!.string()
                    //Log.d("", strResponse)
                    okHttpHelperResponse.onResponse(strResponse)
                } else {
                    val strResponse: String = ""
                    //Log.d("response", strResponse)
                    okHttpHelperResponse.onResponse(strResponse)
                }
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
            }
        })

    }

    /**
     *  Post call with Params with besic auth
     */
    fun POST(url: String, parameters: HashMap<String, String>, context: Context, progressDialogNeedToShow: Boolean, username: String, password: String, okHttpHelperResponse: OKHttpHelperResponse) {
        //Log.d("url", url)
        var progressDialog: CustomProgressDialog? = null
        if (progressDialogNeedToShow) {
            progressDialog = CustomProgressDialog(context)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                progressDialog?.showDialog()
            }
        }

        val builder = FormBody.Builder()
        val it = parameters.entries.iterator()
        while (it.hasNext()) {
            val pair = it.next() as Map.Entry<*, *>
            builder.add(pair.key.toString(), pair.value.toString())
        }

        val formBody = builder.build()
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", getAuthBesic(username, password))
            .post(formBody)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                /*  var strResponse : String = response.body()!!.string()
                  Log.d("response", strResponse)
                  okHttpHelperResponse.onResponse(strResponse)*/
                handleOnResponse(call, response, okHttpHelperResponse)
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }

            }
        })

    }

    /**
     *  Post call with Params with auth token
     */
    fun POST(url: String, parameters: HashMap<String, String>, context: Context, progressDialogNeedToShow: Boolean, token: String, okHttpHelperResponse: OKHttpHelperResponse) {
        //Log.d("url", url)
        var progressDialog: CustomProgressDialog? = null
        if (progressDialogNeedToShow) {
            progressDialog = CustomProgressDialog(context)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                progressDialog?.showDialog()
            }
        }

        val builder = FormBody.Builder()
        val it = parameters.entries.iterator()
        while (it.hasNext()) {
            val pair = it.next() as Map.Entry<*, *>
            builder.add(pair.key.toString(), pair.value.toString())
        }

        val formBody = builder.build()
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", token)
            .post(formBody)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
                //e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                handleOnResponse(call, response, okHttpHelperResponse)
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
            }
        })

    }

    /**
     *  Post Call with Json Body for login only
     */
    fun POST(url: String, json: String, context: Context, progressDialogNeedToShow: Boolean, okHttpHelperResponse: OKHttpHelperResponse) {
        //Log.d("url", url)
        var progressDialog: CustomProgressDialog? = null
        if (progressDialogNeedToShow) {
            progressDialog = CustomProgressDialog(context)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                progressDialog?.showDialog()
            }
        }
        val body = RequestBody.create(JSON, json) // new
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
                //e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                /* var strResponse : String = response.body()!!.string()
                 Log.d("response", strResponse)
                 okHttpHelperResponse.onResponse(strResponse)*/
                //handleOnResponse(call, response,  okHttpHelperResponse)
                if (response.isSuccessful) {
                    val strResponse: String = response.body()!!.string()
                    //Log.d("response", strResponse)
                    okHttpHelperResponse.onResponse(strResponse)
                } else {
                    val strResponse = ""
                    //Log.d("response", strResponse)
                    okHttpHelperResponse.onResponse(strResponse)
                }
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
            }
        })
    }

    /**
     *  Post Call with Json Body for non normal json (non secure)
     */

    fun POSTNonSecure(url: String, json: String, context: Context, progressDialogNeedToShow: Boolean, okHttpHelperResponse: OKHttpHelperResponseNonSec) {
        //Log.d("url", url)
        var progressDialog: LoadingDialog? = null
        if (progressDialogNeedToShow) {
            progressDialog = LoadingDialog(context)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                progressDialog?.showDialog()
            }
        }
        val body = RequestBody.create(JSON, json) // new
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("", "")
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
                //e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                handleOnResponseNonSecure(call, response, okHttpHelperResponse)

                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
            }
        })

    }

    /**
     *  Post Call with Json Body with besic auth
     */
    fun POST(url: String, json: String, context: Context, progressDialogNeedToShow: Boolean, username: String, password: String, okHttpHelperResponse: OKHttpHelperResponse) {
        //Log.d("url", url)
        var progressDialog: CustomProgressDialog? = null
        if (progressDialogNeedToShow) {
            progressDialog = CustomProgressDialog(context)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                progressDialog?.showDialog()
            }
        }
        val body = RequestBody.create(JSON, json) // new
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", getAuthBesic(username, password))
            .post(body)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
                //e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                /* var strResponse : String = response.body()!!.string()
                 Log.d("response", strResponse)
                 okHttpHelperResponse.onResponse(strResponse)*/

                handleOnResponse(call, response, okHttpHelperResponse)
                if (progressDialog != null) {
                    progressDialog?.dialog?.dismiss()
                }
            }
        })
    }

    /**
     *  Post Call with Json Body with auth token
     */
    fun POSTNonDiloag(url: String, json: String, token: String, okHttpHelperResponse: OKHttpHelperResponse) {
        val body = RequestBody.create(JSON, json) // new
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", token)
            .post(body)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
            }
            override fun onResponse(call: Call, response: Response) {
                handleOnResponse(call, response, okHttpHelperResponse)
            }
        })
    }




    fun POST(url: String, json: String, context: Context, progressDialogNeedToShow: Boolean, token: String, okHttpHelperResponse: OKHttpHelperResponse) {
        //session_client = getUnsafeOkHttpClient()
        //Log.d("url", url)
        var progressDialog: CustomProgressDialog? = null
        if (progressDialogNeedToShow) {
            progressDialog = CustomProgressDialog(context)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                progressDialog.showDialog()
            }
        }
        val body = RequestBody.create(JSON, json) // new
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", token)
            .post(body)
            .build()

        client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpHelperResponse.onResponse("")
                if (progressDialog != null) {
                    progressDialog.dialog?.dismiss()
                }
                //e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                handleOnResponse(call, response, okHttpHelperResponse)
                if (progressDialog != null) {
                    progressDialog.dialog?.dismiss()
                }
            }
        })
    }

    private fun getAuthBesic(username: String, password: String): String {
        val auth: String = "$username:$password"
        val encodedAuth: ByteArray =
            org.apache.commons.codec.binary.Base64.encodeBase64(auth.toByteArray(Charset.forName("UTF-8")))
        //Log.d("Authorization", "Basic " + String(encodedAuth))
        return "Basic " + String(encodedAuth)
    }

    private fun handleOnResponse(call: Call, response: Response, okHttpHelperResponse: OKHttpHelperResponse) {
        //Log.d("Responcode", "Responcode " + response.code().toString())
        if (response.code() == 401) {
            tokenExpireListeners.onTokenExpire()
        } else {
            if (response.isSuccessful) {
                val strResponse: String = response.body()!!.string()
                //Log.d("response", strResponse)
                okHttpHelperResponse.onResponse(strResponse)
            } else {
                val strResponse: String = ""
                //Log.d("response", strResponse)
                okHttpHelperResponse.onResponse(strResponse)
            }
        }
    }

    private fun handleOnResponseNonSecure(call: Call, response: Response, okHttpHelperResponseNonSec: OKHttpHelperResponseNonSec) {
        // Log.d("ResponcodeForNonSecure", "ResponcodeForNonSecure " + response.code().toString())
        if (response.code().toString() == "401" || response.code().toString() == "400" || response.code().toString() == "408" || response.code().toString() == "423") {
            //Log.d("ResponcodeForNonSecure", "ResponcodeForNonSecure " + response.code().toString())
            okHttpHelperResponseNonSec.onResponse(response.body()!!.string(), response.code().toString())
        } else if (response.code() == 200) {
            if (response.isSuccessful) {
                val strResponse: String = response.body()!!.string()
                //Log.d("response", strResponse)
                //Log.d("responseSuccessful", strResponse + "::" + response.code().toString())
                okHttpHelperResponseNonSec.onResponse(strResponse, response.code().toString())
            } else {
                val strResponse = ""
                //Log.d("responseNotSuccessful", strResponse + "::" + response.code().toString())
                okHttpHelperResponseNonSec.onResponse(strResponse, response.code().toString())
            }
        } else {
            //Log.d("responseNotSuccessful", response.code().toString())
            //  okHttpHelperResponseNonSec.onResponse("Server error, please try again later", "")
        }
    }

    interface OKHttpHelperResponse {
        fun onResponse(s: String)
    }

    interface OKHttpHelperResponseNonSec {
        fun onResponse(s: String, code: String)
    }

    /*  private fun getUnsafeOkHttpClient(): OkHttpClient? {
          return try {
              // Create a trust manager that does not validate certificate chains
              val trustAllCerts =
                      arrayOf<TrustManager>(
                              object : X509TrustManager {
                                  @Throws(CertificateException::class)
                                  override fun checkClientTrusted(
                                          chain: Array<X509Certificate>,
                                          authType: String
                                  ) {
                                  }

                                  @Throws(CertificateException::class)
                                  override fun checkServerTrusted(
                                          chain: Array<X509Certificate>,
                                          authType: String
                                  ) {
                                  }

                                  override fun getAcceptedIssuers(): Array<X509Certificate> {
                                      return arrayOf()
                                  }
                              }
                      )

              // Install the all-trusting trust manager
              val sslContext = SSLContext.getInstance("SSL")
              sslContext.init(null, trustAllCerts, SecureRandom())

              // Create an ssl socket factory with our all-trusting manager
              val sslSocketFactory = sslContext.socketFactory
              val builder = OkHttpClient.Builder()
              builder.sslSocketFactory(
                      sslSocketFactory,
                      trustAllCerts[0] as X509TrustManager
              )
              builder.hostnameVerifier { hostname, session -> true }
              builder.build()
          } catch (e: Exception) {
              throw RuntimeException(e)
          }
      }*/

    /*@SuppressLint("TrustAllX509TrustManager")
    private fun getUnsafeOkHttpClient(): OkHttpClient? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                    object : X509TrustManager {

                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.certificatePinner(certificatePinner)
            //builder.addInterceptor(SessionOkHttpInterceptor())
            builder.hostnameVerifier { hostname: String?, session: SSLSession? -> true }
            builder.build()
        } catch (e: java.lang.Exception) {
            throw java.lang.RuntimeException(e)
        }
    }*/

    fun stopDiloag() {

    }
}