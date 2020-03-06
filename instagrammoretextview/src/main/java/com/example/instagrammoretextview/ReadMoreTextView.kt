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
import java.lang.Exception

class ReadMoreTextView : AppCompatTextView, ViewTreeObserver.OnGlobalLayoutListener {

    interface IReadMoreTextViewStatusListener {
        var expandedViewPositionSet: HashSet<Int>

        fun addExpandedView(position: Int)

        fun isExpandedView(position: Int): Boolean

    }

    private var mOriginText: CharSequence? = null

    var onExpandedClickCallbacks: (() -> Unit?)? = null

    var isExpandedStatus: Boolean = false

    private var mExpandedText: String = "... 더 보기"

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
            var endIndex = layout.getLineEnd(mTrimLine - 1)
            if (endIndex < 0) {
                endIndex = 0
            }

            var resultSpan = SpannableStringBuilder(text, 0, endIndex)
            val lastChar = resultSpan.substring(endIndex - 1, endIndex)
            if (lastChar == "\n") {
                resultSpan = SpannableStringBuilder(text, 0, endIndex - 1)
            }
            val expandedSpan = SpannableStringBuilder(mExpandedText)
            expandedSpan.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    isExpandedStatus = true
                    text = mOriginText
                    onExpandedClickCallbacks?.invoke()
                }
            }, 0, mExpandedText.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            resultSpan.append(expandedSpan)
            checkLine(resultSpan, expandedSpan)

        }
    }

    private fun checkLine(
        resultSpan: SpannableStringBuilder,
        expandedSpan: SpannableStringBuilder
    ) {
        text = resultSpan

        if (layout.lineCount > mTrimLine) {
            val endIndex = resultSpan.length - (mExpandedText.length + 1)
            if (endIndex > resultSpan.length || endIndex < 0) {
                return
            }
            val temp = resultSpan.subSequence(0, endIndex)
            val tempBuilder = SpannableStringBuilder(temp).append(expandedSpan)
            checkLine(tempBuilder, expandedSpan)
        }
    }

    fun setReadMoreText(text: CharSequence?) {
        super.setText(text)
        mOriginText = text
        if (!isExpandedStatus) {
            viewTreeObserver.addOnGlobalLayoutListener(this)
        }
    }
}