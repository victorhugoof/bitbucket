package br.com.unisul.verbo.model;

public interface BaseModel<T, M> {
	M gerarModel(T object);
}
