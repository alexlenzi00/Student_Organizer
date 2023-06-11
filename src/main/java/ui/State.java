package ui;

import javafx.scene.paint.Color;
import util.Util;

import java.util.StringJoiner;

public enum State {
	LEZIONE_PROGRAMMATA(255, 255, 100), LEZIONE_COMPLETATA(60, 255, 100), LEZIONE_DA_PAGARE(255, 100, 60);
	private final Color color;

	State(int r, int g, int b) {
		this.color = Color.rgb(r, g, b, 0.9);
	}

	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner("");
		String[] array = super.toString().split("_");
		for (String s : array) {
			sj.add(Util.capitalString(s));
		}
		return sj.toString();
	}
}
