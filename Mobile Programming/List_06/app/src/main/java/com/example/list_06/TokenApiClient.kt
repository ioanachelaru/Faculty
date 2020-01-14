package com.example.list_06

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TokenApiClient {

    // Get token
    @GET("login")
    fun getToken(): Observable<Token>

    companion object {

        fun create(): TokenApiClient {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://demo7402239.mockable.io/")
                .build()

            return retrofit.create(TokenApiClient::class.java)

        }
    }

}