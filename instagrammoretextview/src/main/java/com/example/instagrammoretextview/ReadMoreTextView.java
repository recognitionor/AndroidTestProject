package com.example.instagrammoretextview;
/*
* https://github.com/bravoborja/ReadMoreTextView 참조*/

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatTextView;

public class ReadMoreTextView extends AppCompatTextView {

    private static final int INVALID_END_INDEX = -1;
    private static final boolean DEFAULT_SHOW_TRIM_EXPANDED_TEXT = true;
    private static final String ELLIPSIZE = "... ";

    private CharSequence text;
    private BufferType bufferType;
    private boolean readMore = true;
    private CharSequence trimCollapsedText;
    private CharSequence trimExpandedText;
    private ReadMoreTextView.ReadMoreClickableSpan viewMoreSpan;
    private boolean showTrimExpandedText;

    private int lineEndIndex;
    private int trimLines;

    public ReadMoreTextView(Context context) {
        this(context, null);
    }

    public ReadMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ReadMoreTextView);
        int resourceIdTrimCollapsedText =
                typedArray.getResourceId(R.styleable.ReadMoreTextView_trimCollapsedText, R.string.read_more);
        int resourceIdTrimExpandedText =
                typedArray.getResourceId(R.styleable.ReadMoreTextView_trimExpandedText, R.string.read_less);
        this.trimCollapsedText = getResources().getString(resourceIdTrimCollapsedText);
        this.trimExpandedText = getResources().getString(resourceIdTrimExpandedText);
        this.showTrimExpandedText =
                typedArray.getBoolean(R.styleable.ReadMoreTextView_showTrimExpandedText, DEFAULT_SHOW_TRIM_EXPANDED_TEXT);
//        this.trimMode = typedArray.getInt(R.styleable.TestTextView_trimMode, TRIM_MODE_LINES);
        typedArray.recycle();
        viewMoreSpan = new ReadMoreTextView.ReadMoreClickableSpan();
        onGlobalLayoutLineEndIndex();
        setText();
    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);
        setMovementMethod(LinkMovementMethod.getInstance());
        setHighlightColor(Color.TRANSPARENT);
    }

    private CharSequence getDisplayableText() {
        return getTrimmedText(text);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        this.text = text;
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (text != null && lineEndIndex > 0) {
            if (readMore) {
                if (getLayout().getLineCount() > trimLines) {
                    return updateCollapsedText();
                }
            } else {
                return updateExpandedText();
            }
        }
        return text;
    }

    public CharSequence updateCollapsedText() {
        int trimEndIndex = lineEndIndex - (ELLIPSIZE.length() + trimCollapsedText.length() + 1);
        SpannableStringBuilder s = new SpannableStringBuilder(text, 0, trimEndIndex)
                .append(ELLIPSIZE)
                .append(trimCollapsedText);
        return addClickableSpan(s, trimCollapsedText);
    }

    public CharSequence updateExpandedText() {
        if (showTrimExpandedText) {
            SpannableStringBuilder s = new SpannableStringBuilder(text, 0, text.length()).append(trimExpandedText);
            return addClickableSpan(s, trimExpandedText);
        }
        return text;
    }

    private CharSequence addClickableSpan(SpannableStringBuilder s, CharSequence trimText) {
        s.setSpan(viewMoreSpan, s.length() - trimText.length(), s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return s;
    }

    public void setTrimCollapsedText(CharSequence trimCollapsedText) {
        this.trimCollapsedText = trimCollapsedText;
    }

    public void setTrimExpandedText(CharSequence trimExpandedText) {
        this.trimExpandedText = trimExpandedText;
    }

    public void setTrimLines(int trimLines) {
        this.trimLines = trimLines;
    }

    private class ReadMoreClickableSpan extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            readMore = !readMore;
            setText();
        }

        @Override
        public void updateDrawState(TextPaint ds) { }
    }

    private void onGlobalLayoutLineEndIndex() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
                refreshLineEndIndex();
                setText();
            }
        });
    }

    private void refreshLineEndIndex() {
        try {
            if (trimLines == 0) {
                lineEndIndex = getLayout().getLineEnd(0);
            } else if (trimLines > 0 && getLineCount() >= trimLines) {
                lineEndIndex = getLayout().getLineEnd(trimLines - 1);
            } else {
                lineEndIndex = INVALID_END_INDEX;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toggleTextView() {
        viewMoreSpan.onClick(this);
    }
}

