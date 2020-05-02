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

import model.Produto;
import util.HibernateUtil;
import util.Utils;

@Named
@ViewScoped
public class ProdutosController implements Serializable {

	private static final long serialVersionUID = 1L;
	private Produto produto;
	private static Logger logger = LogManager.getLogger(ProdutosController.class);
	private List<Produto> lista_produto;
	private Boolean selecionado = false;

	private int filtro_consulta;
	private int ordem_consulta;
	private String like_consulta;
	private int ativo_consulta;

	@PostConstruct
	private void initialize() {
		produto = new Produto();
	}

	public void limparCampos() {
		produto = new Produto();
		selecionado = false;

	}

	public void salvar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			// salva a atividade no banco de dados
			t = sessao.beginTransaction();
			sessao.merge(produto);
			t.commit();

			System.out.println(produto.getDescricao());
			System.out.println(produto.getPreco());
			System.out.println(produto.getDesconto());
			limparCampos(); // limpa os campos
			Utils.addMsg("Sucesso", "Produto salvo com sucesso", 1);
			logger.info("Produto salvo com sucesso");
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			Utils.addMsg("Erro", "Erro ao salvar produto", 3); // envia msg de erro
			logger.error("Erro ao salvar produto");
			logger.error(e.getMessage());
		} finally {
			// finaliza a sessao
			sessao.close();
		}
	}

	public void apagar() {
		// abre uma sessão no banco
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			if (produto.getFlg_ativo() == true) {
				produto.setFlg_ativo(false);

				t = sessao.beginTransaction();
				sessao.createQuery("UPDATE Produto a SET flg_ativo = 0 WHERE id = '" + produto.getId() + "'")
						.executeUpdate();
				t.commit();

				Utils.addMsg("Sucesso", "Produto inativo", 1);
			} else {
				produto.setFlg_ativo(true);

				t = sessao.beginTransaction();
				sessao.createQuery("UPDATE Produto a SET flg_ativo = 1 WHERE id = '" + produto.getId() + "'")
						.executeUpdate();
				t.commit();

				Utils.addMsg("Sucesso", "Produto ativo", 1);
			}
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			if (produto.getFlg_ativo() == true) {
				produto.setFlg_ativo(true);
				Utils.addMsg("Erro", "Erro ao inativar produto", 3);
			}
			if (produto.getFlg_ativo() == false) {
				produto.setFlg_ativo(false);
				Utils.addMsg("Erro", "Erro ao reativar produto", 3);
			}
		} finally {
			// finaliza a sessão
			sessao.close();
		}
	}

	public void seleciona(ActionEvent evt) {
		limparCampos();
		selecionado = true;
		produto = (Produto) evt.getComponent().getAttributes().get("pro_edita");
		System.out.println("selecionou");
	}

	@SuppressWarnings("unchecked")
	public void filtraProdutos() {
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
					filtro = "WHERE a.descricao LIKE '%" + like_consulta + "%'";
					break;
				case 3:
					filtro = "WHERE a.cod_barras LIKE '%" + like_consulta + "%'";
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
			ordem = " ORDER BY a.descricao";
			break;
		case 3:
			ordem = " ORDER BY a.cod_barras";
			break;

		default:
			ordem = "";
			break;
		}

		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			String sql = "FROM Produto a " + filtro + ativo + ordem;
			System.out.println(sql);
			lista_produto = session.createQuery(sql).list();

		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao filtrar produtos", 3); // caso ocorra erro envia
																	// msg
		} finally {
			session.close(); // finaliza a sessão
		}
		filtro = "";
		ativo = "";
		ordem = "";

		filtro_consulta = 0;
		ordem_consulta = 0;
		like_consulta = null;
		ativo_consulta = 0;
	}

	@SuppressWarnings("unchecked")
	public void listarProdutos() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			setLista_produto(session.createQuery("FROM Produto a ORDER BY a.descricao").list()); // busca as atividades
																									// salvas
			// no banco, ordenadas pela
			// id
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar produtos", 3); // caso ocorra erro envia
																// msg
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	@SuppressWarnings("unchecked")
	public void listar() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Produto> busca = null;
			Integer id_busca = produto.getId();
			if (id_busca != null) {
				busca = session.createQuery("FROM Produto WHERE id=" + id_busca).list();

				if (busca != null) {
					if (busca.isEmpty() == false) {
						produto = busca.get(0);
						selecionado = true;
					} else {
						List<Produto> pro = session.createQuery("FROM Produto WHERE id='" + produto.getId() + "'")
								.list();

						if (pro != null) {
							if (pro.isEmpty() == false)
								produto.setId(pro.get(0).getId());
							else
								produto.setId(null);
						}

						Utils.addMensagem("Registro não encontrado para o código " + id_busca, 2);
						produto = new Produto();
						selecionado = false;
					}
				}
			} else {
				selecionado = false;
				limparCampos();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	public void excluir() {
		if(Utils.deletarBanco(produto, logger)) {
			Utils.addMsg("Sucesso", "Produto apagado com sucesso", 1);
			limparCampos(); // limpa os campos
		}
	}
	
	
	public List<Produto> getLista_produto() {
		return lista_produto;
	}
	
	public void setLista_produto(List<Produto> lista_produto) {
		this.lista_produto = lista_produto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Boolean getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Boolean selecionado) {
		this.selecionado = selecionado;
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

}
