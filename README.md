# Paradigmas de la Programación — Haskell, Java y Apache Spark

Trabajos grupales de **Paradigmas de la Programación (FAMAF–UNC, 2025)**, correspondientes a tres enfoques de programación.

| Laboratorio | Proyecto | Tecnologías |
| --- | --- | --- |
| `paradigmas25-g10-lab1` | Lenguaje específico de dominio (DSL) para componer dibujos geométricos y generar imágenes/fractales | Haskell, GHC, Gloss, funciones de orden superior, `foldDib` |
| `paradigmas25-g10-lab2` | **FeedReader**: lectura de feeds RSS y Reddit, análisis de artículos y detección heurística de entidades nombradas | Java, POO, JSON, HTTP |
| `paradigmas25-g10-lab3` | Extensión de FeedReader para procesamiento de feeds RSS/Atom y entidades nombradas con **Apache Spark** | Java 11, Spark 3.5.1, paralelismo |

## Ejecución (a partir del respaldo original)

**Lab 1 — Haskell:** instalar GHC y Gloss. Dentro de `paradigmas25-g10-lab1/`:

```bash
ghc -package gloss Main.hs
./Main
```

El ejemplo predeterminado usa una composición tipo Escher. El respaldo también tiene `instrucciones.md` para modificar `Main.hs` y mostrar una bandera o figuras fractales.

**Lab 2 — Java:** desde `paradigmas25-g10-lab2/`, con un JDK instalado:

```bash
mkdir -p bin
find src -name '*.java' > sources.txt
javac -d bin -cp 'lib/json-20240303.jar' @sources.txt
java -cp 'bin:lib/json-20240303.jar' FeedReaderMain
java -cp 'bin:lib/json-20240303.jar' FeedReaderMain -ne
```

`-ne` ejecuta el modo de entidades nombradas. Los comandos de classpath corresponden a Linux/macOS.

**Lab 3 — Java + Spark:** el README original indica Java 11 y Spark 3.5.1. El Makefile utiliza una ruta local que se debe sobrescribir:

```bash
make SPARK_FOLDER=/ruta/a/spark-3.5.1-bin-hadoop3 build
make SPARK_FOLDER=/ruta/a/spark-3.5.1-bin-hadoop3 run-articles
make SPARK_FOLDER=/ruta/a/spark-3.5.1-bin-hadoop3 run
```

Los lectores RSS/Atom dependen de fuentes externas y su disponibilidad puede cambiar. No se verificó la ejecución completa en una instalación limpia.

## Estado de los archivos en GitHub

**Importante:** los directorios de los tres laboratorios están actualmente registrados en GitHub como referencias `gitlink` (no como archivos navegables). **Se recuperaron los códigos fuente en el ZIP local de los laboratorios**, pero aún hace falta subirlos como archivos normales. Hasta entonces, los ejemplos de ejecución de arriba describen el respaldo original, no el contenido accesible al clonar este repositorio.

Tras publicar las carpetas, cada laboratorio podrá consultarse desde GitHub con su código y su README original.
