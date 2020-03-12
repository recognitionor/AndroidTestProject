package com.example.instagrammoretextview

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.instagrammoretextview.fragment.ReadMoreTestFragment
import com.example.instagrammoretextview.fragment.WholeTagTestFragment

class MainActivity : AppCompatActivity() {

//    lateinit var editText: EditText
    lateinit var mReadMoreBtn: Button

    lateinit var mWholeTagBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mReadMoreBtn = findViewById(R.id.read_more_btn)
        mWholeTagBtn = findViewById(R.id.whole_tag_btn)



        val readMoreFragment =
            ReadMoreTestFragment()

        val wholeTagFragment =
            WholeTagTestFragment()
        mReadMoreBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.frame_content, readMoreFragment).commit()
        }

        mWholeTagBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.frame_content, wholeTagFragment).commit()
        }
    }
}
