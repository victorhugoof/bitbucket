package br.com.unisul.verbo.model;

public abstract class BaseModelImpl<T, M> implements BaseModel<T, M> {
	Long id;

	
	public Long getId() {
		return id;
	}
}
