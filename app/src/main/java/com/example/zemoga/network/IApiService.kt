package com.example.zemoga.network

import com.example.zemoga.data.dto.TransactionDTO
import io.reactivex.Observable
import retrofit2.http.GET

interface IApiService {

    @GET("/transactions")
    fun getTransactions(
    ): Observable<List<TransactionDTO>>
}