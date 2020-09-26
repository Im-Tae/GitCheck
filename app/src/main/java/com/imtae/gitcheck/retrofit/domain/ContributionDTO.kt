package com.imtae.gitcheck.retrofit.domain

class ContributionDTO {
    var year : String? = null
    var total : Int? = 0
    var contributionInfoList : ArrayList<ContributionInfo> = ArrayList()

    data class ContributionInfo(var date: String, var count: Int, var color: String)
}