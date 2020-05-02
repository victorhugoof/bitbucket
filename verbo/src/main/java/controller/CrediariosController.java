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
import model.Crediario;
import model.ParcelasCred;
import util.HibernateUtil;
import util.Utils;

@Named
@ViewScoped
public class CrediariosController implements Serializable {
	private static final long serialVersionUID = 1L;
	private Crediario cred;
	private ParcelasCred parc;
	private List<Crediario> lista_credi;
	private List<ParcelasCred> lista_parc;
	private List<Cliente> lista_cliente;
	private boolean isEditando;
	private boolean isEditandoParc;
	private boolean gerouParcelas = false;
	private Double valor_limite_usado;
	private Integer codigo_selecionado;
	private Integer cliente_id;
	private String nome_cliente;
	private Double valor_parcelas;

	private Integer filtro_consulta;
	private Integer ordem_consulta;
	private String like_consulta;
	private Integer ativo_consulta;
	private Integer filtro_consulta_cliente;
	private Integer ordem_consulta_cliente;
	private String like_consulta_cliente;
	private Integer ativo_consulta_cliente;
	private Short qtd_parcelas;
	Double limite = null;

	// MODAL DE PAGAMENTO DE PARCELAS
	private ParcelasCred parc_sel;
	private Double valor_pagar;

	private static Logger logger = LogManager.getLogger(CrediariosController.class);

	@PostConstruct
	private void inicialize() {
		limparCampos();
		limparBuscas();
	}

	public void limparBuscas() {
		filtro_consulta = 0;
		ordem_consulta = 2;
		like_consulta = "";
		ativo_consulta = 1;
		filtro_consulta_cliente = 0;
		ordem_consulta_cliente = 2;
		like_consulta_cliente = "";
		ativo_consulta_cliente = 1;
	}

	public void limparCampos() {
		cred = new Crediario();
		cred.setFlg_ativo(true);
		cred.setData_abertura(new Date());
		isEditando = false;
		cliente_id = null;
		nome_cliente = null;
		lista_parc = null;
		qtd_parcelas = null;
		calculaLimite();
	}

	@SuppressWarnings("unchecked")
	public void listar() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Crediario> busca = null;
			Integer id_busca = cred.getId();

			busca = session.createQuery("FROM Crediario WHERE id=" + id_busca).list();

