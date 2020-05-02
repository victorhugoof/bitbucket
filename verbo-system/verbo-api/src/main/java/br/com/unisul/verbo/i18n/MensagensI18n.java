package br.com.unisul.verbo.i18n;

import java.util.ResourceBundle;

public class MensagensI18n {

	private MensagensI18n() {
		super();
	}

	public static String getMensagem(String chave) {
		return getMensagem(chave, "");
	}

	public static String getMensagem(String chave, Object... args) {

		final ResourceBundle resourceBundle = ResourceBundle.getBundle("mensagens");
		if (!resourceBundle.containsKey(chave)) {
			return chave;
		}
		return String.format(resourceBundle.getString(chave), args);
	}

	public enum Mensagens {

		CPF_CNPJ_INVALIDO,
		ESTADO_NAO_ENCONTRADO,
		CIDADE_NAO_ENCONTRADA,
		PRODUTO_NAO_ENCONTRADO,
		CLIENTE_NAO_ENCONTRADO,
		DEPENDENTE_NAO_ENCONTRADO,
		LIMITE_MENOR_QUE_UTILIZADO,
		CONDICAO_PAGAMENTO_NAO_ENCONTRADA,
		FORMA_PAGAMENTO_NAO_ENCONTRADA,
		DESPESA_NAO_ENCONTRADA,
		ENTRADA_NAO_ENCONTRADA,
		GRUPO_ACESSO_NAO_ENCONTRADO,
		ITEM_NAO_ENCONTRADO,
		VENDA_NAO_ENCONTRADA,
		CREDIARIO_NAO_ENCONTRADO,
		PARCELA_NAO_ENCONTRADA,
		NAO_ENCONTRADO;

		public String getMensagem(Object... args) {
			return MensagensI18n.getMensagem(name(), args);
		}
		
		public String getMensagem() {
			return MensagensI18n.getMensagem(name());
		}
	}
}
