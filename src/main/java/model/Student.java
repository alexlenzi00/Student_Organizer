package model;

import java.util.ArrayList;

public class Student extends Savable implements Comparable<Student> {
	private int idStudent;
	private String name;
	private String surname;
	private String contact;

	protected Student() {
	}

	public Student(int idStudent, String name, String surname, String contact) {
		this();
		setIdStudent(idStudent);
		setName(name);
		setSurname(surname);
		setContact(contact);
	}

	public int getIdStudent() {
		return idStudent;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getContact() {
		return contact;
	}

	public ArrayList<Lesson> getLessons() {
		ArrayList<Lesson> lessons = Lesson.loadAll();
		lessons.removeIf(lesson -> lesson.getIdStudent() != getId());
		return lessons;
	}

	private void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setSurname(String surname) {
		this.surname = surname;
	}

	private void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return String.format("%s %s", name, surname);
	}

	@Override
	public int compareTo(Student o) {
		int tmp = name.compareTo(o.getName());
		return tmp != 0 ? tmp : surname.compareTo(o.getSurname());
	}

	public static <T extends Savable> T load(int id) {
		return load("Student", id);
	}

	public static <T extends Savable> ArrayList<T> loadAll() {
		return loadAll("Student");
	}
}
