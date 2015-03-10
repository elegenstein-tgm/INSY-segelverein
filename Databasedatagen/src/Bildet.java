import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Erik
 *
 */
public class Bildet {
	private ArrayList<String> segler_k 		= new ArrayList<>();
	private ArrayList<String> mannschaft_k  = new ArrayList<>();
	static final String JDBC = "org.postgresql.Driver";
	public void dos(){
		collectData("jdbc:postgresql://192.168.5.3/segelverein?user=erik&password=abcc1233");
		genseg();
	}
	private void collectData(String dsn){

		try {
			Class.forName(JDBC);
			Connection con = DriverManager.getConnection(dsn);

			java.sql.Statement st= con.createStatement();
			ResultSet rs = st.executeQuery("select name from mannschaft");
			while (rs.next()) {
				mannschaft_k.add("'"+rs.getString(1)+"'");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void genseg() {
		for(int i = 10001; i <= 20000; i++)
			segler_k.add(""+i);
	}
	public String[][][] getb() {
		String[][][] tmp = new String[1][2][10000];
		tmp[0][0]= segler_k.toArray(tmp[0][0]);
		tmp[0][1]= mannschaft_k.toArray(tmp[0][1]);
		return tmp;
	}
}
