-- ========================
-- GÉNEROS
-- ========================
INSERT INTO generos (id, nombre) VALUES 
(1, 'Realismo Mágico'),
(2, 'Novela Histórica'),
(3, 'Drama'),
(4, 'Fantasía'),
(5, 'Ciencia Ficción'),
(6, 'Misterio'),
(7, 'Romance'),
(8, 'Aventura')
ON CONFLICT (id) DO NOTHING;

-- ========================
-- AUTORES
-- ========================
INSERT INTO autores (id, nombre, url_foto) VALUES 
(1, 'Gabriel García Márquez', NULL),
(2, 'Mario Vargas Llosa', NULL),
(3, 'Isabel Allende', NULL),
(4, 'J.R.R. Tolkien', NULL),
(5, 'George Orwell', NULL),
(6, 'Julio Verne', NULL),
(7, 'Agatha Christie', NULL),
(8, 'Jane Austen', NULL),
(9, 'Antoine de Saint-Exupéry', NULL),
(10, 'Miguel de Cervantes', NULL),
(11, 'Franz Kafka', NULL),
(12, 'Ernest Hemingway', NULL),
(13, 'Harper Lee', NULL),
(14, 'Fiódor Dostoyevski', NULL),
(15, 'Arthur Conan Doyle', NULL),
(16, 'Aldous Huxley', NULL)
ON CONFLICT (id) DO NOTHING;

-- ========================
-- LIBROS
-- ========================
INSERT INTO libros (id, titulo, anio_publicacion, stock, disponible, portada, genero_id) VALUES 
(1, 'Cien Años de Soledad', 1967, 5, true, NULL, 1),
(2, 'La Ciudad y los Perros', 1963, 3, true, NULL, 2),
(3, 'La Casa de los Espíritus', 1982, 0, false, NULL, 1),
(4, 'El Señor de los Anillos', 1954, 8, true, NULL, 4),
(5, '1984', 1949, 6, true, NULL, 5),
(6, 'Viaje al Centro de la Tierra', 1864, 4, true, NULL, 8),
(7, 'Asesinato en el Orient Express', 1934, 5, true, NULL, 6),
(8, 'Orgullo y Prejuicio', 1813, 7, true, NULL, 7),
(9, 'El Principito', 1943, 10, true, NULL, 8),
(10, 'Don Quijote de la Mancha', 1605, 2, true, NULL, 8),
(11, 'La Metamorfosis', 1915, 3, true, NULL, 3),
(12, 'El Viejo y el Mar', 1952, 4, true, NULL, 3),
(13, 'Matar a un Ruiseñor', 1960, 6, true, NULL, 3),
(14, 'Crimen y Castigo', 1866, 2, true, NULL, 3),
(15, 'Estudio en Escarlata', 1887, 5, true, NULL, 6),
(16, 'Un Mundo Feliz', 1932, 4, true, NULL, 5)
ON CONFLICT (id) DO NOTHING;

-- ========================
-- RELACIÓN LIBROS - AUTORES 
-- ========================
INSERT INTO libro_autor (libro_id, autor_id) VALUES 
(1, 1), 
(2, 2), 
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12),
(13, 13),
(14, 14),
(15, 15),
(16, 16)
ON CONFLICT (libro_id, autor_id) DO NOTHING;

