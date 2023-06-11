package ui;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.text.TextAlignment;

public class Block extends Label {
	private State state;
	private double minutes;

	private Block() {
		setMinutes(60);
		super.setPrefWidth(150);
		super.setTextAlignment(TextAlignment.CENTER);
		super.setAlignment(Pos.CENTER);
		super.setWrapText(true);
	}

	public Block(String text, State state, double x, double y) {
		this();
		setText(text);
		setState(state);
		super.setBackground(new Background(new BackgroundFill(state.getColor(), null, null)));
		setPosition(x, y);
	}

	public Block(String text, State state, double x, double y, double minutes) {
		this(text, state, x, y);
		setMinutes(minutes);
	}

	public State getState() {
		return state;
	}

	public Point2D getPosition() {
		return new Point2D(super.getLayoutX(), super.getLayoutY());
	}

	public double getMinutes() {
		return minutes;
	}

	public void setState(State state) {
		this.state = state;
	}

	private void setPosition(double x, double y) {
		super.setLayoutX(x);
		super.setLayoutY(y);
	}

	private void setMinutes(double minutes) {
		this.minutes = minutes;
		super.setPrefHeight(this.minutes * 50 / 60 - 2);
	}

	@Override
	public String toString() {
		return state.toString();
	}
}