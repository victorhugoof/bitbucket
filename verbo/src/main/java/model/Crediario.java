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
@Table(name = "crediarios", uniqueConstraints = @UniqueConstraint(columnNames = {
		"venda" }, name = "uk_crediario_venda"))
public class Crediario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "flg_ativo")
	private Boolean flg_ativo = true;

	@Column(name = "qtd_parcelas", nullable = false)
	private Short qtd_parcelas;

	@Column(name = "qtd_parcelasPagas")
	private Integer qtd_parcelasPagas;

	@Column(name = "valor_cred", nullable = false)
	private Double valor;

	@Column(name = "data_abertura", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date data_abertura;

	@ManyToOne
	@JoinColumn(name = "cliente", nullable = false, foreignKey = @ForeignKey(name = "fk_crediario_cliente"))
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "venda", nullable = true, foreignKey = @ForeignKey(name = "fk_crediario_venda"))
	private Venda venda;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Short getQtd_parcelas() {
		return qtd_parcelas;
	}

	public void setQtd_parcelas(Short qtd_parcelas) {
		this.qtd_parcelas = qtd_parcelas;
	}

	public Date getData_abertura() {
		return data_abertura;
	}

	public void setData_abertura(Date data_abertura) {
		this.data_abertura = data_abertura;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Crediario other = (Crediario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Boolean getFlg_ativo() {
		return flg_ativo;
	}

	public void setFlg_ativo(Boolean flg_ativo) {
		this.flg_ativo = flg_ativo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getQtd_parcelasPagas() {
		return qtd_parcelasPagas;
	}

	public void setQtd_parcelasPagas(Integer qtd_parcelasPagas) {
		this.qtd_parcelasPagas = qtd_parcelasPagas;
	}

}
