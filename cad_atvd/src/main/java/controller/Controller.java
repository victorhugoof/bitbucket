package controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.primefaces.component.texteditor.TextEditor;
import model.Atividade;
import model.Tipo;
import util.HibernateUtil;

@ManagedBean
@ViewScoped
public class Controller implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Atividade atividade;
	private List<Atividade> listaAtv;
	private List<Tipo> listaTipos;
	private TextEditor textDesc;

	

	// MÉTODO QUE IRÁ RODAR NO POSTCONSTRUCT *RODA AUTOMATICAMENTE NO INICIO*
	@PostConstruct
	private void inicialize() {
		atividade = new Atividade();
		listarAtividades();
		listarTipos();
	}
	
	
	// REABRIR UMA ATIVIDADE
	public void reabrir(ActionEvent evt) {
		Atividade atv = (Atividade)evt.getComponent().getAttributes().get("atv_reabre");
		// abre uma sessão no banco
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		
		try {
			atv.setStatus(false); // seta a atividade como status false (em aberto)
			
			// atualiza a atividade no banco de dados definindo seu status com false
			t = sessao.beginTransaction();
			sessao.createQuery("UPDATE Atividade a SET status = 'false' WHERE id = '"+ atv.getId() +"'").executeUpdate();
			t.commit();
			
			
			listarAtividades(); // atualiza a lista com as atividades e seus dados
			addMensagem("Sucesso", "Atividade reaberta com sucesso",FacesMessage.SEVERITY_INFO); // envia a mensagem de sucesso
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if(t != null)
				t.rollback();
			
			atv.setStatus(true); // seta a atividade como status true (concluida)
			addMensagem("Erro", "Erro ao reabrir atividade", FacesMessage.SEVERITY_ERROR); // envia a mensagem de erro
		} finally {
			// finaliza a sessão
			sessao.close();
		}
	}
	
	
	// METODO PARA LIMPAR OS CAMPOS DO CADASTRO DE ATIVIDADES
	public void cancelar() {
		atividade = new Atividade(); // retira a atividade selecionada e limpa os campos
	}
	
	
	// SELECIONA A ATIVIDADE DA TABELA PARA EDIÇÃO
	public void seleciona(ActionEvent evt) {
		atividade = (Atividade)evt.getComponent().getAttributes().get("atv_edita"); // seleciona a atividade da tabela para edicao
	}
	
	
	// EXCLUIR UMA ATIVIDADE
	public void excluir(ActionEvent evt) {
		Atividade atv = (Atividade)evt.getComponent().getAttributes().get("atv_exclui"); // seleciona a atividade da tabela que será excluida
		
		// abre uma sessão no banco
        Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        Transaction t = null;
        
        if(atv.getTipo().getFlg_remove() == 'S') { // se a flag flg_remove do tipo da atividade estiver como S ele efetua a exclusão (para tipos de atividades que permitem exclusão) 
	        try {
	        	
	        	// efetua a exclusão da atividade no banco
				t = sessao.beginTransaction();
				sessao.delete(atv);
				t.commit();
				
				cancelar(); // limpa os campos 
				listarAtividades(); // atualiza a lista das atividades
				addMensagem("Excluida", "Atividade excluida com sucesso", FacesMessage.SEVERITY_INFO); // envia msg de sucesso
			} catch (Exception e) {
				// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
				if(t != null)
					t.rollback();
				
				addMensagem("Erro", "Erro ao excluir atividade", FacesMessage.SEVERITY_ERROR); // envia msg de erro
			} finally {
				// finaliza a sessao
				sessao.close();
			}
        } else { // se a flag flg_remove do tipo da atividade estiver diferente de S ele retorna uma mensagem de erro 
        	addMensagem("Erro", "Você não pode excluir este tipo de atividade", FacesMessage.SEVERITY_ERROR);
        }
	}
	
	
	// DEFINE A ATIVIDADE COM CONCLUÍDA
	public void concluir(ActionEvent evt) {
		Atividade atv = (Atividade)evt.getComponent().getAttributes().get("atv_conclui"); // chama a atividade da tabela que será concluida
		
		// abre uma sessão no banco
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
        Transaction t = null;
        
        int qtdChars = atv.getTipo().getQnt_carac(); //busca a quantidade de chars minimos para descricao no tipo da atividade
		String desc = Jsoup.parse(atv.getDescricao()).text(); //retira as tags html da descricao para comparar a quantidade de chars
		
		
		if(qtdChars > desc.length()) { // caso a qntd de chars da descricao seja maiores ou iguais à qntd definida no banco ele permite salvar no banco
			addMensagem("Erro", "Para este tipo de atividade descrição precisa ter no mínimo "+qtdChars+" caracteres.", FacesMessage.SEVERITY_ERROR);
        } else { // se a atividade estiver em aberto é feito a conclusao da atividade
	        atv.setStatus(true); // seta a atividade como concluida
		     try {
		    	 	// executa a atualizacao no banco de dados
					t = sessao.beginTransaction();
					sessao.update(atv);
					t.commit();
					
					
					cancelar(); // limpa os campos 
					listarAtividades(); // atualiza a lista as atividades
					addMensagem("Concluída", "Atividade concluida com sucesso", FacesMessage.SEVERITY_INFO); // envia msg de sucesso
				} catch (Exception e) {
					// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
					if(t != null)
						t.rollback();
					
					addMensagem("Erro", "Erro ao concluir atividade", FacesMessage.SEVERITY_ERROR); // envia msg de erro
				} finally {
					// fecha a sessão do banco
					sessao.close();
				}
        }
     
	}
	
	
	// TRATA AS REGRAS DA ATIVIDADE E MANDA PARA O METODO SALVAR BANCO
	public void adicionar() {
		// busca data e hora do sistema
		Calendar c = Calendar.getInstance();
		int horaAtual = c.get(Calendar.HOUR_OF_DAY);
		int diaAtual = c.get(Calendar.DAY_OF_WEEK);
		
		int qtdChars = atividade.getTipo().getQnt_carac(); //busca a quantidade de chars minimos para descricao no tipo da atividade
		String desc = Jsoup.parse(textDesc.getValue().toString()).text(); //retira as tags html da descricao para comparar a quantidade de chars
		
		if(qtdChars > desc.length() && atividade.getStatus()) { // caso a qntd de chars da descricao seja maiores ou iguais à qntd definida no banco ele permite salvar no banco
			addMensagem("Erro", "Para este tipo de atividade descrição precisa ter no mínimo "+qtdChars+" caracteres.", FacesMessage.SEVERITY_ERROR);
        } else { // caso a qntd de chars da descricao seja maiores ou iguais à qntd definida no banco ele permite salvar no banco	
			if(atividade.getTipo().getFlg_sexta() == 'N') { // caso o tipo de atividade não posso ser criado na sexta ele verifica se é sexta-feira
				
				if(horaAtual > 13 && diaAtual == Calendar.FRIDAY) { // verifica se é sexta-feira depois das 13 horas
					// se for, retornar msg de erro
					addMensagem("Erro", "Você não pode adicionar este tipo de atividade na sexta-feira depois das 13 horas", FacesMessage.SEVERITY_ERROR);
				} else { // caso não seja sexta-feira dps das 13hrs ele chama a funcao para salvar no banco
					salvaBanco(atividade);
				}
				
			} else { // se a atividade pode ser criada na sexta-feira ele salva no banco
				salvaBanco(atividade);
			}
        }
		
		listarAtividades(); // atualiza a lista de atividades apos o processo

	}
	
	
	// SALVA A ATIVIDADE NO BANCO 
	private void salvaBanco(Atividade a) {
		// abre a sessão no banco
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		
		try {
			// salva a atividade no banco de dados
			t = sessao.beginTransaction();
			sessao.merge(a);
			t.commit();
			
			cancelar(); // limpa os campos
			addMensagem("Sucesso", "Atividade salva com sucesso",FacesMessage.SEVERITY_INFO); // envia msg de sucesso
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if(t != null)
				t.rollback();
			
			addMensagem("Erro", "Erro ao salvar atividade", FacesMessage.SEVERITY_ERROR); // envia msg de erro
		} finally {
			// finaliza a sessao
			sessao.close();
		}
	}
	
	
	// ADICIONA NA LIST TODAS AS ATIVIDADES DO BANCO
	@SuppressWarnings("unchecked")
	private void listarAtividades() {
		// abre a sessão no banco
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
		
		try {
			listaAtv = session.createQuery("FROM Atividade a ORDER BY a.id").list(); // busca as atividades salvas no banco, ordenadas pela id
		}catch (Exception e) {
			addMensagem("Erro", "Erro ao listar atividades", FacesMessage.SEVERITY_ERROR); // caso ocorra erro envia msg
		}finally {
			session.close(); // finaliza a sessão
		}
	}
	
	
	// BUSCA TODOS OS TIPOS DE ATIVIDADES E ADICIONA NA LIST
	@SuppressWarnings("unchecked")
	private void listarTipos() {
		// abre a sessao no banco
		Session session = HibernateUtil.getFabricaDeSessoes().openSession();
				
		try {
			listaTipos = session.createQuery("FROM Tipo").list();  // lista os tipos de atividades salvos no banco
			
			if(listaTipos.isEmpty()) { // caso não retorne nenhum valor ele gera os tipos de atividades e lista novamente
				geraTipos();
				listaTipos = session.createQuery("FROM Tipo").list();
			}
		}catch (Exception e) {
			addMensagem("Erro", "Erro ao listar tipos", FacesMessage.SEVERITY_ERROR); // se ocorrer erro envia uma msg
		}finally {
			session.close(); // finaliza a sessao
		}
	}

	
	// CASO NÃO EXISTA TIPOS DE ATIVIDADES REGISTRADOS ESTE METODO REGISTRA
	public void geraTipos() {
		// abre a sessao no banco
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		
		try {
			// define os tipos de atividades
			Tipo dsv = new Tipo("Desenvolvimento", 'S', 1, 'S');
			Tipo manu = new Tipo("Manutencao", 'S', 1, 'S');
			Tipo urg = new Tipo("Manutencao Urgente", 'N', 50, 'N');
			Tipo atend = new Tipo("Atendimento", 'S', 50, 'S');
		
			// salva no banco
			t = sessao.beginTransaction();
			sessao.saveOrUpdate(dsv);
			sessao.saveOrUpdate(manu);
			sessao.saveOrUpdate(urg);
			sessao.saveOrUpdate(atend);
			t.commit();
			
		} catch (Exception e) {
			// caso ocorra erro é feito um rollback, volta atras todos os comandos no banco
			if(t != null)
				t.rollback();
			
		} finally {
			sessao.close(); // finaliza a sessao
		}
	}
	
	
	// METODO PARA ENVIAR MENSAGENS DE NOTIFICAÇÃO NA PAGINA
	public void addMensagem(String titulo, String descricao, Severity natureza) {
		FacesMessage msg = new FacesMessage(natureza, titulo, descricao);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}


	public Atividade getAtividade() {
		return atividade;
	}


	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}


	public List<Atividade> getListaAtv() {
		return listaAtv;
	}


	public void setListaAtv(List<Atividade> listaAtv) {
		this.listaAtv = listaAtv;
	}


	public List<Tipo> getListaTipos() {
		return listaTipos;
	}


	public void setListaTipos(List<Tipo> listaTipos) {
		this.listaTipos = listaTipos;
	}


	public TextEditor getTextDesc() {
		return textDesc;
	}


	public void setTextDesc(TextEditor textDesc) {
		this.textDesc = textDesc;
	}

}
