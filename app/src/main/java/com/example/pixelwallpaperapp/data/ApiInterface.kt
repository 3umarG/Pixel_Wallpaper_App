package com.example.pixelwallpaperapp.data

import com.example.pixelwallpaperapp.pojo.WallpaperModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {

    @Headers("Authorization: 563492ad6f91700001000001904dafe43732419cac181943b64a57d4")
    @GET("search")
    suspend fun searchWallpaper(
        // Required
        @Query("query") query : String ,
        // Optional
        @Query("per_page") per_page : Int = 80
    ) : Response<WallpaperModel>
}