package com.example.tvmazeapp.app.shows.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tvmazeapp.app.util.SingleLiveEvent
import com.example.tvmazeapp.data.repository.ShowsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ShowDetailsViewModel(application: Application, private val showsRepository: ShowsRepository) :
    AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    val stateHandler = SingleLiveEvent<ShowDetailsStateHandler>()

    fun getEpisodes(showId: Int) {
        compositeDisposable.add(
            showsRepository.getEpisodes(showId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    stateHandler.value = ShowDetailsStateHandler.LoadingEpisodes
                }
                .subscribe({
                    stateHandler.value = ShowDetailsStateHandler.EpisodesLoaded(it)
                }, {
                    stateHandler.value = ShowDetailsStateHandler.Error(it.message!!)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}