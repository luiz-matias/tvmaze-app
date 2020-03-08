package com.example.tvmazeapp.app.episodes.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tvmazeapp.R
import com.example.tvmazeapp.app.episodes.viewmodel.EpisodeDetailsStateHandler
import com.example.tvmazeapp.app.episodes.viewmodel.EpisodeDetailsViewModel
import com.example.tvmazeapp.data.model.Episode
import kotlinx.android.synthetic.main.activity_episode_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EpisodeDetailsActivity : AppCompatActivity() {

    private val viewModel: EpisodeDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_details)

        initObserver()
        loadEpisode(intent.getIntExtra("episodeId", 0))
    }

    private fun initObserver() {
        viewModel.stateHandler.observe(this, Observer {
            when (it) {
                is EpisodeDetailsStateHandler.LoadingEpisode -> {
                    showLoading(true)
                }
                is EpisodeDetailsStateHandler.Error -> {
                    showLoading(false)
                    Toast.makeText(
                        this,
                        "Houve um problema ao recuperar informaçeõs do episódio",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is EpisodeDetailsStateHandler.EpisodeLoaded -> {
                    showLoading(false)
                    updateEpisodeDetails(it.episode)
                }
            }
        })
    }

    private fun updateEpisodeDetails(episode: Episode) {
        toolbar.title = episode.name
        textViewEpisodeTitle.setText(episode.name)
        textViewEpisodeDescription.setText(episode.summary)
    }

    private fun showLoading(isLoading: Boolean) {
        progressBarShows.visibility = if (isLoading) View.VISIBLE else View.GONE
        scrollViewRoot.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun loadEpisode(episodeId: Int) {
        viewModel.getEpisode(episodeId)
    }
}
