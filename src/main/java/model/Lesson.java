package model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Lesson extends Savable implements Comparable<Lesson> {
	private int idLesson;
	private int idRate;
	private int idStudent;
	private int idSubject;
	private int lessonN;
	private LocalDate day;
	private LocalTime startHour;
	private LocalTime duration;
	private String description;

	protected Lesson() {
	}

	private Lesson(int idLesson, int lessonN, LocalDate day, LocalTime startHour, LocalTime duration, String description) {
		setIdLesson(idLesson);
		setLessonN(lessonN);
		setDay(day);
		setStartHour(startHour);
		setDuration(duration);
		setDescription(description);
	}

	public Lesson(int idLesson, int idRate, int idStudent, int idSubject, int lessonN, LocalDate day, LocalTime startHour, LocalTime duration, String description) {
		this(idLesson, lessonN, day, startHour, duration, description);
		setIdRate(idRate);
		setIdStudent(idStudent);
		setIdSubject(idSubject);
	}

	public Lesson(int idLesson, @NotNull Rate rate, @NotNull Student student, @NotNull Subject subject, int lessonN, LocalDate day, LocalTime startHour, LocalTime duration, String description) {
		this(idLesson, rate.getId(), student.getId(), subject.getId(), lessonN, day, startHour, duration, description);
	}

	public int getIdLesson() {
		return idLesson;
	}

	public int getIdRate() {
		return idRate;
	}

	public int getIdStudent() {
		return idStudent;
	}

	public int getIdSubject() {
		return idSubject;
	}

	public int getLessonN() {
		return lessonN;
	}

	public LocalDate getDay() {
		return day;
	}

	public LocalTime getStartHour() {
		return startHour;
	}

	public LocalTime getEndHour() {
		return startHour.plus(duration.toSecondOfDay() / 60, ChronoUnit.MINUTES);
	}

	public LocalTime getDuration() {
		return duration;
	}

	public String getDescription() {
		return description;
	}

	private void setIdLesson(int idLesson) {
		this.idLesson = idLesson;
	}

	private void setIdRate(int idRate) {
		this.idRate = idRate;
	}

	private void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}

	private void setIdSubject(int idSubject) {
		this.idSubject = idSubject;
	}

	private void setLessonN(int lessonN) {
		this.lessonN = lessonN;
	}

	private void setDay(LocalDate day) {
		this.day = day;
	}

	private void setStartHour(LocalTime startHour) {
		this.startHour = startHour;
	}

	private void setDuration(LocalTime duration) {
		this.duration = duration;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(@NotNull Lesson o) {
		int a = day.compareTo(o.getDay());
		int b = startHour.compareTo(o.getStartHour());
		int c = Integer.compare(idStudent, o.getIdStudent());
		return a != 0 ? a : (b != 0 ? b : c);
	}

	@Override
	public String toString() {
		Subject subject = Subject.load(idSubject);
		Student student = Student.load(idStudent);
		return String.format("%s\n%s", student, subject);
	}

	@Override
	public int getId() {
		return getIdLesson();
	}

	@SuppressWarnings("unchecked")
	public static <T extends Savable> T load(int id) {
		return (T) Savable.load(id);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Savable> ArrayList<T> loadAll() {
		return (ArrayList<T>) Savable.load();
	}
}