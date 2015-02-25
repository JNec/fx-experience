/*
 * Copyright (c), Audatex GmbH, Switzerland. This is UNPUBLISHED
 * PROPRIETARY SOURCE CODE of Audatex GmbH; the contents of this file
 * may not be disclosed to third parties, copied or duplicated in any form, in
 * whole or in part, without the prior written permission of Audatex 
 * GmbH. ALL RIGHTS RESERVED.
 */
package org.comtel.javafx.model;

import java.io.IOException;
import java.util.Locale;

import org.comtel.javafx.control.DefaultLayers;
import org.comtel.javafx.xml.KeyboardLayoutHandler;
import org.comtel.javafx.xml.layout.KbLayoutXMLEnum;
import org.comtel.javafx.xml.layout.Keyboard;
import org.slf4j.LoggerFactory;

/**
 * Describe the type here.
 *
 * @author nenad.jankovski
 * @see AXN-
 * @since 29.0 (Feb 25, 2015)
 */
public class ClasspathKeyboardConfigProvider implements KeyboardConfigProvider {
  
  private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ClasspathKeyboardConfigProvider.class);
  
  private final String BASE_PATH = "/xml/";
  KeyboardLayoutHandler handler = new KeyboardLayoutHandler();
  
  public ClasspathKeyboardConfigProvider() {
  }

  /* (non-Javadoc)
   * @see org.comtel.javafx.model.KeyboardConfigProvider#getLayout(java.util.Locale, org.comtel.javafx.control.DefaultLayers, org.comtel.javafx.xml.layout.KbLayoutXMLEnum)
   */
  @Override
  public Keyboard getLayout(Locale loc, DefaultLayers layer, KbLayoutXMLEnum layoutXMLEnum) throws IOException {
    
    String xmlPath = BASE_PATH + layer.toString().toLowerCase(Locale.ENGLISH)
        + (loc.getLanguage().equals("en") ? "/" : "/" + loc.getLanguage() + "/");
    logger.info("use embedded layouts path: {}", xmlPath);
    
    return handler.getLayoutFromClasspath(xmlPath + layoutXMLEnum);
  }

}
