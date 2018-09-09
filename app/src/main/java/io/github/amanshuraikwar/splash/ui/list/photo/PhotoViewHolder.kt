package io.github.amanshuraikwar.splash.ui.list.photo

import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.view.View
import com.bumptech.glide.Glide
import io.github.amanshuraikwar.splash.R
import io.github.amanshuraikwar.splash.ui.list.ViewHolder
import io.github.amanshuraikwar.splash.util.Util
import kotlinx.android.synthetic.main.item_photo.view.*

/**
 * ViewHolder corresponding to CurrenctListItem
 *
 * @author Amanshu Raikwar
 * Created by amanshuraikwar on 11/03/18.
 */
class PhotoViewHolder(itemView: View) : ViewHolder<PhotoListItem>(itemView) {

    private val TAG = Util.getTag(this)

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.item_photo
    }

    override fun bind(listItem: PhotoListItem, host: FragmentActivity) {
        Glide.with(host).load(listItem.photo.photoUrls.small).into(itemView.imageView)
    }
}