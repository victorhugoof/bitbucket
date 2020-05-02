package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "funcionarios", uniqueConstraints = @UniqueConstraint(columnNames = { "cpf" }, name = "uk_cpf"))
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "flg_ativo")
	private Boolean flg_ativo = true;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "cpf")
	private Long cpf;

	@Column(name = "sexo")
	private Boolean sexo = true;

	@Column(name = "dat_nasc")
	@Temporal(TemporalType.DATE)
	private Date dat_nasc;

	@Column(name = "telefone")
	private Long telefone;

	@Column(name = "senha", nullable = false)
	private String senha;

	@Column(name = "dat_adm")
	@Temporal(TemporalType.DATE)
	private Date dat_adm;

	@ManyToOne
	@JoinColumn(name = "grupoacesso", foreignKey = @ForeignKey(name = "fk_funcionario_grupoacesso"), nullable = false)
	private GrupoAcesso descricao;

	@ManyToOne
	@JoinColumn(name = "uf_ibge", foreignKey = @ForeignKey(name = "fk_funcionario_estado"))
	private Estado uf;

	@ManyToOne
	@JoinColumn(name = "cidade_ibge", foreignKey = @ForeignKey(name = "fk_funcionario_cidade"))
	private Cidade cidade;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getFlg_ativo() {
		return flg_ativo;
	}

	public void setFlg_ativo(Boolean flg_ativo) {
		this.flg_ativo = flg_ativo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Boolean getSexo() {
		return sexo;
	}

	public void setSexo(Boolean sexo) {
		this.sexo = sexo;
	}

	public Date getDat_nasc() {
		return dat_nasc;
	}

	public void setDat_nasc(Date dat_nasc) {
		this.dat_nasc = dat_nasc;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDat_adm() {
		return dat_adm;
	}

	public void setDat_adm(Date dat_adm) {
		this.dat_adm = dat_adm;
	}

	public Estado getUf() {
		return uf;
	}

	public void setUf(Estado uf) {
		this.uf = uf;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public GrupoAcesso getDescricao() {
		return descricao;
	}

	public void setDescricao(GrupoAcesso descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
