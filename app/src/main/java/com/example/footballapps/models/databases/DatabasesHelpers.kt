package com.example.footballapps.models.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.footballapps.models.dataclass.FavoriteMatch
import com.example.footballapps.models.dataclass.FavoriteTeam
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteFootBallApps.db", null, 1) {
    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(
            FavoriteMatch.TABLE_FAVORITE_MATCH, true,
            FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.EVENT_ID to TEXT + UNIQUE,
            FavoriteMatch.DATE_MATCH to TEXT,
            FavoriteMatch.TIME_MATCH to TEXT,
            FavoriteMatch.HOME_ID to TEXT,
            FavoriteMatch.HOME_TEAM to TEXT,
            FavoriteMatch.HOME_SCORE to TEXT,
            FavoriteMatch.AWAY_ID to TEXT,
            FavoriteMatch.AWAY_TEAM to TEXT,
            FavoriteMatch.AWAY_SCORE to TEXT
        )

        db.createTable(
            FavoriteTeam.TABLE_FAVORITE_TEAM, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT,
            FavoriteTeam.TEAM_FORMEDYEAR to TEXT,
            FavoriteTeam.TEAM_STADIUM to TEXT,
            FavoriteTeam.TEAM_DESCRIPTION to TEXT
            )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable("TABLE_FAVORITE_MATCH", true)
        db.dropTable("TABLE_FAVORITE_TEAM", true)
    }

}
    // Access property for Context
    val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)
