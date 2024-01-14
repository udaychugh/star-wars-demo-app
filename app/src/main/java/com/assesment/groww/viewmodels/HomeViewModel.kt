package com.assesment.groww.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assesment.groww.StarWarsApp
import com.assesment.groww.adapters.CharAdapter
import com.assesment.groww.adapters.FilmAdapter
import com.assesment.groww.common.ErrorResponse
import kotlinx.coroutines.launch
import timber.log.Timber

enum class FragmentState {
    SPLASH_STATE,
    CHARACTERS_LIST_STATE,
    CHARACTER_INFO_STATE
}

class HomeViewModel: ViewModel() {

    private var characterService = StarWarsApp.INSTANCE.getCharacterService()

    private val _fragmentState = MutableLiveData(FragmentState.SPLASH_STATE)
    val fragmentState : LiveData<FragmentState>
        get() = _fragmentState

    private var currentFragmentState = FragmentState.SPLASH_STATE

    private val _isFetched = MutableLiveData<Pair<Boolean, ErrorResponse?>>()
    val isFetched: LiveData<Pair<Boolean, ErrorResponse?>>
        get() = _isFetched

    private val _filmResponse = MutableLiveData<Pair<FilmAdapter.FilmItems?, ErrorResponse?>>()
    val filmResponse: LiveData<Pair<FilmAdapter.FilmItems?, ErrorResponse?>>
        get() = _filmResponse

    private val filmList = ArrayList<FilmAdapter.FilmItems>()

    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String>
        get() = _toolbarTitle

    private val charArrList = ArrayList<CharAdapter.CharacterItems>()

    private lateinit var characterFullData: CharAdapter.CharacterItems

    fun setFragment(fg: FragmentState) {
        currentFragmentState = fg
        _fragmentState.postValue(fg)
    }

    fun getCurrentFragment(): FragmentState {
        return currentFragmentState
    }

    fun getCharacters() {
        viewModelScope.launch {
            try {
                val result = characterService.getStarWarCharacters()
                if (result.first) {
                    _isFetched.postValue(Pair(true, null))
                } else {
                    _isFetched.postValue(Pair(false, result.second))
                }
            } catch (e: Exception) {
                Timber.e("Error in getting star wars characters = $e")
                _isFetched.postValue(Pair(false, ErrorResponse.PARSING_ERROR))
            }
        }
    }

    fun getCharacterFilms(filmNumber: String) {
        viewModelScope.launch {
            try {
                val (films, err) = characterService.getCharacterFilms(filmNumber)

                if (err != null) {
                    _filmResponse.postValue(Pair(null, err))
                    return@launch
                }

                filmList.clear()
                val filmData = FilmAdapter.FilmItems(films?.title ?: "", films?.director ?: "", films?.producer ?: "", films?.openingCrawl?: "")
                _filmResponse.postValue(Pair(filmData, null))

            } catch (e: Exception) {
                Timber.e("Error in getting characters films = $e")
            }
        }
    }

    fun getCharactersFromLocalDb(): ArrayList<CharAdapter.CharacterItems> {
        val characters = characterService.getCharacterFromDB() ?: return ArrayList()

        for (char in characters.characters) {
            charArrList.add(
                CharAdapter.CharacterItems(
                    name = char.name,
                    height = char.height,
                    mass = char.mass,
                    hairColor = char.hairColor,
                    skinColor = char.skinColor,
                    eyeColor = char.eyeColor,
                    birthYear = char.birthYear,
                    gender = char.gender,
                    homeWorld = char.homeWorld,
                    films = char.films,
                    species = char.species,
                    vehicles = char.vehicles,
                    starships = char.starships,
                    created = char.created,
                    edited = char.edited,
                    url = char.url
                )
            )

        }

        return charArrList

    }

    fun setCharacterFullData(charData: CharAdapter.CharacterItems) {
        characterFullData = charData
    }

    fun getCharacterFullData(): CharAdapter.CharacterItems {
        return characterFullData
    }

    fun setToolbarTitle(name: String) {
        _toolbarTitle.postValue(name)
    }

}