package com.fabiolopz.moviesfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabiolopz.moviesfeed.movies.ListAdapter
import com.fabiolopz.moviesfeed.movies.MoviesMVP
import com.fabiolopz.moviesfeed.movies.ViewModel
import com.fabiolopz.moviesfeed.root.App
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MoviesMVP.View {
    @set:Inject
    var presenter: MoviesMVP.Presenter? = null
    private lateinit var listAdapter: ListAdapter
    private lateinit var resultList: MutableList<ViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).component.inject(this)

        resultList = ArrayList()
        val linearLayoutManager = LinearLayoutManager(this)

        listAdapter = ListAdapter(resultList, R.layout.movie_list_item, this)

        recycler_view_movies.adapter = listAdapter
        recycler_view_movies.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        recycler_view_movies.itemAnimator = DefaultItemAnimator()
        recycler_view_movies.setHasFixedSize(true)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler_view_movies.layoutManager = linearLayoutManager
    }

    override fun onResume() {
        super.onResume()
        presenter?.setView(this)
        presenter?.loadData()
    }

    override fun onStop() {
        super.onStop()
        presenter?.rxJavaUnsuscribe()
        resultList.clear()
        listAdapter.notifyDataSetChanged()
    }

    companion object {
        val TAG: String = MainActivity::class.java.name
    }

    override fun updateData(viewModel: ViewModel) {
        resultList.add(viewModel)
        listAdapter.notifyItemInserted(resultList.size - 1)
        Log.d(TAG, "Información nueva: ${viewModel.name}")
    }

    override fun showSnackBar(s: String) {
        val rootView: ViewGroup = findViewById(R.id.activity_root_view)
        Snackbar.make(rootView, "Información descargada con éxito", Snackbar.LENGTH_SHORT).show()
    }
}
