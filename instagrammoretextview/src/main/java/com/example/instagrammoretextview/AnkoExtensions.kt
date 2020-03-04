package com.example.instagrammoretextview

import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView


inline fun ViewManager.readMoreTextView(init: ReadMoreTextView.() -> Unit): ReadMoreTextView {
    return ankoView({ ReadMoreTextView(it) }, theme = 0, init = init)
}

