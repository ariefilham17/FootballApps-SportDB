package com.example.footballapps.presenters

import com.google.gson.Gson
import com.example.footballapps.helpers.coroutines.CoroutineContextProvider
import com.example.footballapps.helpers.networks.ApiRepository
import com.example.footballapps.helpers.networks.TheSportDBApi
import com.example.footballapps.helpers.response.MatchResponse
import com.example.footballapps.views.interfaces.MatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter (private val view: MatchView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getMatchList(listApi: String?, leagueId: String?){
        try {
            GlobalScope.launch(context.main) {
                val data = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getEventLeagueById(listApi, leagueId)).await(),
                    MatchResponse::class.java
                )
                view.hideLoading()
                view.showData(data.events)
            }
        } catch (e: Exception){
            view.showLoading()
        }
    }
}