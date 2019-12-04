INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('raul', '$2a$10$5.PvnJ2.DATPO2kUPdJhyee0JXZF9fw7TCX60OOFRYuA.feBCh3Vu', 1, 'Raul', 'Bazan', 'raulb@correo.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('alexis', '$2a$10$Ju2Qlr4DoveYdY4UwWlLNeJdPOItCgmWTB/Uh4Dkzw9xoxNdjsssy', 1, 'Alexis', 'Angulo', 'alexisa@correo.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);