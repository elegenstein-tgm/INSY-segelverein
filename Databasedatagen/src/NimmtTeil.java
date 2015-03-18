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
public class NimmtTeil {
	private ArrayList<String> mname = new ArrayList<>(), 
			rname= new ArrayList<>(), 
			rjahr= new ArrayList<>(), 
			sportboot= new ArrayList<>();

	private String dsn ="jdbc:postgresql://192.168.5.3/segelverein?user=erik&password=abcc1233";

	public void dos(){
		collect();
	}
	private void  collect(){
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection(dsn);
			ResultSet rs = con.createStatement().executeQuery("select name from mannschaft LIMIT 2");
			while(rs.next()){
				mname.add(""+rs.getString(1)+"");
			}
			for(int i = 0; i <4999;i++){
				mname.add(mname.get(0));
				mname.add(mname.get(1));
			}
			rs = con.createStatement().executeQuery("select name, jahr from regatta");
			while(rs.next()){
				rname.add("'"+rs.getString(1)+"'");
				rjahr.add(""+rs.getInt(2));
				rname.add("'"+rs.getString(1)+"'");
				rjahr.add(""+rs.getInt(2));
			}
			rs = con.createStatement().executeQuery("select id from sportboot");
			while(rs.next()){
				sportboot.add(rs.getString(1));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public String[][][] getnt() {
		String[][][] tmp 	= new String[1][5][10000];
		tmp[0][0] = mname.toArray(tmp[0][0]);
		tmp[0][1] = rname.toArray(tmp[0][1]);
		tmp[0][2] = rjahr.toArray(tmp[0][2]);
		tmp[0][3] = sportboot.toArray(tmp[0][3]);
		for(int i = 0; i < 10000; i++)
				tmp[0][4][i] = ""+(i+1)%100;

		return tmp;
	}
}
