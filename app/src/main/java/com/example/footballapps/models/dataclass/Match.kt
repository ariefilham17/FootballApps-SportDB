package com.example.footballapps.models.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Match (
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("dateEvent")
    var dateMatch: Date? = null,

    @SerializedName("strTime")
    var timeMatch: String? = null,

    @SerializedName("idHomeTeam")
    var homeTeamId: String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: String? = null,

    @SerializedName("strHomeFormation")
    var homeFormation: String? = null,

    @SerializedName("strHomeGoalDetails")
    var homeGoals: String? = null,

    @SerializedName("intHomeShots")
    var homeShots: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    var homeGoalKeeper: String? = null,

    @SerializedName("strHomeLineupDefense")
    var homeDefense: String? = null,

    @SerializedName("strHomeLineupMidfield")
    var homeMidfield: String? = null,

    @SerializedName("strHomeLineupForward")
    var homeForward: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    var homeSubstitutes: String? = null,

    @SerializedName("idAwayTeam")
    var awayTeamId: String? = null,

    @SerializedName("strAwayTeam")
    var awayTeam: String? = null,

    @SerializedName("intAwayScore")
    var awayScore: String? = null,

    @SerializedName("strAwayFormation")
    var awayFormation: String? = null,

    @SerializedName("strAwayGoalDetails")
    var awayGoals: String? = null,

    @SerializedName("intAwayShots")
    var awayShots: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    var awayGoalKeeper: String? = null,

    @SerializedName("strAwayLineupDefense")
    var awayDefense: String? = null,

    @SerializedName("strAwayLineupMidfield")
    var awayMidfield: String? = null,

    @SerializedName("strAwayLineupForward")
    var awayForward: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    var awaySubstitutes: String? = null

) : Parcelable