package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Cliente;
import model.CondicaoPagamento;
import model.Crediario;
import model.FormaPagamento;
import model.ItemVenda;
import model.ParcelasCred;
import model.Produto;
import model.Venda;
import util.HibernateUtil;
import util.Utils;

@Named
@ViewScoped
public class VendasController implements Serializable {
	private static final long serialVersionUID = 1L;

	private ItemVenda itemVenda;
	private Venda venda;
	private Crediario cred;
	private List<ItemVenda> lista_itemVenda;
	private List<Cliente> lista_cliente;
	private List<Venda> lista_venda;
	private List<Produto> lista_produto;
	private List<CondicaoPagamento> lista_condicao;
	private List<FormaPagamento> lista_forma;
	private List<ParcelasCred> lista_parc;
	private static Logger logger = LogManager.getLogger(VendasController.class);
	private int filtro_consulta;
	private int ordem_consulta;
	private String like_consulta;
	private int ativo_consulta;
	private Integer filtro_consulta_produto;
	private Integer ordem_consulta_produto;
	private String like_consulta_produto;
	private Integer ativo_consulta_produto;
	private Boolean isEditando = false;
	private String codigo_selecionado_produto;
	private String cod_barras;
	private int contagemItens;
	private double valorTotal;
	private Integer cliente_id;
	private String nome_cliente;
	private Short qtd_parcelas;
	private Double valor_parcelas;
	private boolean gerouParcelas = false;
	private Double valor_limite_usado;
	private boolean parcelado = false;

	@PostConstruct
	private void initialize() {
		limparCampos();
		listarCondicao();
		listarForma();
	}

