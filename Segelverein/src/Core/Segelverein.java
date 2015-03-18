/**
 * 
 */
package Core;

import java.awt.EventQueue;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import dbCore.DbCRUD;
import GUI.MainFrame;

/**
 * @author Erik
 *
 */
public class Segelverein {

	public Segelverein(String host, int port, String dbname, String username ,String password){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					 MainFrame frame = new MainFrame();
					MainFrame frame = new MainFrame(new String[] { "boot",
							"bildet", "mannschaft", "nimmt_teil", "person","trainer","segler",
							"regatta", "sportboot", "tourenboot", "wettfahrt",
							"zugewiesen", "erzielt" }, new DbCRUD(host,port,dbname,username,password));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		if(args.length < 4 ){
			printHelp();
			return;
		}
		
		GnuParser gParser = new GnuParser();
		Options o = new Options();
		o.addOption(new Option("-h", true, "Host"));
		o.addOption(new Option("-p",true, "Port"));
		o.addOption("-d", true, "Databasename");
		o.addOption(new Option("-u", "Username"));
		o.addOption("-w", true, "Password");
		try {
			CommandLine cl= gParser.parse(o, args);
			new Segelverein(cl.getOptionValue("-h", "127.0.0.1"),Integer.parseInt(cl.getOptionValue("-p", "5432")), cl.getOptionValue("-d","segelverein"), cl.getOptionValue("-u", "segelverein"),cl.getOptionValue("-W", "segelverein"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printHelp(){
		String helptxt = "-h Databasehost IP\n-p Database port (Default: 5432)\n-d Databasename (Default: segelverein)\n-u Username (Default: segelverein)\n-w Password (Default: segelverein)";
		System.err.println(helptxt);
	}
}