			if (busca != null) {
				if (busca.isEmpty() == false) {
					cred = busca.get(0);
					buscarParcelas();
					calculaParcelas();
					isEditando = true;
					codigo_selecionado = cred.getId();
					qtd_parcelas = cred.getQtd_parcelas();
					cliente_id = cred.getCliente().getId();

					if (cred.getCliente().getUf() != null && cred.getCliente().getUf().getUf().isEmpty() == false) {
						if (cred.getCliente().getCidade() != null
								&& cred.getCliente().getCidade().getNome().isEmpty() == false)
							nome_cliente = cred.getCliente().getNome() + " (" + cred.getCliente().getCidade().getNome()
									+ " - " + cred.getCliente().getUf().getUf() + ")";
						else
							nome_cliente = cred.getCliente().getNome();
					} else
						nome_cliente = cred.getCliente().getNome();
				} else {
					cred.setId(codigo_selecionado);
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
	public void listarCliente() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			List<Cliente> busca = null;
			Integer id_busca = cliente_id;

			busca = session.createQuery("FROM Cliente WHERE id=" + id_busca).list();

			if (busca != null) {
				if (busca.isEmpty() == false) {
					cred.setCliente(busca.get(0));
					cliente_id = cred.getCliente().getId();
					calculaLimite();

					if (cred.getCliente().getUf() != null && cred.getCliente().getUf().getUf().isEmpty() == false) {
						if (cred.getCliente().getCidade() != null
								&& cred.getCliente().getCidade().getNome().isEmpty() == false)
							nome_cliente = cred.getCliente().getNome() + " (" + cred.getCliente().getCidade().getNome()
									+ " - " + cred.getCliente().getUf().getUf() + ")";
						else
							nome_cliente = cred.getCliente().getNome();
					} else
						nome_cliente = cred.getCliente().getNome();
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
			cred.setFlg_ativo(false);

			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Crediario a SET flg_ativo = 0 WHERE id = '" + cred.getId() + "'")
					.executeUpdate();
			t.commit();

			Utils.addMsg("Sucesso", "Crediário inativado", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			cred.setFlg_ativo(true);
			Utils.addMsg("Erro", "Erro ao inativar crediario", 3);
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
			cred.setFlg_ativo(true);

			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Crediario a SET flg_ativo = 1 WHERE id = '" + cred.getId() + "'")
					.executeUpdate();
			t.commit();

			Utils.addMsg("Sucesso", "Crediário inativado", 1);
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			cred.setFlg_ativo(false);
			Utils.addMsg("Erro", "Erro ao ativar crediario", 3);
		} finally {
			// finaliza a sessão
			sessao.close();
		}
	}

	public void salvarCred() throws Exception {
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

	public void salvarParc() throws Exception {
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
	}

	public void salvar() {
		try {
			salvarCred();
			salvarParc();
		} catch (PersistenceException e) {

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
		}

	}

	public void seleciona(ActionEvent evt) {
		limparCampos();
		cred = (Crediario) evt.getComponent().getAttributes().get("cred_edita");
		buscarParcelas();
		calculaParcelas();
		isEditando = true;
		codigo_selecionado = cred.getId();
		qtd_parcelas = cred.getQtd_parcelas();
		cliente_id = cred.getCliente().getId();

		if (cred.getCliente().getUf() != null && cred.getCliente().getUf().getUf().isEmpty() == false) {
			if (cred.getCliente().getCidade() != null && cred.getCliente().getCidade().getNome().isEmpty() == false)
				nome_cliente = cred.getCliente().getNome() + " (" + cred.getCliente().getCidade().getNome() + " - "
						+ cred.getCliente().getUf().getUf() + ")";
			else
				nome_cliente = cred.getCliente().getNome();
		} else
			nome_cliente = cred.getCliente().getNome();
	}

	public void selecionaCliente(ActionEvent evt) {
		cred.setCliente((Cliente) evt.getComponent().getAttributes().get("cli_edita"));
		cliente_id = cred.getCliente().getId();
		calculaLimite();

		if (cred.getCliente().getUf() != null && cred.getCliente().getUf().getUf().isEmpty() == false) {
			if (cred.getCliente().getCidade() != null && cred.getCliente().getCidade().getNome().isEmpty() == false)
				nome_cliente = cred.getCliente().getNome() + " (" + cred.getCliente().getCidade().getNome() + " - "
						+ cred.getCliente().getUf().getUf() + ")";
			else
				nome_cliente = cred.getCliente().getNome();
		} else
			nome_cliente = cred.getCliente().getNome();
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
			if (filtro == "")
				ativo = "WHERE a.flg_ativo = 1";
			else
				ativo = " AND a.flg_ativo = 1";
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
			ordem = " ORDER by a.nome";
			break;
		}

		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			String sql = "FROM Cliente a " + filtro + ativo + ordem;
			lista_cliente = session.createQuery(sql).list();

		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao buscar clientes", 3);
		} finally {
			session.close(); // finaliza a sessão
		}

	}

	@SuppressWarnings("unchecked")
	public void filtraCrediarios() {
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
					filtro = "WHERE a.data_abertura LIKE '%" + like_consulta + "%'";
					break;
				case 3:
					filtro = "WHERE a.cliente.nome LIKE '%" + like_consulta + "%'";
					break;
				case 4:
					filtro = "WHERE a.venda.id LIKE '%" + like_consulta + "%'";
					break;
				case 5:
					filtro = "WHERE a.valor like '%" + like_consulta + "%'";
					break;
				case 6:
					filtro = "WHERE a.qtd_parcelas like '%" + like_consulta + "%'";
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
			ordem = " ORDER BY a.data_abertura ";
			break;
		case 3:
			ordem = " ORDER BY a.cliente.nome ";
			break;
		case 4:
			ordem = " ORDER BY a.venda.id ";
			break;
		case 5:
			ordem = " ORDER BY a.valor ";
			break;
		case 6:
			ordem = " ORDER BY a.qtd_parcelas ";
			break;
		default:
			ordem = " ORDER BY a.data_abertura";
			break;
		}

		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			String sql = "FROM Crediario a " + filtro + ativo + ordem;
			lista_credi = session.createQuery(sql).list();

		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao buscar crediarios", 3); // caso ocorra erro envia
			e.printStackTrace(); // msg
		} finally {
			session.close(); // finaliza a sessão
		}

	}

