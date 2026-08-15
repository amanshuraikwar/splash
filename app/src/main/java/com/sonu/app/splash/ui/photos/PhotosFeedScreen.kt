package com.sonu.app.splash.ui.photos

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sonu.app.splash.data.DataManager
import com.sonu.app.splash.model.unsplash.Photo
import com.sonu.app.splash.model.unsplash.Collection as UnsplashCollection
import com.sonu.app.splash.ui.navigation.LocalSplashAnimatedVisibilityScope
import com.sonu.app.splash.ui.navigation.LocalSplashSharedTransitionScope
import com.sonu.app.splash.ui.navigation.SplashDestinationScope
import com.sonu.app.splash.ui.navigation.SplashRoute
import com.sonu.app.splash.ui.navigation.SplashSharedElementKey
import com.sonu.app.splash.ui.theme.Polygon
import com.sonu.app.splash.ui.theme.PolygonPalette
import java.text.NumberFormat
import java.util.Locale
import kotlinx.coroutines.launch

private val LocalPhotosFeedScrollChanged = staticCompositionLocalOf<(Boolean) -> Unit> { {} }

private val PhotoSharedBoundsTransform = BoundsTransform { _, _ ->
    tween(
        durationMillis = 375,
        easing = FastOutSlowInEasing,
    )
}

private val PhotosHeaderBoundsTransform = BoundsTransform { _, _ ->
    tween(
        durationMillis = 300,
        easing = FastOutSlowInEasing,
    )
}

internal enum class PhotosFeedPage(val title: String) {
    AllPhotos("all photos"),
    Collections("collections"),
}

internal object PhotosFeedTestTags {
    fun photoCard(photoId: String) = "photos-feed:photo-card:$photoId"
}

@Composable
fun PhotosFeedRoute(
    dataManager: DataManager,
    destinationScope: SplashDestinationScope,
    onPhotoClick: ((Photo) -> Unit)? = null,
) {
    val photoClick = onPhotoClick ?: { photo: Photo ->
        val photoId = photo.id
        if (!photoId.isNullOrBlank()) {
            destinationScope.navigate(SplashRoute.PhotoDescription.fromPhoto(photo))
        }
    }

    PhotosFeedPagerScaffold { page ->
        when (page) {
            PhotosFeedPage.AllPhotos -> PhotosFeedPageRoute(
                dataManager = dataManager,
                onPhotoClick = photoClick,
            )

            PhotosFeedPage.Collections -> CollectionsFeedRoute(dataManager = dataManager)
        }
    }
}

@Composable
private fun PhotosFeedPageRoute(
    dataManager: DataManager,
    onPhotoClick: ((Photo) -> Unit)? = null,
    viewModel: PhotosFeedViewModel = viewModel(
        key = PhotosFeedPage.AllPhotos.name,
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
        includeStatusBarPadding = true,
    )
}

@Composable
private fun CollectionsFeedRoute(
    dataManager: DataManager,
    viewModel: CollectionsFeedViewModel = viewModel(
        key = PhotosFeedPage.Collections.name,
        factory = CollectionsFeedViewModel.Factory(dataManager),
    ),
) {
    val state = viewModel.uiState

    LaunchedEffect(viewModel) {
        viewModel.loadInitial()
    }

    CollectionsFeedScreen(
        state = state,
        onRetryClick = viewModel::refresh,
        onLoadMore = viewModel::loadMore,
        includeStatusBarPadding = true,
    )
}

