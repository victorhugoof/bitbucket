CREATE TABLE clientes
(
	id int4 NOT NULL AUTO_INCREMENT,
	cpf varchar(11) NOT NULL,
	nome varchar(50) NOT NULL,
	nome_cidade varchar(50) NOT NULL,
	uf varchar(50) NOT NULL,
	CONSTRAINT PK_clientes PRIMARY KEY (id)
	
);

CREATE TABLE tipo_despesa (
	id int4 NOT NULL AUTO_INCREMENT,
	descricao varchar(50) NOT NULL,
	CONSTRAINT PK_tipo_despesa PRIMARY KEY (id)
);

CREATE TABLE funcionarios
(
	id int4 NOT NULL AUTO_INCREMENT,
	cpf varchar(11) NOT NULL,
	nome varchar(50) NOT NULL,
	data_admissao date NOT NULL,
	data_demissao date,
	CONSTRAINT PK_funcionarios PRIMARY KEY (id)
)
;

CREATE TABLE tempo
(
	id int4 NOT NULL AUTO_INCREMENT,
	ano int4 NOT NULL,
	mes int4 NOT NULL,
	nome_mes varchar(50) NOT NULL,
	CONSTRAINT PK_tempo PRIMARY KEY (id)
)
;

CREATE TABLE despesas
(
	id int4 NOT NULL AUTO_INCREMENT,
	data_despesa date NOT NULL,
	valor numeric(10,2) NOT NULL,
	descricao varchar(50) NOT NULL,
	tipo_despesa int4 NOT NULL,
	tempo int4 NOT NULL, 
	CONSTRAINT PK_despesas PRIMARY KEY (id),
	CONSTRAINT FK_despesas_tempo FOREIGN KEY (tempo) REFERENCES tempo (id),
	CONSTRAINT FK_despesas_tipo FOREIGN KEY (tipo_despesa) REFERENCES tipo_despesa (id)
);

CREATE TABLE vendas
(
	id int4 NOT NULL   AUTO_INCREMENT,
	condicao_pagamento varchar(50) NOT NULL,
	forma_pagamento varchar(50) NOT NULL,
	valor numeric(10,2) NOT NULL,
	quantidade_itens int4 NOT NULL,
	funcionario int4 NOT NULL,
	tempo int4 NOT NULL,
	cliente int4 NOT NULL,
	CONSTRAINT PK_vendas PRIMARY KEY (id),
	CONSTRAINT FK_vendas_funcionarios FOREIGN KEY (funcionario) REFERENCES funcionarios (id),
	CONSTRAINT FK_vendas_tempo FOREIGN KEY (tempo) REFERENCES tempo (id),
	CONSTRAINT FK_vendas_clientes FOREIGN KEY (cliente) REFERENCES clientes (id)
)
;

