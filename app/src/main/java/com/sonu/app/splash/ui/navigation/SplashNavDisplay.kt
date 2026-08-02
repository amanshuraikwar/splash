package com.sonu.app.splash.ui.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import androidx.navigation3.ui.NavDisplay

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSplashSharedTransitionScope = staticCompositionLocalOf<SharedTransitionScope?> { null }

val LocalSplashAnimatedVisibilityScope = staticCompositionLocalOf<AnimatedVisibilityScope?> { null }

@OptIn(ExperimentalSharedTransitionApi::class)
class SplashDestinationScope internal constructor(
    val backStack: NavBackStack<SplashRoute>,
    val sharedTransitionScope: SharedTransitionScope,
    val animatedVisibilityScope: AnimatedVisibilityScope,
) {
    fun navigate(route: SplashRoute) {
        backStack.add(route)
    }

    fun popBackStack(): Boolean = backStack.removeLastOrNull() != null
}

@Composable
fun rememberSplashBackStack(
    startDestination: SplashRoute = SplashRoute.Home,
): NavBackStack<SplashRoute> {
    @Suppress("UNCHECKED_CAST")
    return rememberNavBackStack(startDestination) as NavBackStack<SplashRoute>
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SplashNavDisplay(
    modifier: Modifier = Modifier,
    backStack: NavBackStack<SplashRoute> = rememberSplashBackStack(),
    home: @Composable SplashDestinationScope.(SplashRoute.Home) -> Unit = {},
    about: @Composable SplashDestinationScope.(SplashRoute.About) -> Unit = {},
    photoDescription: @Composable SplashDestinationScope.(SplashRoute.PhotoDescription) -> Unit = {},
    photoFullscreen: @Composable SplashDestinationScope.(SplashRoute.PhotoFullscreen) -> Unit = {},
    photoStats: @Composable SplashDestinationScope.(SplashRoute.PhotoStats) -> Unit = {},
    userDescription: @Composable SplashDestinationScope.(SplashRoute.UserDescription) -> Unit = {},
    collectionDescription: @Composable SplashDestinationScope.(SplashRoute.CollectionDescription) -> Unit = {},
    allSearch: @Composable SplashDestinationScope.(SplashRoute.AllSearch) -> Unit = {},
) {
    SharedTransitionLayout(modifier = modifier) {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            sharedTransitionScope = this,
            entryProvider = entryProvider {
                entry<SplashRoute.Home> { key ->
                    ProvideSplashDestinationScope(
                        backStack = backStack,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                    ) {
                        home(key)
                    }
                }

                entry<SplashRoute.About> { key ->
                    ProvideSplashDestinationScope(
                        backStack = backStack,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                    ) {
                        about(key)
                    }
                }

                entry<SplashRoute.PhotoDescription> { key ->
                    ProvideSplashDestinationScope(
                        backStack = backStack,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                    ) {
                        photoDescription(key)
                    }
                }

                entry<SplashRoute.PhotoFullscreen> { key ->
                    ProvideSplashDestinationScope(
                        backStack = backStack,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                    ) {
                        photoFullscreen(key)
                    }
                }

                entry<SplashRoute.PhotoStats> { key ->
                    ProvideSplashDestinationScope(
                        backStack = backStack,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                    ) {
                        photoStats(key)
                    }
                }

                entry<SplashRoute.UserDescription> { key ->
                    ProvideSplashDestinationScope(
                        backStack = backStack,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                    ) {
                        userDescription(key)
                    }
                }

                entry<SplashRoute.CollectionDescription> { key ->
                    ProvideSplashDestinationScope(
                        backStack = backStack,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                    ) {
                        collectionDescription(key)
                    }
                }

                entry<SplashRoute.AllSearch> { key ->
                    ProvideSplashDestinationScope(
                        backStack = backStack,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = LocalNavAnimatedContentScope.current,
                    ) {
                        allSearch(key)
                    }
                }
            },
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ProvideSplashDestinationScope(
    backStack: NavBackStack<SplashRoute>,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    content: @Composable SplashDestinationScope.() -> Unit,
) {
    val destinationScope = SplashDestinationScope(
        backStack = backStack,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
    )

    CompositionLocalProvider(
        LocalSplashSharedTransitionScope provides sharedTransitionScope,
        LocalSplashAnimatedVisibilityScope provides animatedVisibilityScope,
    ) {
        destinationScope.content()
    }
}
