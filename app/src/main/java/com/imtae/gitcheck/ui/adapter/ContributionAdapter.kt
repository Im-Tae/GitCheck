package com.imtae.gitcheck.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imtae.gitcheck.R
import com.imtae.gitcheck.ui.adapter.viewHolder.ContributionViewHolder
import com.imtae.gitcheck.data.domain.ContributionDTO

class ContributionAdapter(private val contributionList: ArrayList<ContributionDTO>) : RecyclerView.Adapter<ContributionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributionViewHolder {

        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.contributions_layout, parent, false)

        return ContributionViewHolder(itemView)
    }

    override fun getItemCount(): Int = contributionList.size

    override fun onBindViewHolder(holder: ContributionViewHolder, position: Int) = holder.bindItems(contributionList[position])
}