@Composable
internal fun PhotosFeedPagerScaffold(
    modifier: Modifier = Modifier,
    pages: List<PhotosFeedPage> = PhotosFeedPage.entries,
    pageContent: @Composable (PhotosFeedPage) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()
    val pageScrollStates = remember { mutableStateMapOf<Int, Boolean>() }
    val density = LocalDensity.current
    val statusBarPadding = with(density) {
        WindowInsets.statusBars.getTop(this).toDp()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Polygon.colors.background),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize(),
        ) { index ->
            CompositionLocalProvider(
                LocalPhotosFeedScrollChanged provides { isScrolled ->
                    pageScrollStates[index] = isScrolled
                },
            ) {
                pageContent(pages[index])
            }
        }

        PhotosFeedHeader(
            pages = pages,
            selectedPage = pagerState.currentPage,
            statusBarPadding = statusBarPadding,
            floating = pageScrollStates[pagerState.currentPage] == true,
            onPageClick = { index ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            },
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun PhotosFeedHeader(
    pages: List<PhotosFeedPage>,
    selectedPage: Int,
    statusBarPadding: Dp,
    floating: Boolean,
    onPageClick: (Int) -> Unit,
) {
    val sharedTransitionScope = LocalSplashSharedTransitionScope.current
    val animatedVisibilityScope = LocalSplashAnimatedVisibilityScope.current
    val navigationTransitionActive = sharedTransitionScope?.isTransitionActive == true
    val headerContentAlpha = animatedVisibilityScope?.transition?.animateFloat(
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
        label = "photos-header-content-alpha",
    ) { state ->
        if (state == EnterExitState.Visible) 1f else 0f
    }?.value ?: 1f
    val headerContentModifier =
        if (animatedVisibilityScope != null) {
            with(animatedVisibilityScope) {
                Modifier
                    .graphicsLayer { alpha = headerContentAlpha }
                    .animateEnterExit(
                    enter = slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(
                            durationMillis = 180,
                            delayMillis = 120,
                            easing = LinearOutSlowInEasing,
                        ),
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { -it },
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
                    boundsTransform = PhotosHeaderBoundsTransform,
                    resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds(
                        contentScale = ContentScale.FillBounds,
                    ),
                    zIndexInOverlay = 2f,
                )
            }
        } else {
            Modifier
        }
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(1f),
    ) {
        val headerWidth = animateDpAsState(
            targetValue = if (floating && !navigationTransitionActive) {
                maxWidth - 32.dp
            } else {
                maxWidth
            },
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing,
            ),
            label = "photos-header-width",
        )
        val headerHeight = animateDpAsState(
            targetValue = if (floating && !navigationTransitionActive) {
                56.dp
            } else {
                statusBarPadding + 56.dp
            },
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing,
            ),
            label = "photos-header-height",
        )
        val headerStart = animateDpAsState(
            targetValue = if (floating && !navigationTransitionActive) 16.dp else 0.dp,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing,
            ),
            label = "photos-header-start",
        )
        val headerTop = animateDpAsState(
            targetValue = if (floating && !navigationTransitionActive) {
                statusBarPadding + 12.dp
            } else {
                0.dp
            },
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing,
            ),
            label = "photos-header-top",
        )
        val contentTopPadding = animateDpAsState(
            targetValue = if (floating && !navigationTransitionActive) 0.dp else statusBarPadding,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing,
            ),
            label = "photos-header-content-top",
        )
        val cornerRadius = animateDpAsState(
            targetValue = if (floating && !navigationTransitionActive) 4.dp else 0.dp,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing,
            ),
            label = "photos-header-corners",
        )
        val headerShape = RoundedCornerShape(cornerRadius.value)

        Box(
            modifier = Modifier
                .padding(start = headerStart.value, top = headerTop.value)
                .width(headerWidth.value)
                .height(headerHeight.value),
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .then(sharedTopChromeModifier)
                    .shadow(Polygon.elevation.medium, headerShape)
                    .clip(headerShape)
                    .background(Polygon.colors.surface),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(headerShape)
                        .padding(top = contentTopPadding.value),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(start = 12.dp, end = 48.dp)
                            .then(headerContentModifier),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        pages.forEachIndexed { index, page ->
                            PhotosFeedHeaderTab(
                                title = page.title,
                                selected = index == selectedPage,
                                onClick = { onPageClick(index) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PhotosFeedHeaderTab(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .height(56.dp)
            .width(IntrinsicSize.Max)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .height(54.dp)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center,
        ) {
            BasicText(
                text = title,
                maxLines = 1,
                style = Polygon.typography.tab.copy(
                    color = if (selected) {
                        Polygon.colors.primaryText
                    } else {
                        Polygon.colors.disabledText
                    },
                ),
            )
        }
        Box(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .background(
                    if (selected) {
                        PolygonPalette.DarkGrey1
                    } else {
                        Color.Transparent
                    },
                ),
        )
    }
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
    includeStatusBarPadding: Boolean = true,
) {
    val gridState = rememberLazyStaggeredGridState()
    val onScrollChanged = LocalPhotosFeedScrollChanged.current
    val density = LocalDensity.current
    val statusBarPadding = with(density) {
        WindowInsets.statusBars.getTop(this).toDp()
    }
    val navigationBarPadding = with(density) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }
    val gridTopContentPadding = 56.dp + if (includeStatusBarPadding) statusBarPadding else 0.dp
    val floatingThresholdPx = with(density) {
        gridTopContentPadding.toPx()
    }
    val isScrolled = remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex > 0 ||
                gridState.firstVisibleItemScrollOffset >= floatingThresholdPx
        }
    }
    LaunchedEffect(isScrolled.value) {
        onScrollChanged(isScrolled.value)
    }
    val contentPadding = PaddingValues(
        top = gridTopContentPadding,
        bottom = navigationBarPadding,
    )
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
                    contentPadding = contentPadding,
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

