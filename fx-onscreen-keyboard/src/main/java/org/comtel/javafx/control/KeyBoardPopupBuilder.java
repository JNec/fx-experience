package org.comtel.javafx.control;

import java.util.Locale;

import javafx.util.Builder;

import org.comtel.javafx.robot.IRobot;

public class KeyBoardPopupBuilder implements Builder<KeyBoardPopup> {

	private final KeyBoardBuilder kb;

	protected KeyBoardPopupBuilder() {
		kb = KeyBoardBuilder.create();
	}

	public static KeyBoardPopupBuilder create() {
		return new KeyBoardPopupBuilder();
	}

	public KeyBoardPopupBuilder initLocale(Locale locale) {
		kb.initLocale(locale);
		return this;
	}

	public KeyBoardPopupBuilder initScale(double scale) {
		kb.initScale(scale);
		return this;
	}

	public KeyBoardPopupBuilder addIRobot(IRobot robot) {
		kb.addIRobot(robot);
		return this;
	}

	public KeyBoardPopupBuilder layer(DefaultLayers l) {
		kb.layer(l);
		return this;
	}
	
	public KeyBoardPopupBuilder style(String css) {
		kb.style(css);
		return this;
	}
	
	@Override
	public KeyBoardPopup build() {
		KeyBoardPopup popup = new KeyBoardPopup(kb.build());
		return popup;
	}

}
