package com.imtae.gitcheck.bindingadapter

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import com.imtae.gitcheck.R

fun LinearLayout.setExpandCollapse(scrollView: NestedScrollView?= null, arrowImage: ImageView) {
    if (this.visibility == View.GONE) {
        this.expand(scrollView = scrollView)
        arrowImage.setImageResource(R.drawable.arrow_up)
    } else {
        this.collapse()
        arrowImage.setImageResource(R.drawable.arrow_down)
    }
}

fun View.expand(scrollView: NestedScrollView?= null) {

    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    layoutParams.height = 0
    visibility = View.VISIBLE

    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {

            measuredHeight.let {
                layoutParams.height = if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (it * interpolatedTime).toInt()
                requestLayout()
            }
        }
    }

    animation.setAnimationListener(object : Animation.AnimationListener{
        override fun onAnimationStart(animation: Animation?) {}

        override fun onAnimationRepeat(animation: Animation?) {}

        override fun onAnimationEnd(animation: Animation?) {
            scrollView?.run { smoothScrollTo(0, bottom) }
        }
    })
}

fun View.collapse() {

    measuredHeight.let {

        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                super.applyTransformation(interpolatedTime, t)

                if (interpolatedTime == 1f)
                    visibility = View.GONE
                else {
                    layoutParams.height = it - (it * interpolatedTime).toInt()
                    requestLayout()
                }
            }
        }

        animation.apply {
            duration = (it / context.resources.displayMetrics.density).toLong()
            startAnimation(this)
        }
    }
}