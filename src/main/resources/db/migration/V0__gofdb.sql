CREATE TABLE departamentos (
	dnumero SERIAL NOT NULL,
	dnome varchar(255) NOT NULL,
    UNIQUE(dnome),
	CONSTRAINT departamento_pkey PRIMARY KEY (dnumero)
);

CREATE TABLE empregados (
	cpf varchar(255) NOT NULL,
	enome varchar(255),
    salario real,
    cpf_supervisor varchar(255),
    dnumero int,
	CONSTRAINT empregados_pkey  PRIMARY KEY (cpf),
    CONSTRAINT empregados_fkey  FOREIGN KEY (cpf_supervisor) REFERENCES empregados(cpf),
    CONSTRAINT empregados_fkey2 FOREIGN KEY (dnumero) REFERENCES departamentos(dnumero)
);

CREATE TABLE projetos (
	pnumero SERIAL NOT NULL,
	pnome varchar(255) NOT NULL,
    dnumero int,
    UNIQUE(pnome),
	CONSTRAINT projeto_pkey PRIMARY KEY (pnumero),
    CONSTRAINT projeto_fkey2 FOREIGN KEY (dnumero) REFERENCES departamentos(dnumero)
);

CREATE TABLE trabalha (
    id SERIAL NOT NULL,
	cpf varchar(255) NOT NULL,
    pnumero int NOT NULL,
    inicio date NOT NULL,
    termino date NOT NULL,
    CONSTRAINT trabalha_pkey PRIMARY KEY (id),
    CONSTRAINT trabalha_fkey FOREIGN KEY (pnumero) REFERENCES projetos(pnumero),
    CONSTRAINT trabalha_fkey2  FOREIGN KEY (cpf) REFERENCES empregados(cpf)
);

ALTER TABLE departamentos ADD COLUMN cpf_gerente varchar(255);
ALTER TABLE departamentos ADD FOREIGN KEY (cpf_gerente) REFERENCES empregados(cpf);