/*
 * Copyright (c), Audatex GmbH, Switzerland. This is UNPUBLISHED
 * PROPRIETARY SOURCE CODE of Audatex GmbH; the contents of this file
 * may not be disclosed to third parties, copied or duplicated in any form, in
 * whole or in part, without the prior written permission of Audatex 
 * GmbH. ALL RIGHTS RESERVED.
 */
package org.comtel.javafx.model.cache;

import org.comtel.javafx.control.DefaultLayers;
import org.comtel.javafx.xml.layout.KbLayoutXMLEnum;

/**
 * Intended to be used as a key in a Map for caching purposes.
 *
 * @author nenad.jankovski
 * @see AXN-
 * @since 29.0 (Feb 26, 2015)
 */
public class KeyboardConfigCacheItem {

  private final String language;
  private final DefaultLayers layer;
  private final KbLayoutXMLEnum layoutXMLEnum;
  
  public KeyboardConfigCacheItem(String language, DefaultLayers layer, KbLayoutXMLEnum layoutXMLEnum) {
    this.language = language;
    this.layer = layer;
    this.layoutXMLEnum = layoutXMLEnum;
  }

  public String getLanguage() {
    return language;
  }

  public DefaultLayers getLayer() {
    return layer;
  }

  public KbLayoutXMLEnum getLayoutXMLEnum() {
    return layoutXMLEnum;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((language == null) ? 0 : language.hashCode());
    result = prime * result + ((layer == null) ? 0 : layer.hashCode());
    result = prime * result + ((layoutXMLEnum == null) ? 0 : layoutXMLEnum.hashCode());
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
    KeyboardConfigCacheItem other = (KeyboardConfigCacheItem) obj;
    if (language == null) {
      if (other.language != null)
        return false;
    } else if (!language.equals(other.language))
      return false;
    if (layer != other.layer)
      return false;
    if (layoutXMLEnum != other.layoutXMLEnum)
      return false;
    return true;
  }
  
  
}
