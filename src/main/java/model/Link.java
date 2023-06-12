package model;

import java.util.ArrayList;

public class Link extends Savable {
	private int idLink;
	private int idPayment;
	private int idLesson;

	protected Link() {
	}

	public Link(int idLink, int idPayment, int idLesson) {
		setIdLink(idLink);
		setIdPayment(idPayment);
		setIdLesson(idLesson);
	}

	public Link(int idLink, Payment payment, Lesson lesson) {
		this(idLink, payment.getId(), lesson.getId());
	}

	public int getIdLink() {
		return idLink;
	}

	public int getIdPayment() {
		return idPayment;
	}

	public int getIdLesson() {
		return idLesson;
	}

	public void setIdLink(int idLink) {
		this.idLink = idLink;
	}

	public void setIdPayment(int idPayment) {
		this.idPayment = idPayment;
	}

	public void setIdLesson(int idLesson) {
		this.idLesson = idLesson;
	}

	@Override
	public String toString() {
		Lesson lesson = Lesson.load(getIdLesson());
		Student student = Student.load(lesson.getIdStudent());
		return String.format("%d - lezione con %s del %s numero %d", idLink, student, lesson.getDay(), lesson.getLessonN());
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