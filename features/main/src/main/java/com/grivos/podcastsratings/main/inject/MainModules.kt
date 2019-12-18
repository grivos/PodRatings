package com.grivos.podcastsratings.main.inject

import com.grivos.podcastsratings.main.data.repository.MainRepositoryImpl
import com.grivos.podcastsratings.main.domain.repository.MainRepository
import com.grivos.podcastsratings.main.presentation.MainPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object MainModules {
    fun injectFeature() = loadFeature
}

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            viewModelModule,
            repositoryModule
        )
    )
}

val viewModelModule: Module = module {
    viewModel { MainPresenter(mainRepository = get()) }
}

val repositoryModule: Module = module {
    single { MainRepositoryImpl(androidContext()) as MainRepository }
}
