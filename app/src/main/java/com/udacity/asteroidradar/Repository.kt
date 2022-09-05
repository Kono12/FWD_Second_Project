package com.udacity.asteroidradar


import android.content.Context
import com.udacity.asteroidradar.DB.DataBaseClass
import com.udacity.asteroidradar.api.RetrofitBuilder
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject

class Repository {
    lateinit var astroList:ArrayList<Asteroid>
    suspend fun getAstroids(): ArrayList<Asteroid> {
        val jsonObject =
            JSONObject(RetrofitBuilder.retrofitService.getAsteroids("", "", Constants.ApiKey))
        astroList=parseAsteroidsJsonResult(jsonObject)
        return astroList
    }

    suspend fun getPic(): PictureOfDay {
        return RetrofitBuilder.retrofitService.getPictureOfDay(Constants.ApiKey)
    }





}