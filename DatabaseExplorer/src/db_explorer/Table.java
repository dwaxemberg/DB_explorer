package db_explorer;

public class Table {
	private String name;
	private MySQL_Connection connection;
	
	public Table(String name, MySQL_Connection connection) {
		this.name = name;
		this.connection = connection;
	}
	
	public MySQL_Connection getConnection() {
		return connection;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
