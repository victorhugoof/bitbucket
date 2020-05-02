package br.com.unisul.verbo.model;

import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import br.com.unisul.verbo.domain.Funcionario;
import br.com.unisul.verbo.helper.Utils;

public class FuncionarioModel extends BaseModelImpl<Funcionario, FuncionarioModel> {
	
	@NotNull
	@Length(max = 128)
	String nomPessoa;

	@CPF
	@Length(min = 11, max = 11)
	String numCpf;

	@NotNull
	@Length(max = 1)
	String flgSexo;

	@Length(min = 10, max = 10)
	String datNasc;
	
	@Length(max = 14)
	String numTelefone;
	
	Long codCidade;

	@NotNull
	String nomSenha;

	@Length(min = 10, max = 10)
	ZonedDateTime datAdmissao;
	
	@NotNull
	Long codGrupoAcesso;
	
	public FuncionarioModel() {
		super();
	}

	public FuncionarioModel(final Funcionario funcionario) {
		this.nomPessoa = funcionario.getNomPessoa();
		this.numCpf = Utils.somenteNumeros(funcionario.getNumCpf());
		this.flgSexo = funcionario.getFlgSexo();
		this.datNasc = Utils.formatDataBrasil(funcionario.getDatNasc());
		this.numTelefone = Utils.somenteNumeros(funcionario.getNumTelefone());
		this.codCidade = Objects.nonNull(funcionario.getCidade()) ? funcionario.getCidade().getId() : null;
		this.id = funcionario.getId();
		this.nomSenha = funcionario.getNomSenha();
		this.datAdmissao = funcionario.getDatAdmissao();
		this.codGrupoAcesso = Objects.nonNull(funcionario.getGrupoAcesso()) ? funcionario.getGrupoAcesso().getId() : null;
	}

	@Override
	public FuncionarioModel gerarModel(Funcionario object) {
		return new FuncionarioModel(object);
	}
	
	public String getNomSenha() {
		return nomSenha;
	}

	public ZonedDateTime getDatAdmissao() {
		return datAdmissao;
	}

	public Long getCodGrupoAcesso() {
		return codGrupoAcesso;
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

	public String getDatNasc() {
		return datNasc;
	}

	public String getNumTelefone() {
		return numTelefone;
	}

	public Long getCodCidade() {
		return codCidade;
	}
}
