package util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.omnifaces.util.Messages;

public class Utils {

	// mensagem com descricao
	public static void addMsg(String titulo, String descricao, int tipo) {
		switch (tipo) {
		case 1:
			Messages.create("<b>" + titulo + "</b> <i>" + descricao + "</i>").detail(" ").add();
			break;
		case 2:
			Messages.create("<b>" + titulo + "</b> <i>" + descricao + "</i>").warn().detail(" ").add();
			break;
		case 3:
			Messages.create("<b>" + titulo + "</b> <i>" + descricao + "</i>").fatal().detail(" ").add();
			break;
		default:
			Messages.create("<b>" + titulo + "</b> <i>" + descricao + "</i>").detail(" ").add();
			break;
		}
	}

	// mensagem sem descricao
	public static void addMensagem(String msg, int tipo) {
		switch (tipo) {
		case 1:
			Messages.create(msg).detail(" ").add();
			break;
		case 2:
			Messages.create(msg).warn().detail(" ").add();
			break;
		case 3:
			Messages.create(msg).error().detail(" ").add();
			break;
		case 4:
			Messages.create(msg).fatal().detail(" ").add();
			break;
		default:
			Messages.create(msg).detail(" ").add();
			break;
		}
	}

	public static String stackTrace(Exception erro) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		erro.printStackTrace(pw);

		String sStackTrace = sw.toString(); // stack trace as a string
		return sStackTrace;
	}

	public static boolean deletarBanco(Object objeto, Logger log) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			t = sessao.beginTransaction();
			sessao.delete(objeto);
			t.commit();

			sessao.close();
			return true;
		} catch (Exception e) {
			if (t != null)
				t.rollback();

			
			if (Utils.stackTrace(e).contains("ConstraintViolationException")){
				Utils.addMsg("Erro ao apagar:", "existem registros associados", 3);
			} else {
				Utils.addMensagem("Erro ao apagar: <i>" + e.getMessage() + "</i>", 3); // envia msg de erro
			}
			
			
			log.error(stackTrace(e));
			sessao.close();
			
			return false;
		}
	}

}
