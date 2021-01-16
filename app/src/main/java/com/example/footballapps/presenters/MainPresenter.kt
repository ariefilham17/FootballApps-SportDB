package com.example.footballapps.presenters

import android.os.Bundle
import com.example.footballapps.views.interfaces.HandlerMainView
import com.example.footballapps.views.interfaces.MainView

class MainPresenter : HandlerMainView<MainView> {
    private var mView: MainView? = null

    override fun onAttach(view: MainView) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }

    fun showFragment(mFragment: androidx.fragment.app.Fragment, savedInstanceState: Bundle?) {
        mView?.onShowFragment(mFragment, savedInstanceState)
    }
}