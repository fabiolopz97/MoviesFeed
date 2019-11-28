package com.fabiolopz.moviesfeed.http

import com.fabiolopz.moviesfeed.http.apimodel.OmdbApi
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesExtraInfoApiService {
    @GET("/")
    fun getExtraInfoMovie(@Query("t") title:String): Observable<OmdbApi>
}