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
import model.Cliente;
import model.Dependente;
import model.Estado;
import util.HibernateUtil;
import util.Utils;

@Named
@ViewScoped
public class ClientesController implements Serializable {
	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private boolean isEditando;
	private List<Cliente> lista_cliente;
	private List<Estado> lista_estados;
	private List<Cidade> lista_cidades;
	private List<Dependente> lista_dep;
	private int filtro_consulta;
	private int ordem_consulta;
	private String like_consulta;
	private int ativo_consulta;
	private static Logger logger = LogManager.getLogger(ClientesController.class);

	@PostConstruct
	private void inicialize() {
		limparCampos();
		listarEstados();
	}

	public void limparCampos() {
		cliente = new Cliente();
		cliente.setFlg_ativo(true);
		isEditando = false;
	}

	public void salvar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			// salva a atividade no banco de dados
			t = sessao.beginTransaction();
			sessao.merge(cliente);
			t.commit();

			Utils.addMsg("Sucesso", "Cliente salvo com sucesso", 1);
			logger.info("Cliente '" + cliente.getNome().toUpperCase() + "' salvo com sucesso");
			limparCampos(); // limpa os campos
		} catch (PersistenceException e) {
			if (t != null)
				t.rollback();

			if (Utils.stackTrace(e).contains("Duplicate entry") && Utils.stackTrace(e).contains("for key 'uk_cpf'")) {
				Utils.addMsg("Erro ao salvar cliente:", "O CPF digitado já está cadastrado no sistema", 3); // envia msg
																											// de erro
				logger.error("Erro ao salvar cliente: O CPF digitado já está cadastrado no sistema");
			} else {
				Utils.addMensagem("Erro ao salvar cliente: <i>" + e.getMessage() + "</i>", 3); // envia msg de erro
				logger.error("Erro ao salvar cliente: " + e.getMessage());
			}

		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			Utils.addMensagem("Erro ao salvar cliente: <i>" + e.getMessage() + "</i>", 3); // envia msg de erro
			logger.error("Erro ao salvar cliente: <i>" + e.getMessage() + "</i>");
		} finally {
			// finaliza a sessao
			sessao.close();
		}
	}
	
	public void excluir() {
		if(Utils.deletarBanco(cliente, logger)) {
			Utils.addMsg("Sucesso", "Cliente apagado com sucesso", 1);
			limparCampos(); // limpa os campos
		}
	}

	public void inativar() {
		// abre uma sessão no banco
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			cliente.setFlg_ativo(false);

			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Cliente a SET flg_ativo = 0 WHERE id = '" + cliente.getId() + "'")
					.executeUpdate();
			t.commit();

			Utils.addMsg("Sucesso", "Cliente inativado", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			cliente.setFlg_ativo(true);
			Utils.addMsg("Erro", "Erro ao inativar cliente", 3);
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
			cliente.setFlg_ativo(true);

			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Cliente a SET flg_ativo = 1 WHERE id = '" + cliente.getId() + "'")
					.executeUpdate();
			t.commit();

			Utils.addMsg("Sucesso", "Cliente reativado", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			cliente.setFlg_ativo(false);
			Utils.addMsg("Erro", "Erro ao reativar cliente", 3);
		} finally {
			// finaliza a sessão
			sessao.close();
		}
	}

	public void seleciona(ActionEvent evt) {
		limparCampos();
		cliente = (Cliente) evt.getComponent().getAttributes().get("cli_edita");
		listarDependentes();
		isEditando = true;
		listarCidades();
	}

	@SuppressWarnings("unchecked")
	public void listarClientes() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			lista_cliente = session.createQuery("FROM Cliente a ORDER BY a.nome").list();
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar clientes", 3); // caso ocorra erro envia
																// msg
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	@SuppressWarnings("unchecked")
	public void filtraClientes() {
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
			String sql = "FROM Cliente a " + filtro + ativo + ordem;
			System.out.println(sql);
			lista_cliente = session.createQuery(sql).list();

		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao filtrar clientes", 3); // caso ocorra erro envia
																	// msg
		} finally {
			session.close(); // finaliza a sessão
		}

	}

	@SuppressWarnings("unchecked")
	public void listar() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Cliente> busca = null;
			long id_busca = cliente.getId();

			busca = session.createQuery("FROM Cliente WHERE id=" + id_busca).list();

			if (busca != null) {
				if (busca.isEmpty() == false) {
					cliente = busca.get(0);
					listarDependentes();
					isEditando = true;
				} else {
					List<Cliente> cli = session.createQuery("FROM Cliente WHERE cpf='" + cliente.getCpf() + "'").list();

					if (cli != null) {
						if (cli.isEmpty() == false)
							cliente.setId(cli.get(0).getId());
						else
							cliente.setId(null);
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
			Estado est = cliente.getUf();
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
	public void listarDependentes() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			lista_dep = session.createQuery("FROM Dependente a WHERE a.id_cliente = " + cliente + " ORDER BY a.nome")
					.list();
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar dependentes", 3);
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	/// GETTERS E SETTERS
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isEditando() {
		return isEditando;
	}

	public void setEditando(boolean isEditando) {
		this.isEditando = isEditando;
	}

	public List<Cliente> getLista_cliente() {
		return lista_cliente;
	}

	public void setLista_cliente(List<Cliente> lista_cliente) {
		this.lista_cliente = lista_cliente;
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

	public void setLista_cidades(List<Cidade> lista_cidades) {
		this.lista_cidades = lista_cidades;
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

	public List<Dependente> getLista_dep() {
		return lista_dep;
	}

	public void setLista_dep(List<Dependente> lista_dep) {
		this.lista_dep = lista_dep;
	}

}
