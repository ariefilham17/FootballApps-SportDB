package com.example.footballapps.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.footballapps.R
import com.example.footballapps.presenters.MainPresenter
import com.example.footballapps.views.fragments.FavoriteHomeFragment
import com.example.footballapps.views.fragments.MatchHomeFragment
import com.example.footballapps.views.fragments.TeamHomeFragment
import com.example.footballapps.views.interfaces.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {
    var presenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // -------------------------------------------------------------------------------------------------------------
        initPresenter()
        onAttachView(savedInstanceState)
        // -------------------------------------------------------------------------------------------------------------
    }

    private fun bottomNavigationHandler(savedInstanceState: Bundle?) {
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_match -> {
                    presenter?.showFragment(MatchHomeFragment(),savedInstanceState)
                }
                R.id.navigation_team -> {
                    presenter?.showFragment(TeamHomeFragment(),savedInstanceState)
                }
                R.id.navigation_favorite -> {
                    presenter?.showFragment(FavoriteHomeFragment(), savedInstanceState)
                }
            }
            true
        }
        navigation.selectedItemId = R.id.navigation_match
    }

    private fun initPresenter() {
        presenter = MainPresenter()
    }

    override fun onShowFragment(mFragment: androidx.fragment.app.Fragment, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, mFragment, mFragment::class.java.simpleName)
                .commit()
        }
    }

    override fun onAttachView(savedInstanceState: Bundle?) {
        presenter?.onAttach(this)
        bottomNavigationHandler(savedInstanceState)
    }

    override fun onDetachView() {
        presenter?.onDetach()
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

}