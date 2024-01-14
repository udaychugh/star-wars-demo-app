package com.assesment.groww.domain.services

import android.app.Application
import com.assesment.groww.common.Characters
import com.assesment.groww.common.ErrorResponse
import com.assesment.groww.data.AppDataManager
import com.assesment.groww.data.cloud.api.model.CharacterResponse
import com.assesment.groww.data.cloud.api.model.FilmResponse
import com.assesment.groww.data.cloud.api.model.ResultList
import com.assesment.groww.data.cloud.api.respository.CharacterRepo
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

class CharacterService private constructor(app: Application) {

    companion object{
        private var instance: CharacterService? = null

        fun getInstance(app: Application): CharacterService {
            if (instance == null) {
                instance = CharacterService(app)
            }
            return instance!!
        }
    }

    private val appInstance: Application
    private val dataManager: AppDataManager

    init {
        appInstance = app
        dataManager = AppDataManager.getInstance(app)
    }

    private fun getErrorResponse(errorBody: ResponseBody?): ErrorResponse {
        return try {
            val jsonResponse = JSONObject(errorBody!!.string())
            val code = jsonResponse.optString("code")
            Timber.e("Error Code = $code")
            ErrorResponse.valueOf(code)
        } catch (e: IllegalArgumentException) {
            Timber.e("IllegalArgumentException = $e")
            ErrorResponse.SERVER_ERROR
        } catch (e: NullPointerException) {
            Timber.e("NullPointerException = $e")
            ErrorResponse.SERVER_ERROR
        } catch (e: JSONException){
            Timber.e("JSON ERROR = $e")
            ErrorResponse.SERVER_ERROR
        }
    }

    suspend fun getStarWarCharacters(): Pair<Boolean, ErrorResponse?> {
        val response = CharacterRepo.getStarWarCharacters()
        return if (response.isSuccessful) {
            val character = dataManager.loadCharData() ?: Characters()
            response.body().let {
                character.characters = it?.results as? ArrayList<ResultList> ?: ArrayList()
                dataManager.saveCharData(character)
            }
            Pair(true, null)
        } else {
            Pair(false, getErrorResponse(response.errorBody()))
        }
    }

    suspend fun getCharacterFilms(filmNumber: String): Pair<FilmResponse?, ErrorResponse?> {
        val response = CharacterRepo.getCharacterFilms(filmNumber)
        return if (response.isSuccessful) {
            Pair(response.body(), null)
        } else {
            Pair(null, getErrorResponse(response.errorBody()))
        }
    }

    fun getCharacterFromDB(): Characters? {
        return dataManager.loadCharData()
    }

}