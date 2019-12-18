package com.grivos.presentation.base

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T : MvpView> {

    protected var mvpView: T? = null
    private val disposables = CompositeDisposable()

    @CallSuper
    open fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    protected fun detachView() {
        disposables.clear()
        mvpView = null
    }

    protected fun launch(action: () -> Disposable) {
        disposables.add(action())
    }

    protected val isViewAttached: Boolean
        get() = mvpView != null
}
