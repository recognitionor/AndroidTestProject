package com.example.recyclerviewtest.staggerd

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*

class BigImageViewHolder(
    ctx: Context,
    parent: ViewGroup,
    targetView: View,
    private val mBorderSize: Int,
    private val mBigImageSizeSize: Int,
    private val mSmallImageSizeSize: Int
) :
    AbsImageViewHolder(ctx, parent, targetView) {
    override fun createLayout(linearLayout: LinearLayout) {
        with(linearLayout) {
            linearLayout {
                frameLayout {
                    backgroundColor = Color.WHITE
                }.lparams(mBigImageSizeSize, mBigImageSizeSize)

                view {
                    backgroundColor = Color.BLACK
                }.lparams(mBorderSize, matchParent)

                verticalLayout {
                    frameLayout {
                        backgroundColor = Color.WHITE
                    }.lparams(mSmallImageSizeSize, mSmallImageSizeSize)
                    view {
                        backgroundColor = Color.BLACK
                    }.lparams(matchParent, mBorderSize)

                    frameLayout {
                        backgroundColor = Color.WHITE
                    }.lparams(mSmallImageSizeSize, mSmallImageSizeSize)
                }
            }
        }
    }


}