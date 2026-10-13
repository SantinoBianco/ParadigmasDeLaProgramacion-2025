package namedEntity;

import namedEntity.tema.*;
import java.util.ArrayList;
import java.util.List;

/*Esta clase modela la nocion de entidad nombrada*/

public class NamedEntity {
    String name;
    String category;
    int frequency;
    List<Tema> temas;
    String formaCanonica;

    public NamedEntity(String name, String category, int frequency) {
        this.name = name;
        this.category = category;
        this.frequency = frequency;
        this.temas = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void incFrequency() {
        this.frequency++;
    }

    public void addTema(Tema tema) {
        this.temas.add(tema);
    }

    public List<Tema> getTemas() {
        return temas;
    }

    public String getFormaCanonica() {
        return formaCanonica;
    }

    public void setFormaCanonica(String formaCanonica) {
        this.formaCanonica = formaCanonica;
    }

    @Override
    public String toString() {
        return "NamedEntity [name=" + name + ", category=" + category + ", frequency=" + frequency + ", temas=" + temas + ", formaCanonica=" + formaCanonica + "]";
    }

    public void prettyPrint() {
        System.out.println(this.toString());
    }
}

// ------------------- PERSONA Y DERIVADAS -------------------

class Persona extends NamedEntity {
    String apellido;
    String titulo;

    public Persona(String name, String category, int frequency, String apellido, String titulo) {
        super(name, category, frequency);
        this.apellido = apellido;
        this.titulo = titulo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return super.toString() + " [apellido=" + apellido + ", titulo=" + titulo + "]";
    }
}

// ------------------- LUGAR Y DERIVADAS -------------------

class Lugar extends NamedEntity {
    String pais;
    String ciudad;

    public Lugar(String name, String category, int frequency, String pais, String ciudad) {
        super(name, category, frequency);
        this.pais = pais;
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return super.toString() + " [pais=" + pais + ", ciudad=" + ciudad + "]";
    }
}

// ------------------- OTRAS ENTIDADES -------------------

class Organizacion extends NamedEntity {
    int numeroMiembros;
    String tipoOrganizacion;

    public Organizacion(String name, String category, int frequency, String formaCanonica, int numeroMiembros, String tipoOrganizacion) {
        super(name, category, frequency);
        this.formaCanonica = formaCanonica;
        this.numeroMiembros = numeroMiembros;
        this.tipoOrganizacion = tipoOrganizacion;
    }

    public int getNumeroMiembros() {
        return numeroMiembros;
    }

    public void setNumeroMiembros(int numeroMiembros) {
        this.numeroMiembros = numeroMiembros;
    }

    public String getTipoOrganizacion() {
        return tipoOrganizacion;
    }

    public void setTipoOrganizacion(String tipoOrganizacion) {
        this.tipoOrganizacion = tipoOrganizacion;
    }

    @Override
    public String toString() {
        return "Organizacion [formaCanonica=" + formaCanonica + ", numeroMiembros=" + numeroMiembros + ", tipoOrganizacion=" + tipoOrganizacion + "]";
    }

    public void prettyPrint() {
        System.out.println(this.getFormaCanonica() + " " + this.getNumeroMiembros() + " " + this.getTipoOrganizacion());
    }
}

class Producto extends NamedEntity {
    boolean comercial;
    String productor;

    public Producto(String name, String category, int frequency, boolean comercial, String productor) {
        super(name, category, frequency);
        this.comercial = comercial;
        this.productor = productor;
    }

    public boolean isComercial() {
        return comercial;
    }

    public void setComercial(boolean comercial) {
        this.comercial = comercial;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    @Override
    public String toString() {
        return "Producto [comercial=" + comercial + ", productor=" + productor + "]";
    }

    public void prettyPrint() {
        System.out.println(this.isComercial() + " " + this.getProductor());
    }
}

class Evento extends NamedEntity {
    String fecha;
    boolean recurrente;

    public Evento(String name, String category, int frequency, String fecha, boolean recurrente) {
        super(name, category, frequency);
        this.fecha = fecha;
        this.recurrente = recurrente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isRecurrente() {
        return recurrente;
    }

    public void setRecurrente(boolean recurrente) {
        this.recurrente = recurrente;
    }

    @Override
    public String toString() {
        return super.toString() + " [fecha=" + fecha + ", recurrente=" + recurrente + "]";
    }
}

class Fecha extends NamedEntity {
    boolean precisa;

    public Fecha(String name, String category, int frequency, boolean precisa, String formaCanonica) {
        super(name, category, frequency);
        this.precisa = precisa;
        this.formaCanonica = formaCanonica;
    }

    public boolean isPrecisa() {
        return precisa;
    }

    public void setPrecisa(boolean precisa) {
        this.precisa = precisa;
    }

    @Override
    public String toString() {
        return "Fecha [precisa=" + precisa + ", formaCanonica=" + formaCanonica + "]";
    }

    public void prettyPrint() {
        System.out.println(this.isPrecisa() + " " + this.getFormaCanonica());
    }
}