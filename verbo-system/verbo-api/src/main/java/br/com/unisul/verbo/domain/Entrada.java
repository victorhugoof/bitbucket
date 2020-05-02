package br.com.unisul.verbo.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.unisul.verbo.helper.Utils;
import br.com.unisul.verbo.model.EntradaModel;

@Entity
@Table(name = "entrada")
public class Entrada extends BaseEntidadeImpl<Entrada, EntradaModel> {

	@Column(name = "dat_entrada")
	private ZonedDateTime datEntrada;

	@Column(name = "vlr_entrada", precision = 14, scale = 4)
	private BigDecimal vlrEntrada;

	public Entrada() {
		super();
	}

	public Entrada(EntradaModel model) {
		this();
		this.vlrEntrada = model.getVlrEntrada();
		this.datEntrada = Utils.toZonedDateTime(model.getDatEntrada());
	}

	@Override
	public void atualizar(EntradaModel model) {
		this.vlrEntrada = model.getVlrEntrada();
		this.datEntrada = Utils.toZonedDateTime(model.getDatEntrada());
	}
	
	@Override
	public Entrada gerarDomain(EntradaModel object) {
		return new Entrada(object);
	}

	public ZonedDateTime getDatEntrada() {
		return datEntrada;
	}

	public BigDecimal getVlrEntrada() {
		return vlrEntrada;
	}
}
