package com.example.zemoga.data.dto

import com.google.gson.annotations.SerializedName

class TransactionDTO {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("userId")
    var userId: Int? = 0

    @SerializedName("createdDate")
    var createdDate: String? = null

    @SerializedName("commerce")
    var commerce: CommerceDTO? = null

    @SerializedName("branch")
    var branch: BranchDTO? = null
}