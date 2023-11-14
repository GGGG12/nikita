CREATE TABLE IF NOT EXISTS users
(
    user_id bigint NOT NULL DEFAULT nextval('users_user_id_seq'::regclass),
    email character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    phone character varying(255) COLLATE pg_catalog."default",
    surname character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pk PRIMARY KEY (user_id)
);

INSERT INTO users(
    email, name, phone, surname)
VALUES ('test@test.ru', 'testName', '89204441019', 'testSurname');

INSERT INTO users(
    email, name, phone, surname)
VALUES ('check@test.ru', 'checkName', '89215541019', 'checkSurname');



