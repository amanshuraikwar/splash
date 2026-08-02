package com.sonu.app.splash.ui.photos

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sonu.app.splash.data.DataManager
import com.sonu.app.splash.model.unsplash.Photo
import com.sonu.app.splash.ui.navigation.LocalSplashAnimatedVisibilityScope
import com.sonu.app.splash.ui.navigation.LocalSplashSharedTransitionScope
import com.sonu.app.splash.ui.navigation.SplashDestinationScope
import com.sonu.app.splash.ui.navigation.SplashSharedElementKey
import com.sonu.app.splash.ui.theme.Polygon
import com.sonu.app.splash.ui.theme.PolygonPalette

@Composable
fun PhotosFeedRoute(
    dataManager: DataManager,
    destinationScope: SplashDestinationScope,
    onPhotoClick: ((Photo) -> Unit)? = null,
    viewModel: PhotosFeedViewModel = viewModel(
        factory = PhotosFeedViewModel.Factory(dataManager),
    ),
) {
    val state = viewModel.uiState

    LaunchedEffect(viewModel) {
        viewModel.loadInitial()
    }

    PhotosFeedScreen(
        state = state,
        onRetryClick = viewModel::refresh,
        onLoadMore = viewModel::loadMore,
        onPhotoClick = onPhotoClick,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PhotosFeedScreen(
    state: PhotosFeedUiState,
    onRetryClick: () -> Unit,
    onLoadMore: () -> Unit,
    onPhotoClick: ((Photo) -> Unit)?,
    modifier: Modifier = Modifier,
    onImageSuccess: (Photo) -> Unit = {},
    imageLoader: ImageLoader? = null,
    imageCrossfade: Boolean = true,
) {
    val gridState = rememberLazyStaggeredGridState()
    val density = LocalDensity.current
    val gridEdgePadding = 8.dp
    val statusBarPadding = with(density) {
        WindowInsets.statusBars.getTop(this).toDp()
    }
    val navigationBarPadding = with(density) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Polygon.colors.background),
    ) {
        when {
            state.photos.isNotEmpty() -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    state = gridState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = gridEdgePadding,
                        top = statusBarPadding + gridEdgePadding,
                        end = gridEdgePadding,
                        bottom = navigationBarPadding + gridEdgePadding,
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalItemSpacing = 8.dp,
                ) {
                    itemsIndexed(
                        items = state.photos,
                        key = { _, photo -> photo.id.orEmpty() },
                    ) { index, photo ->
                        PhotoFeedCard(
                            photo = photo,
                            onPhotoClick = onPhotoClick,
                            onImageSuccess = onImageSuccess,
                            imageLoader = imageLoader,
                            imageCrossfade = imageCrossfade,
                        )

                        if (index == state.photos.lastIndex) {
                            LaunchedEffect(state.photos.size) {
                                onLoadMore()
                            }
                        }
                    }

                    if (state.isLoadingMore) {
                        item {
                            InlineLoadingMessage()
                        }
                    }
                }
            }

            state.isInitialLoading -> {
                CenterMessage(text = "Loading photos")
            }

            else -> {
                CenterMessage(
                    text = state.errorMessage ?: "No photos yet",
                    actionText = "Retry",
                    onActionClick = onRetryClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun PhotoFeedCard(
    photo: Photo,
    onPhotoClick: ((Photo) -> Unit)?,
    modifier: Modifier = Modifier,
    onImageSuccess: (Photo) -> Unit,
    imageLoader: ImageLoader?,
    imageCrossfade: Boolean,
) {
    val sharedTransitionScope = LocalSplashSharedTransitionScope.current
    val animatedVisibilityScope = LocalSplashAnimatedVisibilityScope.current
    val aspectRatio = photo.safeAspectRatio()
    val photoId = photo.id.orEmpty()
    val imageModifier = Modifier
        .fillMaxWidth()
        .aspectRatio(aspectRatio)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(Polygon.shapes.none)
            .background(photo.backgroundColor())
            .then(
                if (onPhotoClick != null) {
                    Modifier.clickable { onPhotoClick(photo) }
                } else {
                    Modifier
                },
            ),
    ) {
        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
            with(sharedTransitionScope) {
                PhotoImage(
                    photo = photo,
                    modifier = imageModifier.sharedElement(
                        sharedContentState = rememberSharedContentState(
                            key = SplashSharedElementKey.photoImage(photoId),
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                    ),
                    onSuccess = onImageSuccess,
                    imageLoader = imageLoader,
                    crossfade = imageCrossfade,
                )
            }
        } else {
            PhotoImage(
                photo = photo,
                modifier = imageModifier,
                onSuccess = onImageSuccess,
                imageLoader = imageLoader,
                crossfade = imageCrossfade,
            )
        }
    }
}

@Composable
private fun PhotoImage(
    photo: Photo,
    modifier: Modifier = Modifier,
    onSuccess: (Photo) -> Unit,
    imageLoader: ImageLoader?,
    crossfade: Boolean,
) {
    val context = LocalContext.current
    val imageUrl = photo.photoUrls?.small.orEmpty()
    val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .apply { crossfade(crossfade) }
            .build()

    if (imageLoader == null) {
        AsyncImage(
            model = request,
            contentDescription = photo.accessibilityLabel(),
            contentScale = ContentScale.FillBounds,
            onSuccess = { onSuccess(photo) },
            modifier = modifier,
        )
    } else {
        AsyncImage(
            model = request,
            contentDescription = photo.accessibilityLabel(),
            imageLoader = imageLoader,
            contentScale = ContentScale.FillBounds,
            onSuccess = { onSuccess(photo) },
            modifier = modifier,
        )
    }
}

@Composable
private fun CenterMessage(
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
                    .defaultMinSize(
                        minWidth = 96.dp,
                        minHeight = Polygon.dimensions.iconButtonTouchTarget,
                    )
                    .clickable { onActionClick() }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                style = Polygon.typography.button.copy(
                    color = Polygon.colors.primaryText,
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}

@Composable
private fun InlineLoadingMessage(
    modifier: Modifier = Modifier,
) {
    BasicText(
        text = "Loading",
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        style = Polygon.typography.errorMessage.copy(
            color = Polygon.colors.secondaryText,
            textAlign = TextAlign.Center,
        ),
    )
}

private fun Photo.safeAspectRatio(): Float {
    return if (width > 0 && height > 0) {
        width.toFloat() / height.toFloat()
    } else {
        1f
    }
}

private fun Photo.backgroundColor(): Color {
    return runCatching {
        Color(android.graphics.Color.parseColor(color))
    }.getOrDefault(PolygonPalette.Grey2)
}

private fun Photo.accessibilityLabel(): String {
    description?.takeIf { it.isNotBlank() }?.let { return it }
    user?.name?.takeIf { it.isNotBlank() }?.let { return "Photo by $it" }
    return "Unsplash photo"
}
