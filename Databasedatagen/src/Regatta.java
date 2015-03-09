import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Erik
 *
 */
public class Regatta {
	public static final String[] name=new String[]{"Bodensee", "Zauchensee", "Wolfgangsee", "Mondsee","Woerthersee", "Donau", "Wakkensee", "Inn","Gardersee", "Erikson"};
	private ArrayList<String> jahr = new ArrayList<>();
	public static final String[] land = new String[]{"Oesterreich","Deutschland","Italien","Europa","WasWeissIch"};
	
	
	private void gen() {
		genJahr();
	}
	private void genJahr() {
		for(int i = 1915; i <= 2015;i++)
			jahr.add(""+i);
	}
	public String[][][] getr() {
		String[][][] regatta = new String[1][3][10000];
		for(int i = 0; i < 10000;)
			for(int j = 0; j < name.length && i < 10000; j++){
				regatta[0][0][i]=""+name[j]+i+"";
				i++;
			}
		for(int i = 0; i < 10000;)
			for(int j = 2015; j > 1014 && i < 10000; j--){
				regatta[0][1][i]=""+j+"";
				i++;
			}
		for(int i = 0; i < 10000;)
			for(int j = 0; j < land.length && i < 10000; j++){
				regatta[0][2][i]="'"+land[j]+"'";
				i++;
			}
		
		return regatta;
	}
}
