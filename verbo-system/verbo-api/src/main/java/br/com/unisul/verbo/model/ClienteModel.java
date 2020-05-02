package br.com.unisul.verbo.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import br.com.unisul.verbo.domain.Cliente;
import br.com.unisul.verbo.helper.Utils;

public class ClienteModel extends BaseModelImpl<Cliente, ClienteModel> {
	@NotNull
	@Length(max = 128)
	String nomPessoa;

	@CPF
	@Length(min = 11, max = 11)
	String numCpf;

	@NotNull
	@Length(max = 1)
	String flgSexo;

	ZonedDateTime datNasc;
	
	@Length(max = 15)
	String numTelefone;
	
	CidadeModel cidade; 

	@NotNull
	BigDecimal vlrLimiteCredito;

	@NotNull
	BigDecimal vlrCreditoUtilizado;

	String nomObservacoes;

	public ClienteModel() {
		super();
	}

	public ClienteModel(final Cliente cliente) {
		this();
		this.nomPessoa = cliente.getNomPessoa();
		this.numCpf = Utils.somenteNumeros(cliente.getNumCpf());
		this.flgSexo = cliente.getFlgSexo();
		this.datNasc = cliente.getDatNasc();
		this.numTelefone = Utils.somenteNumeros(cliente.getNumTelefone());
		this.cidade = Objects.nonNull(cliente.getCidade()) ? new CidadeModel(cliente.getCidade()) : null;
		this.id = cliente.getId();
		this.vlrLimiteCredito = cliente.getVlrLimiteCredito();
		this.vlrCreditoUtilizado = cliente.getVlrCreditoUtilizado();
		this.nomObservacoes = cliente.getNomObservacoes();
	}
	
	@Override
	public ClienteModel gerarModel(Cliente object) {
		return new ClienteModel(object);
	}

	public BigDecimal getVlrLimiteCredito() {
		return vlrLimiteCredito;
	}

	public BigDecimal getVlrCreditoUtilizado() {
		return vlrCreditoUtilizado;
	}

	public String getNomObservacoes() {
		return nomObservacoes;
	}

	public String getNomPessoa() {
		return nomPessoa;
	}

	public String getNumCpf() {
		return numCpf;
	}

	public String getFlgSexo() {
		return flgSexo;
	}

	public ZonedDateTime getDatNasc() {
		return datNasc;
	}

	public String getNumTelefone() {
		return numTelefone;
	}

	public CidadeModel getCidade() {
		return cidade;
	}
	
}
