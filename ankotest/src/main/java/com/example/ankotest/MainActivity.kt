package com.example.ankotest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    private var padding: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        padding = dip(48)

        var width = DeviceUtils.getDeviceSize(this).x
        width -= (this@MainActivity.padding * 2)
        val temp = width / 3
        var border = width % 2
        Log.d("jhlee", "border 1 : $border")
        val itemMargin = dip(2)
        border = itemMargin % 6 + itemMargin

        Log.d("jhlee", "border 2 : $border")


        val result = temp - border
        border = (width - (result * 3)) / 2

        Log.d("jhlee", "test1 width: $width")
        Log.d("jhlee", "temp: $temp")
        Log.d("jhlee", "border result : $border")
        Log.d("jhlee", "result : $result")



        verticalLayout {

            gravity = Gravity.CENTER
            leftPadding = this@MainActivity.padding
            rightPadding = this@MainActivity.padding

            linearLayout {
                backgroundColor = Color.YELLOW
                frameLayout {

                }.lparams((result * 2) + border, (result * 2) + border)

                view {
                    backgroundColor = Color.RED
                }.lparams(border, matchParent)

                verticalLayout {
                    frameLayout {

                    }.lparams(result, result)

                    view {
                        backgroundColor = Color.RED
                    }.lparams(matchParent, border)

                    frameLayout {

                    }.lparams(result, result)
                }
            }

            view {
                backgroundColor = Color.RED
            }.lparams(matchParent, border)

            linearLayout {
                backgroundColor = Color.YELLOW
                frameLayout {

                }.lparams(result, result)
                view {
                    backgroundColor = Color.RED
                }.lparams(border, matchParent)
                frameLayout {

                }.lparams(result, result)
                view {
                    backgroundColor = Color.RED
                }.lparams(border, matchParent)
                frameLayout {

                }.lparams(result, result)

            }
        }
    }
}
