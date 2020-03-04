package com.example.instagrammoretextview

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
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

    private var mExpandedText: String = " ...more"

    private var mTrimLine: Int = 2

    var position: Int = 0

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onGlobalLayout() {
        viewTreeObserver.removeOnGlobalLayoutListener(this)
        if (layout != null && layout.lineCount > mTrimLine) {
            var endIndex = layout.getLineEnd(mTrimLine - 1) - mExpandedText.length
            if (endIndex < 0) {
                endIndex = 0
            }
            val resultSpan = SpannableStringBuilder(text, 0, endIndex)
            val expandedSpan = SpannableStringBuilder(mExpandedText)
            expandedSpan.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    isExpandedStatus = true
                    text = mOriginText
                }
            }, 0, mExpandedText.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            resultSpan.append(expandedSpan)
            text = resultSpan
        }
    }

    fun setReadMoreText(text: CharSequence?) {
        super.setText(text)
        mOriginText = text
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }
}