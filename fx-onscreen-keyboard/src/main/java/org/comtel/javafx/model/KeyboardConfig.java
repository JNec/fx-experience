/*
 * Copyright (c), Audatex GmbH, Switzerland. This is UNPUBLISHED
 * PROPRIETARY SOURCE CODE of Audatex GmbH; the contents of this file
 * may not be disclosed to third parties, copied or duplicated in any form, in
 * whole or in part, without the prior written permission of Audatex 
 * GmbH. ALL RIGHTS RESERVED.
 */
package org.comtel.javafx.model;

import java.util.Locale;

import org.comtel.javafx.control.DefaultLayers;

/**
 * Holds keyboard layout xml configurations.
 * 
 * @author nenad.jankovski
 * @since 29.0 (Feb 24, 2015)
 */
public class KeyboardConfig {

  private Locale locale;
  private DefaultLayers defaultLayer;
  private String qwertyKeyboardXml;
  private String qwertyShiftedKeyboardXml;
  private String symbolKeyboardXml;
  private String symbolShiftedKeyboardXml;
  private String qwertyCtrlKeyboardXml;
  private String numericKeyboardXml;

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public DefaultLayers getDefaultLayer() {
    return defaultLayer;
  }

  public void setDefaultLayer(DefaultLayers defaultLayer) {
    this.defaultLayer = defaultLayer;
  }

  public String getQwertyKeyboardXml() {
    return qwertyKeyboardXml;
  }

  public void setQwertyKeyboardXml(String qwertyKeyboardXml) {
    this.qwertyKeyboardXml = qwertyKeyboardXml;
  }

  public String getQwertyShiftedKeyboardXml() {
    return qwertyShiftedKeyboardXml;
  }

  public void setQwertyShiftedKeyboardXml(String qwertyShiftedKeyboardXml) {
    this.qwertyShiftedKeyboardXml = qwertyShiftedKeyboardXml;
  }

  public String getSymbolKeyboardXml() {
    return symbolKeyboardXml;
  }

  public void setSymbolKeyboardXml(String symbolKeyboardXml) {
    this.symbolKeyboardXml = symbolKeyboardXml;
  }

  public String getSymbolShiftedKeyboardXml() {
    return symbolShiftedKeyboardXml;
  }

  public void setSymbolShiftedKeyboardXml(String symbolShiftedKeyboardXml) {
    this.symbolShiftedKeyboardXml = symbolShiftedKeyboardXml;
  }

  public String getQwertyCtrlKeyboardXml() {
    return qwertyCtrlKeyboardXml;
  }

  public void setQwertyCtrlKeyboardXml(String qwertyCtrlKeyboardXml) {
    this.qwertyCtrlKeyboardXml = qwertyCtrlKeyboardXml;
  }

  public String getNumericKeyboardXml() {
    return numericKeyboardXml;
  }

  public void setNumericKeyboardXml(String numericKeyboardXml) {
    this.numericKeyboardXml = numericKeyboardXml;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((defaultLayer == null) ? 0 : defaultLayer.hashCode());
    result = prime * result + ((locale == null) ? 0 : locale.hashCode());
    result = prime * result + ((numericKeyboardXml == null) ? 0 : numericKeyboardXml.hashCode());
    result = prime * result + ((qwertyCtrlKeyboardXml == null) ? 0 : qwertyCtrlKeyboardXml.hashCode());
    result = prime * result + ((qwertyKeyboardXml == null) ? 0 : qwertyKeyboardXml.hashCode());
    result = prime * result + ((qwertyShiftedKeyboardXml == null) ? 0 : qwertyShiftedKeyboardXml.hashCode());
    result = prime * result + ((symbolKeyboardXml == null) ? 0 : symbolKeyboardXml.hashCode());
    result = prime * result + ((symbolShiftedKeyboardXml == null) ? 0 : symbolShiftedKeyboardXml.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    KeyboardConfig other = (KeyboardConfig) obj;
    if (defaultLayer != other.defaultLayer)
      return false;
    if (locale == null) {
      if (other.locale != null)
        return false;
    } else if (!locale.equals(other.locale))
      return false;
    if (numericKeyboardXml == null) {
      if (other.numericKeyboardXml != null)
        return false;
    } else if (!numericKeyboardXml.equals(other.numericKeyboardXml))
      return false;
    if (qwertyCtrlKeyboardXml == null) {
      if (other.qwertyCtrlKeyboardXml != null)
        return false;
    } else if (!qwertyCtrlKeyboardXml.equals(other.qwertyCtrlKeyboardXml))
      return false;
    if (qwertyKeyboardXml == null) {
      if (other.qwertyKeyboardXml != null)
        return false;
    } else if (!qwertyKeyboardXml.equals(other.qwertyKeyboardXml))
      return false;
    if (qwertyShiftedKeyboardXml == null) {
      if (other.qwertyShiftedKeyboardXml != null)
        return false;
    } else if (!qwertyShiftedKeyboardXml.equals(other.qwertyShiftedKeyboardXml))
      return false;
    if (symbolKeyboardXml == null) {
      if (other.symbolKeyboardXml != null)
        return false;
    } else if (!symbolKeyboardXml.equals(other.symbolKeyboardXml))
      return false;
    if (symbolShiftedKeyboardXml == null) {
      if (other.symbolShiftedKeyboardXml != null)
        return false;
    } else if (!symbolShiftedKeyboardXml.equals(other.symbolShiftedKeyboardXml))
      return false;
    return true;
  }

}
