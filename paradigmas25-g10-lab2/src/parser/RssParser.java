package parser;

import feed.Article;
import feed.Feed;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase RssParser que extiende GeneralParser para procesar feeds RSS.
 * Convierte el contenido XML de un feed RSS en un objeto Feed que contiene artículos.
 */
public class RssParser extends GeneralParser<Feed> {

    /**
     * Método principal que parsea el contenido XML de un feed RSS.
     * @param xmlContent El contenido XML del feed RSS como String.
     * @return Un objeto Feed que contiene los artículos extraídos del feed.
     */
    @Override
    public Feed parse(String xmlContent) {
        
        Feed feed = new Feed("RSS");
        try {
            Document document = parseXmlContent(xmlContent);
            NodeList items = document.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);

                Article article = parseArticle(item);

                feed.addArticle(article);
            }
        } catch (Exception e) {
            System.err.println("Error parsing RSS feed: " + e.getMessage());
            throw new RuntimeException("Failed to parse RSS feed", e);
        }
        return feed;
    }

    /**
     * Método auxiliar que convierte el contenido XML en un objeto Document.
     * @param xmlContent El contenido XML como String.
     * @return Un objeto Document que representa el contenido XML parseado.
     * @throws Exception Si ocurre un error durante el parseo.
     */
    private Document parseXmlContent(String xmlContent) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        ByteArrayInputStream input = new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8));

        return builder.parse(input);
    }

    /**
     * Método auxiliar que parsea un artículo desde un elemento <item>.
     * @param item El elemento <item> que contiene los datos del artículo.
     * @return Un objeto Article con los datos extraídos.
     */
    private Article parseArticle(Element item) {
        String title = getTagValue("title", item);

        String link = getTagValue("link", item);

        String description = getTagValue("description", item);

        String pubDate = getTagValue("pubDate", item);

        Date publicationDate = parseDate(pubDate);

        return new Article(title, description, publicationDate, link);
    }

    /**
     * Método auxiliar que obtiene el valor de un nodo XML por su etiqueta.
     * @param tag La etiqueta del nodo (por ejemplo, "title", "link").
     * @param element El elemento XML que contiene el nodo.
     * @return El valor del nodo como String, o un mensaje predeterminado si no existe.
     */
    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);

        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }

        return "No " + tag + " available";
    }

    /**
     * Método auxiliar que convierte una fecha en formato String a un objeto Date.
     * @param pubDate La fecha en formato String (por ejemplo, "Mon, 19 May 2025 18:23:42 +0000").
     * @return Un objeto Date que representa la fecha, o la fecha actual si ocurre un error.
     */
    private Date parseDate(String pubDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

            return formatter.parse(pubDate);
        } catch (Exception e) {
            System.err.println("Error parsing date: " + pubDate);

            return new Date();
        }
    }
}