package com.example.tvmazeapp.app.shows.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tvmazeapp.app.util.SingleLiveEvent
import com.example.tvmazeapp.data.repository.ShowsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ShowsViewModel(application: Application, private val showsRepository: ShowsRepository) :
    AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    val stateHandler = SingleLiveEvent<ShowsStateHandler>()

    fun getShows(search: String) {
        compositeDisposable.add(
            showsRepository.getShows(search)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    stateHandler.value = ShowsStateHandler.LoadingShows
                }
                .subscribe({
                    stateHandler.value = ShowsStateHandler.ShowsLoaded(it)
                }, {
                    stateHandler.value = ShowsStateHandler.Error(it.message!!)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}