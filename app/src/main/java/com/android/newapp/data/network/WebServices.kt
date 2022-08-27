package com.android.newapp.data.network

import com.android.newapp.data.models.ModelMovie
import com.android.newapp.data.models.MovieDetailsModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebServices {

    @GET("discover/movie")
    suspend fun getAllMovie() : ModelMovie

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId : Int
                               ,@Query("api_key") apikey : String) : MovieDetailsModel

}