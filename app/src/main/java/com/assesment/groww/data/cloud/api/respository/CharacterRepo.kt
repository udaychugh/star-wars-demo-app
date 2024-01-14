package com.assesment.groww.data.cloud.api.respository

import com.assesment.groww.common.ErrorResponse
import com.assesment.groww.data.cloud.api.interfaces.CharacterInstance
import com.assesment.groww.data.cloud.api.model.CharacterResponse
import com.assesment.groww.data.cloud.api.model.FilmResponse
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object CharacterRepo {

    private fun getServerTimeoutError(): ResponseBody {
        val errorResponseJson = JSONObject()
        errorResponseJson.put("code", ErrorResponse.SERVER_TIMEOUT_ERROR)
        return ResponseBody.create(MediaType.get("application/json"), errorResponseJson.toString())
    }

    private fun getNetworkError(): ResponseBody {
        val errorResponseJson = JSONObject()
        errorResponseJson.put("code", ErrorResponse.NETWORK_ERROR)
        return ResponseBody.create(MediaType.get("application/json"), errorResponseJson.toString())
    }

    suspend fun getStarWarCharacters(): Response<CharacterResponse> {
        return try {
            CharacterInstance.getInterface().getStarWarCharacters()
        } catch (e: SocketTimeoutException) {
            Response.error(599, getServerTimeoutError())
        } catch (e: UnknownHostException) {
            Response.error(408, getNetworkError())
        } catch (e: ConnectException) {
            Response.error(408, getNetworkError())
        }
    }

    suspend fun getCharacterFilms(filmNumber: String): Response<FilmResponse> {
        return try {
            CharacterInstance.getInterface().getCharacterFilms(filmNumber)
        } catch (e: SocketTimeoutException) {
            Response.error(599, getServerTimeoutError())
        } catch (e: UnknownHostException) {
            Response.error(408, getNetworkError())
        } catch (e: ConnectException) {
            Response.error(408, getNetworkError())
        }
    }

}