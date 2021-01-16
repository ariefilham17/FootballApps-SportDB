package com.example.footballapps.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.example.footballapps.R
import com.example.footballapps.models.dataclass.FavoriteMatch
import com.example.footballapps.presenters.FavoriteListMatchPresenter
import com.example.footballapps.views.activities.DetailMatchActivity
import com.example.footballapps.views.adapters.FavoriteMatchRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_favorite_match.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavoriteMatchFragment : androidx.fragment.app.Fragment() {

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var presenter: FavoriteListMatchPresenter
    private lateinit var adapter: FavoriteMatchRecyclerViewAdapter
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_favorite_match, container, false)
        val mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        val divider = androidx.recyclerview.widget.DividerItemDecoration(context, mLayoutManager.orientation)
        // -----------------------------------------------------------------------------------------
        progressBar = rootView.progressBar_FavMatch
        recyclerView = rootView.recyclerView_FavMatch
        swipeRefresh = rootView.swipeRefresh_FavMatch
        // -----------------------------------------------------------------------------------------
        adapter = FavoriteMatchRecyclerViewAdapter(context, favorites){
            startActivity<DetailMatchActivity>(DetailMatchActivity.STRING_EXTRA_FAVORITE to it)
        }
        // -----------------------------------------------------------------------------------------
        presenter = FavoriteListMatchPresenter(context, favorites, progressBar,swipeRefresh, adapter)
        // -----------------------------------------------------------------------------------------
        recyclerView.adapter = adapter
        recyclerView.layoutManager = mLayoutManager
        recyclerView.addItemDecoration(divider)
        // -----------------------------------------------------------------------------------------
        swipeRefresh.onRefresh {
            presenter.showFavorite()
        }
        return rootView
    }

    override fun onResume() {
        super.onResume()
        presenter.showFavorite()
    }

}