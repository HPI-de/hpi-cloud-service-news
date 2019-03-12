package de.hpi.cloud.services.news

import java.io.IOException

/**
 * Main launches the server from the command line.
 */
@Throws(IOException::class, InterruptedException::class)
fun main() {
    val server = NewsService()
    server.start()
    server.blockUntilShutdown()
}
