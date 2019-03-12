package de.hpi.cloud.services.news

import com.google.protobuf.util.Timestamps
import de.hpi.cloud.news.v1test.*
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.protobuf.services.ProtoReflectionService
import io.grpc.stub.StreamObserver
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger

class NewsService {
    companion object {
        private val logger = Logger.getLogger(NewsService::class.java.name)
    }

    private var server: Server? = null

    @Throws(IOException::class)
    internal fun start() {
        /* The port on which the server should run */
        val port = 8000
        server = ServerBuilder.forPort(port)
            .addService(NewsServiceImpl())
            .addService(ProtoReflectionService.newInstance())
            .build()
            .start()
        logger.log(Level.INFO, "Server started, listening on {0}", port)
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down")
                this@NewsService.stop()
                System.err.println("*** server shut down")
            }
        })
    }

    private fun stop() {
        server?.shutdown()
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    @Throws(InterruptedException::class)
    internal fun blockUntilShutdown() {
        server?.awaitTermination()
    }

    internal class NewsServiceImpl : NewsServiceGrpc.NewsServiceImplBase() {
        companion object {
            val articles = listOf<Article>(
                Article.newBuilder()
                    .setId("1")
                    .setSite(Article.Site.HPI_NEWS)
                    .setLink("https://hpi.de/news/jahrgaenge/2019/die-schul-cloud-fuer-brandenburg-bildungsministerin-britta-ernst-unterzeichnet-absichtserklaerung-zur-nutzung-der-hpi-schul-cloud.html")
                    .setTitle("Die Schul-Cloud für Brandenburg: Bildungsministerin Britta Ernst unterzeichnet Absichtserklärung zur Nutzung der HPI Schul-Cloud")
                    .setPublishDate(Timestamps.fromMillis(1551394800000L))
                    .setThumbnail("https://hpi.de/fileadmin/user_upload/hpi/bilder/teaser_news/2019/HPI_Schul_Cloud_2019_1020x420.jpg")
                    .setDescription("Künftig sollen viel mehr Schulen in Brandenburg die HPI Schul-Cloud nutzen und sich miteinander vernetzen können. Eine entsprechende Absichtserklärung unterzeichneten am 01. März 2019 die Bildungsministerin Britta Ernst, HPI-Direktor Professor Christoph Meinel und die Projektpartner in Potsdam.")
                    .setContent(
                        """
                        Seit 2016 entwickelt das Hasso-Plattner-Institut unter der Leitung von Professor Christoph Meinel zusammen mit dem nationalen Excellence-Schulnetzwerk MINT-EC und gefördert durch das Bundesministerium für Bildung und Forschung die HPI Schul-Cloud. Sie soll die technische Grundlage schaffen, dass Lehrkräfte und Schüler in jedem Unterrichtsfach auch moderne digitale Lehr- und Lerninhalte nutzen können, und zwar so einfach, wie Apps über Smartphones oder Tablets nutzbar sind. Von August 2019 bis Juli 2021 wird nun auch eine Brandenburgische Version der Schul-Cloud entwickelt und evaluiert werden. Dazu haben heute in Potsdam Bildungsministerin Britta Ernst, der Gründungsgeschäftsführer der DigitalAgentur Brandenburg GmbH, Olav Wilms, und der Direktor des Hasso-Plattner-Instituts (HPI) und Leiter des Lehrstuhls Internet-Technologien und Systeme, Prof. Christoph Meinel, eine Absichtserklärung zur Pilotierung einer Schul-Cloud im Land Brandenburg unterzeichnet. Für die Pilotierung haben sich bereits 27 „medienfit sek I“-Schulen angemeldet. Weitere medienfit-Grundschulen werden folgen. Die schrittweise Inbetriebnahme der Cloud ist bis zum Schuljahr 2021/22 angestrebt.

                        Bildungsministerin Britta Ernst betonte: „Das Lernen mit digitalen Medien ist die Herausforderung mit der größten Dynamik. Dazu braucht es eine leistungsfähige digitale Infrastruktur. Zu den wichtigsten Vorhaben gehört die im Hasso-Plattner-Institut entwickelte Schul-Cloud. Sie soll die Schulen des Landes vernetzen, so dass Lehrkräfte, Schülerinnen und Schüler jederzeit und überall Zugang zu Lern- und Lehrmaterialien haben. Die professionelle zentrale Wartung für alle Schulen verringert deutlich den Verwaltungs- und Betreuungsaufwand. Wir versprechen uns von der Schul-Cloud einen deutlichen Schub in der digitalen Bildung und freuen uns auf den Start des Projekts.“

                        Auch HPI-Direktor Professor Christoph Meinel freut sich über die Kooperation mit dem Land Brandenburg. „Mit der HPI Schul-Cloud können Lehrkräfte und Schüler in jedem Unterrichtsfach sehr einfach digitale Lehr- und Lerninhalte nutzen und das unter Einhaltung der hohen gesetzlichen Datenschutzregelungen. Ich freue mich daher sehr, dass ab dem neuen Schuljahr weitere Schulen in Brandenburg mit der Schul-Cloud arbeiten werden.“

                        Derzeit arbeiten bundesweit bereits 100 ausgewählte MINT-EC-Schulen mit der HPI Schul-Cloud. Dazu kommen 43 Schulen der Niedersächsischen Bildungscloud (NBC).
                    """.trimIndent()
                    )
                    .buildPartial(),
                Article.newBuilder()
                    .setId("1")
                    .setSite(Article.Site.HPI_MGZN)
                    .setLink("https://hpimgzn.de/2019/von-wurmmehlkeksen-bis-hin-zu-kompostieranlagen/")
                    .setTitle("Von Wurmmehlkeksen bis hin zu Kompostieranlagen")
                    .setPublishDate(Timestamps.fromMillis(1550185200000))
                    .setThumbnail("https://hpimgzn.de/wp-content/uploads/2018/12/photo_2018-12-16_20-19-43.jpg")
                    .setDescription("Am 16.12 hat der Nachhaltigkeitsklub seinen ersten Workshop angeboten. Von Bokashianlagenbau bis hin zur Herstellung selbstgemachter Weihnachtsgeschenke. Lest hier mehr.")
                    .addAuthorIds("1")
                    .setContent(
                        "„Wollt ihr auch einmal Heuschrecken kosten?“ Diese Frage kursiert am 16.12. im HPI Hauptgebäude. Woher sie kommt? Aus Richtung der Teeküche, in der sich für den heutigen Tag eine Mischung aus Nachhaltigkeitsklubmitgliedern und externen Interessierten eingefunden hat. Welcher Anlass? Unser erster Nachhaltigkeitsworkshoptag.\n" +
                                "\n" +
                                "Warum muss man immer alles kaufen? Heute wollen wir selbst Hand anlegen. Und so geht es gleich mit den ersten selbst gemachten Müsliriegeln los. Schon bald wird das Erdgeschoss des Hauptgebäudes mit Müsliriegelduft durchströmt. Aus dem Ofen können wir nach einiger Backzeit nicht nur ansehnliche, sondern auch sehr leckere Müslischnitten entnehmen.\n" +
                                "\n" +
                                "Die Nachwärme der Herdplatte, den wir zum Nüsserösten verwendet haben, nutzen wir gleich weiter, um etwas anderes zu rösten: Heuschrecken und Mehlwürmer, sowie Buffalowürmer. Als diese fertig sind, können wir diese etwas ungewöhnliche Proteinquelle verspeisen sowie die von einer Teilnehmerin mitgebrachten Buffalomehlkekse probieren. Interessant!\n" +
                                "\n" +
                                "\n" +
                                "Natürlich gibt es zwischendurch auch etwas Leckeres zu essen – unter anderem Biogemüse.\n" +
                                "Der Workshop geht weiter mit der Herstellung von Badekugeln und Waschmittel, sowie dem Bau einer Bokashi-Anlage – einem Innenkomposter. Für alle, die Lust haben davon etwas nachzumachen gibt es die Anleitungen hier.\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "Einer dieser Innenkomposter steht inzwischen auch in der Teeküche für euch bereit. Ihr seid herzlich dazu eingeladen, eure Bioabfälle in diesem zu entsorgen und auch, ab in ca. zwei Wochen, wenn der erste Dünger entstanden sein sollte, euch daran zu bedienen – eure Küchenpflanzen werden sich freuen.\n" +
                                "\n" +
                                "\n" +
                                "Bei der Herstellung unserer Kompostieranlagen hat uns die Kreativität gepackt.\n" +
                                "\n" +
                                " \n" +
                                "\n" +
                                "Geplant ist es, auch in Zukunft, einmal monatlich einen Workshoptag anzubieten. Dabei werden wir Trinkschokoladensticks sowie vegane Milchalternativen selbst herstellen, Shampoo produzieren, das Stricken von Hausschuhen erlernen…\n" +
                                "\n" +
                                "Besonders sollen diese Workshops als Austauschplattform und zum Ausprobieren von Alternativen dienen, sowie Freiräume zum Diskutieren bieten.\n" +
                                "\n" +
                                "Kommt bei Ideen einfach auf uns zu oder schreibt uns an:\n" +
                                "Lilith.Diringer@student.hpi.de\n" +
                                "Malte.Barth@student.hpi.de"
                    )
                    .addAllTags(
                        listOf<Tag>(
                            Tag.newBuilder()
                                .setId("1")
                                .setName("Nachhaltigkeitsklub")
                                .setArticleCount(5)
                                .build(),
                            Tag.newBuilder()
                                .setId("2")
                                .setName("Essen")
                                .setArticleCount(1)
                                .build(),
                            Tag.newBuilder()
                                .setId("3")
                                .setName("selbstgemacht")
                                .setArticleCount(2)
                                .build()
                        )
                    )
                    .setViewCount(59)
                    .build()
            )

            val authors = listOf<Author>(
                Author.newBuilder()
                    .setId("1")
                    .addSite(Article.Site.HPI_MGZN)
                    .setLink("https://hpimgzn.de/author/lilith-diringer/")
                    .setDescription("")
                    .setPostCount(7)
                    .setCommentCount(0)
                    .build()
            )
        }


        override fun listArticles(
            request: ListArticlesRequest?,
            responseObserver: StreamObserver<ListArticlesResponse>?
        ) {
            request ?: return
            responseObserver ?: return

            val articles = articles.run {
                val site = request.site ?: return@run this
                filter { it.site == site }
            }
            responseObserver.onNext(
                ListArticlesResponse.newBuilder()
                    .addAllArticles(articles)
                    .build()
            )
            responseObserver.onCompleted()
        }

        override fun getArticle(request: GetArticleRequest?, responseObserver: StreamObserver<Article>?) {
            request ?: return
            responseObserver ?: return

            val article = articles.firstOrNull { it.id == request.id }
                ?: return responseObserver.onError(
                    Status.NOT_FOUND
                        .withDescription("Article with id ${request.id} doesn't exist")
                        .asRuntimeException()
                )
            responseObserver.onNext(article)
            responseObserver.onCompleted()
        }

        override fun listAuthors(request: ListAuthorsRequest?, responseObserver: StreamObserver<ListAuthorsResponse>?) {
            request ?: return
            responseObserver ?: return

            val articles = authors.run {
                val personId = request.personId ?: return@run this
                filter { it.personId == personId }
            }.run {
                val site = request.site ?: return@run this
                filter { it.siteList.contains(site) }
            }.run {
                val articleId = request.articleId ?: return@run this
                val article = articles.firstOrNull { it.id == articleId }
                    ?: return responseObserver.onError(StatusRuntimeException(Status.NOT_FOUND))
                filter { it.id in article.authorIdsList }
            }
            responseObserver.onNext(
                ListAuthorsResponse.newBuilder()
                    .addAllAuthors(authors)
                    .build()
            )
            responseObserver.onCompleted()
        }

        override fun getAuthor(request: GetAuthorRequest?, responseObserver: StreamObserver<Author>?) {
            request ?: return
            responseObserver ?: return

            val author = authors.firstOrNull { it.id == request.id }
                ?: return responseObserver.onError(
                    Status.NOT_FOUND
                        .withDescription("Author with id ${request.id} doesn't exist")
                        .asRuntimeException()
                )
            responseObserver.onNext(author)
            responseObserver.onCompleted()
        }
    }
}
