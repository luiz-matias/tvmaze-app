package com.example.tvmazeapp.repository

interface SeriesRepository {

    fun getSeries(page: Int, size: Int)

}