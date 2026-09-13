package com.sonu.app.splash.data.rss

import com.sonu.app.splash.data.rss.model.RssItem

interface RssDataSource {
    suspend fun getItems(): List<RssItem>
}
