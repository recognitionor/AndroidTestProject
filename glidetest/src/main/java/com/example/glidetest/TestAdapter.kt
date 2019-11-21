package com.example.glidetest

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import android.view.LayoutInflater



class TestAdapter(private var glide: RequestManager) :
    RecyclerView.Adapter<TestViewHolder>() {

    companion object {
        val URL_LIST: ArrayList<String> = arrayListOf(
            "https://cdntest.amondz.com/product/1/resize/mainImg/PSI_1.jpeg",
            "https://cdntest.amondz.com/product/1/resize/subImg/PSI_2.jpeg",
            "https://cdntest.amondz.com/product/1/resize/subImg/PSI_3.jpeg",
            "https://cdntest.amondz.com/product/1/resize/detailImg/PSI_4.jpeg",
            "https://cdntest.amondz.com/product/1/resize/detailImg/PSI_5.jpeg",
            "https://cdntest.amondz.com/product/2/resize/mainImg/PSI_6.jpeg",
            "https://cdntest.amondz.com/product/2/resize/subImg/PSI_7.jpeg",
            "https://cdntest.amondz.com/product/2/resize/subImg/PSI_8.jpeg",
            "https://cdntest.amondz.com/product/2/resize/detailImg/PSI_9.jpeg",
            "https://cdntest.amondz.com/product/2/resize/detailImg/PSI_10.jpeg",
            "https://cdntest.amondz.com/product/3/resize/mainImg/PSI_11.jpeg",
            "https://cdntest.amondz.com/product/3/resize/subImg/PSI_12.jpeg"
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return TestViewHolder(view, context, glide)
    }

    override fun getItemCount(): Int = URL_LIST.size

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.onBind(URL_LIST[position])
    }
}