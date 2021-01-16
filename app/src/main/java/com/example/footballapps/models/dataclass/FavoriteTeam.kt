package com.example.footballapps.models.dataclass

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteTeam (
    val id: Long?,
    var teamId: String?,
    var teamName: String?,
    var teamBadge: String?,
    var teamFormedYear: String?,
    var teamStadium: String?,
    var teamDescription: String?) : Parcelable {

    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val TEAM_FORMEDYEAR: String = "TEAM_FORMEDYEAR"
        const val TEAM_STADIUM: String = "TEAM_STADIUM"
        const val TEAM_DESCRIPTION: String = "TEAM_DESCRIPTION"
    }
}