package xorDashboardDesktop.ui.DashboardEditor.ControlProperties;

import java.io.Serializable;

/**
 * Created by orb1t_ua on 17.10.16.
 */
public class ControlPropertyItem extends Object implements Serializable {
  private String name;
  private EControlPropertyItemType type;
  private String value;

  public ControlPropertyItem(String name, EControlPropertyItemType type, String value){
    this.name = name;
    this.type = type;
    this.value = value;
  }

  public String toString(){
    return this.name + "[" + this.type + "]:" + this.value;
  }

  public String getName() {
    return this.name;
  }

  public EControlPropertyItemType getType() {
    return this.type;
  }

  public String getRawValue() {
    return this.value;
  }

  public String[] getValues(){
    return value.split(";");
  }
  public String getValue(int num){
    return value.split(";")[num];
  }

  public String getValue() {
    return this.getValue(0);
  }

  public void setValue ( String value ) {
    this.value = value;
  }

}
