/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CPS.Settings;

import java.util.ArrayList;

/**
 *
 * @author Clayton
 */
public class CPSModuleSettings<T> extends ArrayList<T> {

   private String moduleName;
   
   public CPSModuleSettings( String name ) {
      super();
      setModuleName( name );     
   }
   
   @Override
   public boolean add( T arg0 ) {
      return super.add( arg0 );
   }
  
   
   
   public String getModuleName() { return moduleName; }
   public void setModuleName( String moduleName ) { this.moduleName = moduleName; }

}
