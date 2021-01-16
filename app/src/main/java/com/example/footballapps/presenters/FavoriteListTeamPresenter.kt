package com.example.footballapps.presenters

import android.content.Context
import android.widget.ProgressBar
import com.example.footballapps.helpers.utils.invisible
import com.example.footballapps.models.databases.database
import com.example.footballapps.models.dataclass.FavoriteTeam
import com.example.footballapps.views.adapters.FavoriteTeamRecyclerViewAdapter
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteListTeamPresenter(private val context: Context?,
                                private var favorites: MutableList<FavoriteTeam>,
                                private val progressBar: ProgressBar,
                                private val swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout,
                                private var adapter: FavoriteTeamRecyclerViewAdapter){

    fun showFavorite() {
        progressBar.invisible()
        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}