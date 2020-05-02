package util;

import java.sql.SQLException;

public class FuncoesBD {

	public static final String CAMINHO_PADRAO = "\\";
	private static final String ID_CAMINHO_PADRAO = "CAMINHO_PADRAO";

	public static String listarCaminhoPadrao() throws SQLException {
		var sql = new StringBuilder();
		sql.append("SELECT valor FROM configuracao ")
			.append(" WHERE ")
			.append(" nome_configuracao = ? ");
		
		try (var db = ConexaoBD.conn(); var ps = db.prepareStatement(sql.toString())) {
			ps.setString(1, ID_CAMINHO_PADRAO);
			
			try (var rs = ps.executeQuery()) {
				String caminho = null;
				
				if (rs.next()) {
					caminho = rs.getString(1);
				} else {
					caminho = salvarCaminho(CAMINHO_PADRAO);
				}
				
				return caminho;
			}
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}
	
	public static String salvarCaminho(String caminho) throws SQLException {
		var sql = new StringBuilder();
		sql.append("INSERT INTO configuracao ")
			.append(" (nome_configuracao, valor) ")
			.append(" VALUES(?, ?) ");
		
		try (var db = ConexaoBD.conn(); var ps = db.prepareStatement(sql.toString())) {
			ps.setString(1, ID_CAMINHO_PADRAO);
			ps.setString(2, caminho);
			ps.execute();
			
			return caminho;
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}
	
	public static void atualizaCaminho(String caminho) throws SQLException {
		var sql = new StringBuilder();
		sql.append("UPDATE configuracao ")
			.append(" SET valor = ? ")
			.append(" WHERE ")
			.append(" nome_configuracao = ? ");
		
		try (var db = ConexaoBD.conn(); var ps = db.prepareStatement(sql.toString())) {
			ps.setString(1, caminho);
			ps.setString(2, ID_CAMINHO_PADRAO);
			ps.execute();
			
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}
}
