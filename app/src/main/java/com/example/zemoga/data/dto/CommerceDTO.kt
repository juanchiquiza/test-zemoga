package com.example.zemoga.data.dto

import com.google.gson.annotations.SerializedName

class CommerceDTO {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("valueToPoints")
    var valueToPoints: Int? = 0

    @SerializedName("branches")
    var branches: List<BranchDTO>? = null
}