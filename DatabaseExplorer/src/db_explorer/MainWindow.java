package db_explorer;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeExpansionListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;

public class MainWindow extends JFrame implements ActionListener, ComponentListener, TreeSelectionListener, TreeExpansionListener, MouseListener {
	private static final long serialVersionUID = 2512076634162148666L;
	private JMenuBar menuBar;
	private JTree conTree;
	private JScrollPane treeView;
	private JList list;
	private JSplitPane splitPane;
	private DefaultMutableTreeNode treeRootMySQL = new DefaultMutableTreeNode("MySQL Connections");
	private DefaultMutableTreeNode selectedNode;
	private MySQL_Connection[] mySQL_connections;
	private int treeIndex = 0;

	public MainWindow() {
		this.setSize(900, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addComponentListener(this);
		createMenu();
		createComponents();
		this.setVisible(true);
	}

	public MySQL_Connection findMySQLConnection(String connectionName) {
		if (mySQL_connections != null) {
			for (int i = 0; i < mySQL_connections.length; i++) {
				if (mySQL_connections[i] == null){
					return null;
				}
				else if (mySQL_connections[i].getName().equals(connectionName)) {
					return mySQL_connections[i];
				}
			}
			return null;
		}
		else {
			return null;
		}
	}

	public void  newMySQLConnection(MySQL_Connection connection) {
		DefaultMutableTreeNode con = new DefaultMutableTreeNode(connection);
		DefaultTreeModel m = (DefaultTreeModel)conTree.getModel();
		m.insertNodeInto(con, treeRootMySQL, treeIndex);
		treeIndex++;
		if (mySQL_connections == null || mySQL_connections.length == 0) {
			mySQL_connections = new MySQL_Connection[5]; //Start with an array of 5 connections (can be made larger)
			mySQL_connections[0] = connection;
		}
		else {
			int index = 0;
			while (index < mySQL_connections.length && mySQL_connections[index] != null) {
				index++;
			}
			if (index == mySQL_connections.length) {
				MySQL_Connection[] tmp = new MySQL_Connection[mySQL_connections.length + 5];
				for (int i = 0; i < mySQL_connections.length; i++) {
					tmp[i] = mySQL_connections[i];
				}
				mySQL_connections = tmp;
			}
			mySQL_connections[index] = connection;
		}
	}

	private void createComponents() {
		//get the container and create a layout
		Container pane = this.getContentPane();
		SpringLayout layout = new SpringLayout();
		pane.setLayout(layout);
		//create components
		conTree = new JTree(treeRootMySQL);
		conTree.addTreeSelectionListener(this);
		conTree.addTreeExpansionListener(this);
		treeView = new JScrollPane(conTree);
		conTree.addMouseListener(this);
		list = new JList(new DefaultListModel());
		list.setBackground(Color.gray);
		list.addMouseListener(this);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(treeView);
		splitPane.setRightComponent(new JScrollPane(list));
		treeView.setMinimumSize(new Dimension(150, 50));
		this.setResizable(false);
		//add components to pane
		pane.add(splitPane);
		putConstraints(layout);
	}

	private void putConstraints(SpringLayout layout) {
		Container pane = this.getContentPane();
		//define variables for easier access
		String north = SpringLayout.NORTH;
		String south = SpringLayout.SOUTH;
		String east = SpringLayout.EAST;
		String west = SpringLayout.WEST;
		//add constraints to items
		//pane
		layout.putConstraint(east, pane, this.getWidth(), west, pane);
		layout.putConstraint(north, pane, this.getHeight(), south, pane);
		//SplitPane
		layout.putConstraint(west, splitPane, 0, west, pane);
		layout.putConstraint(north, splitPane, 0, north, pane);
		layout.putConstraint(south, splitPane, 0, south, pane);
		layout.putConstraint(east, splitPane, 0, east, pane);
	}

	private void createMenu() {
		menuBar = new JMenuBar();
		JMenu connection = new JMenu("Connection");
		menuBar.add(connection);

		//Create menu items
		JMenuItem newMySQL = new JMenuItem("New MySQL Connection");
		JMenuItem newSQLite = new JMenuItem("New SQLite Connection");

		//Set menu item names
		newMySQL.setName("newMySQL");
		newSQLite.setName("newSQLite");

		//Add action listeners to menu items
		newMySQL.addActionListener(this);
		newSQLite.addActionListener(this);

		//Add menu items to menu
		connection.add(newMySQL);
		connection.add(newSQLite);

		this.setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() instanceof JMenuItem) {
			JMenuItem item = (JMenuItem)event.getSource();
			if (item.getName().equals("newMySQL")) {
				@SuppressWarnings("unused")
				NewConnection con = new NewConnection(Connection_Type.MySQL, this);
			}
			else if (item.getName().equals("newSQLite")) {
				//TODO: create a dialog for a new SQLite connection
			}
		}
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MainWindow m = new MainWindow();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)conTree.getSelectionPath().getLastPathComponent();
		selectedNode = node;
		if (selectedNode.getUserObject() instanceof String) {
			if (!selectedNode.getUserObject().equals("MySQL Connections")) {
				DefaultListModel lm = (DefaultListModel)list.getModel();
				lm.removeAllElements();
				for (int i = 0; i < selectedNode.getChildCount(); i++) {
					lm.addElement(selectedNode.getChildAt(i));
				}
			}
		}
	}

	@Override
	public void treeCollapsed(TreeExpansionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void treeExpanded(TreeExpansionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount() == 2) {
			if (e.getSource() instanceof JList) {
				new TableEditor(((DefaultMutableTreeNode)(((DefaultMutableTreeNode)list.getSelectedValue()).getParent().getParent())).getUserObject().toString(), 
						list.getSelectedValue().toString(), ((Table)((DefaultMutableTreeNode)list.getSelectedValue()).getUserObject()).getConnection());
			}
			else {
				if (selectedNode.getUserObject() instanceof MySQL_Connection) {
					if (!((MySQL_Connection)selectedNode.getUserObject()).dbsPopulated()) {
						MySQL_Connection con = ((MySQL_Connection)selectedNode.getUserObject());
						if (con != null) {
							String[] dbs = con.getDatabases();
							if (dbs != null) {
								for (int i = 0; i < dbs.length; i++) {
									((DefaultTreeModel)conTree.getModel()).insertNodeInto(new DefaultMutableTreeNode(new Database(dbs[i],(MySQL_Connection)selectedNode.getUserObject())), selectedNode, i);
								}
							}
						}
					}
				}
				else if (selectedNode.getUserObject() instanceof Database) {
					Database db = (Database)selectedNode.getUserObject();
					if (!db.isOpen()) {
						String [] table = db.open();
						DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode("Tables");
						DefaultMutableTreeNode views = new DefaultMutableTreeNode("Views");
						DefaultMutableTreeNode indexes = new DefaultMutableTreeNode("Indexes");
						DefaultMutableTreeNode triggers = new DefaultMutableTreeNode("Triggers");
						DefaultMutableTreeNode queries = new DefaultMutableTreeNode("Queries");
						DefaultTreeModel tm = ((DefaultTreeModel)conTree.getModel());
						tm.insertNodeInto(tableNode, selectedNode, 0);
						tm.insertNodeInto(views, selectedNode, 1);
						tm.insertNodeInto(indexes, selectedNode, 2);
						tm.insertNodeInto(triggers, selectedNode, 3);
						tm.insertNodeInto(queries, selectedNode, 4);
						if (table != null) {
							for (int i = 0; i < table.length; i++) {
								tm.insertNodeInto(new DefaultMutableTreeNode(new Table(table[i], db.getConnection())), tableNode, i);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
