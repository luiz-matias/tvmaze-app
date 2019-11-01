package com.example.tvmazeapp.data.repository.remote

import android.content.Context
import com.example.tvmazeapp.data.model.Episode
import com.example.tvmazeapp.data.model.SearchResponseData
import com.example.tvmazeapp.data.model.Show
import com.example.tvmazeapp.data.repository.ShowsRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class WebServiceRepository : ShowsRepository {

    companion object {
        private lateinit var instance: WebServiceRepository

        @Synchronized
        fun getInstance(context: Context): WebServiceRepository {
            instance = WebServiceRepository()
            return instance
        }
    }

    private val webService by lazy {
        WebService.getInstance().create()
    }

    override fun getShows(search: String): Observable<SearchResponseData> {
        return webService.getShows(search).subscribeOn(Schedulers.io())
    }

    override fun getShowDetails(showId: Int): Observable<Show> {
        return webService.getShowDetails(showId).subscribeOn(Schedulers.io())
    }

    override fun getEpisodes(showId: Int): Observable<List<Episode>> {
        return webService.getEpisodes(showId).subscribeOn(Schedulers.io())
    }

    override fun getEpisodeDetails(episodeId: Int): Observable<Episode> {
        return webService.getEpisodeDetails(episodeId).subscribeOn(Schedulers.io())
    }


}