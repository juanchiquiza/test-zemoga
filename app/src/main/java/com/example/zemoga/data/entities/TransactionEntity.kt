package com.example.zemoga.data.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TransactionEntity : RealmObject() {
    @PrimaryKey
    var id: Int? = 0
    var transactionObj: String? = null
}