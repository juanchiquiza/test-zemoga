package com.leal.data.models

import java.io.Serializable

class CommerceModel: Serializable {
    var id: Int? = 0
    var name: String? = null
    var valueToPoints: Int? = 0
    var branches: List<BranchModel>? = null
}