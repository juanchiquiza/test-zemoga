package com.example.zemoga.data.dto

import com.google.gson.annotations.SerializedName

class PostDTO {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("body")
    var body: String? = null
}