@Composable
internal fun CollectionsFeedScreen(
    state: CollectionsFeedUiState,
    onRetryClick: () -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier,
    onCollectionClick: ((UnsplashCollection) -> Unit)? = null,
    onImageSuccess: (UnsplashCollection) -> Unit = {},
    imageLoader: ImageLoader? = null,
    imageCrossfade: Boolean = true,
    includeStatusBarPadding: Boolean = true,
) {
    val gridState = rememberLazyStaggeredGridState()
    val onScrollChanged = LocalPhotosFeedScrollChanged.current
    val density = LocalDensity.current
    val statusBarPadding = with(density) {
        WindowInsets.statusBars.getTop(this).toDp()
    }
    val navigationBarPadding = with(density) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }
    val gridTopContentPadding = 56.dp + if (includeStatusBarPadding) statusBarPadding else 0.dp
    val floatingThresholdPx = with(density) {
        gridTopContentPadding.toPx()
    }
    val isScrolled = remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex > 0 ||
                gridState.firstVisibleItemScrollOffset >= floatingThresholdPx
        }
    }
    LaunchedEffect(isScrolled.value) {
        onScrollChanged(isScrolled.value)
    }
    val contentPadding = PaddingValues(
        top = gridTopContentPadding,
        bottom = navigationBarPadding,
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Polygon.colors.background),
    ) {
        when {
            state.collections.isNotEmpty() -> {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(1),
                    state = gridState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = contentPadding,
                ) {
                    itemsIndexed(
                        items = state.collections,
                        key = { _, collection -> collection.id },
                    ) { index, collection ->
                        CollectionFeedCard(
                            collection = collection,
                            onCollectionClick = onCollectionClick,
                            onImageSuccess = onImageSuccess,
                            imageLoader = imageLoader,
                            imageCrossfade = imageCrossfade,
                        )

                        if (index == state.collections.lastIndex) {
                            LaunchedEffect(state.collections.size) {
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
                CenterMessage(text = "Loading collections")
            }

            else -> {
                CenterMessage(
                    text = state.errorMessage ?: "No collections yet",
                    actionText = "Retry",
                    onActionClick = onRetryClick,
                )
            }
        }
    }
}

@Composable
private fun CollectionFeedCard(
    collection: UnsplashCollection,
    onCollectionClick: ((UnsplashCollection) -> Unit)?,
    onImageSuccess: (UnsplashCollection) -> Unit,
    imageLoader: ImageLoader?,
    imageCrossfade: Boolean,
    modifier: Modifier = Modifier,
) {
    val coverPhoto = collection.coverPhoto
    val aspectRatio = coverPhoto?.safeAspectRatio() ?: 1f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 180.dp)
            .aspectRatio(aspectRatio)
            .clip(Polygon.shapes.none)
            .background(coverPhoto?.backgroundColor() ?: PolygonPalette.Grey2)
            .then(
                if (onCollectionClick != null) {
                    Modifier.clickable { onCollectionClick(collection) }
                } else {
                    Modifier
                },
            ),
    ) {
        CollectionCoverImage(
            collection = collection,
            coverPhoto = coverPhoto,
            onSuccess = onImageSuccess,
            imageLoader = imageLoader,
            crossfade = imageCrossfade,
            modifier = Modifier.matchParentSize(),
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(PolygonPalette.DarkGrey1Trans),
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 12.dp, end = 16.dp),
        ) {
            BasicText(
                text = "${collection.totalPhotos.formatCount()} photos",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = Polygon.typography.collectionPhotoCount,
            )

            BasicText(
                text = collection.title.orEmpty().uppercase(Locale.getDefault()),
                modifier = Modifier.padding(top = 4.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = Polygon.typography.collectionTitle,
            )
        }

        CollectionArtist(
            collection = collection,
            imageLoader = imageLoader,
            crossfade = imageCrossfade,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(12.dp),
        )
    }
}

