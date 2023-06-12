package model;

import java.util.ArrayList;

public class Subject extends Savable {
	private int idSubject;
	private String name;

	protected Subject() {
	}

	public Subject(int idSubject, String name) {
		setIdSubject(idSubject);
		setName(name);
	}

	public int getIdSubject() {
		return idSubject;
	}

	public String getName() {
		return name;
	}

	private void setIdSubject(int idSubject) {
		this.idSubject = idSubject;
	}

	private void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
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