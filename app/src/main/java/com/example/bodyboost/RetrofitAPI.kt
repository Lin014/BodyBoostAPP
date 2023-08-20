package com.example.bodyboost

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitAPI {

    //------------------------authentication------------------------------
    @POST("api/authentication/authenticationForgetPasswordCode/")
    fun testUserPwd(
        @Body code:String,
        @Body userID:Int
    ): Call<Void>

    @POST("api/authentication/authenticationRegisterCode/")
    fun testRegisterNum(
        @Body registerNumData: RegisterNumData
    ): Call<Void>
    data class RegisterNumData(
        val code: String,
        val userID: Int
    )
    @POST("api/authentication/resendRegisterMail/")
    fun resendVerificationMail(@Body account:String): Call<Void>

    @POST("api/authentication/sendForgetPasswordMail/")
    fun forgetPwdMail(@Body account:String): Call<String>

    //------------------------user------------------------------
    @GET("api/users/{id}/")
    fun getSpecialUserInfo(
        @Path("id") id:Int
    ): Call<Users>
    @POST("api/users/add/")
    fun addUser(@Body addUserData: AddUserData): Call<Users>
    data class AddUserData(
        val account: String,
        val password: String,
        val email:String
    )
    @DELETE("api/users/delete/{id}/")
    fun deleteUser(
        @Path("id") id:Int
    ):Call<Users>

    @POST("api/users/login/google/")
    fun loginGoogle(
        @Query("email") email:String
    ):Call<Users>

    @POST("api/users/login/normal/")
    fun loginNormal(@Body loginData: LoginData): Call<Users>
    data class LoginData(
        val account: String,
        val password: String
    )
    @PUT("api/users/update/email/{id}/")
    fun updateEmail(
        @Path("id") id:Int,
        @Body email:Users
    ):Call<Void>
    @PUT("api/users/update/password/{id}/")
    fun updatePwd(
        @Path("id") id:Int,
        @Body password:Users
    ):Call<Void>

    //------------------------profile------------------------------
    @GET("api/profile/")
    fun getUserProfile(
        @Path("id") id: Int
    ): Call<Profile>

    @POST("api/profile/add/")
    fun addProfile(@Body profileData: ProfileData): Call<Profile>
    data class ProfileData(
        val name: String,
        val gender: Int,
        val birthday: String,
        val height: Double,
        val weight: Double,
        val userID: Int
    )
    //------------------------DailyBonus------------------------------
    @POST("api/dailybonus/add/{id}/")
    fun addDailyBonus(@Path("id") userId: Int, @Body dailyBonusData: DailyBonusData): Call<DailyBonus>
    data class DailyBonusData(
        val id: Int,
        val date: String,
        val user_id: Int
    )

    @GET("api/dailybonus/{id}/")
    fun searchdailybonus(
        @Path("id") id: Int
    ): Call<DailyBonus>


    //------------------------Setting------------------------------
    @PUT("api/setting/update/{id}")
    fun UpdateSetting(
        @Path("id") id: Int,
        @Body settingData: SettingData
    ): Call<Void>
    data class SettingData(
        val theme: String,
        val anim_char_name: String,
        val is_alerted: Boolean,
        val alert_day: String,
        val alert_time: String
    )

}

