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

import javax.swing.JOptionPane;

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
					"SELECT * FROM " + tablename +" order by (1)");
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

	public int getVersion(String tablename){
		int i = -1;
		try {
			ResultSet rs = con.createStatement().executeQuery("Select version from version where name Like '"+tablename+"'");
			while(rs.next()){
				i = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	public void deleteRow(String table, String[][] where){
		String sql = "Delete from "+table+" Where ";
		for(int i = 0; i < where.length; i++){
			String LikeOrIs=" LIKE '";
			if(isNumber(where[i][1]))
				LikeOrIs ="=";

			sql += "\""+where[i][0]+"\"" + LikeOrIs +where[i][1];
			if(!isNumber(where[i][1]))
				sql += "'";

			if(i != where.length-1){
				sql += " AND ";
			}
		}
		try {
			con.setAutoCommit(false);
			con.createStatement().execute(sql);
			ResultSet rs = con.createStatement().executeQuery("select version from version where name like '"+table+"'");
			rs.next();
			int i = rs.getInt(1);
			con .createStatement().executeUpdate("Update version " +"set version = "+(i+1)+" where \"name\" like '"+table+"'");
			con.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.err.println("Update version where \"name\" like '"+table+"' set version = ");
			System.err.println(sql);
		}
	}
	/**
	 * 
	 */
	public void update(String table, String[][] colsAndVals,int changedcol) {
		String sql ="Update "+table+" SET "+colsAndVals[changedcol][0];
		if(isNumber(colsAndVals[changedcol][1]))
			sql += "="+colsAndVals[changedcol][1]+" ";
		else
			sql += " = '" + colsAndVals[changedcol][1]+"' ";

		sql += "Where ";
		for(int i = 0; i < colsAndVals.length; i++){
			if(i != changedcol){
				sql += colsAndVals[i][0];
				if(isNumber(colsAndVals[i][1])){
					sql += "=" + colsAndVals[i][1];
				}else{
					sql += " Like '"+colsAndVals[i][1]+"'";
				}
				sql+=" ";
				if(i != colsAndVals.length-1)
					sql += "And ";
			}
			
		}

		try {
			con.setAutoCommit(false);
//			System.err.println(sql);
			con.createStatement().execute(sql);
			
			ResultSet rs = con.createStatement().executeQuery("Select version from version where name like '"+table+"'");
//			System.err.println("Select version from version where name like '"+table+"'");
			rs.next();
			int vers = rs.getInt(1);
			con.createStatement().executeUpdate("Update version set version = "+ (vers+1)+" where name like '"+table+"'");
//			System.err.println("Update version set version = "+ (vers+1)+" where name like '"+table+"'");
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}


	}
	/**
	 * 
	 */
	public void insert(String table, String[] Vals) {
		String sql = "";
		sql += "insert into "+table+" VALUES (";
		for(int i = 0 ; i < Vals.length; i++){
			if(isNumber(Vals[i])){
				sql+=Vals[i];
			}else{
				sql+="'"+Vals[i]+"'";	
			}
			if(i != Vals.length-1){
				sql+=",";
			}
		}
		sql += ")";
		try {
			con.setAutoCommit(false);
			con.createStatement().execute(sql);
			ResultSet rs = con.createStatement().executeQuery("Select version from version where name like '"+table+"'");
			rs.next();
			int vers = rs.getInt(1);
			con.createStatement().executeUpdate("Update version set version = "+ (vers+1)+" where name like '"+table+"'");
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	public static boolean isNumber(String text){
		boolean parsable = true;
		try{
			Double.parseDouble(text);
		}catch(NumberFormatException e){
			parsable = false;
		}
		return parsable;
	}

}