-- ========================
-- USUARIOS (Solo data de prueba, IDs empiezan en 10 para no chocar con el CommandLineRunner)
-- ========================
INSERT INTO usuarios (id, celular, email, password, role, is_2fa_enabled, username) VALUES 
(10, '987654321', 'carlos.ruiz@gmail.com', '$2a$10$oyprJT0WXTsNtNobgWR/E.OGUFrFUHN0pIJR/aEEsB0ZV/xcEoag.', 'ROLE_USER', false, 'cruiz'),
(11, '987654322', 'maria.gomez@gmail.com', '$2a$10$oyprJT0WXTsNtNobgWR/E.OGUFrFUHN0pIJR/aEEsB0ZV/xcEoag.', 'ROLE_USER', false, 'mgomez'),
(12, '987654323', 'juan.perez@gmail.com', '$2a$10$oyprJT0WXTsNtNobgWR/E.OGUFrFUHN0pIJR/aEEsB0ZV/xcEoag.', 'ROLE_USER', false, 'jperez'),
(13, '987654324', 'ana.torres@gmail.com', '$2a$10$oyprJT0WXTsNtNobgWR/E.OGUFrFUHN0pIJR/aEEsB0ZV/xcEoag.', 'ROLE_USER', false, 'atorres'),
(14, '987654325', 'luis.diaz@gmail.com', '$2a$10$oyprJT0WXTsNtNobgWR/E.OGUFrFUHN0pIJR/aEEsB0ZV/xcEoag.', 'ROLE_USER', false, 'ldiaz'),
(15, '987654326', 'elena.rojas@gmail.com', '$2a$10$oyprJT0WXTsNtNobgWR/E.OGUFrFUHN0pIJR/aEEsB0ZV/xcEoag.', 'ROLE_USER', false, 'erojas'),
(16, '987654327', 'pedro.castro@gmail.com', '$2a$10$oyprJT0WXTsNtNobgWR/E.OGUFrFUHN0pIJR/aEEsB0ZV/xcEoag.', 'ROLE_USER', false, 'pcastro'),
(17, '987654328', 'lucia.mendez@gmail.com', '$2a$10$oyprJT0WXTsNtNobgWR/E.OGUFrFUHN0pIJR/aEEsB0ZV/xcEoag.', 'ROLE_USER', false, 'lmendez'),
(18, '987654329', 'diego.salazar@gmail.com', '$2a$10$oyprJT0WXTsNtNobgWR/E.OGUFrFUHN0pIJR/aEEsB0ZV/xcEoag.', 'ROLE_USER', false, 'dsalazar'),
(19, '987654330', 'valeria.vega@gmail.com', '$2a$10$oyprJT0WXTsNtNobgWR/E.OGUFrFUHN0pIJR/aEEsB0ZV/xcEoag.', 'ROLE_USER', false, 'vvega')
ON CONFLICT (id) DO NOTHING;

-- ========================
-- PRÉSTAMOS (15 registros variados para alimentar el Dashboard)
-- ========================
INSERT INTO prestamos (id, estado, fecha_solicitud, fecha_recojo, fecha_devolucion, qr_code, libro_id, usuario_id) VALUES 
-- HISTÓRICOS (Finalizados)
(1, 'FINALIZADO', '2026-06-01', '2026-06-02', '2026-06-15', NULL, 1, 10),
(2, 'FINALIZADO', '2026-06-10', '2026-06-11', '2026-06-25', NULL, 4, 11),
(3, 'FINALIZADO', '2026-06-15', '2026-06-16', '2026-06-30', NULL, 7, 12),
(4, 'FINALIZADO', '2026-06-20', '2026-06-22', '2026-07-05', NULL, 9, 13),
(5, 'FINALIZADO', '2026-06-25', '2026-06-26', '2026-07-10', NULL, 12, 14),
-- RECIENTES (Pendientes de revisión por el Bibliotecario)
(6, 'PENDIENTE', '2026-07-10', NULL, NULL, NULL, 2, 15),
(7, 'PENDIENTE', '2026-07-11', NULL, NULL, NULL, 5, 16),
(8, 'PENDIENTE', '2026-07-12', NULL, NULL, NULL, 8, 17),
(9, 'PENDIENTE', '2026-07-12', NULL, NULL, NULL, 16, 18),
-- EN CURSO (Aprobados, algunos ya recogidos y otros esperando al usuario)
(10, 'APROBADO', '2026-07-05', '2026-07-06', NULL, NULL, 13, 19),
(11, 'APROBADO', '2026-07-08', '2026-07-09', NULL, NULL, 11, 10),
(12, 'APROBADO', '2026-07-09', NULL, NULL, NULL, 14, 11), 
-- RECHAZADOS (Por falta de stock o penalidades)
(13, 'RECHAZADO', '2026-07-01', NULL, NULL, NULL, 3, 12), 
(14, 'RECHAZADO', '2026-07-02', NULL, NULL, NULL, 15, 13),
(15, 'RECHAZADO', '2026-07-03', NULL, NULL, NULL, 10, 14)
ON CONFLICT (id) DO NOTHING;

-- ========================
-- REINICIAR SECUENCIADORES 
-- ========================
SELECT setval(pg_get_serial_sequence('public.generos', 'id'), COALESCE(MAX(id), 1)) FROM public.generos;
SELECT setval(pg_get_serial_sequence('public.autores', 'id'), COALESCE(MAX(id), 1)) FROM public.autores;
SELECT setval(pg_get_serial_sequence('public.libros', 'id'), COALESCE(MAX(id), 1)) FROM public.libros;
SELECT setval(pg_get_serial_sequence('public.usuarios', 'id'), COALESCE(MAX(id), 19)) FROM public.usuarios;
SELECT setval(pg_get_serial_sequence('public.prestamos', 'id'), COALESCE(MAX(id), 15)) FROM public.prestamos;