package com.imtae.gitcheck.view.ui.profile.adapter.viewholder

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import com.imtae.gitcheck.R
import com.imtae.gitcheck.data.domain.ContributionDTO
import com.jakewharton.rxbinding4.view.clicks
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.createBalloon
import kotlinx.android.synthetic.main.contributions_layout.view.*

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

            layout.clicks().subscribe {

                val showContributionBalloon = createBalloon(itemView.context) {
                    setText("${contributionList.contributionInfoList[commitIndex].date} \n ${contributionList.contributionInfoList[commitIndex].count}")
                    setTextColorResource(R.color.white)
                    setTextSize(15f)
                    setPadding(3)
                    setArrowVisible(false)
                    setBackgroundColorResource(R.color.github_color)
                    setBalloonAnimation(BalloonAnimation.CIRCULAR)
                    setAutoDismissDuration(2000L)
                }

                showContributionBalloon.setOnBalloonClickListener { showContributionBalloon.dismiss() }
                showContributionBalloon.showAlignTop(layout)
            }

            itemView.contribution_GridLayout.addView(layout)
        }
    }
}