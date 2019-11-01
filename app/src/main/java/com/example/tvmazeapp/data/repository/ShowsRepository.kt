package com.example.tvmazeapp.data.repository

import com.example.tvmazeapp.data.model.Episode
import com.example.tvmazeapp.data.model.SearchResponseData
import com.example.tvmazeapp.data.model.Show
import io.reactivex.Observable

interface ShowsRepository {

    fun getShows(search: String) : Observable<SearchResponseData>
    fun getShowDetails(showId: Int) : Observable<Show>
    fun getEpisodes(showId: Int) : Observable<List<Episode>>
    fun getEpisodeDetails(episodeId: Int) : Observable<Episode>

}