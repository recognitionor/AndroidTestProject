package com.example.ankotest

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {

            gravity = Gravity.CENTER
            leftPadding = dip(16)
            rightPadding = dip(16)

            linearLayout {
                backgroundColor = Color.RED

                frameLayout {
                    backgroundColor = Color.YELLOW
                }.lparams(test2(context), matchParent)

                view {}.lparams(6, matchParent)

                verticalLayout {

                    frameLayout {
                        backgroundColor = Color.YELLOW
                    }.lparams(test3(context), 0, 1f)


                    view {}.lparams(matchParent, 6)

                    frameLayout {
                        backgroundColor = Color.YELLOW
                    }.lparams(test3(context), 0, 1f)

                }.lparams(test3(context), matchParent)
            }.lparams(matchParent, test1(context))

            view {
                backgroundColor = Color.RED
            }.lparams(matchParent, 6)

            linearLayout {
                backgroundColor = Color.RED

                frameLayout {
                    backgroundColor = Color.YELLOW
                }.lparams(test3(context), test3(context))

                view {}.lparams(6, matchParent)

                frameLayout {
                    backgroundColor = Color.YELLOW

                }.lparams(test3(context), test3(context))

                view {}.lparams(6, matchParent)

                frameLayout {
                    backgroundColor = Color.YELLOW

                }.lparams(test3(context), test3(context))

            }.lparams(matchParent, test3(context))

        }
    }


    private fun test1(context: Context): Int {

        var width = DeviceUtils.getDeviceSize(this).x
        Log.d("jhlee", "test1 width: $width")
        width -= dip(16) * 2

        Log.d("jhlee", "test1 sideMargin: $width")

        return ((width / 3) * 2).apply {
            Log.d("jhlee", "test1 : $this")
        }
    }

    private fun test2(context: Context): Int {

        return (test3(context) * 2 + 6).apply {
            Log.d("jhlee", "test2 : $this")
        }
    }

    private fun test3(context: Context): Int {

        var width = DeviceUtils.getDeviceSize(this).x
        width -= dip(16) * 2
        width -= 12
        Log.d("jhlee", "test3 width: $width")

        Log.d("jhlee", "test3 dip: ${dip(2)}")


        Log.d("jhlee", "test3 sideMargin: $width")

        return (width / 3).apply {
            Log.d("jhlee", "test3 : $this")
        }
    }
}
