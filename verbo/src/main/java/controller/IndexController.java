package controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;
import util.Utils;

@Named
@ViewScoped
public class IndexController implements Serializable {
	private static final long serialVersionUID = 1L;
	private String boas_vindas = "Seja bem vindo ao VERBO System";

	@PostConstruct
	private void inicialize() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			// salva a atividade no banco de dados
			t = sessao.beginTransaction();
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			Utils.addMsg("Erro", "Erro ao inicializar o banco", 3); // envia msg de erro
			e.printStackTrace();
		} finally {
			// finaliza a sessao
			sessao.close();
		}
	}

	public String getBoas_vindas() {
		return boas_vindas;
	}

	public void setBoas_vindas(String boas_vindas) {
		this.boas_vindas = boas_vindas;
	}

}
