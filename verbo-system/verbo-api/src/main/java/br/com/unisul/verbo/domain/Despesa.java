package br.com.unisul.verbo.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.unisul.verbo.helper.Utils;
import br.com.unisul.verbo.model.DespesaModel;

@Entity
@Table(name = "despesa")
public class Despesa extends BaseEntidadeImpl<Despesa, DespesaModel> {

	@Column(name = "nom_despesa")
	private String nomDespesa;

	@Column(name = "vlr_despesa", precision = 14, scale = 4)
	private BigDecimal vlrDespesa;

	@Column(name = "flg_recorrente")
	private String flgRecorrente;

	@Column(name = "dat_vencimento")
	private ZonedDateTime datVenc;

	public Despesa() {
		super();
	}

	public Despesa(DespesaModel model) {
		this();
		this.nomDespesa = model.getNomDespesa();
		this.vlrDespesa = model.getVlrDespesa();
		this.flgRecorrente = model.getFlgRecorrente();
		this.datVenc = Utils.toZonedDateTime(model.getDatVenc());
	}

	@Override
	public void atualizar(DespesaModel model) {
		this.nomDespesa = model.getNomDespesa();
		this.vlrDespesa = model.getVlrDespesa();
		this.flgRecorrente = model.getFlgRecorrente();
		this.datVenc = Utils.toZonedDateTime(model.getDatVenc());
	}
	
	@Override
	public Despesa gerarDomain(DespesaModel object) {
		return new Despesa(object);
	}

	public String getNomDespesa() {
		return nomDespesa;
	}

	public BigDecimal getVlrDespesa() {
		return vlrDespesa;
	}

	public String getFlgRecorrente() {
		return flgRecorrente;
	}

	public ZonedDateTime getDatVenc() {
		return datVenc;
	}
}
