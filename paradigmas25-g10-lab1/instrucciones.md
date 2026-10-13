# Instrucciones

## Para `Bandera.hs`
1. **Descripción**: Genera la bandera Argentina, con un mate y una pelota.
2. **Requisitos previos**:
   - Asegúrate de tener instalado GHC.
   - Tener instalado Gloss.
3. **Pasos para ejecutar**:
   - Se deben realizar modificaciones en Main.hs
   1. Cambiar el import
    ```Haskell
    import qualified Basica.Bandera as E
     ```
   2. En las configuraciones cambiar:
    ```Haskell
    ej ancho alto = Conf {
                basic = E.interpBandera
              , fig = E.banderaArgentina
              , width = ancho
              , height = alto
              , r = id
              }
     ```
    ```Haskell
    ejCentro :: Float -> Float -> Conf E.Bandera
    ejCentro ancho alto = Conf {
                basic = E.interpBandera,
                fig = E.banderaArgentina,
                width = ancho,
                height = alto,
                r = moverCentro ancho alto
              }
     ```

   3. Por ultimo modificar inicial.
    ```Haskell
    inicial :: IO (Conf E.Bandera) -> IO ()
     ```

   - Compilar con ghc.
     ```bash
     ghc Main.hs
     ```
   - Ejecuta el programa con:
     ```bash
     ./Main
     ```


## Para `Figure.hs`
1. **Descripción**: Crea una figura fractal utilizando unicamente triangulos.
2. **Requisitos previos**:
   - Asegúrate de tener instalado GHC.
   - Tener instalado Gloss
3. **Pasos para ejecutar**:
   - Se deben realizar modificaciones en `Main.hs`:
     1. Cambiar el import:
        ```haskell
        import qualified Basica.Figure as E
        ```
     2. En las configuraciones, cambiar:
        ```haskell
        ej ancho alto = Conf {
                    basic = E.interpBas,
                    fig = E.fractal 5,  -- Cambiar a la figura fractal
                    width = ancho,
                    height = alto,
                    r = id
                  }
        ```
        ```haskell
        ejCentro :: Float -> Float -> Conf E.Basica
        ejCentro ancho alto = Conf {
                    basic = E.interpBas,
                    fig = E.fractal 5,  -- Cambiar a la figura fractal
                    width = ancho,
                    height = alto,
                    r = moverCentro ancho alto
                  }
        ```
     3. Por último, modificar `inicial` para que use `Conf E.Basica`:
        ```haskell
        inicial :: IO (Conf E.Basica) -> IO ()
        ```
   - Compilar el archivo [Main.hs](http://_vscodecontentref_/2) con el comando:
     ```bash
     ghc Main.hs
     ```
   - Ejecutar el programa con:
     ```bash
     ./Main
     ```
4. **Notas adicionales**:
   - Puedes ajustar el nivel del fractal en `fig = E.fractal n`, donde `n` es la profundidad deseada.

## Comentarios

En nuestra experiencia, el laboratorio no presentó una gran dificultad una vez que logramos comprender el posicionamiento de las figuras para completar funciones como `cuarteto` y `encimar`. Estas funciones fueron clave para estructurar correctamente los dibujos y entender cómo combinar las figuras básicas.

Sin embargo, el concepto de `foldDib` fue un desafío inicial. Nos tomó tiempo entender su propósito y cómo implementarlo correctamente, pero creemos que logramos una buena implementación que funciona como se esperaba. Cave aclarar que estuvimos presentes en la clase de Facundo Bustos, donde se explicó en detalle esta función y su importancia en el diseño del proyecto. La clase nos ayudó a aclarar dudas y a aplicar `foldDib` de manera efectiva.

Además, utilizamos `foldDib` correctamente en el módulo `Pred`, lo que nos permitió realizar funciones sin recurrir al pattern matching. Esto simplificó el código y lo hizo más elegante. Posteriormente, en el módulo `Interp` y en los módulos para dibujar, fue fundamental leer y comprender las librerías de Gloss, especialmente en lo relacionado con aritmética, vectores y colores. Estas librerías nos proporcionaron las herramientas necesarias para interpretar y manipular las figuras de manera precisa.

En general, sentimos que el laboratorio fue una buena experiencia en el paradigma funcional. Nos permitió profundizar en conceptos clave funciones de alto orden, el uso de librerías externas y el trabajo en grupo.