package com.example.tvmazeapp.app.showdetails.viewmodel

import com.example.tvmazeapp.data.model.Episode

sealed class ShowDetailsStateHandler {
    object LoadingEpisodes: ShowDetailsStateHandler()
    data class EpisodesLoaded(val episodes: List<Episode>): ShowDetailsStateHandler()
    data class Error(val error: String): ShowDetailsStateHandler()
}