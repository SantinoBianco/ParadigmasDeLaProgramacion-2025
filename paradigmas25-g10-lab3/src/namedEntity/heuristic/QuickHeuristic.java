package namedEntity.heuristic;

import java.util.ArrayList;
import java.util.List;

public class QuickHeuristic extends Heuristic{
	
	private static List<String> keyWords = List.of(
		    "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you",
		    "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she",
		    "her", "hers", "herself", "it", "its", "itself", "they", "them", "your",
		    "their", "theirs", "themselves", "what", "which", "who", "whom",
		    "this", "that", "these", "those", "am", "is", "are", "was", "were",
		    "be", "been", "being", "have", "has", "had", "having", "do", "does",
		    "did", "doing", "a", "an", "the", "and", "but", "if", "or",
		    "because", "as", "until", "while", "of", "at", "by", "for", "with",
		    "about", "against", "between", "into", "through", "during", "before",
		    "after", "above", "below", "to", "from", "up", "down", "in", "out",
		    "off", "over", "under", "again", "further", "then", "once", "here",
		    "there", "when", "where", "why", "how", "all", "any", "both", "each",
		    "few", "more", "most", "other", "some", "such", "no", "nor", "not",
		    "only", "own", "same", "so", "than", "too", "very", "s", "t", "can",
		    "will", "just", "don", "should", "now", "on",
		    // Contractions without '
		    "im", "ive", "id", "Youre", "youd", "youve",
		    "hes", "hed", "shes", "shed", "itd", "were", "wed", "weve",
		    "theyre", "theyd", "theyve",
		    "shouldnt", "couldnt", "musnt", "cant", "wont",
		    // Common uppercase words
		    "hi", "hello"
			);
	
	// Solo sobreescribe isEntity, y delega getCategory/getSubcategory al map de Heuristic
	@Override
	public boolean isEntity(String word) {
	    // Verificar si la palabra está en el Map de categorías de Heuristic
	    return super.getCategory(word) != null && !keyWords.contains(word.toLowerCase());
	}
	// Chequea si la palabra esta en el mapa de entidades y cumpla con la heuristica
	public List<String> findMatchingEntities(List<String> words) {
	    List<String> matchingEntities = new ArrayList<>();
	    for (String word : words) {
	        if (this.isEntity(word)) {
	            matchingEntities.add(word);
	        }
	    }
	    return matchingEntities;
	}
		
	public static void main(String[] args) {
//		QuickHeuristic qh = new QuickHeuristic();
	}	

}