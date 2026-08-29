package com.sonu.app.splash.ui.rss

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sonu.app.splash.data.rss.RssRepository
import com.sonu.app.splash.data.rss.model.RssItem
import com.sonu.app.splash.ui.navigation.SplashDestinationScope
import com.sonu.app.splash.ui.navigation.SplashRoute
import com.sonu.app.splash.ui.navigation.SplashSharedElementKey
import com.sonu.app.splash.ui.navigation.LocalSplashAnimatedVisibilityScope
import com.sonu.app.splash.ui.navigation.LocalSplashSharedTransitionScope
import com.sonu.app.splash.ui.photos.LocalMediaFeedScrollChanged
import com.sonu.app.polygon.theme.Polygon
import com.sonu.app.polygon.theme.PolygonPalette

private val RssSharedBoundsTransform = BoundsTransform { _, _ ->
    tween(
        durationMillis = 375,
        easing = FastOutSlowInEasing,
    )
}

@Composable
internal fun RssFeedRoute(
    repository: RssRepository,
    destinationScope: SplashDestinationScope,
    viewModel: RssFeedViewModel = viewModel(
        key = "rss-feed",
        factory = RssFeedViewModel.Factory(repository),
    ),
) {
    val state = viewModel.uiState
    val imageDimensions = remember { mutableStateMapOf<String, Pair<Int, Int>>() }

    LaunchedEffect(viewModel) {
        viewModel.loadInitial()
    }

    RssFeedScreen(
        state = state,
        onRetryClick = viewModel::refresh,
        onItemClick = { item ->
            val dimensions = imageDimensions[item.id]
            val itemWithDimensions = if (dimensions != null) {
                item.copy(
                    imageWidth = dimensions.first,
                    imageHeight = dimensions.second,
                )
            } else {
                item
            }
            destinationScope.navigate(SplashRoute.RssDetail.fromItem(itemWithDimensions))
        },
        onItemImageDimensions = { item, width, height ->
            imageDimensions[item.id] = width to height
        },
    )
}

@Composable
internal fun RssFeedScreen(
    state: RssFeedUiState,
    onRetryClick: () -> Unit,
    onItemClick: (RssItem) -> Unit,
    onItemImageDimensions: (RssItem, Int, Int) -> Unit = { _, _, _ -> },
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader? = null,
    imageCrossfade: Boolean = true,
    includeStatusBarPadding: Boolean = true,
) {
    val gridState = rememberLazyStaggeredGridState()
    val onScrollChanged = LocalMediaFeedScrollChanged.current
    val density = LocalDensity.current
    val statusBarPadding = with(density) {
        WindowInsets.statusBars.getTop(this).toDp()
    }
    val navigationBarPadding = with(density) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }
    val gridTopContentPadding = 56.dp + if (includeStatusBarPadding) statusBarPadding else 0.dp
    val floatingThresholdPx = with(density) { gridTopContentPadding.toPx() }
    val isScrolled = remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex > 0 ||
                gridState.firstVisibleItemScrollOffset >= floatingThresholdPx
        }
    }

    LaunchedEffect(isScrolled.value) {
        onScrollChanged(isScrolled.value)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Polygon.colors.background),
    ) {
        when {
            state.items.isNotEmpty() -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    state = gridState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = gridTopContentPadding,
                        bottom = navigationBarPadding,
                    ),
                ) {
                    itemsIndexed(
                        items = state.items,
                        key = { _, item -> item.id },
                    ) { _, item ->
                        RssFeedCard(
                            item = item,
                            onClick = { onItemClick(item) },
                            onImageSuccess = { width, height ->
                                onItemImageDimensions(item, width, height)
                            },
                            imageLoader = imageLoader,
                            imageCrossfade = imageCrossfade,
                        )
                    }
                }
            }

            state.isInitialLoading -> {
                RssCenterMessage(text = "Loading RSS feeds")
            }

            else -> {
                RssCenterMessage(
                    text = state.errorMessage ?: "No RSS items yet",
                    actionText = "Retry",
                    onActionClick = onRetryClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun RssFeedCard(
    item: RssItem,
    onClick: () -> Unit,
    onImageSuccess: (Int, Int) -> Unit,
    imageLoader: ImageLoader?,
    imageCrossfade: Boolean,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = LocalSplashSharedTransitionScope.current
    val animatedVisibilityScope = LocalSplashAnimatedVisibilityScope.current
    var imageFailed by remember(item.id, item.imageUrl) { mutableStateOf(false) }
    val showImage = !item.imageUrl.isNullOrBlank() && !imageFailed
    val imageBindingKey = "${item.id}|${item.imageUrl}"
    val sharedSurfaceModifier =
        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
            with(sharedTransitionScope) {
                Modifier.sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = SplashSharedElementKey.rssSurface(item.id),
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
    val sharedImageModifier =
        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
            with(sharedTransitionScope) {
                Modifier.sharedElement(
                    sharedContentState = rememberSharedContentState(
                        key = SplashSharedElementKey.rssImage(item.id),
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = RssSharedBoundsTransform,
                )
            }
        } else {
            Modifier
        }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .then(sharedSurfaceModifier)
                .background(PolygonPalette.White),
        )

        if (showImage) {
            val context = LocalContext.current
            val transitionCrossfade = imageCrossfade && sharedTransitionScope == null
            val request = remember(
                context,
                item.id,
                item.imageUrl,
                transitionCrossfade,
            ) {
                ImageRequest.Builder(context)
                    .data(item.imageUrl)
                    .memoryCacheKey(SplashSharedElementKey.rssImageMemoryCache(item.id))
                    .apply { crossfade(transitionCrossfade) }
                    .build()
            }

            if (imageLoader == null) {
                key(imageBindingKey) {
                    AsyncImage(
                        model = request,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        onSuccess = { state ->
                            onImageSuccess(state.result.image.width, state.result.image.height)
                        },
                        onError = {
                            imageFailed = true
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .then(sharedImageModifier),
                    )
                }
            } else {
                key(imageBindingKey) {
                    AsyncImage(
                        model = request,
                        imageLoader = imageLoader,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        onSuccess = { state ->
                            onImageSuccess(state.result.image.width, state.result.image.height)
                        },
                        onError = {
                            imageFailed = true
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .then(sharedImageModifier),
                    )
                }
            }
        } else {
            BasicText(
                text = item.title,
                modifier = Modifier.padding(12.dp),
                maxLines = 8,
                overflow = TextOverflow.Ellipsis,
                style = Polygon.typography.photoDescription.copy(
                    color = Polygon.colors.primaryText,
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}

@Composable
private fun RssCenterMessage(
    text: String,
    modifier: Modifier = Modifier,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Polygon.dimensions.screenMarginHorizontal),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        BasicText(
            text = text,
            style = Polygon.typography.errorMessage.copy(
                color = Polygon.colors.secondaryText,
                textAlign = TextAlign.Center,
            ),
        )

        if (actionText != null && onActionClick != null) {
            BasicText(
                text = actionText,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable(onClick = onActionClick)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                style = Polygon.typography.button.copy(
                    color = Polygon.colors.primaryText,
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}
