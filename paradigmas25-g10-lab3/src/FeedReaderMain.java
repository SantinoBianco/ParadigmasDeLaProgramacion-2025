import feed.Article;
import feed.Feed;
import httpRequest.httpRequester;
import parser.SubscriptionParser;
import parser.RssParser;
import parser.RedditParser;
import parser.AtomParser;
import subscription.Subscription;
import subscription.SingleSubscription;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.heuristic.RandomHeuristic;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.util.*;

public class FeedReaderMain {

    private static void printHelp() {
        System.out.println("Please, call this program in correct way: FeedReader [-ne]");
    }

    public static void main(String[] args) {
        System.out.println("************* FeedReader version 1.0 *************");

        // Ruta del archivo de suscripciones
        String subscriptionFilePath = "config/subscriptions.json";

        if (args.length == 1 && args[0].equals("-ne")) {
            // Modo entidades con spark
            runSparkNamedEntities(subscriptionFilePath);
        } else {
            // Modo imprimir artículos sin spark
            runSparkPrintArticles(subscriptionFilePath);
        }
    }

    private static List<Article> fetchAllArticles(String subscriptionFilePath) {
        SubscriptionParser subscriptionParser = new SubscriptionParser();
        Subscription subscription = subscriptionParser.parse(subscriptionFilePath);
        List<String> urls = new ArrayList<>();
        for (SingleSubscription singleSubscription : subscription.getSubscriptionsList()) {
            if (singleSubscription.getUrlType().equalsIgnoreCase("rss")) {
                for (int i = 0; i < singleSubscription.getUlrParamsSize(); i++) {
                    urls.add(singleSubscription.getFeedToRequest(i));
                }
            }
        }
        List<Article> allArticles = new ArrayList<>();
        for (String url : urls) {
            httpRequester requester = new httpRequester();
            RssParser rssParser = new RssParser();
            AtomParser atomParser = new AtomParser();
            String feedContent = requester.getFeedRss(url);
            if (feedContent == null) continue;
            String trimmed = feedContent.trim();
            Feed feed;
            if (trimmed.startsWith("<feed") || trimmed.contains("<feed")) {
                feed = atomParser.parse(feedContent);
            } else {
                feed = rssParser.parse(feedContent);
            }
            allArticles.addAll(feed.getArticleList());
        }
        return allArticles;
    }

    private static void runSparkNamedEntities(String subscriptionFilePath) {
        SparkSession spark = SparkSession.builder()
                .appName("FeedReaderSpark")
                .master("local[*]")
                .getOrCreate();
        JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
        List<Article> allArticles = fetchAllArticles(subscriptionFilePath);
        JavaRDD<Article> articlesRDD = sc.parallelize(allArticles);
        JavaRDD<String> namedEntitiesRDD = articlesRDD.flatMap(article -> {
            Heuristic heuristic = new QuickHeuristic();
            article.computeNamedEntities(heuristic);
            List<String> entities = new ArrayList<>();
            for (namedEntity.NamedEntity ne : article.getNamedEntityList()) {
                entities.add(ne.getName());
            }
            return entities.iterator();
        });
        Map<String, Integer> entityCounts = namedEntitiesRDD
                .mapToPair(entity -> new Tuple2<>(entity, 1))
                .reduceByKey(Integer::sum)
                .collectAsMap();
        entityCounts.forEach((entity, count) -> System.out.println(entity + ": " + count));
        spark.stop();
    }

    private static void runSparkPrintArticles(String subscriptionFilePath) {
        List<Article> allArticles = fetchAllArticles(subscriptionFilePath);
        for (Article article : allArticles) {
            article.prettyPrint();
        }
    }

    private static void processFeeds(String subscriptionFilePath, boolean computeNamedEntities, Heuristic heuristic) {
        // Crear instancias necesarias
        SubscriptionParser subscriptionParser = new SubscriptionParser();
        httpRequester requester = new httpRequester();
        RssParser rssParser = new RssParser();
        RedditParser redditParser = new RedditParser();
        Feed feed = new Feed("FeedReader");

        // Leer el archivo de suscripciones
        Subscription subscription = subscriptionParser.parse(subscriptionFilePath);

        // Procesar cada suscripción
        for (SingleSubscription singleSubscription : subscription.getSubscriptionsList()) {
            for (int i = 0; i < singleSubscription.getUlrParamsSize(); i++) {
                String url = singleSubscription.getFeedToRequest(i);
                System.out.println("Fetching feed from: " + url);

                // Obtener el contenido del feed
                String feedContent = requester.getFeedRss(url);
                if (feedContent == null) {
                    System.err.println("Failed to fetch feed from: " + url);
                    continue;
                }

                // Determinar el tipo de feed y usar el parser adecuado
                if (singleSubscription.getUrlType().equalsIgnoreCase("rss")) {
                    Feed rssFeed = rssParser.parse(feedContent);
                    feed.addArticles(rssFeed.getArticleList());
                } else if (singleSubscription.getUrlType().equalsIgnoreCase("reddit")) {
                    Feed redditFeed = redditParser.parse(feedContent);
                    feed.addArticles(redditFeed.getArticleList());
                } else {
                    System.err.println("Unknown feed type: " + singleSubscription.getUrlType());
                }
            }
        }

        // Mostrar los artículos o computar entidades nombradas
        if (!computeNamedEntities) {
            feed.prettyPrint();
        } else {
            System.out.println("Computing named entities...");
            for (Article article : feed.getArticleList()) {
                article.computeNamedEntities(heuristic);
            }
            // Solo imprimir entidades que están en el map
            feed.printNamedEntitiesOnlyMapped(heuristic);
		}
	}
}