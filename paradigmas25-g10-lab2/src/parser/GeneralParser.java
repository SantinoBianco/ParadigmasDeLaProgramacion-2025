package parser;

public abstract class GeneralParser<T>{

    // Método abstracto para parsear un archivo
    public abstract T parse(String filePath);

    // Método común para validar la ruta del archivo
    protected boolean isValidFilePath(String filePath) {
        return filePath != null && !filePath.isEmpty();
    }
}