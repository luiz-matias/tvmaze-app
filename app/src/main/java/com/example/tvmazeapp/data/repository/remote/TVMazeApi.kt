package com.example.tvmazeapp.data.repository.remote

import com.example.tvmazeapp.data.model.Episode
import com.example.tvmazeapp.data.model.SearchResponseData
import com.example.tvmazeapp.data.model.Show
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVMazeApi {

    @GET("/search/shows")
    fun getShows(@Query("q") search: String) : Observable<SearchResponseData>

    @GET("/search/shows/{id}")
    fun getShowDetails(@Path("id") showId: Int) : Observable<Show>

    @GET("/shows/{id}/episodes")
    fun getEpisodes(@Path("id") showId: Int) : Observable<List<Episode>>

    @GET("/episodes/{id}")
    fun getEpisodeDetails(@Path("id") episodeId: Int) : Observable<Episode>

}