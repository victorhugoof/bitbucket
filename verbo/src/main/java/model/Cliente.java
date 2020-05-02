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
@Table(name = "clientes", uniqueConstraints = @UniqueConstraint(columnNames = { "cpf" }, name = "uk_cpf"))
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "flg_ativo")
	private Boolean flg_ativo;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "sexo")
	private Boolean sexo = true;

	@Column(name = "dat_nasc")
	@Temporal(TemporalType.DATE)
	private Date dat_nasc;

	@Column(name = "limite_cred")
	private Double limite_cred;

	@Column(name = "limite_utilizado")
	private Double limite_utilizado;

	@Column(name = "obs_cad")
	private String obs_cad;

	@Column(name = "telefone")
	private Long telefone;

	@ManyToOne
	@JoinColumn(name = "uf_ibge", foreignKey = @ForeignKey(name = "fk_cliente_estado"))
	private Estado uf;

	@ManyToOne
	@JoinColumn(name = "cidade_ibge", foreignKey = @ForeignKey(name = "fk_cliente_cidade"))
	private Cidade cidade;

	// GETTERS E SETTERS
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
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

	public Double getLimite_cred() {
		return limite_cred;
	}

	public void setLimite_cred(Double limite_cred) {
		this.limite_cred = limite_cred;
	}

	public String getObs_cad() {
		return obs_cad;
	}

	public void setObs_cad(String obs_cad) {
		this.obs_cad = obs_cad;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Cliente other = (Cliente) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id + "";
	}

	public Double getLimite_utilizado() {
		return limite_utilizado;
	}

	public void setLimite_utilizado(Double limite_utilizado) {
		this.limite_utilizado = limite_utilizado;
	}

}