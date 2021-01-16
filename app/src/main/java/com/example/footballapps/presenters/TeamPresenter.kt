package com.example.footballapps.presenters

import com.google.gson.Gson
import com.example.footballapps.helpers.coroutines.CoroutineContextProvider
import com.example.footballapps.helpers.networks.ApiRepository
import com.example.footballapps.helpers.networks.TheSportDBApi
import com.example.footballapps.helpers.response.TeamResponse
import com.example.footballapps.views.interfaces.TeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPresenter (private val view: TeamView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(leagueName: String?){
        try {
            GlobalScope.launch(context.main) {
                val data = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getAllTeamsByLeagueName(leagueName)).await(), TeamResponse::class.java
                )
                view.hideLoading()
                view.showData(data.teams)
            }
        } catch (e: Exception) {
            view.showLoading()
        }
    }

}