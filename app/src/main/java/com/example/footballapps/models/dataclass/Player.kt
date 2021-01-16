package com.example.footballapps.models.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player (
    @SerializedName("idPlayer")
    var playerId: String? = null,

    @SerializedName("strPlayer")
    var playerName: String? = null,

    @SerializedName("strHeight")
    var playerHeight: String? = null,

    @SerializedName("strWeight")
    var playerWeight: String? = null,

    @SerializedName("strFanart1")
    var playerPoster: String? = null,

    @SerializedName("strPosition")
    var playerPosition: String? = null,

    @SerializedName("strCutout")
    var playerPhoto: String? = null,

    @SerializedName("strDescriptionEN")
    var playerDes: String? = null

) : Parcelable