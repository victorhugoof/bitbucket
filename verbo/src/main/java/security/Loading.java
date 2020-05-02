package security;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

@Named
@ApplicationScoped
public class Loading implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(Loading.class);
	private boolean rodouSQL = true;
	private String msg_erro = "Ocorreu um erro ao executar metadata ";

	@PostConstruct
	public void conecta() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		t = sessao.beginTransaction();
		t.rollback();
		sessao.close();

		rodaSQL(this.getClass().getClassLoader().getResource("sql/estados.sql").getPath(), "estados.sql");
		rodaSQL(this.getClass().getClassLoader().getResource("sql/cidades.sql").getPath(), "cidades.sql");
		rodaSQL(this.getClass().getClassLoader().getResource("sql/grupoacesso.sql").getPath(), "grupoacesso.sql");
		rodaSQL(this.getClass().getClassLoader().getResource("sql/formapagamento.sql").getPath(), "formapagamento.sql");
		rodaSQL(this.getClass().getClassLoader().getResource("sql/condicaopagamento.sql").getPath(), "condicaopagamento.sql");
		rodaSQL(this.getClass().getClassLoader().getResource("sql/master.sql").getPath(), "master.sql");
	}

	public void rodaSQL(String path, String nome) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		t = sessao.beginTransaction();
		try {
			logger.info("Iniciando execução do metadata " + nome);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			String linha;

			while ((linha = br.readLine()) != null) {
				sessao.createSQLQuery(linha).executeUpdate();
			}

			br.close();
			logger.info("Metada " + nome + " executado com sucesso");

			t.commit();
			rodouSQL = true;

		} catch (Exception e) {
			msg_erro = "Ocorreu um erro ao executar metadata " + nome + ": \n" + e.getMessage();
			logger.error(msg_erro);
			rodouSQL = false;
		} finally {
			sessao.close(); // finaliza a sessão
		}
	}

	public boolean isRodouSQL() {
		return rodouSQL;
	}

	public void setRodouSQL(boolean rodouSQL) {
		this.rodouSQL = rodouSQL;
	}

	public String getMsg_erro() {
		return msg_erro;
	}

	public void setMsg_erro(String msg_erro) {
		this.msg_erro = msg_erro;
	}
}
