package com.imtae.gitcheck.data.domain

class ContributionDTO {
    var year : String? = null
    var total : Int? = 0
    var contributionInfoList : ArrayList<ContributionInfo> = ArrayList()

    data class ContributionInfo(var date: String, var count: Int, var color: String)
}