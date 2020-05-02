package br.com.unisul.verbo.domain;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.model.BaseModel;

@MappedSuperclass
public abstract class BaseEntidadeImpl<T, M extends BaseModel<T, M>> implements BaseEntidade<T, M> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.ORDINAL)
    @Column(name="cod_situacao")
	private EnumSituacao codSituacao;
	
    @Column(name="dat_cadastro", updatable = false, insertable = false)
    private ZonedDateTime datCadastro;
    
    @Column(name="dat_atualizacao", updatable = false, insertable = false)
    private ZonedDateTime datAtualizacao;
    
    public BaseEntidadeImpl() {
        this.datCadastro = this.datAtualizacao = ZonedDateTime.now(ZoneOffset.UTC);
        this.codSituacao = EnumSituacao.ATIVO;
    }
    
    public Long getId() {
		return id;
	}
    
    public EnumSituacao getCodSituacao() {
		return codSituacao;
	}
	
	public void excluir() {
		this.codSituacao = EnumSituacao.EXCLUIDO;
	}

	public ZonedDateTime getDatCadastro() {
        return datCadastro;
    }

    public ZonedDateTime getDatAtualizacao() {
        return datAtualizacao;
    }

}
