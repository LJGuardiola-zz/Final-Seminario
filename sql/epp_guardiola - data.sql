INSERT INTO cities (id, name, postcode) VALUES (1, 'Carmen de Patagones', '8504');
INSERT INTO cities (id, name, postcode) VALUES (2, 'Viedma', '8500');

INSERT INTO roles (id, name, enabled) VALUES (1, 'Administrador', 1);
INSERT INTO roles (id, name, enabled) VALUES (2, 'Gobierno', 1);
INSERT INTO roles (id, name, enabled) VALUES (3, 'Ciudadano', 1);

INSERT INTO permissions (id, code, description) VALUES (1, 'role_create', 'Crear roles');
INSERT INTO permissions (id, code, description) VALUES (2, 'role_read', 'Visualizar roles');
INSERT INTO permissions (id, code, description) VALUES (3, 'role_update', 'Modificar roles');
INSERT INTO permissions (id, code, description) VALUES (4, 'role_delete', 'Eliminar roles');
INSERT INTO permissions (id, code, description) VALUES (5, 'user_create', 'Crear usuarios');
INSERT INTO permissions (id, code, description) VALUES (6, 'user_read', 'Visualizar usuarios');
INSERT INTO permissions (id, code, description) VALUES (7, 'user_update', 'Modificar usuarios');
INSERT INTO permissions (id, code, description) VALUES (8, 'user_delete', 'Eliminar usuarios');
INSERT INTO permissions (id, code, description) VALUES (9, 'person_create', 'Crear personas');
INSERT INTO permissions (id, code, description) VALUES (10, 'person_read', 'Visualizar personas');
INSERT INTO permissions (id, code, description) VALUES (11, 'person_update', 'Modificar personas');
INSERT INTO permissions (id, code, description) VALUES (12, 'person_delete', 'Eliminar personas');
INSERT INTO permissions (id, code, description) VALUES (13, 'category_create', 'Crear categorías');
INSERT INTO permissions (id, code, description) VALUES (14, 'category_read', 'Visualizar categorías');
INSERT INTO permissions (id, code, description) VALUES (15, 'category_update', 'Modificar categorías');
INSERT INTO permissions (id, code, description) VALUES (16, 'category_delete', 'Eliminar categorías');
INSERT INTO permissions (id, code, description) VALUES (17, 'campaign_create', 'Crear campañas');
INSERT INTO permissions (id, code, description) VALUES (18, 'campaign_read', 'Visualizar categorías');
INSERT INTO permissions (id, code, description) VALUES (19, 'campaign_update', 'Modificar campañas');
INSERT INTO permissions (id, code, description) VALUES (20, 'campaign_delete', 'Eliminar campañas');
INSERT INTO permissions (id, code, description) VALUES (21, 'space_create', 'Crear espacios');
INSERT INTO permissions (id, code, description) VALUES (22, 'space_read', 'Visualizar espacios');
INSERT INTO permissions (id, code, description) VALUES (23, 'space_update', 'Modificar espacios');
INSERT INTO permissions (id, code, description) VALUES (24, 'space_delete', 'Eliminar espacios');

INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 2);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 3);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 4);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 5);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 6);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 7);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 8);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 9);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 9);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 10);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 10);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 11);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 11);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 12);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 13);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 13);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 14);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 14);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 15);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 15);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 16);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 16);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 17);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 17);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 18);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 18);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 19);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 19);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 20);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 20);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 21);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 21);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 22);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 22);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 23);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 23);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (1, 24);
INSERT INTO role_has_permission (role_id, permission_id) VALUES (2, 24);

INSERT INTO users (id, username, email, password, state, role_id) VALUES (1, 'adm', 'adm@site.com', 'secret', 1, 1);
INSERT INTO users (id, username, email, password, state, role_id) VALUES (2, 'gob', 'gob@site.com', 'secret', 1, 2);
INSERT INTO users (id, username, email, password, state, role_id) VALUES (3, 'ljguardiola', 'ljguardiola@gmail.com', 'secret', 1, 3);

INSERT INTO persons (id, is_natural) VALUES (1, 1);
INSERT INTO persons (id, is_natural) VALUES (2, 0);
INSERT INTO persons (id, is_natural) VALUES (3, 0);

INSERT INTO legal_persons (person_id, cuit, name) VALUES (2, '30-50673003-8', 'IMPORTADORA Y EXPORTADORA DE LA PATAGONIA S.A.');
INSERT INTO legal_persons (person_id, cuit, name) VALUES (3, '30-54668997-9', 'YPF SOCIEDAD ANONIMA');

INSERT INTO natural_persons (person_id, cuil, first_name, last_name, birthdate) VALUES (1, '20-39743808-3', 'Lucas Joel', 'Guardiola', '1996-05-10')

