package namedEntity.tema;

// implementamos clase TEMA y debemos aasociar temas a las entidades nombradas

public class Tema {
    private String categoria;
    private String subcategoria;

    public Tema(String categoria, String subcategoria) {
        this.categoria = categoria;
        this.subcategoria = subcategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    @Override
    public String toString() {
        return categoria + " → " + subcategoria;
    }
}