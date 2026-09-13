package com.sonu.app.splash.data.rss.github

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object RssDateParser {
    private val dateTimeFormatters = listOf(
        DateTimeFormatter.ISO_INSTANT,
        DateTimeFormatter.ISO_OFFSET_DATE_TIME,
        DateTimeFormatter.RFC_1123_DATE_TIME,
    )

    private val legacyDateFormats = listOf(
        "EEE, dd MMM yyyy HH:mm:ss zzz",
        "EEE, dd MMM yyyy HH:mm zzz",
        "yyyy-MM-dd'T'HH:mm:ssZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
    )

    fun parseEpochMillis(value: String?): Long? {
        val normalized = value?.trim()?.replace(Regex("\\s+"), " ") ?: return null
        if (normalized.isBlank()) {
            return null
        }

        dateTimeFormatters.forEach { formatter ->
            runCatching {
                return when (formatter) {
                    DateTimeFormatter.ISO_INSTANT -> Instant.parse(normalized).toEpochMilli()
                    DateTimeFormatter.RFC_1123_DATE_TIME ->
                        ZonedDateTime.parse(normalized, formatter).toInstant().toEpochMilli()

                    else -> ZonedDateTime.parse(normalized, formatter).toInstant().toEpochMilli()
                }
            }
        }

        runCatching {
            return LocalDate.parse(normalized, DateTimeFormatter.ISO_LOCAL_DATE)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli()
        }

        legacyDateFormats.forEach { pattern ->
            runCatching {
                val format = SimpleDateFormat(pattern, Locale.US).apply { isLenient = false }
                return format.parse(normalized)?.time
            }
        }

        return null
    }
}
