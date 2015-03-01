import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Erik
 *
 */
public class Mannschaft {
	public static final String[] ALTERS_KLASSEN = new String[]{"U14","U16","U18","U21","U23","U30","U35","U40","OPN","SEN"};
	private static final String TEAMNAME_PEFIX = "NewTeam ";
	private ArrayList<String> names = new ArrayList<>();
	private ArrayList<String> alterk = new ArrayList<>();
	public Mannschaft() {
		genNames();
		genAK();

	}

	private void genNames(){
		for(int i=0,j = (int)'A'; (j <= (int)'Z') && i < 10000; j ++)
			for(int k = (int)'A'; (k <= (int)'Z') && i < 10000; k ++)
				for(int l = (int)'A' ; l <= (int)'Z'&& i < 10000; l ++){
					names.add(TEAMNAME_PEFIX+((char)j)+((char)k)+((char)l));
					i++;
				}
	}
	private void genAK(){
		for(int i = 0; i < 10000; )
			for(int j = 0; j < ALTERS_KLASSEN.length; j++){
				alterk.add("'"+ALTERS_KLASSEN[j]+"'");
				i++;
			}
	}
	public String[][][] getm(){
		String[][][] mannschaft = new String[1][3][10000];
		mannschaft[0][0] = names.toArray(mannschaft[0][0]);
		mannschaft[0][1] = alterk.toArray(mannschaft[0][1]);
		for(int i = 1; i<= 10000; i++){
			mannschaft[0][2][i-1]=""+i;
		}
		return mannschaft;
	}

}
