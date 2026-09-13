package com.sonu.app.splash.data.rss.github

import org.junit.Assert.assertEquals
import org.junit.Test

class RssIndexParserTest {
    @Test
    fun findsAndResolvesXmlLinks() {
        val html = """
            <a href="first.xml">First</a>
            <a href="/rss/second.xml">Second</a>
            <a href="first.xml">Duplicate</a>
            <a href="/feeds/">Reader</a>
        """.trimIndent()

        val urls = RssIndexParser().findFeedUrls(
            indexUrl = "https://example.com/rss/",
            html = html,
        )

        assertEquals(
            listOf(
                "https://example.com/rss/first.xml",
                "https://example.com/rss/second.xml",
            ),
            urls,
        )
    }
}
