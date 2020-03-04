package com.example.instagrammoretextview

import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.*

class MyRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MyViewHolderUI : AnkoComponent<ViewGroup> {
        lateinit var readMoreTextView: ReadMoreTextView

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                readMoreTextView = readMoreTextView {

                }.lparams {
                    width = matchParent
                    height = wrapContent
                    padding = dip(40)
                }
            }
        }
    }

    inner class MyViewHolder(
        private var ui: MyViewHolderUI,
        view: LinearLayout
    ) : RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {
            var temp = itemView.context.getString(R.string.test_text_1)
            for (i in 0 until position + 1) {
                temp += "($i----$i)"
            }
            if (position == 0) {
                temp = ""
            }
//            Log.d("jhlee", "onBind $position")
            ui.readMoreTextView.position = position
            ui.readMoreTextView.post {
                ui.readMoreTextView.setReadMoreText(temp)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val ui = MyViewHolderUI()
        val view = ui.createView(AnkoContext.create(parent.context, parent))
        return MyViewHolder(ui, view)
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MyViewHolder
        holder.bind(position)
    }
}