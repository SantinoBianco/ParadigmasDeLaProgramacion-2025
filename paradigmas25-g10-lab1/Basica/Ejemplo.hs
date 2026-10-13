module Basica.Ejemplo where

import Dibujo
import Interp
import Basica.Comun

-- Definimos un tipo más significativo para Basica
data Basica = FormaF

-- Ejemplo de dibujo utilizando una figura de Basica
ejemplo :: Dibujo Basica
ejemplo = Rotar45 (Basica FormaF)

-- Interpretación de las figuras básicas en imágenes
interpBas :: Basica -> ImagenFlotante
interpBas FormaF = formaF