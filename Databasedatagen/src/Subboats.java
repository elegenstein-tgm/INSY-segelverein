import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Erik
 *
 */
public class Subboats {
	public static final String SQL_RAND_BOAT_CLASS ="(select random_bootKlasse( 2 ))";
	public static final String SQL_RAND_SEGL_SIZE ="(random()+1) * 7";

	public String[][][] getb(){
		String[][][] blub =new String[2][2][10000];

		for( int i = 0; i < 20000; i++){
			if(i <10000){
				blub[0][0][i] 	= ""+(i+1);
				blub[0][1][i]	= SQL_RAND_BOAT_CLASS;
			}else{
				blub[1][0][i%10000] 	= ""+(i+1);
				blub[1][1][i%10000]		= SQL_RAND_SEGL_SIZE;
			}
		}
		return blub;
	}
	
}
