package com.example.recyclerviewtest

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import java.lang.reflect.InvocationTargetException


object DeviceUtils {
    /**
     *  size.x = width, size.y = hegiht
     */
    fun getDeviceSize(context: Activity): Point {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        context.windowManager.defaultDisplay.let { display ->
            val size = Point()
            display.getSize(size)
            return size
        }
    }

    fun isSdkVersionLollipopAndAbove() : Boolean {
        return android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    fun showKeyboard(context: Context, view : View) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, 0)
    }

    fun hideKeyboard(context: Context, view : View) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * ex) Samsung SM-G950N
     */
    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) capitalize(model)
        else "${capitalize(manufacturer)} $model"
    }

    private fun capitalize(s: String?): String {
        if (s == null || s.isEmpty()) return ""

        val first = s[0]
        return if (Character.isUpperCase(first)) s
        else Character.toUpperCase(first) + s.substring(1)
    }


    /**
     * Soft Navigation Bar 검사
     */
    fun getNavigationBarSize(context: Context): Point {
        val appUsableSize = getAppUsableScreenSize(context)
        val realScreenSize = getRealScreenSize(context)

        // navigation bar on the right
        if (appUsableSize.x < realScreenSize.x) {
            return Point(realScreenSize.x - appUsableSize.x, appUsableSize.y)
        }

        // navigation bar at the bottom
        return if (appUsableSize.y < realScreenSize.y) {
            Point(appUsableSize.x, realScreenSize.y - appUsableSize.y)
        } else Point(0,0)

        // navigation bar is not present
    }

    fun getAppUsableScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point(0,0)
        display.getSize(size)
        return size
    }

    fun getRealScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point(0,0)

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size)
        } else if (Build.VERSION.SDK_INT >= 14) {
            try {
                size.x = Display::class.java.getMethod("getRawWidth").invoke(display) as Int
                size.y = Display::class.java.getMethod("getRawHeight").invoke(display) as Int
            } catch (e: IllegalAccessException) {
            } catch (e: InvocationTargetException) {
            } catch (e: NoSuchMethodException) {
            }

        }

        return size
    }

}

