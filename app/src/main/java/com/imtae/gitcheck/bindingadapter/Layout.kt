package com.imtae.gitcheck.bindingadapter

import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter

@BindingAdapter("setVisibilityProfileLinearLayout")
fun LinearLayout.setVisibilityProfileLinearLayout(text: String?) {
    if (!text.isNullOrBlank()) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}