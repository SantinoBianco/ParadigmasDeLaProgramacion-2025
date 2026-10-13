# FeedReader - Laboratorio 3 Paradigmas

## Configuración del entorno y ejecución

### Requisitos previos
- **Java 11** (recomendado para Spark 3.5.1)
- **Apache Spark 3.5.1** instalado (ajustar la ruta en el Makefile)

Al tener otra versión de Java y Spark no funciona, es necesario __cambiar de versión de Java__. En nuestro caso usamos la versión 11:
```sh
sudo apt install openjdk-11-jdk 
sudo update-alternatives --config java
sudo update-alternatives --config javac
java -version   # Debe mostrar Java 11
javac -version  # Debe mostrar Java 11
```

### Compilación y ejecución

Para compilar:

```sh
make build
```

Para limpiar los archivos compilados:
```sh
make clean
```

#### Modos de ejecución
- Para imprimir los artículos (modo clásico, secuencial):
  ```sh
  make run-articles
  ```
- Para contar entidades nombradas usando Spark (paralelo):
  ```sh
  make run
  ```

## Decisiones de diseño
- Se implementó soporte para feeds RSS y Atom, detectando el tipo automáticamente. Nueva clase AtomParser
- El procesamiento de entidades nombradas se realiza en paralelo usando Spark, pero la impresión de artículos es secuencial.
- Se filtran URLs, HTML y basura antes de contar entidades.
- Ademas implementamos la funcion `fetchAllArticles` lo que nos permite no repetir tanto codigo.

## Conceptos importantes
### 1. **Flujo de la aplicación**
- **Lectura de suscripciones:** Se lee `config/subscriptions.json` usando `SubscriptionParser`, generando una lista de feeds a consultar.
- **Descarga y parseo:** Cada feed se descarga (HTTP) y se parsea (RSS/Atom) en paralelo usando Spark. El reparto de tareas es el siguiente:
  - **FeedReaderMain** coordina el flujo general y decide el modo de ejecución (artículos o entidades).
  - **SubscriptionParser** transforma el archivo de configuración en objetos de suscripción.
  - **httpRequester** se encarga de realizar las descargas HTTP de cada feed.
  - **RssParser** y **AtomParser** parsean el contenido XML según el tipo de feed detectado.
  - **Spark** reparte la descarga y el parseo de cada feed entre distintos workers (uno por feed), y luego reparte el procesamiento de entidades nombradas entre los artículos (uno por artículo).
  - **Article** encapsula la lógica de extracción de entidades nombradas de cada artículo.
- **Procesamiento:** Los artículos se procesan para extraer entidades nombradas usando heurísticas.
- **Salida:** Se imprime la lista de artículos o el conteo de entidades nombradas, según el modo de ejecución.

### 2. **¿Por qué usar Apache Spark?**
- Permite procesar múltiples feeds en paralelo, acelerando la descarga y análisis de grandes cantidades de datos.
- Facilita el escalado a conjuntos de datos más grandes o a clusters si fuera necesario.

### 3. **Ventajas y desventajas de Spark**
**Ventajas:**
- Paralelismo automático y eficiente.
- API sencilla para procesamiento distribuido.
- Escalabilidad.

**Desventajas:**
- Sobrecarga para proyectos pequeños. En el caso del lab anterior con solamente dos feeds, lo hacia mas lento.
- Serialización de objetos puede ser problemática si no se cuida.

### 4. **Inversión de control**
- El concepto de inversión de control se aplica principalmente al usar Apache Spark. En vez de que el desarrollador controle directamente el ciclo de procesamiento de cada feed y artículo, se delega ese control al framework de Spark.
- El desarrollador define las transformaciones y acciones (por ejemplo, cómo parsear feeds, cómo extraer entidades), pero Spark decide cómo y cuándo ejecutar cada tarea en paralelo, distribuyendo el trabajo entre los distintos workers.
- Así, el control del flujo de ejecución (descarga, parseo y procesamiento en paralelo) lo tiene Spark, no el código del usuario. El desarrollador deja de controlar directamente la iteración sobre feeds y artículos, y la asignación de tareas a los hilos o procesos.
- El usuario solo describe "qué" hacer con los datos, y Spark se encarga del "cómo" y "cuándo" hacerlo, gestionando la concurrencia, la distribución y la recolección de resultados.

### 5. **¿Considera que Spark requiere que el código original tenga una integración tight vs loose coupling?**
- Spark permite y favorece un diseño de **loose coupling** (bajo acoplamiento). El código de negocio (parseo, heurísticas, lógica de entidades) puede estar separado y ser independiente del framework de procesamiento distribuido.
- Solo las partes que interactúan directamente con Spark (creación de RDDs, transformaciones y acciones) dependen de su API. El resto del sistema puede mantenerse modular y reutilizable.
- Esto facilita el mantenimiento y la posibilidad de cambiar el framework de procesamiento en el futuro, ya que la lógica principal no está fuertemente acoplada a Spark.

### 6. **Impacto de Spark en la estructura del código**
- No fue necesario realizar cambios significativos en los métodos o clases para que sean serializables, ya que el modelo de datos y la lógica ya eran compatibles con Spark.
- Un cambio que hicimos fue implementar la funcion `fetchAllArticles` para modularizar parte del codigo del lab2.
- El único cambio estructural relevante fue la creación de la clase `AtomParser` para soportar feeds Atom, pero esto no fue debido al impacto de Spark sino a los feeds de subscriptions.

