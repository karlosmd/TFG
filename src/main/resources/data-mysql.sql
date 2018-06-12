INSERT INTO `usuarios` (`id`, `activo`, `nombre`, `apellidos`, `rol`, `email`, `password`, `token`) VALUES
(1, 1, 'Jorge', 'Merino', 0, 'jorge@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', '0f9be69baeda4fe19844e09a70275b03'),
(2, 1, 'Carlos', 'Marrón', 0, 'carlos@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', '978a03c438a64a8b804a6e711bc298da'),
(3, 1, 'Luis', 'Gómez', 0, 'luis@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', 'fdc26caae1a14f18bb9fcc34642d3356'),
(4, 1, 'María', 'Fernández', 0, 'maria@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', '275fee1ddf9d491dab18533f210219fe'),
(5, 1, 'Juan', 'Álvarez', 0, 'juan@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', '8b189c60313d403fb0b0bbe2b2bffe4e'),
(6, 1, 'Ana', 'González', 0, 'ana@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', 'd1b9c3f756254f168c0e960c770cca87'),
(7, 1, 'Carolina', 'Ortega', 0, 'carol@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', 'e2d2e3aa33af41f683277f2c58f814e0'),
(8, 1, 'Francisco', 'Martín', 0, 'fran@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', '56548a02c4bf4c2b9603ad34fcca0888'),
(9, 1, 'Diego', 'Jiménez', 0, 'diego@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', '2b85467f69094a0c8229e8498993b8ba'),
(10, 1, 'Luis', 'Ramírez', 0, 'luisr@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', '301e1ba3840140829a353fc7a5d1ef4c'),
(11, 1, 'Ivan', 'Martínez', 1, 'ivan@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', '66e4f626db8a4a0781900b3e9d054c22'),
(12, 1, 'Clara', 'Sánchez', 1, 'clara@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', 'da5e4b0f5e52451ea8fc07e6e52c2aa2'),
(13, 1, 'Jose Luis', 'Fernández', 1, 'jose@ucm.es', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly', 'f309bb7b24a34097a410bb8402701e63');

INSERT INTO `alumnos` (`id`, `titulacion`, `valor`, `id_gamificacion`) VALUES
(1, 'GIS', 0, 20),
(2, 'GIS', 0, 21),
(3, 'GIS', 0, 22),
(4, 'GIS', 0, 23),
(5, 'GII', 0, 24),
(6, 'GIC', 0, 25),
(7, 'GII', 0, 26),
(8, 'DGII', 0, 27),
(9, 'GIS', 0, 28),
(10, 'DGII', 0, 29);

INSERT INTO `profesores` (`id`, `departamento`, `despacho`) VALUES
(11, "Hardware", 256),
(12, "IA", 596),
(13, "Informatica", 301);

INSERT INTO `asignaturas` (`id`, `activo`, `nombre`, `grupo`, `curso`, `profesor`, `id_gamificacion_juego`, `id_gamificacion_grupo`, `nombre_tablero`, `nombre_juego`) VALUES
(1, 1, 'Modelado del Software', '3ºE', '2017/2018', 11, 33, 40, 'ZZZ0', 'ZZZ0'),
(2, 1, 'Fundamentos de la Programación', '1ºB', '2016/2017', 11, 34, 41, 'ZZZZ', 'ZZZZ');