	public void buscarClientes() {
		limparBuscas();
		filtraClientes();
	}

	public void buscarCrediarios() {
		limparBuscas();
		filtraCrediarios();
	}

	@SuppressWarnings("unchecked")
	public void buscarParcelas() {
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			lista_parc = session
					.createQuery("FROM ParcelasCred a WHERE a.crediario = " + cred.getId() + " ORDER BY a.parcela_num")
					.list();
		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao listar parcelas", 3);
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	public void calculaParcelas() {
		if (cred.getValor() != null && qtd_parcelas != null)
			setValor_parcelas(cred.getValor() / qtd_parcelas);

		if (cred.getValor() == null || qtd_parcelas == null)
			setValor_parcelas(null);
	}

	public void gerarParcelas() {
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

	public void selecionaParc(ActionEvent evt) {
		parc_sel = (ParcelasCred) evt.getComponent().getAttributes().get("parc_edita");

		if (parc_sel.getPago() != null)
			valor_pagar = parc_sel.getValor() - parc_sel.getPago();
		else
			valor_pagar = parc_sel.getValor();
	}

	public void pagarParc() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;

		if (parc_sel.getPago() != null)
			parc_sel.setPago(parc_sel.getPago() + valor_pagar);
		else
			parc_sel.setPago(valor_pagar);

		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		d = cal.getTime();

		parc_sel.setData_pagamento(d);

		if (parc_sel.getPago().equals(parc_sel.getValor())) {
			parc_sel.setFlg_paga(true);

			if (cred.getQtd_parcelasPagas() != null)
				cred.setQtd_parcelasPagas(cred.getQtd_parcelasPagas() + 1);
			else
				cred.setQtd_parcelasPagas(1);
		}

		retiraLimite(valor_pagar, cred.getCliente());

		try {
			t = sessao.beginTransaction();
			sessao.merge(parc_sel);
			sessao.merge(cred);
			sessao.merge(cred.getCliente());
			Utils.addMsg("Sucesso", "Feito pagamento da parcela com sucesso", 1);
			parc_sel = null;
			t.commit();
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if (t != null)
				t.rollback();

			logger.error("Erro ao pagar: " + Utils.stackTrace(e));
			Utils.addMsg("Erro", "Erro ao pagar parcela", 3); // envia msg de erro
		} finally {
			sessao.close(); // finaliza a sessão
		}
	}

	public void pagarParcArray() {
		if (parc_sel.getPago() != null)
			parc_sel.setPago(parc_sel.getPago() + valor_pagar);
		else
			parc_sel.setPago(valor_pagar);

		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		d = cal.getTime();

		parc_sel.setData_pagamento(d);

		if (parc_sel.getPago().equals(parc_sel.getValor())) {
			parc_sel.setFlg_paga(true);
			if (cred.getQtd_parcelasPagas() != null)
				cred.setQtd_parcelasPagas(cred.getQtd_parcelasPagas() + 1);
			else
				cred.setQtd_parcelasPagas(1);
		}

		retiraLimite(valor_pagar, cred.getCliente());
		parc_sel = null;
	}

	public void calculaLimite() {
		if (cred.getCliente() != null) {
			if (cred.getCliente().getLimite_cred() != null)
				if (cred.getCliente().getLimite_utilizado() != null)
					limite = cred.getCliente().getLimite_cred() - cred.getCliente().getLimite_utilizado();
				else
					limite = cred.getCliente().getLimite_cred();
		} else {
			limite = null;
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

	public void excluir() {
		for(int i=0; i<lista_parc.size(); i++) {
			Utils.deletarBanco(lista_parc.get(i), logger);
		}
		
		if(Utils.deletarBanco(cred, logger)) {
			Utils.addMsg("Sucesso", "Crediario apagado com sucesso", 1);
			limparCampos(); // limpa os campos
		}
	}
	
	
	// GETTERS E SETTERS
	public Crediario getCred() {
		return cred;
	}

	public void setCred(Crediario cred) {
		this.cred = cred;
	}

	public List<Crediario> getLista_credi() {
		return lista_credi;
	}

	public void setLista_credi(List<Crediario> lista_credi) {
		this.lista_credi = lista_credi;
	}

	public List<ParcelasCred> getLista_parc() {
		return lista_parc;
	}

	public void setLista_parc(List<ParcelasCred> lista_parc) {
		this.lista_parc = lista_parc;
	}

	public boolean isEditando() {
		return isEditando;
	}

	public void setEditando(boolean isEditando) {
		this.isEditando = isEditando;
	}

	public String getNome_cliente() {
		return nome_cliente;
	}

	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}

	public List<Cliente> getLista_cliente() {
		return lista_cliente;
	}

	public void setLista_cliente(List<Cliente> lista_cliente) {
		this.lista_cliente = lista_cliente;
	}

	public Integer getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Integer cliente_id) {
		this.cliente_id = cliente_id;
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

	public Integer getFiltro_consulta_cliente() {
		return filtro_consulta_cliente;
	}

	public void setFiltro_consulta_cliente(Integer filtro_consulta_cliente) {
		this.filtro_consulta_cliente = filtro_consulta_cliente;
	}

	public Integer getOrdem_consulta_cliente() {
		return ordem_consulta_cliente;
	}

	public void setOrdem_consulta_cliente(Integer ordem_consulta_cliente) {
		this.ordem_consulta_cliente = ordem_consulta_cliente;
	}

	public String getLike_consulta_cliente() {
		return like_consulta_cliente;
	}

	public void setLike_consulta_cliente(String like_consulta_cliente) {
		this.like_consulta_cliente = like_consulta_cliente;
	}

	public Integer getAtivo_consulta_cliente() {
		return ativo_consulta_cliente;
	}

	public void setAtivo_consulta_cliente(Integer ativo_consulta_cliente) {
		this.ativo_consulta_cliente = ativo_consulta_cliente;
	}

	public ParcelasCred getParc() {
		return parc;
	}

	public void setParc(ParcelasCred parc) {
		this.parc = parc;
	}

	public Double getValor_parcelas() {
		return valor_parcelas;
	}

	public void setValor_parcelas(Double valor_parcelas) {
		this.valor_parcelas = valor_parcelas;
	}

	public boolean isEditandoParc() {
		return isEditandoParc;
	}

	public void setEditandoParc(boolean isEditandoParc) {
		this.isEditandoParc = isEditandoParc;
	}

	public ParcelasCred getParc_sel() {
		return parc_sel;
	}

	public void setParc_sel(ParcelasCred parc_sel) {
		this.parc_sel = parc_sel;
	}

	public Double getValor_pagar() {
		return valor_pagar;
	}

	public void setValor_pagar(Double valor_pagar) {
		this.valor_pagar = valor_pagar;
	}

	public Integer getCodigo_selecionado() {
		return codigo_selecionado;
	}

	public void setCodigo_selecionado(Integer codigo_selecionado) {
		this.codigo_selecionado = codigo_selecionado;
	}

	public Short getQtd_parcelas() {
		return qtd_parcelas;
	}

	public void setQtd_parcelas(Short qtd_parcelas) {
		this.qtd_parcelas = qtd_parcelas;
	}

	public Double getLimite() {
		return limite;
	}

	public void setLimite(Double limite) {
		this.limite = limite;
	}

}
