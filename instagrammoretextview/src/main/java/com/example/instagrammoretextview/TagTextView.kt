package com.example.instagrammoretextview

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View

class TagTextView : ReadMoreTextView {


    companion object {
        var SPACE_TEXT = "\u0020\u0020"
    }


    private var mTagList: ArrayList<String> = ArrayList()

    private var mSpannableList: ArrayList<SpannableStringBuilder> = ArrayList()

    private var mTrimLine: Int = 2

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        movementMethod = LinkMovementMethod.getInstance()
    }

    fun setReadMoreText(tagList: ArrayList<String>) {
        mTagList = tagList
        val tempWholeStringBuilder = SpannableStringBuilder()
        for (i in 0 until tagList.size) {

            val tempString = "#" + tagList[i]
            val bodyStringBuilder = SpannableStringBuilder(tempString + SPACE_TEXT)

            bodyStringBuilder.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    isExpandedStatus = true
                    setOriginText()
                    onExpandedClickCallbacks?.invoke()
                }
            }, 0, bodyStringBuilder.length - SPACE_TEXT.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
            mSpannableList.add(bodyStringBuilder)
            tempWholeStringBuilder.append(bodyStringBuilder)
        }

        setReadMoreText(tempWholeStringBuilder)
    }

    override fun correctionLine(
        resultSpan: SpannableStringBuilder,
        expandedSpan: SpannableStringBuilder
    ) {
        val lastSpaceString =
            resultSpan.substring(resultSpan.lastIndex - (mExpandedWholeText.length + SPACE_TEXT.length))
        if (lastSpaceString != SPACE_TEXT) {
            resultSpan.delete(
                resultSpan.lastIndexOf(SPACE_TEXT) + SPACE_TEXT.length,
                resultSpan.length - mExpandedWholeText.length
            )
        }
        val reCorrectedString = reCorrectionStringBuilder(resultSpan)
        super.correctionLine(reCorrectedString, expandedSpan)
    }

    private fun reCorrectionStringBuilder(
        tempSpan: SpannableStringBuilder
    ): SpannableStringBuilder {

        val renewalSpan = SpannableStringBuilder(tempSpan.toString())
        tempSpan.delete(
            tempSpan.length - (mExpandedWholeText.length + SPACE_TEXT.length),
            tempSpan.length
        )
        var startIndex = 0
        var endIndex = tempSpan.indexOf(SPACE_TEXT, startIndex + SPACE_TEXT.lastIndex)

        while ((endIndex < tempSpan.length)) {
            endIndex = tempSpan.indexOf(SPACE_TEXT, startIndex)

            if (endIndex < 0) {
                endIndex = tempSpan.length
            }
            val tempString = tempSpan.substring(startIndex, endIndex)
            renewalSpan.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Log.d("jhlee", "renewal $tempString")
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.color = Color.YELLOW
                }
            }, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

            renewalSpan.setSpan(
                ForegroundColorSpan(Color.BLACK),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            endIndex += SPACE_TEXT.length
            startIndex = endIndex
        }

        renewalSpan.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    setOriginText()
                }
            },
            renewalSpan.length - mExpandedWholeText.length,
            renewalSpan.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        return renewalSpan
    }
}