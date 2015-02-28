import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Erik
 *
 */
public class Boot {
	public static final String SQL_BOAT_DEPTH_RAND = "trunc((random()+1) * 3)", SQL_PERSON_RAND = "trunc(random()  * 21)";
	private ArrayList<String> names = new ArrayList<>();
	public void genNames() {
		String[] basicnames = new String[]{"Sturmwind","Falke","Adler","Phoenix","Tiger","Amanda","Jessi","Olymp","Zeus","Helix","Alpha","Beta","Gamma","Delta","Fox","Peter","Rudolf","Dragon","Rabbit","Niko"};
		for(int j = 0; j < 20; j++)
			for(int i = 0; i < 1000;i++){
				names.add(basicnames[j]+i);
			}
	}
	public String[][][] getb(){
		String[][][] blub =new String[1][3][20000];
		blub[0][0] = names.toArray(blub[0][0]);
		for( int i = 0; i < 20000; i++){
			blub[0][1][i]= SQL_PERSON_RAND;
			blub[0][2][i]= SQL_BOAT_DEPTH_RAND;
		}
		/**
		for( int i = 0; i < 10000; i++){
			blub[1][1][i]= SQL_PERSON_RAND;
			blub[1][2][i]= SQL_BOAT_DEPTH_RAND;
		}
		**/
		return blub;
	}
}
