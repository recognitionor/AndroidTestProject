//package com.example.glidetest
//
//import android.content.Context
//import android.util.Log
//import com.bumptech.glide.Glide
//import com.bumptech.glide.GlideBuilder
//import com.bumptech.glide.Registry
//import com.bumptech.glide.annotation.GlideModule
//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
//import com.bumptech.glide.load.model.GlideUrl
//import com.bumptech.glide.module.AppGlideModule
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import java.io.InputStream
//
//@GlideModule
//class TestGlideModule : AppGlideModule() {
//
//    override fun applyOptions(context: Context, builder: GlideBuilder) {
//        super.applyOptions(context, builder)
//    }
//
//    override fun isManifestParsingEnabled(): Boolean {
//        return super.isManifestParsingEnabled()
//    }
//
//    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
//        super.registerComponents(context, glide, registry)
//        Log.d("jhlee", "registerComponents context : $context")
//        Log.d("jhlee", "registerComponents glide.context : ${glide.context}")
//        Log.d("jhlee", "registerComponents glide : $glide")
//        val httpClient = OkHttpClient.Builder()
//        httpClient.addInterceptor { chain: Interceptor.Chain? ->
//            val time = System.currentTimeMillis()
//            Log.d("jhlee", "1 : ${System.currentTimeMillis() - time} $context ${glide.context}")
//            val request = chain?.request()
//            Log.d("jhlee", "url : ${request?.url()}")
//
//            Log.d("jhlee", "2 : ${System.currentTimeMillis() - time}")
//            Thread.sleep(2000)
//            val response = chain?.proceed(request)
//            Log.d("jhlee", "3 : ${System.currentTimeMillis() - time}")
//            response
//        }
//
//        registry.replace(
//            GlideUrl::class.java, InputStream::class.java,
//            OkHttpUrlLoader.Factory(httpClient.build()))    }
//}