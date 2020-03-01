package com.example.instagrammoretextview

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

/**
 * SpannableStringBuilder로 일부 텍스트만 font를 변경하고자 만듬
 */
class CustomTypefaceSpan(mFamily: String?, var mTypeface: Typeface) : TypefaceSpan(mFamily) {

    override fun updateDrawState(ds: TextPaint?) {
        applyCustomTypeFace(ds!!, mTypeface)
    }

    override fun updateMeasureState(paint: TextPaint?) {
        applyCustomTypeFace(paint!!, mTypeface)
    }

    private fun applyCustomTypeFace(paint: Paint, typeface: Typeface) {
        val oldStyle: Int = paint.typeface?.style ?: 0
        val fake = oldStyle and typeface.style.inv()

        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }

        paint.typeface = typeface
    }
}