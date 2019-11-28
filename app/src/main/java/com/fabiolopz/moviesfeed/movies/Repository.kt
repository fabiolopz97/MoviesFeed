package com.fabiolopz.moviesfeed.movies

import com.fabiolopz.moviesfeed.http.apimodel.Result
import io.reactivex.Observable

interface Repository {
    fun getResultFromNetwork():Observable<Result>
    fun getResultFromCache():Observable<Result>
    fun getResultData():Observable<Result>

    fun getCountryFromNetwork():Observable<String>
    fun getCountryFromCache():Observable<String>
    fun getCountryData():Observable<String>
}