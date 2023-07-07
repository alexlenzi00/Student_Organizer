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

	public static <T extends Savable> T load(int id) {
		return load("Subject", id);
	}

	public static <T extends Savable> ArrayList<T> loadAll() {
		return loadAll("Subject");
	}
}