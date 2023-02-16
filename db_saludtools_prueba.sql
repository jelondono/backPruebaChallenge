-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-02-2023 a las 01:10:34
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_saludtools_prueba`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gender`
--

CREATE TABLE `gender` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `gender`
--

INSERT INTO `gender` (`id`, `name`) VALUES
(1, 'Masculino'),
(2, 'Femenino');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medicines`
--

CREATE TABLE `medicines` (
  `ID` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `minAge` int(11) NOT NULL,
  `maxAge` int(11) NOT NULL,
  `singleGender` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `medicines`
--

INSERT INTO `medicines` (`ID`, `name`, `minAge`, `maxAge`, `singleGender`) VALUES
(1, 'Sildenafil', 18, 70, 1),
(2, 'Prostatil', 18, 70, 1),
(3, 'Tamoxifeno', 25, 80, 2),
(4, 'Raloxifeno', 25, 80, 2),
(5, 'Dolex niños', 1, 14, NULL),
(6, 'Dolex fortex', 15, 75, NULL),
(7, 'Atorvastatina', 18, 0, NULL),
(8, 'Ibuprofeno jarabe', 0, 10, NULL),
(9, 'Ibuprofeno', 10, 0, NULL),
(10, 'Paracetamol jarabe', 0, 10, NULL),
(11, 'Paracetamol', 10, 0, NULL),
(12, 'Naproxeno', 14, 0, NULL),
(13, 'Amoxicilina', 14, 0, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `patient`
--

CREATE TABLE `patient` (
  `id` bigint(20) NOT NULL,
  `birthdate` date NOT NULL,
  `gender_id` bigint(11) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `patient`
--

INSERT INTO `patient` (`id`, `birthdate`, `gender_id`, `lastname`, `name`) VALUES
(2, '2002-12-13', 1, 'Londoño', 'Juanes');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prescriptions`
--

CREATE TABLE `prescriptions` (
  `id` bigint(20) NOT NULL,
  `prescription_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `patient_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `prescriptions`
--

INSERT INTO `prescriptions` (`id`, `prescription_date`, `patient_id`) VALUES
(1, '2022-05-15 05:00:00', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prescription_medicines`
--

CREATE TABLE `prescription_medicines` (
  `id` bigint(20) NOT NULL,
  `prescription_id` bigint(20) NOT NULL,
  `medicine_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `prescription_medicines`
--

INSERT INTO `prescription_medicines` (`id`, `prescription_id`, `medicine_id`) VALUES
(1, 1, 1),
(3, 1, 7);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `gender`
--
ALTER TABLE `gender`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `medicines`
--
ALTER TABLE `medicines`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `singleGender` (`singleGender`);

--
-- Indices de la tabla `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpd715wipxfrhppqxa83m2fx0v` (`gender_id`);

--
-- Indices de la tabla `prescriptions`
--
ALTER TABLE `prescriptions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKri216h1p6fomc89j5snys8fk8` (`patient_id`);

--
-- Indices de la tabla `prescription_medicines`
--
ALTER TABLE `prescription_medicines`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKkljsa0768eycowt9le7nb215q` (`medicine_id`),
  ADD KEY `FK14vcxytfp7rr81y9fdhl37bbw` (`prescription_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `gender`
--
ALTER TABLE `gender`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `medicines`
--
ALTER TABLE `medicines`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `patient`
--
ALTER TABLE `patient`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `prescriptions`
--
ALTER TABLE `prescriptions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `prescription_medicines`
--
ALTER TABLE `prescription_medicines`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `medicines`
--
ALTER TABLE `medicines`
  ADD CONSTRAINT `medicines_ibfk_1` FOREIGN KEY (`singleGender`) REFERENCES `gender` (`id`);

--
-- Filtros para la tabla `patient`
--
ALTER TABLE `patient`
  ADD CONSTRAINT `FKpd715wipxfrhppqxa83m2fx0v` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`);

--
-- Filtros para la tabla `prescriptions`
--
ALTER TABLE `prescriptions`
  ADD CONSTRAINT `FKri216h1p6fomc89j5snys8fk8` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`);

--
-- Filtros para la tabla `prescription_medicines`
--
ALTER TABLE `prescription_medicines`
  ADD CONSTRAINT `FK14vcxytfp7rr81y9fdhl37bbw` FOREIGN KEY (`prescription_id`) REFERENCES `prescriptions` (`id`),
  ADD CONSTRAINT `FKkljsa0768eycowt9le7nb215q` FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
