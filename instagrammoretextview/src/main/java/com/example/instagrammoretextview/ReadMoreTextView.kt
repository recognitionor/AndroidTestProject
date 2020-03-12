package com.example.instagrammoretextview

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatTextView

open class ReadMoreTextView : AppCompatTextView, ViewTreeObserver.OnGlobalLayoutListener {

    interface IReadMoreTextViewStatusListener {
        var expandedViewPositionSet: HashSet<Int>

        fun addExpandedView(position: Int)

        fun isExpandedView(position: Int): Boolean

    }

    private var mExpandedDotText: String = context.getString(R.string.read_more_dot_text)

    private var mTrimLine: Int = 2

    private var mOriginText: CharSequence? = null

    private var mExpandedText: String = context.getString(R.string.read_more_text)

    var onExpandedClickCallbacks: (() -> Unit?)? = null

    var isExpandedStatus: Boolean = false

    var mExpandedWholeText: String = mExpandedDotText + mExpandedText

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
            var endIndex = layout.getLineEnd(mTrimLine - 1) - mExpandedWholeText.length
            if (endIndex < 0) {
                endIndex = 0
            }
            val resultSpan = SpannableStringBuilder(text, 0, endIndex)
            val expandedSpan = createExpandedSpannable(resultSpan)
            resultSpan.append(expandedSpan)
            correctionLine(resultSpan, expandedSpan)
        }
    }

    protected open fun correctionLine(
        resultSpan: SpannableStringBuilder,
        expandedSpan: SpannableStringBuilder
    ) {

        text = resultSpan

        if (layout != null && layout.lineCount > mTrimLine) {
            val endIndex = resultSpan.length - (mExpandedText.length + 1)
            if (endIndex > resultSpan.length || endIndex < 0) {
                return
            }
            val temp = resultSpan.subSequence(0, endIndex)
            val tempBuilder = SpannableStringBuilder(temp).append(expandedSpan)
            correctionLine(tempBuilder, expandedSpan)
        }
    }

    fun setReadMoreText(text: CharSequence?) {
        super.setText(text)
        mOriginText = text
        if (!isExpandedStatus) {
            viewTreeObserver.addOnGlobalLayoutListener(this)
        }
    }

    private fun createExpandedSpannable(resultSpan: SpannableStringBuilder): SpannableStringBuilder {
        val expandedSpan = SpannableStringBuilder(mExpandedWholeText)
        expandedSpan.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    setOriginText()
                }
            },
            mExpandedDotText.length,
            mExpandedWholeText.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        val colorSpan = ForegroundColorSpan(Color.CYAN)
        expandedSpan.setSpan(
            colorSpan,
            0,
            mExpandedWholeText.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        return expandedSpan
    }

    fun setOriginText() {
        isExpandedStatus = true
        text = mOriginText
        onExpandedClickCallbacks?.invoke()
    }
}