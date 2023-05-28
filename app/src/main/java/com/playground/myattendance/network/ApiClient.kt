package com.playground.myattendance.network

import com.playground.myattendance.model.ResponseCheckOut
import com.playground.myattendance.model.ResponseCheckin
import com.playground.myattendance.model.ResponseLogin
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiClient {

    @FormUrlEncoded
    @POST("sisindi/login.php")
    fun login (
        @Field("post_nip") nip : String,
        @Field("post_password") password : String
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("sisindi/checkin.php")
    fun checkin (
        @Field("post_nip") nip : String
    ): Call<ResponseCheckin>

    @FormUrlEncoded
    @POST("sisindi/checkout.php")
    fun checkout (
        @Field("post_nip") nip : String
    ): Call<ResponseCheckOut>
}