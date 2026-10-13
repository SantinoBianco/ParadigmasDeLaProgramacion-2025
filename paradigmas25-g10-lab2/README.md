#  Laboratorio 2: Programación Orientada a Objetos


##  Integrantes del grupo

- Santino Bianco  
- Bautista Cordoba  
- Fabrizio Reyna  
- Fabrizio Mandile


##  Comandos para compilar y ejecutar en Ubuntu

### 1. **Generar la lista de archivos `.java`**
```bash
find src -name "*.java" > sources.txt
```

### 2. **Compilar los archivos fuente**
```bash
javac -d bin -cp lib/json-20240303.jar @sources.txt
```

### 3. **Ejecutar el programa**

- Con heurísticas:
```bash
java -cp "bin:lib/json-20240303.jar" FeedReaderMain -ne
```

- Sin heurísticas:
```bash
java -cp "bin:lib/json-20240303.jar" FeedReaderMain
```
### 4. **Cambiar entre heuristicas**

Cambiar por RandomHeuristic() en donde dice QuickHeuristic()
```java
 processFeeds(subscriptionFilePath, true, new QuickHeuristic());
```
---


## Bitácora del Trabajo

Durante el desarrollo del laboratorio, nos encontramos con varios desafíos importantes. Uno de los principales fue diseñar correctamente la jerarquía de clases, lo cual fue especialmente difícil dado que era nuestra primera vez programando en **Java**

Al principio, no sabíamos muy bien por dónde arrancar con la jerarquía de clases, así que empezamos creando varios archivos separados, uno para cada clase.
Pero eso luego se cambio, para ponerlos como atributos.

Uno de los puntos más difíciles fue trabajar con el polimorfismo en la clase `GeneralParser`. Teníamos una idea básica de lo que era, pero aplicarlo bien nos costó. Tuvimos que investigar cómo funcionan las clases abstractas, y cómo hacer que distintas clases compartieran un comportamiento sin perder flexibilidad. Nos equivocamos varias veces, pero al final entendimos cómo hacerlo de forma correcta.




---
