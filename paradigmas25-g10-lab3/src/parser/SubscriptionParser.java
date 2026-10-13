package parser;

import java.io.FileReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import subscription.SingleSubscription;
import subscription.Subscription;

public class SubscriptionParser extends GeneralParser<subscription.Subscription> {

    @Override
    public subscription.Subscription parse(String rutaArchivo) {
        
        Subscription subscription = new Subscription(rutaArchivo);


        try (FileReader lector = new FileReader(rutaArchivo)) {
            JSONTokener tokener = new JSONTokener(lector);
            JSONArray feedsArray = new JSONArray(tokener);

            for (int i = 0; i < feedsArray.length(); i++) {
                JSONObject feed = feedsArray.getJSONObject(i);
                String url = feed.getString("url");
                String urlType = feed.getString("urlType");
                JSONArray urlParamsArray = feed.getJSONArray("urlParams");

                SingleSubscription singleSubscription = new SingleSubscription(url, null, urlType);

                for (int j = 0; j < urlParamsArray.length(); j++) {
                    singleSubscription.setUlrParams(urlParamsArray.getString(j));
                }

                subscription.addSingleSubscription(singleSubscription);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return subscription;
    }
}