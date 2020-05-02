package br.com.unisul.verbo.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.helper.Utils;
import br.com.unisul.verbo.i18n.MensagensI18n.Mensagens;
import br.com.unisul.verbo.model.ClienteModel;

@Entity
@Table(name = "cliente")
public class Cliente extends BaseEntidadeImpl<Cliente, ClienteModel> {

	@Column(name = "nom_pessoa")
	private String nomPessoa;

	@Column(name = "num_cpf")
	private String numCpf;

	@Column(name = "flg_sexo")
	private String flgSexo;

	@Column(name="dat_nascimento")
	private ZonedDateTime datNasc;
	
	@Column(name = "num_telefone")
	private String numTelefone;
	
	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_cidade")
	private Cidade cidade;
	
	@Column(name = "vlr_limite_credito", precision = 14, scale = 4)
	private BigDecimal vlrLimiteCredito;

	@Column(name = "vlr_credito_utilizado", precision = 14, scale = 4)
	private BigDecimal vlrCreditoUtilizado;
	
	@Column(name = "nom_observacoes")
	private String nomObservacoes;
	

	public Cliente() {
		super();
	}
	
	public Cliente(ClienteModel model, Cidade cidade) {
		this();
		this.nomPessoa = model.getNomPessoa();
		this.numCpf = model.getNumCpf();
		this.flgSexo = model.getFlgSexo();
		this.datNasc = model.getDatNasc();
		this.numTelefone = Objects.nonNull(model.getNumTelefone()) ? Utils.formatNumFone(model.getNumTelefone()) : null;
		this.cidade = cidade;
		this.vlrLimiteCredito = model.getVlrLimiteCredito();
		this.vlrCreditoUtilizado = model.getVlrCreditoUtilizado();
		this.nomObservacoes = model.getNomObservacoes();
	}
	
	public void atualizar(ClienteModel model, Cidade cidade) {
		validaAtualizacaoCredito(model);
		this.nomPessoa = model.getNomPessoa();
		this.numCpf = model.getNumCpf();
		this.flgSexo = model.getFlgSexo();
		this.datNasc = model.getDatNasc();
		this.numTelefone = Objects.nonNull(model.getNumTelefone()) ? Utils.formatNumFone(model.getNumTelefone()) : null;
		this.cidade = cidade;
		this.vlrLimiteCredito = model.getVlrLimiteCredito();
		this.nomObservacoes = model.getNomObservacoes();
	}
	
	private void validaAtualizacaoCredito(ClienteModel model) {
		if (model.getVlrLimiteCredito().compareTo(this.vlrCreditoUtilizado) < 0) {
			throw new BusinessException(Mensagens.LIMITE_MENOR_QUE_UTILIZADO.getMensagem());
		}
	}
	
	@Override
	public Cliente gerarDomain(ClienteModel object) {
		throw new BusinessException(getClass());
	}

	@Override
	public void atualizar(ClienteModel model) {
		atualizar(model, null);
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

	public Cidade getCidade() {
		return cidade;
	}
	
}