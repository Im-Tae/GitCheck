package com.imtae.gitcheck.bindingadapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setText")
fun TextView.setText(text: String?) {
    if (!text.isNullOrBlank()) {
        this.apply {
            visibility = View.VISIBLE
            setText(text)
        }
    }
    else this.visibility = View.GONE
}