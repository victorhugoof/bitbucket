CREATE PROCEDURE cadastraMesParaAno(ano INT)
BEGIN
DECLARE anoMes INT DEFAULT 0;

loop_mes: LOOP	
		SET anoMes = anoMes + 1;

		INSERT INTO tempo(ano, mes, nome_mes)
		SELECT ano, anoMes, CASE 
																		WHEN anoMes = 1 THEN "Janeiro"
																		WHEN anoMes = 2 THEN "Fevereiro"
																		WHEN anoMes = 3 THEN "Março"
																		WHEN anoMes = 4 THEN "Abril"
																		WHEN anoMes = 5 THEN "Maio"
																		WHEN anoMes = 6 THEN "Junho"
																		WHEN anoMes = 7 THEN "Julho"
																		WHEN anoMes = 8 THEN "Agosto"
																		WHEN anoMes = 9 THEN "Setembro"
																		WHEN anoMes = 10 THEN "Outubro"
																		WHEN anoMes = 11 THEN "Novembro"
																		WHEN anoMes = 12 THEN "Dezembro"
																		ELSE "BUG"
																	END AS nome_mes;
		IF anoMes = 12 THEN
	        LEAVE loop_mes;
	  END IF;
	END LOOP loop_mes;
END;

CALL cadastraMesParaAno(2019);
