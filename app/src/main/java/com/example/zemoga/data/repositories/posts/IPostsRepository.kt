package com.example.zemoga.data.repositories.posts

import com.example.zemoga.data.dto.PostDTO
import com.example.zemoga.data.entities.TransactionEntity
import io.reactivex.Observable

interface IPostsRepository {
    fun getPosts(): Observable<List<PostDTO>>?
    fun getPost(id: Int): Observable<PostDTO>?
    fun saveTransaction(entity: TransactionEntity): Observable<Boolean>?
    fun getTransactionDB(): Observable<List<TransactionEntity>>?
    fun deleteTransaction(id: Int): Observable<Boolean>?
    fun deleteAllTransaction(): Observable<Boolean>?
}