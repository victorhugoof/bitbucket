package br.com.unisul.verbo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.unisul.verbo.domain.Despesa;
import br.com.unisul.verbo.helper.Utils;

public class DespesaModel extends BaseModelImpl<Despesa, DespesaModel> {

	@NotNull
	@Length(max = 128)
	String nomDespesa;

	@NotNull
	BigDecimal vlrDespesa;

	@NotNull
	@Length(max = 1)
	String flgRecorrente;

	@NotNull
	@Length(min = 10, max = 10)
	String datVenc;
	
	public DespesaModel() {
		super();
	}

	public DespesaModel(Despesa despesa) {
		this();
		this.id = despesa.getId();
		this.nomDespesa = despesa.getNomDespesa();
		this.vlrDespesa = despesa.getVlrDespesa();
		this.flgRecorrente = despesa.getFlgRecorrente();
		this.datVenc = Utils.formatDataBrasil(despesa.getDatVenc());
	}

	@Override
	public DespesaModel gerarModel(Despesa object) {
		return new DespesaModel(object);
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

	public String getDatVenc() {
		return datVenc;
	}
	
}
