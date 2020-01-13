package com.example.glidetest

import android.app.Activity
import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class MyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        (context as Activity).let {
            Log.d("jhlee", "$it")
        }
        super.applyOptions(context, builder)
        Log.d("jhlee", "applyOptions $context")
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        (context as Activity).let {
            Log.d("jhlee", "$it")
        }
        Log.d("jhlee", "registerComponents $context")

    }
}