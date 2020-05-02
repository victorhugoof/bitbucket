package controller;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.hibernate.Session;

import model.Cliente;
import model.Despesa;
import model.Funcionario;
import model.ItemVenda;
import model.Produto;
import model.Venda;
import util.HibernateUtil;
import util.Utils;

@Named
@ViewScoped
public class RelatoriosController implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean balanco;
	private boolean clientes;
	private boolean funcionarios;
	private boolean gastos;
	private boolean produtos;
	private boolean vendas;
	private boolean filtrodata;

	private Cliente cliente;
	private Funcionario funcionario;
	private Produto produto;
	private Venda venda;

	private Date datainicio;
	private Date datafim;

	private int var;
	private boolean pordata;
	private boolean porgastos;
	private boolean tabelabalanco;
	private boolean maisvendidos;
	private boolean tabelaqtproduto;
	private boolean tabelaprodvend;
	private boolean tabelavendas;
	private boolean tabelaclientes;
	private boolean tabelafuncionario;
	private boolean tabelagastos;

	private String like_consulta;
	private int filtro_consulta;
	private int ordem_consulta = 1;
	private List<ItemVenda> lista_balanco;
	private List<Produto> lista_produto;
	private List<ItemVenda> lista_vendprod;
	private List<ItemVenda> lista_venda;
	private List<Cliente> lista_cliente;
	private List<Despesa> lista_despesas;

	private List<Funcionario> lista_funcionario;

	@PostConstruct
	public void initialize() {
		balanco = false;
		clientes = false;
		funcionarios = false;
		gastos = false;
		produtos = false;
		vendas = false;
		pordata = false;
		porgastos = false;
		filtrodata = false;
		tabelabalanco = false;
		tabelaqtproduto = false;
		tabelaprodvend = false;
		tabelavendas = false;
		tabelaclientes = false;
		tabelafuncionario = false;
		tabelagastos = false;
	}

	public void selcombo() {
		initialize();
		if (var == 1)
			balanco = true;
		if (var == 2)
			clientes = true;
		if (var == 3)
			funcionarios = true;
		if (var == 4)
			gastos = true;
		if (var == 5)
			produtos = true;
		if (var == 6)
			vendas = true;
	}

	public void checkdata() {
		if (pordata == true) {
			filtrodata = true;
		} else
			filtrodata = false;
	}
	
	@SuppressWarnings("unchecked")
	public void relatoriogastos() {
		tabelagastos = true;
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			String sql = "FROM Despesa a ";
			System.out.println(sql);
			lista_despesas = session.createQuery(sql).list();

		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao filtrar despesas", 3); // caso ocorra erro envia
																		// msg
		} finally {
			session.close(); // finaliza a sessão
		}
	}

	@SuppressWarnings("unchecked")
	public void relatorioproduto() {
		// MAIS VENDIDOS
		if (maisvendidos == true) {
			tabelaprodvend = true;
			tabelaqtproduto = false;
			String filtro = "";
			String ativo = "";
			String ordem = "";

			ordem = "GROUP BY a.codigo_venda.dt_emissao, a.quantidade";

			if (like_consulta != null) {
				if (like_consulta.isEmpty() == false) {
					switch (filtro_consulta) {
					case 1:
						if (filtrodata == true && datainicio != null && datafim != null) {
							filtro = "WHERE a.codigo_produto LIKE '%" + like_consulta + "%' "
									+ " AND a.codigo_venda.dt_emissao BETWEEN '" + datainicio + "' AND '" + datafim;
						} else {
							filtro = "WHERE a.codigo_produto LIKE '%" + like_consulta + "%'";
						}
						break;
					case 2:
						if (filtrodata == true && datainicio != null && datafim != null) {
							filtro = "WHERE a.codigo_produto.descricao LIKE '%" + like_consulta + "%' "
									+ " AND a.codigo_venda.dt_emissao BETWEEN '" + datainicio + "' AND '" + datafim;
						} else {
							filtro = "WHERE a.codigo_produto.descricao LIKE '%" + like_consulta + "%'";
						}
						break;
					case 3:
						if (filtrodata == true && datainicio != null && datafim != null) {
							filtro = "WHERE a.codigo_produto.cod_barras LIKE '%" + like_consulta + "%' "
									+ " AND a.codigo_venda.dt_emissao BETWEEN '" + datainicio + "' AND '" + datafim;
						} else {
							filtro = "WHERE a.codigo_produto.cod_barras LIKE '%" + like_consulta + "%'";
						}
						break;
					default:
						filtro = "";
						break;
					}
				}
			}
			Session session = HibernateUtil.getFabricaDeSessoes().openSession();

			try {
				String sql = "FROM ItemVenda a " + filtro + ativo + ordem;
				System.out.println(sql);
				lista_vendprod = session.createQuery(sql).list();

			} catch (Exception e) {
				Utils.addMsg("Erro", "Erro ao filtrar produtos", 3); // caso ocorra erro envia
																		// msg
			} finally {
				session.close(); // finaliza a sessão
			}
		}
		// POR QUANTIDADE
		if (maisvendidos == false) {
			tabelaqtproduto = true;
			tabelaprodvend = false;
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
		}
	}

	@SuppressWarnings("unchecked")
	public void relatoriovendas() {
		tabelavendas = true;

		tabelaprodvend = true;
		tabelaqtproduto = false;
		String filtro = "";
		String ativo = "";
		String ordem = "";
		String filtro2 = "";

		if (filtrodata == true && datainicio != null && datafim != null) {
			filtro2 = "WHERE a.codigo_venda.dt_emissao BETWEEN '" + datainicio + "' AND '" + datafim + "' ";
		}

		ordem = "GROUP BY a.codigo_venda.dt_emissao, a.quantidade";

		Session session = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			String sql = "FROM ItemVenda a " + filtro + filtro2 + ativo + ordem;
			System.out.println(sql);
			lista_vendprod = session.createQuery(sql).list();

		} catch (Exception e) {
			Utils.addMsg("Erro", "Erro ao filtrar produtos", 3); // caso ocorra erro envia
																	// msg
		} finally {
			session.close(); // finaliza a sessão
		}

	}

	public void editarfuncionario() {

	}

	@SuppressWarnings("unchecked")
	public void relatoriofuncionarios() {
		tabelafuncionario = true;
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
				default:
					filtro = "";
					break;
				}
			}
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

	public void infocrediario() {

	}

	@SuppressWarnings("unchecked")
	public void relatoriocliente() {
		tabelaclientes = true;
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
				default:
					filtro = "";
					break;
				}
			}
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

	@SuppressWarnings("unchecked")
	public void emitebalanco() {

		filtrodata = false;
		tabelabalanco = true;

		if (porgastos == false && pordata == true) {
			String filtro = "";
			String ativo = "";
			String ordem = "";

			if (like_consulta != null) {
				if (like_consulta.isEmpty() == false) {
					switch (filtro_consulta) {
					case 1:
						filtro = "WHERE v.data LIKE '%" + datainicio + "%'";
						break;
					default:
						filtro = "";
						break;
					}
				}
			}

			switch (ordem_consulta) {
			case 1:
				ordem = " ORDER BY v.data";
				break;
			default:
				ordem = "";
				break;
			}

			Session session = HibernateUtil.getFabricaDeSessoes().openSession();

			try {
				String sql = "FROM ItemVenda a " + filtro + ativo + ordem;
				System.out.println(sql);
				lista_balanco = session.createQuery(sql).list();

			} catch (Exception e) {
				Utils.addMsg("Erro", "Erro ao filtrar balanço", 3); // caso ocorra erro envia
																	// msg
			} finally {
				session.close(); // finaliza a sessão
			}
		}
		if (porgastos == true && pordata == false) {
			String ordem = "";

			switch (ordem_consulta) {
			case 1:
				ordem = " ORDER BY v.data, e.dt_entrada, d.data";
				break;
			default:
				ordem = "";
				break;
			}

			Session session = HibernateUtil.getFabricaDeSessoes().openSession();

			try {
				String sql = "FROM ItemVenda a  " + ordem + " GROUP BY v.data, e.data, d.data";
				System.out.println(sql);
				lista_balanco = session.createQuery(sql).list();

			} catch (Exception e) {
				Utils.addMsg("Erro", "Erro ao filtrar balanço", 3); // caso ocorra erro envia
																	// msg
			} finally {
				session.close(); // finaliza a sessão
			}
		}
	}

	public void desfazer() {
		initialize();
	}

	// GETTERS E SETTERS

	public boolean isBalanco() {
		return balanco;
	}

	public void setBalanco(boolean balanco) {
		this.balanco = balanco;
	}

	public boolean isClientes() {
		return clientes;
	}

	public void setClientes(boolean clientes) {
		this.clientes = clientes;
	}

	public boolean isFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(boolean funcionarios) {
		this.funcionarios = funcionarios;
	}

	public boolean isGastos() {
		return gastos;
	}

	public void setGastos(boolean gastos) {
		this.gastos = gastos;
	}

	public boolean isProdutos() {
		return produtos;
	}

	public void setProdutos(boolean produtos) {
		this.produtos = produtos;
	}

	public boolean isVendas() {
		return vendas;
	}

	public void setVendas(boolean vendas) {
		this.vendas = vendas;
	}

	public int getVar() {
		return var;
	}

	public void setVar(int var) {
		this.var = var;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public Date getDatafim() {
		return datafim;
	}

	public void setDatafim(Date datafim) {
		this.datafim = datafim;
	}

	public boolean isFiltrodata() {
		return filtrodata;
	}

	public void setFiltrodata(boolean filtrodata) {
		this.filtrodata = filtrodata;
	}

	public void setPordata(boolean pordata) {
		this.pordata = pordata;
	}

	public boolean isPorgastos() {
		return porgastos;
	}

	public void setPorgastos(boolean porgastos) {
		this.porgastos = porgastos;
	}

	public boolean isPordata() {
		return pordata;
	}

	public boolean isTabelabalanco() {
		return tabelabalanco;
	}

	public void setTabelabalanco(boolean tabelabalanco) {
		this.tabelabalanco = tabelabalanco;
	}

	public String getLike_consulta() {
		return like_consulta;
	}

	public void setLike_consulta(String like_consulta) {
		this.like_consulta = like_consulta;
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

	public List<ItemVenda> getLista_balanco() {
		return lista_balanco;
	}

	public void setLista_balanco(List<ItemVenda> lista_balanco) {
		this.lista_balanco = lista_balanco;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public boolean isMaisvendidos() {
		return maisvendidos;
	}

	public void setMaisvendidos(boolean maisvendidos) {
		this.maisvendidos = maisvendidos;
	}

	public boolean isTabelaqtproduto() {
		return tabelaqtproduto;
	}

	public void setTabelaqtproduto(boolean tabelaqtproduto) {
		this.tabelaqtproduto = tabelaqtproduto;
	}

	public List<Produto> getLista_produto() {
		return lista_produto;
	}

	public void setLista_produto(List<Produto> lista_produto) {
		this.lista_produto = lista_produto;
	}

	public boolean isTabelaprodvend() {
		return tabelaprodvend;
	}

	public void setTabelaprodvend(boolean tabelaprodvend) {
		this.tabelaprodvend = tabelaprodvend;
	}

	public List<ItemVenda> getLista_vendprod() {
		return lista_vendprod;
	}

	public void setLista_vendprod(List<ItemVenda> lista_vendprod) {
		this.lista_vendprod = lista_vendprod;
	}

	public List<ItemVenda> getLista_venda() {
		return lista_venda;
	}

	public void setLista_venda(List<ItemVenda> lista_venda) {
		this.lista_venda = lista_venda;
	}

	public boolean isTabelavendas() {
		return tabelavendas;
	}

	public void setTabelavendas(boolean tabelavendas) {
		this.tabelavendas = tabelavendas;
	}

	public boolean isTabelaclientes() {
		return tabelaclientes;
	}

	public void setTabelaclientes(boolean tabelaclientes) {
		this.tabelaclientes = tabelaclientes;
	}

	public List<Cliente> getLista_cliente() {
		return lista_cliente;
	}

	public void setLista_cliente(List<Cliente> lista_cliente) {
		this.lista_cliente = lista_cliente;
	}

	public boolean isTabelafuncionario() {
		return tabelafuncionario;
	}

	public void setTabelafuncionario(boolean tabelafuncionario) {
		this.tabelafuncionario = tabelafuncionario;
	}

	public List<Funcionario> getLista_funcionario() {
		return lista_funcionario;
	}

	public void setLista_funcionario(List<Funcionario> lista_funcionario) {
		this.lista_funcionario = lista_funcionario;
	}

	public boolean isTabelagastos() {
		return tabelagastos;
	}

	public void setTabelagastos(boolean tabelagastos) {
		this.tabelagastos = tabelagastos;
	}

	public List<Despesa> getLista_despesas() {
		return lista_despesas;
	}

	public void setLista_despesas(List<Despesa> lista_despesas) {
		this.lista_despesas = lista_despesas;
	}

}
