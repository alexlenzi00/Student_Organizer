package util;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Util {
	private Util() {
	}

	public static String capitalString(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		char firstChar = Character.toUpperCase(str.charAt(0));
		String restOfString = str.substring(1).toLowerCase();
		return firstChar + restOfString;
	}

	public static String file2String(String filename) {
		StringJoiner sj = new StringJoiner(" ");
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			while ((line = br.readLine()) != null) {
				sj.add(line.replace("\n", "").replace("\t", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sj.toString();
	}

	public static String getDurationString(LocalTime duration) {
		StringJoiner sj = new StringJoiner(" e ");
		int hours = duration.getHour();
		int minutes = duration.getMinute();
		if (hours != 0) {
			sj.add(String.format("%d or%c", hours, hours == 1 ? 'a' : 'e'));
		}
		if (minutes != 0) {
			sj.add(String.format("%02d minuti", minutes));
		}
		return sj.toString();
	}

	public static String getTimeString(LocalTime time) {
		return String.format("%02d:%02d", time.getHour(), time.getMinute());
	}

	public static String getDateString(LocalDate date, String delimiter) {
		StringJoiner sj = new StringJoiner(delimiter);
		sj.add(String.format("%02d", date.getDayOfMonth()));
		sj.add(String.format("%02d", date.getMonth().getValue()));
		sj.add(String.format("%d", date.getYear()));
		return sj.toString();
	}

	public static String getDateStringExtended(@NotNull LocalDate date) {
		List<String> stringDay = Arrays.asList("Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom");
		List<String> stringMonth = Arrays.asList("Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic");
		String weekDay = stringDay.get(date.getDayOfWeek().getValue() - 1);
		int day = date.getDayOfMonth();
		String month = stringMonth.get(date.getMonth().getValue() - 1);
		int year = date.getYear();
		return String.format("%s %02d %s %d", weekDay, day, month, year);
	}
}
