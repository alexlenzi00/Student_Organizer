package model;

import org.jetbrains.annotations.NotNull;
import ui.App;

import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class Savable implements Identifiable {
	public boolean save() {
		return App.save(this);
	}

	public static <T extends Savable> @NotNull ArrayList<T> loadAll(String tableName) {
		return App.loadAll(tableName);
	}

	public static <T extends Savable> T load(String tableName, int id) {
		return App.load(tableName, id);
	}

	@Override
	public int getId() {
		int ris = -1;
		try {
			String name = this.getClass().getSimpleName();
			Method m = this.getClass().getDeclaredMethod("getId" + name);
			ris = (int) m.invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ris;
	}
}