	public void limparCampos() {
		itemVenda = new ItemVenda();
		venda = new Venda();
		lista_itemVenda = new ArrayList<>();
		isEditando = false;
		cod_barras = null;
		valorTotal = 0;
		nome_cliente = null;
		cliente_id = null;
		contagemItens = 0;
		codigo_selecionado_produto = null;
		cred = new Crediario();
		lista_parc = null;
		qtd_parcelas = 0;
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

	public void salvarVenda() throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		;

		try {
			t = sessao.beginTransaction();
			venda.setValor(valorTotal);
			sessao.merge(venda);
			t.commit();
			Utils.addMsg("Sucesso", "Venda salva com sucesso", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			Utils.addMsg("Erro", "Erro ao salvar venda", 3); // envia msg de erro
			logger.error("Erro ao gerar venda no banco: " + Utils.stackTrace(e));
		}

	}

	public void salvarItens() throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			Query q = sessao.createQuery("FROM Venda v ORDER BY v.id desc");
			q.setMaxResults(1);
			Venda ultima = (Venda) q.getResultList().get(0);
			t = sessao.beginTransaction();

			for (int i = 0; i < lista_itemVenda.size(); i++) {
				lista_itemVenda.get(i).setCodigo_venda(ultima);
				sessao.merge(lista_itemVenda.get(i));

				lista_itemVenda.get(i).getCodigo_produto().setQt_estoque(lista_itemVenda.get(i).getCodigo_produto().getQt_estoque() + lista_itemVenda.get(i).getQuantidade());
				sessao.merge(lista_itemVenda.get(i).getCodigo_produto());
			}
			t.commit();
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();
			Utils.addMsg("Erro", "Erro ao salvar entrada de mercadorias", 3); // envia msg de erro
			logger.error("Erro ao salvar itens entrada: " + Utils.stackTrace(e));
		}
	}

	public void salvar() {
		if (lista_itemVenda.isEmpty() == false || lista_itemVenda != null) {
			try {
				salvarVenda();
				salvarItens();
				salvarCred();
				limparCampos();
			} catch (Exception e) {
				logger.error("Erro ao salvar venda: " + Utils.stackTrace(e));
				Utils.addMsg("Erro", "Erro ao salvar venda", 3); // envia msg de erro
			}
		} else {
			Utils.addMsg("Erro", "Você precisa colocar ao menos um item", 3); // envia msg de erro
		}
	}

	@SuppressWarnings("unchecked")
	public void listarProdutos() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Produto> busca = null;
			String id_busca = cod_barras;

			busca = session.createQuery("FROM Produto WHERE cod_barras='" + id_busca + "'").list();

			if (busca != null) {
				if (busca.isEmpty() == false) {
					itemVenda.setCodigo_produto(busca.get(0));
					cod_barras = itemVenda.getCodigo_produto().getCod_barras() + "";
					codigo_selecionado_produto = itemVenda.getCodigo_produto().getCod_barras();
				} else {
					cod_barras = codigo_selecionado_produto;
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
			for (Produto pbanco : lista_produto) {
				for (ItemVenda itv : lista_itemVenda) {
					if (itv.getCodigo_produto().getId() == pbanco.getId()) {
						pbanco.setQt_estoque(pbanco.getQt_estoque() - itv.getQuantidade());
					}
				}
			}

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

	public void buscarClientes() {
		limparBuscas();
		filtraClientes();
	}

	public void selecionaProduto(ActionEvent evt) {
		itemVenda.setCodigo_produto((Produto) evt.getComponent().getAttributes().get("setPro"));
		codigo_selecionado_produto = itemVenda.getCodigo_produto().getCod_barras();
		cod_barras = itemVenda.getCodigo_produto().getCod_barras();
		itemVenda.setVl_unit(itemVenda.getCodigo_produto().getPreco());
		isEditando = true;
	}

	public void inserirItem() {
		itemVenda.setCodigo_venda(venda);
		contagemItens++;
		itemVenda.setIndice(contagemItens);
		lista_itemVenda.add(itemVenda);
		itemVenda.setVl_unit(itemVenda.getCodigo_produto().getPreco());
		valorTotal = valorTotal + (itemVenda.getVl_unit() * itemVenda.getQuantidade());
		itemVenda = new ItemVenda();
		cod_barras = null;
	}

	public void selecionaVenda(ActionEvent evt) {
		venda = (Venda) evt.getComponent().getAttributes().get("setVen");
		// cliente = venda.getCodigo_cliente();
	}

	public void selecionaCliente(ActionEvent evt) {
		venda.setCodigo_cliente((Cliente) evt.getComponent().getAttributes().get("cli_seleciona"));
		cliente_id = venda.getCodigo_cliente().getId();
		
		if (venda.getCodigo_cliente().getUf() != null
				&& venda.getCodigo_cliente().getUf().getUf().isEmpty() == false) {
			if (venda.getCodigo_cliente().getCidade() != null
					&& venda.getCodigo_cliente().getCidade().getNome().isEmpty() == false)
				nome_cliente = venda.getCodigo_cliente().getNome() + " ("
						+ venda.getCodigo_cliente().getCidade().getNome() + " - "
						+ venda.getCodigo_cliente().getUf().getUf() + ")";
			else
				nome_cliente = venda.getCodigo_cliente().getNome();
		} else
			nome_cliente = venda.getCodigo_cliente().getNome();
	}

	public void apagaItem(ActionEvent evt) {
		ItemVenda i = (ItemVenda) evt.getComponent().getAttributes().get("item_exclui");
		valorTotal = valorTotal - (i.getVl_unit() * i.getQuantidade());
		lista_itemVenda.remove(i);
		itemVenda = new ItemVenda();
		cod_barras = null;
	}

	public void somaTotal() {
		double soma = 0;
		for (ItemVenda it : lista_itemVenda) {
			soma += (it.getCodigo_produto().getPreco() * it.getQuantidade());
		}
		venda.setValor(soma);
	}

	@SuppressWarnings("unchecked")
	public void listarVendas() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			setLista_venda(session.createQuery("FROM Venda v ORDER BY v.id").list()); // busca as atividades
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar vendas", 3); // caso ocorra erro envia
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
	public void listarCliente() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Cliente> busca = null;
			Integer id_busca = cliente_id;

			busca = session.createQuery("FROM Cliente WHERE id=" + id_busca).list();

			if (busca != null) {
				if (busca.isEmpty() == false) {
					venda.setCodigo_cliente(busca.get(0));
					cliente_id = venda.getCodigo_cliente().getId();

					if (venda.getCodigo_cliente().getUf() != null
							&& venda.getCodigo_cliente().getUf().getUf().isEmpty() == false) {
						if (venda.getCodigo_cliente().getCidade() != null
								&& venda.getCodigo_cliente().getCidade().getNome().isEmpty() == false)
							nome_cliente = venda.getCodigo_cliente().getNome() + " ("
									+ venda.getCodigo_cliente().getCidade().getNome() + " - "
									+ venda.getCodigo_cliente().getUf().getUf() + ")";
						else
							nome_cliente = venda.getCodigo_cliente().getNome();
					} else
						nome_cliente = venda.getCodigo_cliente().getNome();
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

	@SuppressWarnings("unchecked")
	public void listarCondicao() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			lista_condicao = session.createQuery("FROM CondicaoPagamento a ORDER BY a.descricao").list();
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar condições de pagamento", 3);
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	@SuppressWarnings("unchecked")
	public void listarForma() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			lista_forma = session.createQuery("FROM FormaPagamento a ORDER BY a.id").list();
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar formas de pagamento", 3);
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	public void salvarCrediario() throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		;

		try {
			t = sessao.beginTransaction();
			sessao.merge(cred);
			sessao.merge(cred.getCliente());
			t.commit();

		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			logger.error("Erro ao gerar crediario no banco: " + Utils.stackTrace(e));
		}
	}

	public void salvarParcCred() throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			Query q = sessao.createQuery("FROM Crediario c ORDER BY c.id desc");
			q.setMaxResults(1);
			Crediario ultimo = (Crediario) q.getResultList().get(0);
			t = sessao.beginTransaction();

			for (int i = 0; i < lista_parc.size(); i++) {
				lista_parc.get(i).setCrediario(ultimo);
				sessao.merge(lista_parc.get(i));
			}

			Utils.addMsg("Sucesso", "Crediário salvo com sucesso", 1);
			limparCampos();
			t.commit();
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

		}
		sessao.close();
	}

	public void salvarCred() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		try {
			Query q = sessao.createQuery("FROM Venda c ORDER BY c.id desc");
			q.setMaxResults(1);
			Venda ultimo = (Venda) q.getResultList().get(0);
			t = sessao.beginTransaction();

			cred.setCliente(venda.getCodigo_cliente());
			cred.setVenda(ultimo);
			cred.setData_abertura(new Date());
			cred.setQtd_parcelas(qtd_parcelas);
			cred.setValor(ultimo.getValor());

			calculaParcelasCred();
			gerarParcelasCred();
			salvarCrediario();
			salvarParcCred();
		} catch (PersistenceException e) {
			if (t != null)
				t.rollback();

			if (Utils.stackTrace(e).contains("Duplicate entry")
					&& Utils.stackTrace(e).contains("for key 'uk_crediario_venda'")) {
				Utils.addMsg("Erro ao salvar crediario:", "<i>Já possui um crediario para esta venda</i>", 3); // envia
																												// msg
																												// de
																												// erro
				logger.error("Erro ao salvar crediario:", "<i>Já possui um crediario para esta venda</i>");
			} else {
				Utils.addMensagem("Erro ao salvar crediario: <i>" + e.getMessage() + "</i>", 3); // envia msg de erro
				logger.error("Erro ao salvar crediario: <i>" + e.getMessage() + "</i>");
			}

		} catch (Exception e) {
			logger.error("Erro ao salvar crediario: " + Utils.stackTrace(e));
			Utils.addMsg("Erro", "Erro ao salvar crediario", 3); // envia msg de erro
			if (t != null)
				t.rollback();

		}
		sessao.close();

	}

	public void calculaParcelasCred() {
		if (cred.getValor() != null && qtd_parcelas != null)
			setValor_parcelas(cred.getValor() / qtd_parcelas);

		if (cred.getValor() == null || qtd_parcelas == null)
			setValor_parcelas(null);
	}

	public void gerarParcelasCred() {
		lista_parc = null;
		lista_parc = new ArrayList<ParcelasCred>();

		for (int i = 0; i < qtd_parcelas; i++) {
			ParcelasCred p = new ParcelasCred();
			Date d = cred.getData_abertura();

			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1 + i);

			d = cal.getTime();

			p.setParcela_num(i + 1);
			p.setCrediario(cred);
			p.setValor(valor_parcelas);
			p.setData_vencimento(d);
			p.setData_pagamento(null);
			p.setPago(null);

			lista_parc.add(p);
		}

		cred.setQtd_parcelas(qtd_parcelas);

		if (gerouParcelas) {
			retiraLimite(valor_limite_usado, cred.getCliente());
			usaLimite(cred.getValor(), cred.getCliente());
		} else {
			usaLimite(cred.getValor(), cred.getCliente());
			valor_limite_usado = cred.getValor();
			gerouParcelas = true;
		}
	}

	public void retiraLimite(Double valor, Cliente c) {
		if (c.getLimite_utilizado() != null) {
			c.setLimite_utilizado(c.getLimite_utilizado() - valor);
		}
	}

	public void usaLimite(Double valor, Cliente c) {
		if (c.getLimite_utilizado() != null) {
			c.setLimite_utilizado(c.getLimite_utilizado() + valor);
		} else {
			c.setLimite_utilizado(valor);
		}
	}

	public void seParcelado() {
		if (venda.getCondicaoPag().getId() == 2) {
			parcelado = true;
			System.out.println("parcelado");
		} else {
			parcelado = false;
			System.out.println("avista");
		}

	}

	public ItemVenda getItemVenda() {
		return itemVenda;
	}

	public void setItemVenda(ItemVenda itemVenda) {
		this.itemVenda = itemVenda;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Crediario getCred() {
		return cred;
	}

	public void setCred(Crediario cred) {
		this.cred = cred;
	}

	public List<ItemVenda> getLista_itemVenda() {
		return lista_itemVenda;
	}

	public void setLista_itemVenda(List<ItemVenda> lista_itemVenda) {
		this.lista_itemVenda = lista_itemVenda;
	}

	public List<Cliente> getLista_cliente() {
		return lista_cliente;
	}

	public void setLista_cliente(List<Cliente> lista_cliente) {
		this.lista_cliente = lista_cliente;
	}

	public List<Venda> getLista_venda() {
		return lista_venda;
	}

	public void setLista_venda(List<Venda> lista_venda) {
		this.lista_venda = lista_venda;
	}

	public List<Produto> getLista_produto() {
		return lista_produto;
	}

	public void setLista_produto(List<Produto> lista_produto) {
		this.lista_produto = lista_produto;
	}

	public List<CondicaoPagamento> getLista_condicao() {
		return lista_condicao;
	}

	public void setLista_condicao(List<CondicaoPagamento> lista_condicao) {
		this.lista_condicao = lista_condicao;
	}

	public List<FormaPagamento> getLista_forma() {
		return lista_forma;
	}

	public void setLista_forma(List<FormaPagamento> lista_forma) {
		this.lista_forma = lista_forma;
	}

	public List<ParcelasCred> getLista_parc() {
		return lista_parc;
	}

	public void setLista_parc(List<ParcelasCred> lista_parc) {
		this.lista_parc = lista_parc;
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

	public Boolean getIsEditando() {
		return isEditando;
	}

	public void setIsEditando(Boolean isEditando) {
		this.isEditando = isEditando;
	}

	public String getCodigo_selecionado_produto() {
		return codigo_selecionado_produto;
	}

	public void setCodigo_selecionado_produto(String codigo_selecionado_produto) {
		this.codigo_selecionado_produto = codigo_selecionado_produto;
	}

	public String getCod_barras() {
		return cod_barras;
	}

	public void setCod_barras(String cod_barras) {
		this.cod_barras = cod_barras;
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

	public Short getQtd_parcelas() {
		return qtd_parcelas;
	}

	public void setQtd_parcelas(Short qtd_parcelas) {
		this.qtd_parcelas = qtd_parcelas;
	}

	public Double getValor_parcelas() {
		return valor_parcelas;
	}

	public void setValor_parcelas(Double valor_parcelas) {
		this.valor_parcelas = valor_parcelas;
	}

	public boolean isGerouParcelas() {
		return gerouParcelas;
	}

	public void setGerouParcelas(boolean gerouParcelas) {
		this.gerouParcelas = gerouParcelas;
	}

	public Double getValor_limite_usado() {
		return valor_limite_usado;
	}

	public void setValor_limite_usado(Double valor_limite_usado) {
		this.valor_limite_usado = valor_limite_usado;
	}

	public boolean isParcelado() {
		return parcelado;
	}

	public void setParcelado(boolean parcelado) {
		this.parcelado = parcelado;
	}

	// GETTERS E SETTERS
	
}
