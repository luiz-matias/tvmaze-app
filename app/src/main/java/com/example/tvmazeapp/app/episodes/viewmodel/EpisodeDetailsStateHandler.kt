package com.example.tvmazeapp.app.episodes.viewmodel

import com.example.tvmazeapp.data.model.Episode

sealed class EpisodeDetailsStateHandler {
    object LoadingEpisode: EpisodeDetailsStateHandler()
    data class EpisodeLoaded(val episode: Episode): EpisodeDetailsStateHandler()
    data class Error(val error: String): EpisodeDetailsStateHandler()
}