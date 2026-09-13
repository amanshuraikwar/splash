package com.sonu.app.splash.data.rss.github

import android.util.Log
import com.sonu.app.splash.data.rss.RssDataSource
import com.sonu.app.splash.data.rss.model.RssItem
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class GitHubRssDataSource @Inject constructor(
    private val api: RssApi,
    private val indexParser: RssIndexParser,
    private val xmlParser: RssXmlParser,
) : RssDataSource {
    override suspend fun getItems(): List<RssItem> = withContext(Dispatchers.IO) {
        val indexHtml = api.getIndex().use { it.string() }
        val feedUrls = indexParser.findFeedUrls(INDEX_URL, indexHtml)

        coroutineScope {
            feedUrls
                .map { feedUrl ->
                    async {
                        runCatching { fetchFeed(feedUrl) }
                            .onFailure { error ->
                                if (error is CancellationException) {
                                    throw error
                                }
                                Log.w(TAG, "Unable to load RSS feed: $feedUrl", error)
                            }
                            .getOrDefault(emptyList())
                    }
                }
                .awaitAll()
                .flatten()
        }
    }

    private suspend fun fetchFeed(feedUrl: String): List<RssItem> {
        val xml = api.getFeed(feedUrl).use { it.string() }
        return xmlParser.parse(feedUrl, xml).items
    }

    private companion object {
        const val TAG = "GitHubRssDataSource"
        const val INDEX_URL = "https://amanshuraikwar.github.io/rss/"
    }
}
