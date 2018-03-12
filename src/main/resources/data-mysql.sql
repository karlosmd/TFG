INSERT INTO `alumnos` (`id`, `activo`, `nombre`, `apellidos`, `email`, `password`) VALUES
(1, 1, 'Jorge', 'Merino', 'jorge@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(2, 1, 'Carlos', 'Marrón', 'carlos@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(3, 1, 'Luis', 'Gómez', 'luis@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(4, 1, 'María', 'Fernández', 'maria@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(5, 1, 'Juan', 'Álvarez', 'juan@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(6, 1, 'Ana', 'González', 'ana@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(7, 1, 'Carolina', 'Ortega', 'carol@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(8, 1, 'Francisco', 'Martín', 'fran@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(9, 1, 'Diego', 'Jiménez', 'diego@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(10, 1, 'Luis', 'Ramírez', 'luisr@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly');

INSERT INTO `profesores` (`id`, `activo`, `nombre`, `apellidos`, `email`, `password`) VALUES
(1, 1, 'Ivan', 'Martínez', 'ivan@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(2, 1, 'Clara', 'Sánchez', 'clara@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly'),
(3, 1, 'Jose Luis', 'Fernández', 'jose@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly');

INSERT INTO `asignaturas` (`id`, `activo`, `nombre`, `grupo`, `curso`, `profesor`) VALUES
(1, 1, 'Modelado del Software', '3ºE', '2017/2018', 1),
(2, 1, 'Fundamentos de la Programación', '1ºB', '2016/2017', 1);
