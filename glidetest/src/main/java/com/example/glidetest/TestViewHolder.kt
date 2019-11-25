package com.example.glidetest

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager

class TestViewHolder(
    itemView: View,
    private val mGlide: RequestManager
) :
    RecyclerView.ViewHolder(itemView) {

    fun onBind(url: String) {

        val imageView = itemView.findViewById<ImageView>(R.id.rv_item)
        Log.d("jhlee", "imageView.context : ${imageView.context}")

        mGlide.load(url).into(imageView)
    }

}