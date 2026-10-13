import feed.Article;
import feed.Feed;
import httpRequest.httpRequester;
import parser.SubscriptionParser;
import parser.RssParser;
import parser.RedditParser;
import subscription.Subscription;
import subscription.SingleSubscription;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.heuristic.RandomHeuristic;

public class FeedReaderMain {

    private static void printHelp() {
        System.out.println("Please, call this program in correct way: FeedReader [-ne]");
    }

    public static void main(String[] args) {
        System.out.println("************* FeedReader version 1.0 *************");

        // Ruta del archivo de suscripciones
        String subscriptionFilePath = "config/subscriptions.json";

        if (args.length == 0) {
            // Caso 1: Mostrar los artículos del feed
            processFeeds(subscriptionFilePath, false, null);
        } else if (args.length == 1 && args[0].equals("-ne")) {
            // Caso 2: Computar entidades nombradas
            processFeeds(subscriptionFilePath, true, new QuickHeuristic());
        } else {
            // Caso 3: Argumentos inválidos
            printHelp();
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