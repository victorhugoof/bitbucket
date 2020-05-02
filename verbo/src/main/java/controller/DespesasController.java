package controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Despesa;
import util.HibernateUtil;
import util.Utils;

@Named
@ViewScoped
public class DespesasController implements Serializable {
	private static final long serialVersionUID = 1L;
	private Despesa desp;
	private List<Despesa> lista_desp;
	private boolean isEditando;
	private int filtro_consulta;
	private int ordem_consulta;
	private String like_consulta;
	private int ativo_consulta;
	private int recorrente_consulta;
	private static Logger logger = LogManager.getLogger(DespesasController.class);

	@PostConstruct
	private void inicialize() {
		limparCampos();
	}

	public void limparCampos() {
		desp = new Despesa();
		desp.setFlg_ativo(true);
		isEditando = false;
	}

	@SuppressWarnings("unchecked")
	public void listar() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Despesa> busca = null;
			Short id_busca = desp.getId();

			busca = session.createQuery("FROM Despesa WHERE id=" + id_busca).list();

			if (busca != null) {
				if (busca.isEmpty() == false) {
					desp = busca.get(0);
					isEditando = true;
				} else {
					List<Despesa> des = session.createQuery("FROM Despesa WHERE descricao='" + desp.getDescricao()
							+ "' AND valor=" + desp.getValor() + " AND data='" + desp.getData() + "'").list();

					if (des != null) {
						if (des.isEmpty() == false)
							desp.setId(des.get(0).getId());
						else
							desp.setId(null);
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
			lista_desp = session.createQuery("FROM Despesa a ORDER BY a.descricao").list();
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar clientes", 3);
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	public void inativar() {
		// abre uma sessão no banco
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			desp.setFlg_ativo(false);

			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Despesa a SET flg_ativo = 0 WHERE id = '" + desp.getId() + "'").executeUpdate();
			t.commit();

			Utils.addMsg("Sucesso", "Despesa inativada", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			desp.setFlg_ativo(true);
			Utils.addMsg("Erro", "Erro ao inativar despesa", 3);
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
			desp.setFlg_ativo(true);

			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Despesa a SET flg_ativo = 1 WHERE id = '" + desp.getId() + "'").executeUpdate();
			t.commit();

			Utils.addMsg("Sucesso", "Despesa reativada", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			desp.setFlg_ativo(false);
			Utils.addMsg("Erro", "Erro ao reativar despesa", 3);
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
			sessao.merge(desp);
			t.commit();

			limparCampos(); // limpa os campos
			Utils.addMsg("Sucesso", "Despesa salva com sucesso", 1); // envia msg de
																		// sucesso
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			logger.error("Erro ao salvar: " + Utils.stackTrace(e));
			Utils.addMsg("Erro", "Erro ao salvar despesa", 3); // envia msg de erro
		} finally {
			// finaliza a sessao
			sessao.close();
		}
	}

	public void seleciona(ActionEvent evt) {
		limparCampos();
		desp = (Despesa) evt.getComponent().getAttributes().get("desp_edita");
		isEditando = true;
	}

	@SuppressWarnings("unchecked")
	public void filtraDespesas() {
		String filtro = "";
		String ativo = "";
		String ordem = "";
		String recorre = "";

		if (like_consulta != null) {
			if (like_consulta.isEmpty() == false) {
				switch (filtro_consulta) {
				case 1:
					filtro = "WHERE a.id LIKE '%" + like_consulta + "%'";
					break;
				case 2:
					filtro = "WHERE a.descricao LIKE '%" + like_consulta + "%'";
					break;
				case 3:
					filtro = "WHERE a.valor LIKE '%" + like_consulta + "%'";
					break;
				case 4:
					filtro = "WHERE a.data LIKE '%" + like_consulta + "%'";
					break;
				default:
					filtro = "";
					break;
				}
			}
		}

		switch (ativo_consulta) {
		case 1:
			if (filtro == "" && recorre == "")
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

		switch (recorrente_consulta) {
		case 1:
			if (filtro == "" && ativo == "")
				recorre = "WHERE a.flg_recorrente = 0";
			else
				recorre = " AND a.flg_recorrente = 0";
			break;
		case 2:
			if (filtro == "" && ativo == "")
				recorre = "WHERE a.flg_recorrente = 1";
			else
				recorre = " AND a.flg_recorrente = 1";
			break;

		case 3:
			recorre = "";
			break;

		default:
			recorre = "";
			break;
		}

		switch (ordem_consulta) {
		case 1:
			ordem = " ORDER BY a.id";
			break;
		case 2:
			ordem = " ORDER BY a.descricao";
			break;
		case 3:
			ordem = " ORDER BY a.valor";
			break;
		case 4:
			ordem = " ORDER BY a.data";
			break;
		default:
			ordem = "";
			break;
		}

		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			String sql = "FROM Despesa a " + filtro + ativo + recorre + ordem;
			System.out.println(sql);
			lista_desp = session.createQuery(sql).list();

		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao filtrar despesas", 3); // caso ocorra erro envia
																	// msg
		} finally {
			session.close(); // finaliza a sessão
		}

	}

	public void excluir() {
		if(Utils.deletarBanco(desp, logger)) {
			Utils.addMsg("Sucesso", "Despesa apagada com sucesso", 1);
			limparCampos(); // limpa os campos
		}
	}
	
	
	// GETTERS E SETTERS
	public final Despesa getDesp() {
		return desp;
	}

	public final void setDesp(Despesa desp) {
		this.desp = desp;
	}

	public final List<Despesa> getLista_desp() {
		return lista_desp;
	}

	public final void setLista_desp(List<Despesa> lista_desp) {
		this.lista_desp = lista_desp;
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

	public boolean isEditando() {
		return isEditando;
	}

	public void setEditando(boolean isEditando) {
		this.isEditando = isEditando;
	}

	public int getRecorrente_consulta() {
		return recorrente_consulta;
	}

	public void setRecorrente_consulta(int recorrente_consulta) {
		this.recorrente_consulta = recorrente_consulta;
	}

}
