package com.assesment.groww.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.assesment.groww.common.Characters
import org.json.JSONObject
import timber.log.Timber

class AppDataManager private constructor(app: Application) {


    companion object {

        private const val TAG = "AppDataManager"

        private const val SHARED_PREFERENCES_FILE_SUFFIX = ".application.prefs"

        private const val STAR_WARS_CHAR_INFO = "starWarCharacters"

        private var instance: AppDataManager? = null

        fun getInstance(app: Application): AppDataManager {
            if (instance == null){
                instance = AppDataManager(app)
            }

            return instance!!
        }
    }

    private var prefs: SharedPreferences
    private val app: Application

    init {
        this.app = app
        prefs = app.getSharedPreferences(app.packageName + SHARED_PREFERENCES_FILE_SUFFIX, Context.MODE_PRIVATE)
    }

    fun saveCharData(characters: Characters) {
        prefs.edit().putString(STAR_WARS_CHAR_INFO, characters.toJson().toString()).commit()
    }

    fun loadCharData(): Characters? {
        return try {
            val savedCharJson = JSONObject(prefs.getString(STAR_WARS_CHAR_INFO, "{}")!!)
            Characters.fromJson(savedCharJson)
        } catch (e: Exception){
            Timber.e(TAG, "Error reading user data from prefs = $e")
            null
        }
    }

}