package com.imtae.gitcheck.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imtae.gitcheck.R
import com.imtae.gitcheck.adapter.viewHolder.ContributionViewHolder
import com.imtae.gitcheck.retrofit.domain.ContributionDTO

class ContributionAdapter(private val contributions: ArrayList<ContributionDTO>) : RecyclerView.Adapter<ContributionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributionViewHolder {

        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.contributions_layout, parent, false)

        return ContributionViewHolder(itemView)
    }

    override fun getItemCount(): Int = contributions.size

    override fun onBindViewHolder(holder: ContributionViewHolder, position: Int) = holder.bindItems(contributions[position], position)
}