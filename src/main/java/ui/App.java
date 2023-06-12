package ui;

import db.Database;
import db.sqlLiteDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import util.Util;

import java.io.File;
import java.net.URL;

public class App extends Application {
	protected static Stage stage;
	protected static Database database;

	public static void init(String databasePath, String SQLFilename) {
		File dbFile = new File(databasePath);
		boolean tmp = dbFile.exists();
		setDatabase(new sqlLiteDatabase(databasePath));
		if (!tmp) {
			App.getDatabase().executeUpdate(Util.file2String(SQLFilename));
		}
	}

	public static Database getDatabase() {
		return App.database;
	}

	public static Stage getStage() {
		return App.stage;
	}

	public static void setDatabase(Database database) {
		App.database = database;
	}

	public static void setStage(Stage stage) {
		App.stage = stage;
	}

	@Override
	public void start(Stage stage) {
		setStage(stage);
		init("./students.db", "./db.sql");
		Scene scene = new Calendar().getScene();
		URL cssFile = getClass().getResource("style.css");
		if (cssFile != null) {
			scene.getStylesheets().add(cssFile.toExternalForm());
			stage.setTitle("Student Organizer");
			stage.setResizable(false);
			stage.setX((Screen.getPrimary().getBounds().getWidth() - scene.getWidth()) / 2);
			stage.setY((Screen.getPrimary().getBounds().getHeight() - scene.getHeight()) / 4.5);
			stage.setScene(scene);
			stage.show();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}