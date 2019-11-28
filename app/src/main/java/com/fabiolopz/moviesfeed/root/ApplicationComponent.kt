package com.fabiolopz.moviesfeed.root

import com.fabiolopz.moviesfeed.MainActivity
import com.fabiolopz.moviesfeed.http.MovieExtraInfoApiModule
import com.fabiolopz.moviesfeed.http.MovieTitleApiModule
import com.fabiolopz.moviesfeed.movies.MoviesModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    MoviesModule::class,
    MovieTitleApiModule::class,
    MovieExtraInfoApiModule::class
])
interface ApplicationComponent {
    fun inject(target: MainActivity)
}