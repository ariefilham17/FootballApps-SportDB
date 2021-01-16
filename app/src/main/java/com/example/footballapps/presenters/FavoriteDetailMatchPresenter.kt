package com.example.footballapps.presenters

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import com.example.footballapps.helpers.utils.formatDateToMatch
import com.example.footballapps.helpers.utils.formatTimeToMatch
import com.example.footballapps.models.databases.database
import com.example.footballapps.models.dataclass.FavoriteMatch
import com.example.footballapps.models.dataclass.Match
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class FavoriteDetailMatchPresenter(private val context: Context,
                                   private val matchData: MutableList<Match>,
                                   private val swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout
) {

    var isFavorite = false

    fun addToFavorite(){
        try {
            context.database.use {
                insert(
                    FavoriteMatch.TABLE_FAVORITE_MATCH,
                    FavoriteMatch.EVENT_ID to matchData.first().eventId,
                    FavoriteMatch.DATE_MATCH to matchData.first().dateMatch?.formatDateToMatch(),
                    FavoriteMatch.TIME_MATCH to matchData.first().timeMatch?.formatTimeToMatch(),
                    FavoriteMatch.HOME_ID to matchData.first().homeTeamId,
                    FavoriteMatch.HOME_TEAM to matchData.first().homeTeam,
                    FavoriteMatch.HOME_SCORE to matchData.first().homeScore,
                    FavoriteMatch.AWAY_ID to matchData.first().awayTeamId,
                    FavoriteMatch.AWAY_TEAM to matchData.first().awayTeam,
                    FavoriteMatch.AWAY_SCORE to matchData.first().awayScore
                )
            }
            swipeRefresh.snackbar("Added to favorite").show()
        } catch (e: SQLiteException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    fun removeFromFavorite(id: String?){
        try {
            context.database.use {
                delete(FavoriteMatch.TABLE_FAVORITE_MATCH, "(" + FavoriteMatch.EVENT_ID + "=" + id + ")")
            }
            swipeRefresh.snackbar("Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    fun favoriteState(id: String?){
        context.database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                .whereArgs(FavoriteMatch.EVENT_ID+"="+id)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

}

