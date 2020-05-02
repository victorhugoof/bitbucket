package br.com.unisul.verbo.enuns;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumSituacao {

	ATIVO, EXCLUIDO, INATIVO;

	@JsonCreator
	public static EnumSituacao getByCodigo(@JsonProperty("codigo") final int codigo) {

		if (EnumSituacao.values().length > codigo) {
			return EnumSituacao.values()[codigo];
		}
		return null;
	}

	@JsonProperty("codigo")
	public int getCodigo() {
		return ordinal();
	}

	@JsonProperty("nome")
	public String getNome() {
		return name();
	}
}
