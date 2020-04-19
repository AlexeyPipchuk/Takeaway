package com.example.takeaway.app

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T : TakeawayView> {

    private val compositeDisposable = CompositeDisposable()

    protected var view: T? = null

    @Suppress("UNCHECKED_CAST")
    fun attachView(view: T) {
        view as? T ?: throw IllegalArgumentException("Presenter can't attach this view")
        this.view = view
        onViewAttach()
    }

    fun detachView() {
        onViewDetach()
        this.view = null
        compositeDisposable.clear()
    }

    protected open fun onViewAttach() = Unit

    protected open fun onViewDetach() = Unit

    fun Disposable.addToDisposable() {
        compositeDisposable.add(this)
    }
}