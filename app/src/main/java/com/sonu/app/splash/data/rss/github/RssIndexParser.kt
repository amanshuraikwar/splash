package com.sonu.app.splash.data.rss.github

import java.net.URI
import javax.inject.Inject

class RssIndexParser @Inject constructor() {
    private val xmlLinkPattern = Regex(
        pattern = """(?is)href\s*=\s*[\"']([^\"']+\.xml(?:\?[^\"']*)?)[\"']""",
    )

    fun findFeedUrls(indexUrl: String, html: String): List<String> {
        return xmlLinkPattern
            .findAll(html)
            .mapNotNull { match ->
                match.groupValues.getOrNull(1)
                    ?.replace("&amp;", "&")
                    ?.let { resolveUrl(indexUrl, it) }
            }
            .distinct()
            .toList()
    }

    private fun resolveUrl(baseUrl: String, value: String): String? {
        return runCatching {
            val resolved = URI(baseUrl).resolve(value)
            resolved.toString().takeIf { it.startsWith("http://") || it.startsWith("https://") }
        }.getOrNull()
    }
}
