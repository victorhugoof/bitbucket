package service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import enuns.EnumTipoPixel;
import javafx.scene.image.Image;
import service.OpenCvService;

public class OpenCvServiceImpl implements OpenCvService {

	@Override
	public Image erosao(File imagem, Double size) {

		String output = String.format("imagens-processadas/erosao-%s.jpg", new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date()));
		var matImgDst = new Mat();
		var matImgSrc = Imgcodecs.imread(imagem.getPath());

		var element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2 * size + 1, 2 * size + 1), new Point(size, size));
		Imgproc.erode(matImgSrc, matImgDst, element);
		Imgcodecs.imwrite(output, matImgDst);
		
		var file = new File(output);
		if (file.exists()) {
			return new Image(file.toURI().toString());
		}

		return null;
	}

	@Override
	public Image dilatacao(File imagem, Double size){
		
		String output = String.format("imagens-processadas/dilatacao-%s.jpg", new SimpleDateFormat("dd-MM-yyyy-HH-mm").format(new Date()));
		var matImgDst = new Mat();
		var matImgSrc = Imgcodecs.imread(imagem.getPath());

		var element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2 * size + 1, 2 * size + 1), new Point(size, size));
		Imgproc.dilate(matImgSrc, matImgDst, element);
		Imgcodecs.imwrite(output, matImgDst);
		
		var file = new File(output);
		if (file.exists()) {
			return new Image(file.toURI().toString());
		}

		return null;
	}

	@Override
	public Image bordaCanny(File imagem, Double size, Integer blurSize, Boolean usaBlur) {
		String output = String.format("imagens-processadas/canny-%s.jpg", new SimpleDateFormat("dd-MM-yyyy-HH-mm").format(new Date()));
		var matImgSrc = Imgcodecs.imread(imagem.getPath());
		var matImgDst = new Mat();

		if (usaBlur) {
			Imgproc.GaussianBlur(matImgSrc, matImgSrc, new Size(blurSize, blurSize),  0, 0, Core.BORDER_DEFAULT);
			
		}
		
		Imgproc.Canny(matImgSrc, matImgDst, size, size * 3, 3, false);
		Imgcodecs.imwrite(output, matImgDst);
		
		var file = new File(output);
		if (file.exists()) {
			return new Image(file.toURI().toString());
		}

		return null;
	}

	@Override
	public Image bordaSobel(File imagem, Double size, Integer blurSize, Boolean usaBlur, Boolean usaCinza) {
		String output = String.format("imagens-processadas/sobel-%s.jpg", new SimpleDateFormat("dd-MM-yyyy-HH-mm").format(new Date()));
		var matImgSrc = Imgcodecs.imread(imagem.getPath());
		var matImgDst = new Mat();

		if (usaBlur) {
			Imgproc.GaussianBlur(matImgSrc, matImgSrc, new Size(blurSize, blurSize),  0, 0, Core.BORDER_DEFAULT);
		}
		
		if (usaCinza) {
			Imgproc.cvtColor(matImgSrc, matImgSrc, Imgproc.COLOR_RGB2GRAY);
		}
		
		var gradX = new Mat(); 
		var gradY = new Mat();
        var absX = new Mat(); 
        var absY = new Mat();
        
        Imgproc.Sobel(matImgSrc, gradX, CvType.CV_16S, 1, 0, 3, size, 0, Core.BORDER_DEFAULT );
        Imgproc.Sobel(matImgSrc, gradY, CvType.CV_16S, 0, 1, 3, size, 0, Core.BORDER_DEFAULT );
        
        Core.convertScaleAbs(gradX, absX);
        Core.convertScaleAbs(gradY, absY);
        Core.addWeighted(absX, 0.5, absY, 0.5, 0, matImgDst);
		
		Imgcodecs.imwrite(output, matImgDst);
		
		var file = new File(output);
		if (file.exists()) {
			return new Image(file.toURI().toString());
		}

		return null;
	}

	@Override
	public Image bordaLaplace(File imagem, Double size, Integer blurSize, Boolean usaBlur, Boolean usaCinza) {
		String output = String.format("imagens-processadas/laplace-%s.jpg", new SimpleDateFormat("dd-MM-yyyy-HH-mm").format(new Date()));
		var matImgSrc = Imgcodecs.imread(imagem.getPath());
		var matImgDst = new Mat();

		if (usaBlur) {
			Imgproc.GaussianBlur(matImgSrc, matImgSrc, new Size(blurSize, blurSize),  0, 0, Core.BORDER_DEFAULT);
		}
		
		if (usaCinza) {
			Imgproc.cvtColor(matImgSrc, matImgSrc, Imgproc.COLOR_RGB2GRAY);
		}
		
		Imgproc.Laplacian(matImgSrc, matImgDst, CvType.CV_16S, 3, size, 0, Core.BORDER_DEFAULT);
		Imgcodecs.imwrite(output, matImgDst);
		
		var file = new File(output);
		if (file.exists()) {
			return new Image(file.toURI().toString());
		}

		return null;
	}

	@Override
	public Image escalaCinza(File imagem, EnumTipoPixel tipo) {
		String output = String.format("imagens-processadas/escala-cinza-%s.jpg", new SimpleDateFormat("dd-MM-yyyy-HH-mm").format(new Date()));
		var matImgSrc = Imgcodecs.imread(imagem.getPath());
		var matImgDst = new Mat();
		
		if (Objects.nonNull(tipo)) {
			
			if (tipo.equals(EnumTipoPixel.RED)) {
				Core.extractChannel(matImgSrc, matImgDst, 2);
			}
			
			if (tipo.equals(EnumTipoPixel.GREEN)) {
				Core.extractChannel(matImgSrc, matImgDst, 1);
			}
			
			if (tipo.equals(EnumTipoPixel.BLUE)) {
				Core.extractChannel(matImgSrc, matImgDst, 0);
			}
		} else {
			
			Imgproc.cvtColor(matImgSrc, matImgDst, Imgproc.COLOR_RGB2GRAY);
		}
		
		Imgcodecs.imwrite(output, matImgDst);
		
		var file = new File(output);
		if (file.exists()) {
			return new Image(file.toURI().toString());
		}

		return null;
	}
	
	@Override
	public Image ruido(File imagem) {
		String output = String.format("imagens-processadas/ruido-%s.jpg", new SimpleDateFormat("dd-MM-yyyy-HH-mm").format(new Date()));
		var matImgSrc = Imgcodecs.imread(imagem.getPath());
		var matImgDst = new Mat();

		var floodfilled = Mat.zeros(matImgSrc.rows() + 2, matImgSrc.cols() + 2, CvType.CV_8U);
	    Imgproc.floodFill(matImgSrc, floodfilled, new Point(0, 0), new Scalar(255), new Rect(), new Scalar(0), new Scalar(0), 4 + (255 << 8) + Imgproc.FLOODFILL_MASK_ONLY);

	    Core.subtract(floodfilled, Scalar.all(0), floodfilled);

	    Rect roi = new Rect(1, 1, matImgSrc.cols() - 2, matImgSrc.rows() - 2);

	    floodfilled.submat(roi).copyTo(matImgDst);

		Imgcodecs.imwrite(output, matImgDst);
		
		var file = new File(output);
		if (file.exists()) {
			return new Image(file.toURI().toString());
		}

		return null;
	}

}
