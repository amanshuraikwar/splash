package io.github.amanshuraikwar.splash.data.jetpack.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PositionalDataSource
import android.util.Log
import io.github.amanshuraikwar.splash.data.jetpack.NetworkState
import io.github.amanshuraikwar.splash.util.Util
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

/**
 * @author Amanshu Raikwar
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class ContentDataSource<DataModel>
constructor(val retryExecutor: Executor,
            val networkState: MutableLiveData<NetworkState> = MutableLiveData(),
            val initialLoad: MutableLiveData<NetworkState> = MutableLiveData())
    : PositionalDataSource<DataModel>() {

    private val TAG = Util.getTag(this)

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    //region override methods
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<DataModel>) {

        networkState.postValue(NetworkState.LOADING)

        getNetworkCall(
                page = (params.startPosition/params.loadSize) + 1,
                perPage = params.loadSize)
                .enqueue(

                        object : retrofit2.Callback<List<DataModel>> {

                            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {

                                // if failed, on retry
                                // load range again with same params
                                retry = {
                                    loadRange(params, callback)
                                }

                                networkState.postValue(
                                        NetworkState.error(t.message
                                                ?: "unknown err"))
                            }

                            override fun onResponse(call: Call<List<DataModel>>,
                                                    response: Response<List<DataModel>>) {

                                if (response.isSuccessful) {

                                    retry = null

                                    val items = response.body() ?: emptyList()
                                    callback.onResult(items)
                                    networkState.postValue(NetworkState.LOADED)

                                } else {

                                    retry = {
                                        loadRange(params, callback)
                                    }

                                    networkState.postValue(
                                            NetworkState.error("error code: ${response.code()}"))
                                }
                            }
                })
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<DataModel>) {

        val request = getNetworkCall(page = 1, perPage = params.pageSize)

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        try {

            retry = null

            val response = request.execute()
            val items = response.body() ?: emptyList()

            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)

            callback.onResult(items, 0)

        } catch (ioException: IOException) {

            retry = {
                loadInitial(params, callback)
            }

            val error = NetworkState.error(ioException.message
                    ?: "unknown error")

            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }
    //endregion

    open fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    fun <DataModelOut> map(mapper: (DataModel) -> DataModelOut): WrapperContentDataSource<DataModelOut, DataModel> {
        return WrapperContentDataSource(this, createListFunction(mapper))
    }

    private fun <DataModelOut> createListFunction(mapper: (DataModel) -> DataModelOut)
            : (List<DataModel>) -> List<DataModelOut> {
        return fun (listIn: List<DataModel>): List<DataModelOut> {
            val listOut: MutableList<DataModelOut> = mutableListOf()
            listIn.forEach {
                listOut.add(mapper(it))
            }
            return listOut
        }
    }

    abstract fun getNetworkCall(page: Int, perPage: Int): Call<List<DataModel>>
}