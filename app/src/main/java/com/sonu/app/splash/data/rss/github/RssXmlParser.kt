package com.sonu.app.splash.data.rss.github

import android.text.Html
import android.util.Xml
import com.sonu.app.splash.data.rss.model.RssItem
import java.net.URI
import java.util.Locale
import javax.inject.Inject
import org.xmlpull.v1.XmlPullParser

data class ParsedRssFeed(
    val title: String,
    val items: List<RssItem>,
)

class RssXmlParser @Inject constructor() {
    fun parse(feedUrl: String, xml: String): ParsedRssFeed {
        val parser = Xml.newPullParser()
        parser.setInput(xml.reader())

        var feedTitle = ""
        var itemBuilder: ItemBuilder? = null
        var itemDepth = -1
        var activeText: ActiveText? = null
        val parsedItems = mutableListOf<RssItem>()

        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            when (parser.eventType) {
                XmlPullParser.START_TAG -> {
                    val tag = parser.localTagName()
                    if (itemBuilder == null && tag in ITEM_TAGS) {
                        itemBuilder = ItemBuilder(feedUrl)
                        itemDepth = parser.depth
                    } else if (itemBuilder == null && tag == "title") {
                        activeText = ActiveText(
                            kind = TextKind.FEED_TITLE,
                            tag = tag,
                            depth = parser.depth,
                        )
                    } else if (itemBuilder != null) {
                        val builder = itemBuilder
                        when (tag) {
                            "title" -> activeText = builder.text(TextKind.TITLE, parser)
                            "guid", "id" -> activeText = builder.text(TextKind.ID, parser)
                            "link" -> {
                                val href = parser.attribute("href")
                                val relation = parser.attribute("rel")
                                if (!href.isNullOrBlank() &&
                                    relation?.equals("self", ignoreCase = true) != true
                                ) {
                                    builder.linkCandidates += href
                                } else {
                                    if (href.isNullOrBlank()) {
                                        activeText = builder.text(TextKind.LINK, parser)
                                    }
                                }
                            }

                            "pubdate", "date", "updated", "published", "modified", "issued" -> {
                                activeText = builder.text(TextKind.DATE, parser)
                            }

                            "description", "summary", "content", "encoded" -> {
                                activeText = builder.text(TextKind.DESCRIPTION, parser)
                                builder.addImageAttributes(parser)
                            }

                            "thumbnail", "enclosure", "image" -> {
                                builder.addImageAttributes(parser)
                            }

                            "url" -> activeText = builder.text(TextKind.IMAGE, parser)
                        }
                    }
                }

                XmlPullParser.TEXT, XmlPullParser.CDSECT -> {
                    activeText?.value?.append(parser.text)
                }

                XmlPullParser.END_TAG -> {
                    val tag = parser.localTagName()
                    val currentText = activeText
                    if (currentText != null && currentText.depth == parser.depth && currentText.tag == tag) {
                        val value = currentText.value.toString()
                        if (currentText.kind == TextKind.FEED_TITLE) {
                            if (feedTitle.isBlank()) {
                                feedTitle = cleanText(value)
                            }
                        } else {
                            itemBuilder?.accept(currentText.kind, value)
                        }
                        activeText = null
                    }

                    if (itemBuilder != null && parser.depth == itemDepth && tag in ITEM_TAGS) {
                        itemBuilder.build(feedTitle, parsedItems.size)?.let(parsedItems::add)
                        itemBuilder = null
                        itemDepth = -1
                        activeText = null
                    }
                }
            }
        }

