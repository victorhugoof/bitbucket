CREATE OR REPLACE FUNCTION public.dat_atualizacao_trigger_function()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$ 
BEGIN
    NEW.dat_atualizacao := 'now()';
    RETURN NEW;
END;
$function$
;

--- ESTADO
CREATE TABLE estado (
	id int8 NOT NULL,
	uf_estado varchar(2) NOT NULL,
	nom_estado varchar(128) NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT estado_pkey PRIMARY KEY (id),
	CONSTRAINT estado_nom_estado_uk UNIQUE (nom_estado),
	CONSTRAINT estado_uf_estado_uk UNIQUE (uf_estado)
);

CREATE
    TRIGGER estado_dat_atualizacao_trigger BEFORE UPDATE
        ON
        estado FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;

--- CIDADE
CREATE TABLE cidade (
	id int8 NOT NULL,
	nom_cidade varchar(128) NOT NULL,
	cod_estado int8 NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT cidade_pkey PRIMARY KEY (id),
	CONSTRAINT cidade_cod_estado_fkey FOREIGN KEY (cod_estado) REFERENCES estado(id),
	CONSTRAINT cidade_nom_cidade_uf_estado_uk UNIQUE (nom_cidade,cod_estado)
);

CREATE
    TRIGGER cidade_dat_atualizacao_trigger BEFORE UPDATE
        ON
        cidade FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;
        
--- PRODUTO
CREATE SEQUENCE produto_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE produto (
	id int8 DEFAULT nextval('produto_id_seq'::regclass) NOT NULL,
	cod_barras varchar(15),
	nom_descricao varchar(128) NOT NULL,
	nom_observacoes text,
	flg_desconto varchar(1) NOT NULL,
	vlr_venda numeric(14,4) NOT NULL,
	qtd_estoque int4 NOT NULL,
	flg_servico varchar(1) NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT produto_pkey PRIMARY KEY (id)
);

CREATE
    TRIGGER produto_dat_atualizacao_trigger BEFORE UPDATE
        ON
        produto FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;

--- CLIENTE
CREATE SEQUENCE cliente_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE cliente (
	id int8 DEFAULT nextval('cliente_id_seq'::regclass) NOT NULL,
	nom_pessoa varchar(128) NOT NULL,
	num_cpf varchar(11),
	flg_sexo varchar(1) NOT NULL,
	dat_nascimento timestamptz,
	vlr_limite_credito numeric(14,4) NOT NULL,
	vlr_credito_utilizado numeric(14,4) NOT NULL,
	num_telefone varchar(14),
	cod_cidade int8,
	nom_observacoes text,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT cliente_pkey PRIMARY KEY (id),
	CONSTRAINT cliente_cpf_uk UNIQUE (num_cpf),
	CONSTRAINT cliente_cidade_fkey FOREIGN KEY (cod_cidade) REFERENCES cidade(id)
);

CREATE
    TRIGGER cliente_dat_atualizacao_trigger BEFORE UPDATE
        ON
        cliente FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;
        
--- DEPENDENTE
CREATE SEQUENCE dependente_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE dependente (
	id int8 DEFAULT nextval('dependente_id_seq'::regclass) NOT NULL,
	nom_dependente varchar(128) NOT NULL,
	cod_cliente int8 NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT dependente_pkey PRIMARY KEY (id),
	CONSTRAINT dependente_nome_cliente_uk UNIQUE (nom_dependente,cod_cliente),
	CONSTRAINT dependente_cliente_fkey FOREIGN KEY (cod_cliente) REFERENCES cliente(id)
);

CREATE
    TRIGGER dependente_dat_atualizacao_trigger BEFORE UPDATE
        ON
        dependente FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;
        
--- CONDICAO PAGAMENTO
CREATE SEQUENCE condicao_pagamento_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE condicao_pagamento (
	id int8 DEFAULT nextval('condicao_pagamento_id_seq'::regclass) NOT NULL,
	nom_condicao varchar(128) NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT condicao_pagamento_pkey PRIMARY KEY (id),
	CONSTRAINT condicao_pagamento_nome_uk UNIQUE (nom_condicao)
);

