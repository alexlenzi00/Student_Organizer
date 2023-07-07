package db;

import model.Savable;
import org.jetbrains.annotations.NotNull;
import ui.App;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.StringJoiner;

public class SQLiteSaver implements Saver {
	public boolean save(@NotNull Savable savableObject) {
		Class<?> c = savableObject.getClass();
		StringJoiner q1 = new StringJoiner(", ", "INSERT INTO " + c.getSimpleName() + "(", ")");
		StringJoiner q2 = new StringJoiner(", ", "VALUES (", ")");
		for (Field field : c.getDeclaredFields()) {
			try {
				field.setAccessible(true);
				String name = field.getName();
				Object value = field.get(savableObject);
				q1.add(name);
				if (value instanceof String) {
					q2.add("\"" + value + "\"");
				} else if (value instanceof LocalTime) {
					q2.add("'" + value + ":00'");
				} else if (value instanceof LocalDate) {
					q2.add("'" + value + "'");
				} else {
					q2.add(value.toString());
				}
			} catch (IllegalAccessException ignored) {
				return false;
			}
		}
		return App.getDatabase().executeUpdate(q1 + " " + q2);
	}
}
