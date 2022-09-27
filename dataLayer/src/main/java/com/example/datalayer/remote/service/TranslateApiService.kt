package com.example.datalayer.remote.service

import com.example.datalayer.remote.model.TranslateResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface TranslateApiService {

    @FormUrlEncoded
    @POST("v1/papago/n2mt")
    suspend fun papagoTranslate(
        @Header("Content-Type") contentType: String = "application/x-www-form-urlencoded; charset=UTF-8",
        @Header("X-Naver-Client-Id") clientId: String = "JocSM4TGQuUTnn5Z04q7",
        @Header("X-Naver-Client-Secret") clientSecret: String = "BrW74cBoAs",
        @Field("source") source: String,
        @Field("target") target: String,
        @Field("text") text: String
    ) : TranslateResponse

}