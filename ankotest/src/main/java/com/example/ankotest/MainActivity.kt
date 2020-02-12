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

        padding = dip(16)

        var width = DeviceUtils.getDeviceSize(this).x
        width -= (this@MainActivity.padding * 2)
        val tempImageSize = width / 3
        val border: Int
        var itemMarginOffset = dip(2)
        itemMarginOffset = when (itemMarginOffset % 3) {
            0 -> itemMarginOffset
            1 -> 2 + itemMarginOffset
            2 -> 1 + itemMarginOffset
            else -> itemMarginOffset
        }
        itemMarginOffset /= 2
        val result = tempImageSize - itemMarginOffset

        border = (width - (result * 3)) / 2


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
