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
public class Erzielt {
	private ArrayList<String> mname  = new ArrayList<>();
	private ArrayList<String> wname  = new ArrayList<>();
	private ArrayList<String> wjahr  = new ArrayList<>();
	private ArrayList<String> wdatum = new ArrayList<>();
	private ArrayList<String> punkte = new ArrayList<>();

	private String dsn ="jdbc:postgresql://192.168.5.3/segelverein?user=erik&password=abcc1233";

	public void dos(){
		collect();
	}
	private void  collect(){
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(dsn);
			ResultSet rs = con.createStatement().executeQuery("select name from mannschaft");
			while(rs.next()){
				mname.add(""+rs.getString(1)+"");
			}
			rs = con.createStatement().executeQuery("select name, jahr, datum from wettfahrt");
			while(rs.next()){
				wname.add("'"+rs.getString(1)+"'");
				wjahr.add(""+rs.getString(2)+"");
				wdatum.add("'"+rs.getString(3)+"'");
			}
			
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Fuck Tha Coding ParaDIGm
		for(int j = 0; j < (wjahr.size()/100);j++)
			for(int i = 0; i < 100; i++)
				punkte.add(""+(i+1));
		
	}
	public String[][][] gete(){
		String[][][] tmp = new String[1][5][10000];
		tmp[0][0] = mname.toArray(tmp[0][0]);
		tmp[0][1] = wname.toArray(tmp[0][1]);
		tmp[0][2] = wjahr.toArray(tmp[0][2]);
		tmp[0][3] = wdatum.toArray(tmp[0][3]);
		tmp[0][4] = punkte.toArray(tmp[0][4]);
				
		return tmp;
	}

}
