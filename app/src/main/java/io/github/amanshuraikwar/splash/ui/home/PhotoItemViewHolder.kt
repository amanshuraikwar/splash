/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.amanshuraikwar.splash.ui.home

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import io.github.amanshuraikwar.splash.R
import io.github.amanshuraikwar.splash.data.jetpack.NetworkState
import io.github.amanshuraikwar.splash.data.jetpack.Status

/**
 * A View Holder that can display a loading or have click action.
 * It is used to show the network state of paging.
 */
class PhotoItemViewHolder(view: View)
    : RecyclerView.ViewHolder(view) {
    private val imageView = view.findViewById<ImageView>(R.id.imageView)

    fun bindTo(photoUrl: String, host: FragmentActivity) {
        Glide.with(host).load(photoUrl).into(imageView)
    }

    companion object {
        fun create(parent: ViewGroup): PhotoItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_photo, parent, false)
            return PhotoItemViewHolder(view)
        }

        fun toVisbility(constraint : Boolean): Int {
            return if (constraint) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}