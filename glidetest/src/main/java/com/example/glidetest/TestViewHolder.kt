package com.example.glidetest

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager

class TestViewHolder(itemView: View, context: Context, private val mGlide: RequestManager) :
    RecyclerView.ViewHolder(itemView) {

    fun onBind(url: String) {

        val imageView = itemView.findViewById<ImageView>(R.id.rv_item)
        mGlide.load(url).into(imageView)
    }

}