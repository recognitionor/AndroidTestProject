package com.example.recyclerviewtest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewtest.grid.GridTestFragment
import com.example.recyclerviewtest.spannablegrid.SpanableTestFragment
import com.example.recyclerviewtest.staggerd.StaggeredTestFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.grid_btn).setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_content_view, GridTestFragment())
                commit()
            }

        }

        findViewById<Button>(R.id.spanable_btn).setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_content_view, SpanableTestFragment()).commit()
        }

        findViewById<Button>(R.id.stagged_btn).setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_content_view, StaggeredTestFragment()).commit()
        }



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}