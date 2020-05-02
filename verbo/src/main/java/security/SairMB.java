package security;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.security.LogoutMB;
import com.github.adminfaces.template.util.Constants;

/**
 * Created by rmpestano on 03/02/17.
 */
@Named
@RequestScoped
public class SairMB extends LogoutMB implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	AdminConfig adminConfig;

	@Override
	public void doLogout() throws IOException {
		String loginPage = adminConfig.getLoginPage();
		if (loginPage == null || "".equals(loginPage)) {
			loginPage = Constants.DEFAULT_LOGIN_PAGE;
		}
		if (!loginPage.startsWith("/")) {
			loginPage = "/" + loginPage;
		}
		Faces.getSession().invalidate();
		ExternalContext ec = Faces.getExternalContext();
		ec.redirect(ec.getRequestContextPath() + loginPage);

	}

}
