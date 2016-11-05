package orTestStandDesktop.ui.DashboardEditor.ControlProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by orb1t_ua on 17.10.16.
 */
public class ControlProperties implements Serializable {
  private Vector<ControlPropertyItem> properties;
  private HashMap<String, Integer> propertiesNames;


  public ControlProperties(){
    properties = new Vector();
    propertiesNames = new HashMap<>();
  }


  public Vector<ControlPropertyItem> getProperties() {
    return this.properties;
  }


  private void addProperty ( ControlPropertyItem item ){
    this.properties.add(item);
    this.propertiesNames.put(item.getName(), this.properties.indexOf(item));
  }


  public void setProperty ( ControlPropertyItem item ) {
	  if ( this.properties.indexOf( item.getName() ) > 0 )
      this.properties.set(this.propertiesNames.get(item.getName()), item);
	  else {
		  this.properties.add( item );
		  this.propertiesNames.put(item.getName(), this.properties.indexOf(item));
	  }
  }

  public void setPropertyValue ( String name, String value ) {
//	  ControlPropertyItem newItm = new ControlPropertyItem( name, this.getPropertyType( name ), ( getPropertyValues( name ).length > 1 ? value + getPropertyRawValue( name ).substring( getPropertyRawValue( name ).indexOf( ";" ) ) : value ) );
		if ( this.propertiesNames.containsKey( name ) )
      this.properties.set( this.propertiesNames.get(name), new ControlPropertyItem( name, this.getPropertyType( name ), ( getPropertyValues( name ).length > 1 ? value + getPropertyRawValue( name ).substring( getPropertyRawValue( name ).indexOf( ";" ) ) : value ) ) );
	  else {
//			this.properties.add( newItm );
//			this.propertiesNames.put( newItm.getName(), this.properties.indexOf( newItm ) );
			System.err.println( "setPropertyValue() for no-existing key" );
		}
  }


  public String toString(){
    StringBuilder stringBuilder = new StringBuilder();
    for ( ControlPropertyItem item : properties){
      stringBuilder.append ( "\n" + properties.indexOf(item) + ". " + item.toString() );
    }
    return stringBuilder.toString();
  }

  public String getPropertyValue(String name) {
    return ( null != properties && null != propertiesNames ) ? this.properties.get ( this.propertiesNames.get( name ) ).getValue() : "0";
  }

  public String getPropertyRawValue(String name) {
    return this.properties.get ( this.propertiesNames.get( name ) ).getRawValue();
  }

  public String[] getPropertyValues(String name) {
    return this.properties.get ( this.propertiesNames.get( name ) ).getValues();
  }

  public ControlPropertyItem getPropertyItem(String name) {
    return this.properties.get ( this.propertiesNames.get( name ) );
  }

  public int size() {
    return this.properties.size();
  }

  public EControlPropertyItemType getPropertyType ( String name ) {
    return this.properties.get ( this.propertiesNames.get( name ) ).getType();
  }
}
