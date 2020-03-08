package com.example.tvmazeapp.app.episodes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tvmazeapp.app.util.SingleLiveEvent
import com.example.tvmazeapp.data.repository.ShowsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class EpisodeDetailsViewModel(application: Application, private val showsRepository: ShowsRepository) :
    AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    val stateHandler = SingleLiveEvent<EpisodeDetailsStateHandler>()

    fun getEpisode(episodeId: Int) {
        compositeDisposable.add(
            showsRepository.getEpisodeDetails(episodeId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    stateHandler.value = EpisodeDetailsStateHandler.LoadingEpisode
                }
                .subscribe({
                    stateHandler.value = EpisodeDetailsStateHandler.EpisodeLoaded(it)
                }, {
                    stateHandler.value = EpisodeDetailsStateHandler.Error(it.message!!)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}