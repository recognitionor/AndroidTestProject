package com.example.instagrammoretextview

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatTextView

class ReadMoreTextView : AppCompatTextView, ViewTreeObserver.OnGlobalLayoutListener {

    private var mOriginText: CharSequence? = null

    private var isExpandedStatus: Boolean = false

    private var isFirstInitText: Boolean = true

    private var mExpandedText: String = " ...more"

    private var mTrimLine: Int = 2

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        viewTreeObserver.addOnGlobalLayoutListener(this)
        movementMethod = LinkMovementMethod.getInstance()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        if (isFirstInitText && !text.isNullOrEmpty()) {
            mOriginText = text
            isFirstInitText = false
        }
    }

    override fun onGlobalLayout() {
        val obs: ViewTreeObserver = viewTreeObserver
        obs.removeOnGlobalLayoutListener(this)
        if (layout != null && !mOriginText.isNullOrEmpty()) {
            if (!isExpandedStatus && layout.lineCount > mTrimLine) {
                val endIndex = layout.getLineEnd(mTrimLine - 1) - mExpandedText.length
                val span = SpannableStringBuilder(mOriginText, 0, endIndex)
                updateCollapseText(span)
                text = span
            }
        }
    }

    private fun updateCollapseText(spanBuilder: SpannableStringBuilder) {
        val appendSpan = SpannableStringBuilder(mExpandedText)
        appendSpan.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                updateExpandedText()
            }
        }, 0, mExpandedText.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        spanBuilder.append(appendSpan)
    }

    private fun updateExpandedText() {
        isExpandedStatus = true
        mOriginText?.length?.let {
            text = SpannableStringBuilder(mOriginText, 0, it)
        }
    }
}