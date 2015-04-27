/*
 * Copyright (c), Audatex GmbH, Switzerland. This is UNPUBLISHED
 * PROPRIETARY SOURCE CODE of Audatex GmbH; the contents of this file
 * may not be disclosed to third parties, copied or duplicated in any form, in
 * whole or in part, without the prior written permission of Audatex 
 * GmbH. ALL RIGHTS RESERVED.
 */
package org.comtel.javafx.model;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.comtel.javafx.control.DefaultLayers;
import org.comtel.javafx.model.cache.KeyboardConfigCacheItem;
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
  private final KeyboardLayoutHandler handler;
  private final Map<KeyboardConfigCacheItem, Keyboard> cache = new HashMap<KeyboardConfigCacheItem, Keyboard>();

  public ClasspathKeyboardConfigProvider(KeyboardLayoutHandler handler) {
    this.handler = handler;
  }

  /* (non-Javadoc)
   * @see org.comtel.javafx.model.KeyboardConfigProvider#getLayout(java.util.Locale, org.comtel.javafx.control.DefaultLayers, org.comtel.javafx.xml.layout.KbLayoutXMLEnum)
   */
  @Override
  public Keyboard getLayout(Locale loc, DefaultLayers layer, KbLayoutXMLEnum layoutXMLEnum) throws IOException {

    String xmlPath = BASE_PATH + layer.toString().toLowerCase(Locale.ENGLISH)
        + (loc.getLanguage().equals("en") ? "/" : "/" + loc.getLanguage() + "/");
    logger.debug("use embedded layouts path: {}", xmlPath);

    // check cache
    return getItem(loc.getLanguage(), layer, layoutXMLEnum, xmlPath);
  }

  /**
   * @param language
   * @param layer
   * @param layoutXMLEnum
   * @return
   * @throws IOException
   */
  private Keyboard getItem(String language, DefaultLayers layer, KbLayoutXMLEnum layoutXMLEnum, String xmlPath)
      throws IOException {

    KeyboardConfigCacheItem cacheItem = new KeyboardConfigCacheItem(language, layer, layoutXMLEnum);

    for (Entry<KeyboardConfigCacheItem, Keyboard> entry : cache.entrySet()) {
      if (entry.getKey().equals(cacheItem)) {
        logger.debug("Getting keyboard from cache: " + language + " - " + layer + " - " + layoutXMLEnum);
        return entry.getValue();
      }
    }
    Keyboard kb = handler.getLayoutFromClasspath(xmlPath + layoutXMLEnum);
    cache.put(cacheItem, kb);

    return kb;
  }

  /* (non-Javadoc)
   * @see org.comtel.javafx.model.KeyboardConfigProvider#isValid()
   */
  @Override
  public boolean isValid() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.comtel.javafx.model.KeyboardConfigProvider#getAvailableLocales()
   */
  @Override
  public Collection<Locale> getAvailableLocales() {
    return Collections.emptyList();
  }

}
