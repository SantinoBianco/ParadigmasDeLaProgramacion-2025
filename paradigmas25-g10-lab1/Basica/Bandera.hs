module Basica.Bandera (
    Bandera,
    interpBandera,
    banderaArgentina
) where

import Graphics.Gloss
import qualified Graphics.Gloss.Data.Point.Arithmetic as V
import Dibujo
import Interp

-- Definición del tipo Bandera
data Bandera = FranjaCeleste | FranjaBlanca | Sol | Pelota | Mate deriving (Eq, Show)

-- Función para calcular la magnitud de un vector
magnitud :: Vector -> Float
magnitud (x, y) = sqrt (x * x + y * y)

-- Interpretación básica para Bandera
interpBandera :: Bandera -> ImagenFlotante
interpBandera FranjaCeleste d w h = color (makeColorI 116 172 223 255) $ Polygon [
    d,
    d V.+ w,
    d V.+ w V.+ h,
    d V.+ h
  ]
interpBandera FranjaBlanca d w h = color white $ Polygon [
    d,
    d V.+ w,
    d V.+ w V.+ h,
    d V.+ h
  ]
interpBandera Sol d w h = color yellow $ translate (fst d + fst w / 2) (snd d + snd h / 2) $ circleSolid (snd h / 2)
interpBandera Pelota d w h = pictures [
    color black $ translate (fst d + fst w * 0.25) (snd d + snd h * 0.25) $ circleSolid (snd h * 0.3),
    color white $ translate (fst d + fst w * 0.25) (snd d + snd h * 0.25) $ hexagonos (snd h * 0.05) 
  ]
interpBandera Mate d w h = pictures [
    color black $ translate (fst d + fst w * 0.75) (snd d + snd h * 0.25 + snd h * 0.1) $ rotate 45 $ rectangleSolid 3 (snd h * 0.5), 
    color (makeColorI 139 69 19 255) $ translate (fst d + fst w * 0.75) (snd d + snd h * 0.25) $ circleSolid (snd h * 0.2)
  ]

-- Función para dibujar hexágonos en la pelota
hexagonos :: Float -> Picture
hexagonos radio = pictures $ map (translateHex radio) posiciones
  where
    posiciones = [(0, 0), (radio * 2, radio * 2), (-radio * 2, radio * 2), (radio * 2, -radio * 2), (-radio * 2, -radio * 2)]
    translateHex r (x, y) = translate x y $ polygon (hexagonPath (r * 1.2)) 
    hexagonPath r = [(r * cos (pi * 2 * i / 6), r * sin (pi * 2 * i / 6)) | i <- [0..5]]

-- Crear una franja celeste
franjaCeleste :: Dibujo Bandera
franjaCeleste = Basica FranjaCeleste

-- Crear una franja blanca
franjaBlanca :: Dibujo Bandera
franjaBlanca = Basica FranjaBlanca

-- Crear el sol
sol :: Dibujo Bandera
sol = Basica Sol

-- Crear la pelota de fútbol
pelota :: Dibujo Bandera
pelota = Basica Pelota

-- Crear el mate
mate :: Dibujo Bandera
mate = Basica Mate

-- Crear la bandera Argentina con pelota y mate
banderaArgentina :: Dibujo Bandera
banderaArgentina = apilar 1 2 franjaCeleste (apilar 1 1 (encimar franjaBlanca sol) (encimar franjaCeleste (juntar 1 1 pelota mate)))