package com.example.zemoga.network

import com.example.zemoga.data.dto.PostDTO
import io.reactivex.Observable
import retrofit2.http.GET

interface IApiService {

    @GET("/posts")
    fun getPosts(
    ): Observable<List<PostDTO>>
}