INSERT INTO citizens (id, natural_person_id, user_id) VALUES (1, 1, 3);

INSERT INTO space_campaigns (id, message) VALUES (1, 'Asegúrate de que cada uno mantenga los dos metros de distancia, recuerde utilizar Tapabocas');
INSERT INTO space_campaigns (id, message) VALUES (2, 'Gracias por disfrutar el espacio, RECUERDE: lavarse las manos y mantener distancia');

INSERT INTO space_categories (id, name, description) VALUES (1, 'Tienda de comestibles', 'Tienda de comestibles');
INSERT INTO space_categories (id, name, description) VALUES (2, 'Restaurantes', 'Restaurantes');
INSERT INTO space_categories (id, name, description) VALUES (3, 'Bancos', 'Bancos');
INSERT INTO space_categories (id, name, description) VALUES (4, 'Farmacias', 'Farmacias');
INSERT INTO space_categories (id, name, description) VALUES (5, 'Hospitales', 'Hospitales');
INSERT INTO space_categories (id, name, description) VALUES (6, 'Gasolineras', 'Gasolineras');
INSERT INTO space_categories (id, name, description) VALUES (7, 'Plazas', 'Plazas');
INSERT INTO space_categories (id, name, description) VALUES (8, 'Balnearios', 'Balnearios');

INSERT INTO spaces (id, name, available, is_closed, capacity, latitude, longitude, radius, category_id, entry_campaign_id, exit_campaign_id) VALUES (1, 'La Anónima', 1, 1, 70, -40.7980539, -62.9803166, 50, 1, 1, 2);
INSERT INTO spaces (id, name, available, is_closed, capacity, latitude, longitude, radius, category_id, entry_campaign_id, exit_campaign_id) VALUES (2, 'YPF (Bertolone)', 1, 1, 150, -40.7939599, -62.9950746, 100, 6, 1, 2);
INSERT INTO spaces (id, name, available, is_closed, capacity, latitude, longitude, radius, category_id, entry_campaign_id, exit_campaign_id) VALUES (3, 'Plaza 7 de Marzo', 1, 0, 200, -40.8024396, -62.9834717, 100, 7, 1, 2);
INSERT INTO spaces (id, name, available, is_closed, capacity, latitude, longitude, radius, category_id, entry_campaign_id, exit_campaign_id) VALUES (4, 'Plaza Villarino', 1, 0, 1000, -40.7984739, -62.9811552, 400, 7, 1, 2);

INSERT INTO closed_spaces (space_id, street, city_id) VALUES (1, 'San Lorenzo 59', 1);
INSERT INTO closed_spaces (space_id, street, city_id) VALUES (2, 'Bertolone 300', 1);

INSERT INTO responsible (closed_space_id, person_id, start_date, end_date) VALUES (1, 2, '2021-05-03 22:49:11', null);
INSERT INTO responsible (closed_space_id, person_id, start_date, end_date) VALUES (2, 3, '2021-05-03 22:53:02', null);

INSERT INTO device_brands (id, name) VALUES (1, 'Apple');
INSERT INTO device_brands (id, name) VALUES (2, 'Google');
INSERT INTO device_brands (id, name) VALUES (3, 'HTC');
INSERT INTO device_brands (id, name) VALUES (4, 'Lenovo');
INSERT INTO device_brands (id, name) VALUES (5, 'LG');
INSERT INTO device_brands (id, name) VALUES (6, 'Motorola');
INSERT INTO device_brands (id, name) VALUES (7, 'Nokia');
INSERT INTO device_brands (id, name) VALUES (8, 'Samsung');
INSERT INTO device_brands (id, name) VALUES (9, 'Sony');
INSERT INTO device_brands (id, name) VALUES (10, 'Xiaomi');

