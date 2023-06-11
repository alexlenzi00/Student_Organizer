package ui;

import javafx.scene.paint.Color;

public class State {
	String name;
	Color color;

	protected State(String name, Color color) {
		setName(name);
		setColor(color);
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return name;
	}
}
