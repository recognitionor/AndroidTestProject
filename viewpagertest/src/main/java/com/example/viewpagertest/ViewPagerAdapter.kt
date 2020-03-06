package com.example.viewpagertest

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.*

class ViewPagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class TestUI() : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                backgroundColor = Color.YELLOW
                frameLayout {
                    lparams(200, 200)
                    backgroundColor = Color.RED
                }
                lparams(matchParent, matchParent)
            }
        }


    }

    inner class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind() {
            Log.d("jhlee", "onBInd")
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TestViewHolder(
        TestUI().createView(
            AnkoContext.Companion.create(
                parent.context,
                parent
            )
        )
    )

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as TestViewHolder
        holder.onBind()
    }

}