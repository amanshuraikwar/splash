package com.sonu.app.splash.ui.photodescription

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.MeshGradientPainter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.toBitmap
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.crossfade
import com.sonu.app.splash.R
import com.sonu.app.splash.data.DataManager
import com.sonu.app.splash.model.unsplash.Exif
import com.sonu.app.splash.model.unsplash.Location
import com.sonu.app.splash.model.unsplash.Photo
import com.sonu.app.splash.model.unsplash.User
import com.sonu.app.splash.ui.navigation.LocalSplashAnimatedVisibilityScope
import com.sonu.app.splash.ui.navigation.LocalSplashSharedTransitionScope
import com.sonu.app.splash.ui.navigation.SplashDestinationScope
import com.sonu.app.splash.ui.navigation.SplashRoute
import com.sonu.app.splash.ui.navigation.SplashSharedElementKey
import com.sonu.app.polygon.theme.Polygon
import com.sonu.app.polygon.theme.PolygonPalette
import com.sonu.app.polygon.theme.PolygonTheme
import java.text.NumberFormat
import java.util.Locale
import kotlinx.coroutines.flow.first
import androidx.palette.graphics.Palette

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

@Composable
internal fun PhotoDescriptionRoute(
    dataManager: DataManager,
    route: SplashRoute.PhotoDescription,
    destinationScope: SplashDestinationScope,
    viewModel: PhotoDescriptionViewModel = viewModel(
        key = "photo-description:${route.photoId}",
        factory = PhotoDescriptionViewModel.Factory(
            dataManager = dataManager,
            preview = route.toPreview(),
        ),
    ),
) {
    val state = viewModel.uiState

    LaunchedEffect(viewModel) {
        viewModel.loadInitial()
    }

    PhotoDescriptionScreen(
        state = state,
        onBackClick = { destinationScope.popBackStack() },
        onRetryClick = viewModel::retry,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun PhotoDescriptionScreen(
    state: PhotoDescriptionUiState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader? = null,
    imageCrossfade: Boolean = true,
) {
    val density = LocalDensity.current
    val statusBarPadding = with(density) { WindowInsets.statusBars.getTop(this).toDp() }
    val navigationBarPadding = with(density) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    }
    val photo = state.photo
    val fallbackTitleColor = (photo?.color ?: state.preview.color)
        .toComposeColor(Polygon.colors.primaryText)
    var titleMeshColors by remember(state.photoId) {
        mutableStateOf(fallbackTitleColor.toMeshColorSet())
    }
    val heroAspectRatio = photo?.safeAspectRatio() ?: state.preview.safeAspectRatio()
    val sharedTransitionScope = LocalSplashSharedTransitionScope.current
    val animatedVisibilityScope = LocalSplashAnimatedVisibilityScope.current
    val hasNavigationAnimation = animatedVisibilityScope != null
    val isSharedTransitionActive = sharedTransitionScope?.isTransitionActive == true
    val detailContentVisibility = remember(state.photoId) {
        MutableTransitionState(false)
    }

    LaunchedEffect(state.photoId, hasNavigationAnimation, sharedTransitionScope) {
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent),
    ) {
        PhotoSharedBackground(
            photoId = state.photoId,
            modifier = Modifier.matchParentSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = navigationBarPadding),
        ) {
            PhotoHero(
                state = state,
                imageLoader = imageLoader,
                imageCrossfade = imageCrossfade,
                onImageSuccess = { result ->
                    result.image.toBitmap(
                        width = result.image.width.coerceAtLeast(1),
                        height = result.image.height.coerceAtLeast(1),
                    ).let { bitmap ->
                        Palette.from(bitmap)
                            .maximumColorCount(8)
                            .clearFilters()
                            .generate { palette ->
                                titleMeshColors = palette?.toMeshColorSet(fallbackTitleColor)
                                    ?: fallbackTitleColor.toMeshColorSet()
                            }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(heroAspectRatio),
            )

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
                    Spacer(modifier = Modifier.height(36.dp))

                    PhotoLocationRow(
                        location = photo?.location,
                    )

                    PhotoDescriptionText(
                        description = photo?.description ?: state.preview.description,
                        titleMeshColors = titleMeshColors,
                    )

                    PhotoUserBlock(
                        user = photo?.user,
                        preview = state.preview,
                        imageLoader = imageLoader,
                        imageCrossfade = imageCrossfade,
                    )

                    PhotoStatsStrip(photo = photo)

                    PhotoInfoGrid(photo = photo)

                    if (state.isLoading) {
                        InlineDetailMessage(text = "Loading details")
                    }

                    state.errorMessage?.let { message ->
                        InlineDetailMessage(
                            text = message,
                            actionText = "Retry",
                            onActionClick = onRetryClick,
                        )
                    }

                    state.actionMessage?.let { message ->
                        InlineDetailMessage(text = message)
                    }
                }
            }
        }

        PhotoBackButton(
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
private fun PhotoSharedBackground(
    photoId: String,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = LocalSplashSharedTransitionScope.current
    val animatedVisibilityScope = LocalSplashAnimatedVisibilityScope.current

    val sharedBackgroundModifier =
        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
            with(sharedTransitionScope) {
                Modifier.sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = SplashSharedElementKey.photoSurface(photoId),
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = EnterTransition.None,
                    exit = ExitTransition.None,
                    boundsTransform = PhotoSharedBoundsTransform,
                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                    renderInOverlayDuringTransition = true,
                    zIndexInOverlay = 0f,
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
private fun PhotoHero(
    state: PhotoDescriptionUiState,
    imageLoader: ImageLoader?,
    imageCrossfade: Boolean,
    onImageSuccess: (SuccessResult) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sharedTransitionScope = LocalSplashSharedTransitionScope.current
    val animatedVisibilityScope = LocalSplashAnimatedVisibilityScope.current
    val sharedImageModifier =
        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
            with(sharedTransitionScope) {
                Modifier.sharedElement(
                    sharedContentState = rememberSharedContentState(
                        key = SplashSharedElementKey.photoImage(state.photoId),
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = PhotoSharedBoundsTransform,
                    zIndexInOverlay = 1f,
                )
            }
        } else {
            Modifier
        }

    Box(
        modifier = modifier
            .background(Color.Transparent)
            .zIndex(1f),
    ) {
        val stableImageUrl = state.preview.imageUrl
            ?: state.photo?.photoUrls?.small
            ?: state.photo?.photoUrls?.regular
        val sharedImageMemoryCacheKey = SplashSharedElementKey.photoImageMemoryCache(state.photoId)

        PhotoDetailImage(
            imageUrl = stableImageUrl,
            contentDescription = state.photo?.accessibilityLabel()
                ?: state.preview.accessibilityLabel(),
            imageLoader = imageLoader,
            crossfade = false,
            contentScale = ContentScale.FillBounds,
            memoryCacheKey = sharedImageMemoryCacheKey,
            onSuccess = onImageSuccess,
            modifier = Modifier
                .matchParentSize()
                .then(sharedImageModifier),
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Polygon.colors.scrim.copy(alpha = 0.08f)),
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun PhotoBackButton(
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
        label = "photo-back-content-alpha",
    ) { state ->
        if (state == EnterExitState.Visible) 1f else 0f
    }?.value ?: 1f
    val buttonContentModifier =
        if (animatedVisibilityScope != null) {
            with(animatedVisibilityScope) {
                Modifier
                    .graphicsLayer { alpha = buttonContentAlpha }
                    .animateEnterExit(
                    enter = slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(
                            durationMillis = 180,
                            delayMillis = 120,
                            easing = LinearOutSlowInEasing,
                        ),
                    ),
                    exit = slideOutHorizontally(
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

    Box(
        modifier = modifier
            .size(40.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
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
private fun PhotoDetailImage(
    imageUrl: String?,
    contentDescription: String,
    imageLoader: ImageLoader?,
    crossfade: Boolean,
    contentScale: ContentScale,
    memoryCacheKey: String? = null,
    placeholderMemoryCacheKey: String? = null,
    onSuccess: (SuccessResult) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    if (imageUrl.isNullOrBlank()) {
        return
    }

    val context = LocalContext.current
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .memoryCacheKey(memoryCacheKey)
        .placeholderMemoryCacheKey(placeholderMemoryCacheKey)
        .apply { crossfade(crossfade) }
        .build()

    if (imageLoader == null) {
        AsyncImage(
            model = request,
            contentDescription = contentDescription,
            contentScale = contentScale,
            onSuccess = { state -> onSuccess(state.result) },
            modifier = modifier,
        )
    } else {
        AsyncImage(
            model = request,
            contentDescription = contentDescription,
            imageLoader = imageLoader,
            contentScale = contentScale,
            onSuccess = { state -> onSuccess(state.result) },
            modifier = modifier,
        )
    }
}

@Composable
private fun PhotoLocationRow(
    location: Location?,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val title = location?.title?.takeIf { it.isNotBlank() }
        ?: location?.name?.takeIf { it.isNotBlank() }
        ?: "unknown location"
    val hasCoordinates = location != null && (location.lat != 0.0 || location.lon != 0.0)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (hasCoordinates) {
                    Modifier.clickable {
                        val uri = Uri.parse("geo:${location.lat},${location.lon}?z=14")
                        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                    }
                } else {
                    Modifier
                },
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_place_black_24dp),
            contentDescription = "Location",
            colorFilter = ColorFilter.tint(Polygon.colors.success),
            modifier = Modifier.size(16.dp),
        )

        BasicText(
            text = title,
            modifier = Modifier.padding(start = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = Polygon.typography.locationTitle,
        )
    }
}

@Composable
private fun PhotoDescriptionText(
    description: String?,
    titleMeshColors: List<Color>,
    modifier: Modifier = Modifier,
) {
    val text = description?.takeIf { it.isNotBlank() } ?: "No description"

    MeshGradientText(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .background(PolygonPalette.Grey2)
            .padding(16.dp),
        style = Polygon.typography.photoDescription,
        colors = titleMeshColors,
        animate = true,
    )
}

@Composable
private fun PhotoUserBlock(
    user: User?,
    preview: PhotoDescriptionPreview,
    imageLoader: ImageLoader?,
    imageCrossfade: Boolean,
    modifier: Modifier = Modifier,
) {
    val userName = user?.name ?: preview.userName
    val username = user?.username ?: preview.username
    val avatarUrl = user?.profileImage?.large
        ?: user?.profileImage?.meduim
        ?: user?.profileImage?.small
        ?: preview.userAvatarUrl

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(Polygon.dimensions.userPictureSmall)
                .clip(CircleShape)
                .background(PolygonPalette.Grey3),
        ) {
            PhotoDetailImage(
                imageUrl = avatarUrl,
                contentDescription = userName ?: "Photographer",
                imageLoader = imageLoader,
                crossfade = imageCrossfade,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
            )
        }

        BasicText(
            text = userName?.lowercase(Locale.getDefault()).orEmpty(),
            modifier = Modifier.padding(top = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = Polygon.typography.artistName,
        )

        BasicText(
            text = username?.let { "@$it" }.orEmpty(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = Polygon.typography.artistUsername,
        )
    }
}

@Composable
private fun PhotoStatsStrip(
    photo: Photo?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PhotoStatValue(
            label = "likes",
            value = photo?.likes?.formatCount() ?: "--",
            modifier = Modifier.weight(1f),
        )

        PhotoStatValue(
            label = "views",
            value = photo?.views?.formatCount() ?: "--",
            modifier = Modifier.weight(1f),
        )

        PhotoStatValue(
            label = "downloads",
            value = photo?.downloads?.formatCount() ?: "--",
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun PhotoStatValue(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(PolygonPalette.Grey2)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BasicText(
            text = value,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = Polygon.typography.photoStatsValue.copy(
                color = Polygon.colors.primaryText,
                textAlign = TextAlign.Center,
            ),
        )

        BasicText(
            text = label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = Polygon.typography.artistUsername.copy(
                textAlign = TextAlign.Center,
            ),
        )
    }
}

@Composable
private fun PhotoInfoGrid(
    photo: Photo?,
    modifier: Modifier = Modifier,
) {
    val exif = photo?.exif
    val infoItems = listOf(
        PhotoInfoItem(R.drawable.ic_camera_alt_black_24dp, exif?.make.blankAsDash()),
        PhotoInfoItem(R.drawable.ic_camera_roll_black_24dp, exif?.model.blankAsDash()),
        PhotoInfoItem(R.drawable.ic_av_timer_black_24dp, exif?.exposureTime?.let { "${it}s" }.blankAsDash()),
        PhotoInfoItem(R.drawable.ic_camera_black_24dp, exif?.aperture?.let { "f/$it" }.blankAsDash()),
        PhotoInfoItem(R.drawable.ic_center_focus_strong_black_24dp, exif?.focalLength?.let { "${it}mm" }.blankAsDash()),
        PhotoInfoItem(R.drawable.ic_iso_black_24dp, exif.isoText()),
        PhotoInfoItem(R.drawable.ic_image_aspect_ratio_black_24dp, photo.resolutionText()),
        PhotoInfoItem(R.drawable.ic_timeline_black_24dp, "stats"),
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    ) {
        infoItems.chunked(2).forEach { rowItems ->
            Row(modifier = Modifier.fillMaxWidth()) {
                rowItems.forEach { item ->
                    PhotoInfoCell(
                        item = item,
                        modifier = Modifier.weight(1f),
                    )
                }

                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun PhotoInfoCell(
    item: PhotoInfoItem,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .defaultMinSize(minHeight = 48.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(item.iconRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Polygon.colors.activeIcon),
            modifier = Modifier.size(24.dp),
        )

        Spacer(modifier = Modifier.width(16.dp))

        BasicText(
            text = item.value,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = Polygon.typography.photoStatsValue,
        )
    }
}

@Composable
private fun InlineDetailMessage(
    text: String,
    modifier: Modifier = Modifier,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
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
                    .padding(top = 8.dp)
                    .defaultMinSize(minWidth = 96.dp, minHeight = 48.dp)
                    .clickable { onActionClick() }
                    .padding(PaddingValues(horizontal = 16.dp, vertical = 14.dp)),
                style = Polygon.typography.button.copy(
                    color = Polygon.colors.primaryText,
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}

private data class PhotoInfoItem(
    @DrawableRes val iconRes: Int,
    val value: String,
)

@Composable
private fun MeshGradientText(
    text: String,
    style: TextStyle,
    colors: List<Color>,
    animate: Boolean,
    modifier: Modifier = Modifier,
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    val infiniteTransition = rememberInfiniteTransition(label = "title-mesh-gradient")
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 4200,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "title-mesh-anchor-motion",
    )
    val titleTextColor = Polygon.colors.primaryText

    BoxWithConstraints(modifier = modifier) {
        val maxWidthPx = with(density) { maxWidth.roundToPx() }.coerceAtLeast(1)
        val measuredStyle = style.copy(color = titleTextColor)
        val textLayout = remember(text, style, maxWidthPx) {
            textMeasurer.measure(
                text = AnnotatedString(text),
                style = measuredStyle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                constraints = Constraints(maxWidth = maxWidthPx),
            )
        }
        val anchorPositions = remember(animationProgress, animate) {
            meshAnchorPositions(if (animate) animationProgress else 0f)
        }
        val meshColors = colors.ifEmpty { listOf(PolygonPalette.Grey3) }
        val gradientPainter = remember(meshColors, anchorPositions) {
            MeshGradientPainter(rows = 2, columns = 2) {
                anchorPositions.forEachIndexed { index, position ->
                    val row = index / 3
                    val column = index % 3
                    setVertex(
                        row,
                        column,
                        position,
                        meshColors[index % meshColors.size],
                    )
                }
            }
        }
        Canvas(
            modifier = Modifier
                .size(
                    with(density) { textLayout.size.width.toDp() },
                    with(density) { textLayout.size.height.toDp() },
                )
                .semantics { contentDescription = text },
        ) {
            drawContext.canvas.saveLayer(
                Rect(0f, 0f, size.width, size.height),
                Paint(),
            )
            drawText(
                textLayoutResult = textLayout,
                color = Color.White,
            )
            drawContext.canvas.saveLayer(
                Rect(0f, 0f, size.width, size.height),
                Paint().apply { blendMode = BlendMode.SrcIn },
            )
            with(gradientPainter) {
                draw(size)
            }
            drawContext.canvas.restore()
            drawContext.canvas.restore()
        }
    }
}

@Preview(
    name = "Photo title mesh gradient",
    showBackground = true,
    backgroundColor = 0xFF101010,
    widthDp = 393,
    heightDp = 180,
)
@Composable
internal fun PhotoDescriptionTitlePreview() {
    PolygonTheme {
        PhotoDescriptionTitlePreviewContent()
    }
}

@Composable
internal fun PhotoDescriptionTitlePreviewContent(
    modifier: Modifier = Modifier,
    animate: Boolean = true,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(PolygonPalette.Grey2)
            .padding(16.dp),
    ) {
        MeshGradientText(
            text = "A title rendered with image colors",
            modifier = Modifier.fillMaxWidth(),
            style = Polygon.typography.photoDescription,
            colors = listOf(
                Color(0xFFE85D75),
                Color(0xFF6C63FF),
                Color(0xFF12B886),
                Color(0xFFFFC857),
            ),
            animate = animate,
        )
    }
}

private fun Palette.toMeshColorSet(fallback: Color): List<Color> {
    val semanticColors = listOf(
        vibrantSwatch,
        darkVibrantSwatch,
        lightVibrantSwatch,
        mutedSwatch,
        darkMutedSwatch,
        lightMutedSwatch,
    ).mapNotNull { swatch -> swatch?.let { Color(it.rgb) } }
    val populationColors = swatches
        .sortedByDescending { it.population }
        .map { Color(it.rgb) }
    return (semanticColors + populationColors + fallback)
        .distinct()
        .expandToMeshColors(fallback)
}

private fun Color.toMeshColorSet(): List<Color> {
    return listOf(this).expandToMeshColors(this)
}

private fun List<Color>.expandToMeshColors(fallback: Color): List<Color> {
    val baseColors = (this + fallback).distinct()
    val derivedColors = listOf(0.18f, 0.34f, 0.5f, 0.66f, 0.82f)
        .flatMapIndexed { index, fraction ->
            baseColors.map { color ->
                if (index % 2 == 0) {
                    color.mixWith(Color.White, fraction)
                } else {
                    color.mixWith(Color.Black, fraction)
                }
            }
        }

    return (baseColors + derivedColors)
        .distinct()
        .take(9)
}

private fun Color.mixWith(other: Color, fraction: Float): Color {
    return Color(
        red = red + (other.red - red) * fraction,
        green = green + (other.green - green) * fraction,
        blue = blue + (other.blue - blue) * fraction,
        alpha = alpha + (other.alpha - alpha) * fraction,
    )
}

private fun meshAnchorPositions(progress: Float): List<Offset> {
    val basePositions = listOf(
        Offset(0f, 0f),
        Offset(0.5f, 0f),
        Offset(1f, 0f),
        Offset(0f, 0.5f),
        Offset(0.5f, 0.5f),
        Offset(1f, 0.5f),
        Offset(0f, 1f),
        Offset(0.5f, 1f),
        Offset(1f, 1f),
    )
    val anchorAmplitudes = listOf(
        Offset(0f, 0f),
        Offset(0.18f, 0f),
        Offset(0f, 0f),
        Offset(0f, 0.18f),
        Offset(0.24f, 0.24f),
        Offset(0f, 0.18f),
        Offset(0f, 0f),
        Offset(0.18f, 0f),
        Offset(0f, 0f),
    )
    val phase = progress * (2f * Math.PI.toFloat())
    return basePositions.mapIndexed { index, position ->
        val amplitude = anchorAmplitudes[index]
        val anchorPhase = phase + (index * 0.7f)
        Offset(
            x = (position.x + amplitude.x * kotlin.math.sin(anchorPhase)).coerceIn(0f, 1f),
            y = (position.y + amplitude.y * kotlin.math.cos(anchorPhase)).coerceIn(0f, 1f),
        )
    }
}

private fun String?.toComposeColor(fallback: Color): Color {
    return takeUnless { it.isNullOrBlank() }
        ?.let { value -> runCatching { Color(android.graphics.Color.parseColor(value)) }.getOrNull() }
        ?: fallback
}

private fun Photo.safeAspectRatio(): Float {
    return if (width > 0 && height > 0) {
        width.toFloat() / height.toFloat()
    } else {
        1f
    }
}

private fun PhotoDescriptionPreview.safeAspectRatio(): Float {
    return if (width > 0 && height > 0) {
        width.toFloat() / height.toFloat()
    } else {
        1f
    }
}

private fun Photo.accessibilityLabel(): String {
    val photographer = user?.name
    return when {
        !description.isNullOrBlank() && !photographer.isNullOrBlank() -> {
            "$description by $photographer"
        }

        !description.isNullOrBlank() -> description
        !photographer.isNullOrBlank() -> "Photo by $photographer"
        else -> "Unsplash photo"
    }
}

private fun PhotoDescriptionPreview.accessibilityLabel(): String {
    return when {
        !description.isNullOrBlank() && !userName.isNullOrBlank() -> "$description by $userName"
        !description.isNullOrBlank() -> description
        !userName.isNullOrBlank() -> "Photo by $userName"
        else -> "Unsplash photo"
    }
}

private fun String?.blankAsDash(): String {
    return takeIf { !it.isNullOrBlank() } ?: "--"
}

private fun Exif?.isoText(): String {
    val iso = this?.iso ?: 0
    return if (iso > 0) {
        iso.toString()
    } else {
        "--"
    }
}

private fun Photo?.resolutionText(): String {
    return if (this != null && width > 0 && height > 0) {
        "${width}x${height}"
    } else {
        "--"
    }
}

private fun Int.formatCount(): String {
    return NumberFormat.getNumberInstance().format(this)
}
