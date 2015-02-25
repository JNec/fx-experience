package org.comtel.javafx.xml.layout;

public enum KbLayoutXMLEnum {
  KB_LAYOUT_NUMERIC_XML("kb-layout-numeric.xml"),
  KB_LAYOUT_SYM_SHIFT_XML("kb-layout-sym-shift.xml"),
  KB_LAYOUT_SYM_XML("kb-layout-sym.xml"),
  KB_LAYOUT_CTRL_XML("kb-layout-ctrl.xml"),
  KB_LAYOUT_SHIFT_XML("kb-layout-shift.xml"),
  KB_LAYOUT_XML("kb-layout.xml");
  
  private String fileName;
  
  private KbLayoutXMLEnum(String fileName) {
    this.fileName = fileName;
  }
  
  @Override
  public String toString() {
    return fileName;
  }
  
}