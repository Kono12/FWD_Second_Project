package com.udacity.asteroidradar


import com.udacity.asteroidradar.api.RetrofitBuilder
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class Repository {

    suspend fun getAstroids(): List<Asteroid> {
        val jsonObject =
            JSONObject(RetrofitBuilder.retrofitService.getAsteroids("", "", Constants.ApiKey))
        return parseAsteroidsJsonResult(jsonObject)
    }

    suspend fun getPic(): PictureOfDay {
        return RetrofitBuilder.retrofitService.getPictureOfDay(Constants.ApiKey)
    }
}