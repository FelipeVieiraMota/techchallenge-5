CREATE TABLE tb_users (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL
);

-- TODO: Remove this insert when the application changes its database from H2 to PostgresSQL.
-- Don't be in panic about the password, it's just a test password, this insert is gonna be revmoved when the application 
-- changes its database from H2 to PostgresSQL.
-- Password: test123
INSERT INTO tb_users (id, login, password, role)
    VALUES ('b1a5f58e-8af5-4b67-bbdc-61c964c4507d', 'admin', '$2a$10$opfcew1E18S4QyxZap7AHuO5UHgHrRCeKz4NDGdUCoJAxAd3wqP7a', 'ADMIN');
