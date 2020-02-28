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

class ATestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a)

        findViewById<TextView>(R.id.textviewa).setOnClickListener {
            Intent(this@ATestActivity, BTestActivity::class.java).run {
                startActivityForResult(this, 200)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 200) {
            setResult(14)
            Log.d("jhlee", "ATestActivity onActivityResult requestCode : $requestCode")
            Log.d("jhlee", "ATestActivity onActivityResult resultCode : $resultCode")
            finish()
        }
    }
}
