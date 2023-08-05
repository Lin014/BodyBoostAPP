package com.example.bodyboost

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface RetrofitAPI {

    //------------------------authentication------------------------------
    @POST("/authentication/authenticationForgetPasswordCode/")
    fun testUserPwd(
        @Body code:String,
        @Body userID:Int
    ): Call<Void>

    @POST("/authentication/authenticationRegisterCode/")
    fun testRegisterNum(
        @Body code:String,
        @Body userID:Int
    ): Call<Void>

    @POST("/authentication/resendRegisterMail/")
    fun testRegisterMail(@Body account:String): Call<String>
    @POST("/authentication/sendForgetPasswordMail/")
    fun forgetPwdMail(@Body account:String): Call<String>

    //------------------------user------------------------------
    @GET("/users/")
    fun getUserInfo(
        @Path("id,account,password,email,created_type,status,created_at")
        id:Int,
        account:String,
        password:String,
        email:String,
        created_type:String,
        status:String,
        created_at:String
    ):Call<Users>

    @POST("/users/add/")
    fun addUser(
        @Body account:String,
        @Body password:String,
        @Body email:String
    ):Call<Users>

    @DELETE("/users/delete/{id}/")
    fun deleteUser(
        @Path("id") id:String
    ):Call<Users>

    @POST("/users/login/google/")
    fun loginGoogle(
        @Body email:String
    ):Call<Users>

    @POST("/users/login/normal/")
    fun loginNormal(
        @Body account:String,
        @Body password:String
    ):Call<Users>

    @PUT("/users/update/email/{id}/")
    fun updateEmail(
        @Path("id") id:String,
        @Body email:Users
    ):Call<Void>
    @PUT("/users/update/password/{id}/")
    fun updatePwd(
        @Path("id") id:String,
        @Body password:Users
    ):Call<Void>

}