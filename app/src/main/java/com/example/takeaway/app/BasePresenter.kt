package com.example.takeaway.app

abstract class BasePresenter<T : TakeawayView> {

    protected var view: T? = null

    fun attachView(view: T) {
        this.view = view
        onViewCreated()
    }

    fun detachView() {
        this.view = null
    }

    protected open fun onViewCreated() = Unit
}