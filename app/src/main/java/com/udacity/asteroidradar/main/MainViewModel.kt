package com.udacity.asteroidradar.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    val response:MutableLiveData<List<Asteroid>> = MutableLiveData()
    val pic : MutableLiveData<PictureOfDay> = MutableLiveData()

    fun getAstroids(){
        viewModelScope.launch {
             response.value=repository.getAstroids()
        }
    }

    fun getPic(){
        viewModelScope.launch {
            pic.value=repository.getPic()
        }
    }

}
