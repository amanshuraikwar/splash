package com.sonu.app.splash.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import com.sonu.app.splash.ui.navigation.SplashDestinationScope
import com.sonu.app.splash.ui.navigation.SplashNavDisplay
import com.sonu.app.splash.ui.navigation.SplashRoute
import com.sonu.app.splash.ui.navigation.rememberSplashBackStack
import com.sonu.app.polygon.theme.PolygonTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SplashApp(
    modifier: Modifier = Modifier,
    backStack: NavBackStack<SplashRoute> = rememberSplashBackStack(),
    darkTheme: Boolean = false,
    home: @Composable SplashDestinationScope.(SplashRoute.Home) -> Unit = {},
    about: @Composable SplashDestinationScope.(SplashRoute.About) -> Unit = {},
    photoDescription: @Composable SplashDestinationScope.(SplashRoute.PhotoDescription) -> Unit = {},
    photoFullscreen: @Composable SplashDestinationScope.(SplashRoute.PhotoFullscreen) -> Unit = {},
    photoStats: @Composable SplashDestinationScope.(SplashRoute.PhotoStats) -> Unit = {},
    userDescription: @Composable SplashDestinationScope.(SplashRoute.UserDescription) -> Unit = {},
    collectionDescription: @Composable SplashDestinationScope.(SplashRoute.CollectionDescription) -> Unit = {},
    allSearch: @Composable SplashDestinationScope.(SplashRoute.AllSearch) -> Unit = {},
) {
    PolygonTheme(
        darkTheme = darkTheme,
        applySystemBars = true,
    ) {
        SplashNavDisplay(
            modifier = modifier,
            backStack = backStack,
            home = home,
            about = about,
            photoDescription = photoDescription,
            photoFullscreen = photoFullscreen,
            photoStats = photoStats,
            userDescription = userDescription,
            collectionDescription = collectionDescription,
            allSearch = allSearch,
        )
    }
}
