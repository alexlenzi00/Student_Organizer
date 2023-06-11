package util;

import model.*;
import org.jetbrains.annotations.NotNull;
import ui.App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

	public static void main(String[] args) {
		App.init("./students.db", "./db.sql");
		Rate r1 = new Rate(1, "Pacchetto 1h", LocalTime.of(1, 0, 0), 15.00);
		Rate r2 = new Rate(2, "Pacchetto 5h", LocalTime.of(5, 0, 0), 65.00);
		Rate r3 = new Rate(3, "Pacchetto 10h", LocalTime.of(10, 0, 0), 120.00);
		r1.save();
		r2.save();
		r3.save();
		Student s1 = new Student(1, "Alex", "Lenzi", "");
		Student s2 = new Student(2, "Chiara", "Logli", "");
		s1.save();
		s2.save();
		Subject sj1 = new Subject(1, "Fondamenti Informatica 1");
		Subject sj2 = new Subject(2, "Fondamenti Informatica 2");
		Subject sj3 = new Subject(3, "Sistemi Operativi");
		sj1.save();
		sj2.save();
		sj3.save();
		LocalDate d1 = LocalDate.of(2023, 6, 4);
		LocalTime t1 = LocalTime.of(14, 30);
		Lesson l1 = new Lesson(1, r1, s1, sj1, 1, d1, t1, LocalTime.of(1, 0, 0), "Lezione test");
		if (l1.save()) {
			System.out.println("Lezione salvata con successo!");
		}
		Lesson l2 = new Lesson(2, r1, s2, sj3, 1, d1.plus(1, ChronoUnit.DAYS), t1.plus(30, ChronoUnit.MINUTES), LocalTime.of(1, 0, 0), "Lezione test 2");
		if (l2.save()) {
			System.out.println("Lezione salvata con successo!");
		}
		Lesson l3 = new Lesson(3, r2, s2, sj2, 1, d1.plus(2, ChronoUnit.DAYS), t1.plus(90, ChronoUnit.MINUTES), LocalTime.of(1, 0, 0), "Lezione test 2");
		if (l3.save()) {
			System.out.println("Lezione salvata con successo!");
		}
		Payment p1 = new Payment(1, d1, 1, 1);
		p1.save();
		Link link = new Link(1, 1, 1);
		link.save();
		Student student1 = Student.load(1);
		System.out.println(student1);
		Student student2 = Student.load(2);
		System.out.println(student2);
		ArrayList<Student> students = Student.loadAll();
		System.out.println(students);
		ArrayList<Rate> rates = Rate.loadAll();
		System.out.println(rates);
		ArrayList<Subject> subjects = Subject.loadAll();
		System.out.println(subjects);
		ArrayList<Lesson> lessons = Lesson.loadAll();
		System.out.println(lessons);
		ArrayList<Link> links = Link.loadAll();
		System.out.println(links);
		ArrayList<Payment> payments = Payment.loadAll();
		System.out.println(payments);
	}
}
