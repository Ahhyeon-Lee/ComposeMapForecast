package com.example.datalayer.remote

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class Response(
    @SerializedName("response")
    val response:JSONObject
)