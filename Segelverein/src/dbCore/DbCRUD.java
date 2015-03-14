/**
 * 
 */
package dbCore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * UPDATE / INSERT ??? -> View entscheidet!
 * KA
 * Versioning Table -> versions 
 */

/**
 * @author Erik
 *
 */
public class DbCRUD {
	private String dsn;
	private Connection con;
	private static String JDBC = "org.postgresql.Driver";

	public DbCRUD() {
		this("jdbc:postgresql://localhost/segelverein?user=segel&password=verein");
	}

	public DbCRUD(String dsn) {
		this.dsn = dsn;
		try {
			Class.forName(JDBC);
			con = DriverManager.getConnection(dsn);
		} catch (ClassNotFoundException e) {
			System.err.println("Make sure that Postgre-Connectors are available");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String[][] selectALL(String tablename) {
		ArrayList<String[]> bettBlocka = new ArrayList<>();
		try {
			ResultSet rs = con.createStatement().executeQuery(
					"SELECT * FROM " + tablename);
			while (rs.next()) {
				String[] tmp = new String[rs.getMetaData().getColumnCount()];
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					tmp[i - 1] = rs.getString(i);
				}
				bettBlocka.add(tmp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[][] zzz = new String[bettBlocka.size()][];
		for (int i = 0; i < zzz.length; i++)
			zzz[i] = bettBlocka.get(i);

		return zzz;
	}
	
	public String[] getColNamesForSelect(String selectsql) {
		String[] names = null;
		try {
			ResultSet rs = con.createStatement().executeQuery(selectsql);
			ResultSetMetaData rsmd = rs.getMetaData();
			names = new String[rsmd.getColumnCount()];
			for(int i = 0; i < names.length; i++){
				names[i] = rsmd.getColumnName(i+1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;
	}
	
	
}
