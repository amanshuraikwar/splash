package com.sonu.app.splash.data.rss.github

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [35])
class RssXmlParserTest {
    @Test
    fun parsesRssMetadataMediaAndDate() {
        val feed = RssXmlParser().parse(
            feedUrl = "https://example.com/rss/feed.xml",
            xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <rss xmlns:media="http://search.yahoo.com/mrss/" version="2.0">
                  <channel>
                    <title>Example Feed</title>
                    <item>
                      <title>Example &amp; item</title>
                      <link>/items/example</link>
                      <guid>item-1</guid>
                      <pubDate>Tue, 25 Aug 2026 10:15:00 GMT</pubDate>
                      <description>A useful description.</description>
                      <media:content url="https://example.com/image.jpg" medium="image" />
                    </item>
                  </channel>
                </rss>
            """.trimIndent(),
        )

        assertEquals("Example Feed", feed.title)
        assertEquals(1, feed.items.size)
        assertEquals("Example & item", feed.items.single().title)
        assertEquals("item-1", feed.items.single().id)
        assertEquals("https://example.com/items/example", feed.items.single().link)
        assertEquals("https://example.com/image.jpg", feed.items.single().imageUrl)
        assertNotNull(feed.items.single().publishedAtEpochMillis)
    }

    @Test
    fun parsesAtomAndUsesTitleWhenMediaIsUnavailable() {
        val feed = RssXmlParser().parse(
            feedUrl = "https://example.com/atom/feed.xml",
            xml = """
                <feed xmlns="http://www.w3.org/2005/Atom">
                  <title>Atom Feed</title>
                  <entry>
                    <title>Text only entry</title>
                    <id>entry-1</id>
                    <link href="https://example.com/entry" />
                    <updated>2026-08-25T10:15:00Z</updated>
                    <summary>Summary text.</summary>
                  </entry>
                </feed>
            """.trimIndent(),
        )

        val item = feed.items.single()
        assertEquals("Atom Feed", feed.title)
        assertEquals("Text only entry", item.title)
        assertEquals("https://example.com/entry", item.link)
        assertNull(item.imageUrl)
        assertTrue(item.publishedAtEpochMillis != null)
    }
}