@Composable
private fun CollectionCoverImage(
    collection: UnsplashCollection,
    coverPhoto: Photo?,
    onSuccess: (UnsplashCollection) -> Unit,
    imageLoader: ImageLoader?,
    crossfade: Boolean,
    modifier: Modifier = Modifier,
) {
    val imageUrl = coverPhoto?.photoUrls?.small.orEmpty()
    if (imageUrl.isBlank()) {
        return
    }

    val context = LocalContext.current
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .apply { crossfade(crossfade) }
        .build()

    if (imageLoader == null) {
        AsyncImage(
            model = request,
            contentDescription = collection.accessibilityLabel(),
            contentScale = ContentScale.FillBounds,
            onSuccess = { onSuccess(collection) },
            modifier = modifier,
        )
    } else {
        AsyncImage(
            model = request,
            contentDescription = collection.accessibilityLabel(),
            imageLoader = imageLoader,
            contentScale = ContentScale.FillBounds,
            onSuccess = { onSuccess(collection) },
            modifier = modifier,
        )
    }
}

@Composable
private fun CollectionArtist(
    collection: UnsplashCollection,
    imageLoader: ImageLoader?,
    crossfade: Boolean,
    modifier: Modifier = Modifier,
) {
    val artistName = collection.user?.name.orEmpty()
    val artistImageUrl = collection.user?.profileImage?.large.orEmpty()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(PolygonPalette.Grey1Trans),
        ) {
            if (artistImageUrl.isNotBlank()) {
                val context = LocalContext.current
                val request = ImageRequest.Builder(context)
                    .data(artistImageUrl)
                    .apply { crossfade(crossfade) }
                    .build()

                if (imageLoader == null) {
                    AsyncImage(
                        model = request,
                        contentDescription = artistName.ifBlank { "Collection artist" },
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize(),
                    )
                } else {
                    AsyncImage(
                        model = request,
                        contentDescription = artistName.ifBlank { "Collection artist" },
                        imageLoader = imageLoader,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize(),
                    )
                }
            }
        }

        BasicText(
            text = artistName.lowercase(Locale.getDefault()),
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = Polygon.typography.artistNameLight,
        )
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
    val cardModifier = Modifier
        .fillMaxWidth()
        .then(
            if (onPhotoClick != null) {
                Modifier.clickable { onPhotoClick(photo) }
            } else {
                Modifier
            },
        )

    Box(
        modifier = modifier
            .then(cardModifier)
            .testTag(PhotosFeedTestTags.photoCard(photoId))
            .clip(Polygon.shapes.none)
            .background(PolygonPalette.White),
    ) {
        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
            with(sharedTransitionScope) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = SplashSharedElementKey.photoSurface(photoId),
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            enter = EnterTransition.None,
                            exit = ExitTransition.None,
                            boundsTransform = PhotoSharedBoundsTransform,
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                        )
                        .background(PolygonPalette.White),
                )
            }
        }

        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
            with(sharedTransitionScope) {
                PhotoImage(
                    photo = photo,
                    modifier = imageModifier.sharedElement(
                        sharedContentState = rememberSharedContentState(
                            key = SplashSharedElementKey.photoImage(photoId),
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = PhotoSharedBoundsTransform,
                    ),
                    onSuccess = onImageSuccess,
                    imageLoader = imageLoader,
                    crossfade = false,
                    memoryCacheKey = SplashSharedElementKey.photoImageMemoryCache(photoId),
                )
            }
        } else {
            PhotoImage(
                photo = photo,
                modifier = imageModifier,
                onSuccess = onImageSuccess,
                imageLoader = imageLoader,
                crossfade = imageCrossfade,
                memoryCacheKey = null,
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
    memoryCacheKey: String?,
) {
    val context = LocalContext.current
    val imageUrl = photo.photoUrls?.small.orEmpty()
    val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .memoryCacheKey(memoryCacheKey)
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

private fun UnsplashCollection.accessibilityLabel(): String {
    title?.takeIf { it.isNotBlank() }?.let { return it }
    user?.name?.takeIf { it.isNotBlank() }?.let { return "Collection by $it" }
    return "Unsplash collection"
}

private fun Int.formatCount(): String {
    return NumberFormat.getIntegerInstance(Locale.getDefault()).format(this)
}