INSERT INTO device_models (id, name, device_brand_id) VALUES (1, 'Samsung Galaxy A10', 8);
INSERT INTO device_models (id, name, device_brand_id) VALUES (2, 'Samsung Galaxy A20', 8);
INSERT INTO device_models (id, name, device_brand_id) VALUES (3, 'Samsung Galaxy A30', 8);
INSERT INTO device_models (id, name, device_brand_id) VALUES (4, 'Samsung Galaxy S10', 8);
INSERT INTO device_models (id, name, device_brand_id) VALUES (5, 'Samsung Galaxy S20', 8);
INSERT INTO device_models (id, name, device_brand_id) VALUES (6, 'iPhone X', 1);
INSERT INTO device_models (id, name, device_brand_id) VALUES (7, 'iPhone Xs', 1);
INSERT INTO device_models (id, name, device_brand_id) VALUES (8, 'iPhone 11', 1);
INSERT INTO device_models (id, name, device_brand_id) VALUES (9, 'iPhone SE 2', 1);
INSERT INTO device_models (id, name, device_brand_id) VALUES (10, 'iPhone 12', 1);
INSERT INTO device_models (id, name, device_brand_id) VALUES (11, 'Google Pixel 3', 2);
INSERT INTO device_models (id, name, device_brand_id) VALUES (12, 'Google Pixel 4 XL', 2);
INSERT INTO device_models (id, name, device_brand_id) VALUES (13, 'Google Pixel 4', 2);
INSERT INTO device_models (id, name, device_brand_id) VALUES (14, 'Google Pixel 4a', 2);
INSERT INTO device_models (id, name, device_brand_id) VALUES (15, 'Google Pixel 5', 2);
INSERT INTO device_models (id, name, device_brand_id) VALUES (16, 'Ultra', 3);
INSERT INTO device_models (id, name, device_brand_id) VALUES (17, 'HTC U11', 3);
INSERT INTO device_models (id, name, device_brand_id) VALUES (18, 'HTC U11+', 3);
INSERT INTO device_models (id, name, device_brand_id) VALUES (19, 'HTC U12+', 3);
INSERT INTO device_models (id, name, device_brand_id) VALUES (20, 'Desire 20 pro', 3);
INSERT INTO device_models (id, name, device_brand_id) VALUES (21, 'A6 Note', 4);
INSERT INTO device_models (id, name, device_brand_id) VALUES (22, 'K10 Note', 4);
INSERT INTO device_models (id, name, device_brand_id) VALUES (23, 'K10 Plus', 4);
INSERT INTO device_models (id, name, device_brand_id) VALUES (24, 'Legion Duel', 4);
INSERT INTO device_models (id, name, device_brand_id) VALUES (25, 'Legion Phone Duel 2', 4);
INSERT INTO device_models (id, name, device_brand_id) VALUES (26, 'K71', 5);
INSERT INTO device_models (id, name, device_brand_id) VALUES (27, 'K52', 5);
INSERT INTO device_models (id, name, device_brand_id) VALUES (28, 'K62', 5);
INSERT INTO device_models (id, name, device_brand_id) VALUES (29, 'Q52', 5);
INSERT INTO device_models (id, name, device_brand_id) VALUES (30, 'K92 5G', 5);
INSERT INTO device_models (id, name, device_brand_id) VALUES (31, 'Moto G Play', 6);
INSERT INTO device_models (id, name, device_brand_id) VALUES (32, 'Edge S', 6);
INSERT INTO device_models (id, name, device_brand_id) VALUES (33, 'Moto E7 Power', 6);
INSERT INTO device_models (id, name, device_brand_id) VALUES (34, 'Moto G60', 6);
INSERT INTO device_models (id, name, device_brand_id) VALUES (35, 'Moto G20', 6);
INSERT INTO device_models (id, name, device_brand_id) VALUES (36, 'C20', 7);
INSERT INTO device_models (id, name, device_brand_id) VALUES (37, 'G10', 7);
INSERT INTO device_models (id, name, device_brand_id) VALUES (38, 'G20', 7);
INSERT INTO device_models (id, name, device_brand_id) VALUES (39, 'X10', 7);
INSERT INTO device_models (id, name, device_brand_id) VALUES (40, 'X20', 7);
INSERT INTO device_models (id, name, device_brand_id) VALUES (41, 'Xperia 5 II', 9);
INSERT INTO device_models (id, name, device_brand_id) VALUES (42, 'Xperia Pro', 9);
INSERT INTO device_models (id, name, device_brand_id) VALUES (43, 'Xperia 10 III', 9);
INSERT INTO device_models (id, name, device_brand_id) VALUES (44, 'Xperia 5 III', 9);
INSERT INTO device_models (id, name, device_brand_id) VALUES (45, 'Xperia 1 III', 9);
INSERT INTO device_models (id, name, device_brand_id) VALUES (46, 'Mi 10 Lite', 10);
INSERT INTO device_models (id, name, device_brand_id) VALUES (47, 'Redmi Note 9T', 10);
INSERT INTO device_models (id, name, device_brand_id) VALUES (48, 'Redmi Note 10 Pro Max', 10);
INSERT INTO device_models (id, name, device_brand_id) VALUES (49, 'Mi 10S', 10);
INSERT INTO device_models (id, name, device_brand_id) VALUES (50, 'Mi 11X Pro', 10);

INSERT INTO device_companies (id, name) VALUES (1, 'Claro');
INSERT INTO device_companies (id, name) VALUES (2, 'Movistar');
INSERT INTO device_companies (id, name) VALUES (3, 'Personal');
INSERT INTO device_companies (id, name) VALUES (4, 'Tuenti');

