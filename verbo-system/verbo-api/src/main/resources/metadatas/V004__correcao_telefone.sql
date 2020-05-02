ALTER TABLE cliente ALTER COLUMN num_telefone TYPE varchar(15) USING num_telefone::varchar;
ALTER TABLE funcionario ALTER COLUMN num_telefone TYPE varchar(15) USING num_telefone::varchar;