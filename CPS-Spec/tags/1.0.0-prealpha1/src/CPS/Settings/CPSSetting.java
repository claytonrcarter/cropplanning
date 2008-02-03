/* Copyright (C) 2008 Clayton Carter */

package CPS.Settings;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class to encapsulate a single program or module setting.
 * 
 * @author Clayton
 * @see CPSModuleSettings
 */
public class CPSSetting<T> {

   public static final int SETTING_TYPE_INT = 0;
   public static final int SETTING_TYPE_FLOAT = 1;
   public static final int SETTING_TYPE_STRING = 2;
   public static final int SETTING_TYPE_BOOLEAN = 3;
   public static final int SETTING_TYPE_LIST = 4;
   public static final int SETTING_TYPE_FILE = 5;
   
   private String settingName;
   private String settingDescription;
   private T settingValue;
   private ArrayList<T> validValues;
   private int settingType;
   
   public CPSSetting( String name, T value ) {
      setName( name );
      setValue( value );
   }
   
   public CPSSetting( String name, String desc, T value, int type ) {
      this( name, value );
      setDescription(desc);
      setSettingType(type);
   }
   
   public CPSSetting( String name, String desc, T value, T[] valid ) {
      this( name, desc, value, CPSSetting.SETTING_TYPE_LIST );
      setValidValues( new ArrayList( Arrays.asList( valid ) ));
   }
   
   public String getName() { return settingName; }
   public void setName( String settingName ) { this.settingName = settingName; }
   
   public String getDescription() { return settingDescription; }
   public void setDescription( String settingDescription ) { this.settingDescription = settingDescription; }
   
   public T getValue() { return settingValue; }
   public void setValue( T settingValue ) { this.settingValue = settingValue; }
   
   public ArrayList<T> getValidValues() { return validValues; }
   public void setValidValues( ArrayList<T> validValues ) { this.validValues = validValues; }
   
   public int getSettingType() { return settingType; }
   public void setSettingType( int settingType ) { this.settingType = settingType; }
   
   
   
}
