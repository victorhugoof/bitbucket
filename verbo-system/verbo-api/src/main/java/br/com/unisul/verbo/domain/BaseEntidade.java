package br.com.unisul.verbo.domain;


public interface BaseEntidade<T, M> {
	T gerarDomain(M object);
	void atualizar(M model);
}
