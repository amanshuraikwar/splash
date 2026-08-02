package com.sonu.app.splash.ui.photodescription

import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.cardview.widget.CardView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.sonu.app.splash.R
import com.sonu.app.splash.model.unsplash.Exif
import com.sonu.app.splash.model.unsplash.Location
import com.sonu.app.splash.model.unsplash.Photo
import com.sonu.app.splash.model.unsplash.User
import com.sonu.app.splash.ui.list.ListItem
import com.sonu.app.splash.ui.list.ListItemTypeFactory
import com.sonu.app.splash.ui.list.SimpleListItemOnClickListener
import com.sonu.app.splash.ui.list.ViewHolder
import com.sonu.app.splash.util.DrawableUtils

/**
 * Created by amanshuraikwar on 13/04/18.
 */
class PhotoDescriptionUiElements {

    interface LocationListItemOnClickListener {
        fun onLocationClick(location: Location)
    }

    class LocationListItem(val location: Location?) : ListItem<LocationListItemOnClickListener>() {

        override fun type(typeFactory: ListItemTypeFactory?) = LocationListItem::class.java.hashCode()
    }

    class LocationViewHolder(itemView: View) : ViewHolder<LocationListItem>(itemView) {

        companion object {

            @LayoutRes
            val LAYOUT = R.layout.item_location
        }

        private val titleTv: TextView = itemView.findViewById(R.id.titleTv)

        override fun bind(listItem: LocationListItem?, parentActivity: FragmentActivity?) {

            listItem?.let {
             titleTv.text = it.location?.title
            }
        }
    }

    class PhotoDescriptionTextListItem(val description: String)
        : ListItem<SimpleListItemOnClickListener>() {

        override fun type(typeFactory: ListItemTypeFactory?)
                = PhotoDescriptionTextListItem::class.java.hashCode()
    }

    class PhotoDescriptionTextViewHolder(itemView: View)
        : ViewHolder<PhotoDescriptionTextListItem>(itemView) {

        companion object {

            @LayoutRes
            val LAYOUT = R.layout.item_photo_description_text
        }

        private val descriptionTv: TextView = itemView.findViewById(R.id.descriptionTv)

        override fun bind(listItem: PhotoDescriptionTextListItem?,
                          parentActivity: FragmentActivity?) {

            listItem?.let {
                descriptionTv.text = listItem.description
            }
        }
    }

    interface PhotoUserListItemOnClickListener {
        fun onUserPhotoClick(user: User, animationView: View)
    }

    class PhotoUserListItem(val user: User) : ListItem<PhotoUserListItemOnClickListener>() {

        override fun type(typeFactory: ListItemTypeFactory?) = PhotoUserListItem::class.java.hashCode()
    }

    class PhotoUserViewHolder(itemView: View) : ViewHolder<PhotoUserListItem>(itemView) {

        companion object {

            @LayoutRes
            val LAYOUT = R.layout.item_photo_user
        }

        private val userPhotoIv: ImageView = itemView.findViewById(R.id.userPhotoIv)

        private val userNameTv: TextView = itemView.findViewById(R.id.userNameTv)

        private val userUsernameTv: TextView = itemView.findViewById(R.id.userUsernameTv)

        private val userPhotoCv: CardView = itemView.findViewById(R.id.userPhotoCv)

        override fun bind(listItem: PhotoUserListItem?, parentActivity: FragmentActivity?) {

            listItem?.let{

                userNameTv.text = it.user.name.lowercase()

                userUsernameTv.text = String.format("@%s", it.user.username)

                Glide.with(parentActivity ?: return)
                        .load(it.user.profileImage.large)
                        .apply(RequestOptions().centerCrop().circleCrop())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(userPhotoIv)

                userPhotoCv.setOnClickListener {
                    _ ->
                    it.onClickListener.onUserPhotoClick(it.user, userPhotoCv)
                }
            }
        }
    }

    interface PhotoInfoListItemOnClickListener {
        fun onStatsBtnClick(photoId: String)
    }

    class PhotoInfoListItem(val photoId: String, val photoExif: Exif)
        : ListItem<PhotoInfoListItemOnClickListener>() {

        override fun type(typeFactory: ListItemTypeFactory?) = PhotoInfoListItem::class.java.hashCode()
    }

    class PhotoInfoViewHolder(itemView: View) : ViewHolder<PhotoInfoListItem>(itemView) {

        companion object {

            @LayoutRes
            val LAYOUT = R.layout.item_photo_info
        }

        private val exifExposureTimeBtn: Button = itemView.findViewById(R.id.exifExposureTimeBtn)

        private val exifFocalLengthBtn: Button = itemView.findViewById(R.id.exifFocalLengthBtn)

        private val exifIsoBtn: Button = itemView.findViewById(R.id.exifIsoBtn)

        private val exifMakeBtn: Button = itemView.findViewById(R.id.exifMakeBtn)

        private val exifModelBtn: Button = itemView.findViewById(R.id.exifModelBtn)

        private val exifApertureBtn: Button = itemView.findViewById(R.id.exifApertureBtn)

        private val photoResolutionBtn: Button = itemView.findViewById(R.id.photoResolutionBtn)

        private val photoStatsBtn: Button = itemView.findViewById(R.id.photoStatsBtn)

        override fun bind(listItem: PhotoInfoListItem?, parentActivity: FragmentActivity?) {

            listItem?.let{

                exifMakeBtn.text =
                        if (it.photoExif.make != null) it.photoExif.make
                        else "--"

                exifModelBtn.text =
                        if (it.photoExif.model != null) it.photoExif.model
                        else "--"

                exifExposureTimeBtn.text =
                        if (it.photoExif.exposureTime != null)
                            String.format("%ss", it.photoExif.exposureTime)
                        else "--"

                exifApertureBtn.text =
                        if (it.photoExif.aperture != null)
                            String.format("f/%s", it.photoExif.aperture)
                        else "--"

                exifIsoBtn.text  =
                        if (it.photoExif.iso != 0)
                            String.format("%d", it.photoExif.iso)
                        else "--"

                exifFocalLengthBtn.text =
                        if (it.photoExif.focalLength != null)
                            String.format("%smm", it .photoExif.focalLength)
                        else "--"

                photoStatsBtn.setOnClickListener{
                    _ ->
                    it.onClickListener.onStatsBtnClick(it.photoId)
                }
            }
        }
    }

    class FourThreeEmptyListItem(@ColorInt val color: Int) : ListItem<SimpleListItemOnClickListener>() {

        override fun type(typeFactory: ListItemTypeFactory?)
                = FourThreeEmptyListItem::class.java.hashCode()
    }

    class FourThreeViewHolder(itemView: View) : ViewHolder<FourThreeEmptyListItem>(itemView) {

        companion object {

            @LayoutRes
            val LAYOUT = R.layout.item_four_three_empty
        }

        private val view: View = itemView.findViewById(R.id.view)

        override fun bind(listItem: FourThreeEmptyListItem?, parentActivity: FragmentActivity?) {

            listItem?.let {
                view.background = DrawableUtils.createRippleDrawable(listItem.color)
            }
        }
    }
}
