package enuns;

public enum EnumTipoPixel {

	RED, 
	GREEN, 
	BLUE;

	public int getCodigo() {
		return ordinal();
	}

	public String getNome() {
		return name();
	}
}
