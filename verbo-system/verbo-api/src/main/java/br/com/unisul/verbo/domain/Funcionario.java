package br.com.unisul.verbo.domain;

import java.time.ZonedDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import br.com.unisul.verbo.exception.NotImplementedException;
import br.com.unisul.verbo.helper.Utils;
import br.com.unisul.verbo.model.FuncionarioModel;

@Entity
@Table(name = "funcionario")
public class Funcionario extends BaseEntidadeImpl<Funcionario, FuncionarioModel> {

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
	
	@Column(name = "nom_senha")
	private String nomSenha;

	@Column(name = "dat_admissao")
	private ZonedDateTime datAdmissao;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_grupo_acesso")
	private GrupoAcesso grupoAcesso;

	public Funcionario() {
		super();
	}
	
	public Funcionario(FuncionarioModel model, Cidade cidade, GrupoAcesso grupoAcesso) {
		this();
		this.nomPessoa = model.getNomPessoa();
		this.numCpf = model.getNumCpf();
		this.flgSexo = model.getFlgSexo();
		this.datNasc = Utils.toZonedDateTime(model.getDatNasc());
		this.numTelefone = Utils.formatNumFone(model.getNumTelefone());
		this.cidade = cidade;
		this.nomSenha = model.getNomSenha();
		this.datAdmissao = model.getDatAdmissao();
		this.grupoAcesso = grupoAcesso;
	}
	
	public void atualizar(FuncionarioModel model, Cidade cidade, GrupoAcesso grupoAcesso) {
		this.nomPessoa = model.getNomPessoa();
		this.numCpf = model.getNumCpf();
		this.flgSexo = model.getFlgSexo();
		this.datNasc = Utils.toZonedDateTime(model.getDatNasc());
		this.numTelefone = Utils.formatNumFone(model.getNumTelefone());
		this.cidade = cidade;
		this.nomSenha = model.getNomSenha();
		this.datAdmissao = model.getDatAdmissao();
		this.grupoAcesso = grupoAcesso;
	}
	
	@Override
	public Funcionario gerarDomain(FuncionarioModel object) {
		throw new NotImplementedException();
	}

	@Override
	public void atualizar(FuncionarioModel model) {
		throw new NotImplementedException();
	}
	
	public String getNomSenha() {
		return nomSenha;
	}

	public ZonedDateTime getDatAdmissao() {
		return datAdmissao;
	}

	public GrupoAcesso getGrupoAcesso() {
		return grupoAcesso;
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
