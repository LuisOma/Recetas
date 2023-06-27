package com.example.recipes.data.network.retrofit.implement

import com.example.recipes.data.network.retrofit.response.RecipesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RecipesNetwork {
    @GET("getRecipes")
    @Headers("Content-Type: application/json")
    suspend fun getRecipes(
    ): Response<RecipesResponse>
}