package db;

public class sqlLiteDatabase extends Database {
	private final String databasePath;

	public sqlLiteDatabase(String databasePath) {
		this.databasePath = databasePath;
	}

	@Override
	protected String getUrl() {
		return "jdbc:sqlite:" + databasePath;
	}
}
