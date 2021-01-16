package com.example.footballapps.presenters

import com.google.gson.Gson
import com.example.footballapps.helpers.coroutines.CoroutineContextProvider
import com.example.footballapps.helpers.networks.ApiRepository
import com.example.footballapps.helpers.networks.TheSportDBApi
import com.example.footballapps.helpers.response.TeamResponse
import com.example.footballapps.views.interfaces.SearchTeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchTeamPresenter (private val view: SearchTeamView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getSearchTeamsList(e: String?){
        GlobalScope.launch(context.main) {
            try {
                val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeamsSearch(e)).await(), TeamResponse::class.java)
                view.showSearchTeamList(data.teams)
            } catch (e: Exception) {
                view.showSearchTeamListNull()
            }

        }
    }
}