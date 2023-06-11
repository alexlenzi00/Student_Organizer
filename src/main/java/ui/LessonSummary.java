package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Lesson;
import model.Rate;
import model.Student;
import model.Subject;
import org.jetbrains.annotations.NotNull;
import util.Util;

import java.util.Objects;

public class LessonSummary extends Stage {
	private Label createLabel(String text, double x, double y, double width, double height) {
		Label label = new Label(text);
		label.setLayoutX(x);
		label.setLayoutY(y);
		label.setPrefSize(width, height);
		label.setId("T2");
		label.setWrapText(true);
		label.setTextAlignment(TextAlignment.LEFT);
		label.setAlignment(Pos.TOP_LEFT);
		return label;
	}

	public LessonSummary(Stage owner, @NotNull Lesson lesson) {
		initOwner(owner);
		initModality(Modality.APPLICATION_MODAL);
		initStyle(StageStyle.DECORATED);
		setTitle("Riepilogo lezione");
		AnchorPane root = new AnchorPane();
		root.setId("background");
		{
			Label titleLabel = createLabel("Riepilogo lezione", 25, 10, 650, 50);
			titleLabel.setId("T1");
			root.getChildren().add(titleLabel);
		}
		root.getChildren().add(createLabel("Studente:\nMateria:\nTariffa:\nNumero lezione:\nGiorno lezione:\nOrario lezione:\nDurata lezione:\nArgomenti trattati:", 50, 60, 250, 300));
		{
			Subject subject = Subject.load(lesson.getIdSubject());
			Student student = Student.load(lesson.getIdStudent());
			Rate rate = Rate.load(lesson.getIdRate());
			Label rightLabel = createLabel("", 325, 60, 500, 300);
			rightLabel.setText(String.format("%s\n%s\n%s\n%d\n%s\n%s - %s\n%s\n%s", student, subject, rate.getName(), lesson.getLessonN(), Util.getDateString(lesson.getDay(), "/"), Util.getTimeString(lesson.getStartHour()), Util.getTimeString(lesson.getEndHour()), Util.getDurationString(lesson.getDuration()), lesson.getDescription()));
			root.getChildren().add(rightLabel);
		}
		Scene scene = new Scene(root, 850, 375);
		String cssFile = Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm();
		scene.getStylesheets().add(cssFile);
		setScene(scene);
		super.setResizable(false);
		super.setX((javafx.stage.Screen.getPrimary().getBounds().getWidth() - scene.getWidth()) / 2);
		super.setY((javafx.stage.Screen.getPrimary().getBounds().getHeight() - scene.getHeight()) / 2.5);
	}
}
