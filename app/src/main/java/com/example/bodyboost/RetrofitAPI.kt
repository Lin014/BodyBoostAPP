package com.example.bodyboost

import com.example.bodyboost.entity.CustomFood
import com.example.bodyboost.entity.DietRecord
import com.example.bodyboost.entity.Food
import com.example.bodyboost.entity.FoodType
import com.example.bodyboost.entity.Store
import com.example.bodyboost.entity.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitAPI {

// ------food---------------------------------------------------------------------------------------
    @GET("api/food/")
    fun getAllFood(
        @Query("page") page:Int,
        @Query("page_size") page_size:Int
    ): Call<List<Food>>

    @POST("api/food/add/")
    fun addFood( @Body addFoodData: AddFoodData ): Call<Food>
    data class AddFoodData(
        val name: String,
        val calorie: Float,
        val size: Float,
        val unit: String,
        val protein: Float,
        val fat: Float,
        val carb: Float,
        val sodium: Float,
        val modify: Boolean,
        val food_type_id: Int,
        val store_id: Int
    )

    @DELETE("api/food/delete/{id}")
    fun deleteFood( @Path("id") id:String ): Call<Food>

    @PUT("api/food/update/{id}")
    fun updateFood(
        @Path("id") id:String,
        @Body updateFood:Food
    ): Call<Void>

// ------food type----------------------------------------------------------------------------------
    @GET("api/foodtype/")
    fun getFoodType(): Call<List<FoodType>>

    @POST("api/foodtype/add/")
    fun addFoodType( @Body addFoodType: FoodTypeData ): Call<FoodType>
    data class FoodTypeData(
        val type:String
    )

    @DELETE("api/foodtype/delete/{id}")
    fun deleteFoodType( @Path("id") id:String ): Call<FoodType>

    @PUT("api/foodtype/update/{id}")
    fun updateFoodType(
        @Path("id") id:String,
        @Body type:FoodType
    ): Call<Void>

// ------search food--------------------------------------------------------------------------------
    @GET("api/searchfood/foodtype/{id}/{userId}")
    fun searchFoodById(
        @Path("id") id:String,
        @Path("userId") userId:String,
        @Query("page") page:Int,
        @Query("page_size") page_size: Int
        ): Call<List<Food>>

    @GET("api/searchfood/store/{id}/{userId}")
    fun searchFoodByStore(
        @Query("page") page:Int,
        @Query("page_size") page_size: Int,
        @Path("id") id:String,
        @Path("userId") userId:String
    ): Call<Food>

    @GET("api/searchfood/word/{userId}")
    fun searchFoodByWord(
        @Query("name") name:String,
        @Query("page") page:Int,
        @Query("page_size") page_size: Int,
        @Path("userId") userId:String
    ): Call<Food>

// ------store--------------------------------------------------------------------------------------
    @GET("api/store/")
    fun getAllStore(): Call<List<Store>>

    @POST("api/store/add/")
    fun addStore( @Body name: StoreData ): Call<Store>
    data class StoreData(
        val name:String
    )

    @DELETE("api/store/delete/{id}")
    fun deleteStore( @Path("id") id:String ): Call<Store>

    @PUT("api/store/update/{id}")
    fun updateStore(
        @Path("id") id:String,
        @Body name:Store
    ): Call<Void>

// ------custom food--------------------------------------------------------------------------------
    @GET("api/customfood")
    fun getCustomFood(
        @Query("page") page:Int,
        @Query("page_size") page_size:Int
    ): Call<CustomFood>

    @POST("api/customfood/add")
    fun addCustomFood( @Body addCustomFoodData: CustomFoodData )
    data class CustomFoodData(
        val name: String,
        val calorie: Float,
        val size: Float,
        val unit: String,
        val protein: Float,
        val fat: Float,
        val carb: Float,
        val sodium: Float,
        val modify: Boolean,
        val food_type_id: Int,
        val store_id: Int,
        val user_id: Int
    )

    @DELETE("api/customfood/delete/{id}")
    fun deleteCustomFood( @Path("id") id:String ): Call<CustomFood>

    @PUT("api/customfood/update/{id}")
    fun updateCustomFood(
        @Path("id") id:String,
        @Body updateCustomFood: CustomFoodData
    ):Call<Void>

    @GET("api/customfood/{id}")
    fun getCustomFoodById(
        @Path("id") id: String,
        @Query("page") page:Int,
        @Query("page_size") page_size:Int
    ): Call<CustomFood>

// ------diet record--------------------------------------------------------------------------------
    @POST("api/dietrecord/add/")
    fun addDietRecord( @Body addDietRecordData: DietRecordData )
    data class DietRecordData(
        val date: String,
        val label: String,
        val serving_amount: Float,
        val name: String,
        val calorie: Float,
        val size: Float,
        val unit: String,
        val protein: Float,
        val fat: Float,
        val carb: Float,
        val sodium: Float,
        val modify: Boolean,
        val food_type_id: Int,
        val store_id: Int,
        val user_id: Int
    )

//    @POST("api/dietrecord/add/many")
//    fun addManyDietRecord()

    @DELETE("api/dietrecord/delete/{id}")
    fun deleteDietRecord( @Path("id") id:String ): Call<DietRecord>

    @PUT("api/dietrecord/update/{id}")
    fun updateDietRecord(
        @Path("id") id:String,
        @Body updateDietRecordData: DietRecordData
    ):Call<Void>

    @GET("api/dietrecord/{id}")
    fun getDietRecord(
        @Path("id") id:String
    ): Call<DietRecord>

// -------------------------------------------------------------------------------------------------
// ------users--------------------------------------------------------------------------------------
    @POST("api/users/login/normal/")
    fun loginNormal(@Body loginData: LoginData): Call<Users>
        data class LoginData(
            val account: String,
            val password: String
        )
}