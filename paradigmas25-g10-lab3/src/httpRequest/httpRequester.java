package httpRequest;


/* Esta clase se encarga de realizar efectivamente el pedido de feed al servidor de noticias
 * Leer sobre como hacer una http request en java
 * https://www.baeldung.com/java-http-request
 * */


/*
Utilizaremos la API HttpClient para realizar las peticiones HTTP.
https://www.baeldung.com/java-9-http-client

La API consta de tres clases principales:

HttpRequest representa la solicitud que se enviará a través de HttpClient.
HttpClient se comporta como un contenedor para la información de configuración común a varias solicitudes.
HttpResponse representa el resultado de una llamada HttpRequest.
*/

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class httpRequester {

    public String getFeedRss(String urlFeed) {
        return getContentFromUrl(urlFeed);
    }

    public String getFeedReedit(String urlFeed) {
        return getContentFromUrl(urlFeed);
    }

    // Esta función se conecta a la URL y devuelve el texto que obtiene
    private String getContentFromUrl(String url) {
        HttpClient client = HttpClient.newHttpClient(); // Cliente para hacer pedidos
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.err.println("Error HTTP " + response.statusCode() + " al solicitar: " + url);
                return null;
            }
			return response.body(); // Si todo sale bien, devolvemos el texto recibido
        } catch (IOException | InterruptedException e) {
            System.out.println("Hubo un error al pedir el contenido de: " + url);
            e.printStackTrace();
            return null;
		}
	}
}