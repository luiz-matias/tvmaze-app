package com.example.tvmazeapp.di

import com.example.tvmazeapp.app.shows.viewmodel.ShowDetailsViewModel
import com.example.tvmazeapp.app.shows.viewmodel.ShowsViewModel
import com.example.tvmazeapp.data.repository.ShowsRepository
import com.example.tvmazeapp.data.repository.remote.WebService
import com.example.tvmazeapp.data.repository.remote.WebServiceRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //region ViewModels
    viewModel { ShowsViewModel(get(), get()) }
    viewModel { ShowDetailsViewModel(get(), get()) }
    //endregion

    //region Repositories
    single { WebService().create() }
    single<ShowsRepository> { WebServiceRepository(get()) }
    //endregion

}