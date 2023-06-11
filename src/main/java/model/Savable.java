package model;

import db.Loader;
import db.Saver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public abstract class Savable implements Identifiable {
	public boolean save() {
		return Saver.save(this);
	}

	private static <T extends Savable> @Nullable T load(String tableName, int id) {
		T ris = null;
		ArrayList<T> list = loadAll(tableName);
		for (T savable : list) {
			if (savable.getId() == id) {
				ris = savable;
				break;
			}
		}
		return ris;
	}

	private static <T extends Savable> @NotNull ArrayList<T> loadAll(String tableName) {
		return Loader.load(tableName);
	}

	protected static Object load(Integer @NotNull ... optionalId) {
		String tableName = Thread.currentThread().getStackTrace()[2].getClassName();
		if (optionalId.length == 0) {
			return loadAll(tableName);
		} else {
			return load(tableName, optionalId[0]);
		}
	}

	@Override
	public abstract int getId();
}