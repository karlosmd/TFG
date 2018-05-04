INSERT INTO `usuarios` (`id`, `activo`, `nombre`, `apellidos`, `rol`, `email`, `password`) VALUES
(1, 1, 'Jorge', 'Merino', 0, 'jorge@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(2, 1, 'Carlos', 'Marrón', 0, 'carlos@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(3, 1, 'Luis', 'Gómez', 0, 'luis@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(4, 1, 'María', 'Fernández', 0, 'maria@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(5, 1, 'Juan', 'Álvarez', 0, 'juan@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(6, 1, 'Ana', 'González', 0, 'ana@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(7, 1, 'Carolina', 'Ortega', 0, 'carol@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(8, 1, 'Francisco', 'Martín', 0, 'fran@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(9, 1, 'Diego', 'Jiménez', 0, 'diego@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(10, 1, 'Luis', 'Ramírez', 0, 'luisr@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(11, 1, 'Ivan', 'Martínez', 1, 'ivan@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(12, 1, 'Clara', 'Sánchez', 1, 'clara@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(13, 1, 'Jose Luis', 'Fernández', 1, 'jose@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly');

INSERT INTO `alumnos` (`id`, `titulacion`) VALUES
(1, 'GIS'),
(2, 'GIS'),
(3, 'GIS'),
(4, 'GIS'),
(5, 'GII'),
(6, 'GIC'),
(7, 'GII'),
(8, 'DGII'),
(9, 'GIS'),
(10, 'DGII');

INSERT INTO `profesores` (`id`, `departamento`, `despacho`) VALUES
(11, "Hardware", 256),
(12, "IA", 596),
(13, "Informatica", 301);

INSERT INTO `asignaturas` (`id`, `activo`, `nombre`, `grupo`, `curso`, `profesor`) VALUES
(1, 1, 'Modelado del Software', '3ºE', '2017/2018', 11),
(2, 1, 'Fundamentos de la Programación', '1ºB', '2016/2017', 11);
