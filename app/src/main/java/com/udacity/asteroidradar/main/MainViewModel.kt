package com.udacity.asteroidradar.main


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.RV.RV_Adapter
import com.udacity.asteroidradar.Repository
import kotlinx.coroutines.launch


class MainViewModel(private val repository: Repository) : ViewModel() {

    var arraylist: ArrayList<Asteroid> = ArrayList<Asteroid>()
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
               // getAstroids()
            }
        }
    }

    fun getPicture() {
        viewModelScope.launch {
            try {
                _pic.value = repository.getPic()
            } catch (e: Exception) {
                e.printStackTrace()
            //    getPicture()
            }
        }
    }



     fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}
