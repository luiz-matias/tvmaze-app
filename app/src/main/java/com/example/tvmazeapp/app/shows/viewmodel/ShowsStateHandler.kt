package com.example.tvmazeapp.app.shows.viewmodel

import com.example.tvmazeapp.data.model.Show

sealed class ShowsStateHandler {
    object LoadingShows: ShowsStateHandler()
    data class ShowsLoaded(val shows: List<Show>): ShowsStateHandler()
    data class Error(val error: String): ShowsStateHandler()
}