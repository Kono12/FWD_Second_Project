package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.RV.RV_Adapter
import com.udacity.asteroidradar.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repository: Repository) : ViewModel() {


    val response: MutableLiveData<List<Asteroid>> = MutableLiveData()

    private val _pic = MutableLiveData<PictureOfDay>()
    val pic: LiveData<PictureOfDay>
        get() = _pic

    lateinit var adapter: RV_Adapter


    init {
        getAstroids()
        getPicture()
    }

    fun getAstroids() {
        viewModelScope.launch {
            try {
                response.value = repository.getAstroids()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getPicture() {
        viewModelScope.launch {
            try {
                _pic.value = repository.getPic()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}
