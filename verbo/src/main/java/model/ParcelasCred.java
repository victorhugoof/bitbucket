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

@Entity
@Table(name = "crediarios_parcelas")
public class ParcelasCred implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "parcela_num")
	private Integer parcela_num;

	@Column(name = "valor")
	private Double valor;

	@Column(name = "valor_pago")
	private Double pago;

	@Column(name = "data_pagamento")
	@Temporal(TemporalType.DATE)
	private Date data_pagamento;

	@Column(name = "data_vencimento")
	@Temporal(TemporalType.DATE)
	private Date data_vencimento;

	@Column(name = "flg_paga")
	private Boolean flg_paga = false;

	@ManyToOne
	@JoinColumn(name = "id_crediario", nullable = false, foreignKey = @ForeignKey(name = "fk_parcelas_crediario"))
	private Crediario crediario;

	// GETTERS E SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getData_pagamento() {
		return data_pagamento;
	}

	public void setData_pagamento(Date data_pagamento) {
		this.data_pagamento = data_pagamento;
	}

	public Date getData_vencimento() {
		return data_vencimento;
	}

	public void setData_vencimento(Date data_vencimento) {
		this.data_vencimento = data_vencimento;
	}

	public Crediario getCrediario() {
		return crediario;
	}

	public void setCrediario(Crediario crediario) {
		this.crediario = crediario;
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
		ParcelasCred other = (ParcelasCred) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Double getPago() {
		return pago;
	}

	public void setPago(Double pago) {
		this.pago = pago;
	}

	public Integer getParcela_num() {
		return parcela_num;
	}

	public void setParcela_num(Integer parcela_num) {
		this.parcela_num = parcela_num;
	}

	public Boolean getFlg_paga() {
		return flg_paga;
	}

	public void setFlg_paga(Boolean flg_paga) {
		this.flg_paga = flg_paga;
	}

}
