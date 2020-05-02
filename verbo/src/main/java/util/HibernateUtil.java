package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static Logger logger = LogManager.getLogger(HibernateUtil.class);
	private static SessionFactory fabricaDeSessoes = criarFabricaDeSessoes();

	public static SessionFactory getFabricaDeSessoes() {
		return fabricaDeSessoes;
	}

	private static SessionFactory criarFabricaDeSessoes() {
		try {
			SessionFactory fabrica = new Configuration().configure().buildSessionFactory();

			return fabrica;
		} catch (Throwable ex) {
			Exception e = new Exception(ex);

			if (Utils.stackTrace(e).contains("Error calling Driver#connect")) {
				logger.error("Sem conexão: Não foi possível conectar ao banco de dados");
				logger.error(Utils.stackTrace(e));
			} else {
				logger.error("A fábrica de sessões não pode ser criada");
				logger.error(Utils.stackTrace(e));
			}

			throw new ExceptionInInitializerError(ex);
		}
	}

}
