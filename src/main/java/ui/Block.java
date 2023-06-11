package ui;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.text.TextAlignment;
import model.Lesson;
import model.Link;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

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

	public Block(Lesson lesson, double x, double y) {
		this();
		setText(lesson.toString());
		State state = new Scheduled();
		LocalDate lessonDay = lesson.getDay();
		LocalDate now = LocalDate.now();
		LocalTime endHour = lesson.getEndHour();
		if (lessonDay.isBefore(now) || (lessonDay.isEqual(now) && endHour.isBefore(LocalTime.now()))) {
			state = new ToPay();
			ArrayList<Link> links = Link.loadAll();
			for (Link link : links) {
				if (link.getIdLesson() == lesson.getId()) {
					state = new Completed();
					break;
				}
			}
		}
		setState(state);
		super.setBackground(new Background(new BackgroundFill(getState().getColor(), null, null)));
		setPosition(x, y);
	}

	public Block(Lesson lesson, double x, double y, double minutes) {
		this(lesson, x, y);
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