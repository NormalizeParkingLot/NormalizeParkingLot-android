package com.kick.npl

import android.app.Application
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NPLApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(NAVER_MAP_CLIENT_ID)
    }
}