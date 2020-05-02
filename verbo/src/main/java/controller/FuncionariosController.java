package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Cidade;
import model.Estado;
import model.Funcionario;
import model.GrupoAcesso;
import util.HibernateUtil;
import util.Utils;

@Named
@ViewScoped
public class FuncionariosController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Funcionario funcionario;
	private Funcionario funcionario2;
	private boolean isEditando;
	private List<Funcionario> lista_funcionario;
	private List<Estado> lista_estados;
	private List<Cidade> lista_cidades;
	private List<GrupoAcesso> lista_grupos;
	private int filtro_consulta;
	private int ordem_consulta;
	private String like_consulta;
	private int ativo_consulta;
	private static Logger logger = LogManager.getLogger(FuncionariosController.class);

	@PostConstruct
	private void inicialize() {
		funcionario2 = new Funcionario();
		limparCampos();
		listarEstados();
		listarGrupos();
	}

	public void limparCampos() {
		funcionario = null;
		funcionario = new Funcionario();
		isEditando = false;
	}

	public void salvar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			// salva a atividade no banco de dados
			t = sessao.beginTransaction();
			sessao.merge(funcionario);
			t.commit();

			Utils.addMsg("Sucesso", "Funcionário salvo com sucesso", 1);
			logger.info("Funcionário '" + funcionario.getNome().toUpperCase() + "' salvo com sucesso");
			limparCampos(); // limpa os campos
		} catch (PersistenceException e) {
			if (t != null)
				t.rollback();

			if (Utils.stackTrace(e).contains("Duplicate entry") && Utils.stackTrace(e).contains("for key 'uk_cpf'")) {
				Utils.addMsg("Erro ao salvar funcionario:", "<i>O CPF digitado já está cadastrado no sistema</i>", 3); // envia
																														// msg
																														// de
																														// erro
				logger.error("Erro ao salvar funcionario:", "<i>O CPF digitado já está cadastrado no sistema</i>");
			} else {
				Utils.addMensagem("Erro ao salvar funcionario: <i>" + e.getMessage() + "</i>", 3); // envia msg de erro
				logger.error("Erro ao salvar funcionario: <i>" + e.getMessage() + "</i>");
			}

		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			Utils.addMensagem("Erro ao salvar funcionario: <i>" + e.getMessage() + "</i>", 3); // envia msg de erro
			logger.error("Erro ao salvar funcionario: <i>" + e.getMessage() + "</i>");
		} finally {
			// finaliza a sessao
			sessao.close();
		}
	}

	public void inativar() {
		// abre uma sessão no banco
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			funcionario.setFlg_ativo(false);

			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Funcionario a SET flg_ativo = 0 WHERE id = '" + funcionario.getId() + "'")
					.executeUpdate();
			t.commit();

			Utils.addMsg("Sucesso", "Funcionario inativado", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			funcionario.setFlg_ativo(true);
			Utils.addMsg("Erro", "Erro ao inativar funcionario", 3);
		} finally {
			// finaliza a sessão
			sessao.close();
		}
	}

	public void ativar() {
		// abre uma sessão no banco
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			funcionario.setFlg_ativo(true);

			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Funcionario a SET flg_ativo = 1 WHERE id = '" + funcionario.getId() + "'")
					.executeUpdate();
			t.commit();

			Utils.addMsg("Sucesso", "Funcionario reativado", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			funcionario.setFlg_ativo(false);
			Utils.addMsg("Erro", "Erro ao reativar funcionario", 3);
		} finally {
			// finaliza a sessão
			sessao.close();
		}
	}

	public void seleciona(ActionEvent evt) {
		limparCampos();
		funcionario = (Funcionario) evt.getComponent().getAttributes().get("func_edita");
		isEditando = true;
		listarCidades();
	}

	@SuppressWarnings("unchecked")
	public void listarFuncionarios() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			setLista_funcionario(session.createQuery("FROM Funcionario a ORDER BY a.id").list()); // busca as atividades
																									// salvas
			// no banco, ordenadas pela
			// id
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar funcionarios", 3); // caso ocorra erro envia
																	// msg
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	@SuppressWarnings("unchecked")
	public void filtraFuncionarios() {
		String filtro = "";
		String ativo = "";
		String ordem = "";

		if (like_consulta != null) {
			if (like_consulta.isEmpty() == false) {
				switch (filtro_consulta) {
				case 1:
					filtro = "WHERE a.id LIKE '%" + like_consulta + "%'";
					break;
				case 2:
					filtro = "WHERE a.nome LIKE '%" + like_consulta + "%'";
					break;
				case 3:
					filtro = "WHERE a.cpf LIKE '%" + like_consulta + "%'";
					break;
				case 4:
					filtro = "WHERE a.cidade.nome LIKE '%" + like_consulta + "%'";
					break;
				case 5:
					filtro = "WHERE a.uf.uf LIKE '%" + like_consulta + "%'";
					break;
				default:
					filtro = "";
					break;
				}
			}
		}

		switch (ativo_consulta) {
		case 1:
			if (filtro == "")
				ativo = "WHERE a.flg_ativo = 1";
			else
				ativo = " AND a.flg_ativo = 1";
			break;
		case 2:
			if (filtro == "")
				ativo = "WHERE a.flg_ativo = 0";
			else
				ativo = " AND a.flg_ativo = 0";
			break;

		case 3:
			ativo = "";
			break;

		default:
			ativo = "";
			break;
		}

		switch (ordem_consulta) {
		case 1:
			ordem = " ORDER BY a.id";
			break;
		case 2:
			ordem = " ORDER BY a.nome";
			break;
		case 3:
			ordem = " ORDER BY a.cpf";
			break;
		case 4:
			ordem = " ORDER BY a.cidade.nome";
			break;
		case 5:
			ordem = " ORDER BY a.uf.uf";
			break;
		default:
			ordem = "";
			break;
		}

		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			String sql = "FROM Funcionario a " + filtro + ativo + ordem;
			System.out.println(sql);
			lista_funcionario = session.createQuery(sql).list();

		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao filtrar funcionarios", 3); // caso ocorra erro envia
																		// msg
		} finally {
			session.close(); // finaliza a sessão
		}

	}

	@SuppressWarnings("unchecked")
	public void listar() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Funcionario> busca = null;
			long id_busca = funcionario.getId();

			busca = session.createQuery("FROM Funcionario WHERE id=" + id_busca).list();

			if (busca != null) {
				if (busca.isEmpty() == false) {
					funcionario = busca.get(0);
					isEditando = true;
				} else {
					List<Funcionario> func = session
							.createQuery("FROM Funcionario WHERE cpf='" + funcionario.getCpf() + "'").list();

					if (func != null) {
						if (func.isEmpty() == false)
							funcionario.setId(func.get(0).getId());
						else
							funcionario.setId(null);
					}

					Utils.addMensagem("Registro não encontrado para o código " + id_busca, 2);
					isEditando = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	@SuppressWarnings("unchecked")
	public void listarEstados() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			lista_estados = session.createQuery("FROM Estado a ORDER BY a.nome").list();
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar estados", 3);
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	@SuppressWarnings("unchecked")
	public void listarCidades() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Estado est = funcionario.getUf();
			if (est != null)
				lista_cidades = session
						.createQuery("FROM Cidade a WHERE a.codigo_uf =" + est.getId() + " ORDER BY a.nome").list();
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar cidades", 3);
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	@SuppressWarnings("unchecked")
	public void listarGrupos() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			lista_grupos = session.createQuery("FROM GrupoAcesso g ORDER BY g.descricao").list();
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar grupos de acesso", 3);
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	public void excluir() {
		if(Utils.deletarBanco(funcionario, logger)) {
			Utils.addMsg("Sucesso", "Funcionario apagado com sucesso", 1);
			limparCampos(); // limpa os campos
		}
	}
	
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public boolean isEditando() {
		return isEditando;
	}

	public void setEditando(boolean isEditando) {
		this.isEditando = isEditando;
	}

	public List<Funcionario> getLista_funcionario() {
		return lista_funcionario;
	}

	public void setLista_funcionario(List<Funcionario> lista_funcionario) {
		this.lista_funcionario = lista_funcionario;
	}

	public List<Estado> getLista_estados() {
		return lista_estados;
	}

	public void setLista_estados(List<Estado> lista_estados) {
		this.lista_estados = lista_estados;
	}

	public List<Cidade> getLista_cidades() {
		return lista_cidades;
	}

	public int getFiltro_consulta() {
		return filtro_consulta;
	}

	public void setFiltro_consulta(int filtro_consulta) {
		this.filtro_consulta = filtro_consulta;
	}

	public int getOrdem_consulta() {
		return ordem_consulta;
	}

	public void setOrdem_consulta(int ordem_consulta) {
		this.ordem_consulta = ordem_consulta;
	}

	public List<GrupoAcesso> getLista_grupos() {
		return lista_grupos;
	}

	public void setLista_grupos(List<GrupoAcesso> lista_grupos) {
		this.lista_grupos = lista_grupos;
	}

	public String getLike_consulta() {
		return like_consulta;
	}

	public void setLike_consulta(String like_consulta) {
		this.like_consulta = like_consulta;
	}

	public int getAtivo_consulta() {
		return ativo_consulta;
	}

	public void setAtivo_consulta(int ativo_consulta) {
		this.ativo_consulta = ativo_consulta;
	}

	public void setLista_cidades(List<Cidade> lista_cidades) {
		this.lista_cidades = lista_cidades;
	}

	public Funcionario getFuncionario2() {
		return funcionario2;
	}

	public void setFuncionario2(Funcionario funcionario2) {
		this.funcionario2 = funcionario2;
	}

}
