package com.sonu.app.splash.ui.rss

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sonu.app.splash.R
import com.sonu.app.splash.ui.navigation.SplashDestinationScope
import com.sonu.app.splash.ui.navigation.SplashRoute
import com.sonu.app.splash.ui.navigation.SplashSharedElementKey
import com.sonu.app.splash.ui.navigation.LocalSplashAnimatedVisibilityScope
import com.sonu.app.splash.ui.navigation.LocalSplashSharedTransitionScope
import com.sonu.app.polygon.theme.Polygon
import com.sonu.app.polygon.theme.PolygonPalette
import java.text.DateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.flow.first

private val RssSharedBoundsTransform = BoundsTransform { _, _ ->
    tween(
        durationMillis = 375,
        easing = FastOutSlowInEasing,
    )
}

private val RssHeaderBoundsTransform = BoundsTransform { _, _ ->
    tween(
        durationMillis = 300,
        easing = FastOutSlowInEasing,
    )
}

@Composable
internal fun RssDetailRoute(
    route: SplashRoute.RssDetail,
    destinationScope: SplashDestinationScope,
) {
    RssDetailScreen(
        route = route,
        onBackClick = { destinationScope.popBackStack() },
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun RssDetailScreen(
    route: SplashRoute.RssDetail,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader? = null,
    imageCrossfade: Boolean = true,
) {
    val density = LocalDensity.current
    val statusBarPadding = with(density) { WindowInsets.statusBars.getTop(this).toDp() }
    val navigationBarPadding = with(density) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }
    val sharedTransitionScope = LocalSplashSharedTransitionScope.current
    val animatedVisibilityScope = LocalSplashAnimatedVisibilityScope.current
    var imageFailed by remember(route.itemId, route.imageUrl) { mutableStateOf(false) }
    var imageWidth by remember(route.itemId, route.imageUrl) {
        mutableStateOf(route.imageWidth)
    }
    var imageHeight by remember(route.itemId, route.imageUrl) {
        mutableStateOf(route.imageHeight)
    }
    val showImage = !route.imageUrl.isNullOrBlank() && !imageFailed
    val hasNavigationAnimation = animatedVisibilityScope != null
    val imageContentScaleFraction = animatedVisibilityScope?.transition?.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 375,
                easing = FastOutSlowInEasing,
            )
        },
        label = "rss-image-content-scale",
    ) { state ->
        if (state == EnterExitState.Visible) 1f else 0f
    }?.value ?: 1f
    val imageAspectRatio = if (imageWidth > 0 && imageHeight > 0) {
        imageWidth.toFloat() / imageHeight.toFloat()
    } else {
        1f
    }
    val detailContentVisibility = remember(route.itemId) {
        MutableTransitionState(false)
    }

    LaunchedEffect(route.itemId, hasNavigationAnimation, sharedTransitionScope) {
        if (hasNavigationAnimation) {
            withFrameNanos { }
            if (sharedTransitionScope?.isTransitionActive == true) {
                snapshotFlow { sharedTransitionScope.isTransitionActive }
                    .first { isActive -> !isActive }
            }
        }
        detailContentVisibility.targetState = true
    }

    val routeExitModifier = if (animatedVisibilityScope != null) {
        with(animatedVisibilityScope) {
            Modifier.animateEnterExit(
                enter = EnterTransition.None,
                exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = 195,
                        easing = FastOutLinearInEasing,
                    ),
                ),
            )
        }
    } else {
        Modifier
    }
    val sharedImageModifier =
        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
            with(sharedTransitionScope) {
                Modifier.sharedElement(
                    sharedContentState = rememberSharedContentState(
                        key = SplashSharedElementKey.rssImage(route.itemId),
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = RssSharedBoundsTransform,
                    zIndexInOverlay = 1f,
                )
            }
        } else {
            Modifier
        }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent),
    ) {
        RssSharedBackground(
            itemId = route.itemId,
            modifier = Modifier.matchParentSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = navigationBarPadding),
        ) {
            if (showImage) {
                val context = LocalContext.current
                val transitionCrossfade = imageCrossfade && sharedTransitionScope == null
                val request = remember(
                    context,
                    route.itemId,
                    route.imageUrl,
                    transitionCrossfade,
                ) {
                    ImageRequest.Builder(context)
                        .data(route.imageUrl)
                        .memoryCacheKey(SplashSharedElementKey.rssImageMemoryCache(route.itemId))
                        .placeholderMemoryCacheKey(
                            SplashSharedElementKey.rssImageMemoryCache(route.itemId),
                        )
                        .apply { crossfade(transitionCrossfade) }
                        .build()
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(imageAspectRatio)
                        .zIndex(1f),
                ) {
                    RssDetailImage(
                        model = request,
                        contentDescription = route.title,
                        imageLoader = imageLoader,
                        contentScale = RssAnimatedContentScale(imageContentScaleFraction),
                        onSuccess = { width, height ->
                            if (width > 0 && height > 0) {
                                imageWidth = width
                                imageHeight = height
                            }
                        },
                        onError = { imageFailed = true },
                        modifier = Modifier
                            .matchParentSize()
                            .then(sharedImageModifier),
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(PolygonPalette.Grey2),
                    contentAlignment = Alignment.Center,
                ) {
                    BasicText(
                        text = route.title,
                        modifier = Modifier.padding(16.dp),
                        style = Polygon.typography.photoDescription.copy(
                            color = Polygon.colors.primaryText,
                        ),
                    )
                }
            }

            AnimatedVisibility(
                visibleState = detailContentVisibility,
                modifier = Modifier
                    .fillMaxWidth()
                    .then(routeExitModifier),
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 225,
                        easing = LinearOutSlowInEasing,
                    ),
                ),
                exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = 195,
                        easing = FastOutLinearInEasing,
                    ),
                ),
            ) {
                Column {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(PolygonPalette.Grey2)
                            .padding(16.dp),
                    ) {
                        if (showImage) {
                            BasicText(
                                text = route.title,
                                maxLines = 8,
                                overflow = TextOverflow.Ellipsis,
                                style = Polygon.typography.photoDescription.copy(
                                    color = Polygon.colors.primaryText,
                                ),
                            )
                        }

                        BasicText(
                            text = route.feedTitle,
                            modifier = Modifier.padding(top = 20.dp),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = Polygon.typography.artistName,
                        )

                        route.publishedAtEpochMillis?.let { timestamp ->
                            BasicText(
                                text = timestamp.formatRssDate(),
                                modifier = Modifier.padding(top = 4.dp),
                                style = Polygon.typography.artistUsername,
                            )
                        }

                        BasicText(
                            text = route.description ?: "No description",
                            modifier = Modifier.padding(top = 24.dp),
                            style = Polygon.typography.artistName.copy(
                                color = Polygon.colors.secondaryText,
                                fontFeatureSettings = null,
                            ),
                        )

                        route.link?.let { link ->
                            val context = LocalContext.current
                            BasicText(
                                text = "Open original",
                                modifier = Modifier
                                    .padding(top = 24.dp)
                                    .clickable {
                                        context.startActivity(
                                            Intent(Intent.ACTION_VIEW, Uri.parse(link)),
                                        )
                                    }
                                    .padding(vertical = 12.dp),
                                style = Polygon.typography.button.copy(
                                    color = Polygon.colors.primaryText,
                                ),
                            )
                        }
                    }
                }
            }
        }

        RssBackButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = statusBarPadding + 12.dp, start = 16.dp)
                .zIndex(2f),
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun RssSharedBackground(
    itemId: String,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = LocalSplashSharedTransitionScope.current
    val animatedVisibilityScope = LocalSplashAnimatedVisibilityScope.current
    val sharedBackgroundModifier =
        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
            with(sharedTransitionScope) {
                Modifier.sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = SplashSharedElementKey.rssSurface(itemId),
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = EnterTransition.None,
                    exit = ExitTransition.None,
                    boundsTransform = RssSharedBoundsTransform,
                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                )
            }
        } else {
            Modifier
        }

    Box(
        modifier = modifier
            .then(sharedBackgroundModifier)
            .background(PolygonPalette.White),
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun RssBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = LocalSplashSharedTransitionScope.current
    val animatedVisibilityScope = LocalSplashAnimatedVisibilityScope.current
    val buttonContentAlpha = animatedVisibilityScope?.transition?.animateFloat(
        transitionSpec = {
            if (targetState == EnterExitState.Visible) {
                tween(
                    durationMillis = 180,
                    delayMillis = 120,
                    easing = LinearOutSlowInEasing,
                )
            } else {
                tween(
                    durationMillis = 120,
                    easing = FastOutLinearInEasing,
                )
            }
        },
        label = "rss-back-content-alpha",
    ) { state ->
        if (state == EnterExitState.Visible) 1f else 0f
    }?.value ?: 1f
    val buttonContentModifier =
        if (animatedVisibilityScope != null) {
            with(animatedVisibilityScope) {
                Modifier
                    .graphicsLayer { alpha = buttonContentAlpha }
                    .animateEnterExit(
                        enter = androidx.compose.animation.slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = 180,
                                delayMillis = 120,
                                easing = LinearOutSlowInEasing,
                            ),
                        ),
                        exit = androidx.compose.animation.slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = 120,
                                easing = FastOutLinearInEasing,
                            ),
                        ),
                    )
            }
        } else {
            Modifier
        }
    val sharedTopChromeModifier =
        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
            with(sharedTransitionScope) {
                Modifier.sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = SplashSharedElementKey.photosTopChrome,
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = androidx.compose.animation.fadeIn(
                        animationSpec = tween(durationMillis = 225),
                    ),
                    exit = androidx.compose.animation.fadeOut(
                        animationSpec = tween(durationMillis = 195),
                    ),
                    boundsTransform = RssHeaderBoundsTransform,
                    resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds(
                        contentScale = ContentScale.FillBounds,
                    ),
                    zIndexInOverlay = 2f,
                )
            }
        } else {
            Modifier
        }

    Box(
        modifier = modifier
            .size(40.dp)
            .clickable(onClick = onClick),
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .then(sharedTopChromeModifier)
                .shadow(Polygon.elevation.medium)
                .clip(Polygon.shapes.small)
                .background(PolygonPalette.White),
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(Polygon.shapes.small)
                    .then(buttonContentModifier),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow_back_black_24dp),
                    contentDescription = "Back",
                    colorFilter = ColorFilter.tint(PolygonPalette.DarkGrey3),
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}

