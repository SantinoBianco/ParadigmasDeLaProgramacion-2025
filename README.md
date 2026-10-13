# Paradigmas de la Programación — Haskell, Java y Spark

Tres laboratorios grupales realizados en **FAMAF, Universidad Nacional de Córdoba (2025)**. Este repositorio contiene ahora el **código fuente navegable** de los tres proyectos, junto con sus archivos de configuración y documentación.

| Laboratorio | Proyecto | Tecnologías |
| --- | --- | --- |
| [Lab 1 — Programación funcional](./paradigmas25-g10-lab1) | DSL para componer dibujos geométricos: rotación, simetrías, superposición y gráficos como Escher | Haskell, GHC, Gloss |
| [Lab 2 — Programación orientada a objetos](./paradigmas25-g10-lab2) | **FeedReader**: consulta de feeds RSS/Reddit y reconocimiento heurístico de entidades nombradas | Java, JSON, HTTP, POO |
| [Lab 3 — Procesamiento paralelo](./paradigmas25-g10-lab3) | FeedReader extendido: soporte RSS/Atom y procesamiento paralelo de feeds y artículos | Java 11, Apache Spark 3.5.1 |

> **Nota:** este repositorio mantiene el nombre de la materia, pero las carpetas corresponden a proyectos de desarrollo concretos. Cada laboratorio conserva su propio README y código, recuperados del respaldo del equipo.

## Cómo ejecutarlos

Clonar:

```bash
git clone https://github.com/SantinoBianco/ParadigmasDeLaProgramacion-2025.git
cd ParadigmasDeLaProgramacion-2025
```

### Lab 1 — Haskell / Gloss

Requiere **GHC** y el paquete **Gloss** instalado.

```bash
cd paradigmas25-g10-lab1
ghc -package gloss Main.hs
./Main
```

El archivo [`instrucciones.md`](./paradigmas25-g10-lab1/instrucciones.md) detalla otras figuras y configuraciones.

### Lab 2 — Java / FeedReader

Requiere **JDK**. El proyecto incluye la biblioteca JSON que usa el código.

```bash
cd paradigmas25-g10-lab2
mkdir -p bin
find src -name '*.java' > sources.txt
javac -d bin -cp 'lib/json-20240303.jar' @sources.txt
java -cp 'bin:lib/json-20240303.jar' FeedReaderMain
```

Para el modo de detección de entidades nombradas:

```bash
java -cp 'bin:lib/json-20240303.jar' FeedReaderMain -ne
```

Las instrucciones de classpath están escritas para **Linux/macOS**; en Windows se utiliza `;` como separador.

### Lab 3 — Java / Apache Spark

Requiere **Java 11** y **Spark 3.5.1**. Dentro de `paradigmas25-g10-lab3/`, el Makefile permite indicar la ubicación de Spark:

```bash
make SPARK_FOLDER=/ruta/a/spark-3.5.1-bin-hadoop3 build
make SPARK_FOLDER=/ruta/a/spark-3.5.1-bin-hadoop3 run-articles
make SPARK_FOLDER=/ruta/a/spark-3.5.1-bin-hadoop3 run
```

- `run-articles`: procesa e imprime artículos.
- `run`: detecta y contabiliza entidades nombradas usando Spark.

Consultá el [README del Lab 3](./paradigmas25-g10-lab3/README.md) para las decisiones de diseño y la configuración.

## Conceptos aplicados

- Diseño de un pequeño **lenguaje de dominio específico (DSL)** e interpretación de sus expresiones.
- **Abstracción, herencia, polimorfismo** y separación de responsabilidades en Java.
- **Procesamiento paralelo y distribuido** con Spark, RSS/Atom y extracción de información.

**Estado:** código de los tres laboratorios publicado en GitHub. Las instrucciones se basan en los archivos y READMEs originales; no se comprobó la ejecución completa en todas las plataformas ni la disponibilidad actual de los feeds externos.
