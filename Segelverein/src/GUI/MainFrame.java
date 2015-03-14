package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	public String[] tablenames ; 
	private JTable table;
	int activeTab = 0;
	private JLabel lblNewLabel;
	
	public JTable[] getTables() {
		return tables;
	}

	public void setTables(JTable[] tables) {
		this.tables = tables;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//					MainFrame frame = new MainFrame();
					MainFrame frame = new MainFrame(new String[]{"boot","bildet","mannschaft","nimmt_teil","person","regatta","sportboot","tourenboot","wettfahrt","zugewiesen","erzielt"});
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame(String[] tmp){
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
	}
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane_1, BorderLayout.NORTH);

		table = new JTable();/*
				table.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentShown(ComponentEvent arg0) {
						System.out.println("tab1");
					}
				});*/
		table.setToolTipText("boot");
		tabbedPane_1.addTab("1", null, table, null);
		table.setModel(new DefaultTableModel(
				new Object[][] {
						{"bring", null, null, null, null, null, null, null, null},
						{null, null, null, null, "sucks", null, null, null, null},
				},
				new String[] {
						"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
				}
				));
		System.out.println(table.getModel().getColumnName(9));
		lblNewLabel = new JLabel("New label");
		tabbedPane_1.addTab("New tab", null, lblNewLabel, null);

	}

	
	public void updateActualTab(){
		
	}
	
	private void fillTabs(){
		DbCRUD crud = new DbCRUD("jdbc:postgresql://192.168.5.3/segelverein?user=erik&password=abcc1233");
		for(int i = 0; i < tablenames.length; i++){
			String[] colnames = crud.getColNamesForSelect("Select * from "+tablenames[i]);
			tables[i] = new JTable();
			tables[i].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tables[i].setModel(new DefaultTableModel(crud.selectALL(tablenames[i]),colnames));
			DefaultTableModel model = (DefaultTableModel) tables[i].getModel();
			model.addRow(new String[]{});
			model.addTableModelListener(new MyTableListener());
			JScrollPane JSP = new JScrollPane(tables[i]);
			JPanel deleUpdate = new JPanel(new BorderLayout());
			
			JButton btnSaveChanges = new JButton("Delete row");
			JButton btnUpdate = new JButton("Force Update");
			// Add Listeners 
			
			deleUpdate.add(btnUpdate, BorderLayout.WEST);
			deleUpdate.add(btnSaveChanges, BorderLayout.EAST);
			contentPane.add(tabbedPane, BorderLayout.NORTH);
			contentPane.add(deleUpdate, BorderLayout.SOUTH);
			tabbedPane.addTab(tablenames[i], null, JSP, null);
			tabbedPane.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					if (arg0.getSource() instanceof JTabbedPane) {
						System.out.println("Tab.No." + ((JTabbedPane)arg0.getSource()).getSelectedIndex());
						activeTab = ((JTabbedPane)arg0.getSource()).getSelectedIndex();
					}

				}
			});
		}

	}
	public class MyTableListener implements TableModelListener{
		/* (non-Javadoc)
		 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
		 */
		@Override
		public void tableChanged(TableModelEvent e) {
			int row, col ;
			col =e.getColumn();
			row = e.getFirstRow();
			if(row == (tables[tabbedPane.getSelectedIndex()].getRowCount()-1)){
				//Insert
			}else{
				//Update
			}
		}
	}


}
