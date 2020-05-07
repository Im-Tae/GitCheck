package com.imtae.gitcheck.adapter.viewHolder

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import com.imtae.gitcheck.retrofit.domain.ContributionDTO
import kotlinx.android.synthetic.main.contributions_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class ContributionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItems(contributionList: ContributionDTO) {

        itemView.year.text = contributionList.year
        itemView.total_commit.text = contributionList.total.toString()

        itemView.contribution_GridLayout.removeAllViews()

        for (commitIndex in 0 until contributionList.contributionInfoList.size) {

            val param = LinearLayout.LayoutParams(37, 37).apply { setMargins(3) }
            val layout = LinearLayout(itemView.context).apply {
                layoutParams = param
                setBackgroundColor(Color.parseColor(contributionList.contributionInfoList[commitIndex].color))
            }

            itemView.contribution_GridLayout.addView(layout)
        }
    }
}