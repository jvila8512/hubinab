-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-02-2023 a las 15:00:21
-- Versión del servidor: 10.4.22-MariaDB
-- Versión de PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectodl`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria_user`
--

CREATE TABLE `categoria_user` (
  `id` bigint(20) NOT NULL,
  `categoria_user` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categoria_user`
--

INSERT INTO `categoria_user` (`id`, `categoria_user`) VALUES
(1, 'Berkshire Gris'),
(3, '24 Metal'),
(4, 'Interno B2B integrated'),
(5, 'Gris Genérico'),
(6, 'Gorro Negro Bebes'),
(7, 'pixel valor'),
(8, 'Account alarm'),
(9, 'Rampa card'),
(10, 'Account Nacional Gris');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comenetarios_idea`
--

CREATE TABLE `comenetarios_idea` (
  `id` bigint(20) NOT NULL,
  `comentario` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `idea_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `comenetarios_idea`
--

INSERT INTO `comenetarios_idea` (`id`, `comentario`, `user_id`, `idea_id`) VALUES
(1, 'Acero leading-edge', NULL, NULL),
(2, 'Australian', NULL, NULL),
(3, 'Hogar Guapa backing', NULL, NULL),
(4, 'open-source Azul', NULL, NULL),
(5, 'Manzana', NULL, NULL),
(6, 'Gris', NULL, NULL),
(7, 'methodologies Acero', NULL, NULL),
(8, 'Facilitador Violeta Amarillo', NULL, NULL),
(9, 'Hormigon', NULL, NULL),
(10, 'Regional', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `componentes`
--

CREATE TABLE `componentes` (
  `id` bigint(20) NOT NULL,
  `componente` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `documento_url` varchar(255) DEFAULT NULL,
  `activo` bit(1) DEFAULT NULL,
  `ecosistema_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `componentes`
--

INSERT INTO `componentes` (`id`, `componente`, `descripcion`, `documento_url`, `activo`, `ecosistema_id`) VALUES
(1, 'tangible', 'Versatil ROI', 'index GB Interacciones', b'1', NULL),
(2, 'e-enable Bebes fritas', 'Oficial Pizza withdrawal', 'Relacciones turn-key benficios', b'1', NULL),
(3, 'invoice', 'Account El tangible', 'Sabroso Buckinghamshire Creativo', b'0', NULL),
(4, 'Madera', 'Infraestructura Malawi Marino', 'Moda', b'0', NULL),
(5, 'generating', 'Deportes', 'Central Estratega Guapo', b'0', NULL),
(6, 'Pelota Verde', 'Ergonómico redundant', 'Bedfordshire Gorro Normas', b'1', NULL),
(7, 'Representante Salud JBOD', 'repurpose inversa Leona', 'payment driver copying', b'1', NULL),
(8, 'Pollo', 'Morado networks Berkshire', 'indexing withdrawal', b'1', NULL),
(9, 'action-items', 'empower EXE', 'Venezuela optical', b'1', NULL),
(10, 'Salud implementación Normas', 'Rústico', 'SAS optical Desarrollador', b'1', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contacto`
--

CREATE TABLE `contacto` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `telefono` varchar(255) NOT NULL,
  `correo` varchar(255) NOT NULL,
  `mensaje` varchar(255) NOT NULL,
  `tipo_contacto_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `contacto`
--

INSERT INTO `contacto` (`id`, `nombre`, `telefono`, `correo`, `mensaje`, `tipo_contacto_id`) VALUES
(2, 'Cantabria Inversor', 'online Chad', 'Queso deposit Acero', 'Global', 4),
(3, 'alarm Centralizado', 'Peso', 'Montserrat drive', 'equipos Buckinghamshire', NULL),
(4, 'local generate Moda', 'bandwidth e-business Belarussian', 'Bacon', 'cero Metal', NULL),
(5, 'Gerente Ladrillo', 'Rojo', 'e-markets SDR Silla', '24/7 Orígenes', NULL),
(6, 'online', 'metódica Borders', 'partnerships', 'Hormigon redundant synergies', NULL),
(7, 'Seguro', 'syndicate Hormigon', 'Asturias Pescado', 'Electrónica', NULL),
(8, 'Open-source hacking', 'Producto Hong Inteligente', 'Mesa', 'Guantes', NULL),
(9, 'Hecho vortals', 'Comunicaciones', 'Canarias evolve', 'Videojuegos Kwanza', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `databasechangelog`
--

CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `databasechangelog`
--

INSERT INTO `databasechangelog` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`, `DEPLOYMENT_ID`) VALUES
('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2022-12-01 05:25:28', 1, 'EXECUTED', '8:b93de5e45c44e0d0dc109899d554b96a', 'createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165804-1', 'jhipster', 'config/liquibase/changelog/20221016165804_added_entity_TipoIdea.xml', '2022-12-01 05:25:29', 2, 'EXECUTED', '8:73e321eda45236717aa7a968160026f5', 'createTable tableName=tipo_idea', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165804-1-data', 'jhipster', 'config/liquibase/changelog/20221016165804_added_entity_TipoIdea.xml', '2022-12-01 05:25:29', 3, 'EXECUTED', '8:dc8ba6a71d9c11437212802be2143a23', 'loadData tableName=tipo_idea', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165805-1', 'jhipster', 'config/liquibase/changelog/20221016165805_added_entity_Idea.xml', '2022-12-01 05:25:29', 4, 'EXECUTED', '8:5d54415fd40e20363a47dbf8559247e7', 'createTable tableName=idea', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165805-1-data', 'jhipster', 'config/liquibase/changelog/20221016165805_added_entity_Idea.xml', '2022-12-01 05:25:29', 5, 'EXECUTED', '8:4214e190523fa0f08143420175a870e6', 'loadData tableName=idea', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165806-1', 'jhipster', 'config/liquibase/changelog/20221016165806_added_entity_Reto.xml', '2022-12-01 05:25:30', 6, 'EXECUTED', '8:ca384b27514ad659095b8ae51b49cd35', 'createTable tableName=reto', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165806-1-data', 'jhipster', 'config/liquibase/changelog/20221016165806_added_entity_Reto.xml', '2022-12-01 05:25:30', 7, 'EXECUTED', '8:1d252176ad05e29ef4826e44178285fd', 'loadData tableName=reto', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165807-1', 'jhipster', 'config/liquibase/changelog/20221016165807_added_entity_Ecosistema.xml', '2022-12-01 05:25:31', 8, 'EXECUTED', '8:76b92bcd276625cfa7f30d18ad7e22fe', 'createTable tableName=ecosistema', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165807-1-data', 'jhipster', 'config/liquibase/changelog/20221016165807_added_entity_Ecosistema.xml', '2022-12-01 05:25:32', 9, 'EXECUTED', '8:329b19c83d465db73c943ad515beaefc', 'loadData tableName=ecosistema', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165808-1', 'jhipster', 'config/liquibase/changelog/20221016165808_added_entity_UsuarioEcosistema.xml', '2022-12-01 05:25:32', 10, 'EXECUTED', '8:72d8698ed84137770158813947e30868', 'createTable tableName=usuario_ecosistema', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165808-1-relations', 'jhipster', 'config/liquibase/changelog/20221016165808_added_entity_UsuarioEcosistema.xml', '2022-12-01 05:25:34', 11, 'EXECUTED', '8:13f4294b3690714ecdebfa4150f72000', 'createTable tableName=rel_usuario_ecosistema__ecosistema; addPrimaryKey tableName=rel_usuario_ecosistema__ecosistema', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165808-1-data', 'jhipster', 'config/liquibase/changelog/20221016165808_added_entity_UsuarioEcosistema.xml', '2022-12-01 05:25:34', 12, 'EXECUTED', '8:f181d99ebaab39e0506257b2b8300471', 'loadData tableName=usuario_ecosistema', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165809-1', 'jhipster', 'config/liquibase/changelog/20221016165809_added_entity_Componentes.xml', '2022-12-01 05:25:34', 13, 'EXECUTED', '8:9656b8b8a226d684b7937e79b6bc0f73', 'createTable tableName=componentes', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165809-1-data', 'jhipster', 'config/liquibase/changelog/20221016165809_added_entity_Componentes.xml', '2022-12-01 05:25:35', 14, 'EXECUTED', '8:6028cfe18363a8f94aca77521b89b216', 'loadData tableName=componentes', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165810-1', 'jhipster', 'config/liquibase/changelog/20221016165810_added_entity_EcosistemaRol.xml', '2022-12-01 05:25:35', 15, 'EXECUTED', '8:80b7a6938dd085905bcf2ee82cbde9ca', 'createTable tableName=ecosistema_rol', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165810-1-data', 'jhipster', 'config/liquibase/changelog/20221016165810_added_entity_EcosistemaRol.xml', '2022-12-01 05:25:35', 16, 'EXECUTED', '8:23ca8642b457ea55ed939d26b5cd0d67', 'loadData tableName=ecosistema_rol', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165811-1', 'jhipster', 'config/liquibase/changelog/20221016165811_added_entity_EcosistemaPeticiones.xml', '2022-12-01 05:25:36', 17, 'EXECUTED', '8:cb4994fed9b20b9cca32bdc86eb51273', 'createTable tableName=ecosistema_peticiones', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165811-1-data', 'jhipster', 'config/liquibase/changelog/20221016165811_added_entity_EcosistemaPeticiones.xml', '2022-12-01 05:25:36', 18, 'EXECUTED', '8:4071ae73f5d443dbe4af54c5d743ec34', 'loadData tableName=ecosistema_peticiones', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165812-1', 'jhipster', 'config/liquibase/changelog/20221016165812_added_entity_Proyectos.xml', '2022-12-01 05:25:36', 19, 'EXECUTED', '8:2ecad8150554e3e1896ecbca0c01d591', 'createTable tableName=proyectos', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165812-1-relations', 'jhipster', 'config/liquibase/changelog/20221016165812_added_entity_Proyectos.xml', '2022-12-01 05:25:40', 20, 'EXECUTED', '8:06060da9f08841e0fd89853eeaaf1837', 'createTable tableName=rel_proyectos__sector; addPrimaryKey tableName=rel_proyectos__sector; createTable tableName=rel_proyectos__linea_investigacion; addPrimaryKey tableName=rel_proyectos__linea_investigacion; createTable tableName=rel_proyectos__...', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165812-1-data', 'jhipster', 'config/liquibase/changelog/20221016165812_added_entity_Proyectos.xml', '2022-12-01 05:25:40', 21, 'EXECUTED', '8:06778bf64506a213ccbe20c2214a7ab2', 'loadData tableName=proyectos', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165813-1', 'jhipster', 'config/liquibase/changelog/20221016165813_added_entity_Participantes.xml', '2022-12-01 05:25:40', 22, 'EXECUTED', '8:f11d4e542057d9ededfa5ff7a8fa55fc', 'createTable tableName=participantes', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165813-1-data', 'jhipster', 'config/liquibase/changelog/20221016165813_added_entity_Participantes.xml', '2022-12-01 05:25:41', 23, 'EXECUTED', '8:641035b270773ec8b7c190243a888faa', 'loadData tableName=participantes', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165814-1', 'jhipster', 'config/liquibase/changelog/20221016165814_added_entity_InnovacionRacionalizacion.xml', '2022-12-01 05:25:42', 24, 'EXECUTED', '8:2d683a73e204a1d6989b6b0185e4f8de', 'createTable tableName=innovacion_racionalizacion', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165814-1-data', 'jhipster', 'config/liquibase/changelog/20221016165814_added_entity_InnovacionRacionalizacion.xml', '2022-12-01 05:25:42', 25, 'EXECUTED', '8:bcfbf0928981e12d3d0a7cfbc31dad62', 'loadData tableName=innovacion_racionalizacion', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165815-1', 'jhipster', 'config/liquibase/changelog/20221016165815_added_entity_Tramite.xml', '2022-12-01 05:25:42', 26, 'EXECUTED', '8:38f5e28f43cc0f9a9d29bdabe0b496b0', 'createTable tableName=tramite', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165815-1-data', 'jhipster', 'config/liquibase/changelog/20221016165815_added_entity_Tramite.xml', '2022-12-01 05:25:42', 27, 'EXECUTED', '8:c0aada404109f830327becdc224e82d4', 'loadData tableName=tramite', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165816-1', 'jhipster', 'config/liquibase/changelog/20221016165816_added_entity_Noticias.xml', '2022-12-01 05:25:43', 28, 'EXECUTED', '8:61bf2a94ef94509aae83edacee79bffc', 'createTable tableName=noticias', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165816-1-data', 'jhipster', 'config/liquibase/changelog/20221016165816_added_entity_Noticias.xml', '2022-12-01 05:25:43', 29, 'EXECUTED', '8:b69821dc502297a2ddd04741eb5c1a56', 'loadData tableName=noticias', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165817-1', 'jhipster', 'config/liquibase/changelog/20221016165817_added_entity_TipoNoticia.xml', '2022-12-01 05:25:43', 30, 'EXECUTED', '8:04672ac7e765da2039a1c6a80f1c3f71', 'createTable tableName=tipo_noticia', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165817-1-data', 'jhipster', 'config/liquibase/changelog/20221016165817_added_entity_TipoNoticia.xml', '2022-12-01 05:25:44', 31, 'EXECUTED', '8:571d554f70f0359065747589706170e8', 'loadData tableName=tipo_noticia', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165818-1', 'jhipster', 'config/liquibase/changelog/20221016165818_added_entity_Contacto.xml', '2022-12-01 05:25:44', 32, 'EXECUTED', '8:4bf415576d2ee45caac0c6d31f13bc80', 'createTable tableName=contacto', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165818-1-data', 'jhipster', 'config/liquibase/changelog/20221016165818_added_entity_Contacto.xml', '2022-12-01 05:25:44', 33, 'EXECUTED', '8:f98dc5f5e0687f1b4b2103b1278e7ce1', 'loadData tableName=contacto', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165819-1', 'jhipster', 'config/liquibase/changelog/20221016165819_added_entity_TipoContacto.xml', '2022-12-01 05:25:45', 34, 'EXECUTED', '8:a72195bb14fc945154357fb226f4cf3b', 'createTable tableName=tipo_contacto', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165819-1-data', 'jhipster', 'config/liquibase/changelog/20221016165819_added_entity_TipoContacto.xml', '2022-12-01 05:25:45', 35, 'EXECUTED', '8:c179282e502769979b8d260987db6b82', 'loadData tableName=tipo_contacto', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221104121038-1', 'jhipster', 'config/liquibase/changelog/20221104121038_added_entity_ComenetariosIdea.xml', '2022-12-01 05:25:45', 36, 'EXECUTED', '8:33ec62d78b32dde9aa39e9eecae136ce', 'createTable tableName=comenetarios_idea', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221104121038-1-data', 'jhipster', 'config/liquibase/changelog/20221104121038_added_entity_ComenetariosIdea.xml', '2022-12-01 05:25:46', 37, 'EXECUTED', '8:e6cbdac3d0af02c1e0eda24bf0fda550', 'loadData tableName=comenetarios_idea', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221104121039-1', 'jhipster', 'config/liquibase/changelog/20221104121039_added_entity_LikeIdea.xml', '2022-12-01 05:25:46', 38, 'EXECUTED', '8:29ddaef6d1b58a58dadbc574268d9adf', 'createTable tableName=like_idea', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221104121039-1-data', 'jhipster', 'config/liquibase/changelog/20221104121039_added_entity_LikeIdea.xml', '2022-12-01 05:25:47', 39, 'EXECUTED', '8:54e471b9572f9ab65d7c89135e8c9690', 'loadData tableName=like_idea', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221104121040-1', 'jhipster', 'config/liquibase/changelog/20221104121040_added_entity_Entidad.xml', '2022-12-01 05:25:47', 40, 'EXECUTED', '8:daa6370a60fd2a6eea12503c07e41c4f', 'createTable tableName=entidad', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221104121040-1-data', 'jhipster', 'config/liquibase/changelog/20221104121040_added_entity_Entidad.xml', '2022-12-01 05:25:47', 41, 'EXECUTED', '8:d113ef5b2f44bd0fb6ad207ebfc0ab7b', 'loadData tableName=entidad', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221104121041-1', 'jhipster', 'config/liquibase/changelog/20221104121041_added_entity_TipoProyecto.xml', '2022-12-01 05:25:48', 42, 'EXECUTED', '8:1932f5cfa53206745fde4094d2d8b708', 'createTable tableName=tipo_proyecto', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221104121041-1-data', 'jhipster', 'config/liquibase/changelog/20221104121041_added_entity_TipoProyecto.xml', '2022-12-01 05:25:48', 43, 'EXECUTED', '8:3d89578c9b290e683a22bea795abbe5c', 'loadData tableName=tipo_proyecto', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221104121042-1', 'jhipster', 'config/liquibase/changelog/20221104121042_added_entity_Sector.xml', '2022-12-01 05:25:49', 44, 'EXECUTED', '8:04a0a0474197b2f9424ed9d20407f746', 'createTable tableName=sector', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221104121042-1-data', 'jhipster', 'config/liquibase/changelog/20221104121042_added_entity_Sector.xml', '2022-12-01 05:25:49', 45, 'EXECUTED', '8:7113e913c1d3d7a8af0bafcf80d45ae3', 'loadData tableName=sector', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221104121043-1', 'jhipster', 'config/liquibase/changelog/20221104121043_added_entity_LineaInvestigacion.xml', '2022-12-01 05:25:50', 46, 'EXECUTED', '8:34237e157dc61a077210548e63f42d81', 'createTable tableName=linea_investigacion', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221104121043-1-data', 'jhipster', 'config/liquibase/changelog/20221104121043_added_entity_LineaInvestigacion.xml', '2022-12-01 05:25:50', 47, 'EXECUTED', '8:5060e1d0e2378dd5256d2da1a2df2239', 'loadData tableName=linea_investigacion', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221104121044-1', 'jhipster', 'config/liquibase/changelog/20221104121044_added_entity_Ods.xml', '2022-12-01 05:25:50', 48, 'EXECUTED', '8:c0853b7c373750c9a2c27223f2e736f1', 'createTable tableName=ods', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221104121044-1-data', 'jhipster', 'config/liquibase/changelog/20221104121044_added_entity_Ods.xml', '2022-12-01 05:25:51', 49, 'EXECUTED', '8:ed045d13b885689d63a1d63a8f6c508b', 'loadData tableName=ods', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221130104625-1', 'jhipster', 'config/liquibase/changelog/20221130104625_added_entity_RedesSociales.xml', '2022-12-01 05:25:51', 50, 'EXECUTED', '8:4aa1f89e0ff808d01ece8864b1a3ccc8', 'createTable tableName=redes_sociales', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221130104625-1-data', 'jhipster', 'config/liquibase/changelog/20221130104625_added_entity_RedesSociales.xml', '2022-12-01 05:25:51', 51, 'EXECUTED', '8:6e7bd86da0ada93c0a326349589926e6', 'loadData tableName=redes_sociales', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221130104626-1', 'jhipster', 'config/liquibase/changelog/20221130104626_added_entity_CategoriaUser.xml', '2022-12-01 05:25:51', 52, 'EXECUTED', '8:0f4c867e3c3fd0b5382ce71ee8ed4424', 'createTable tableName=categoria_user', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221130104626-1-data', 'jhipster', 'config/liquibase/changelog/20221130104626_added_entity_CategoriaUser.xml', '2022-12-01 05:25:52', 53, 'EXECUTED', '8:5f39b93449742237e327f0ca2045f5d6', 'loadData tableName=categoria_user', '', NULL, '4.6.1', 'faker', NULL, '9890321312'),
('20221016165805-2', 'jhipster', 'config/liquibase/changelog/20221016165805_added_entity_constraints_Idea.xml', '2022-12-01 05:25:58', 54, 'EXECUTED', '8:501929ca1017f053d5d8d9f1a93ff1ac', 'addForeignKeyConstraint baseTableName=idea, constraintName=fk_idea__user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=idea, constraintName=fk_idea__reto_id, referencedTableName=reto; addForeignKeyConstraint baseTableName...', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165806-2', 'jhipster', 'config/liquibase/changelog/20221016165806_added_entity_constraints_Reto.xml', '2022-12-01 05:26:01', 55, 'EXECUTED', '8:697c1e52fcfe75733af39dac885cd9b3', 'addForeignKeyConstraint baseTableName=reto, constraintName=fk_reto__user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=reto, constraintName=fk_reto__ecosistema_id, referencedTableName=ecosistema', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165807-2', 'jhipster', 'config/liquibase/changelog/20221016165807_added_entity_constraints_Ecosistema.xml', '2022-12-01 05:26:07', 56, 'EXECUTED', '8:f405c3ad75c2c536756ea7fe75db227a', 'addForeignKeyConstraint baseTableName=ecosistema, constraintName=fk_ecosistema__user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=ecosistema, constraintName=fk_ecosistema__ecosistema_rol_id, referencedTableName=ecosistem...', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165808-2', 'jhipster', 'config/liquibase/changelog/20221016165808_added_entity_constraints_UsuarioEcosistema.xml', '2022-12-01 05:26:12', 57, 'EXECUTED', '8:b0ed3bc2307114d960d330d38bba6dc3', 'addForeignKeyConstraint baseTableName=usuario_ecosistema, constraintName=fk_usuario_ecosistema__user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=usuario_ecosistema, constraintName=fk_usuario_ecosistema__categoria_user_i...', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165809-2', 'jhipster', 'config/liquibase/changelog/20221016165809_added_entity_constraints_Componentes.xml', '2022-12-01 05:26:14', 58, 'EXECUTED', '8:705249b5f9a1111a9955ddfdcb9c2d35', 'addForeignKeyConstraint baseTableName=componentes, constraintName=fk_componentes__ecosistema_id, referencedTableName=ecosistema', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165811-2', 'jhipster', 'config/liquibase/changelog/20221016165811_added_entity_constraints_EcosistemaPeticiones.xml', '2022-12-01 05:26:18', 59, 'EXECUTED', '8:10ef903849130a7877696e1805617d61', 'addForeignKeyConstraint baseTableName=ecosistema_peticiones, constraintName=fk_ecosistema_peticiones__user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=ecosistema_peticiones, constraintName=fk_ecosistema_peticiones__ecos...', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165812-2', 'jhipster', 'config/liquibase/changelog/20221016165812_added_entity_constraints_Proyectos.xml', '2022-12-01 05:26:30', 60, 'EXECUTED', '8:7a53713a256035079af06b1de0df9635', 'addForeignKeyConstraint baseTableName=proyectos, constraintName=fk_proyectos__user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=rel_proyectos__sector, constraintName=fk_rel_proyectos__sector__proyectos_id, referencedTabl...', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165813-2', 'jhipster', 'config/liquibase/changelog/20221016165813_added_entity_constraints_Participantes.xml', '2022-12-01 05:26:32', 61, 'EXECUTED', '8:8bb81ac5377094f482fec440adada476', 'addForeignKeyConstraint baseTableName=participantes, constraintName=fk_participantes__proyectos_id, referencedTableName=proyectos', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165814-2', 'jhipster', 'config/liquibase/changelog/20221016165814_added_entity_constraints_InnovacionRacionalizacion.xml', '2022-12-01 05:26:35', 62, 'EXECUTED', '8:70a586005af0157ea3d2188c6c857cd4', 'addForeignKeyConstraint baseTableName=innovacion_racionalizacion, constraintName=fk_innovacion_racionalizacion__tipo_idea_id, referencedTableName=tipo_idea', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165816-2', 'jhipster', 'config/liquibase/changelog/20221016165816_added_entity_constraints_Noticias.xml', '2022-12-01 05:26:43', 63, 'EXECUTED', '8:5f2c9c40ff3ac1e7377c84785b7b552c', 'addForeignKeyConstraint baseTableName=noticias, constraintName=fk_noticias__user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=noticias, constraintName=fk_noticias__ecosistema_id, referencedTableName=ecosistema; addForeig...', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221016165818-2', 'jhipster', 'config/liquibase/changelog/20221016165818_added_entity_constraints_Contacto.xml', '2022-12-01 05:26:47', 64, 'EXECUTED', '8:0dc167236be7a104ffe7de8661a14b0b', 'addForeignKeyConstraint baseTableName=contacto, constraintName=fk_contacto__tipo_contacto_id, referencedTableName=tipo_contacto', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221104121038-2', 'jhipster', 'config/liquibase/changelog/20221104121038_added_entity_constraints_ComenetariosIdea.xml', '2022-12-01 05:26:50', 65, 'EXECUTED', '8:616d94ee11d7c7f50e4a100af79cd36e', 'addForeignKeyConstraint baseTableName=comenetarios_idea, constraintName=fk_comenetarios_idea__user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=comenetarios_idea, constraintName=fk_comenetarios_idea__idea_id, referencedT...', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221104121039-2', 'jhipster', 'config/liquibase/changelog/20221104121039_added_entity_constraints_LikeIdea.xml', '2022-12-01 05:26:55', 66, 'EXECUTED', '8:d75400286e84ee54e3b7aee1fe13e450', 'addForeignKeyConstraint baseTableName=like_idea, constraintName=fk_like_idea__user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=like_idea, constraintName=fk_like_idea__idea_id, referencedTableName=idea', '', NULL, '4.6.1', NULL, NULL, '9890321312'),
('20221130104625-2', 'jhipster', 'config/liquibase/changelog/20221130104625_added_entity_constraints_RedesSociales.xml', '2022-12-01 05:26:57', 67, 'EXECUTED', '8:d4e3c0eb5c3185a3dfb343ca42a18d84', 'addForeignKeyConstraint baseTableName=redes_sociales, constraintName=fk_redes_sociales__ecosistema_id, referencedTableName=ecosistema', '', NULL, '4.6.1', NULL, NULL, '9890321312');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `databasechangeloglock`
--

CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `databasechangeloglock`
--

INSERT INTO `databasechangeloglock` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'1', '2022-12-06 23:16:40', 'DESKTOP-9P58SVG (192.168.1.104)');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ecosistema`
--

CREATE TABLE `ecosistema` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `tematica` varchar(255) NOT NULL,
  `activo` bit(1) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `ranking` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `ecosistema_rol_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ecosistema`
--

INSERT INTO `ecosistema` (`id`, `nombre`, `tematica`, `activo`, `logo_url`, `ranking`, `user_id`, `ecosistema_rol_id`) VALUES
(2, 'Universidad-LT', 'Ecosistema para el fortalecimiento del conocimiento en la creación de nuevos proyectos y tecnologías innovadoras', b'1', 'Universidad-LT', 0, 1, 3),
(4, 'Desarrollo Local-LT', 'Ecosistemas de apoyo a ideas de proyectos territoriales, promotor  para alcanzar la escalabilidad de los ecosistemas y fomentar la cultura de innovación, proveedor de financiamiento a proyectos', b'0', 'foto5', 2, NULL, NULL),
(9, 'ANIR-LT', 'Ecosistema que habilita herramientas; para  hacer crecer ideas creativas e innovadoras, impulsando las innovaciones en las organizaciones ', b'1', 'ecosistema.png', 3, 1, 2),
(80, 'zz\\x', 'zxz\\x', b'1', 'vladi1.jpeg', NULL, 1, 2),
(81, 'Yane', 'sdfsdf', b'1', 'RC1.png', NULL, 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ecosistema_peticiones`
--

CREATE TABLE `ecosistema_peticiones` (
  `id` bigint(20) NOT NULL,
  `motivo` varchar(255) DEFAULT NULL,
  `fecha_peticion` date DEFAULT NULL,
  `aprobada` bit(1) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `ecosistema_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ecosistema_peticiones`
--

INSERT INTO `ecosistema_peticiones` (`id`, `motivo`, `fecha_peticion`, `aprobada`, `user_id`, `ecosistema_id`) VALUES
(1, 'UIC-Franc Amarillo dedicada', '2022-10-16', b'0', NULL, NULL),
(2, 'circuit', '2022-10-15', b'1', NULL, NULL),
(3, 'Verde Ejecutivo', '2022-10-16', b'1', NULL, NULL),
(4, 'transmit', '2022-10-16', b'0', NULL, NULL),
(5, 'Dollar) Hormigon', '2022-10-15', b'1', NULL, NULL),
(6, 'Baleares Mónaco de', '2022-10-16', b'1', NULL, NULL),
(7, 'Metal Pantalones', '2022-10-16', b'0', NULL, NULL),
(8, 'XSS B2B', '2022-10-16', b'0', NULL, NULL),
(9, 'Andalucía', '2022-10-16', b'0', NULL, NULL),
(10, 'Barranco back-end', '2022-10-16', b'1', NULL, NULL),
(11, 'Posrado', NULL, b'0', 2, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ecosistema_rol`
--

CREATE TABLE `ecosistema_rol` (
  `id` bigint(20) NOT NULL,
  `ecosistema_rol` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ecosistema_rol`
--

INSERT INTO `ecosistema_rol` (`id`, `ecosistema_rol`, `descripcion`) VALUES
(1, 'Rústico Gestionado', 'Metal soporte'),
(2, 'Datos Orgánico digital', 'configurable'),
(3, 'Negro', 'Analista Lituania'),
(4, 'ROI XSS', 'Métricas'),
(6, 'synthesize Acero medición', 'bleeding-edge'),
(7, 'Global', 'Intuitivo Grupo'),
(8, 'Web', 'Distrito'),
(9, 'incentivize Administrador Joyería', 'invoice up');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entidad`
--

CREATE TABLE `entidad` (
  `id` bigint(20) NOT NULL,
  `entidad` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `entidad`
--

INSERT INTO `entidad` (`id`, `entidad`) VALUES
(1, 'Producto'),
(2, 'Ordenador payment Municipio'),
(3, 'Madera enhance Borders'),
(4, 'Madera'),
(5, 'Ingeniería innovative Kroon'),
(6, 'Parafarmacia'),
(7, 'Agente'),
(8, 'navigating'),
(9, 'País'),
(10, 'Violeta');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `idea`
--

CREATE TABLE `idea` (
  `id` bigint(20) NOT NULL,
  `numero_registro` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `autor` varchar(255) NOT NULL,
  `fecha_incripcion` date DEFAULT NULL,
  `visto` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `reto_id` bigint(20) DEFAULT NULL,
  `tipo_idea_id` bigint(20) DEFAULT NULL,
  `entidad_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `idea`
--

INSERT INTO `idea` (`id`, `numero_registro`, `titulo`, `descripcion`, `autor`, `fecha_incripcion`, `visto`, `user_id`, `reto_id`, `tipo_idea_id`, `entidad_id`) VALUES
(3, 29852, 'Castilla-La Taiwan program', 'cross-platform', 'mobile circuit', '2022-10-16', 53608, 1, 1, 2, 4),
(4, 4931, 'deposit Violeta Extendido', 'facilitate Métricas Rupee', 'sexy', '2022-10-16', 38456, NULL, NULL, NULL, NULL),
(5, 40517, 'open-source networks', 'Metal', 'Ensalada e-services', '2022-10-16', 19356, NULL, NULL, NULL, NULL),
(6, 39048, 'Increible Canarias', 'pixel Configurable Terrenos', 'Electrónica', '2022-10-16', 66039, NULL, NULL, NULL, NULL),
(7, 99317, 'Plástico payment matrix', 'matrices index', 'Gestionado hacking Granito', '2022-10-15', 53179, NULL, NULL, NULL, NULL),
(13, 123, 'dfgfg', '2323dfd', 'dfdfgdg', '2023-01-11', 23, 1, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `innovacion_racionalizacion`
--

CREATE TABLE `innovacion_racionalizacion` (
  `id` bigint(20) NOT NULL,
  `tematica` varchar(255) NOT NULL,
  `fecha` date NOT NULL,
  `vp` int(11) NOT NULL,
  `autores` varchar(255) NOT NULL,
  `numero_identidad` bigint(20) NOT NULL,
  `observacion` varchar(255) DEFAULT NULL,
  `aprobada` bit(1) DEFAULT NULL,
  `publico` bit(1) NOT NULL,
  `tipo_idea_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `innovacion_racionalizacion`
--

INSERT INTO `innovacion_racionalizacion` (`id`, `tematica`, `fecha`, `vp`, `autores`, `numero_identidad`, `observacion`, `aprobada`, `publico`, `tipo_idea_id`) VALUES
(1, 'Ejecutivo', '2022-10-16', 52174, 'necesidades', 80475, 'Chipre Mesa', b'0', b'1', NULL),
(2, 'Consultor Tonga', '2022-10-16', 7431, 'Pescado Coordinador', 71431, 'Caserio bleeding-edge', b'1', b'0', NULL),
(3, 'navigating Ladrillo', '2022-10-16', 56184, 'recontextualize', 97075, 'payment bandwidth monitor', b'0', b'1', NULL),
(4, 'Algodón portals', '2022-10-16', 56453, 'Representante generación', 68928, 'Patatas Hormigon estrategia', b'0', b'0', NULL),
(5, 'bus Madera', '2022-10-15', 87267, 'Funcionario neural users', 69747, 'recontextualize intuitive Bicicleta', b'1', b'1', NULL),
(6, 'SDD', '2022-10-16', 65670, 'copying Marroquinería', 25056, 'aggregate optical Parafarmacia', b'1', b'1', NULL),
(7, 'Acero', '2022-10-15', 42838, 'interface', 92246, 'Raton', b'0', b'0', NULL),
(8, 'Méjico', '2022-10-16', 25516, 'Baht Card', 54955, 'Nacional', b'1', b'0', NULL),
(9, 'SDR SQL', '2022-10-16', 35949, 'Calidad up B2B', 8228, 'quantify orquestar', b'0', b'0', NULL),
(10, 'Account', '2022-10-15', 85531, 'soporte bypass', 5402, 'dynamic', b'1', b'1', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jhi_authority`
--

CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `jhi_authority`
--

INSERT INTO `jhi_authority` (`name`) VALUES
('ROLE_ADMIN'),
('ROLE_ADMINECOSISTEMA'),
('ROLE_GESTOR'),
('ROLE_USER');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jhi_user`
--

CREATE TABLE `jhi_user` (
  `id` bigint(20) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(191) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(10) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `jhi_user`
--

INSERT INTO `jhi_user` (`id`, `login`, `password_hash`, `first_name`, `last_name`, `email`, `image_url`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`) VALUES
(1, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', '', b'1', 'es', NULL, NULL, 'system', NULL, NULL, 'system', NULL),
(2, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', '', b'1', 'es', NULL, NULL, 'system', NULL, NULL, 'system', NULL),
(3, 'javiervl', '$2a$10$lNeLztY9Z5okInLP/1KiAOZJmZYEclfgP2Pdu19MzSd8vGERvtar.', 'Javier', 'Vila Labrada', 'jvila85@nauta.cu', NULL, b'1', 'es', NULL, NULL, 'anonymousUser', '2022-12-01 15:43:19', NULL, 'javiervl', '2022-12-01 15:44:45'),
(5, 'jvila85', '$2a$10$m5fJs8W648VjkKv3M1tSNerzXdxdnzShhmTIjJ90G/bTFMNIRdwnq', 'Javier1', 'Vila Labrada', 'javier.vila.labrada@gmail.com', NULL, b'1', 'es', 'AbcEXL5yz87IPVdNTUtm', NULL, 'anonymousUser', '2022-12-01 17:15:50', NULL, 'admin', '2022-12-05 00:04:28');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jhi_user_authority`
--

CREATE TABLE `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `jhi_user_authority`
--

INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`) VALUES
(1, 'ROLE_ADMIN'),
(1, 'ROLE_USER'),
(2, 'ROLE_USER'),
(3, 'ROLE_ADMINECOSISTEMA'),
(5, 'ROLE_GESTOR');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `like_idea`
--

CREATE TABLE `like_idea` (
  `id` bigint(20) NOT NULL,
  `jhi_like` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `idea_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `like_idea`
--

INSERT INTO `like_idea` (`id`, `jhi_like`, `user_id`, `idea_id`) VALUES
(3, 'horas', NULL, NULL),
(4, 'del Ergonómico Metal', NULL, NULL),
(5, 'Gerente habilidad', NULL, NULL),
(6, 'models', NULL, NULL),
(7, 'Pizza deposit', NULL, NULL),
(8, 'Funcionario gestión', NULL, NULL),
(9, 'SSL Marca', NULL, NULL),
(10, 'interface', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `linea_investigacion`
--

CREATE TABLE `linea_investigacion` (
  `id` bigint(20) NOT NULL,
  `linea` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `linea_investigacion`
--

INSERT INTO `linea_investigacion` (`id`, `linea`) VALUES
(1, 'streamline'),
(2, 'Increible Account Mercados'),
(3, 'la Avon'),
(4, 'deliverables productize Agente'),
(5, 'Gris'),
(6, 'quantify SMTP'),
(7, 'Creativo'),
(8, 'Leu Metal Andalucía'),
(9, 'de'),
(10, 'transform');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `noticias`
--

CREATE TABLE `noticias` (
  `id` bigint(20) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `decripcion` varchar(255) NOT NULL,
  `url_foto` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `ecosistema_id` bigint(20) DEFAULT NULL,
  `tipo_noticia_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `noticias`
--

INSERT INTO `noticias` (`id`, `titulo`, `decripcion`, `url_foto`, `user_id`, `ecosistema_id`, `tipo_noticia_id`) VALUES
(1, 'Reconocimiento', 'La ANIR entrega  reconocimientos y premios a los innovadores que alcanzaron la condición nacional 8vo Congreso. ', 'foto10', 1, 2, 1),
(2, 'Reconocimiento', 'Reconocimieto a los aniristas en Balance de la ANIR', 'foto11', NULL, NULL, NULL),
(3, 'Reconocimiento', 'La mujer tunera innovadora ', 'foto12', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ods`
--

CREATE TABLE `ods` (
  `id` bigint(20) NOT NULL,
  `ods` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ods`
--

INSERT INTO `ods` (`id`, `ods`) VALUES
(1, 'Profundo'),
(2, 'Central Explanada'),
(3, 'Operaciones Sopa mesh'),
(4, 'application'),
(5, 'Producto Consultor'),
(6, 'quantifying Castilla-La'),
(7, 'Morado'),
(8, 'withdrawal'),
(9, 'Funcionario HDD Estonia'),
(10, 'Ethiopian Salud');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `participantes`
--

CREATE TABLE `participantes` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `correo` varchar(255) NOT NULL,
  `proyectos_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `participantes`
--

INSERT INTO `participantes` (`id`, `nombre`, `telefono`, `correo`, `proyectos_id`) VALUES
(1, 'calculate Gris interface', 'hibrida synthesize', 'Cambridgeshire', NULL),
(2, 'Aragón', 'Subida', 'metódica', NULL),
(3, 'Bebes', 'Jefe', 'calculating Fantástico Guapa', NULL),
(4, 'Avon', 'neutral Inteligente', 'Sabroso schemas Intranet', NULL),
(5, 'SDD', 'Baleares Fantástico', 'Productor', NULL),
(6, 'Colombian deposit Funcionalidad', 'reboot Bedfordshire', 'Austria Cambridgeshire Franc', NULL),
(7, 'Decoración', 'repurpose de Franc', 'Account SQL paradigma', NULL),
(8, 'Micronesia architectures partnerships', 'invoice', 'array Masía', NULL),
(9, 'Puerta Account', 'Factores Estonia', 'Bedfordshire', NULL),
(10, 'Poland Metal', 'Hormigon', 'Morado Toallas SMTP', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proyectos`
--

CREATE TABLE `proyectos` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `autor` varchar(255) NOT NULL,
  `necesidad` varchar(255) NOT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `ecosistema_id` bigint(20) DEFAULT NULL,
  `tipo_proyecto_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `proyectos`
--

INSERT INTO `proyectos` (`id`, `nombre`, `descripcion`, `autor`, `necesidad`, `fecha_inicio`, `fecha_fin`, `logo_url`, `user_id`, `ecosistema_id`, `tipo_proyecto_id`) VALUES
(1, 'enhance Consultor International', 'Programable', 'solutions Buckinghamshire metódica', 'brand Gold multiestado', '2022-10-16', '2022-10-16', 'Versatil quantifying withdrawal', NULL, NULL, NULL),
(2, 'Toallas', 'radical', 'Home protocol migración', 'Plástico Algodón', '2022-10-16', '2022-10-16', 'Para payment Mesa', NULL, NULL, NULL),
(3, 'multi-byte', 'deposit Rojo', 'Checa', 'Bedfordshire program', '2022-10-16', '2022-10-15', 'empresa synthesize estructura', NULL, NULL, NULL),
(4, 'Guapo Pakistan', 'paradigms Fantástico', 'Extremadura Decoración Factores', 'asimétrica architectures', '2022-10-16', '2022-10-16', 'Vatu Web Samoa', NULL, NULL, NULL),
(5, 'port Sección middleware', 'indexing Colonia', 'Camiseta Entrada', 'Silla Música', '2022-10-16', '2022-10-15', 'synthesize', NULL, NULL, NULL),
(6, 'Avon bypass Gerente', 'enterprise Inteligente', 'Colombian', 'Ergonómico Gestionado Morado', '2022-10-16', '2022-10-16', 'Metal Regional Oficial', NULL, NULL, NULL),
(7, 'architectures navigating', 'Algodón Parafarmacia', 'Canarias Informática', 'Cine Productor', '2022-10-16', '2022-10-15', 'Plástico', NULL, NULL, NULL),
(8, 'archivo Ladrillo', 'Bedfordshire Negro capacitor', 'deposit Electrónica holistic', 'real-time vortals Montserrat', '2022-10-16', '2022-10-16', 'Savings', NULL, NULL, NULL),
(9, 'habilidad Hormigon', 'mindshare drive payment', 'Centralizado Bebes', 'Extremadura Borders Andalucía', '2022-10-16', '2022-10-16', 'synergistic Madera Sincronizado', NULL, NULL, NULL),
(10, 'interfaz Granito Fantástico', 'solutions Dollar Pollo', 'Marketing architectures', 'Franc Práctico', '2022-10-16', '2022-10-16', 'Polarizado', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `redes_sociales`
--

CREATE TABLE `redes_sociales` (
  `id` bigint(20) NOT NULL,
  `redes_url` varchar(255) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `ecosistema_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `redes_sociales`
--

INSERT INTO `redes_sociales` (`id`, `redes_url`, `logo_url`, `ecosistema_id`) VALUES
(1, 'port', 'Moda mobile', NULL),
(2, 'Money input withdrawal', 'paradigms streamline', NULL),
(3, 'payment Raton Acero', 'Hormigon', NULL),
(4, 'Metal Patatas clicks-and-mortar', 'dinámica Asociado', NULL),
(5, 'dot-com multi-byte', 'Amarillo schemas Agente', NULL),
(6, 'Sabroso', 'Cliente Fantástico', NULL),
(7, 'Implementación', 'Aruba', NULL),
(8, 'Negro', 'generating mindshare', NULL),
(9, 'Marca', 'Orígenes Producto Pizza', NULL),
(10, 'Baleares', 'matrix methodologies Costa', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rel_proyectos__linea_investigacion`
--

CREATE TABLE `rel_proyectos__linea_investigacion` (
  `linea_investigacion_id` bigint(20) NOT NULL,
  `proyectos_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rel_proyectos__ods`
--

CREATE TABLE `rel_proyectos__ods` (
  `ods_id` bigint(20) NOT NULL,
  `proyectos_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rel_proyectos__sector`
--

CREATE TABLE `rel_proyectos__sector` (
  `sector_id` bigint(20) NOT NULL,
  `proyectos_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rel_usuario_ecosistema__ecosistema`
--

CREATE TABLE `rel_usuario_ecosistema__ecosistema` (
  `ecosistema_id` bigint(20) NOT NULL,
  `usuario_ecosistema_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `rel_usuario_ecosistema__ecosistema`
--

INSERT INTO `rel_usuario_ecosistema__ecosistema` (`ecosistema_id`, `usuario_ecosistema_id`) VALUES
(4, 12),
(9, 12);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reto`
--

CREATE TABLE `reto` (
  `id` bigint(20) NOT NULL,
  `reto` varchar(255) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `motivacion` varchar(255) NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date DEFAULT NULL,
  `activo` bit(1) DEFAULT NULL,
  `validado` bit(1) DEFAULT NULL,
  `url_foto` varchar(255) DEFAULT NULL,
  `visto` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `ecosistema_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `reto`
--

INSERT INTO `reto` (`id`, `reto`, `descripcion`, `motivacion`, `fecha_inicio`, `fecha_fin`, `activo`, `validado`, `url_foto`, `visto`, `user_id`, `ecosistema_id`) VALUES
(1, 'Deportes synthesize Música', 'Extendido', 'architect', '2022-10-16', '2022-10-16', b'1', b'1', 'killer', 21124, NULL, NULL),
(2, 'generate Puente COM', 'sistemática mission-critical holistic', 'Gris', '2022-10-16', '2022-10-15', b'1', b'0', 'Mesa Programable', 77398, NULL, NULL),
(3, 'rich', 'Checking Configurable Verde', 'quantifying Enfocado alarm', '2022-10-16', '2022-10-15', b'0', b'1', 'Morado', 43423, NULL, NULL),
(4, 'Refinado Market', 'Ladrillo funcionalidad Organizado', 'Plástico', '2022-10-16', '2022-10-16', b'1', b'0', 'compress Plástico Cambridgeshire', 39169, NULL, NULL),
(5, '24/7 enfoque Organizado', 'IB', 'withdrawal', '2022-10-16', '2022-10-15', b'1', b'1', 'Cambridgeshire', 14106, NULL, NULL),
(6, 'Amarillo Pelota Total', 'Especialista Obligatorio', 'bluetooth', '2022-10-16', '2022-10-16', b'0', b'0', 'synergize Moda Artesanal', 36766, NULL, NULL),
(7, 'Pelota recíproca explícita', 'Desarrollador', 'Togo', '2022-10-16', '2022-10-15', b'1', b'0', 'Huerta codificar Account', 59707, NULL, NULL),
(8, 'Bedfordshire SSL online', 'Algodón Madera', 'proyección Optimizado modular', '2022-10-15', '2022-10-16', b'1', b'1', 'India benficios', 54741, NULL, NULL),
(9, 'user-centric Total parse', 'morph de Kroon', 'infrastructures de', '2022-10-16', '2022-10-15', b'1', b'0', 'Checking PNG 24/7', 19974, NULL, NULL),
(10, 'Rua Rústico', 'engineer wireless back', 'Rústico Datos País', '2022-10-15', '2022-10-16', b'1', b'0', 'hack Russian Herzegovina', 34493, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sector`
--

CREATE TABLE `sector` (
  `id` bigint(20) NOT NULL,
  `sector` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `sector`
--

INSERT INTO `sector` (`id`, `sector`) VALUES
(1, 'navigate empower Caserio'),
(2, 'Comunidad'),
(3, 'Blanco'),
(4, 'generación Violeta solid'),
(5, 'Artesanal'),
(6, 'bluetooth Avon RAM'),
(7, 'Guantes real-time regional'),
(8, 'Blanco productize Métricas'),
(9, 'Jefe niches Grupo'),
(10, 'Avon');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_contacto`
--

CREATE TABLE `tipo_contacto` (
  `id` bigint(20) NOT NULL,
  `tipo_contacto` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tipo_contacto`
--

INSERT INTO `tipo_contacto` (`id`, `tipo_contacto`) VALUES
(2, 'index 24/365'),
(3, 'transmit Shilling'),
(4, 'Avon'),
(5, 'Programable Rojo'),
(6, 'Re-implementado'),
(7, 'connecting International'),
(8, 'Samoa payment'),
(9, 'Increible Algodón Atún'),
(10, 'impactful Borders'),
(11, 'fgdfgd'),
(12, 'fgdfgd');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_idea`
--

CREATE TABLE `tipo_idea` (
  `id` bigint(20) NOT NULL,
  `tipo_idea` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tipo_idea`
--

INSERT INTO `tipo_idea` (`id`, `tipo_idea`) VALUES
(1, 'Camiseta Pantalones Increible'),
(2, 'Totalmente País Marketing'),
(3, 'Niger'),
(4, 'services'),
(11, 'trtrt'),
(12, 'gyhgfhgf');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_noticia`
--

CREATE TABLE `tipo_noticia` (
  `id` bigint(20) NOT NULL,
  `tipo_noticia` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tipo_noticia`
--

INSERT INTO `tipo_noticia` (`id`, `tipo_noticia`) VALUES
(1, 'Algodón Cambridgeshire 1080p'),
(2, 'Intranet backing de'),
(3, 'Rojo'),
(4, 'partnerships port Ergonómico'),
(5, 'Sorprendente'),
(6, 'Pequeño Genérico Cantabria'),
(7, 'transmitting Baleares generación'),
(8, 'Gerente'),
(9, 'Home'),
(10, 'Gris');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_proyecto`
--

CREATE TABLE `tipo_proyecto` (
  `id` bigint(20) NOT NULL,
  `tipo_proyecto` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tipo_proyecto`
--

INSERT INTO `tipo_proyecto` (`id`, `tipo_proyecto`) VALUES
(1, 'transmitter'),
(2, 'Bedfordshire wireless Leone'),
(3, 'Diseñador array'),
(4, 'Guapo'),
(5, 'SSL'),
(6, 'Algodón Cuesta'),
(7, 'IB Deportes'),
(8, 'Somoni'),
(9, 'Supervisor'),
(10, 'Plástico');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tramite`
--

CREATE TABLE `tramite` (
  `id` bigint(20) NOT NULL,
  `inscripcion` varchar(255) DEFAULT NULL,
  `prueba_experimental` varchar(255) DEFAULT NULL,
  `exmanen_evaluacion` varchar(255) DEFAULT NULL,
  `dictamen` varchar(255) DEFAULT NULL,
  `concesion` bit(1) DEFAULT NULL,
  `denegado` bit(1) DEFAULT NULL,
  `reclamacion` bit(1) DEFAULT NULL,
  `anulacion` bit(1) DEFAULT NULL,
  `fecha_notificacion` date DEFAULT NULL,
  `feca_certificado` date DEFAULT NULL,
  `observacion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tramite`
--

INSERT INTO `tramite` (`id`, `inscripcion`, `prueba_experimental`, `exmanen_evaluacion`, `dictamen`, `concesion`, `denegado`, `reclamacion`, `anulacion`, `fecha_notificacion`, `feca_certificado`, `observacion`) VALUES
(1, 'end-to-end Navarra', 'unleash scalable', 'nivel Ergonómico metrics', 'Negro', b'0', b'1', b'1', b'1', '2022-10-16', '2022-10-16', 'sensor invoice architectures'),
(2, 'Analista núcleo Director', 'Silla Multi', 'Electrónica calculate', 'Bedfordshire Genérico', b'1', b'1', b'0', b'1', '2022-10-16', '2022-10-16', 'JBOD withdrawal'),
(3, 'Pantalones relationships', 'mission-critical', 'redundant', 'Artesanal', b'0', b'1', b'1', b'1', '2022-10-16', '2022-10-16', 'redundant'),
(4, 'tiempo', 'services Calidad Chalet', 'Bebes canal', 'Increible facilitate', b'0', b'1', b'1', b'0', '2022-10-16', '2022-10-15', 'Gris'),
(5, 'recontextualize', 'Desarrollador', 'Account', 'Director Prolongación redundant', b'1', b'1', b'1', b'1', '2022-10-15', '2022-10-15', 'Bedfordshire Pescado Reducido'),
(6, 'Amarillo online', 'Muelle', 'CSS', 'Ladrillo', b'0', b'1', b'0', b'0', '2022-10-16', '2022-10-16', 'Métricas'),
(7, 'Franc', 'Práctico Música', 'asíncrona back-end', 'RSS Algodón', b'1', b'1', b'0', b'0', '2022-10-16', '2022-10-16', 'drive haptic'),
(8, 'Money Salud mobile', 'Ingeniero AI Implementación', 'compressing', 'Madera Cambridgeshire', b'1', b'1', b'0', b'1', '2022-10-15', '2022-10-16', 'contingencia'),
(9, 'Marca Cambridgeshire', 'synthesizing Productor', 'cross-platform withdrawal', 'La Investment', b'1', b'1', b'1', b'0', '2022-10-16', '2022-10-16', 'Música Refinado'),
(10, 'deliver connect', 'empower orchestrate', 'Galicia Ladrillo', 'Moldovan extranet', b'1', b'1', b'0', b'1', '2022-10-16', '2022-10-15', 'Apartamento Bebes');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_ecosistema`
--

CREATE TABLE `usuario_ecosistema` (
  `id` bigint(20) NOT NULL,
  `fecha_ingreso` date NOT NULL,
  `bloqueado` bit(1) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `categoria_user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario_ecosistema`
--

INSERT INTO `usuario_ecosistema` (`id`, `fecha_ingreso`, `bloqueado`, `user_id`, `categoria_user_id`) VALUES
(11, '2022-12-13', b'1', 1, 3),
(12, '2022-12-08', b'1', 3, 8);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria_user`
--
ALTER TABLE `categoria_user`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `comenetarios_idea`
--
ALTER TABLE `comenetarios_idea`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_comenetarios_idea__user_id` (`user_id`),
  ADD KEY `fk_comenetarios_idea__idea_id` (`idea_id`);

--
-- Indices de la tabla `componentes`
--
ALTER TABLE `componentes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_componentes__ecosistema_id` (`ecosistema_id`);

--
-- Indices de la tabla `contacto`
--
ALTER TABLE `contacto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_contacto__tipo_contacto_id` (`tipo_contacto_id`);

--
-- Indices de la tabla `databasechangeloglock`
--
ALTER TABLE `databasechangeloglock`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `ecosistema`
--
ALTER TABLE `ecosistema`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ecosistema__user_id` (`user_id`),
  ADD KEY `fk_ecosistema__ecosistema_rol_id` (`ecosistema_rol_id`);

--
-- Indices de la tabla `ecosistema_peticiones`
--
ALTER TABLE `ecosistema_peticiones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ecosistema_peticiones__user_id` (`user_id`),
  ADD KEY `fk_ecosistema_peticiones__ecosistema_id` (`ecosistema_id`);

--
-- Indices de la tabla `ecosistema_rol`
--
ALTER TABLE `ecosistema_rol`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `entidad`
--
ALTER TABLE `entidad`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `idea`
--
ALTER TABLE `idea`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idea__user_id` (`user_id`),
  ADD KEY `fk_idea__reto_id` (`reto_id`),
  ADD KEY `fk_idea__tipo_idea_id` (`tipo_idea_id`),
  ADD KEY `fk_idea__entidad_id` (`entidad_id`);

--
-- Indices de la tabla `innovacion_racionalizacion`
--
ALTER TABLE `innovacion_racionalizacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_innovacion_racionalizacion__tipo_idea_id` (`tipo_idea_id`);

--
-- Indices de la tabla `jhi_authority`
--
ALTER TABLE `jhi_authority`
  ADD PRIMARY KEY (`name`);

--
-- Indices de la tabla `jhi_user`
--
ALTER TABLE `jhi_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_user_login` (`login`),
  ADD UNIQUE KEY `ux_user_email` (`email`);

--
-- Indices de la tabla `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
  ADD PRIMARY KEY (`user_id`,`authority_name`),
  ADD KEY `fk_authority_name` (`authority_name`);

--
-- Indices de la tabla `like_idea`
--
ALTER TABLE `like_idea`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_like_idea__user_id` (`user_id`),
  ADD KEY `fk_like_idea__idea_id` (`idea_id`);

--
-- Indices de la tabla `linea_investigacion`
--
ALTER TABLE `linea_investigacion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `noticias`
--
ALTER TABLE `noticias`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_noticias__user_id` (`user_id`),
  ADD KEY `fk_noticias__ecosistema_id` (`ecosistema_id`),
  ADD KEY `fk_noticias__tipo_noticia_id` (`tipo_noticia_id`);

--
-- Indices de la tabla `ods`
--
ALTER TABLE `ods`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `participantes`
--
ALTER TABLE `participantes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_participantes__proyectos_id` (`proyectos_id`);

--
-- Indices de la tabla `proyectos`
--
ALTER TABLE `proyectos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_proyectos__user_id` (`user_id`),
  ADD KEY `fk_proyectos__ecosistema_id` (`ecosistema_id`),
  ADD KEY `fk_proyectos__tipo_proyecto_id` (`tipo_proyecto_id`);

--
-- Indices de la tabla `redes_sociales`
--
ALTER TABLE `redes_sociales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_redes_sociales__ecosistema_id` (`ecosistema_id`);

--
-- Indices de la tabla `rel_proyectos__linea_investigacion`
--
ALTER TABLE `rel_proyectos__linea_investigacion`
  ADD PRIMARY KEY (`proyectos_id`,`linea_investigacion_id`),
  ADD KEY `fk_rel_proyectos__linea_investigacion__linea_investigacion_id` (`linea_investigacion_id`);

--
-- Indices de la tabla `rel_proyectos__ods`
--
ALTER TABLE `rel_proyectos__ods`
  ADD PRIMARY KEY (`proyectos_id`,`ods_id`),
  ADD KEY `fk_rel_proyectos__ods__ods_id` (`ods_id`);

--
-- Indices de la tabla `rel_proyectos__sector`
--
ALTER TABLE `rel_proyectos__sector`
  ADD PRIMARY KEY (`proyectos_id`,`sector_id`),
  ADD KEY `fk_rel_proyectos__sector__sector_id` (`sector_id`);

--
-- Indices de la tabla `rel_usuario_ecosistema__ecosistema`
--
ALTER TABLE `rel_usuario_ecosistema__ecosistema`
  ADD PRIMARY KEY (`usuario_ecosistema_id`,`ecosistema_id`),
  ADD KEY `fk_rel_usuario_ecosistema__ecosistema__ecosistema_id` (`ecosistema_id`);

--
-- Indices de la tabla `reto`
--
ALTER TABLE `reto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_reto__user_id` (`user_id`),
  ADD KEY `fk_reto__ecosistema_id` (`ecosistema_id`);

--
-- Indices de la tabla `sector`
--
ALTER TABLE `sector`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipo_contacto`
--
ALTER TABLE `tipo_contacto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipo_idea`
--
ALTER TABLE `tipo_idea`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipo_noticia`
--
ALTER TABLE `tipo_noticia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tipo_proyecto`
--
ALTER TABLE `tipo_proyecto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tramite`
--
ALTER TABLE `tramite`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario_ecosistema`
--
ALTER TABLE `usuario_ecosistema`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_usuario_ecosistema__user_id` (`user_id`),
  ADD KEY `fk_usuario_ecosistema__categoria_user_id` (`categoria_user_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria_user`
--
ALTER TABLE `categoria_user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `comenetarios_idea`
--
ALTER TABLE `comenetarios_idea`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `componentes`
--
ALTER TABLE `componentes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `contacto`
--
ALTER TABLE `contacto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `ecosistema`
--
ALTER TABLE `ecosistema`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=82;

--
-- AUTO_INCREMENT de la tabla `ecosistema_peticiones`
--
ALTER TABLE `ecosistema_peticiones`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `ecosistema_rol`
--
ALTER TABLE `ecosistema_rol`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `entidad`
--
ALTER TABLE `entidad`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `idea`
--
ALTER TABLE `idea`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `innovacion_racionalizacion`
--
ALTER TABLE `innovacion_racionalizacion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `jhi_user`
--
ALTER TABLE `jhi_user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `like_idea`
--
ALTER TABLE `like_idea`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `linea_investigacion`
--
ALTER TABLE `linea_investigacion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `noticias`
--
ALTER TABLE `noticias`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `ods`
--
ALTER TABLE `ods`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `participantes`
--
ALTER TABLE `participantes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `proyectos`
--
ALTER TABLE `proyectos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `redes_sociales`
--
ALTER TABLE `redes_sociales`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `reto`
--
ALTER TABLE `reto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `sector`
--
ALTER TABLE `sector`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `tipo_contacto`
--
ALTER TABLE `tipo_contacto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `tipo_idea`
--
ALTER TABLE `tipo_idea`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `tipo_noticia`
--
ALTER TABLE `tipo_noticia`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `tipo_proyecto`
--
ALTER TABLE `tipo_proyecto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `tramite`
--
ALTER TABLE `tramite`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `usuario_ecosistema`
--
ALTER TABLE `usuario_ecosistema`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `comenetarios_idea`
--
ALTER TABLE `comenetarios_idea`
  ADD CONSTRAINT `fk_comenetarios_idea__idea_id` FOREIGN KEY (`idea_id`) REFERENCES `idea` (`id`),
  ADD CONSTRAINT `fk_comenetarios_idea__user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Filtros para la tabla `componentes`
--
ALTER TABLE `componentes`
  ADD CONSTRAINT `fk_componentes__ecosistema_id` FOREIGN KEY (`ecosistema_id`) REFERENCES `ecosistema` (`id`);

--
-- Filtros para la tabla `contacto`
--
ALTER TABLE `contacto`
  ADD CONSTRAINT `fk_contacto__tipo_contacto_id` FOREIGN KEY (`tipo_contacto_id`) REFERENCES `tipo_contacto` (`id`);

--
-- Filtros para la tabla `ecosistema`
--
ALTER TABLE `ecosistema`
  ADD CONSTRAINT `fk_ecosistema__ecosistema_rol_id` FOREIGN KEY (`ecosistema_rol_id`) REFERENCES `ecosistema_rol` (`id`),
  ADD CONSTRAINT `fk_ecosistema__user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Filtros para la tabla `ecosistema_peticiones`
--
ALTER TABLE `ecosistema_peticiones`
  ADD CONSTRAINT `fk_ecosistema_peticiones__ecosistema_id` FOREIGN KEY (`ecosistema_id`) REFERENCES `ecosistema` (`id`),
  ADD CONSTRAINT `fk_ecosistema_peticiones__user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Filtros para la tabla `idea`
--
ALTER TABLE `idea`
  ADD CONSTRAINT `fk_idea__entidad_id` FOREIGN KEY (`entidad_id`) REFERENCES `entidad` (`id`),
  ADD CONSTRAINT `fk_idea__reto_id` FOREIGN KEY (`reto_id`) REFERENCES `reto` (`id`),
  ADD CONSTRAINT `fk_idea__tipo_idea_id` FOREIGN KEY (`tipo_idea_id`) REFERENCES `tipo_idea` (`id`),
  ADD CONSTRAINT `fk_idea__user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Filtros para la tabla `innovacion_racionalizacion`
--
ALTER TABLE `innovacion_racionalizacion`
  ADD CONSTRAINT `fk_innovacion_racionalizacion__tipo_idea_id` FOREIGN KEY (`tipo_idea_id`) REFERENCES `tipo_idea` (`id`);

--
-- Filtros para la tabla `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
  ADD CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Filtros para la tabla `like_idea`
--
ALTER TABLE `like_idea`
  ADD CONSTRAINT `fk_like_idea__idea_id` FOREIGN KEY (`idea_id`) REFERENCES `idea` (`id`),
  ADD CONSTRAINT `fk_like_idea__user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Filtros para la tabla `noticias`
--
ALTER TABLE `noticias`
  ADD CONSTRAINT `fk_noticias__ecosistema_id` FOREIGN KEY (`ecosistema_id`) REFERENCES `ecosistema` (`id`),
  ADD CONSTRAINT `fk_noticias__tipo_noticia_id` FOREIGN KEY (`tipo_noticia_id`) REFERENCES `tipo_noticia` (`id`),
  ADD CONSTRAINT `fk_noticias__user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Filtros para la tabla `participantes`
--
ALTER TABLE `participantes`
  ADD CONSTRAINT `fk_participantes__proyectos_id` FOREIGN KEY (`proyectos_id`) REFERENCES `proyectos` (`id`);

--
-- Filtros para la tabla `proyectos`
--
ALTER TABLE `proyectos`
  ADD CONSTRAINT `fk_proyectos__ecosistema_id` FOREIGN KEY (`ecosistema_id`) REFERENCES `ecosistema` (`id`),
  ADD CONSTRAINT `fk_proyectos__tipo_proyecto_id` FOREIGN KEY (`tipo_proyecto_id`) REFERENCES `tipo_proyecto` (`id`),
  ADD CONSTRAINT `fk_proyectos__user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Filtros para la tabla `redes_sociales`
--
ALTER TABLE `redes_sociales`
  ADD CONSTRAINT `fk_redes_sociales__ecosistema_id` FOREIGN KEY (`ecosistema_id`) REFERENCES `ecosistema` (`id`);

--
-- Filtros para la tabla `rel_proyectos__linea_investigacion`
--
ALTER TABLE `rel_proyectos__linea_investigacion`
  ADD CONSTRAINT `fk_rel_proyectos__linea_investigacion__linea_investigacion_id` FOREIGN KEY (`linea_investigacion_id`) REFERENCES `linea_investigacion` (`id`),
  ADD CONSTRAINT `fk_rel_proyectos__linea_investigacion__proyectos_id` FOREIGN KEY (`proyectos_id`) REFERENCES `proyectos` (`id`);

--
-- Filtros para la tabla `rel_proyectos__ods`
--
ALTER TABLE `rel_proyectos__ods`
  ADD CONSTRAINT `fk_rel_proyectos__ods__ods_id` FOREIGN KEY (`ods_id`) REFERENCES `ods` (`id`),
  ADD CONSTRAINT `fk_rel_proyectos__ods__proyectos_id` FOREIGN KEY (`proyectos_id`) REFERENCES `proyectos` (`id`);

--
-- Filtros para la tabla `rel_proyectos__sector`
--
ALTER TABLE `rel_proyectos__sector`
  ADD CONSTRAINT `fk_rel_proyectos__sector__proyectos_id` FOREIGN KEY (`proyectos_id`) REFERENCES `proyectos` (`id`),
  ADD CONSTRAINT `fk_rel_proyectos__sector__sector_id` FOREIGN KEY (`sector_id`) REFERENCES `sector` (`id`);

--
-- Filtros para la tabla `rel_usuario_ecosistema__ecosistema`
--
ALTER TABLE `rel_usuario_ecosistema__ecosistema`
  ADD CONSTRAINT `fk_rel_usuario_ecosistema__ecosistema__ecosistema_id` FOREIGN KEY (`ecosistema_id`) REFERENCES `ecosistema` (`id`),
  ADD CONSTRAINT `fk_rel_usuario_ecosistema__ecosistema__usuario_ecosistema_id` FOREIGN KEY (`usuario_ecosistema_id`) REFERENCES `usuario_ecosistema` (`id`);

--
-- Filtros para la tabla `reto`
--
ALTER TABLE `reto`
  ADD CONSTRAINT `fk_reto__ecosistema_id` FOREIGN KEY (`ecosistema_id`) REFERENCES `ecosistema` (`id`),
  ADD CONSTRAINT `fk_reto__user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Filtros para la tabla `usuario_ecosistema`
--
ALTER TABLE `usuario_ecosistema`
  ADD CONSTRAINT `fk_usuario_ecosistema__categoria_user_id` FOREIGN KEY (`categoria_user_id`) REFERENCES `categoria_user` (`id`),
  ADD CONSTRAINT `fk_usuario_ecosistema__user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
