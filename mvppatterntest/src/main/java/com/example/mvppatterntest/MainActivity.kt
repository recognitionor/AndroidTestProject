package com.example.mvppatterntest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.mvppatterntest.fragment.TestFragment
import com.example.mvppatterntest.retrofit.RetrofitAPI
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private var mRetrofit: Retrofit? = null

    private var mRetrofitAPI: RetrofitAPI? = null

    private var test: Call<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRetrofitInit()
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction().replace(
            R.id.main_content_view,
            TestFragment()
        ).commit()

        test = mRetrofitAPI?.test()


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun setRetrofitInit() {
        mRetrofit = Retrofit.Builder()
            .baseUrl("http://testapi.amondz.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mRetrofitAPI = mRetrofit?.create(RetrofitAPI::class.java)
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
