package com.example.stagviewer

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {

    protected fun getLocationOnScreen(mEditText: View): Rect {
        val mRect = Rect()
        val location = IntArray(2)
        mEditText.getLocationOnScreen(location)
        mRect.left = location[0]
        mRect.top = location[1]
        mRect.right = location[0] + mEditText.width
        mRect.bottom = location[1] + mEditText.height
        return mRect
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val handleReturn = super.dispatchTouchEvent(ev)
        val view: View? = currentFocus
        val x = ev.x.toInt()
        val y = ev.y.toInt()
        if (view is EditText) {
            val innerView: View? = currentFocus
            if (ev.action == MotionEvent.ACTION_UP &&
                !getLocationOnScreen(innerView!!).contains(x, y)
            ) {
                val input = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                input.hideSoftInputFromWindow(
                    window.currentFocus?.getWindowToken(), 0
                )
            }
        }
        return handleReturn
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(R.layout.activity_main)


    }
}