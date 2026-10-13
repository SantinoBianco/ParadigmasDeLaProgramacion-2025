package feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namedEntity.NamedEntity;
import namedEntity.heuristic.Heuristic;

/*Esta clase modela el contenido de un articulo (ie, un item en el caso del rss feed) */

public class Article implements java.io.Serializable {
	private String title;
	private String text;
	private Date publicationDate;
	private String link;
	
	private List<NamedEntity> namedEntityList = new ArrayList<NamedEntity>();
	
	
	public Article(String title, String text, Date publicationDate, String link) {
		super();
		this.title = title;
		this.text = text;
		this.publicationDate = publicationDate;
		this.link = link;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@Override
	public String toString() {
		return "Article [title=" + title + ", text=" + text + ", publicationDate=" + publicationDate + ", link=" + link
				+ "]";
	}
	
	
	
	public NamedEntity getNamedEntity(String namedEntity){
		for (NamedEntity n: namedEntityList){
			if (n.getName().compareTo(namedEntity) == 0){				
				return n;
			}
		}
		return null;
	}
	
	public void computeNamedEntities(Heuristic h){ // Esta función toma el texto del artículo, lo limpia y aplica una heurística para identificar entidades nombradas
        String text = this.getTitle() + " " +  this.getText();  
        String charsToRemove = ".,;:()'!?\n";
        for (char c : charsToRemove.toCharArray()) {
            text = text.replace(String.valueOf(c), "");
        }
        // Eliminar URLs, hrefs, tags HTML y entidades
        text = text.replaceAll("https?://\\S+", ""); 
        text = text.replaceAll("href=\\\"[^\\\"]*\\\"", ""); 
        text = text.replaceAll("href='[^']*'", ""); 
        text = text.replaceAll("<[^>]*>", ""); 
        text = text.replaceAll("&[a-zA-Z0-9#]+;", " "); 
        // Eliminar palabras con caracteres raros o que no sean solo letras/números
        for (String s: text.split(" ")) {
            if (!s.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ0-9']+")) continue;
            if (h.isEntity(s)){
                NamedEntity ne = this.getNamedEntity(s);
                if (ne == null) {
                    // Usar buildEntity para obtener la entidad real
                    NamedEntity entity = null;
                    try {
                        java.lang.reflect.Method buildEntityMethod = h.getClass().getMethod("buildEntity", String.class);
                        entity = (NamedEntity) buildEntityMethod.invoke(h, s);
                    } catch (Exception e) {
                        // Si no existe buildEntity, fallback a NamedEntity genérico
                        entity = new NamedEntity(s, h.getCategory(s), 1);
                    }
                    if (entity == null) {
                        entity = new NamedEntity(s, h.getCategory(s), 1);
                    }
                    this.namedEntityList.add(entity);
                }else {
                    ne.incFrequency();
                }
            }
        } 
    }

	public void printNamedEntitiesOnlyMapped(Heuristic h) {
	    if (namedEntityList.isEmpty()) {
	        System.out.println("No named entities found.");
	        return;
	    }
	    System.out.printf("%-20s | %-15s | %-15s | %-10s%n", "Entity Name", "Category", "Subcategory", "Frequency");
	    System.out.println("--------------------------------------------------");
	    for (NamedEntity entity : namedEntityList) {
	        String category = h.getCategory(entity.getName());
	        String subcategory = h.getSubcategory(entity.getName());
	        if (!"Other".equals(category)) {
	            System.out.printf("%-20s | %-15s | %-15s | %-10d%n",
	                entity.getName(),
	                category,
	                subcategory,
	                entity.getFrequency());
	        }
	    }
	}

	public void prettyPrint() {
		System.out.println("**********************************************************************************************");
		System.out.println("Title: " + stripHtml(this.getTitle()));
		System.out.println("Publication Date: " + this.getPublicationDate());
		System.out.println("Link: " + this.getLink());
		System.out.println("Text: " + stripHtml(this.getText()));
		System.out.println("**********************************************************************************************");
	}

	private String stripHtml(String input) {
        if (input == null) return "";
        return input.replaceAll("<[^>]*>", "").replaceAll("&[a-zA-Z0-9#]+;", " ").trim();
    }
	
	public boolean hasMappedEntities(Heuristic h) {
	    for (NamedEntity entity : namedEntityList) {
	        if (!"Other".equals(h.getCategory(entity.getName()))) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public List<NamedEntity> getNamedEntityList() {
        return namedEntityList;
    }
	
	public static void main(String[] args) {
		  Article a = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
			  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
			  new Date(),
			  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
			  );
		 
		  a.prettyPrint();
	}
	
	
}



