package ui;

import db.Database;
import db.sqLiteDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Savable;
import util.Util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class App extends Application {
	protected static Stage stage;
	protected static Database database;
	protected ArrayList<UpdatableScreen> screens = new ArrayList<>();

	public static void init(String databasePath, String SQLFilename) {
		File dbFile = new File(databasePath);
		boolean tmp = dbFile.exists();
		setDatabase(new sqLiteDatabase(databasePath));
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

	protected static void setDatabase(Database database) {
		App.database = database;
	}

	protected static void setStage(Stage stage) {
		App.stage = stage;
	}

	public void changeScreen(String screenName) {
		UpdatableScreen screen = null;
		for (UpdatableScreen s : screens) {
			if (s.getName().equals(screenName)) {
				screen = s;
				break;
			}
		}
		if (screen != null) {
			Scene scene = new Calendar().getScene();
			URL cssFile = getClass().getResource("style.css");
			if (cssFile != null) {
				scene.getStylesheets().add(cssFile.toExternalForm());
				stage.setTitle(screen.getTitle());
				stage.setResizable(false);
				stage.setX((Screen.getPrimary().getBounds().getWidth() - scene.getWidth()) / 2);
				stage.setY((Screen.getPrimary().getBounds().getHeight() - scene.getHeight()) / 4.5);
				stage.setScene(scene);
				stage.show();
			}
		}
	}

	public static boolean save(Savable savableObject) {
		return getDatabase().getSaver().save(savableObject);
	}

	public static <T extends Savable> ArrayList<T> loadAll(String tableName) {
		return getDatabase().getLoader().loadAll(tableName);
	}

	public static <T extends Savable> T load(String tableName, int id) {
		return getDatabase().getLoader().load(tableName, id);
	}

	@Override
	public void start(Stage stage) {
		setStage(stage);
		init("./students.db", "./db.sql");
		screens.add(new Calendar());
		changeScreen("Calendar");
	}

	public static void main(String[] args) {
		launch(args);
	}
}