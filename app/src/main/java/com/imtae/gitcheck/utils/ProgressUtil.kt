package com.imtae.gitcheck.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.imtae.gitcheck.R

class ProgressUtil(context: Context) {

    private var dialog : Dialog = Dialog(context)

    init {
        dialog.setContentView(R.layout.progress_bar)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
    }

    fun show() = dialog.show()

    fun hide() {
        dialog.hide()
        dialog.dismiss()
    }
}