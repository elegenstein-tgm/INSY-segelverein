/**
 * 
 */

/**
 * @author Erik
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Person p = new Person("jdbc:postgresql://192.168.142.129/namegenerator?user=erik&password=abcc1233");
//		genPerson(p);
//		genTrainerSegler();
//		genBoot();
//		genSubBoot();
//		genMannschaft();
//		genRegatta();
//		genWettfahrt();
//		genBildet();
//		genZugewiesen();
		genNT();
		
	}
	/**
	 * requires inserted Mannschaft
	 */
	
	public static void genNT() {
		NimmtTeil nt = new NimmtTeil();
		nt.dos();
		new WriteInserts(new String[]{"mname","rname","rjahr","sportboot","startnr"}, nt.getnt()[0], "nimmt_teil");
	}
	
	public static void genZugewiesen(){
		Zugewiesen z = new Zugewiesen();
		z.dos();
		new WriteInserts(new String[]{"id","name"}, z.getz()[0], "zugewiesen");
	}
	public static void genBildet(){
		Bildet b = new Bildet();
		b.dos();
		new WriteInserts(new String[]{"key","name",},b.getb()[0],"bildet");
	}
	
	public static void genBoot() {
		Boot b = new Boot();
		b.genNames();
		String[][][] p =b.getb();
		new WriteInserts(new String[]{"name","personen","tiefgang"},p[0],"boot");
	}
	/**
	 * requires inserted Regatta
	 */
	public static void genWettfahrt(){
		Wettfahrt f = new Wettfahrt();
		f.dos();
		new WriteInserts(new String[]{"name","jahr","datum","laenge"}, f.getw()[0], "wettfahrt");
	}
	
	public static void genMannschaft() {
		Mannschaft m = new Mannschaft();
		new WriteInserts(new String[]{"name","aklasse", "key"},m.getm()[0],"mannschaft");
	}
	public static void genRegatta() {
		Regatta m = new Regatta();
		new WriteInserts(new String[]{"name","jahr", "land"},m.getr()[0],"regatta");
	}
	public static void genSubBoot() {
		Subboats b = new Subboats();
		String[][][] p =b.getb();

		new WriteInserts(new String[]{"id","segelflaeche"},p[1],"sportboot");
		new WriteInserts(new String[]{"id","bootsklasse"},p[0],"tourenboot");
	}
	
	public static void genTrainerSegler() {
		String[][][] person_subs = new String[2][1][10000];
		for(int i = 1 ; i <= 10000; i++){
			person_subs[0][0][i-1]= ""+i;
		}
		for(int i = 10001 ; i <= 20000; i++){
			person_subs[1][0][i-10001]= ""+i;
		}
		new WriteInserts(new String[]{"key"},person_subs[1],"segler");
		new WriteInserts(new String[]{"key"},person_subs[0],"trainer");
	}
	public static void genPerson(Person p) {
		String[][] names = new String[2][2000];
		names = p.getp();
		new WriteInserts(new String[]{"name","geburtsdatum"}, names, "person");
	}
}
