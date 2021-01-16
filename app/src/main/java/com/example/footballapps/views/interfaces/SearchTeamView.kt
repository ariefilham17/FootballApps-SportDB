package com.example.footballapps.views.interfaces

import com.example.footballapps.models.dataclass.Team

interface SearchTeamView {
    fun showSearchTeamList(data: List<Team>)
    fun showSearchTeamListNull()
}