CREATE
    TRIGGER condicao_pagamento_dat_atualizacao_trigger BEFORE UPDATE
        ON
        condicao_pagamento FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();; 

--- FORMA PAGAMENTO
CREATE SEQUENCE forma_pagamento_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE forma_pagamento (
	id int8 DEFAULT nextval('forma_pagamento_id_seq'::regclass) NOT NULL,
	nom_forma varchar(128) NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id),
	CONSTRAINT forma_pagamento_nome_uk UNIQUE (nom_forma)
);

CREATE
    TRIGGER forma_pagamento_dat_atualizacao_trigger BEFORE UPDATE
        ON
        forma_pagamento FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;
        
--- GRUPO ACESSO
CREATE SEQUENCE grupo_acesso_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE grupo_acesso (
	id int8 DEFAULT nextval('grupo_acesso_id_seq'::regclass) NOT NULL,
	nom_grupo varchar(128) NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT grupo_acesso_pkey PRIMARY KEY (id),
	CONSTRAINT grupo_acesso_nome_uk UNIQUE (nom_grupo)
);

CREATE
    TRIGGER grupo_acesso_dat_atualizacao_trigger BEFORE UPDATE
        ON
        grupo_acesso FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;        
        
--- DESPESA
CREATE SEQUENCE despesa_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE despesa (
	id int8 DEFAULT nextval('despesa_id_seq'::regclass) NOT NULL,
	nom_despesa varchar(128) NOT NULL,
	vlr_despesa numeric(14,4) NOT NULL,
	flg_recorrente varchar(1) NOT NULL,
	dat_vencimento timestamptz,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT despesa_pkey PRIMARY KEY (id)
);

CREATE
    TRIGGER despesa_dat_atualizacao_trigger BEFORE UPDATE
        ON
        despesa FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;        
        
        
--- ENTRADA
CREATE SEQUENCE entrada_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE entrada (
	id int8 DEFAULT nextval('entrada_id_seq'::regclass) NOT NULL,
	dat_entrada timestamptz,
	vlr_entrada numeric(14,4) NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT entrada_pkey PRIMARY KEY (id)
);

CREATE
    TRIGGER entrada_dat_atualizacao_trigger BEFORE UPDATE
        ON
        entrada FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;          
        
  --- FUNCIONARIO
CREATE SEQUENCE funcionario_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE funcionario (
	id int8 DEFAULT nextval('funcionario_id_seq'::regclass) NOT NULL,
	nom_pessoa varchar(128) NOT NULL,
	num_cpf varchar(11),
	flg_sexo varchar(1) NOT NULL,
	dat_nascimento timestamptz,
	num_telefone varchar(14),
	cod_cidade int8,
	nom_senha varchar(128) NOT NULL,
	dat_admissao timestamptz,
	cod_grupo_acesso int8 NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT funcionario_pkey PRIMARY KEY (id),
	CONSTRAINT funcionario_cpf_uk UNIQUE (num_cpf),
	CONSTRAINT funcionario_cidade_fkey FOREIGN KEY (cod_cidade) REFERENCES cidade(id),
	CONSTRAINT funcionario_grupo_acesso_fkey FOREIGN KEY (cod_grupo_acesso) REFERENCES grupo_acesso(id)
);

CREATE
    TRIGGER funcionario_dat_atualizacao_trigger BEFORE UPDATE
        ON
        funcionario FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;         
        
--- ITEM ENTRADA
CREATE SEQUENCE entritem_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE entritem (
	id int8 DEFAULT nextval('entritem_id_seq'::regclass) NOT NULL,
	qtd_entrada int4 NOT NULL,
	vlr_unitario numeric(14,4) NOT NULL,
	num_item int4 NOT NULL,
	cod_produto int8 NOT NULL,
	cod_entrada int8 NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT entritem_pkey PRIMARY KEY (id),
	CONSTRAINT entritem_num_item_uk UNIQUE (cod_entrada, num_item),
	CONSTRAINT entritem_produtp_fkey FOREIGN KEY (cod_produto) REFERENCES produto(id),
	CONSTRAINT entritem_entrada_fkey FOREIGN KEY (cod_entrada) REFERENCES entrada(id)
);

