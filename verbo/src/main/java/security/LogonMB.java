package security;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.omnifaces.util.Faces;

import com.github.adminfaces.template.session.AdminSession;

import model.Funcionario;
import model.Usuario;
import util.HibernateUtil;
import util.Utils;

/**
 * Created by rmpestano on 12/20/14.
 *
 * This is just a login example.
 *
 * AdminSession uses isLoggedIn to determine if user must be redirect to login
 * page or not. By default AdminSession isLoggedIn always resolves to true so it
 * will not try to redirect user.
 *
 * If you already have your authorization mechanism which controls when user
 * must be redirect to initial page or logon you can skip this class.
 */
@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer email;
	private String password;
	private Usuario user = new Usuario();
	private Usuario currentUser;
	private boolean remember;
	private static Logger logger = LogManager.getLogger(LogonMB.class);
	private boolean validou = false;
	private boolean gerente;

	@SuppressWarnings("unchecked")
	public void valida() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Funcionario> busca = null;
			long id_busca = email;

			busca = session.createQuery("FROM Funcionario WHERE id=" + id_busca).list();

			if (busca != null) {
				if (busca.isEmpty() == false) {
					user.setFunc_logado(busca.get(0));

					if (password.equals(user.getFunc_logado().getSenha())) {
						if(user.getFunc_logado().getFlg_ativo()) {
							validou = true;
						} else {
							Utils.addMensagem("Funcionário inativo", 4);
							validou = false;
						}
					} else {
						Utils.addMensagem("Senha incorreta", 4);
						validou = false;
					}
				} else {
					Utils.addMensagem("Usuário " + id_busca + " não existe", 4);
					validou = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	public void login() throws IOException {
		currentUser = user;
		user = new Usuario();
		Utils.addMensagem("Logado como <b>" + currentUser.getFunc_logado().getNome() + "</b>", 1);
		Faces.getExternalContext().getFlash().setKeepMessages(true);
		Faces.redirect("index.xhtml");
		logger.info("Logado como " + currentUser.getFunc_logado().getNome() + "[" + currentUser.getFunc_logado().getId()
				+ "]");

		if (currentUser.getFunc_logado().getDescricao().getId() == 2)
			gerente = true;
	}

	@Override
	public boolean isLoggedIn() {

		return currentUser != null;
	}

	public void desloga() {
		currentUser = null;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	public boolean isValidou() {
		return validou;
	}

	public void setValidou(boolean validou) {
		this.validou = validou;
	}

	public Integer getEmail() {
		return email;
	}

	public void setEmail(Integer email) {
		this.email = email;
	}

	public boolean isGerente() {
		return gerente;
	}

	public void setGerente(boolean gerente) {
		this.gerente = gerente;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Usuario getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Usuario currentUser) {
		this.currentUser = currentUser;
	}

}