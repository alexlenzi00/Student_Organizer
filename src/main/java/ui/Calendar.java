package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import model.Lesson;
import model.Link;
import util.Util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.WEEKS;

@SuppressWarnings("CanBeFinal")
public class Calendar extends ScreenUI {
	protected LocalDate actualMonday;
	protected LocalDate calendarMonday;
	protected int weekOffset;
	protected Map<LocalDate, ArrayList<Block>> blocks;
	protected ArrayList<Label> days;

	public Calendar() {
		title = "Student Organizer - Calendar";
		LocalDate now = LocalDate.now();
		calendarMonday = actualMonday = now.minus(now.getDayOfWeek().getValue() - 1, DAYS);
		weekOffset = 0;
		blocks = new LinkedHashMap<>();
		root = new AnchorPane();
		root.setId("background");
		root.setPrefHeight(860);
		root.setPrefWidth(1600);
		days = new ArrayList<>();
		// colonne del calendario
		{
			Label colonna0 = new Label();
			colonna0.setId("calendarCol");
			colonna0.setPrefSize(100.0, 765.0);
			colonna0.setLayoutX(400);
			colonna0.setLayoutY(35);
			root.getChildren().add(colonna0);
			for (int i = 0; i < 7; i++) {
				Label dayLabel = new Label();
				dayLabel.setId("calendarCol");
				dayLabel.setLayoutX(getLabelX(i));
				dayLabel.setLayoutY(35);
				dayLabel.setPrefSize(150.0, 765.0);
				dayLabel.setTextAlignment(TextAlignment.CENTER);
				dayLabel.setAlignment(Pos.TOP_CENTER);
				dayLabel.setWrapText(true);
				root.getChildren().add(dayLabel);
				days.add(dayLabel);
			}
		}
		// righe del calendario
		{
			Label riga0 = new Label();
			riga0.setId("calendarRow");
			riga0.setLayoutX(400);
			riga0.setLayoutY(50);
			riga0.setPrefSize(1150.0, 50.0);
			root.getChildren().add(riga0);
			for (int i = 7; i < 21; i++) {
				Label hour = new Label();
				hour.setId("calendarRow");
				hour.setLayoutX(400);
				hour.setLayoutY(getLabelY(i - 7));
				hour.setPrefSize(1150.0, 50.0);
				hour.setText(String.format("%02d:00 - %02d:00", i, i + 1));
				root.getChildren().add(hour);
			}
		}
		// bottoni di controllo
		Label lChangeWeek = new Label("Cambia\nsettimana");
		{
			lChangeWeek.setId("T2");
			lChangeWeek.setPrefSize(200, 100);
			lChangeWeek.setLayoutX(100);
			lChangeWeek.setLayoutY(600);
			lChangeWeek.setAlignment(Pos.CENTER);
			lChangeWeek.setTextAlignment(TextAlignment.CENTER);
			lChangeWeek.setWrapText(true);
			root.getChildren().add(lChangeWeek);
		}
		Button btWeekPrevious = new Button("<");
		{
			btWeekPrevious.setPrefSize(50, 50);
			btWeekPrevious.setLayoutX(50);
			btWeekPrevious.setLayoutY(625);
			btWeekPrevious.setOnAction(actionEvent -> previous());
			root.getChildren().add(btWeekPrevious);
		}
		Button btWeekNext = new Button(">");
		{
			btWeekNext.setPrefSize(50, 50);
			btWeekNext.setLayoutX(300);
			btWeekNext.setLayoutY(625);
			btWeekNext.setOnAction(actionEvent -> next());
			root.getChildren().add(btWeekNext);
		}
		Button btWeekReset = new Button("Settimana attuale");
		{
			btWeekReset.setPrefSize(300, 50);
			btWeekReset.setLayoutX(50);
			btWeekReset.setLayoutY(700);
			btWeekReset.setOnAction(actionEvent -> reset());
			root.getChildren().add(btWeekReset);
		}
		update();
		setTitle("Student Organizer - Calendar");
	}

	private double getLabelX(double col) {
		return 500.0 + col * 150.0;
	}

	private double getLabelY(double row) {
		return (row + 2) * 50.0;
	}

	@Override
	protected void update() {
		calendarMonday = actualMonday.plus(weekOffset, WEEKS);
		for (LocalDate date : blocks.keySet()) {
			for (Block block : blocks.get(date)) {
				root.getChildren().remove(block);
			}
		}
		blocks.clear();
		ArrayList<Lesson> lessons = Lesson.loadAll();
		lessons.sort(Lesson::compareTo);
		lessons.removeIf(lesson -> {
			LocalDate calendarSunday = calendarMonday.plus(6, DAYS);
			return lesson.getDay().isBefore(calendarMonday) || lesson.getDay().isAfter(calendarSunday);
		});
		for (Lesson lesson : lessons) {
			String text = lesson.toString();
			State state = State.LEZIONE_PROGRAMMATA;
			LocalDate lessonDay = lesson.getDay();
			LocalDate now = LocalDate.now();
			LocalTime endHour = lesson.getEndHour();
			if (lessonDay.isBefore(now) || (lessonDay.isEqual(now) && endHour.isBefore(LocalTime.now()))) {
				state = State.LEZIONE_DA_PAGARE;
				ArrayList<Link> links = Link.loadAll();
				for (Link link : links) {
					if (link.getIdLesson() == lesson.getIdLesson()) {
						state = State.LEZIONE_COMPLETATA;
						break;
					}
				}
			}
			double x = getLabelX(calendarMonday.until(lesson.getDay()).getDays());
			double y = getLabelY(lesson.getStartHour().getHour() - 7 + lesson.getStartHour().getMinute() / 60.0);
			LocalTime duration = lesson.getDuration();
			LocalDate date = lesson.getDay();
			blocks.computeIfAbsent(date, k -> new ArrayList<>());
			Block block = new Block(text, state, x, y, duration.toSecondOfDay() / 60.0);
			block.setOnMouseClicked(mouseEvent -> {
				LessonSummary lessonSummary = new LessonSummary(App.getStage(), lesson);
				lessonSummary.show();
			});
			blocks.get(lesson.getDay()).add(block);
			root.getChildren().add(block);
		}
		for (int i = 0; i < 7; i++) {
			days.get(i).setText(Util.getDateStringExtended(calendarMonday.plus(i, DAYS)));
		}
	}

	protected void changeWeek(int n) {
		weekOffset = n > 0 ? weekOffset + 1 : (n < 0 ? weekOffset - 1 : 0);
		update();
	}

	protected void next() {
		changeWeek(1);
	}

	protected void previous() {
		changeWeek(-1);
	}

	protected void reset() {
		changeWeek(0);
	}
}