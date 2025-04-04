CREATE TABLE tb_users (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL
);


-- TODO: Remove this insert when the application changes its database from H2 to PostgresSQL.
-- Don't be in panic about the passwords, this is just for tests purposes, but these inserts must be removed when the application
-- changes its database from H2 to PostgresSQL.
-- All passwords are : test123

INSERT INTO tb_users (id, login, password, role)
    VALUES ('b1a5f58e-8af5-4b67-bbdc-61c964c4507d', 'admin', '$2a$10$opfcew1E18S4QyxZap7AHuO5UHgHrRCeKz4NDGdUCoJAxAd3wqP7a', 'ADMIN');

INSERT INTO tb_users (id, login, password, role)
    VALUES ('45f0ff7c-5884-454e-b0f8-e958f4de43b3', 'julia', '$2a$10$opfcew1E18S4QyxZap7AHuO5UHgHrRCeKz4NDGdUCoJAxAd3wqP7a', 'MEDICO');

INSERT INTO tb_users (id, login, password, role)
    VALUES ('1d699458-400e-4112-99bc-c2d10e790fc4', 'arthur', '$2a$10$opfcew1E18S4QyxZap7AHuO5UHgHrRCeKz4NDGdUCoJAxAd3wqP7a', 'MEDICO');

INSERT INTO tb_users (id, login, password, role)
    VALUES ('ce939ecf-4a20-4ed4-be44-84384aadf71f', 'guilherme', '$2a$10$opfcew1E18S4QyxZap7AHuO5UHgHrRCeKz4NDGdUCoJAxAd3wqP7a', 'PACIENTE');

INSERT INTO tb_users (id, login, password, role)
    VALUES ('32ea5a93-2ebc-4e1a-9c89-96854ccccf1f', 'roberto', '$2a$10$opfcew1E18S4QyxZap7AHuO5UHgHrRCeKz4NDGdUCoJAxAd3wqP7a', 'PACIENTE');

INSERT INTO tb_users (id, login, password, role)
    VALUES ('ce62c01e-8190-4711-9243-e89046fceb42', 'julio', '$2a$10$opfcew1E18S4QyxZap7AHuO5UHgHrRCeKz4NDGdUCoJAxAd3wqP7a', 'PACIENTE');
