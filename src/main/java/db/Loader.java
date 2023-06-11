package db;

import model.Savable;
import ui.App;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Loader {
	@SuppressWarnings("unchecked")
	public static <T extends Savable> ArrayList<T> load(String tableName) {
		ArrayList<T> ris = new ArrayList<>();
		try {
			Class<T> c = (Class<T>) Class.forName(tableName);
			String query = String.format("SELECT * FROM %s", tableName.replace("model.", ""));
			try (ResultSet resultSet = App.getDatabase().executeQuery(query)) {
				Constructor<T> TConstructor = null;
				for (Constructor<T> constructor : (Constructor<T>[]) c.getDeclaredConstructors()) {
					if (constructor.getParameterCount() == 0) {
						constructor.setAccessible(true);
						TConstructor = constructor;
						break;
					}
				}
				while (TConstructor != null && resultSet != null && resultSet.next()) {
					T last = TConstructor.newInstance();
					for (Field field : c.getDeclaredFields()) {
						field.setAccessible(true);
						String name = field.getName();
						Class<?> fieldClass = field.getType();
						Object value;
						boolean isDate = fieldClass.equals(LocalDate.class);
						boolean isTime = fieldClass.equals(LocalTime.class);
						if (isDate || isTime) {
							String str = resultSet.getString(name);
							String[] tokens = str.split(isDate ? "-" : ":");
							int t1 = Integer.parseInt(tokens[0]);
							int t2 = Integer.parseInt(tokens[1]);
							int t3 = isDate ? Integer.parseInt(tokens[2]) : 0;
							value = isDate ? LocalDate.of(t1, t2, t3) : LocalTime.of(t1, t2, t3);
						} else {
							value = resultSet.getObject(name);
						}
						field.set(last, value);
					}
					ris.add(last);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ris;
	}
}