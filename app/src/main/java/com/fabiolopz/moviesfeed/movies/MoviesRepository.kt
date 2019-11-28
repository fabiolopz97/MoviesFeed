package com.fabiolopz.moviesfeed.movies

import com.fabiolopz.moviesfeed.http.MoviesApiService
import com.fabiolopz.moviesfeed.http.MoviesExtraInfoApiService
import com.fabiolopz.moviesfeed.http.apimodel.OmdbApi
import com.fabiolopz.moviesfeed.http.apimodel.Result
import com.fabiolopz.moviesfeed.http.apimodel.TopMoviesRated
import io.reactivex.Observable

class MoviesRepository(
    private val mService: MoviesApiService,
    private val eService: MoviesExtraInfoApiService
): Repository {
    private var countries: MutableList<String> = ArrayList()
    private var results: MutableList<Result> = ArrayList()
    private var lastTimestamp: Long = System.currentTimeMillis()

    private fun isUpdated() =
        (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME

    override fun getResultFromNetwork(): Observable<Result> {
        val topMoviesRateObservable: Observable<TopMoviesRated> = mService
            .getTopMoviesRated(1)
            .concatWith(mService.getTopMoviesRated(2))
            .concatWith(mService.getTopMoviesRated(3))
        return topMoviesRateObservable.concatMap {
            topMoviesRate ->
            Observable.fromIterable(topMoviesRate.results)
        }.doOnNext {
            result -> results.add(result)
        }
    }

    override fun getResultFromCache(): Observable<Result> {
        return if(isUpdated()){
            Observable.fromIterable(results)
        } else {
            lastTimestamp = System.currentTimeMillis()
            results.clear()
            Observable.empty()
        }
    }

    override fun getResultData(): Observable<Result> {
        return getResultFromCache().switchIfEmpty(getResultFromNetwork())
    }

    override fun getCountryFromNetwork(): Observable<String> {
        return getResultFromNetwork()
            .concatMap {
                result -> eService.getExtraInfoMovie(result.title!!)
            }.concatMap {
                omdbApi ->
                if(omdbApi.country == null){
                    Observable.just("Desconocido")
                } else {
                    Observable.just( omdbApi.country!!)
                }
            }.doOnNext {
                country -> countries.add(country)
            }
    }

    override fun getCountryFromCache(): Observable<String> {
        return if(isUpdated()){
            Observable.fromIterable(countries)
        } else {
            lastTimestamp = System.currentTimeMillis()
            countries.clear()
            Observable.empty()
        }
    }

    override fun getCountryData(): Observable<String> {
        return getCountryFromCache().switchIfEmpty(getCountryFromNetwork())
    }

    companion object{
        const val CACHE_LIFETIME: Long = 20 * 1000 //Cache que durará 20 segundos
    }
}