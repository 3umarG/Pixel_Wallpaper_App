package com.example.pixelwallpaperapp.repo

import com.example.pixelwallpaperapp.data.RetrofitInstance

class Repository {
    companion object {
        suspend fun searchWallpaper(query: String) =
            RetrofitInstance.api.searchWallpaper(query)
    }
}