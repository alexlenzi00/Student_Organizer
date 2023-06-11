package model;

import util.Util;

import java.time.LocalTime;
import java.util.ArrayList;

public class Rate extends Savable {
	private int idRate;
	private String name;
	private LocalTime duration;
	private double price;

	protected Rate() {
	}

	public Rate(int idRate, String name, LocalTime duration, double price) {
		setIdRate(idRate);
		setName(name);
		setDuration(duration);
		setPrice(price);
	}

	public int getIdRate() {
		return idRate;
	}

	public String getName() {
		return name;
	}

	public LocalTime getDuration() {
		return duration;
	}

	public double getPrice() {
		return price;
	}

	private void setIdRate(int idRate) {
		this.idRate = idRate;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setDuration(LocalTime duration) {
		this.duration = duration;
	}

	private void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return String.format("Tariffa \"%s\" della durata di %s", name, Util.getDurationString(duration));
	}

	@Override
	public int getId() {
		return getIdRate();
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