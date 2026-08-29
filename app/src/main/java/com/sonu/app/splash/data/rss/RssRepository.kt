package com.sonu.app.splash.data.rss

import com.sonu.app.splash.data.rss.model.RssItem
import javax.inject.Inject
import kotlin.jvm.JvmSuppressWildcards

class RssRepository @Inject constructor(
    private val dataSources: Set<@JvmSuppressWildcards RssDataSource>,
) {
    suspend fun getItems(): List<RssItem> {
        return dataSources
            .flatMap { it.getItems() }
            .distinctBy(RssItem::id)
            .sortedWith(
                compareByDescending<RssItem> {
                    it.publishedAtEpochMillis ?: Long.MIN_VALUE
                },
            )
    }
}
