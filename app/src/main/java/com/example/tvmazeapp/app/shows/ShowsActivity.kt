package com.example.tvmazeapp.app.shows

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvmazeapp.R
import com.example.tvmazeapp.data.repository.ShowsRepository
import com.example.tvmazeapp.data.repository.remote.WebServiceRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_shows.*

class ShowsActivity : AppCompatActivity() {

    private val adapter: ShowsAdapter by lazy {
        ShowsAdapter(this)
    }

    private val repository: ShowsRepository by lazy {
        WebServiceRepository.getInstance()
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shows)

        initRecyclerView()

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

    private fun searchShows(search: String) {
        compositeDisposable.add(
            repository.getShows(search)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    progressBarShows.visibility = View.VISIBLE
                    recyclerViewShows.visibility = View.GONE
                }
                .subscribe({
                    adapter.updateItens(ArrayList(it))
                }, {
                    Toast.makeText(
                        this,
                        "Houve um problema ao recuperar a lista de shows",
                        Toast.LENGTH_SHORT
                    ).show()
                }, {
                    progressBarShows.visibility = View.GONE
                    recyclerViewShows.visibility = View.VISIBLE
                })
        )
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
