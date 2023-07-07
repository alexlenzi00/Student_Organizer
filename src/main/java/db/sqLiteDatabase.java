package db;

public class sqLiteDatabase extends Database {
	private final String databasePath;

	public sqLiteDatabase(String databasePath) {
		this.databasePath = databasePath;
		setLoader(new SQLiteLoader());
		setSaver(new SQLiteSaver());
	}

	@Override
	protected String getUrl() {
		return "jdbc:sqlite:" + databasePath;
	}
}
