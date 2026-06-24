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
ON CONFLICT DO NOTHING;

-- ========================
-- REINICIAR SECUENCIADORES (Para evitar DuplicateKeyException al crear nuevos)
-- ========================
SELECT setval(pg_get_serial_sequence('public.generos', 'id'), COALESCE(MAX(id), 1)) FROM public.generos;
SELECT setval(pg_get_serial_sequence('public.autores', 'id'), COALESCE(MAX(id), 1)) FROM public.autores;
SELECT setval(pg_get_serial_sequence('public.libros', 'id'), COALESCE(MAX(id), 1)) FROM public.libros;