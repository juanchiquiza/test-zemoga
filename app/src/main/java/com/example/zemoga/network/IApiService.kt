package com.example.zemoga.network

import com.example.zemoga.data.dto.PostDTO
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface IApiService {

    @GET("/posts")
    fun getPosts(
    ): Observable<List<PostDTO>>

    @GET("/posts/{id}")
    fun getPost(
        @Path("id") id: Int,
    ): Observable<PostDTO>
}