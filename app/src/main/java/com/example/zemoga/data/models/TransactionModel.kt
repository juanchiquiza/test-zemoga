package com.leal.data.models

import java.io.Serializable

class TransactionModel: Serializable {
    var id: Int? = 0
    var userId: Int? = 0
    var createdDate: String? = null
    var commerce: CommerceModel? = null
    var branch: BranchModel? = null
}