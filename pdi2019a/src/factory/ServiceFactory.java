package factory;

import service.Desafio1Service;
import service.Desafio2Service;
import service.ImageProcessService;
import service.OpenCvService;
import service.Prova1Service;
import service.ProvaTeste1Service;
import service.SegundaChamadaService;
import service.impl.Desafio1ServiceImpl;
import service.impl.Desafio2ServiceImpl;
import service.impl.ImageProcessServiceImpl;
import service.impl.OpenCvServiceImpl;
import service.impl.Prova1ServiceImpl;
import service.impl.ProvaTeste1ServiceImpl;
import service.impl.SegundaChamadaServiceImpl;

public class ServiceFactory {
	
	private ServiceFactory() {
		super();
	}

	public static Desafio1Service getDesafio1Service() {
		return new Desafio1ServiceImpl();
	}

	public static Desafio2Service getDesafio2Service() {
		return new Desafio2ServiceImpl();
	}

	public static Prova1Service getProva1Service() {
		return new Prova1ServiceImpl();
	}

	public static ProvaTeste1Service getProvaTeste1Service() {
		return new ProvaTeste1ServiceImpl();
	}

	public static ImageProcessService getImageProcessService() {
		return new ImageProcessServiceImpl();
	}
	
	public static OpenCvService getOpenCvService() {
		return new OpenCvServiceImpl();
	}

	public static SegundaChamadaService getSegundaChamadaService() {
		return new SegundaChamadaServiceImpl();
	}

}
