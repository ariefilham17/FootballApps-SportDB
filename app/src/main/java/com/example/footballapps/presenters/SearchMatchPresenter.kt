package com.example.footballapps.presenters

import com.google.gson.Gson
import com.example.footballapps.helpers.coroutines.CoroutineContextProvider
import com.example.footballapps.helpers.networks.ApiRepository
import com.example.footballapps.helpers.networks.TheSportDBApi
import com.example.footballapps.helpers.response.SearchMatchResponse
import com.example.footballapps.views.interfaces.SearchMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchMatchPresenter (private val view: SearchMatchView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getSearchMatchesList(e: String?){
        GlobalScope.launch(context.main) {
            try {
                val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getEventsSearch(e)).await(), SearchMatchResponse::class.java)
                view.showSearchMatchList(data.event)
            } catch (e: Exception) {
                view.showSearchMatchListNull()
            }

        }
    }
}