CREATE
    TRIGGER entritem_dat_atualizacao_trigger BEFORE UPDATE
        ON
        entritem FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;           
        
--- VENDA
CREATE SEQUENCE venda_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE venda (
	id int8 DEFAULT nextval('venda_id_seq'::regclass) NOT NULL,
	dat_emissao timestamptz NOT NULL,
	vlr_total numeric(14,4) NOT NULL,
	flg_orcamento varchar(1) NOT NULL,
	cod_forma_pagamento int8 NOT NULL,
	cod_condicao_pagamento int8 NOT NULL,
	cod_cliente int8 NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT venda_pkey PRIMARY KEY (id),
	CONSTRAINT venda_formapagamento_fkey FOREIGN KEY (cod_forma_pagamento) REFERENCES forma_pagamento(id),
	CONSTRAINT venda_condicaopagamento_fkey FOREIGN KEY (cod_condicao_pagamento) REFERENCES condicao_pagamento(id),
	CONSTRAINT venda_cliente_fkey FOREIGN KEY (cod_cliente) REFERENCES cliente(id)
);

CREATE
    TRIGGER venda_dat_atualizacao_trigger BEFORE UPDATE
        ON
        entrada FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();; 
        
--- ITEM VENDA
CREATE SEQUENCE venditem_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE venditem (
	id int8 DEFAULT nextval('venditem_id_seq'::regclass) NOT NULL,
	qtd_venda int4 NOT NULL,
	vlr_unitario numeric(14,4) NOT NULL,
	num_item int4 NOT NULL,
	cod_produto int8 NOT NULL,
	cod_venda int8 NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT venditem_pkey PRIMARY KEY (id),
	CONSTRAINT venditem_num_item_uk UNIQUE (cod_venda, num_item),
	CONSTRAINT venditem_produto_fkey FOREIGN KEY (cod_produto) REFERENCES produto(id),
	CONSTRAINT venditem_venda_fkey FOREIGN KEY (cod_venda) REFERENCES venda(id)
);

CREATE
    TRIGGER venditem_dat_atualizacao_trigger BEFORE UPDATE
        ON
        venditem FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;           
--- CREDIARIO
CREATE SEQUENCE crediario_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE crediario (
	id int8 DEFAULT nextval('crediario_id_seq'::regclass) NOT NULL,
	dat_emissao timestamptz NOT NULL,
	vlr_total numeric(14,4) NOT NULL,
	cod_cliente int8 NOT NULL,
	cod_venda int8 NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT crediario_pkey PRIMARY KEY (id),
	CONSTRAINT crediario_cliente_fkey FOREIGN KEY (cod_cliente) REFERENCES cliente(id),
	CONSTRAINT crediario_venda_fkey FOREIGN KEY (cod_venda) REFERENCES venda(id)
);

CREATE
    TRIGGER crediario_dat_atualizacao_trigger BEFORE UPDATE
        ON
        entrada FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();; 
        
--- PARCELA CREDIARIO
CREATE SEQUENCE credparc_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE credparc (
	id int8 DEFAULT nextval('credparc_id_seq'::regclass) NOT NULL,
	num_parcela int4 NOT NULL,
	vlr_parcela numeric(14,4) NOT NULL,
	vlr_pago numeric(14,4) NOT NULL,
	dat_pagamento timestamptz NOT NULL,
	dat_vencimento timestamptz NOT NULL,
	flg_quitada varchar(1) NOT NULL,
	cod_crediario int8 NOT NULL,
	cod_situacao int4 NOT NULL DEFAULT 0,
	dat_cadastro timestamptz NOT NULL DEFAULT now(),
	dat_atualizacao timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT credparc_pkey PRIMARY KEY (id),
	CONSTRAINT credparc_num_item_entrada_uk UNIQUE (cod_crediario, num_parcela),
	CONSTRAINT credparc_crediario_fkey FOREIGN KEY (cod_crediario) REFERENCES crediario(id)
);

CREATE
    TRIGGER credparc_dat_atualizacao_trigger BEFORE UPDATE
        ON
        credparc FOR EACH ROW EXECUTE PROCEDURE dat_atualizacao_trigger_function();;           
        
