package com.example.footballapps.presenters

import android.content.Context
import android.widget.ProgressBar
import com.example.footballapps.helpers.utils.invisible
import com.example.footballapps.models.databases.database
import com.example.footballapps.models.dataclass.FavoriteMatch
import com.example.footballapps.views.adapters.FavoriteMatchRecyclerViewAdapter
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteListMatchPresenter (private val context: Context?,
                                  private var favorites: MutableList<FavoriteMatch>,
                                  private val progressBar: ProgressBar,
                                  private val swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout,
                                  private var adapter: FavoriteMatchRecyclerViewAdapter
){

    fun showFavorite(){
        progressBar.invisible()
        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

}