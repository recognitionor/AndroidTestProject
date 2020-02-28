package com.example.recyclerviewtest.staggerd

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*

class SmallImageViewHolder(
    ctx: Context,
    parent: ViewGroup,
    targetView: View,
    private val mBorderSize: Int,
    private val mImageSizeSize: Int
) :
    AbsImageViewHolder(ctx, parent, targetView) {
    override fun createLayout(linearLayout: LinearLayout) {
        with(linearLayout) {
            linearLayout {
                frameLayout {
                    backgroundColor = Color.WHITE

                }.lparams(mImageSizeSize, mImageSizeSize)

                view {
                    backgroundColor = Color.BLACK
                }.lparams(mBorderSize, matchParent)

                frameLayout {
                    backgroundColor = Color.WHITE

                }.lparams(mImageSizeSize, mImageSizeSize)

                view {
                    backgroundColor = Color.BLACK
                }.lparams(mBorderSize, matchParent)

                frameLayout {
                    backgroundColor = Color.WHITE

                }.lparams(mImageSizeSize, mImageSizeSize)
            }
        }
    }
}