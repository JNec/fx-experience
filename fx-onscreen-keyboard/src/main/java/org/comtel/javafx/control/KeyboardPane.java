package org.comtel.javafx.control;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.transform.Scale;

import org.comtel.javafx.event.KeyButtonEvent;
import org.comtel.javafx.model.ClasspathKeyboardConfigProvider;
import org.comtel.javafx.model.KeyboardConfig;
import org.comtel.javafx.model.KeyboardConfigProvider;
import org.comtel.javafx.robot.IRobot;
import org.comtel.javafx.xml.KeyboardLayoutHandler;
import org.comtel.javafx.xml.layout.KbLayoutXMLEnum;
import org.comtel.javafx.xml.layout.Keyboard;
import org.slf4j.LoggerFactory;

public class KeyboardPane extends Region implements StandardKeyCode, EventHandler<KeyButtonEvent> {

  private final static org.slf4j.Logger logger = LoggerFactory.getLogger(KeyboardPane.class);
  private final KeyboardLayoutHandler handler = new KeyboardLayoutHandler();
  private KeyboardConfigProvider provider = new ClasspathKeyboardConfigProvider(handler);

  private final String DEFAULT_CSS = "/css/KeyboardButtonStyle.css";
  // private final String DEFAULT_FONT_URL = "/font/FontKeyboardFX.ttf";

  private final StringProperty keyBoardStyleProperty = new SimpleStringProperty(DEFAULT_CSS);

  private Region qwertyKeyboardPane;
  private Region qwertyShiftedKeyboardPane;
  private Region symbolKeyboardPane;
  private Region symbolShiftedKeyboardPane;
  private Region qwertyCtrlKeyboardPane;
  private Region numericKeyboardPane;

  private final BooleanProperty symbolProperty = new SimpleBooleanProperty(false);
  private final BooleanProperty shiftProperty = new SimpleBooleanProperty(false);
  private final BooleanProperty ctrlProperty = new SimpleBooleanProperty(false);

  private final BooleanProperty spaceKeyMoveProperty = new SimpleBooleanProperty(true);

  private final DoubleProperty scaleOffsetProperty = new SimpleDoubleProperty(0.2);

  private final DoubleProperty scaleProperty = new SimpleDoubleProperty(1.0);
  private final DoubleProperty minScaleProperty = new SimpleDoubleProperty(0.7);
  private final DoubleProperty maxScaleProperty = new SimpleDoubleProperty(5.0);

  private final ObjectProperty<DefaultLayers> layerProperty = new SimpleObjectProperty<>(DefaultLayers.DEFAULT);
  // private final ObjectProperty<Path> layerPathProperty = new SimpleObjectProperty<>();

  private final ObjectProperty<Locale> localeProperty = new SimpleObjectProperty<>(Locale.getDefault());
  private final ObjectProperty<Locale> currentLocale = new SimpleObjectProperty<>();

  private EventHandler<? super Event> closeEventHandler;

  private double mousePressedX;
  private double mousePressedY;

  private final ObservableList<IRobot> robots = FXCollections.observableArrayList();
  private final ObservableList<KeyboardConfig> keyboardCondifgs = FXCollections.observableArrayList();

  public KeyboardPane() {
    setId("key-background");
    setFocusTraversable(false);
  }

  // @Override
  public String getUserAgentStylesheet() {
    return keyBoardStyleProperty.get();
  }

  public void load() throws MalformedURLException, IOException, URISyntaxException {

    getStylesheets().add(keyBoardStyleProperty.get());

    // This should be configurable according to the desired KeyboardLayer
    setLayoutLocale(localeProperty.get());

    // This should be configurable
    setKeyboardLayer(KeyboardLayer.QWERTY, localeProperty.get());

    if (scaleProperty.get() != 1.0) {
      getTransforms().setAll(new Scale(scaleProperty.get(), scaleProperty.get(), 1, 0, 0, 0));
    }

    registerListener();
  }

