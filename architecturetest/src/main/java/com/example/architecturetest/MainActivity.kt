package com.example.architecturetest

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    companion object {
        const val NAME = "name"
        const val POSITION = "position"
        const val NUMBER = "number"
    }

    private var textViewName: TextView? = null
    private var textViewPosition: TextView? = null
    private var textViewNumber: TextView? = null
    private var btnModified: Button? = null

    private lateinit var viewModel: PlayerInfoViewModel

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val name = result.data?.getStringExtra(NAME)
                val number = result.data?.getStringExtra(NUMBER)
                val position = result.data?.getStringExtra(POSITION)
                val playerInfo = PlayerInfo()
                name?.let {
                    playerInfo.name = it
                }
                number?.let {
                    playerInfo.number = it
                }
                position?.let {
                    playerInfo.position = it
                }
                viewModel.getPlayerInfo().value = playerInfo
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(PlayerInfoViewModel::class.java)


        viewModel.getPlayerInfo().observe(this, Observer {
            updateUI(it)
        })

        textViewName = findViewById(R.id.tv_name)
        textViewNumber = findViewById(R.id.tv_number)
        textViewPosition = findViewById(R.id.tv_position)
        btnModified = findViewById(R.id.btn_modified)
        btnModified?.setOnClickListener {
            val intent = Intent(this, ModifiedActivity::class.java)
            resultLauncher.launch(intent)
        }
        if (viewModel.getPlayerInfo().value == null) {
            fetchPlayerInfo()
        }
    }

    private fun fetchPlayerInfo() {
        val playerInfo = PlayerInfo()
        playerInfo.name = "messi"
        playerInfo.number = "10"
        playerInfo.position = "god"
        viewModel.getPlayerInfo().value = playerInfo
    }

    private fun updateUI(playerInfo: PlayerInfo) {
        textViewName?.text = playerInfo.name
        textViewPosition?.text = playerInfo.position
        textViewNumber?.text = playerInfo.number
    }
}