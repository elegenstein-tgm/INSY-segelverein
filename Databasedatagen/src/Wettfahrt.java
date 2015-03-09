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
public class Wettfahrt {
	private String dsn = "";
	Connection con ;
	static final String JDBC = "org.postgresql.Driver";
	private ArrayList<String> name 		= new ArrayList<>();
	private ArrayList<String> jahr 		= new ArrayList<>();
	private ArrayList<String> land 		= new ArrayList<>();
	private ArrayList<String> datum 	= new ArrayList<>();//generated
	private ArrayList<String> laenge 	= new ArrayList<>();//generated


	/**
	 * 
	 * @param dsn "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true"
	 */
	public void dos(){
		collectData("jdbc:postgresql://10.0.104.40/segelverein?user=erik&password=abcc1233");
		generate();
	}
	private void collectData(String dsn){
		this.dsn = dsn;
		try {
			Class.forName(JDBC);
			con = DriverManager.getConnection(dsn);

			java.sql.Statement st= con.createStatement();
			ResultSet rs = st.executeQuery("select * from regatta");
			while (rs.next()) {
				name.add(""+rs.getString(1)+"");
				jahr.add(rs.getString(2));
				land.add("'"+rs.getString(3)+"'");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void generate() {
		/**
		 * Datum :)
		 */
		for(int i = 0; i < jahr.size(); i++){
			for(int m = 1; m < 12; m++)
				for(int d = 1; d < 28; d++){
					String mm ="", dd="";
					if(m < 10)
						mm += "0";
					if(d < 10)
						dd+="0";
					mm+=m;
					dd+=d;
					datum.add("'"+jahr.get(i)+"-"+mm+"-"+dd+"'");
				}
			laenge.add(""+(i+1));
		}
	}
	public String[][][] getw() {
		String[][][] tmp = new String[1][4][10000];
		tmp[0][0]=name.toArray(tmp[0][0]);
		tmp[0][1]=jahr.toArray(tmp[0][1]);
		tmp[0][2]=datum.toArray(tmp[0][2]);
		tmp[0][3]=laenge.toArray(tmp[0][3]);
		return tmp;
	}

}

