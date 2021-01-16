package com.example.footballapps.presenters

import com.google.gson.Gson
import com.example.footballapps.helpers.coroutines.CoroutineContextProvider
import com.example.footballapps.helpers.networks.ApiRepository
import com.example.footballapps.helpers.networks.TheSportDBApi
import com.example.footballapps.helpers.response.TeamResponse
import com.example.footballapps.views.interfaces.TeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailTeamPresenter (private val view: TeamView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetailById(teamID: String?) {
        try {
            GlobalScope.launch(context.main) {
                val dataTeam = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getTeamDetailById(teamID)).await(),
                    TeamResponse::class.java
                )
                view.hideLoading()
                view.showData(dataTeam.teams)
            }
        } catch (e: Exception) {
            view.showLoading()
        }
    }
}