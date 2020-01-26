package de.hpi.cloud.services.news

import com.google.api.server.spi.types.DateAndTime
import java.net.URL

/**
 * Represents a news article.
 */
data class Article(
    /**
     * The unique ID (across sites) of this article.
     */
    val id: String,

    /**
     * Site of first appearance of this article.
     */
    val site: Site,

    /**
     * A link to the original source.
     */
    val link: URL,

    /**
     * The title of this news article.
     */
    val title: String,

    /**
     * The publishing date.
     */
    val publishDate: DateAndTime,

    /**
     * A URL pointing to the thumbnail image.
     */
    val thumbnail: URL? = null,

    /**
     * A short text summarizing this article. Recommended for a preview.
     */
    val description: String,

    /**
     * The publishing date.
     */
    val authorIds: List<String>? = null,

    /**
     * The publishing date.
     */
    val content: String,

    /**
     * The publishing date.
     */
    val tags: List<Tag>? = null,

    /**
     * The publishing date.
     */
    val viewCount: Int? = null
)

/**
 * Site of an article.
 */
enum class Site {
    /**
     * Unspecified site.
     */
    UNSPECIFIED,

    /**
     * The article originates from [hpi.de/medien/presseinformationen/news.html].
     */
    HPI_NEWS,

    /**
     * The article originates from [hpi.de/medien/presseinformationen/news.html].
     */
    HPI_MGZN,
}


/**
 * Represents a tag by which articles can be filtered.
 */
data class Tag(
    /**
     * The unique ID of this tag.
     */
    val id: String,

    /**
     * The name of this tag.
     */
    val name: String,

    /**
     * The number of articles tagged with this tag.
     */
    val articleCount: Int
)
