package service;

import java.io.File;

import enuns.EnumTipoPixel;
import javafx.scene.image.Image;

public interface OpenCvService {
	Image erosao(File imagem, Double size);
	Image dilatacao(File imagem, Double size);
	Image bordaCanny(File imagem, Double size, Integer blurSize, Boolean usaBlur);
	Image bordaSobel(File imagem, Double size, Integer blurSize, Boolean usaBlur, Boolean usaCinza);
	Image bordaLaplace(File imagem, Double size, Integer blurSize, Boolean usaBlur, Boolean usaCinza);
	Image escalaCinza(File imagem, EnumTipoPixel tipo);
	Image ruido(File imagem);
}

