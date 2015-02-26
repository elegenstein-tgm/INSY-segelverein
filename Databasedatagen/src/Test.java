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
		Person p = new Person("jdbc:postgresql://192.168.142.129/namegenerator?user=erik&password=abcc1233");
		String[] names = p.getp();
		new WriteInserts(new String[]{"name"}, names, "person");
	}

}
