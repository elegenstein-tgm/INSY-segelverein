import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

/**
 * 
 */

/**
 * @author Erik
 *
 */
public class Person {
	private ArrayList<String> vornamen  = new ArrayList<>();//200 names, 100 male-names + 100 female-names
	private ArrayList<String> nachnamen = new ArrayList<>();//100 surename 
	private ArrayList<String> names		= new ArrayList<>();
	private String dsn;
	private Connection con;

	static final String JDBC = "org.postgresql.Driver";
	/**
	 * 
	 * @param dsn "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true"
	 */
	public Person(String dsn) {
		this.dsn = dsn;
		try {
			Class.forName(JDBC);
			con = DriverManager.getConnection(dsn);
			collect();
			genNames();
			put();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void collect() {
		try {
			java.sql.Statement st= con.createStatement();
			ResultSet rs = st.executeQuery("select male, female from namegenerator");
			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					vornamen.add(rs.getString(i));
				}
			}
			rs = st.executeQuery("select surname from namegenerator");
			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					nachnamen.add(rs.getString(i));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void genNames(){
		for(int i = 0; i < nachnamen.size(); i++)
			for(int j = 0; j < vornamen.size(); j++)
				names.add(""+vornamen.get(j)+" "+nachnamen.get(i));
	}
	public void put() {
		for(int i = 0; i< names.size(); i++)
			System.out.println(names.get(i));
	}
	public String[] getp() {
		String[] a = new String[20000];
		a = names.toArray(a);

		return a;
	}

}
