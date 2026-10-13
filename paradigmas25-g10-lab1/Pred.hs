module Pred where

import Dibujo

type Pred a = a -> Bool

--Para la definiciones de la funciones de este modulo, no pueden utilizar
--pattern-matching, sino alto orden a traves de la funcion foldDib, mapDib 

-- Dado un predicado sobre básicas, cambiar todas las que satisfacen
-- el predicado por el resultado de llamar a la función indicada por el
-- segundo argumento con dicha figura.
-- Por ejemplo, `cambiar (== Triangulo) (\x -> Rotar (Basica x))` rota
-- todos los triángulos.
cambiar :: Pred a -> (a -> Dibujo a) -> Dibujo a -> Dibujo a
cambiar p f = foldDib (\x -> if p x then f x else Basica x) rotar rotar45 espejar juntar apilar encimar

-- Alguna básica satisface el predicado.
anyDib :: Pred a -> Dibujo a -> Bool
anyDib p = foldDib
  (\x -> p x)                          -- Si es Basica, aplico el predicado
  id                                   -- Si es Rotar, da igual, no cambia el resultado
  id                                   -- Si es Rotar45, da igual no cambia el resultado
  id                                   -- Si es Espejar, da igual no cambia el resultado
  (\_ _ b1 b2 -> b1 || b2)             -- Si es Apilar, con OR busco si alguna de las dos figuras cumple el predicado
  (\_ _ b1 b2 -> b1 || b2)             -- Si es Juntar, con OR busco si alguna de las dos figuras cumple el predicado
  (\b1 b2 -> b1 || b2)                 -- Si es Encimar, con OR busco si alguna de las dos figuras cumple el predicado


-- Todas las básicas satisfacen el predicado.
allDib :: Pred a -> Dibujo a -> Bool
allDib p = foldDib
  (\x -> p x)                          -- Si es Basica, aplico el predicado
  id                                   -- Si es Rotar, da igual, no cambia el resultado
  id                                   -- Si es Rotar45, da igual no cambia el resultado
  id                                   -- Si es Espejar, da igual no cambia el resultado
  (\_ _ b1 b2 -> b1 && b2)             -- Si es Apilar, con AND busco si alguna de las dos figuras cumple el predicado
  (\_ _ b1 b2 -> b1 && b2)             -- Si es Juntar, con AND busco si alguna de las dos figuras cumple el predicado
  (\b1 b2 -> b1 && b2)                 -- Si es Encimar, con AND busco si alguna de las dos figuras cumple el predicado


-- Función auxiliar para verificar 4 rotaciones
esRot360aux :: Dibujo a -> Bool
esRot360aux d = case d of
  Rotar (Rotar (Rotar (Rotar _))) -> True
  _ -> False

-- Función auxiliar para verificar 2 espejados
esFlip2aux :: Dibujo a -> Bool
esFlip2aux d = case d of
  Espejar (Espejar _) -> True
  _ -> False

-- Verificar si una figura tiene 4 rotaciones seguidas
esRot360 :: Pred (Dibujo a)
esRot360 d = foldDib
  (const False)  -- Si es Basica, no puede ser 4 rotaciones seguidas
  (\x -> esRot360aux d)    -- Usamos la función auxiliar para verificar 4 rotaciones
  (const False)  -- Si es Rotar45, no puede ser una rotación de 360 grados
  (const False)  -- Si es Espejar, no puede ser una rotación de 360 grados
  (\_ _ b1 b2 -> b1 || b2)  -- Si es Apilar, con OR buscamos si alguna de las dos figuras cumple la condición
  (\_ _ b1 b2 -> b1 || b2)  -- Si es Juntar, con OR buscamos si alguna de las dos figuras cumple la condición
  (\b1 b2 -> b1 || b2)
  d

-- Verificar si una figura tiene 2 espejados consecutivos
esFlip2 :: Pred (Dibujo a)
esFlip2 d = foldDib
  (\_ -> False)  -- Basica no es flip
  (\_ -> False)  -- Rotar no es flip
  (\_ -> False)  -- Rotar45 no es flip
  (\x -> esFlip2aux d)  -- Usamos la función auxiliar para verificar 2 espejados
  (\_ _ b1 b2 -> b1 || b2)  -- Si alguna apilada tiene flip
  (\_ _ b1 b2 -> b1 || b2)  -- Si alguna junta tiene flip
  (\b1 b2 -> b1 || b2) 
  d

data Superfluo = RotacionSuperflua | FlipSuperfluo

---- Chequea si el dibujo tiene una rotacion superflua
errorRotacion :: Dibujo a -> [Superfluo]
errorRotacion d = if esRot360 d then [RotacionSuperflua] else [] 

-- Chequea si el dibujo tiene un flip superfluo
errorFlip :: Dibujo a -> [Superfluo]
errorFlip d = if esFlip2 d then [FlipSuperfluo] else []

-- Aplica todos los chequeos y acumula todos los errores, y
-- sólo devuelve la figura si no hubo ningún error.
checkSuperfluo :: Dibujo a -> Either [Superfluo] (Dibujo a)
checkSuperfluo d =
  if null (errorRotacion d ++ errorFlip d)
    then Right d
    else Left (errorRotacion d ++ errorFlip d)

