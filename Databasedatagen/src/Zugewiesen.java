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
 */
public class Zugewiesen {
	private ArrayList<String> mannn 	= new ArrayList<>();
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
				mannn.add("'"+rs.getString(1)+"'");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String[][][] getz() {
		String[][][] tmp = new String[1][2][10000];
		tmp[0][1] = mannn.toArray(tmp[0][1]);
		for(int i = 1; i <= 10000; i++){
			tmp[0][0][i-1]= ""+i;
		}
		return tmp;
	}
}
