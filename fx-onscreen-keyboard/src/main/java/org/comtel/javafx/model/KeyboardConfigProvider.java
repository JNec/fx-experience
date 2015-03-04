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
import java.util.Locale;

import org.comtel.javafx.control.DefaultLayers;
import org.comtel.javafx.xml.layout.KbLayoutXMLEnum;
import org.comtel.javafx.xml.layout.Keyboard;

/**
 * Describe the type here.
 *
 * @author nenad.jankovski
 * @see AXN-
 * @since 29.0 (Feb 25, 2015)
 */
public interface KeyboardConfigProvider {
  public Keyboard getLayout(Locale loc, DefaultLayers layer, KbLayoutXMLEnum layoutXMLEnum) throws IOException;
  public Collection<Locale> getAvailableLocales();
  
  /**
   * Checks if the wrapped configuration is valid.
   * @return true if the configuration can provide actual Kyeboard objects.
   */
  public boolean isValid();
}
