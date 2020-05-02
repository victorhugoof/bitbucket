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

import model.Cliente;
import model.Dependente;
import util.HibernateUtil;
import util.Utils;

@Named
@ViewScoped
public class DependentesController implements Serializable {
	private static final long serialVersionUID = 1L;
	private Dependente dep;
	private boolean isEditando;
	private List<Dependente> lista_dep;
	private List<Cliente> lista_cliente;
	private int filtro_consulta;
	private int ordem_consulta;
	private String like_consulta;
	private int ativo_consulta;
	private int filtro_consulta_cliente;
	private int ordem_consulta_cliente;
	private String like_consulta_cliente;
	private int ativo_consulta_cliente;
	private Integer cliente_id;
	private String nome_cliente;
	private static Logger logger = LogManager.getLogger(DependentesController.class);

	@PostConstruct
	private void inicialize() {
		limparCampos();
	}

	public void limparCampos() {
		dep = new Dependente();
		dep.setFlg_ativo(true);
		isEditando = false;
		cliente_id = null;
		nome_cliente = null;
		like_consulta_cliente = null;
		like_consulta = null;
	}

	@SuppressWarnings("unchecked")
	public void listar() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Dependente> busca = null;
			Short id_busca = dep.getId();

			busca = session.createQuery("FROM Dependente WHERE id=" + id_busca).list();

