package com.fabiolopz.moviesfeed.movies

import com.fabiolopz.moviesfeed.http.MoviesApiService
import com.fabiolopz.moviesfeed.http.MoviesExtraInfoApiService
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {
    @Provides
    fun provideMoviesPresenter(moviesModel: MoviesMVP.Model): MoviesMVP.Presenter {
        return MoviesPresenter(moviesModel)
    }

    @Provides
    fun provideMovieModel(repository: Repository): MoviesMVP.Model {
        return MoviesModel(repository)
    }

    @Provides
    fun provideMovieRepository(
        moviesApiService: MoviesApiService,
        moviesExtraInfoApiService: MoviesExtraInfoApiService
    ): Repository {
        return MoviesRepository(moviesApiService, moviesExtraInfoApiService)
    }
}