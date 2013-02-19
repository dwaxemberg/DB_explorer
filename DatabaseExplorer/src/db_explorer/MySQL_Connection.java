package db_explorer;
import java.sql.*;
public class MySQL_Connection {
	private Connection conn = null;
	private String name;
	private String host;
	private String port;
	private String user;
	private String password;
	private boolean dbPopulated = false;
	
	public MySQL_Connection(String name, String host, String port, String user, String password) {
		this.name = name;
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		try {
			connect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public boolean dbsPopulated() {
		return dbPopulated;
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public boolean insert(String insertStatement) {
		try {
			Statement stmnt = conn.createStatement();
			stmnt.execute(insertStatement);
			conn.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ResultSet executeQuery(String query) {
		try {
			return conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(query);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean delete(String deleteStatement) {
		try {
			conn.createStatement().execute(deleteStatement);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String[] getTables(String database) {
		try {
			ResultSet rs = conn.getMetaData().getColumns(database, null, null, null);
			int index = 0;
			String[] tables = new String[0];
			String[] tmp;
			while (rs.next()) {
				if (!contains(tables, rs.getString("TABLE_NAME"))) {
					tmp = new String[tables.length+1];
					for (int i = 0; i < tables.length; i++) {
						tmp[i] = tables[i];
					}
					tables = tmp;
					tables[index] = rs.getString("TABLE_NAME");
					index++;
				}
			}
			return tables;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private boolean contains(String[] arr, String value) {
		for (int i = 0; i < arr.length && arr[i] != null; i++) {
			if (arr[i].equals(value))
				return true;
		}
		return false;
	}
	
	public void printColumnNames(String database) {
		try {
			ResultSet rs = conn.getMetaData().getColumns(database, null, null, null);
			while (rs.next()) {
				System.out.println(rs.getString("COLUMN_NAME"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] getDatabases() {
		try {
			ResultSet rs = conn.getMetaData().getCatalogs();
			int index = 0;
			String[] dbs = new String[0];
			String[] tmp;
			while (rs.next()) {
			    //System.out.println("TABLE_CAT = " + rs.getString("TABLE_CAT") );
				tmp = new String[dbs.length+1];
				for (int i = 0; i < dbs.length; i++) {
					tmp[i] = dbs[i];
				}
				dbs = tmp;
				dbs[index] = rs.getString("TABLE_CAT");
				index++;
			}
			dbPopulated = true;
			return dbs;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void connect() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + host + ":" + port + "/";
			conn = DriverManager.getConnection(url, user, password);
		}
		catch (Exception e) {
			throw e;
		}
	}

	public void connect(String database) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
			conn = DriverManager.getConnection(url, user, password);
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} 
		catch (Exception e) {
			
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
}
