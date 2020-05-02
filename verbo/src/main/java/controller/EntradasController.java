package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Entrada;
import model.ItemEntrada;
import model.Produto;
import util.HibernateUtil;
import util.Utils;

@Named
@ViewScoped
public class EntradasController implements Serializable {
	private static final long serialVersionUID = 1L;

	private ItemEntrada itemEntrada;
	private ItemEntrada itemEdita;
	private static Logger logger = LogManager.getLogger(ProdutosController.class);
	private List<ItemEntrada> lista_itemEntrada;
	private List<Produto> lista_produto;
	private List<Entrada> lista_entrada;
	private Entrada entrada;
	private Boolean isEditando;
	private Integer codigo_selecionado;
	private Integer filtro_consulta;
	private Integer ordem_consulta;
	private String like_consulta;
	private Integer ativo_consulta;
	private Integer filtro_consulta_produto;
	private Integer ordem_consulta_produto;
	private String like_consulta_produto;
	private Integer ativo_consulta_produto;
	private String cod_barras;
	private Boolean editandoItem = false;
	private int contagemItens;
	private double valorTotal;

	@PostConstruct
	private void initialize() {
		limparCampos();
	}

	public void limparCampos() {
		entrada = new Entrada();
		itemEntrada = new ItemEntrada();
		itemEntrada.setId_entrada(entrada);
		lista_itemEntrada = null;
		lista_itemEntrada = new ArrayList<ItemEntrada>();
		cod_barras = null;
		valorTotal = 0;
		isEditando = false;
		limparBuscas();
	}

	public void limparBuscas() {
		filtro_consulta = 0;
		ordem_consulta = 2;
		like_consulta = "";
		ativo_consulta = 1;
		filtro_consulta_produto = 0;
		ordem_consulta_produto = 2;
		like_consulta_produto = "";
		ativo_consulta_produto = 1;
	}

	@SuppressWarnings("unchecked")
	public void listar() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Entrada> busca = null;
			Integer id_busca = entrada.getId();

			busca = session.createQuery("FROM Entrada WHERE id=" + id_busca).list();

