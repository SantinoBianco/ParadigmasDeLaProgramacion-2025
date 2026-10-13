package parser;

import feed.Article;
import feed.Feed;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AtomParser extends GeneralParser<Feed> {
    @Override
    public Feed parse(String xmlContent) {
        Feed feed = new Feed("Atom");
        try {
            Document document = parseXmlContent(xmlContent);
            NodeList entries = document.getElementsByTagName("entry");
            for (int i = 0; i < entries.getLength(); i++) {
                Element entry = (Element) entries.item(i);
                Article article = parseArticle(entry);
                feed.addArticle(article);
            }
        } catch (Exception e) {
            System.err.println("Error parsing Atom feed: " + e.getMessage());
            throw new RuntimeException("Failed to parse Atom feed", e);
        }
        return feed;
    }

    private Document parseXmlContent(String xmlContent) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream input = new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8));
        return builder.parse(input);
    }

    private Article parseArticle(Element entry) {
        String title = getTagValue("title", entry);
        String link = getLink(entry);
        String summary = getTagValue("summary", entry);
        if (summary.equals("No summary available")) {
            summary = getTagValue("content", entry);
        }
        String dateStr = getTagValue("updated", entry);
        if (dateStr.startsWith("No ")) {
            dateStr = getTagValue("published", entry);
        }
        Date publicationDate = parseDate(dateStr);
        return new Article(title, summary, publicationDate, link);
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "No " + tag + " available";
    }

    private String getLink(Element entry) {
        NodeList links = entry.getElementsByTagName("link");
        for (int i = 0; i < links.getLength(); i++) {
            Element linkElem = (Element) links.item(i);
            if (linkElem.hasAttribute("href")) {
                return linkElem.getAttribute("href");
            }
        }
        return "No link available";
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.startsWith("No ")) return new Date();
        try {

            return javax.xml.bind.DatatypeConverter.parseDateTime(dateStr).getTime();
        } catch (Exception e) {
            System.err.println("Error parsing Atom date: " + dateStr);
            return new Date();
        }
    }
}
