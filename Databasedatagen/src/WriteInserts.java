import java.io.BufferedWriter;
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
	public WriteInserts(String[] colnames, String[] colvals, String tablename) {
		String zzz = "Insert into \""+tablename+"\" (";
		for(int i = 0; i < colnames.length; i++){
			if(i != 0){
				zzz +=", " +colnames[i];
			}else{
				zzz += colnames[i];
			}
		}
		zzz+=") Values (";
		for(int i = 0; i < colvals.length; i++){
			if(i != 0){
				zzz+= ",(";
			}
			for(int j = 0; j < colnames.length; j++)
			{
				if(j != 0)
					zzz+=", "+"'"+colvals[i]+"'";
				else
					zzz+="'"+colvals[i]+"'";
				i++;
			}
			zzz+=")";
		}
		System.out.println(zzz);
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
