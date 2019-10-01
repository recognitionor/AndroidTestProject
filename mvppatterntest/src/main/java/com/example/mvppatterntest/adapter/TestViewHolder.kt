package com.example.mvppatterntest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvppatterntest.R

class TestViewHolder(context: Context, parent: ViewGroup?, private val listenerFunc: ((Int) -> Unit)?)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(
    R.layout.view_holder, parent, false))