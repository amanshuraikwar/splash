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

package io.github.amanshuraikwar.splash.data.jetpack

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import io.github.amanshuraikwar.splash.data.jetpack.datasource.ContentDataSource
import javax.inject.Inject

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class ContentDataSourceFactory<DataModel>
@Inject constructor(private val dataSource: ContentDataSource<DataModel>)
    : DataSource.Factory<Int, DataModel>() {

    val sourceLiveData = MutableLiveData<ContentDataSource<DataModel>>()

    override fun create(): ContentDataSource<DataModel> {
        sourceLiveData.postValue(dataSource)
        return dataSource
    }
}
