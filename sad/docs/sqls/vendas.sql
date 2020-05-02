INSERT INTO vendas (condicao_pagamento, forma_pagamento, valor, quantidade_itens, funcionario, tempo, cliente)
SELECT condicao_pagamento, forma_pagamento, ROUND(valor, 2) AS valor, quantidade_itens, funcionario, tempo, cliente FROM (
	SELECT 
		(SELECT FLOOR(RAND()*(3-1+1)+1)) AS funcionario,
		(SELECT FLOOR(RAND()*(40-1+1)+1)) AS tempo,
		(SELECT FLOOR(RAND()*(841-1+1)+1)) AS cliente,
		(SELECT
			CASE
				WHEN FLOOR(RAND()*(2-1 + 1)+ 1) = 1 THEN "AVISTA"
				ELSE "PARCELADO"
			END) AS condicao_pagamento,
		(SELECT
			CASE
				WHEN FLOOR(RAND()*(2-1 + 1)+ 1) = 1 THEN "DINHEIRO"
				ELSE CASE 
								WHEN FLOOR(RAND()*(2-1 + 1)+ 1) = 1 THEN "CARTÃO"
								ELSE "CREDIÁRIO"
							END
			END) AS forma_pagamento,
		(SELECT FLOOR(RAND()*(5-1+1)+1)) AS quantidade_itens,
		(SELECT (RAND()*(RAND()*(30-15+3))*(30-15+3)+15) - (RAND()*(30-15+3)+15)) AS valor
	) AS T 
WHERE valor >= 0;


CREATE PROCEDURE cadastraVendas2(qtd INT)
BEGIN
DECLARE contador INT DEFAULT 0;

loop_vendas: LOOP	
		SET contador = contador + 1;

		INSERT INTO vendas (condicao_pagamento, forma_pagamento, valor, quantidade_itens, funcionario, tempo, cliente)
		SELECT condicao_pagamento, forma_pagamento, ROUND(valor, 2) AS valor, quantidade_itens, funcionario, tempo, cliente FROM (
			SELECT 
				(SELECT FLOOR(RAND()*(3-1+1)+1)) AS funcionario,
				(SELECT FLOOR(RAND()*(40-1+1)+1)) AS tempo,
				(SELECT FLOOR(RAND()*(841-1+1)+1)) AS cliente,
				(SELECT
					CASE
						WHEN FLOOR(RAND()*(2-1 + 1)+ 1) = 1 THEN "AVISTA"
						ELSE "PARCELADO"
					END) AS condicao_pagamento,
				(SELECT
					CASE
						WHEN FLOOR(RAND()*(2-1 + 1)+ 1) = 1 THEN "DINHEIRO"
						ELSE CASE 
										WHEN FLOOR(RAND()*(2-1 + 1)+ 1) = 1 THEN "CARTÃO"
										ELSE "CREDIÁRIO"
									END
					END) AS forma_pagamento,
				(SELECT FLOOR(RAND()*(5-1+1)+1)) AS quantidade_itens,
				(SELECT (RAND()*(RAND()*(30-15+3))*(30-15+3)+15) - (RAND()*(30-15+3)+15)) AS valor
			) AS T 
		WHERE valor >= 0;
	
		IF contador >= qtd THEN
	        LEAVE loop_vendas;
	  END IF;
	END LOOP loop_vendas;
END;

CALL cadastraVendas2(100000);

