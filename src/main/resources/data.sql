INSERT INTO posts (id, content, description, title) VALUES (1, 'The content of the first posts', 'the post that was posted first', 'First post of all');
INSERT INTO posts (id, content, description, title) VALUES (2, 'The content of the second posts', 'the post that was posted second', 'Second post');

INSERT INTO comments (id, body, email, name, post_id) VALUES (1, 'Cool post, loved it!!', 'test@email.com', 'Radu', 1);
INSERT INTO comments (id, body, email, name, post_id) VALUES (2, 'Cool post, liked it!!', 'test@email.com', 'Alex', 1);

INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_USER');

# password is password for both users
INSERT INTO users (id, email, name, password, username) VALUES (1, 'first@gmail.com', 'first', '$2a$10$4/Ep/FneQKE1P8YcLkxefefo.60ed95ntIKRx2buOfcJIJRAM2vbW', 'firstUser');
INSERT INTO users (id, email, name, password, username) VALUES (2, 'second@gmail.com', 'second', '$2a$10$4/Ep/FneQKE1P8YcLkxefefo.60ed95ntIKRx2buOfcJIJRAM2vbW', 'secondUser');

INSERT INTO useer_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO useer_roles(user_id, role_id) VALUES (2, 2);