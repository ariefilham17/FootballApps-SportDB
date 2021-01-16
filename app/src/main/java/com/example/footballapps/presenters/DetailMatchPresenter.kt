package com.example.footballapps.presenters

import com.google.gson.Gson
import com.example.footballapps.helpers.coroutines.CoroutineContextProvider
import com.example.footballapps.helpers.networks.ApiRepository
import com.example.footballapps.helpers.networks.TheSportDBApi
import com.example.footballapps.helpers.response.MatchResponse
import com.example.footballapps.helpers.response.TeamResponse
import com.example.footballapps.views.interfaces.DetailMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMatchPresenter (private val view: DetailMatchView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getMatchDetail(eventID: String?, homeTeamID: String?, awayTeamID: String?) {

        try {
            GlobalScope.launch(context.main) {
                val dataMatch = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getEventDetailById(eventID)).await(),
                    MatchResponse::class.java
                )

                val dataHomeTeam = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeamDetailById(homeTeamID)).await(),
                    TeamResponse::class.java
                )

                val dataAwayTeam = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeamDetailById(awayTeamID)).await(),
                    TeamResponse::class.java
                )

                view.hideLoading()
                view.showData(dataMatch.events, dataHomeTeam.teams, dataAwayTeam.teams)
            }
        } catch (e: Exception) {
            view.showLoading()
        }
    }
}