package com.assesment.groww.data.cloud.api.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse (
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<ResultList>,
)


data class ResultList(
    val name: String,
    val height: String,
    val mass: String,
    @SerializedName("hair_color") val hairColor: String,
    @SerializedName("skin_color") val skinColor: String,
    @SerializedName("eye_color") val eyeColor: String,
    @SerializedName("birth_year") val birthYear: String,
    val gender: String,
    @SerializedName("homeworld") val homeWorld: String,
    val films: List<String>,
    val species: List<String>,
    val vehicles: List<String>,
    val starships: List<String>,
    val created: String,
    val edited: String,
    val url: String

)

data class FilmResponse(
    val title: String,
    val director: String,
    val producer: String,
    @SerializedName("opening_crawl") val openingCrawl: String
)