package com.grivos.podcastsratings.main.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.grivos.podcastsratings.main.domain.model.MainDisplayMode
import com.grivos.podcastsratings.main.domain.repository.MainRepository
import com.grivos.presentation.base.BaseViewModelPresenter
import com.grivos.presentation.base.MvpView

interface MainView : MvpView {
    fun goToSystemSettings()
    fun setDisplayMode(displayMode: MainDisplayMode)
}

class MainPresenter(
    private val mainRepository: MainRepository
) : BaseViewModelPresenter<MainView>() {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onViewResumed() {
        val displayMode = if (mainRepository.isAccessibilityEnabled) {
            MainDisplayMode.Normal
        } else {
            MainDisplayMode.NoAccessibility
        }
        mvpView?.setDisplayMode(displayMode)
    }

    fun onClickGoToSettings() {
        mvpView?.goToSystemSettings()
    }
}
