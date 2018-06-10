package com.sonu.app.splash.ui.list

import android.view.View
import com.sonu.app.splash.ui.collection.CollectionHorizontalListItem
import com.sonu.app.splash.ui.collection.CollectionHorizontalViewHolder
import com.sonu.app.splash.ui.collection.CollectionListItem
import com.sonu.app.splash.ui.collection.CollectionViewHolder
import com.sonu.app.splash.ui.download.DownloadListItem
import com.sonu.app.splash.ui.download.DownloadViewHolder
import com.sonu.app.splash.ui.header.HeaderHorizontalListItem
import com.sonu.app.splash.ui.header.HeaderHorizontalViewHolder
import com.sonu.app.splash.ui.header.HeaderListItem
import com.sonu.app.splash.ui.header.HeaderViewHolder
import com.sonu.app.splash.ui.loading.LoadingListItem
import com.sonu.app.splash.ui.loading.LoadingViewHolder
import com.sonu.app.splash.ui.photo.PhotoHorizontalListItem
import com.sonu.app.splash.ui.photo.PhotoHorizontalViewHolder
import com.sonu.app.splash.ui.photo.PhotoListItem
import com.sonu.app.splash.ui.photo.PhotoViewHolder
import com.sonu.app.splash.ui.photodescription.PhotoDescriptionUiElements.*
import com.sonu.app.splash.ui.user.UserHorizontalListItem
import com.sonu.app.splash.ui.user.UserHorizontalViewHolder
import com.sonu.app.splash.ui.user.UserListItem
import com.sonu.app.splash.ui.user.UserViewHolder

/**
 * Created by amanshuraikwar on 13/04/18.
 */
class ListItemTypeFactory {

    fun getLayout(type: Int) = when(type) {

        PhotoListItem::class.java.hashCode() -> PhotoViewHolder.LAYOUT

        LoadingListItem::class.java.hashCode() -> LoadingViewHolder.LAYOUT

        HeaderHorizontalListItem::class.java.hashCode() -> HeaderHorizontalViewHolder.LAYOUT

        CollectionListItem::class.java.hashCode() -> CollectionViewHolder.LAYOUT

        PhotoHorizontalListItem::class.java.hashCode() -> PhotoHorizontalViewHolder.LAYOUT

        CollectionHorizontalListItem::class.java.hashCode() -> CollectionHorizontalViewHolder.LAYOUT

        UserHorizontalListItem::class.java.hashCode() -> UserHorizontalViewHolder.LAYOUT

        UserListItem::class.java.hashCode() -> UserViewHolder.LAYOUT

        DownloadListItem::class.java.hashCode() -> DownloadViewHolder.LAYOUT

        HeaderListItem::class.java.hashCode() -> HeaderViewHolder.LAYOUT

        LocationListItem::class.java.hashCode() -> LocationViewHolder.LAYOUT

        PhotoDescriptionTextListItem::class.java.hashCode() -> PhotoDescriptionTextViewHolder.LAYOUT

        PhotoUserListItem::class.java.hashCode() -> PhotoUserViewHolder.LAYOUT

        PhotoInfoListItem::class.java.hashCode() -> PhotoInfoViewHolder.LAYOUT

        FourThreeEmptyListItem::class.java.hashCode() -> FourThreeViewHolder.LAYOUT

        else -> 0
    }

    fun createViewHolder(parent: View, type: Int): ViewHolder<*>? = when(type) {

        PhotoListItem::class.java.hashCode() -> PhotoViewHolder(parent)

        LoadingListItem::class.java.hashCode() -> LoadingViewHolder(parent)

        HeaderHorizontalListItem::class.java.hashCode() -> HeaderHorizontalViewHolder(parent)

        CollectionListItem::class.java.hashCode() -> CollectionViewHolder(parent)

        PhotoHorizontalListItem::class.java.hashCode() -> PhotoHorizontalViewHolder(parent)

        CollectionHorizontalListItem::class.java.hashCode() -> CollectionHorizontalViewHolder(parent)

        UserHorizontalListItem::class.java.hashCode() -> UserHorizontalViewHolder(parent)

        UserListItem::class.java.hashCode() -> UserViewHolder(parent)

        DownloadListItem::class.java.hashCode() -> DownloadViewHolder(parent)

        HeaderListItem::class.java.hashCode() -> HeaderViewHolder(parent)

        LocationListItem::class.java.hashCode() -> LocationViewHolder(parent)

        PhotoDescriptionTextListItem::class.java.hashCode() -> PhotoDescriptionTextViewHolder(parent)

        PhotoUserListItem::class.java.hashCode() -> PhotoUserViewHolder(parent)

        PhotoInfoListItem::class.java.hashCode() -> PhotoInfoViewHolder(parent)

        FourThreeEmptyListItem::class.java.hashCode() -> FourThreeViewHolder(parent)

        else -> null
    }
}