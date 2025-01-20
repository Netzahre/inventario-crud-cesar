DROP DATABASE IF EXISTS inventarioEsther;
CREATE DATABASE inventarioEsther;
USE inventarioEsther;

CREATE TABLE Aula (
    idAula INT AUTO_INCREMENT PRIMARY KEY,
    Numeracion VARCHAR(255) NOT NULL,
    Descripcion TEXT,
    IP VARCHAR(15) NOT NULL
);

CREATE TABLE Producto (
    idProducto INT AUTO_INCREMENT PRIMARY KEY,
    Descripcion TEXT NOT NULL,
    EAN INT NOT NULL,
    keyRFID VARCHAR(10) NOT NULL,
    idCategoria INT
);

-- Crear la tabla Categoría
CREATE TABLE Categoria (
    idCategoria INT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    Descripcion TEXT,
    Estado VARCHAR(20) NOT NULL
);

-- Crear la tabla Marcajes
CREATE TABLE Marcajes (
    idMarcaje INT AUTO_INCREMENT PRIMARY KEY,
    idProducto INT NOT NULL,
    idAula INT NOT NULL,
    Tipo INT NOT NULL,
    TimeStamp DATETIME NOT NULL,
    TipoTexto VARCHAR(10),
    FOREIGN KEY (idProducto) REFERENCES Producto(idProducto),
    FOREIGN KEY (idAula) REFERENCES Aula(idAula)
);

CREATE TRIGGER trg_marcajes_tipo
    BEFORE INSERT ON Marcajes
    FOR EACH ROW
BEGIN
    IF NEW.Tipo = 0 THEN SET NEW.TipoTexto = 'Entrada';
    ELSEIF NEW.Tipo = 1 THEN SET NEW.TipoTexto = 'Salida';
END IF;
END;


-- Insertar datos en la tabla 'Aula'
INSERT INTO Aula (Numeracion, Descripcion, IP) VALUES
    ('2.1.1', 'Aula de Computación', '192.168.1.1'),
    ('2.1.2', 'Laboratorio de Física', '192.168.1.2'),
    ('3.2.1', 'Aula de Química', '192.168.2.1'),
    ('1.1.1', 'Aula de Matemáticas', '192.168.0.1'),
    ('3.1.1', 'Aula de Audiovisuales', '192.168.3.1');

-- Insertar datos en la tabla 'Producto'
INSERT INTO Producto (Descripcion, EAN, keyRFID, idCategoria) VALUES
    ('Osciloscopio marca Acme NS123', 123, 'RFID123', 1),
    ('Multímetro digital FL-20', 3452, 'RFID456', 2),
    ('Proyector Epson X40', 5435, 'RFID789', 3),
    ('Pizarra digital SmartBoard', 65464, 'RFID111', 4),
    ('Microscopio Carl Zeiss', 5435, 'RFID222', 5);

-- Insertar datos en la tabla 'Categoría'
INSERT INTO Categoria (Nombre, Descripcion, Estado) VALUES
    ('Electrónica', 'Equipo relacionado con circuitos y mediciones', 'Activo'),
    ('Audio Visual', 'Equipos relacionados con presentaciones', 'Activo'),
    ('Laboratorio', 'Equipos utilizados en prácticas de laboratorio', 'Activo'),
    ('Educación', 'Material de apoyo en enseñanza', 'Activo'),
    ('Biología', 'Equipos utilizados en estudios biológicos', 'Activo');

-- Insertar datos en la tabla 'Marcajes'
INSERT INTO Marcajes (idProducto, idAula, Tipo, TimeStamp) VALUES
    (1, 1, 0, '2025-01-01 08:00:00'),
    (2, 2, 0, '2025-01-01 09:00:00'),
    (3, 3, 0, '2025-01-01 10:00:00'),
    (4, 4, 0, '2025-01-01 11:00:00'),
    (5, 5, 0, '2025-01-01 12:00:00');
