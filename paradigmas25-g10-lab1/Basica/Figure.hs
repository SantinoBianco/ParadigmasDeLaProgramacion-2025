module Basica.Figure where

import Dibujo
import Interp
import Graphics.Gloss
import Graphics.Gloss.Data.Vector
import qualified Graphics.Gloss.Data.Point.Arithmetic as V

data Basica = Triangulo Int deriving (Show, Eq)  -- El Int representa el nivel para cambiar el color

-- Interpretación: Color distinto según el nivel
interpBas :: Basica -> ImagenFlotante
interpBas (Triangulo n) d w h =
  color (nivelColor n) $
    polygon [d, d V.+ w, d V.+ h]

-- Asigna colores distintos según la profundidad
nivelColor :: Int -> Color
nivelColor 0 = blue
nivelColor 1 = makeColorI 5 180 180 255
nivelColor 2 = makeColorI 120 140 140 255
nivelColor 3 = makeColorI 4 230 100 255
nivelColor 4 = makeColorI 89 60 123 255
nivelColor 5 = makeColorI 255 30 30 255
nivelColor _ = red  -- Niveles más profundos en rojo oscuro

-- Fractal de triangulos 
fractal :: Int -> Dibujo Basica
fractal 0 = basica (Triangulo 0)
fractal n =
  let sub = fractal (n - 1)
  in encimar (basica (Triangulo n))
             (cuarteto sub sub sub sub)  -- 4 copias para llenar bien el espacio
