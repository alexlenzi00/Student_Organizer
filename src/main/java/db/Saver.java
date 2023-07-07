package db;

import model.Savable;

public interface Saver {
	boolean save(Savable savableObject);
}
