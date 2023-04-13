package com.example.zemoga.data.repositories.posts

import com.example.zemoga.data.database.DBHelper
import com.example.zemoga.data.dto.PostDTO
import com.example.zemoga.data.entities.PostEntity
import com.example.zemoga.di.posts.DaggerIPostsComponent
import com.example.zemoga.network.ApiFactory
import com.example.zemoga.di.posts.PostsModule
import io.reactivex.Observable
import javax.inject.Inject

class PostsRepository : IPostsRepository {

    @Inject
    lateinit var databaseHelper: DBHelper

    init {
        DaggerIPostsComponent.builder().postsModule(PostsModule()).build().inject(this)
    }

    override fun getPosts(): Observable<List<PostDTO>>? {
        return ApiFactory.build()?.getPosts()?.flatMap { responseDto ->
            Observable.just(responseDto)
        }
    }

    override fun getPost(id: Int): Observable<PostDTO>? {
        return ApiFactory.build()?.getPost(id)?.flatMap { responseDto ->
            Observable.just(responseDto)
        }
    }

    override fun savePost(entity: PostEntity): Observable<Boolean>? {
        return databaseHelper.saveTransaction(entity)
    }

    override fun getTransactionDB(): Observable<List<PostEntity>>? {
        return databaseHelper.getTransactions()
    }

    override fun deleteTransaction(id: Int): Observable<Boolean>? {
        return databaseHelper.deleteTransaction(id)
    }

    override fun deleteAllTransaction(): Observable<Boolean>? {
        return databaseHelper.deleteAllTransactions()
    }
}