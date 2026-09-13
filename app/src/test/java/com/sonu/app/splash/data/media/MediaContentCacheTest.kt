package com.sonu.app.splash.data.media

import com.sonu.app.splash.data.media.model.Creator
import com.sonu.app.splash.data.media.model.Media
import com.sonu.app.splash.data.media.model.MediaStats
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class MediaContentCacheTest {

    @Test
    fun loadMoreAccumulatesPagesAndStopsOnAnEmptyPage() = runBlocking {
        val repository = MediaRepository(
            setOf(
                object : MediaDataSource {
                    override fun supports(request: MediaPageRequest): Boolean = true

                    override suspend fun getPage(request: MediaPageRequest): List<Media> {
                        val page = (request as MediaPageRequest.All).page
                        return when (page) {
                            1 -> listOf(media("first"), media("second"))
                            2 -> listOf(media("third"))
                            else -> emptyList()
                        }
                    }

                    override suspend fun getById(id: String): Media = media(id)

                    override suspend fun getStatistics(id: String): MediaStats =
                        error("Not used by this test")

                    override suspend fun getCreator(username: String): Creator =
                        error("Not used by this test")
                },
            ),
        )
        val cache = MediaContentCache(
            repository,
            MediaPageRequestFactory { page -> MediaPageRequest.All(page = page) },
        )

        cache.loadMore()
        assertEquals(listOf("first", "second"), cache.state.value.items.map(Media::id))
        assertEquals(true, cache.state.value.canLoadMore)

        cache.loadMore()
        assertEquals(
            listOf("first", "second", "third"),
            cache.state.value.items.map(Media::id),
        )

        cache.loadMore()
        assertFalse(cache.state.value.canLoadMore)
        assertEquals(3, cache.state.value.items.size)
    }

    private fun media(id: String) = Media(id = id)
}
