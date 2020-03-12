package com.example.instagrammoretextview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.instagrammoretextview.R
import com.example.instagrammoretextview.TagTextView
import com.example.instagrammoretextview.tagTextView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textView
import org.jetbrains.anko.wrapContent

class WholeTagTestFragment : Fragment() {

    lateinit var mLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.whole_tag_content, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLayout = view.findViewById(R.id.whole_tag_layout)
        with(mLayout) {
            val list: ArrayList<String> = ArrayList()
            for (i in 0 until 30) {
                list.add("테스트sl$i")
            }

            tagTextView {
            }.run {
                this.setReadMoreText(list)
            }

            textView {
                text = "TEST!!!!"
            }
        }
    }
}