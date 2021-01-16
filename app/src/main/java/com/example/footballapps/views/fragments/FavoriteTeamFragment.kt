package com.example.footballapps.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.example.footballapps.R
import com.example.footballapps.models.dataclass.FavoriteTeam
import com.example.footballapps.presenters.FavoriteListTeamPresenter
import com.example.footballapps.views.activities.DetailTeamActivity
import com.example.footballapps.views.adapters.FavoriteTeamRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_favorite_team.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavoriteTeamFragment : androidx.fragment.app.Fragment() {

    private var favorites: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamRecyclerViewAdapter
    private lateinit var presenter: FavoriteListTeamPresenter
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_favorite_team, container, false)
        val mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        val divider = androidx.recyclerview.widget.DividerItemDecoration(context, mLayoutManager.orientation)
        // -----------------------------------------------------------------------------------------
        progressBar = rootView.progressBar_teamFav
        recyclerView = rootView.recyclerView_teamFav
        swipeRefresh = rootView.swipeRefresh_teamFav
        // -----------------------------------------------------------------------------------------
        adapter = FavoriteTeamRecyclerViewAdapter(context, favorites) {
            startActivity<DetailTeamActivity>(DetailTeamActivity.STRING_EXTRA_FAVORITE to it)
        }
        // -----------------------------------------------------------------------------------------
        presenter = FavoriteListTeamPresenter(context, favorites, progressBar, swipeRefresh, adapter)
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