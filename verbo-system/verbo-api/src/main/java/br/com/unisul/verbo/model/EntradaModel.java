package br.com.unisul.verbo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.unisul.verbo.domain.Entrada;
import br.com.unisul.verbo.helper.Utils;

public class EntradaModel extends BaseModelImpl<Entrada, EntradaModel> {

	@NotNull
	@Length(min = 10, max = 10)
	String datEntrada;

	@NotNull
	BigDecimal vlrEntrada;

	public EntradaModel() {
		super();
	}

	public EntradaModel(Entrada entrada) {
		this();
		this.id = entrada.getId();
		this.vlrEntrada = entrada.getVlrEntrada();
		this.datEntrada = Utils.formatDataBrasil(entrada.getDatEntrada());
	}

	@Override
	public EntradaModel gerarModel(Entrada object) {
		return new EntradaModel(object);
	}

	public String getDatEntrada() {
		return datEntrada;
	}

	public BigDecimal getVlrEntrada() {
		return vlrEntrada;
	}

}