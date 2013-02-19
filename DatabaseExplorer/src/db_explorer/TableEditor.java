package db_explorer;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TableEditor extends JFrame implements MouseListener {
	private static final long serialVersionUID = 1L;
	private String tableName;
	private String database;
	private MySQL_Connection con;
	private JTable table;
	
	public TableEditor(String database, String tableName, MySQL_Connection con) {
		this.database = database;
		this.tableName = tableName;
		this.con = con;
		initialize();
		populateTable();
	}
	
	private void initialize() {
		this.setSize(600,600);
		table = new JTable();
		table.addMouseListener(this);
		Container pane = this.getContentPane();
		SpringLayout layout = new SpringLayout();
		pane.setLayout(layout);
		pane.add(table);
		table.setBackground(Color.blue);
		table.setShowGrid(true);
		table.setModel((DefaultTableModel)table.getModel());
		layout.putConstraint(SpringLayout.SOUTH, table, -20, SpringLayout.SOUTH, pane);
		layout.putConstraint(SpringLayout.EAST, table, 0, SpringLayout.EAST, pane);
		layout.putConstraint(SpringLayout.NORTH, table, 0, SpringLayout.NORTH, pane);
		layout.putConstraint(SpringLayout.WEST, table, 0, SpringLayout.WEST, pane);
		
		layout.putConstraint(SpringLayout.EAST, pane, 1000, SpringLayout.WEST, pane);
		layout.putConstraint(SpringLayout.SOUTH, pane, 600, SpringLayout.NORTH, pane);
		
		
		this.pack();
		this.setVisible(true);
	}

	private void populateTable() {
		try {
			con.connect(database);
			ResultSet rs = con.executeQuery("SELECT * FROM " + tableName);
			int numColumns = rs.getMetaData().getColumnCount();
			DefaultTableModel tm = (DefaultTableModel)(table.getModel());
			while (rs.next()) {
				for (int i = 1; i < numColumns; i++) {
					tm.addColumn(rs.getArray(i));
				}
			}
			System.out.println("Here");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
