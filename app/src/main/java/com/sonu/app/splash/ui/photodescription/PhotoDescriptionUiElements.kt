package com.sonu.app.splash.ui.photodescription

import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.CardView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
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

        @BindView(R.id.titleTv)
        lateinit var titleTv: TextView

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

        @BindView(R.id.descriptionTv)
        lateinit var descriptionTv: TextView

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

        @BindView(R.id.userPhotoIv)
        lateinit var userPhotoIv: ImageView

        @BindView(R.id.userNameTv)
        lateinit var userNameTv: TextView

        @BindView(R.id.userUsernameTv)
        lateinit var userUsernameTv: TextView

        @BindView(R.id.userPhotoCv)
        lateinit var userPhotoCv: CardView

        override fun bind(listItem: PhotoUserListItem?, parentActivity: FragmentActivity?) {

            listItem?.let{

                userNameTv.text = it.user.name.toLowerCase()

                userUsernameTv.text = String.format("@%s", it.user.username)

                Glide.with(parentActivity)
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

        @BindView(R.id.exifExposureTimeBtn)
        lateinit var exifExposureTimeBtn: Button

        @BindView(R.id.exifFocalLengthBtn)
        lateinit var exifFocalLengthBtn: Button

        @BindView(R.id.exifIsoBtn)
        lateinit var exifIsoBtn: Button

        @BindView(R.id.exifMakeBtn)
        lateinit var exifMakeBtn: Button

        @BindView(R.id.exifModelBtn)
        lateinit var exifModelBtn: Button

        @BindView(R.id.exifApertureBtn)
        lateinit var exifApertureBtn: Button

        @BindView(R.id.photoResolutionBtn)
        lateinit var photoResolutionBtn: Button

        @BindView(R.id.photoStatsBtn)
        lateinit var photoStatsBtn: Button

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

        @BindView(R.id.view)
        lateinit var view: View

        override fun bind(listItem: FourThreeEmptyListItem?, parentActivity: FragmentActivity?) {

            listItem?.let {
                view.background = DrawableUtils.createRippleDrawable(listItem.color)
            }
        }
    }
}