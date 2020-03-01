package com.example.instagrammoretextview

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatTextView

class ReadMoreTextView : AppCompatTextView, ViewTreeObserver.OnGlobalLayoutListener {

    companion object {
        private const val INVALID_END_INDEX = -1
        private const val DEFAULT_SHOW_TRIM_EXPANDED_TEXT = true
        private const val ELLIPSIZE = "... "
    }

    private var mText: CharSequence? = null
    private var mBufferType: BufferType? = null
    var mReadMore = true
    private var mTrimCollapsedText: CharSequence? = null
    private var mTrimExpandedText: CharSequence? = null
    private var mViewMoreSpan: ReadMoreClickableSpan? = null
    private var mShowTrimExpandedText = false

    private var mLineEndIndex = 0
    private var mTrimLines = 0

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray =
            context!!.obtainStyledAttributes(attrs, R.styleable.ReadMoreTextView)
        val resourceIdTrimCollapsedText = typedArray.getResourceId(
            R.styleable.ReadMoreTextView_trimCollapsedText,
            R.string.read_more
        )
        val resourceIdTrimExpandedText = typedArray.getResourceId(
            R.styleable.ReadMoreTextView_trimExpandedText,
            R.string.read_less
        )
        mTrimCollapsedText = resources.getString(resourceIdTrimCollapsedText)
        mTrimExpandedText = resources.getString(resourceIdTrimExpandedText)
        mShowTrimExpandedText = typedArray.getBoolean(
            R.styleable.ReadMoreTextView_showTrimExpandedText,
            DEFAULT_SHOW_TRIM_EXPANDED_TEXT
        )
        typedArray.recycle()
        mViewMoreSpan = ReadMoreClickableSpan()
        onGlobalLayoutLineEndIndex()
        setText()
    }

    private fun setText() {
        super.setText(getDisplayableText(), mBufferType)
        movementMethod = LinkMovementMethod.getInstance()
        highlightColor = Color.TRANSPARENT
    }

    private fun getDisplayableText(): CharSequence? {
        return getTrimmedText(mText)
    }

    override fun setText(
        text: CharSequence?,
        type: BufferType
    ) {
        this.mText = text
        mBufferType = type
        setText()
    }

    private fun getTrimmedText(text: CharSequence?): CharSequence? {
        if (text != null && mLineEndIndex > 0) {
            if (mReadMore) {
                if (layout.lineCount > mTrimLines) {
                    return updateCollapsedText()
                }
            } else {
                return updateExpandedText()
            }
        }
        return text
    }

    fun updateCollapsedText(): CharSequence? {
        val trimEndIndex =
            mLineEndIndex - (ELLIPSIZE.length + mTrimCollapsedText!!.length + 1)
        val s =
            SpannableStringBuilder(text, 0, trimEndIndex)
                .append(ELLIPSIZE)
                .append(mTrimCollapsedText)
        return addClickableSpan(s, mTrimCollapsedText!!)
    }

    fun updateExpandedText(): CharSequence? {
        if (mShowTrimExpandedText) {
            val s =
                SpannableStringBuilder(mText, 0, mText!!.length).append(mTrimExpandedText)
            return addClickableSpan(s, mTrimExpandedText!!)
        }
        return mText
    }

    private fun addClickableSpan(
        s: SpannableStringBuilder,
        trimText: CharSequence
    ): CharSequence? {
        s.setSpan(
            mViewMoreSpan,
            s.length - trimText.length,
            s.length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )
        return s
    }

    fun setTrimCollapsedText(trimCollapsedText: CharSequence?) {
        this.mTrimCollapsedText = trimCollapsedText
    }

    fun setTrimExpandedText(trimExpandedText: CharSequence?) {
        this.mTrimExpandedText = trimExpandedText
    }

    fun setTrimLines(trimLines: Int) {
        this.mTrimLines = trimLines
    }

    private fun onGlobalLayoutLineEndIndex() {
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }


    inner class ReadMoreClickableSpan : ClickableSpan() {
        override fun onClick(widget: View) {
            mReadMore = !mReadMore
            setText()
        }

        override fun updateDrawState(ds: TextPaint) {}
    }

    override fun onGlobalLayout() {
        val obs: ViewTreeObserver = viewTreeObserver
        obs.removeOnGlobalLayoutListener(this)
        refreshLineEndIndex()
        setText()
    }

    private fun refreshLineEndIndex() {
        try {
            mLineEndIndex = when (mTrimLines) {
                0 -> {
                    layout.getLineEnd(0)
                }
                in 1..lineCount -> {
                    layout.getLineEnd(mTrimLines - 1)
                }
                else -> {
                    INVALID_END_INDEX
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toggleTextView() {
        mViewMoreSpan!!.onClick(this)
    }
}