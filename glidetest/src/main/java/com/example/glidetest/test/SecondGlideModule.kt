//package com.example.glidetest.test
//
//import android.content.Context
//import android.util.Log
//import com.bumptech.glide.Glide
//import com.bumptech.glide.GlideBuilder
//import com.bumptech.glide.Registry
//import com.bumptech.glide.annotation.GlideModule
//import com.bumptech.glide.module.AppGlideModule
//
//@GlideModule
//class SecondGlideModule : AppGlideModule() {
//    override fun applyOptions(context: Context, builder: GlideBuilder) {
//        super.applyOptions(context, builder)
//        Log.d("jhlee", "applyOptions $context")
//    }
//
//    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
//        super.registerComponents(context, glide, registry)
//        Log.d("jhlee", "registerComponents $context")
//
//    }
//}