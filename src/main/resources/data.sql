INSERT INTO app_user (email, firstname, lastname, password, role)
VALUES ( 'tim.leuenberger@bluewin.ch', 'Tim', 'Leuenberger', '$2a$10$OsZWOEPuTg4v.aTqGMl9vOT5OmfmTGax3lzgcAbx3sUc.qPvEQmni', 'USER' );

INSERT INTO task (title, description, completed, created_by)
VALUES ( 'Kekse einkaufen', null, false, 1);

INSERT INTO task (title, description, completed, created_by)
VALUES ( 'Kaffee einkaufen', null, true, 1);

INSERT INTO task (title, description, completed, created_by)
VALUES ('Milch einkaufen', null, false, 1);

INSERT INTO task (title, description, completed, created_by)
VALUES ( 'Zucker einkaufen', null, true, 1);