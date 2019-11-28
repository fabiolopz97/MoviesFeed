package com.fabiolopz.moviesfeed.movies

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MoviesPresenter(private val model: MoviesMVP.Model): MoviesMVP.Presenter {
    private lateinit var view: MoviesMVP.View
    private var subscription: Disposable? = null

    override fun loadData() {
        subscription = model.result()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewModel ->
                    view.updateData(viewModel)
                },
                {
                    error ->
                    error.stackTrace
                    view.showSnackBar("Error al descargar las peliculas...")

                },
                {
                    view.showSnackBar("Información descargada con éxito")
                }
            )
    }

    override fun rxJavaUnsuscribe() {
        if(subscription != null && !subscription!!.isDisposed){
            subscription!!.dispose()
        }
    }

    override fun setView(view: MoviesMVP.View) {
        this.view = view
    }
}