package com.grivos.podcastsratings.main.presentation

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.core.view.isVisible
import com.grivos.podcastsratings.R
import com.grivos.podcastsratings.main.domain.model.MainDisplayMode
import com.grivos.podcastsratings.main.domain.model.MainDisplayMode.NoAccessibility
import com.grivos.podcastsratings.main.domain.model.MainDisplayMode.Normal
import com.grivos.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), MainView {

    private val presenter: MainPresenter by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainGoToSettings.setOnClickListener { presenter.onClickGoToSettings() }
        presenter.attachView(this, lifecycle)
    }

    override fun goToSystemSettings() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        startActivity(intent)
    }

    override fun setDisplayMode(displayMode: MainDisplayMode) {
        when (displayMode) {
            Normal -> {
                mainTitle.isVisible = true
                mainGroupNoAccessibility.isVisible = false
            }
            NoAccessibility -> {
                mainTitle.isVisible = false
                mainGroupNoAccessibility.isVisible = true
            }
        }
    }
}
