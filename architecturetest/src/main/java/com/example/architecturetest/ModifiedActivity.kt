package com.example.architecturetest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ModifiedActivity : AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var editTextPosition: EditText
    lateinit var editTextNumber: EditText

    lateinit var btnDone: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modified)
        editTextName = findViewById(R.id.et_name)
        editTextPosition = findViewById(R.id.et_position)
        editTextNumber = findViewById(R.id.et_number)
        btnDone = findViewById(R.id.btn_done)

        btnDone.setOnClickListener {
            val intent = Intent()
            intent.putExtra(MainActivity.NAME, editTextName.text.toString())
            intent.putExtra(MainActivity.POSITION, editTextPosition.text.toString())
            intent.putExtra(MainActivity.NUMBER, editTextNumber.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}