package com.example.tvmazeapp.app.episodes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.tvmazeapp.R
import com.example.tvmazeapp.data.repository.ShowsRepository
import com.example.tvmazeapp.data.repository.remote.WebServiceRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_episode_details.*

class EpisodeDetailsActivity : AppCompatActivity() {


    private val repository: ShowsRepository by lazy {
        WebServiceRepository.getInstance()
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_details)

        loadEpisode(intent.getIntExtra("episodeId", 0))

    }

    private fun loadEpisode(episodeId: Int) {
        compositeDisposable.add(
            repository.getEpisodeDetails(episodeId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    scrollViewRoot.visibility = View.INVISIBLE
                    progressBarShows.visibility = View.VISIBLE
                }
                .subscribe({
                    toolbar.title = it.name
                    textViewEpisodeTitle.setText(it.name)
                    textViewEpisodeDescription.setText(it.summary)
                }, {
                    Toast.makeText(
                        this,
                        "Houve um problema ao recuperar informaçeõs do episódio",
                        Toast.LENGTH_SHORT
                    ).show()
                }, {
                    progressBarShows.visibility = View.GONE
                    scrollViewRoot.visibility = View.VISIBLE
                })
        )
    }
}
