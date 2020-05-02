package enuns;

public enum EnumTipoVizinho {

	VIZINHO_X, 
	VIZINHO_CRUZ, 
	VIZINHO_3X3;
	
	public static EnumTipoVizinho getByCodigo(int codigo) {

		if (EnumTipoVizinho.values().length > codigo) {
			return EnumTipoVizinho.values()[codigo];
		}
		return null;
	}

	public int getCodigo() {
		return ordinal();
	}

	public String getNome() {
		return name();
	}
}
