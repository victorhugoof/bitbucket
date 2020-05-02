package util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoBD {
	
	private ConexaoBD() {
		super();
	}
	
	public static Connection conn() {
		Connection conn = null;
		try {
			File f = new File("pdi.sqlite");
			if(f.exists()) {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:pdi.sqlite");
			}
			
		} catch (Exception e) {
			Funcoes.exibeMsgErro(e);
		}
		
		return conn;
	}
}
