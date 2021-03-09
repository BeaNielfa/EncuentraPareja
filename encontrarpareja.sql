-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-03-2021 a las 20:47:41
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `encontrarpareja`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `amigos`
--

CREATE TABLE `amigos` (
  `id` int(11) NOT NULL,
  `idUser1` int(11) NOT NULL,
  `idUser2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `amigos`
--

INSERT INTO `amigos` (`id`, `idUser1`, `idUser2`) VALUES
(1, 1, 5),
(2, 5, 1),
(3, 13, 5),
(4, 5, 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `likes`
--

CREATE TABLE `likes` (
  `id` int(11) NOT NULL,
  `idUser1` int(11) NOT NULL,
  `idUser2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `likes`
--

INSERT INTO `likes` (`id`, `idUser1`, `idUser2`) VALUES
(1, 5, 1),
(2, 15, 11),
(3, 15, 4),
(4, 15, 5),
(5, 15, 13),
(6, 15, 9),
(10, 13, 5),
(11, 1, 5),
(13, 5, 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `preferencias`
--

CREATE TABLE `preferencias` (
  `id` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `relacion` varchar(20) NOT NULL,
  `deporte` int(11) NOT NULL,
  `politica` int(11) NOT NULL,
  `arte` int(11) NOT NULL,
  `hijos` varchar(20) NOT NULL,
  `interes` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `preferencias`
--

INSERT INTO `preferencias` (`id`, `idUser`, `relacion`, `deporte`, `politica`, `arte`, `hijos`, `interes`) VALUES
(1, 1, 'Esporadica', 70, 10, 50, 'Quiere', 'Hombres'),
(2, 4, 'Esporadica', 85, 30, 60, 'Quiere', 'Mujeres'),
(3, 5, 'Esporadica', 90, 5, 30, 'Quiere', 'Mujeres'),
(4, 6, 'Esporadica', 100, 70, 20, 'Tiene', 'Mujeres'),
(5, 7, 'Esporadica', 80, 80, 30, 'Tiene', 'Hombres'),
(6, 9, 'Seria', 70, 10, 50, 'Quiere', 'Hombres'),
(7, 10, 'Esporadica', 85, 15, 15, 'Quiere', 'Hombres'),
(8, 11, 'Esporadica', 60, 80, 50, 'Quiere', 'Ambos'),
(9, 12, 'Esporadica', 50, 60, 30, 'Quiere', 'Ambos'),
(10, 13, 'Esporadica', 30, 20, 50, 'Quiere', 'Hombres'),
(11, 14, 'Esporadica', 50, 10, 70, 'Quiere', 'Mujeres'),
(12, 15, 'Esporadica', 60, 90, 30, 'Quiere', 'Ambos'),
(13, 16, 'Seria', 60, 10, 60, 'Quiere', 'Mujeres'),
(14, 17, 'Esporadica', 65, 25, 31, 'Quiere', 'Mujeres'),
(15, 18, 'Seria', 25, 50, 60, 'Tiene', 'Hombres'),
(16, 19, 'Esporadica', 35, 15, 20, 'Ambos', 'Mujeres');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `privilegios`
--

CREATE TABLE `privilegios` (
  `id` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `activar` int(11) NOT NULL,
  `modificar` int(11) NOT NULL,
  `alta` int(11) NOT NULL,
  `baja` int(11) NOT NULL,
  `privilegios` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `privilegios`
--

INSERT INTO `privilegios` (`id`, `idUser`, `activar`, `modificar`, `alta`, `baja`, `privilegios`) VALUES
(1, 2, 1, 1, 1, 1, 1),
(2, 8, 1, 1, 0, 0, 0),
(3, 20, 1, 1, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rolsasignados`
--

CREATE TABLE `rolsasignados` (
  `id` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idRol` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `rolsasignados`
--

INSERT INTO `rolsasignados` (`id`, `idUser`, `idRol`) VALUES
(1, 1, 2),
(4, 2, 1),
(5, 4, 2),
(6, 5, 2),
(7, 6, 2),
(8, 7, 2),
(9, 8, 1),
(10, 9, 2),
(11, 10, 2),
(12, 11, 2),
(13, 12, 2),
(14, 13, 2),
(15, 14, 2),
(16, 15, 2),
(17, 16, 2),
(18, 17, 2),
(19, 18, 2),
(20, 19, 2),
(21, 20, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiporol`
--

CREATE TABLE `tiporol` (
  `id` int(11) NOT NULL,
  `descripcion` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tiporol`
--

INSERT INTO `tiporol` (`id`, `descripcion`) VALUES
(1, 'Admin'),
(2, 'User');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `contraseña` varchar(255) NOT NULL,
  `foto` blob DEFAULT NULL,
  `activado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `apellidos`, `email`, `contraseña`, `foto`, `activado`) VALUES
(1, 'Arancha', 'Golderos', 'arancha@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(2, 'Beatriz', 'Nielfa Naranjo', 'bea@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(4, 'Jonas', 'Lopez', 'jonas@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(5, 'Jorge', 'Muñoz', 'jorge@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(6, 'Antonio', 'Ruedas', 'antonio@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(7, 'Anita', 'Paz', 'ana@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(8, 'Kevin', 'Diez Gomez', 'kevin@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(9, 'Sheila', 'Nieva', 'sheila@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(10, 'Alba', 'Ruiz', 'alba@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(11, 'Adrian', 'Diez Gomez', 'adrian@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(12, 'Celia', 'Llanos', 'celia@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(13, 'Laura', 'Perez', 'laura@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(14, 'Santi', 'Nielfa', 'santi@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(15, 'Alberto', 'Muñoz', 'alberto@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(16, 'David', 'Madrid', 'david@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 0),
(17, 'Alvaro', 'Sanchez', 'alvaro@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(18, 'Paula', 'Zamorano', 'paula@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(19, 'Javier', 'Garcia', 'javier@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1),
(20, 'Lorena', 'Romero Naranjo', 'lorena@gmail.com', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', NULL, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `amigos`
--
ALTER TABLE `amigos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idUser1` (`idUser1`),
  ADD KEY `idUser2` (`idUser2`);

--
-- Indices de la tabla `likes`
--
ALTER TABLE `likes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idUser1` (`idUser1`),
  ADD KEY `idUser2` (`idUser2`);

--
-- Indices de la tabla `preferencias`
--
ALTER TABLE `preferencias`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idUser` (`idUser`);

--
-- Indices de la tabla `privilegios`
--
ALTER TABLE `privilegios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idUser` (`idUser`);

--
-- Indices de la tabla `rolsasignados`
--
ALTER TABLE `rolsasignados`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idUser` (`idUser`),
  ADD KEY `idRol` (`idRol`);

--
-- Indices de la tabla `tiporol`
--
ALTER TABLE `tiporol`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `amigos`
--
ALTER TABLE `amigos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `likes`
--
ALTER TABLE `likes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `preferencias`
--
ALTER TABLE `preferencias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `privilegios`
--
ALTER TABLE `privilegios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `rolsasignados`
--
ALTER TABLE `rolsasignados`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `tiporol`
--
ALTER TABLE `tiporol`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `amigos`
--
ALTER TABLE `amigos`
  ADD CONSTRAINT `amigos_ibfk_1` FOREIGN KEY (`idUser1`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `amigos_ibfk_2` FOREIGN KEY (`idUser2`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `likes`
--
ALTER TABLE `likes`
  ADD CONSTRAINT `likes_ibfk_1` FOREIGN KEY (`idUser1`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `likes_ibfk_2` FOREIGN KEY (`idUser2`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `preferencias`
--
ALTER TABLE `preferencias`
  ADD CONSTRAINT `preferencias_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `privilegios`
--
ALTER TABLE `privilegios`
  ADD CONSTRAINT `privilegios_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `rolsasignados`
--
ALTER TABLE `rolsasignados`
  ADD CONSTRAINT `rolsasignados_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `rolsasignados_ibfk_2` FOREIGN KEY (`idRol`) REFERENCES `tiporol` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
