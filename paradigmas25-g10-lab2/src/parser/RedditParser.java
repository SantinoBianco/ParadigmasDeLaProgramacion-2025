package parser;

import feed.Article;
import feed.Feed;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

public class RedditParser extends GeneralParser<Feed> {

    @Override
    public Feed parse(String jsonContent) {
        Feed feed = new Feed("Reddit");
        try {
            // Parsear el contenido JSON
            JSONObject jsonObject = new JSONObject(jsonContent);
            JSONArray posts = jsonObject.getJSONObject("data").getJSONArray("children");

            // Extraer los datos de cada post
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.getJSONObject(i).getJSONObject("data");
                String title = post.getString("title");
                String link = post.getString("url");
                String description = post.optString("selftext", "No description available");

                // Crear un artículo y agregarlo al feed
                Article article = new Article(title, description, new Date(), link);
                feed.addArticle(article);
            }
        } catch (Exception e) {
            System.err.println("Error parsing Reddit feed: " + e.getMessage());
            e.printStackTrace();
        }
        return feed;
    }
}