package db_explorer;
import javax.swing.*;
import java.awt.Container;
import java.awt.event.*;

public class NewConnection extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField txtConName;
	private JTextField txtHost;
	private JTextField txtPort;
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private MainWindow parent;
	
	public NewConnection(Connection_Type type, MainWindow parent) {
		this.setSize(400,700);
		this.parent = parent;
		switch(type) {
		case MySQL:
			newConnection_MySQL();
			break;
		case SQLite:
			break;
		default:
			break;
		}
	}
	
	public void newConnection_MySQL() {
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setTitle("New Connection - MySQL");
		Container pane = this.getContentPane();
		SpringLayout layout = new SpringLayout();
		pane.setLayout(layout);
		//Create Labels
		JLabel lblConName = new JLabel("Connection Name:");
		JLabel lblHost = new JLabel("Host Name/IP Address:");
		JLabel lblPort = new JLabel("Port (not implemented):");
		JLabel lblUser = new JLabel("User Name:");
		JLabel lblPassword = new JLabel("Password:");
		//Create Text Fields
		txtConName = new JTextField("", 20);
		txtHost = new JTextField("localhost", 20);
		txtPort = new JTextField("3306", 8);
		txtUser = new JTextField("root", 20);
		txtPassword = new JPasswordField("", 20);
		//Create Buttons
		JButton btnOK = new JButton("OK");
		JButton btnCancel = new JButton("Cancel");
		JButton btnTestCon = new JButton("Test Connection");
		btnOK.setName("OK");
		btnCancel.setName("Cancel");
		btnTestCon.setName("Test Connection");
		btnOK.addActionListener(this);
		btnCancel.addActionListener(this);
		btnTestCon.addActionListener(this);
		//Add items to pane
		pane.add(lblConName);
		pane.add(txtConName);
		pane.add(lblHost);
		pane.add(txtHost);
		pane.add(lblPort);
		pane.add(txtPort);
		pane.add(lblUser);
		pane.add(txtUser);
		pane.add(lblPassword);
		pane.add(txtPassword);
		pane.add(btnOK);
		pane.add(btnCancel);
		pane.add(btnTestCon);
		
		this.getRootPane().setDefaultButton(btnOK);
		//Add constraints to items (location/size)
			//Connection name
		layout.putConstraint(SpringLayout.EAST, txtConName, -15, SpringLayout.EAST, pane);
		layout.putConstraint(SpringLayout.NORTH, txtConName, 40, SpringLayout.NORTH, pane);
		layout.putConstraint(SpringLayout.EAST, lblConName, -10, SpringLayout.WEST, txtConName);
		layout.putConstraint(SpringLayout.NORTH,  lblConName, 4, SpringLayout.NORTH, txtConName);
			//Host Name
		layout.putConstraint(SpringLayout.EAST, lblHost, -10, SpringLayout.WEST, txtHost);
		layout.putConstraint(SpringLayout.EAST, txtHost, -15, SpringLayout.EAST, pane);
		layout.putConstraint(SpringLayout.NORTH, txtHost, 5, SpringLayout.SOUTH, txtConName);
		layout.putConstraint(SpringLayout.NORTH,  lblHost, 4, SpringLayout.NORTH, txtHost);
			//Port
		layout.putConstraint(SpringLayout.EAST, lblPort, -10, SpringLayout.WEST, txtPort);
		layout.putConstraint(SpringLayout.WEST, txtPort, 0, SpringLayout.WEST, txtHost);
		layout.putConstraint(SpringLayout.NORTH, txtPort, 5, SpringLayout.SOUTH, txtHost);
		layout.putConstraint(SpringLayout.NORTH,  lblPort, 4, SpringLayout.NORTH, txtPort);
			//User Name
		layout.putConstraint(SpringLayout.EAST, lblUser, -10, SpringLayout.WEST, txtUser);
		layout.putConstraint(SpringLayout.EAST,  txtUser,  -15, SpringLayout.EAST, pane);
		layout.putConstraint(SpringLayout.NORTH, txtUser, 5, SpringLayout.SOUTH, txtPort);
		layout.putConstraint(SpringLayout.NORTH, lblUser, 4, SpringLayout.NORTH, txtUser);
			//Password
		layout.putConstraint(SpringLayout.EAST, lblPassword, -10, SpringLayout.WEST, txtPassword);
		layout.putConstraint(SpringLayout.EAST, txtPassword, -15, SpringLayout.EAST, pane);
		layout.putConstraint(SpringLayout.NORTH, txtPassword, 5, SpringLayout.SOUTH, txtUser);
		layout.putConstraint(SpringLayout.NORTH, lblPassword, 4, SpringLayout.NORTH, txtPassword);
			//OK Button
		layout.putConstraint(SpringLayout.EAST, btnOK, -15, SpringLayout.EAST, pane);
		layout.putConstraint(SpringLayout.SOUTH, btnOK, -5, SpringLayout.SOUTH, pane);
			//Cancel Button
		layout.putConstraint(SpringLayout.EAST, btnCancel, 5, SpringLayout.WEST, btnOK);
		layout.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnOK);
			//Test Connection Button
		layout.putConstraint(SpringLayout.SOUTH,  btnTestCon, 0, SpringLayout.SOUTH, btnOK);
		layout.putConstraint(SpringLayout.WEST, btnTestCon, 15, SpringLayout.WEST, pane);
			//Pane
		layout.putConstraint(SpringLayout.SOUTH, pane, 200, SpringLayout.SOUTH, txtPassword);
		layout.putConstraint(SpringLayout.EAST, pane, 440, SpringLayout.WEST, pane);
		
		this.pack();
		this.setVisible(true);
		
	}
	
	private String getPassword() {
		StringBuilder b = new StringBuilder();
		b.append(txtPassword.getPassword());
		return b.toString();
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() instanceof JButton) {
			JButton btnClicked = (JButton)event.getSource();
			if (btnClicked.getName().equals("Test Connection")) {
				try {
					MySQL_Connection con = new MySQL_Connection(txtConName.getText(), txtHost.getText(), txtPort.getText(), txtUser.getText(), getPassword());
					con.connect();
					con.close();
					JOptionPane.showMessageDialog(this, "Connection Successful!", "Connection Status", JOptionPane.INFORMATION_MESSAGE);
				}
				catch (Exception e) {
					e.printStackTrace();
					String usingPass;
					if (getPassword().equals(""))
						usingPass = "No";
					else
						usingPass = "Yes";
					JOptionPane.showMessageDialog(this, "Could not connect to: \n" 
							+ txtUser.getText() + "@" + txtHost.getText() + "\nUsing password: " 
							+ usingPass, "Error Connecting", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (btnClicked.getName().equals("OK")) {
				if (txtConName.getText().equals("")) {
					JOptionPane.showMessageDialog(this, "Please enter a name for this connection.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if (txtHost.getText().equals("")) {
					JOptionPane.showMessageDialog(this, "Please enter a Host Name/IP Address for this connection.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if (txtUser.getText().equals("")) {
					JOptionPane.showMessageDialog(this, "Please enter a User for this connection.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if (txtPort.getText().equals("")) {
					JOptionPane.showMessageDialog(this, "Please enter a Port for this connection.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					//TODO: Create new connection in main window.
					MySQL_Connection con = new MySQL_Connection(txtConName.getText(), txtHost.getText(), txtPort.getText(), txtUser.getText(), getPassword());
					if (parent.findMySQLConnection(con.getName()) == null) {
						parent.newMySQLConnection(con);
						this.setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(this, "A connection with this name already exists. \nPlease choose another name.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			else if (btnClicked.getName().equals("Cancel")) {
				this.dispose();
			}
		}
	}
}
