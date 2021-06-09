CREATE TABLE EMPREGADOS
(
    EMP_NUMERO  BIGSERIAL    NOT NULL,
    EMP_CPF     VARCHAR(255) NOT NULL UNIQUE,
    EMP_NOME    VARCHAR(255) NOT NULL,
    EMP_SALARIO REAL         NOT NULL,
    DEP_NUMERO  BIGINT,
    CONSTRAINT EMP_PKEY PRIMARY KEY (EMP_NUMERO),
    CONSTRAINT EMP_FKEY FOREIGN KEY (DEP_NUMERO) REFERENCES DEPARTAMENTOS (DEP_NUMERO)
);