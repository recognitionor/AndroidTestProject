package com.example.glidetest

import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.InputStream

interface IGlideManager {

    var mGlide: Glide
    var mRequestManager: RequestManager

    fun createGlide(activity: AppCompatActivity) {
        Log.d("jhlee", "$activity")
        mGlide = GlideApp.get(activity)
        mRequestManager = GlideApp.with(activity)
        replaceRegister(activity)
    }

    fun replaceRegister(activity: AppCompatActivity) {
        val httpClient = OkHttpClient.Builder()
        try {
            httpClient.addInterceptor { chain: Interceptor.Chain? ->
                val time = System.currentTimeMillis()
                val request = chain?.request()
                Log.d("jhleetest", "${activity.componentName} :  url : ${request?.url()}")
                Thread.sleep(1000)
                Log.d(
                    "jhleetest",
                    "2 : ${System.currentTimeMillis() - time} url : ${request?.url()}"
                )
                val response = chain?.proceed(request)
                Log.d(
                    "jhleetest",
                    "3 : ${System.currentTimeMillis() - time}url : ${request?.url()}"
                )
                response
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mGlide.registry.replace(
            GlideUrl::class.java, InputStream::class.java,
            OkHttpUrlLoader.Factory(httpClient.build())
        )
    }
}