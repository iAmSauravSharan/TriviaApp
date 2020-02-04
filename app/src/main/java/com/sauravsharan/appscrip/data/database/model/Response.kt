package com.sauravsharan.appscrip.data.database.model


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("response")
    val response: List<Questions>
)