module Dibujo (
    Dibujo(..),
    basica,
    rotar,
    rotar45,
    espejar,
    apilar,
    juntar,
    encimar,
    comp,
    r180,
    r270,
    (.-.),
    (///),
    (^^^),
    cuarteto,
    encimar4,
    ciclar,
    pureDib,
    mapDib,
    foldDib
) where

-- Definir el lenguaje via constructores de tipo
data Dibujo a = Basica a 
              | Rotar (Dibujo a) 
              | Rotar45 (Dibujo a)  
              | Espejar (Dibujo a) 
              | Apilar Float Float (Dibujo a) (Dibujo a) 
              | Juntar Float Float (Dibujo a) (Dibujo a)
              | Encimar (Dibujo a) (Dibujo a)
              deriving(Show, Eq)

-- crear figuras basicas

basica :: a -> Dibujo a
basica = Basica

rotar :: Dibujo a -> Dibujo a 
rotar = Rotar

rotar45 :: Dibujo a -> Dibujo a
rotar45 = Rotar45

espejar :: Dibujo a -> Dibujo a
espejar = Espejar

apilar :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
apilar = Apilar

juntar :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
juntar = Juntar

encimar :: Dibujo a -> Dibujo a -> Dibujo a
encimar = Encimar

-- Composición n-veces de una función con sí misma.
comp :: (a -> a) -> Int -> a -> a
comp f n x 
       | n < 0 = error "No se puede componer negativamente"
       | n == 0 = x
       | otherwise = comp f (n - 1) (f x)

-- Rotaciones de múltiplos de 90.
r180 :: Dibujo a -> Dibujo a
r180 = rotar . rotar

r270 :: Dibujo a -> Dibujo a
r270 = rotar . rotar . rotar

-- Pone una figura sobre la otra, ambas ocupan el mismo espacio.
(.-.) :: Dibujo a -> Dibujo a -> Dibujo a
(.-.) = apilar 0.5 0.5

-- Pone una figura al lado de la otra, ambas ocupan el mismo espacio.
(///) :: Dibujo a -> Dibujo a -> Dibujo a
(///) = juntar 0.5 0.5

-- Superpone una figura con otra.
(^^^) :: Dibujo a -> Dibujo a -> Dibujo a
(^^^) = encimar

-- Dadas cuatro dibujos las ubica en los cuatro cuadrantes.
cuarteto :: Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a
cuarteto d1 d2 d3 d4 = apilar 1 1 (juntar 1 1 d1 d2) (juntar 1 1 d3 d4)

-- Una dibujo repetido con las cuatro rotaciones, superpuestas.
encimar4 :: Dibujo a -> Dibujo a
encimar4 d = d ^^^ (rotar d) ^^^ (r180 d) ^^^ (r270 d)

-- Cuadrado con la misma figura rotada i * 90, para i ∈ {0, ..., 3}.
-- No confundir con encimar4!
ciclar :: Dibujo a -> Dibujo a
ciclar d = cuarteto d (rotar d) (r180 d) (r270 d)

-- Transfomar un valor de tipo a como una Basica.
pureDib :: a -> Dibujo a
pureDib = basica 

-- map para nuestro lenguaje.
mapDib :: (a -> b) -> Dibujo a -> Dibujo b
mapDib f (Basica d) = Basica (f d)
mapDib f (Rotar d) = Rotar (mapDib f d)
mapDib f (Rotar45 d) = Rotar45 (mapDib f d)
mapDib f (Espejar d) = Espejar (mapDib f d)
mapDib f (Apilar  a b d1 d2) = Apilar a b (mapDib f d1) (mapDib f d2)
mapDib f (Juntar a b d1 d2) = Juntar a b (mapDib f d1) (mapDib f d2)
mapDib f (Encimar d1 d2) = Encimar (mapDib f d1) (mapDib f d2)

-- Funcion de fold para Dibujos a
foldDib :: (a -> b) -> (b -> b) -> (b -> b) -> (b -> b) ->
       (Float -> Float -> b -> b -> b) -> 
       (Float -> Float -> b -> b -> b) -> 
       (b -> b -> b) ->
       Dibujo a -> b
foldDib fBasica fRotar fRotar45 fEspejar fApilar fJuntar fEncimar = fold
  where
    fold (Basica a) = fBasica a
    fold (Rotar a) = fRotar (fold a)
    fold (Rotar45 a) = fRotar45 (fold a)
    fold (Espejar a) = fEspejar (fold a)
    fold (Apilar x1 x2 a b) = fApilar x1 x2 (fold a) (fold b)
    fold (Juntar x1 x2 a b) = fJuntar x1 x2 (fold a) (fold b)
    fold (Encimar a b) = fEncimar (fold a) (fold b)





