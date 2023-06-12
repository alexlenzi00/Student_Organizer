package model;

import util.Util;

import java.time.LocalDate;
import java.util.ArrayList;

public class Payment extends Savable {
	private int idPayment;
	private LocalDate day;
	private int idStudent;
	private int idRate;

	protected Payment() {
	}

	public Payment(int idPayment, LocalDate day, int idStudent, int idRate) {
		setIdPayment(idPayment);
		setDay(day);
		setIdStudent(idStudent);
		setIdRate(idRate);
	}

	public Payment(int idPayment, LocalDate day, Student student, Rate rate) {
		this(idPayment, day, student.getId(), rate.getId());
	}

	public int getIdPayment() {
		return idPayment;
	}

	public LocalDate getDay() {
		return day;
	}

	public int getIdStudent() {
		return idStudent;
	}

	public int getIdRate() {
		return idRate;
	}

	private void setIdPayment(int idPayment) {
		this.idPayment = idPayment;
	}

	private void setDay(LocalDate day) {
		this.day = day;
	}

	private void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}

	private void setIdRate(int idRate) {
		this.idRate = idRate;
	}

	@Override
	public String toString() {
		Student student = Student.load(getIdStudent());
		Rate rate = Rate.load(getIdRate());
		return String.format("%d - Pagamento di %.2f euro da %s di \"'%s\" in data %s", getIdPayment(), rate.getPrice(), student, rate.getName(), Util.getDateString(getDay(), "/"));
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
