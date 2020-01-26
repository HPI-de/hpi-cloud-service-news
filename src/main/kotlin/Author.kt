package de.hpi.cloud.services.news

import java.net.URL

/**
 * Represents the Author (wrapper around person) of an article.
 *
 * *Note: An instance is related to a specific site, so e.g. postCount is not global.*
 */
data class Author(
    /**
     * The unique ID of this author.
     */
    val id: String,

    /**
     * The ID of the person of this author.
     */
    val personId: String,

    /**
     * The site on which this author is registered.
     */
    val site: Site,

    /**
     * A URL pointing to the profile of this author on this site.
     */
    val link: URL,

    /**
     * A description provided by the author about himself.
     */
    val description: String? = null,

    /**
     * The number of posts.
     */
    val postCount: Int? = null,

    /**
     * The number of comments.
     */
    val commentCount: Int? = null
)
