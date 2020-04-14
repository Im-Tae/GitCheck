package com.imtae.gitcheck.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardUtil {
    companion object {
        fun hideKeyboard(view: View?, context: Context) {
            if (view != null) {
                val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
            Thread.sleep(100)
        }

        fun showKeyboard(view: View?, context: Context) {
            if (view != null) {
                val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            }
            Thread.sleep(100)
        }
    }
}