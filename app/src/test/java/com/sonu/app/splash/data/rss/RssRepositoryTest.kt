package com.sonu.app.splash.data.rss

import com.sonu.app.splash.data.rss.model.RssItem
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class RssRepositoryTest {
    @Test
    fun mergesSourcesDeduplicatesAndSortsDatedItemsFirst() = runBlocking {
        val repository = RssRepository(
            setOf(
                object : RssDataSource {
                    override suspend fun getItems(): List<RssItem> = listOf(
                        item("old", 100L),
                        item("without-date", null),
                    )
                },
                object : RssDataSource {
                    override suspend fun getItems(): List<RssItem> = listOf(
                        item("new", 200L),
                        item("old", 300L),
                    )
                },
            ),
        )

        assertEquals(
            listOf("new", "old", "without-date"),
            repository.getItems().map(RssItem::id),
        )
    }

    private fun item(id: String, publishedAt: Long?): RssItem {
        return RssItem(
            id = id,
            feedTitle = "Feed",
            feedUrl = "https://example.com/feed.xml",
            title = id,
            link = null,
            description = null,
            imageUrl = null,
            publishedAtEpochMillis = publishedAt,
        )
    }
}
