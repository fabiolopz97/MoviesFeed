package com.fabiolopz.moviesfeed.root

import android.app.Application
import com.fabiolopz.moviesfeed.http.MovieExtraInfoApiModule
import com.fabiolopz.moviesfeed.http.MovieTitleApiModule
import com.fabiolopz.moviesfeed.movies.MoviesModule

class App: Application() {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent
            .builder()
            //.applicationModule(ApplicationModule(this))
            .moviesModule(MoviesModule())
            .movieTitleApiModule(MovieTitleApiModule())
            .movieExtraInfoApiModule(MovieExtraInfoApiModule())
            .build()
    }


}