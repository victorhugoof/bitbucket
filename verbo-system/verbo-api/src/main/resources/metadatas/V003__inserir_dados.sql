INSERT INTO condicao_pagamento (nom_condicao) VALUES ('Avista');
INSERT INTO condicao_pagamento (nom_condicao) VALUES ('Parcelado');

INSERT INTO forma_pagamento (nom_forma) VALUES ('Dinheiro');
INSERT INTO forma_pagamento (nom_forma) VALUES ('Cartao');
INSERT INTO forma_pagamento (nom_forma) VALUES ('Crediário');

INSERT INTO grupo_acesso (nom_grupo) VALUES ('Funcionario');
INSERT INTO grupo_acesso (nom_grupo) VALUES ('Gerente');

INSERT INTO funcionario (nom_pessoa, nom_senha, flg_sexo, cod_grupo_acesso) VALUES ('MASTER', 'verbo', 'M', (SELECT id FROM grupo_acesso WHERE nom_grupo = 'Gerente'));