			if (busca != null) {
				if (busca.isEmpty() == false) {
					entrada = busca.get(0);
					buscarItens();
					valorTotal = entrada.getValor();

					isEditando = true;
					codigo_selecionado = entrada.getId();
				} else {
					entrada.setId(codigo_selecionado);
					Utils.addMensagem("Registro não encontrado para o código " + id_busca, 2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	public void buscarEntradas() {
		limparBuscas();
		filtraEntradas();
	}

	@SuppressWarnings("unchecked")
	public void filtraEntradas() {
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
					filtro = "WHERE a.dt_entrada LIKE '%" + like_consulta + "%'";
					break;
				case 3:
					filtro = "WHERE a.valor like '%" + like_consulta + "%'";
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
				ativo = " WHERE a.flg_ativo = 1";
			else
				ativo = " AND a.flg_ativo = 1";
			break;
		case 2:
			if (filtro == "")
				ativo = " WHERE a.flg_ativo = 0";
			else
				ativo = " AND a.flg_ativo = 0";
			break;

		case 3:
			ativo = "";
			break;

		default:
			if (filtro == "")
				ativo = " WHERE a.flg_ativo = 1";
			else
				ativo = " AND a.flg_ativo = 1";
			break;
		}

		switch (ordem_consulta) {
		case 1:
			ordem = " ORDER BY a.id";
			break;
		case 2:
			ordem = " ORDER BY a.dt_entrada ";
			break;
		case 3:
			ordem = " ORDER BY a.valor ";
			break;
		default:
			ordem = " ORDER BY a.dt_entrada";
			break;
		}

		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			String sql = "FROM Entrada a " + filtro + ativo + ordem;
			lista_entrada = session.createQuery(sql).list();

		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao buscar entradas", 3); // caso ocorra erro envia
			e.printStackTrace(); // msg
		} finally {
			session.close(); // finaliza a sessão
		}

	}

	public void seleciona(ActionEvent evt) {
		limparCampos();
		entrada = (Entrada) evt.getComponent().getAttributes().get("ent_edita");
		buscarItens();
		valorTotal = entrada.getValor();
		isEditando = true;
		codigo_selecionado = entrada.getId();
	}

	@SuppressWarnings("unchecked")
	public void listarProdutos() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Produto> busca = null;
			String id_busca = cod_barras;

			busca = session.createQuery("FROM Produto WHERE id='" + id_busca + "'").list();

			if (busca != null) {
				if (busca.isEmpty() == false) { // achou por id
					itemEntrada.setProduto(busca.get(0));
					cod_barras = itemEntrada.getProduto().getId() + "";
				} else {
					busca = null;
					busca = session.createQuery("FROM Produto WHERE cod_barras='" + id_busca + "'").list();

					if (busca != null) {
						if (busca.isEmpty() == false) { // não encontrou por id tenta achar por cod_barras
							itemEntrada.setProduto(busca.get(0));
							cod_barras = itemEntrada.getProduto().getCod_barras() + "";
						} else {
							Utils.addMensagem("Registro não encontrado para o código " + id_busca, 2);
						}
					}
				}
			} else {
				Utils.addMensagem("Registro não encontrado para o código " + id_busca, 2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	@SuppressWarnings("unchecked")
	public void filtraProdutos() {
		String filtro = "";
		String ativo = "";
		String ordem = "";

		if (like_consulta_produto != null) {
			if (like_consulta_produto.isEmpty() == false) {
				switch (filtro_consulta_produto) {
				case 1:
					filtro = "WHERE a.id LIKE '%" + like_consulta_produto + "%'";
					break;
				case 2:
					filtro = "WHERE a.descricao LIKE '%" + like_consulta_produto + "%'";
					break;
				case 3:
					filtro = "WHERE a.cod_barras LIKE '%" + like_consulta_produto + "%'";
					break;
				case 4:
					filtro = "WHERE a.preco LIKE '%" + like_consulta_produto + "%'";
					break;
				case 5:
					filtro = "WHERE a.qt_estoque LIKE '%" + like_consulta_produto + "%'";
					break;
				default:
					filtro = "";
					break;
				}
			}
		}

		switch (ativo_consulta_produto) {
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
			if (filtro == "")
				ativo = "WHERE a.flg_ativo = 1";
			else
				ativo = " AND a.flg_ativo = 1";
			break;
		}

		switch (ordem_consulta_produto) {
		case 1:
			ordem = " ORDER BY a.id";
			break;
		case 2:
			ordem = " ORDER BY a.descricao";
			break;
		case 3:
			ordem = " ORDER BY a.cod_barras";
			break;
		case 4:
			ordem = " ORDER BY a.preco";
			break;
		case 5:
			ordem = " ORDER BY a.qt_estoque";
			break;
		default:
			ordem = " ORDER by a.descricao";
			break;
		}

		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			String sql = "FROM Produto a " + filtro + ativo + ordem;
			lista_produto = session.createQuery(sql).list();

		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao buscar produtos", 3);
		} finally {
			session.close(); // finaliza a sessão
		}

	}

	public void buscarProdutos() {
		limparBuscas();
		filtraProdutos();
	}

	public void selecionaProduto(ActionEvent evt) {
		itemEntrada.setProduto((Produto) evt.getComponent().getAttributes().get("pro_edita"));
		cod_barras = itemEntrada.getProduto().getCod_barras();
	}

	public void inserirItem() {
		if (editandoItem == false) {
			itemEntrada.setId_entrada(entrada);
			contagemItens++;
			itemEntrada.setIndice(contagemItens);
			lista_itemEntrada.add(itemEntrada);
			valorTotal = valorTotal + (itemEntrada.getVl_unit() * itemEntrada.getQt_entrada());
		} else {
			valorTotal = valorTotal - (itemEdita.getVl_unit() * itemEdita.getQt_entrada());
			valorTotal = valorTotal + (itemEntrada.getVl_unit() * itemEntrada.getQt_entrada());
		}

		itemEdita = null;
		itemEntrada = new ItemEntrada();
		cod_barras = null;
		editandoItem = false;
	}

	public void selecionaItem(ActionEvent evt) {
		itemEdita = (ItemEntrada) evt.getComponent().getAttributes().get("item_edita");
		itemEntrada = itemEdita;
		cod_barras = itemEntrada.getProduto().getCod_barras();
		editandoItem = true;
	}

	public void apagaItem(ActionEvent evt) {
		ItemEntrada i = (ItemEntrada) evt.getComponent().getAttributes().get("item_exclui");
		valorTotal = valorTotal - (i.getVl_unit() * i.getQt_entrada());
		lista_itemEntrada.remove(i);
		itemEntrada = new ItemEntrada();
		cod_barras = null;
	}

	@SuppressWarnings("unchecked")
	public void buscarItens() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			lista_itemEntrada = session
					.createQuery("FROM ItemEntrada a WHERE a.id_entrada = " + entrada.getId() + " ORDER BY a.indice")
					.list();
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar itens", 3);
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	public boolean salvarEntrada() throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		;

		try {
			t = sessao.beginTransaction();
			entrada.setValor(valorTotal);
			sessao.merge(entrada);
			t.commit();

			return true;
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			Utils.addMsg("Erro", "Erro ao salvar entrada de mercadorias", 3); // envia msg de erro
			logger.error("Erro ao gerar entrada no banco: " + Utils.stackTrace(e));
			return false;
		}

	}

	public boolean salvarItens() throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			Query q = sessao.createQuery("FROM Entrada e ORDER BY e.id desc");
			q.setMaxResults(1);
			Entrada ultima = (Entrada) q.getResultList().get(0);
			t = sessao.beginTransaction();

			for (int i = 0; i < lista_itemEntrada.size(); i++) {
				lista_itemEntrada.get(i).setId_entrada(ultima);
				sessao.merge(lista_itemEntrada.get(i));

				lista_itemEntrada.get(i).getProduto()
						.setQt_estoque(lista_itemEntrada.get(i).getProduto().getQt_estoque()
								+ lista_itemEntrada.get(i).getQt_entrada());
				sessao.merge(lista_itemEntrada.get(i).getProduto());
			}

			Utils.addMsg("Sucesso", "Entrada de mercadorias salva com sucesso", 1);
			t.commit();
			return true;
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();
			Utils.addMsg("Erro", "Erro ao salvar entrada de mercadorias", 3); // envia msg de erro
			logger.error("Erro ao salvar itens entrada: " + Utils.stackTrace(e));
			return false;
		}
	}

	public void salvar() {
		if (lista_itemEntrada.isEmpty() == false || lista_itemEntrada != null) {
			try {
				if (salvarEntrada())
					if (salvarItens())
						limparCampos();
			} catch (Exception e) {
				logger.error("Erro ao salvar entrada: " + Utils.stackTrace(e));
				Utils.addMsg("Erro", "Erro ao salvar entrada de mercadorias", 3); // envia msg de erro
			}
		} else {
			Utils.addMsg("Erro", "Você precisa colocar ao menos um item", 3); // envia msg de erro
		}
	}

	public void inativar() {
		// abre uma sessão no banco
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			entrada.setFlg_ativo(false);

			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Entrada a SET flg_ativo = 0 WHERE id = '" + entrada.getId() + "'")
					.executeUpdate();
			sessao.createQuery("UPDATE ItemEntrada a set flg_ativo WHERE id_entrada = '" + entrada.getId() + "'")
					.executeUpdate();
			t.commit();

			Utils.addMsg("Sucesso", "Entrada de mercadorias inativada", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			entrada.setFlg_ativo(true);
			Utils.addMsg("Erro", "Erro ao inativar entrada de mercadorias", 3);
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
			entrada.setFlg_ativo(true);

			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Entrada a SET flg_ativo = 0 WHERE id = '" + entrada.getId() + "'")
					.executeUpdate();
			sessao.createQuery("UPDATE ItemEntrada a set flg_ativo WHERE id_entrada = '" + entrada.getId() + "'")
					.executeUpdate();
			t.commit();

			Utils.addMsg("Sucesso", "Entrada de mercadorias inativado", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			entrada.setFlg_ativo(false);
			Utils.addMsg("Erro", "Erro ao ativar entrada de mercadorias", 3);
		} finally {
			// finaliza a sessão
			sessao.close();
		}
	}

	// GETTERS E SETTERS
	public ItemEntrada getItemEntrada() {
		return itemEntrada;
	}

	public void setItemEntrada(ItemEntrada itemEntrada) {
		this.itemEntrada = itemEntrada;
	}

	public List<ItemEntrada> getLista_itemEntrada() {
		return lista_itemEntrada;
	}

	public void setLista_itemEntrada(List<ItemEntrada> lista_itemEntrada) {
		this.lista_itemEntrada = lista_itemEntrada;
	}

	public List<Produto> getLista_produto() {
		return lista_produto;
	}

	public void setLista_produto(List<Produto> lista_produto) {
		this.lista_produto = lista_produto;
	}

	public List<Entrada> getLista_entrada() {
		return lista_entrada;
	}

	public void setLista_entrada(List<Entrada> lista_entrada) {
		this.lista_entrada = lista_entrada;
	}

	public Entrada getEntrada() {
		return entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	public Boolean getIsEditando() {
		return isEditando;
	}

	public void setIsEditando(Boolean isEditando) {
		this.isEditando = isEditando;
	}

	public Integer getCodigo_selecionado() {
		return codigo_selecionado;
	}

	public void setCodigo_selecionado(Integer codigo_selecionado) {
		this.codigo_selecionado = codigo_selecionado;
	}

	public Integer getFiltro_consulta() {
		return filtro_consulta;
	}

	public void setFiltro_consulta(Integer filtro_consulta) {
		this.filtro_consulta = filtro_consulta;
	}

	public Integer getOrdem_consulta() {
		return ordem_consulta;
	}

	public void setOrdem_consulta(Integer ordem_consulta) {
		this.ordem_consulta = ordem_consulta;
	}

	public String getLike_consulta() {
		return like_consulta;
	}

	public void setLike_consulta(String like_consulta) {
		this.like_consulta = like_consulta;
	}

	public Integer getAtivo_consulta() {
		return ativo_consulta;
	}

	public void setAtivo_consulta(Integer ativo_consulta) {
		this.ativo_consulta = ativo_consulta;
	}

	public String getCod_barras() {
		return cod_barras;
	}

	public void setCod_barras(String cod_barras) {
		this.cod_barras = cod_barras;
	}

	public Integer getFiltro_consulta_produto() {
		return filtro_consulta_produto;
	}

	public void setFiltro_consulta_produto(Integer filtro_consulta_produto) {
		this.filtro_consulta_produto = filtro_consulta_produto;
	}

	public Integer getOrdem_consulta_produto() {
		return ordem_consulta_produto;
	}

	public void setOrdem_consulta_produto(Integer ordem_consulta_produto) {
		this.ordem_consulta_produto = ordem_consulta_produto;
	}

	public String getLike_consulta_produto() {
		return like_consulta_produto;
	}

	public void setLike_consulta_produto(String like_consulta_produto) {
		this.like_consulta_produto = like_consulta_produto;
	}

	public Integer getAtivo_consulta_produto() {
		return ativo_consulta_produto;
	}

	public void setAtivo_consulta_produto(Integer ativo_consulta_produto) {
		this.ativo_consulta_produto = ativo_consulta_produto;
	}

	public Boolean getEditandoItem() {
		return editandoItem;
	}

	public void setEditandoItem(Boolean editandoItem) {
		this.editandoItem = editandoItem;
	}

	public int getContagemItens() {
		return contagemItens;
	}

	public void setContagemItens(int contagemItens) {
		this.contagemItens = contagemItens;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

/// GETTERS E SETTERS
}
