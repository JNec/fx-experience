/*
 * Copyright (c), Audatex GmbH, Switzerland. This is UNPUBLISHED
 * PROPRIETARY SOURCE CODE of Audatex GmbH; the contents of this file
 * may not be disclosed to third parties, copied or duplicated in any form, in
 * whole or in part, without the prior written permission of Audatex 
 * GmbH. ALL RIGHTS RESERVED.
 */
package org.comtel.javafx.control;

import org.comtel.javafx.xml.layout.Keyboard.Row.Key;

/**
 * Describe the type here.
 *
 * @author nenad.jankovski
 * @see AXN-
 * @since 29.0 (Apr 22, 2015)
 */
public class KeyboardKeyToStringHelper {
  
  public static String toString(Key key) {
    return "Key [codes=" + key.getCodes() + ", keyLabel=" + key.getKeyLabel() + ", keyIconStyle=" + key.getKeyIconStyle()
        + ", keyLabelStyle=" + key.getKeyLabelStyle() + ", keyWidth=" + key.getKeyWidth() + ", keyEdgeFlags=" + key.getKeyEdgeFlags()
        + ", horizontalGap=" + key.getHorizontalGap() + "]";
  }
}
