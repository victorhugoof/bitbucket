package br.com.unisul.verbo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.unisul.verbo.domain.Crediario;
import br.com.unisul.verbo.helper.Utils;

public class CrediarioModel extends BaseModelImpl<Crediario, CrediarioModel> {
	
	@NotNull
	@Length(min = 10, max = 10)
	String datEmissao;

	@NotNull
	BigDecimal vlrTotal;

	@NotNull
	Long codCliente;

	@NotNull
	Long codVenda;
	
	public CrediarioModel() {
		super();
	}

	public CrediarioModel(Crediario crediario) {
		this();
		this.id = crediario.getId();
		this.datEmissao = Utils.formatDataBrasil(crediario.getDatEmissao());
		this.vlrTotal = crediario.getVlrTotal();
		this.codCliente = crediario.getCliente().getId();
		this.codVenda = crediario.getVenda().getId();
	}
	
	@Override
	public CrediarioModel gerarModel(Crediario object) {
		return new CrediarioModel(object);
	}

	public String getDatEmissao() {
		return datEmissao;
	}

	public BigDecimal getVlrTotal() {
		return vlrTotal;
	}

	public Long getCodCliente() {
		return codCliente;
	}

	public Long getCodVenda() {
		return codVenda;
	}
}
