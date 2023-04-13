package com.example.zemoga.data.repositories.posts

import com.example.zemoga.data.dto.PostDTO
import com.example.zemoga.data.entities.PostEntity
import io.reactivex.Observable

interface IPostsRepository {
    fun getPosts(): Observable<List<PostDTO>>?
    fun getPost(id: Int): Observable<PostDTO>?
    fun savePost(entity: PostEntity): Observable<Boolean>?
    fun getTransactionDB(): Observable<List<PostEntity>>?
    fun deleteTransaction(id: Int): Observable<Boolean>?
    fun deleteAllTransaction(): Observable<Boolean>?
}