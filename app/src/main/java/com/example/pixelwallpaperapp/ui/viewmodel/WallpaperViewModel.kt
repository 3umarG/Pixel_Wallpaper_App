package com.example.pixelwallpaperapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pixelwallpaperapp.pojo.WallpaperModel
import com.example.pixelwallpaperapp.repo.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class WallpaperViewModel : ViewModel() {
    private val _mutableLiveData = MutableLiveData<WallpaperModel>()
    var wallpaperData = _mutableLiveData as LiveData<WallpaperModel>


    suspend fun getWallpaper(query: String = "people") {
        viewModelScope.launch {
            val response  = async {
                Repository.searchWallpaper(query)
            }

            if (response.await().isSuccessful) {
                val wallpapers  = response.await().body() as WallpaperModel
                _mutableLiveData.value = wallpapers
            } else {
                println("ERROR RESPONSE : failed !!")
            }

        }
    }
}