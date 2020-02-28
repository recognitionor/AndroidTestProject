package com.example.activitystacktest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*

class BTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.b)

        findViewById<TextView>(R.id.textview).setOnClickListener { view ->
            setResult(11)

            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("jhlee", "MainActivityonActivityResult requestCode : $requestCode")
        Log.d("jhlee", "MainActivityonActivityResult resultCode : $resultCode")

    }

}
