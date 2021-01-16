package com.example.footballapps.presenters

import com.google.gson.Gson
import com.example.footballapps.helpers.coroutines.CoroutineContextProvider
import com.example.footballapps.helpers.networks.ApiRepository
import com.example.footballapps.helpers.networks.TheSportDBApi
import com.example.footballapps.helpers.response.PlayerResponse
import com.example.footballapps.views.interfaces.PlayerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter (private val view: PlayerView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerList(teamID: String?){
        try {
            GlobalScope.launch(context.main) {
                val data = gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getAllPlayerFromid(teamID)).await(), PlayerResponse::class.java
                )
                view.hideLoading()
                view.showData(data.player)
            }
        } catch (e: Exception) {
            view.showLoading()
        }
    }

}