  private void registerListener() {

    shiftProperty.addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
        if (ctrlProperty.get()) {
          logger.warn("ignore in ctrl mode");
          return;
        }
        setKeyboardLayer(symbolProperty.get() ? KeyboardLayer.SYMBOL : KeyboardLayer.QWERTY, localeProperty.get());
      }
    });

    ctrlProperty.addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean ctrl) {
        if (ctrl) {
          setKeyboardLayer(KeyboardLayer.CTRL, localeProperty.get());
        } else {
          setKeyboardLayer(symbolProperty.get() ? KeyboardLayer.SYMBOL : KeyboardLayer.QWERTY, localeProperty.get());
        }
      }
    });

    symbolProperty.addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
        if (ctrlProperty.get()) {
          logger.warn("ignore in ctrl mode");
          return;
        }
        setKeyboardLayer(arg2 ? KeyboardLayer.SYMBOL : KeyboardLayer.QWERTY, localeProperty.get());
      }
    });

    scaleProperty.addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> arg0, Number o, Number s) {
        if (o != s) {
          getTransforms().setAll(new Scale(s.doubleValue(), s.doubleValue(), 1, 0, 0, 0));
        }
      }
    });

    // setOnKeyPressed(new EventHandler<KeyEvent>() {
    //
    // public void handle(KeyEvent e) {
    // // e.consume();
    // switch (e.getCode()) {
    // case SHIFT:
    // isShiftDown.set(isShiftDown.get());
    // break;
    // // case CONTROL:
    // // setCtrlDown(!isCtrlDown);
    // // break;
    // // case ALT:
    // // setSymbolDown(!isSymbolDown);
    // // break;
    // }
    // }
    // });
  }

  public void setLayoutLocale(Locale local) {
    // cache last
    if (currentLocale.get() != null && currentLocale.get().getLanguage().equals(local.getLanguage())) {
      getChildren().clear();
      getChildren().addAll(
          qwertyKeyboardPane,
          qwertyShiftedKeyboardPane,
          qwertyCtrlKeyboardPane,
          symbolKeyboardPane,
          symbolShiftedKeyboardPane);

      for (javafx.scene.Node node : getChildren()) {
        node.setVisible(false);
      }      
      return;
    }
    currentLocale.set(local);
    localeProperty.set(local);
    logger.debug("try to set keyboard local: {}", local);
    
    try {
      getChildren().clear();
      qwertyKeyboardPane = createKeyboardPane(provider.getLayout(local, layerProperty().get(), KbLayoutXMLEnum.KB_LAYOUT_XML));
      qwertyShiftedKeyboardPane = createKeyboardPane(provider.getLayout(local, layerProperty().get(), KbLayoutXMLEnum.KB_LAYOUT_SHIFT_XML));
      qwertyCtrlKeyboardPane = createKeyboardPane(provider.getLayout(local, layerProperty().get(), KbLayoutXMLEnum.KB_LAYOUT_CTRL_XML));
      symbolKeyboardPane = createKeyboardPane(provider.getLayout(local, layerProperty().get(), KbLayoutXMLEnum.KB_LAYOUT_SYM_XML));
      symbolShiftedKeyboardPane = createKeyboardPane(provider.getLayout(local, layerProperty().get(), KbLayoutXMLEnum.KB_LAYOUT_SYM_SHIFT_XML));
    
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    
    getChildren().addAll(
        qwertyKeyboardPane,
        qwertyShiftedKeyboardPane,
        qwertyCtrlKeyboardPane,
        symbolKeyboardPane,
        symbolShiftedKeyboardPane);

    for (javafx.scene.Node node : getChildren()) {
      node.setVisible(false);
    }
    return;

  }

  public void setNumericOnlyLayout(Locale local) {
    Keyboard keyboard = null;
    try {      
        keyboard = provider.getLayout(local, DefaultLayers.DEFAULT, KbLayoutXMLEnum.KB_LAYOUT_NUMERIC_XML);      
    } catch (IOException e) {
      e.printStackTrace();
    }

    getChildren().clear();
    
    if (numericKeyboardPane == null) {
      numericKeyboardPane = createKeyboardPane(keyboard);
    }
    getChildren().add(numericKeyboardPane);
  }

  public Map<Locale, Path> getAvailableLocales() {

    Map<Locale, Path> localList = new HashMap<>();
    localList.put(new Locale("de"), null);
    localList.put(new Locale("ru"), null);

    for (KeyboardConfig config : keyboardCondifgs) {
      localList.put(config.getLocale(), null);
    }
    return localList;
  }

  public void setKeyboardLayer(KeyboardLayer layer) {
    setKeyboardLayer(layer, localeProperty.get());
  }

  public void setKeyboardLayer(KeyboardLayer layer, Locale local) {
    final Region pane;
    switch (layer) {
      case QWERTY:
        setLayoutLocale(local);
        pane = shiftProperty.get() ? qwertyShiftedKeyboardPane : qwertyKeyboardPane;
        break;
      case SYMBOL:
        setLayoutLocale(local);
        pane = shiftProperty.get() ? symbolShiftedKeyboardPane : symbolKeyboardPane;
        break;
      case CTRL:
        setLayoutLocale(local);
        pane = qwertyCtrlKeyboardPane;
        break;
      case NUMBER:
        setLayoutLocale(local);
        pane = qwertyCtrlKeyboardPane;
        break;
      case NUMERIC_ONLY:
        setNumericOnlyLayout(local);
        pane = numericKeyboardPane;
        break;
      default:
        setLayoutLocale(local);
        pane = qwertyKeyboardPane;
        break;
    }

    for (javafx.scene.Node node : getChildren()) {
      node.setVisible(false);
    }
    pane.setVisible(true);

  }

  private Region createKeyboardPane(Keyboard layout) {

    GridPane rPane = new GridPane();
    rPane.setAlignment(Pos.CENTER);
    // pane.setPrefSize(600, 200);

    if (layout.getVerticalGap() != null) {
      rPane.setVgap(layout.getVerticalGap());
    }
    rPane.setId("key-background-row");

    int defaultKeyWidth = 10;
    if (layout.getKeyWidth() != null) {
      defaultKeyWidth = layout.getKeyWidth();
    }

    int defaultKeyHeight = 35;
    if (layout.getKeyHeight() != null) {
      defaultKeyHeight = layout.getKeyHeight();
    }

    int rowIdx = 0;
    for (Keyboard.Row row : layout.getRow()) {
      int colIdx = 0;
      GridPane colPane = new GridPane();
      colPane.setId("key-background-column");
      // colPane.setVgap(20);
      // colPane.setPrefWidth(Region.USE_COMPUTED_SIZE);

      RowConstraints rc = new RowConstraints();
      rc.setPrefHeight(defaultKeyHeight);

      if (row.getRowEdgeFlags() != null) {
        if (row.getRowEdgeFlags().equals("bottom")) {
          rc.setValignment(VPos.BOTTOM);
        }
        if (row.getRowEdgeFlags().equals("top")) {
          rc.setValignment(VPos.TOP);
        }
      }
      int rowWidth = 0;
      for (Keyboard.Row.Key key : row.getKey()) {

        if (key.getHorizontalGap() != null) {
          colPane.setHgap(key.getHorizontalGap());
        } else if (layout.getHorizontalGap() != null) {
          colPane.setHgap(layout.getHorizontalGap());
        }
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.SOMETIMES);
        cc.setFillWidth(true);
        cc.setPrefWidth(key.getKeyWidth() != null ? key.getKeyWidth() : defaultKeyWidth);

        if (key.getCodes() == null || key.getCodes().isEmpty()) {
          // add placeholder
          Pane placeholder = new Pane();
          colPane.add(placeholder, colIdx, 0);
          colPane.getColumnConstraints().add(cc);

          logger.trace("placeholder: {}", cc);
          colIdx++;
          rowWidth += cc.getPrefWidth();
          continue;
        }

        MultiKeyButton button = new MultiKeyButton(scaleProperty, getStylesheets());
        button.setFocusTraversable(false);
        button.setOnShortPressed(this);
        button.setCache(true);

        button.setMinHeight(10);
        button.setPrefHeight(defaultKeyHeight);
        button.setPrefWidth(defaultKeyWidth);
        button.setMaxWidth(defaultKeyWidth * 100);

        String[] codes = key.getCodes().split(",");
        if (codes.length > 0 && !codes[0].isEmpty()) {
          button.setKeyCode(parseInt(codes[0]));
        }
        if (codes.length > 1) {
          for (String code : codes) {
            int keyCode = parseInt(code);
            if (keyCode != button.getKeyCode()) {
              button.addExtKeyCode(keyCode);
            }
          }
        }

        if (key.getKeyLabelStyle() != null && key.getKeyLabelStyle().startsWith(".")) {
          for (String style : key.getKeyLabelStyle().split(";")) {
            button.getStyleClass().add(style.substring(1));
          }
        }

        if (button.getKeyCode() == LOCALE_SWITCH) {
          button.addExtKeyCode(
              LOCALE_SWITCH,
              Locale.ENGLISH.getLanguage().toUpperCase(Locale.ENGLISH),
              button.getStyleClass());
          for (Locale l : getAvailableLocales().keySet()) {
            button.addExtKeyCode(LOCALE_SWITCH, l.getLanguage().toUpperCase(Locale.ENGLISH), button.getStyleClass());
          }
        }

        if (key.getKeyIconStyle() != null && key.getKeyIconStyle().startsWith(".")) {
          logger.trace("Load css style: {}", key.getKeyIconStyle());
          Label icon = new Label();
          // icon.setSnapToPixel(true);
          // do not reduce css shape quality JavaFX8
          // icon.setCacheShape(false);

          for (String style : key.getKeyIconStyle().split(";")) {
            icon.getStyleClass().add(style.substring(1));
          }
          icon.setMaxSize(40, 40);
          button.setContentDisplay(ContentDisplay.CENTER);
          button.setGraphic(icon);

        } else if (key.getKeyIconStyle() != null && key.getKeyIconStyle().startsWith("@")) {

          InputStream is = KeyboardPane.class.getResourceAsStream(key.getKeyIconStyle().replace("@", "/") + ".png");
          Image image = new Image(is);
          if (!image.isError()) {
            button.setGraphic(new ImageView(image));
          } else {
            logger.error("Image: {} not found", key.getKeyIconStyle());
          }
        }

        button.setText(key.getKeyLabel());

        if (button.isContextAvailable() && button.getGraphic() == null) {
          button.getStyleClass().add("extend-style");
        }

        if (key.getKeyEdgeFlags() != null) {
          if (key.getKeyEdgeFlags().equals("right")) {
            cc.setHalignment(HPos.RIGHT);
            button.setAlignment(Pos.BASELINE_RIGHT);
          } else if (key.getKeyEdgeFlags().equals("left")) {
            cc.setHalignment(HPos.LEFT);
            button.setAlignment(Pos.BASELINE_LEFT);
          } else {
            cc.setHalignment(HPos.CENTER);
          }
        } else {
          cc.setHalignment(HPos.CENTER);
          button.setAlignment(Pos.BASELINE_CENTER);
        }

        switch (button.getKeyCode()) {
          case java.awt.event.KeyEvent.VK_SPACE:
            button.setOnMouseMoved(new MouseMovedHandler());
            button.setOnMouseDragged(new MouseDraggedHandler());

            break;
          case BACK_SPACE:
          case DELETE:
            button.setOnLongPressed(new EventHandler<Event>() {

              @Override
              public void handle(Event e) {
                e.consume();
                sendToComponent((char) 97, true);
                sendToComponent((char) java.awt.event.KeyEvent.VK_DELETE, ctrlProperty.get());
              }
            });
            break;
        }

        colPane.add(button, colIdx, 0);
        colPane.getColumnConstraints().add(cc);

        logger.trace("btn: {} {}", button.getText(), cc);
        colIdx++;
        rowWidth += cc.getPrefWidth();
      }
      logger.trace("row[{}] - {}", rowIdx, rowWidth);
      colPane.getRowConstraints().add(rc);
      // colPane.setGridLinesVisible(true);
      rPane.add(colPane, 0, rowIdx);
      rowIdx++;
    }

    logger.trace("-----end pane-----");
    return rPane;
  }

  private static int parseInt(String i) {
    return i.startsWith("0x") ? Integer.parseInt(i.substring(2), 16) : Integer.parseInt(i);
  }

  public boolean isShifted() {
    return shiftProperty.get();
  }

  public boolean isSymbol() {
    return symbolProperty.get();
  }

  public boolean isCtrl() {
    return ctrlProperty.get();
  }

  @Override
  public void handle(KeyButtonEvent event) {
    event.consume();
    KeyButtonEvent kbEvent = event;
    if (!kbEvent.getEventType().equals(KeyButtonEvent.SHORT_PRESSED)) {
      logger.warn("ignore non short pressed events");
      return;
    }
    KeyButton kb = (KeyButton) kbEvent.getSource();
    switch (kb.getKeyCode()) {
      case SHIFT_DOWN:
        // switch shifted
        shiftProperty.set(!shiftProperty.get());
        break;
      case SYMBOL_DOWN:
        // switch sym / qwerty
        symbolProperty.set(!symbolProperty.get());
        break;
      case CLOSE:
        if (closeEventHandler == null) {
          System.exit(0);
        } else {
          closeEventHandler.handle(new KeyButtonEvent(KeyButtonEvent.ANY));
        }
        break;
      case TAB:
        sendToComponent((char) java.awt.event.KeyEvent.VK_TAB, true);
        break;
      case BACK_SPACE:
        sendToComponent((char) java.awt.event.KeyEvent.VK_BACK_SPACE, true);
        break;
      case DELETE:
        sendToComponent((char) java.awt.event.KeyEvent.VK_DELETE, true);
        break;
      case CTRL_DOWN:
        // switch ctrl
        ctrlProperty.set(!ctrlProperty.get());
        break;
      case LOCALE_SWITCH:
        Locale l = new Locale(kb.getText());
        try {         
          setLayoutLocale(l);
        } catch (RuntimeException e) {
          logger.error(e.getMessage(), e);
        }
        if (ctrlProperty.get()) {
          ctrlProperty.set(false);
        } else if (symbolProperty.get()) {
          symbolProperty.set(false);
        } else {
          setKeyboardLayer(KeyboardLayer.QWERTY, l);
        }
        break;
      case ENTER:
        sendToComponent((char) java.awt.event.KeyEvent.VK_ENTER, true);
        break;
      case ARROW_UP:
        sendToComponent((char) java.awt.event.KeyEvent.VK_UP, true);
        break;
      case ARROW_DOWN:
        sendToComponent((char) java.awt.event.KeyEvent.VK_DOWN, true);
        break;
      case ARROW_LEFT:
        sendToComponent((char) java.awt.event.KeyEvent.VK_LEFT, true);
        break;
      case ARROW_RIGHT:
        sendToComponent((char) java.awt.event.KeyEvent.VK_RIGHT, true);
        break;
      case UNDO:
        sendToComponent((char) java.awt.event.KeyEvent.VK_Z, true);
        break;
      case REDO:
        sendToComponent((char) java.awt.event.KeyEvent.VK_Y, true);
        break;

      default:
        // logger.debug(java.awt.event.KeyEvent.getKeyText(kb.getKeyCode()));
        if (kb.getKeyCode() > -1) {
          sendToComponent((char) kb.getKeyCode(), ctrlProperty.get());
        } else {
          logger.debug("unknown key code: {}", kb.getKeyCode());
          sendToComponent((char) kb.getKeyCode(), true);
        }
        break;
    }

  }

  /**
   * send keyEvent to iRobot implementation
   * 
   * @param ch
   * @param ctrl
   */
  private void sendToComponent(char ch, boolean ctrl) {

    logger.trace("send ({})", ch);

    if (ctrl) {
      switch (Character.toUpperCase(ch)) {
        case java.awt.event.KeyEvent.VK_MINUS:
          if (scaleProperty.get() > minScaleProperty.get()) {
            scaleProperty.set(scaleProperty.get() - scaleOffsetProperty.get());
          }
          return;
        case 0x2B:
          if (scaleProperty.get() < maxScaleProperty.get()) {
            scaleProperty.set(scaleProperty.get() + scaleOffsetProperty.get());
          }
          return;
      }
    }

    if (robots.isEmpty()) {
      logger.error("no robot handler available");
      return;
    }
    for (IRobot robot : robots) {
      robot.sendToComponent(this, ch, ctrl);
    }

  }

  public void addRobotHandler(IRobot robot) {
    robots.add(robot);
  }

  public void removeRobotHandler(IRobot robot) {
    robots.remove(robot);
  }

  public void addKeyboardConfig(KeyboardConfig config) {
    keyboardCondifgs.add(config);
  }

  public void removeKeyboardConfig(KeyboardConfig config) {
    keyboardCondifgs.remove(config);
  }

  public void setOnKeyboardCloseButton(EventHandler<? super Event> value) {
    closeEventHandler = value;
  }

  public void setLayer(DefaultLayers dl) {
    layerProperty.set(dl);
  }

  public ReadOnlyObjectProperty<DefaultLayers> layerProperty() {
    return layerProperty;
  }

  public void setLocale(Locale locale) {
    localeProperty.set(locale);
  }

  public ReadOnlyObjectProperty<Locale> localeProperty() {
    return localeProperty;
  }

  public void setScale(double scale) {
    scaleProperty.set(scale);
  }

  public ReadOnlyDoubleProperty scaleProperty() {
    return ReadOnlyDoubleWrapper.readOnlyDoubleProperty(scaleProperty);
  }

  public void setScaleOffset(double offset) {
    scaleOffsetProperty.set(offset);
  }

  public ReadOnlyDoubleProperty scaleOffsetProperty() {
    return ReadOnlyDoubleWrapper.readOnlyDoubleProperty(scaleOffsetProperty);
  }

  public ReadOnlyDoubleProperty minScaleProperty() {
    return ReadOnlyDoubleWrapper.readOnlyDoubleProperty(minScaleProperty);
  }

  public void setMinimumScale(double min) {
    minScaleProperty.set(min);
  }

  public ReadOnlyDoubleProperty maxScaleProperty() {
    return ReadOnlyDoubleWrapper.readOnlyDoubleProperty(maxScaleProperty);
  }

  public void setMaximumScale(double max) {
    maxScaleProperty.set(max);
  }

  public StringProperty keyBoardStyleProperty() {
    return keyBoardStyleProperty;
  }

  public void setKeyBoardStyle(String css) {
    keyBoardStyleProperty.set(css);
  }

  public BooleanProperty spaceKeyMoveProperty() {
    return spaceKeyMoveProperty;
  }

  public void setSpaceKeyMove(boolean m) {
    spaceKeyMoveProperty.set(m);
  }

  class MouseMovedHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
      // on
      // Double.isNaN(getScene().getWindow().getX())
      // init window position
      if (spaceKeyMoveProperty.get()) {
        mousePressedX = getScene().getWindow().getX() - event.getScreenX();
        mousePressedY = getScene().getWindow().getY() - event.getScreenY();
      }
    }
  }

  class MouseDraggedHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent mouseEvent) {
      if (spaceKeyMoveProperty.get()) {
        getScene().getWindow().setX(mouseEvent.getScreenX() + mousePressedX);
        getScene().getWindow().setY(mouseEvent.getScreenY() + mousePressedY);
      }
    }
  }

}
