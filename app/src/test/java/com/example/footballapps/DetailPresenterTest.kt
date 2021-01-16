package com.example.footballapps

import com.google.gson.Gson
import com.example.footballapps.helpers.coroutines.TestContextProvider
import com.example.footballapps.helpers.networks.ApiRepository
import com.example.footballapps.helpers.networks.TheSportDBApi
import com.example.footballapps.helpers.response.MatchResponse
import com.example.footballapps.helpers.response.TeamResponse
import com.example.footballapps.models.dataclass.Match
import com.example.footballapps.models.dataclass.Team
import com.example.footballapps.presenters.DetailMatchPresenter
import com.example.footballapps.views.interfaces.DetailMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPresenterTest {
    @Mock
    private
    lateinit var view: DetailMatchView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit
    var apiRepository: ApiRepository

    private lateinit var presenter: DetailMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun testMatchDetail() {
        // Testing Presenter untuk Match Detail pada DetailActivity
        val match: MutableList<Match> = mutableListOf()
        val homeTeams: MutableList<Team> = mutableListOf()
        val awayTeams: MutableList<Team> = mutableListOf()
        val responseMatch = MatchResponse(match)
        val responseHomeTeam = TeamResponse(homeTeams)
        val responseAwayTeam = TeamResponse(awayTeams)
        val eventID = "576595"
        val homeTeamID = "133623"
        val awayTeamID = "134777"

        GlobalScope.launch {
            Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getEventDetailById(eventID)).await(),
                MatchResponse::class.java
            )).thenReturn(responseMatch)

            Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetailById(homeTeamID)).await(),
                TeamResponse::class.java
            )).thenReturn(responseHomeTeam)

            Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeamDetailById(awayTeamID)).await(),
                TeamResponse::class.java
            )).thenReturn(responseAwayTeam)

            presenter.getMatchDetail(eventID, homeTeamID, awayTeamID)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showData(match, homeTeams, awayTeams)
            Mockito.verify(view).hideLoading()
        }
    }



}