package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import dbCore.DbCRUD;

public class MainFrame extends JFrame {
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JPanel contentPane;
	private JTable[] tables;
	public String[] tablenames;
	int activeTab = 0;
	public DbCRUD crud = new DbCRUD("jdbc:postgresql://192.168.5.3/segelverein?user=erik&password=abcc1233");
	public Versioner vers;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					 MainFrame frame = new MainFrame();
					MainFrame frame = new MainFrame(new String[] { "boot",
							"bildet", "mannschaft", "nimmt_teil", "person","trainer","segler",
							"regatta", "sportboot", "tourenboot", "wettfahrt",
							"zugewiesen", "erzielt" }, new DbCRUD("jdbc:postgresql://192.168.5.3/segelverein?user=erik&password=abcc1233"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame(String[] tmp,DbCRUD a) {
		crud = a;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 541);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		tablenames = tmp;
		tables = new JTable[tablenames.length];
		fillTabs();
		vers = new Versioner();
		Aktualize e = new Aktualize();
		e.start();

	}

	/**
	 * Create the frame.
	 */

	private void fillTabs() {
		for (int i = 0; i < tablenames.length; i++) {
			tables[i] = new JTable();
			tables[i].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane JSP = new JScrollPane(tables[i]);
			JPanel deleUpdate = new JPanel(new BorderLayout());

			
			JButton btnDeleteRow 	= new JButton("Delete row");
			JButton btnUpdate 		= new JButton("Force Update / Cancel");
			// Add Listeners
			btnDeleteRow.setActionCommand("Delete");
			btnDeleteRow.addActionListener(new BtnListener());
			btnUpdate.setActionCommand("Update");
			btnUpdate.addActionListener(new BtnListener());
			
			
			deleUpdate.add(btnUpdate,BorderLayout.WEST);
			deleUpdate.add(btnDeleteRow,BorderLayout.EAST);
			contentPane.add(tabbedPane, BorderLayout.NORTH);
			contentPane.add(deleUpdate, BorderLayout.SOUTH);
			tabbedPane.addTab(tablenames[i], null, JSP, null);
			tabbedPane.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					if (arg0.getSource() instanceof JTabbedPane) {
						/*System.out.println("Tab.No."
								+ ((JTabbedPane) arg0.getSource())
										.getSelectedIndex());
						 */
						activeTab = ((JTabbedPane) arg0.getSource())
								.getSelectedIndex();
					}

				}
			});
		}

	}

	public class MyTableListener implements TableModelListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.swing.event.TableModelListener#tableChanged(javax.swing.event
		 * .TableModelEvent)
		 */
		@Override
		public void tableChanged(TableModelEvent e) {
			int row, col;
			System.out.println("Table changed");
			col = e.getColumn();
			row = e.getFirstRow();
			
			if (row == (tables[tabbedPane.getSelectedIndex()].getRowCount() - 1)) {
				// Insert
				String zzz = "";
				System.out.print("insert into "+tablenames[tabbedPane.getSelectedIndex()]+" VALUES (");
				for(int i = 0; i < ((DefaultTableModel)tables[tabbedPane.getSelectedIndex()].getModel()).getColumnCount();i++)
					zzz+=((DefaultTableModel)tables[tabbedPane.getSelectedIndex()].getModel()).getValueAt(row, i);
				
				String s[] = new String[((DefaultTableModel)tables[tabbedPane.getSelectedIndex()].getModel()).getColumnCount()];
				for(int i = 0; i < ((DefaultTableModel)tables[tabbedPane.getSelectedIndex()].getModel()).getColumnCount();i++)
					s[i]= (String) ((DefaultTableModel)tables[tabbedPane.getSelectedIndex()].getModel()).getValueAt(row, i);
				if(!zzz.contains("null")){
					System.err.println("INSERT");
					crud.insert(tablenames[activeTab], s);
				}
			} else {
				String[][] s = new String[((DefaultTableModel)tables[tabbedPane.getSelectedIndex()].getModel()).getColumnCount()][2];
				for(int i  = 0; i < s.length; i++){
					s[i][0] = ((DefaultTableModel)tables[tabbedPane.getSelectedIndex()].getModel()).getColumnName(i);
					s[i][1] = (String) ((DefaultTableModel)tables[tabbedPane.getSelectedIndex()].getModel()).getValueAt(row, i);
//					System.err.println(s[i][0]+" "+s[i][1]);
				}
				crud.update(tablenames[activeTab], s, col);
			}
		}
	}

	public class Versioner {
		int[] versions = new int[tablenames.length];

		/**
		 * 
		 */
		public Versioner() {
			for (int i = 0; i < versions.length; i++) {
				versions[i] = -1;
				getCheckFill(i);
			}
		}
		public void getCheckFill(int table) {
			if (crud.getVersion(tablenames[table]) == versions[table]) {
				return;
			} else {
				updateDataModel(table);
				versions[table]= crud.getVersion(tablenames[table]);
			}
		}

		public void updateDataModel(int table) {
			tables[table].setModel(new DefaultTableModel(crud
					.selectALL(tablenames[table]),  crud.getColNamesForSelect("Select * from "
							+ tablenames[table])));
			((DefaultTableModel)tables[table].getModel()).addRow(new String[]{});
			tables[table].getModel().addTableModelListener(new MyTableListener());
		}
	}
	public class BtnListener implements ActionListener{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Delete")){
				String[][] kp = new String[((DefaultTableModel)tables[activeTab].getModel()).getColumnCount()][2];
				for(int i = 0; i < ((DefaultTableModel)tables[activeTab].getModel()).getColumnCount(); i++){
					kp[i][0] = tables[activeTab].getColumnName(i);
					kp[i][1] = (String) tables[activeTab].getValueAt(tables[activeTab].getSelectedRow(), i);
				}
				crud.deleteRow(tablenames[activeTab], kp);
				return;
			}
			if(e.getActionCommand().equals("Update")){
				vers.updateDataModel(activeTab);
				return;
			}
		}
		
	}
	
	public class Aktualize extends Thread{
		public boolean runns = true;
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while(runns){
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				vers.getCheckFill(activeTab);
			}
		}
	}
}
