-- Sacar del esqueleto final!
module Interp where
import Graphics.Gloss
import Graphics.Gloss.Data.Vector
import qualified Graphics.Gloss.Data.Point.Arithmetic as V

import Dibujo

-- Gloss provee el tipo Vector y Picture.
type ImagenFlotante = Vector -> Vector -> Vector -> Picture
type Interpretacion a = a -> ImagenFlotante

mitad :: Vector -> Vector
mitad = (0.5 V.*)

-- Interpretaciones de los constructores de Dibujo

--interpreta el operador de rotacion
interp_rotar :: ImagenFlotante -> ImagenFlotante
interp_rotar img d w h = img (d V.+ w) h (V.negate w)

--interpreta el operador de espejar
interp_espejar :: ImagenFlotante -> ImagenFlotante
interp_espejar img d w h = img (d V.+ w) (V.negate w) h

--interpreta el operador de rotacion 45
interp_rotar45 :: ImagenFlotante -> ImagenFlotante
interp_rotar45 img d w h = img (d V.+ mitad(w V.+ h)) (mitad ((w V.+ h))) (mitad(((h V.- w))))

-- Interpreta el operador de apilar
interp_apilar :: Float -> Float -> ImagenFlotante -> ImagenFlotante -> ImagenFlotante 
interp_apilar m n f g d w h = Pictures [
    f d ((m / (m + n)) V.* w) h,
    g (d V.+ (m / (m + n)) V.* w) ((n / (m + n)) V.* w) h]

--Nota: Cambiamos los Int a float para que funcione el interp con el foldDib.

-- Interpreta el operador de juntar
interp_juntar :: Float -> Float -> ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_juntar m n f g d w h = Pictures [
    f (d V.+ (n / (m + n)) V.* h) w ((m / (m + n)) V.* h),
    g d w ((n / (m + n)) V.* h)]

--interpreta el operador de encimar
interp_encimar :: ImagenFlotante -> ImagenFlotante -> ImagenFlotante
interp_encimar f g d w h = Pictures[f d w h, g d w h]


-- Interpreta cualquier expresión del tipo Dibujo a
interp :: Interpretacion a -> Dibujo a -> ImagenFlotante
interp interpretacion = foldDib
  interpretacion       -- Para figuras básicas
  interp_rotar         -- Para rotar
  interp_rotar45       -- Para rotar 45 grados
  interp_espejar       -- Para espejar
  interp_juntar        -- Para juntar
  interp_apilar        -- Para apilar
  interp_encimar       -- Para encimar
  