package com.example.tvmazeapp.app.shows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvmazeapp.R
import com.example.tvmazeapp.app.episodes.EpisodeDetailsActivity
import com.example.tvmazeapp.app.episodes.EpisodesAdapter
import com.example.tvmazeapp.data.repository.ShowsRepository
import com.example.tvmazeapp.data.repository.remote.WebServiceRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_show_details.*

class ShowDetailsActivity : AppCompatActivity() {

    private val adapter: EpisodesAdapter by lazy {
        EpisodesAdapter(this)
    }

    private val repository: ShowsRepository by lazy {
        WebServiceRepository.getInstance()
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)

        initRecyclerView()

        textViewShowTitle.setText(intent.getStringExtra("showName"))
        toolbar.title = intent.getStringExtra("showName")
        textViewShowDescription.setText(intent.getStringExtra("showDescription"))
        loadEpisodes(intent.getIntExtra("showId", 1))

    }

    private fun loadEpisodes(showId: Int) {
        compositeDisposable.add(
            repository.getEpisodes(showId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    progressBarShows.visibility = View.VISIBLE
                    recyclerViewEpisodes.visibility = View.GONE
                }
                .subscribe({
                    adapter.updateItens(ArrayList(it))
                }, {
                    Toast.makeText(
                        this,
                        "Houve um problema ao recuperar os episódios",
                        Toast.LENGTH_SHORT
                    ).show()
                }, {
                    progressBarShows.visibility = View.GONE
                    recyclerViewEpisodes.visibility = View.VISIBLE
                })
        )
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