        return ParsedRssFeed(
            title = feedTitle.ifBlank { feedUrl },
            items = parsedItems,
        )
    }

    private class ActiveText(
        val kind: TextKind,
        val tag: String,
        val depth: Int,
        val value: StringBuilder = StringBuilder(),
    )

    private class ItemBuilder(
        private val feedUrl: String,
    ) {
        var title: String? = null
        var id: String? = null
        var description: String? = null
        var publishedAtEpochMillis: Long? = null
        var imageWidth: Int? = null
        var imageHeight: Int? = null
        val linkCandidates = mutableListOf<String>()
        val imageCandidates = mutableListOf<String>()

        fun text(kind: TextKind, parser: XmlPullParser): ActiveText {
            return ActiveText(kind = kind, tag = parser.localTagName(), depth = parser.depth)
        }

        fun addImageAttributes(parser: XmlPullParser) {
            parser.attribute("url")?.let { imageCandidates += it }
            parser.attribute("href")?.let { imageCandidates += it }
            parser.attribute("src")?.let { imageCandidates += it }

            if (imageWidth == null) {
                imageWidth = parser.attribute("width")?.toPositiveInt()
            }
            if (imageHeight == null) {
                imageHeight = parser.attribute("height")?.toPositiveInt()
            }
        }

        fun accept(kind: TextKind, value: String) {
            val cleanValue = value.trim()
            if (cleanValue.isBlank()) {
                return
            }

            when (kind) {
                TextKind.TITLE -> if (title.isNullOrBlank()) title = cleanText(cleanValue)
                TextKind.ID -> if (id.isNullOrBlank()) id = cleanValue
                TextKind.LINK -> linkCandidates += cleanValue
                TextKind.DATE -> {
                    if (publishedAtEpochMillis == null) {
                        publishedAtEpochMillis = RssDateParser.parseEpochMillis(cleanValue)
                    }
                }

                TextKind.DESCRIPTION -> if (description.isNullOrBlank()) description = cleanValue
                TextKind.IMAGE -> imageCandidates += cleanValue
                TextKind.FEED_TITLE -> Unit
            }
        }

        fun build(feedTitle: String, index: Int): RssItem? {
            val cleanTitle = title?.takeIf { it.isNotBlank() } ?: return null
            val link = linkCandidates
                .asSequence()
                .mapNotNull { resolveUrl(feedUrl, it) }
                .firstOrNull()
            val imageUrl = (imageCandidates + extractImageUrls(description.orEmpty()))
                .asSequence()
                .mapNotNull { resolveUrl(feedUrl, it) }
                .firstOrNull()
            val stableId = id?.takeIf { it.isNotBlank() }
                ?: link
                ?: "$feedUrl#$index:${cleanTitle.lowercase(Locale.US)}"

            return RssItem(
                id = stableId,
                feedTitle = feedTitle.ifBlank { feedUrl },
                feedUrl = feedUrl,
                title = cleanTitle,
                link = link,
                description = description?.let(::cleanText)?.takeIf { it.isNotBlank() },
                imageUrl = imageUrl,
                publishedAtEpochMillis = publishedAtEpochMillis,
                imageWidth = imageWidth ?: 0,
                imageHeight = imageHeight ?: 0,
            )
        }
    }

    private enum class TextKind {
        FEED_TITLE,
        TITLE,
        ID,
        LINK,
        DATE,
        DESCRIPTION,
        IMAGE,
    }

    private companion object {
        val ITEM_TAGS = setOf("item", "entry")
        val imagePattern = Regex(
            """(?is)<img[^>]+(?:src|data-src)\\s*=\\s*[\"']([^\"']+)[\"']""",
        )

        fun XmlPullParser.localTagName(): String {
            return name.orEmpty().substringAfter(':').lowercase(Locale.US)
        }

        fun XmlPullParser.attribute(attributeName: String): String? {
            for (index in 0 until attributeCount) {
                if (getAttributeName(index).substringAfter(':').equals(attributeName, ignoreCase = true)) {
                    return getAttributeValue(index)
                }
            }
            return null
        }

        fun resolveUrl(baseUrl: String, value: String): String? {
            return runCatching {
                val normalized = if (value.startsWith("//")) "https:$value" else value
                URI(baseUrl).resolve(normalized).toString()
                    .takeIf { it.startsWith("http://") || it.startsWith("https://") }
            }.getOrNull()
        }

        fun extractImageUrls(html: String): List<String> {
            return imagePattern.findAll(html).map { it.groupValues[1] }.toList()
        }

        fun String.toPositiveInt(): Int? {
            return toIntOrNull()?.takeIf { it > 0 }
        }

        fun cleanText(value: String): String {
            return Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
                .toString()
                .replace(Regex("\\s+"), " ")
                .trim()
        }
    }
}
