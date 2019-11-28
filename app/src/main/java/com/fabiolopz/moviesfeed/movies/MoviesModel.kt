package com.fabiolopz.moviesfeed.movies

import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class MoviesModel(
    private var repository: Repository
): MoviesMVP.Model {

    override fun result(): Observable<ViewModel> {
        return Observable.zip(repository.getResultData(), repository.getCountryData(), BiFunction {
            //TODO: cambiar result.toString cuando tengamos el Pojo de datos
            t1, t2 ->
            ViewModel(t1.title!!, t2)
        })
    }
}