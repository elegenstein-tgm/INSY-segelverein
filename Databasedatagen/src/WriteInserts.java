import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 */

/**
 * @author Erik
 *
 */
public class WriteInserts {
	public WriteInserts(String[] colnames, String[][] colvals, String tablename) {
		String zzz = "INSERT INTO "+tablename+" ( ";
		for(int i = 0 ; i < colnames.length; i ++){
			if(i == 0)
				zzz += colnames[i];
			else
				zzz += ", " + colnames[i];
		}
		zzz+=") VALUES ";
		System.err.println(colvals[0].length);
		for(int i = 0; i < colvals[0].length; i ++){
			if(i != 0)
				zzz += ","; 
			zzz += "(";
			for( int u = 0; u < colnames.length;u ++){
				if(u == 0)
					zzz += "'"+colvals[u][i]+"'";
				else
					zzz += ", " + colvals[u][i]+"";/// Need some ' but in this case its a function call! so 1 would be not productive
			}
			zzz += ")";
		}
		String zzz1 = zzz.substring(zzz.length()/2);
		System.out.println(zzz);
		System.out.println(zzz1);
		File abc = new File("insert_"+tablename+".sql");
		try {
			if(!abc.exists())
			{
				abc.createNewFile();
			}
			FileWriter buff = new FileWriter(abc);
			buff.write(zzz);
			buff.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 	}

}
