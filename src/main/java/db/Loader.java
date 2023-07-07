package db;

import model.Savable;

import java.util.ArrayList;

public interface Loader {
	<T extends Savable> ArrayList<T> loadAll(String tableName);
	<T extends Savable> T load(String tableName, int id);
}