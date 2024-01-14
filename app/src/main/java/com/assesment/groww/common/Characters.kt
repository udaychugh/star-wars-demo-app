package com.assesment.groww.common

import com.assesment.groww.data.cloud.api.model.ResultList
import org.json.JSONArray
import org.json.JSONObject

class Characters {

    companion object {

        fun fromJson(characterJson: JSONObject): Characters {
            val char = Characters()
            val charArr = characterJson.optJSONArray("characters") ?: JSONArray()
            for (i in 0 until charArr.length()){
                charArr.optJSONObject(i)?.let {
                    char.characters.add(
                        ResultList(
                            name = it.getString("name"),
                            height = it.getString("height"),
                            mass = it.getString("mass"),
                            hairColor = it.getString("hairColor"),
                            skinColor = it.getString("skinColor"),
                            eyeColor = it.getString("eyeColor"),
                            birthYear = it.getString("birthYear"),
                            gender = it.getString("gender"),
                            homeWorld = it.getString("homeWorld"),
                            films = it.getString("films").substring(1, it.getString("films").length - 1).split(", ").map { str -> str.trim() }.toList(),
                            species = it.getString("species").substring(1, it.getString("species").length - 1).split(", ").map { str -> str.trim() }.toList(),
                            vehicles = it.getString("vehicles").substring(1, it.getString("vehicles").length - 1).split(", ").map { str -> str.trim() }.toList(),
                            starships = it.getString("starships").substring(1, it.getString("starships").length - 1).split(", ").map { str -> str.trim() }.toList(),
                            created = it.getString("created"),
                            edited = it.getString("edited"),
                            url = it.getString("url")
                        )
                    )
                }
            }

            return char
        }

    }

    var characters: ArrayList<ResultList> = ArrayList()

    fun toJson(): JSONObject {
        val charArrJson = JSONObject()
        val charArray = JSONArray()
        for (char in characters) {
            val charJson = JSONObject()
            charJson.put("name", char.name)
            charJson.put("height", char.height)
            charJson.put("mass", char.mass)
            charJson.put("hairColor", char.hairColor)
            charJson.put("skinColor", char.skinColor)
            charJson.put("eyeColor", char.eyeColor)
            charJson.put("birthYear", char.birthYear)
            charJson.put("gender", char.gender)
            charJson.put("homeWorld", char.homeWorld)
            charJson.put("films", char.films)
            charJson.put("species", char.species)
            charJson.put("vehicles", char.vehicles)
            charJson.put("starships", char.starships)
            charJson.put("created", char.created)
            charJson.put("edited", char.edited)
            charJson.put("url", char.url)
            charArray.put(charJson)
        }
        charArrJson.put("characters", charArray)
        return charArrJson
    }
}