@Composable
private fun RssDetailImage(
    model: ImageRequest,
    contentDescription: String,
    imageLoader: ImageLoader?,
    contentScale: ContentScale,
    onSuccess: (Int, Int) -> Unit,
    onError: () -> Unit,
    modifier: Modifier,
) {
    if (imageLoader == null) {
        AsyncImage(
            model = model,
            contentDescription = contentDescription,
            contentScale = contentScale,
            onSuccess = { state ->
                onSuccess(state.result.image.width, state.result.image.height)
            },
            onError = { onError() },
            modifier = modifier,
        )
    } else {
        AsyncImage(
            model = model,
            imageLoader = imageLoader,
            contentDescription = contentDescription,
            contentScale = contentScale,
            onSuccess = { state ->
                onSuccess(state.result.image.width, state.result.image.height)
            },
            onError = { onError() },
            modifier = modifier,
        )
    }
}

private class RssAnimatedContentScale(
    private val fraction: Float,
) : ContentScale {
    override fun computeScaleFactor(srcSize: Size, dstSize: Size): ScaleFactor {
        val crop = ContentScale.Crop.computeScaleFactor(srcSize, dstSize)
        val fit = ContentScale.Fit.computeScaleFactor(srcSize, dstSize)
        val progress = fraction.coerceIn(0f, 1f)
        return ScaleFactor(
            scaleX = crop.scaleX + (fit.scaleX - crop.scaleX) * progress,
            scaleY = crop.scaleY + (fit.scaleY - crop.scaleY) * progress,
        )
    }
}

private fun Long.formatRssDate(): String {
    return DateFormat
        .getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault())
        .format(Date(this))
}
