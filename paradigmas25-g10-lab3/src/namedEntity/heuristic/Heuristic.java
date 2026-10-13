package namedEntity.heuristic;

import java.util.Map;

public abstract class Heuristic {

	private static Map<String, String> categoryMap = Map.ofEntries(
    // Organizaciones
    Map.entry("Microsoft", "NamedEntity:Organizacion"),
    Map.entry("Apple", "NamedEntity:Organizacion"),
    Map.entry("Google", "NamedEntity:Organizacion"),
    Map.entry("Amazon", "NamedEntity:Organizacion"),
    Map.entry("Facebook", "NamedEntity:Organizacion"),
    Map.entry("Netflix", "NamedEntity:Organizacion"),
    Map.entry("Tesla", "NamedEntity:Organizacion"),
    Map.entry("SpaceX", "NamedEntity:Organizacion"),
    Map.entry("LinkedIn", "NamedEntity:Organizacion"),
    Map.entry("Uber", "NamedEntity:Organizacion"),
    Map.entry("Airbnb", "NamedEntity:Organizacion"),
    Map.entry("Disney", "NamedEntity:Organizacion"),
    Map.entry("Samsung", "NamedEntity:Organizacion"),
    Map.entry("Toyota", "NamedEntity:Organizacion"),
    Map.entry("Pfizer", "NamedEntity:Organizacion"),
    Map.entry("Boeing", "NamedEntity:Organizacion"),
    Map.entry("BBC", "NamedEntity:Organizacion"),
    Map.entry("Reuters", "NamedEntity:Organizacion"),
    Map.entry("UNICEF", "NamedEntity:Organizacion"),
    Map.entry("WHO", "NamedEntity:Organizacion"),
    Map.entry("UNESCO", "NamedEntity:Organizacion"),
    Map.entry("Shell", "NamedEntity:Organizacion"),
    Map.entry("Ford", "NamedEntity:Organizacion"),
    Map.entry("Coca-Cola Company", "NamedEntity:Organizacion"),
    // Personas
    Map.entry("Musk", "Persona:Apellido"),
    Map.entry("Biden", "Persona:Apellido"),
    Map.entry("Trump", "Persona:Apellido"),
    Map.entry("Messi", "Persona:Apellido"),
    Map.entry("Federer", "Persona:Apellido"),
    Map.entry("Einstein", "Persona:Apellido"),
    Map.entry("Newton", "Persona:Apellido"),
    Map.entry("Curie", "Persona:Apellido"),
    Map.entry("Jobs", "Persona:Apellido"),
    Map.entry("Zuckerberg", "Persona:Apellido"),
    Map.entry("Obama", "Persona:Apellido"),
    Map.entry("Merkel", "Persona:Apellido"),
    Map.entry("Ronaldo", "Persona:Apellido"),
    Map.entry("Williams", "Persona:Apellido"),
    Map.entry("Winfrey", "Persona:Apellido"),
    Map.entry("Bezos", "Persona:Apellido"),
    Map.entry("Gates", "Persona:Apellido"),
    Map.entry("Twain", "Persona:Apellido"),
    Map.entry("Antoinette", "Persona:Apellido"),
    Map.entry("Lovelace", "Persona:Apellido"),
    Map.entry("Barack", "Persona:Nombre"),
    Map.entry("Angela", "Persona:Nombre"),
    Map.entry("Cristiano", "Persona:Nombre"),
    Map.entry("Lionel", "Persona:Nombre"),
    Map.entry("Serena", "Persona:Nombre"),
    Map.entry("Oprah", "Persona:Nombre"),
    Map.entry("Jeff", "Persona:Nombre"),
    Map.entry("Bill", "Persona:Nombre"),
    Map.entry("Mark", "Persona:Nombre"),
    Map.entry("Marie", "Persona:Nombre"),
    Map.entry("Ada", "Persona:Nombre"),
    // Países
    Map.entry("USA", "Lugar:Pais"),
    Map.entry("Usa", "Lugar:Pais"),
    Map.entry("United States", "Lugar:Pais"),
    Map.entry("Russia", "Lugar:Pais"),
    Map.entry("France", "Lugar:Pais"),
    Map.entry("Germany", "Lugar:Pais"),
    Map.entry("Japan", "Lugar:Pais"),
    Map.entry("Canada", "Lugar:Pais"),
    Map.entry("Brazil", "Lugar:Pais"),
    Map.entry("India", "Lugar:Pais"),
    Map.entry("China", "Lugar:Pais"),
    Map.entry("UK", "Lugar:Pais"),
    Map.entry("Italy", "Lugar:Pais"),
    Map.entry("Australia", "Lugar:Pais"),
    Map.entry("Mexico", "Lugar:Pais"),
    Map.entry("Argentina", "Lugar:Pais"),
    Map.entry("Chile", "Lugar:Pais"),
    Map.entry("South Korea", "Lugar:Pais"),
    Map.entry("Turkey", "Lugar:Pais"),
    // Ciudades
    Map.entry("Paris", "Lugar:Ciudad"),
    Map.entry("London", "Lugar:Ciudad"),
    Map.entry("Tokyo", "Lugar:Ciudad"),
    Map.entry("New York", "Lugar:Ciudad"),
    Map.entry("Berlin", "Lugar:Ciudad"),
    Map.entry("Toronto", "Lugar:Ciudad"),
    Map.entry("Beijing", "Lugar:Ciudad"),
    Map.entry("San Francisco", "Lugar:Ciudad"),
    Map.entry("Los Angeles", "Lugar:Ciudad"),
    Map.entry("Chicago", "Lugar:Ciudad"),
    Map.entry("Rome", "Lugar:Ciudad"),
    Map.entry("Sydney", "Lugar:Ciudad"),
    Map.entry("Melbourne", "Lugar:Ciudad"),
    Map.entry("Istanbul", "Lugar:Ciudad"),
    Map.entry("Seoul", "Lugar:Ciudad"),
    Map.entry("Buenos Aires", "Lugar:Ciudad"),
    Map.entry("Santiago", "Lugar:Ciudad"),
    Map.entry("Moscow", "Lugar:Ciudad"),
    Map.entry("Madrid", "Lugar:Ciudad"),
    Map.entry("Barcelona", "Lugar:Ciudad"),
    // Eventos
    Map.entry("Olympics", "Evento"),
    Map.entry("World Cup", "Evento"),
    Map.entry("Super Bowl", "Evento"),
    Map.entry("Grammys", "Evento"),
    Map.entry("Oscars", "Evento"),
    Map.entry("Emmys", "Evento"),
    Map.entry("Champions League", "Evento"),
    Map.entry("Copa America", "Evento"),
    Map.entry("Wimbledon", "Evento"),
    Map.entry("Tour de France", "Evento"),
    Map.entry("Formula 1", "Evento"),
    Map.entry("MotoGP", "Evento"),
    Map.entry("US Open", "Evento"),
    Map.entry("Roland Garros", "Evento"),
    // Productos
    Map.entry("iPhone", "Producto"),
    Map.entry("MacBook", "Producto"),
    Map.entry("PlayStation", "Producto"),
    Map.entry("Xbox", "Producto"),
    Map.entry("Coca-Cola", "Producto"),
    Map.entry("Pepsi", "Producto"),
    Map.entry("Galaxy", "Producto"),
    Map.entry("Pixel", "Producto"),
    Map.entry("Kindle", "Producto"),
    Map.entry("Surface", "Producto"),
    Map.entry("Echo", "Producto"),
    Map.entry("Alexa", "Producto"),
    Map.entry("Nest", "Producto"),
    Map.entry("Switch", "Producto"),
    Map.entry("Playmobil", "Producto"),
    Map.entry("Lego", "Producto"),
    Map.entry("Barbie", "Producto"),
    Map.entry("Hot Wheels", "Producto"),
    Map.entry("iPad", "Producto"),
    Map.entry("AirPods", "Producto")
	);

	// Devuelve la categoría o "Other" si no está
	public String getCategory(String entity){
	    String val = categoryMap.get(entity);
	    if (val == null) return "Other";
	    return val.split(":")[0];
	}

	// Devuelve la subcategoría o "-" si no está
	public String getSubcategory(String entity){
	    String val = categoryMap.get(entity);
	    if (val == null || !val.contains(":")) return "-";
	    return val.split(":")[1];
	}
	
	
	public abstract boolean isEntity(String word);
		
}
