package org.comtel.samples;

import java.util.Locale;

import javax.swing.ButtonGroup;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.comtel.javafx.control.DefaultLayers;
import org.comtel.javafx.control.KeyBoardPopup;
import org.comtel.javafx.control.KeyBoardPopupBuilder;
import org.comtel.javafx.robot.RobotFactory;

public class MainDemo extends Application {

	private Animation fadeAnimation;

	private KeyBoardPopup popup;

	@Override
	public void start(Stage stage) {

		stage.setTitle("FX Keyboard (" + System.getProperty("javafx.runtime.version") + ")");
		stage.setResizable(true);

		popup = KeyBoardPopupBuilder.create().initScale(1.0).initLocale(Locale.ITALIAN).layer(DefaultLayers.NUMBLOCK).addIRobot(RobotFactory.createFXRobot()).build();
		popup.getKeyBoard().setOnKeyboardCloseButton(event -> setPopupVisible(false, null));
		
		VBox pane = new VBox(20);

		Button okButton = new Button("Ok");
		okButton.setDefaultButton(true);
		
		Label l = new Label("Label");

		Button cancelButton = new Button("Cancel");
		cancelButton.setCancelButton(true);

		CheckBox spaceKeyMoveCb = new CheckBox("Movable");
		spaceKeyMoveCb.setSelected(true);
		popup.getKeyBoard().spaceKeyMoveProperty().bind(spaceKeyMoveCb.selectedProperty());
		
		TextField tf = new TextField("");
		tf.textProperty().addListener(new ChangeListener<String>() {
		  /* (non-Javadoc)
		   * @see javafx.beans.value.ChangeListener#changed(javafx.beans.value.ObservableValue, java.lang.Object, java.lang.Object)
		   */
		  @Override
		  public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    if ("".equals(newValue)) {
		      l.setText("");
		      return;
		    }
		    l.setText((int)newValue.toCharArray()[newValue.length()-1]+"");
		    
		  }
                });
		
		pane.getChildren().add(new ToolBar(okButton, cancelButton, spaceKeyMoveCb, l));
		pane.getChildren().add(new Label("Text1"));
		pane.getChildren().add(tf);
		pane.getChildren().add(new TextArea(""));
		pane.getChildren().add(new Label("Password"));
		pane.getChildren().add(new PasswordField());
		
		

		// pane.getChildren().add(KeyBoardBuilder.create().addIRobot(RobotFactory.createFXRobot()).build());

		Scene scene = new Scene(pane, 600, 400);

		// add keyboard scene listener to all text components
		scene.focusOwnerProperty().addListener((value, n1, n2) -> {
			if (n2 != null && n2 instanceof TextInputControl) {
				setPopupVisible(true, (TextInputControl) n2);
			} else {
				setPopupVisible(false, null);
			}
		});

		// add double click listener
		stage.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			if (event.getClickCount() == 2) {
				Node node = scene.getFocusOwner();
				if (node != null && node instanceof TextInputControl) {
					setPopupVisible(true, (TextInputControl) node);
				}
			}
		});

		stage.setOnCloseRequest((event) -> System.exit(0));

		stage.setScene(scene);
		popup.show(stage);
		stage.show();

	}

	private void setPopupVisible(final boolean b, final TextInputControl textNode) {

		Platform.runLater(() -> {
			if (b && textNode != null) {
				Rectangle2D textNodeBounds = new Rectangle2D(textNode.getScene().getWindow().getX() + textNode.getLocalToSceneTransform().getTx(), textNode.getScene().getWindow().getY()
						+ textNode.getLocalToSceneTransform().getTy(), textNode.getWidth(), textNode.getHeight());

				Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
				if (textNodeBounds.getMinX() + popup.getWidth() > screenBounds.getMaxX()) {
					popup.setX(screenBounds.getMaxX() - popup.getWidth());
				} else {
					popup.setX(textNodeBounds.getMinX());
				}

				if (textNodeBounds.getMaxY() + popup.getHeight() > screenBounds.getMaxY()) {
					popup.setY(textNodeBounds.getMinY() - popup.getHeight() + 20);
				} else {
					popup.setY(textNodeBounds.getMaxY() + 40);
				}
			}

			if (fadeAnimation != null) {
				fadeAnimation.stop();
			}
			if (!b) {
				popup.hide();
				return;
			}
			if (popup.isShowing()) {
				return;
			}
			popup.getKeyBoard().setOpacity(.0);

			FadeTransition fade = new FadeTransition(Duration.seconds(.5), popup.getKeyBoard());
			fade.setToValue(b ? 1. : .8);
			// fade.setOnFinished((event) -> fadeAnimation = null);

			ScaleTransition scale = new ScaleTransition(Duration.seconds(.5), popup.getKeyBoard());
			scale.setToX(b ? 1. : .8);
			scale.setToY(b ? 1. : .8);

			ParallelTransition tx = new ParallelTransition(fade, scale);
			fadeAnimation = tx;
			tx.play();
			if (b && !popup.isShowing()) {
				popup.show(popup.getOwnerWindow());
			}

		});
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
