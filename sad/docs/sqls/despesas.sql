SELECT
	*
FROM
	tipo_despesa
JOIN tempo ON
	TRUE ;

INSERT INTO despesas (data_despesa, valor, descricao, tipo_despesa, tempo)
SELECT
	CONCAT(ano, '-', LPAD(mes, 2, '0') , '-', FLOOR(RAND()*(28-1+1)+1)) AS data_despesa,
	CASE 
		WHEN tipo_despesa.id = 1 THEN (200 + (RAND()*(30-15+3)+15) - (RAND()*(30-15+3)+15))
		WHEN tipo_despesa.id = 2 THEN (50 + (RAND()*(30-15+3)+15) - (RAND()*(30-15+3)+15))
		WHEN tipo_despesa.id = 3 THEN (80 + (RAND()*(30-15+3)+15) - (RAND()*(30-15+3)+15))
		WHEN tipo_despesa.id = 4 THEN (1000 + (RAND()*(30-15+3)+15) - (RAND()*(30-15+3)+15))
		WHEN tipo_despesa.id = 6 THEN (2000 + (RAND()*(30-15+3)+15) - (RAND()*(30-15+3)+15))
		ELSE  (RAND()*(30-15+3)+15)
	END AS valor,
	descricao,
	tipo_despesa.id AS tipo_despesa,
	tempo.id AS tempo
FROM
	tipo_despesa
JOIN tempo ON
	TRUE AND tempo.id <= 40 ;

SELECT *
FROM
	despesash