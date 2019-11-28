package com.fabiolopz.moviesfeed.http


import com.fabiolopz.moviesfeed.http.apimodel.TopMoviesRated
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {
    @GET("top_rated")
    fun getTopMoviesRated(@Query("page") page:Int): Observable<TopMoviesRated>
}