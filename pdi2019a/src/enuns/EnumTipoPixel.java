package enuns;

public enum EnumTipoPixel {

	BLUE,
	GREEN, 
	RED; 

	public int getCodigo() {
		return ordinal();
	}

	public String getNome() {
		return name();
	}
}
