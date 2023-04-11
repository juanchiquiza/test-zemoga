package com.example.zemoga.data.repositories.transaction

import com.example.zemoga.data.database.DBHelper
import com.example.zemoga.di.transaction.DaggerITransactionComponent
import com.example.zemoga.data.dto.TransactionDTO
import com.example.zemoga.data.entities.TransactionEntity
import com.example.zemoga.network.ApiFactory
import com.example.zemoga.di.transaction.TransactionModule
import io.reactivex.Observable
import javax.inject.Inject

class TransactionRepository: ITransactionRepository {

    @Inject
    lateinit var databaseHelper: DBHelper

    init {
        DaggerITransactionComponent.builder().transactionModule(TransactionModule()).build().inject(this)
    }

    override fun getTransaction(): Observable<List<TransactionDTO>>? {
        return ApiFactory.build()?.getTransactions()?.flatMap { responseDto ->
            Observable.just(responseDto)
        }
    }

    override fun saveTransaction(entity: TransactionEntity): Observable<Boolean>? {
        return databaseHelper.saveTransaction(entity)
    }

    override fun getTransactionDB(): Observable<List<TransactionEntity>>? {
        return databaseHelper.getTransactions()
    }

    override fun deleteTransaction(id: Int): Observable<Boolean>? {
        return databaseHelper.deleteTransaction(id)
    }

    override fun deleteAllTransaction(): Observable<Boolean>? {
        return databaseHelper.deleteAllTransactions()
    }
}