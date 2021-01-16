package com.example.footballapps.helpers.networks

object TheSportDBApi {

    private const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/"

    fun getEventLeagueById(listApi: String?, leagueId: String?): String {
        return "$BASE_URL$listApi?id=${leagueId.toString()}"
    }

    fun getEventDetailById(eventId: String?): String {
        return "${BASE_URL}lookupevent.php?id=${eventId.toString()}"
    }

    fun getTeamDetailById(teamId: String?): String {
        return "${BASE_URL}lookupteam.php?id=${teamId.toString()}"
    }

    fun getAllTeamsByLeagueName(leagueName: String?): String {
        return "${BASE_URL}search_all_teams.php?l=${leagueName.toString()}"
    }

    fun getAllPlayerFromid(teamId: String?): String{
        return "${BASE_URL}lookup_all_players.php?id=${teamId.toString()}"
    }

    fun getTeamsSearch(team: String?): String {
        return "${BASE_URL}searchteams.php?t=${team.toString()}"
    }

    fun getEventsSearch(event: String?): String {
        return "${BASE_URL}searchevents.php?e=${event.toString()}"
    }
}