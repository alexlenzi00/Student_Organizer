package ui;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public abstract class ScreenUI {
	protected Scene scene;
	protected String title;
	protected AnchorPane root;

	public Scene getScene() {
		return scene;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	protected abstract void update();
}