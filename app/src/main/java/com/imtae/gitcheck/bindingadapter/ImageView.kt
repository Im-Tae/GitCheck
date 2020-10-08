package com.imtae.gitcheck.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(url: String?) {
    Picasso.get().load(url).into(this)
}