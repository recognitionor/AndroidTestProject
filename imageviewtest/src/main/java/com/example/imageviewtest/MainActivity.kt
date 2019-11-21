package com.example.imageviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gsv = GetMaxTextureSizeSurfaceView(this) {
            Log.d("jhlee", it)
        }
        findViewById<LinearLayout>(R.id.frame).addView(gsv, 1, 1)
        val tv = findViewById<Button>(R.id.button)

        val imageView = findViewById<ImageView>(R.id.imageView)
        tv.setOnClickListener {
            tv.text = gsv.mRenderer.mTextureLimit.toString()
        }
        Glide.with(this).load("https://cdn.imweb.me/upload/S201902065c5aebacb5d37/6d3c7ab47eda5.png")
            .apply(RequestOptions().apply {
                override(4000, 5000)
            })
            .thumbnail(0.1f)
            .into(imageView)
    }
}
