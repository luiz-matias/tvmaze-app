package com.example.tvmazeapp.app.shows.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvmazeapp.R
import com.example.tvmazeapp.app.shows.viewmodel.ShowsStateHandler
import com.example.tvmazeapp.app.shows.viewmodel.ShowsViewModel
import kotlinx.android.synthetic.main.activity_shows.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowsActivity : AppCompatActivity() {

    private val adapter: ShowsAdapter by lazy {
        ShowsAdapter(this)
    }

    private val viewModel: ShowsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shows)

        initRecyclerView()
        initObserver()

        searchViewShows.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchShows(searchViewShows.query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    private fun initObserver() {
        viewModel.stateHandler.observe(this, Observer {
            when (it) {
                is ShowsStateHandler.LoadingShows -> {
                    showLoading(true)
                }
                is ShowsStateHandler.Error -> {
                    showLoading(false)
                    Toast.makeText(
                        this,
                        "Houve um problema ao recuperar a lista de shows",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ShowsStateHandler.ShowsLoaded -> {
                    showLoading(false)
                    adapter.updateItens(ArrayList(it.shows))
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        progressBarShows.visibility = if (isLoading) View.VISIBLE else View.GONE
        recyclerViewShows.visibility = if (!isLoading) View.VISIBLE else View.GONE
    }

    private fun searchShows(search: String) {
        viewModel.getShows(search)
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerViewShows.adapter = adapter
        recyclerViewShows.layoutManager = linearLayoutManager

        adapter.onItemClickListener = {
            startActivity(
                Intent(
                    this@ShowsActivity,
                    ShowDetailsActivity::class.java
                )
                    .putExtra("showName", it.name)
                    .putExtra("showDescription", it.summary)
                    .putExtra("showId", it.id)
            )
        }

    }
}
