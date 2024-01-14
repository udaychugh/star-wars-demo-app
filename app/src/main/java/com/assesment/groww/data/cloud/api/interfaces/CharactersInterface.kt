package com.assesment.groww.data.cloud.api.interfaces

import com.assesment.groww.data.cloud.api.CloudAPIClient
import com.assesment.groww.data.cloud.api.model.CharacterResponse
import com.assesment.groww.data.cloud.api.model.FilmResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersInterface {

    @GET("people")
    suspend fun getStarWarCharacters(

    ): Response<CharacterResponse>

    @GET("films/{film_number}")
    suspend fun getCharacterFilms(
        @Path("film_number") filmNumber: String
    ): Response<FilmResponse>

}

object CharacterInstance {
    private var interfaceObj: CharactersInterface? = null

    fun getInterface(): CharactersInterface {
        if (interfaceObj == null){
            interfaceObj = CloudAPIClient.getInstance().create(CharactersInterface::class.java)
        }
        return interfaceObj!!
    }
}