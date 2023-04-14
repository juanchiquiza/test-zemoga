package com.example.zemoga.data.repositories.posts

import com.example.zemoga.data.dto.PostDTO
import com.example.zemoga.data.entities.PostEntity
import io.reactivex.Observable

interface IPostsRepository {
    fun getPosts(): Observable<List<PostDTO>>?
    fun getPost(id: Int): Observable<PostDTO>?
    fun savePost(entity: PostEntity): Observable<Boolean>?
    fun getFavoritePost(id: Int): PostEntity?
    fun getPostsDB(): Observable<List<PostEntity>>?
    fun deleteFavoritePost(id: Int): Observable<Boolean>?
    fun deleteAllPosts(): Observable<Boolean>?
}