package db_explorer;

public class Database {
	private String name;
	private MySQL_Connection connection;
	private boolean open = false;
	
	public Database(String name, MySQL_Connection connection) {
		this.name = name;
		this.connection = connection;
	}
	
	public MySQL_Connection getConnection() {
		return connection;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public String[] open() {
		open = true;
		return connection.getTables(name);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
