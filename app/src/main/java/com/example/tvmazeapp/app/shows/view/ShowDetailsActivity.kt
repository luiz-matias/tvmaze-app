package com.example.tvmazeapp.app.shows.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvmazeapp.R
import com.example.tvmazeapp.app.episodes.view.EpisodeDetailsActivity
import com.example.tvmazeapp.app.episodes.view.EpisodesAdapter
import com.example.tvmazeapp.app.shows.viewmodel.ShowDetailsStateHandler
import com.example.tvmazeapp.app.shows.viewmodel.ShowDetailsViewModel
import kotlinx.android.synthetic.main.activity_show_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowDetailsActivity : AppCompatActivity() {

    private val adapter: EpisodesAdapter by lazy {
        EpisodesAdapter(this)
    }

    private val viewModel: ShowDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)

        initRecyclerView()
        initObserver()

        textViewShowTitle.setText(intent.getStringExtra("showName"))
        toolbar.title = intent.getStringExtra("showName")
        textViewShowDescription.setText(intent.getStringExtra("showDescription"))
        loadEpisodes(intent.getIntExtra("showId", 1))

    }

    private fun initObserver() {
        viewModel.stateHandler.observe(this, Observer {
            when (it) {
                is ShowDetailsStateHandler.LoadingEpisodes -> {
                    showLoading(true)
                }
                is ShowDetailsStateHandler.Error -> {
                    showLoading(false)
                    Toast.makeText(
                        this,
                        "Houve um problema ao recuperar os episódios",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is ShowDetailsStateHandler.EpisodesLoaded -> {
                    showLoading(false)
                    adapter.updateItens(ArrayList(it.episodes))
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        progressBarShows.visibility = if (isLoading) View.VISIBLE else View.GONE
        recyclerViewEpisodes.visibility = if (!isLoading) View.VISIBLE else View.GONE
    }

    private fun loadEpisodes(showId: Int) {
        viewModel.getEpisodes(showId)
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerViewEpisodes.adapter = adapter
        recyclerViewEpisodes.layoutManager = linearLayoutManager

        adapter.onItemClickListener = {
            startActivity(
                Intent(
                    this@ShowDetailsActivity,
                    EpisodeDetailsActivity::class.java
                ).putExtra("episodeId", it.id)
            )
        }

    }
}
