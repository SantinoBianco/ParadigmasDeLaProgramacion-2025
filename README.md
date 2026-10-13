# Paradigmas de la Programación — laboratorios (2025)

Repositorio de apoyo para los trabajos prácticos de **Paradigmas de la Programación (2025)**, organizado en tres laboratorios del **grupo 10**.

> **Estado del código publicado:** este repositorio **no contiene el código fuente de los laboratorios como carpetas normales**. Cada entrada `paradigmas25-g10-labN` es una **referencia Git a un commit de otro repositorio** (`gitlink`, modo `160000`). No hay un archivo `.gitmodules` que indique dónde clonar los repositorios originales. Por eso, no se pueden inspeccionar ni ejecutar los trabajos desde este clon tal como está.

## Índice del contenido registrado

| Entrada | Tipo de contenido disponible |
| --- | --- |
| `paradigmas25-g10-lab1` | Referencia Git al trabajo del laboratorio 1 |
| `paradigmas25-g10-lab2` | Referencia Git al trabajo del laboratorio 2 |
| `paradigmas25-g10-lab3` | Referencia Git al trabajo del laboratorio 3 |

Las referencias conservan identificadores de commits, pero **sin la URL del repositorio de origen no permiten acceder al contenido directamente**. No se atribuyen lenguajes, algoritmos ni funcionalidades concretas que no puedan comprobarse con estos archivos.

## Cómo inspeccionar el repositorio

```bash
git clone https://github.com/SantinoBianco/ParadigmasDeLaProgramacion-2025.git
cd ParadigmasDeLaProgramacion-2025
git ls-tree HEAD
```

La última instrucción muestra las tres entradas con el tipo `commit` y los identificadores asociados.

**Importante:** ejecutar `git submodule update --init --recursive` por sí solo **no reconstruye los laboratorios**, porque este repositorio carece de la configuración `.gitmodules` con sus direcciones de origen.

## Qué falta para que los proyectos se puedan revisar

Para convertir este repositorio en un portafolio navegable:

1. **Localizar los repositorios originales** de los tres laboratorios del grupo 10.
2. **Incorporar su código fuente** en directorios versionados (si las condiciones del curso y del equipo lo permiten) **o** configurar submódulos Git correctos con sus URLs.
3. Agregar a cada laboratorio su descripción, tecnologías, requisitos y comandos de ejecución **a partir del código real**.

Hasta que eso se complete, este README es un **índice fiel a las referencias disponibles**, no una guía de ejecución funcional.

---

**Contexto:** laboratorios académicos de Paradigmas de la Programación. Este repositorio puede consultarse como registro de trabajos, pero **las entregas no están reproducibles aquí actualmente**.
