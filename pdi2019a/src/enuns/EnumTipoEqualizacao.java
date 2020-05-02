package enuns;

public enum EnumTipoEqualizacao {

	NORMAL, 
	CENTRALIZADA, 
	VALIDOS;

	public int getCodigo() {
		return ordinal();
	}

	public String getNome() {
		return name();
	}
}