			if (busca != null) {
				if (busca.isEmpty() == false) {
					dep = busca.get(0);
					isEditando = true;

					cliente_id = dep.getId_cliente().getId();

					if (dep.getId_cliente().getUf() != null && dep.getId_cliente().getUf().getUf().isEmpty() == false) {
						if (dep.getId_cliente().getCidade() != null
								&& dep.getId_cliente().getCidade().getNome().isEmpty() == false)
							nome_cliente = dep.getId_cliente().getNome() + " ("
									+ dep.getId_cliente().getCidade().getNome() + " - "
									+ dep.getId_cliente().getUf().getUf() + ")";
						else
							nome_cliente = dep.getId_cliente().getNome();
					} else
						nome_cliente = dep.getId_cliente().getNome();
				} else {
					List<Dependente> des = session.createQuery(
							"FROM Dependente WHERE nome='" + dep.getNome() + "' AND id_cliente=" + dep.getId_cliente())
							.list();

					if (des != null) {
						if (des.isEmpty() == false)
							dep.setId(des.get(0).getId());
						else
							dep.setId(null);
					}

					Utils.addMensagem("Registro não encontrado para o código " + id_busca, 2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	@SuppressWarnings("unchecked")
	public void listarTodos() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			lista_dep = session.createQuery("FROM Dependente a ORDER BY a.nome").list();
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar dependentes", 3);
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	@SuppressWarnings("unchecked")
	public void listarTodosClientes() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			lista_cliente = session.createQuery("FROM Cliente a ORDER BY a.nome").list();
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar clientes", 3);
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	@SuppressWarnings("unchecked")
	public void listarCliente() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Cliente> busca = null;
			Integer id_busca = cliente_id;

			busca = session.createQuery("FROM Cliente WHERE id=" + id_busca).list();

			if (busca != null) {
				if (busca.isEmpty() == false) {
					dep.setId_cliente(busca.get(0));
					cliente_id = dep.getId_cliente().getId();

					if (dep.getId_cliente().getUf() != null && dep.getId_cliente().getUf().getUf().isEmpty() == false) {
						if (dep.getId_cliente().getCidade() != null
								&& dep.getId_cliente().getCidade().getNome().isEmpty() == false)
							nome_cliente = dep.getId_cliente().getNome() + " ("
									+ dep.getId_cliente().getCidade().getNome() + " - "
									+ dep.getId_cliente().getUf().getUf() + ")";
						else
							nome_cliente = dep.getId_cliente().getNome();
					} else
						nome_cliente = dep.getId_cliente().getNome();
				} else {
					Utils.addMensagem("Registro não encontrado para o código " + id_busca, 2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	public void inativar() {
		// abre uma sessão no banco
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			dep.setFlg_ativo(false);

			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Dependente a SET flg_ativo = 0 WHERE id = '" + dep.getId() + "'")
					.executeUpdate();
			t.commit();

			Utils.addMsg("Sucesso", "Dependente inativada", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			dep.setFlg_ativo(true);
			Utils.addMsg("Erro", "Erro ao inativar dependente", 3);
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
			dep.setFlg_ativo(true);

			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Dependente a SET flg_ativo = 1 WHERE id = '" + dep.getId() + "'")
					.executeUpdate();
			t.commit();

			Utils.addMsg("Sucesso", "Dependente reativada", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			dep.setFlg_ativo(false);
			Utils.addMsg("Erro", "Erro ao reativar dependente", 3);
		} finally {
			// finaliza a sessão
			sessao.close();
		}
	}

	public void salvar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			// salva a atividade no banco de dados
			t = sessao.beginTransaction();
			sessao.merge(dep);
			t.commit();

			limparCampos(); // limpa os campos
			Utils.addMsg("Sucesso", "Dependente salva com sucesso", 1); // envia msg de
																		// sucesso
		} catch (PersistenceException e) {
			if (t != null)
				t.rollback();

			if (Utils.stackTrace(e).contains("Duplicate entry") && Utils.stackTrace(e).contains("for key 'uk_nome'")) {
				Utils.addMsg("Erro ao salvar dependente:",
						"<i>Já possui um dependente com este nome para o cliente selecionado</i>", 3); // envia msg de
																										// erro
				logger.error("Erro ao salvar dependente:",
						"<i>Já possui um dependente com este nome para o cliente selecionado</i>");
			} else {
				Utils.addMensagem("Erro ao salvar dependente: <i>" + e.getMessage() + "</i>", 3); // envia msg de erro
				logger.error("Erro ao salvar dependente: <i>" + e.getMessage() + "</i>");
			}

		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			logger.error("Erro ao salvar: " + Utils.stackTrace(e));
			Utils.addMsg("Erro", "Erro ao salvar dependente", 3); // envia msg de erro
		} finally {
			// finaliza a sessao
			sessao.close();
		}
	}

	public void seleciona(ActionEvent evt) {
		limparCampos();
		dep = (Dependente) evt.getComponent().getAttributes().get("dep_edita");
		isEditando = true;

		cliente_id = dep.getId_cliente().getId();

		if (dep.getId_cliente().getUf() != null && dep.getId_cliente().getUf().getUf().isEmpty() == false) {
			if (dep.getId_cliente().getCidade() != null && dep.getId_cliente().getCidade().getNome().isEmpty() == false)
				nome_cliente = dep.getId_cliente().getNome() + " (" + dep.getId_cliente().getCidade().getNome() + " - "
						+ dep.getId_cliente().getUf().getUf() + ")";
			else
				nome_cliente = dep.getId_cliente().getNome();
		} else
			nome_cliente = dep.getId_cliente().getNome();
	}

	public void selecionaCliente(ActionEvent evt) {
		dep.setId_cliente((Cliente) evt.getComponent().getAttributes().get("cli_edita"));
		cliente_id = dep.getId_cliente().getId();

		if (dep.getId_cliente().getUf() != null && dep.getId_cliente().getUf().getUf().isEmpty() == false) {
			if (dep.getId_cliente().getCidade() != null && dep.getId_cliente().getCidade().getNome().isEmpty() == false)
				nome_cliente = dep.getId_cliente().getNome() + " (" + dep.getId_cliente().getCidade().getNome() + " - "
						+ dep.getId_cliente().getUf().getUf() + ")";
			else
				nome_cliente = dep.getId_cliente().getNome();
		} else
			nome_cliente = dep.getId_cliente().getNome();
	}

	@SuppressWarnings("unchecked")
	public void filtraDependentes() {
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
					filtro = "WHERE a.id_cliente.nome LIKE '%" + like_consulta + "%'";
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
			ordem = " ORDER BY a.id_cliente.nome";
			break;
		default:
			ordem = "";
			break;
		}

		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			String sql = "FROM Dependente a " + filtro + ativo + ordem;
			System.out.println(sql);
			lista_dep = session.createQuery(sql).list();

		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao filtrar dependente", 3); // caso ocorra erro envia
			e.printStackTrace(); // msg
		} finally {
			session.close(); // finaliza a sessão
		}

	}

	@SuppressWarnings("unchecked")
	public void filtraClientes() {
		String filtro = "";
		String ativo = "";
		String ordem = "";

		if (like_consulta_cliente != null) {
			if (like_consulta_cliente.isEmpty() == false) {
				switch (filtro_consulta_cliente) {
				case 1:
					filtro = "WHERE a.id LIKE '%" + like_consulta_cliente + "%'";
					break;
				case 2:
					filtro = "WHERE a.nome LIKE '%" + like_consulta_cliente + "%'";
					break;
				case 3:
					filtro = "WHERE a.cpf LIKE '%" + like_consulta_cliente + "%'";
					break;
				case 4:
					filtro = "WHERE a.cidade.nome LIKE '%" + like_consulta_cliente + "%'";
					break;
				case 5:
					filtro = "WHERE a.uf.uf LIKE '%" + like_consulta_cliente + "%'";
					break;
				default:
					filtro = "";
					break;
				}
			}
		}

		switch (ativo_consulta_cliente) {
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

		switch (ordem_consulta_cliente) {
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
			String sql = "FROM Cliente a " + filtro + ativo + ordem;
			System.out.println(sql);
			lista_cliente = session.createQuery(sql).list();

		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao filtrar cliente", 3); // caso ocorra erro envia
																// msg
		} finally {
			session.close(); // finaliza a sessão
		}

	}

	public void excluir() {
		if(Utils.deletarBanco(dep, logger)) {
			Utils.addMsg("Sucesso", "Dependente apagado com sucesso", 1);
			limparCampos(); // limpa os campos
		}
	}
	
	/// GETTERS E SETTERS
	public Dependente getDep() {
		return dep;
	}

	public void setDep(Dependente dep) {
		this.dep = dep;
	}

	public boolean isEditando() {
		return isEditando;
	}

	public void setEditando(boolean isEditando) {
		this.isEditando = isEditando;
	}

	public List<Dependente> getLista_dep() {
		return lista_dep;
	}

	public void setLista_dep(List<Dependente> lista_dep) {
		this.lista_dep = lista_dep;
	}

	public List<Cliente> getLista_cliente() {
		return lista_cliente;
	}

	public void setLista_cliente(List<Cliente> lista_cliente) {
		this.lista_cliente = lista_cliente;
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

	public int getFiltro_consulta_cliente() {
		return filtro_consulta_cliente;
	}

	public void setFiltro_consulta_cliente(int filtro_consulta_cliente) {
		this.filtro_consulta_cliente = filtro_consulta_cliente;
	}

	public int getOrdem_consulta_cliente() {
		return ordem_consulta_cliente;
	}

	public void setOrdem_consulta_cliente(int ordem_consulta_cliente) {
		this.ordem_consulta_cliente = ordem_consulta_cliente;
	}

	public String getLike_consulta_cliente() {
		return like_consulta_cliente;
	}

	public void setLike_consulta_cliente(String like_consulta_cliente) {
		this.like_consulta_cliente = like_consulta_cliente;
	}

	public int getAtivo_consulta_cliente() {
		return ativo_consulta_cliente;
	}

	public void setAtivo_consulta_cliente(int ativo_consulta_cliente) {
		this.ativo_consulta_cliente = ativo_consulta_cliente;
	}

	public Integer getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Integer cliente_id) {
		this.cliente_id = cliente_id;
	}

	public String getNome_cliente() {
		return nome_cliente;
	}

	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}

	// GETTERS E SETTERS

}
