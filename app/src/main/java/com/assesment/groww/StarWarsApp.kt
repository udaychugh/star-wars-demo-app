package com.assesment.groww

import android.app.Application
import com.assesment.groww.data.cloud.api.CloudAPIClient
import com.assesment.groww.domain.services.CharacterService
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.FontAwesomeModule
import timber.log.Timber

class StarWarsApp: Application() {

    companion object{
        lateinit var INSTANCE: StarWarsApp
    }

    private var characterService: CharacterService? = null

    fun getCharacterService(): CharacterService {
        return characterService ?: throw IllegalStateException("Character service is not initialized")
    }


    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        Iconify.with(FontAwesomeModule())

        val apiBaseUrl = "https://swapi.dev/api/"

        CloudAPIClient.initializeWith(apiBaseUrl)

        characterService = CharacterService.getInstance(this)

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

    }

}