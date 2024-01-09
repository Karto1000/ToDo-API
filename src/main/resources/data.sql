INSERT INTO app_user (email, firstname, lastname, password, role)
VALUES ('a@a.ch', 'Andrea', 'Aaa', '$2a$12$WppcFWbq/RaOXZrWLZHe9OUJpLfzgNcvRklLVimWJ0gFX.qbbXEGe', 'USER');

INSERT INTO app_user (email, firstname, lastname, password, role)
VALUES ('b@b.ch', 'Berta', 'Bbb', '$2a$12$K88qhO3xJRffMUcSawWnSePFUI4z32mXLt4tTwwO6mKEY4fpG3B1S', 'USER');

INSERT INTO task (title, description, completed, created_by)
VALUES ('Kekse einkaufen', null, false, 1);

INSERT INTO task (title, description, completed, created_by)
VALUES ('Kaffee einkaufen', null, true, 1);

INSERT INTO task (title, description, completed, created_by)
VALUES ('Milch einkaufen', null, false, 2);

INSERT INTO task (title, description, completed, created_by)
VALUES ('Müesli einkaufen', null, true, 2);
