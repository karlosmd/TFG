INSERT INTO `roles`(`nombre`) VALUES ('Alumno');
INSERT INTO `roles`(`nombre`) VALUES ('Profesor');

INSERT INTO `usuarios` (`id_usuario`, `activo`, `apellidos`, `email`, `nombre`, `password`) VALUES ('1', '1', 'Merino', 'jorge@ucm.es', 'Jorge', '$2a$10$GoNhjsQMGaHZ5NM4SuvQZux3OubVTosOQvtXX7m1r59BN.FSOvKly');
INSERT INTO `usuario_rol` (`id_rol`, `id_usuario`) VALUES (2, 1);

INSERT INTO `asignaturas` (`id_asignatura`, `activo`, `curso`, `grupo`, `nombre`) VALUES
('1', '1', '2017/2018', '3ºE', 'Modelado del Software'),
('2', '1', '2016/2017', '1ºB', 'Fundamentos de la Programación');
