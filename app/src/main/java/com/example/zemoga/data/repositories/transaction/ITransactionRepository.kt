package com.example.zemoga.data.repositories.transaction

import com.example.zemoga.data.dto.TransactionDTO
import com.example.zemoga.data.entities.TransactionEntity
import io.reactivex.Observable

interface ITransactionRepository {
    fun getTransaction(): Observable<List<TransactionDTO>>?
    fun saveTransaction(entity: TransactionEntity): Observable<Boolean>?
    fun getTransactionDB(): Observable<List<TransactionEntity>>?
    fun deleteTransaction(id: Int): Observable<Boolean>?
    fun deleteAllTransaction(): Observable<Boolean>?
}