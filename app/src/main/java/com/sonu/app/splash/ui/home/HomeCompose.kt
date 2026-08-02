package com.sonu.app.splash.ui.home

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sonu.app.splash.data.DataManager
import com.sonu.app.splash.ui.SplashApp
import com.sonu.app.splash.ui.photos.PhotosFeedRoute

object HomeCompose {
    @JvmStatic
    fun setContent(activity: ComponentActivity, dataManager: DataManager) {
        activity.setContent {
            SplashApp(
                home = {
                    PhotosFeedRoute(
                        dataManager = dataManager,
                        destinationScope = this,
                    )
                },
            )
        }
    }
}
