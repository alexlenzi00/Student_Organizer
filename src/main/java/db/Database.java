package db;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

public abstract class Database {
	protected static Connection connection;
	protected Saver saver;
	protected Loader loader;

	protected Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(getUrl());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public Saver getSaver() {
		return saver;
	}

	public Loader getLoader() {
		return loader;
	}

	protected void setSaver(Saver saver) {
		this.saver = saver;
	}

	protected void setLoader(Loader loader) {
		this.loader = loader;
	}

	protected abstract String getUrl();

	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException ignored) {
		}
	}

	public ResultSet executeQuery(String query) {
		ResultSet ris = null;
		if (getConnection() != null) {
			try {
				Statement statement = connection.createStatement();
				ris = statement.executeQuery(query);
			} catch (SQLException ignored) {
			}
		}
		return ris;
	}

	public @Nullable ResultSet executeQueryWithParams(String query, Object @NotNull ... params) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				preparedStatement.setObject(i + 1, params[i]);
			}
			return preparedStatement.executeQuery();
		} catch (SQLException ignored) {
		}
		return null;
	}

	public boolean executeUpdate(String query) {
		boolean ris = true;
		Connection c;
		if ((c = getConnection()) != null) {
			try {
				Statement statement = c.createStatement();
				c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
				statement.executeUpdate(query);
			} catch (SQLException ignored) {
				ris = false;
			}
		}
		return ris;
	}

	public boolean executeUpdateWithParams(String query, Object @NotNull ... params) {
		boolean ris = true;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				preparedStatement.setObject(i + 1, params[i]);
			}
			preparedStatement.executeUpdate();
		} catch (SQLException ignored) {
			ris = false;
		}
		return ris;
	}
}
