/* FirstRunWizard.java - created: Dec 2, 2008
 * Copyright (C) 2008 Clayton Carter
 *
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: https://github.com/claytonrcarter/cropplanning
 *    email:   cropplanning@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package CPS.Core.UI;

import CPS.Data.CPSDateValidator;
import CPS.Module.CPSGlobalSettings;
import CPS.Module.CPSModule;
import CPS.Module.CPSWizardPage;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JLabel;
import org.netbeans.api.wizard.WizardDisplayer;
import org.netbeans.spi.wizard.*;
import org.netbeans.spi.wizard.WizardPage.*;


public class WizardManager {

   private ArrayList<CPSWizardPage> pages;

   /** Stores the type of this wizard (from CPSWizardPage.WIZ_TYPE_*) Default type of wizard is WIZ_TYPE_PRE_INIT */
   private int wizardType;
   private boolean cancelled = false;

   protected final static String PAGE_INTRO = "intro";
   protected final static String PAGE_OUT_DIR = "outDir";
   protected final static String PAGE_GEN_SET = "genSettings";

   protected final static String SETTING_OUT_DIR = "outDir";
   protected final static String SETTING_FARM_NAME = "farmName";
   protected final static String SETTING_BED_LENGTH = "bedLength";
   protected final static String SETTING_LAST_FROST = "lastFrost";
   protected final static String SETTING_FIRST_FROST = "firstFrost";

   /**
    * Create a new WizardManager to be run PRE_INIT.
    */
   public WizardManager () {
      this( CPSWizardPage.WIZ_TYPE_PRE_INIT );
   }

   public WizardManager( int type ) {
      wizardType = type;
      pages = new ArrayList<CPSWizardPage>();
      if ( wizardType == CPSWizardPage.WIZ_TYPE_PRE_INIT )
         pages.add( new IntroWizardPage() );
   }
   
   public void addWizard( CPSWizardPage wp ) {
      if ( wp != null )
         pages.add( wp );
   }
   
   public void addWizards( CPSWizardPage[] wp ) {
      if ( wp != null )
         pages.addAll( java.util.Arrays.asList( wp ) );
   }

   protected Wizard buildWizard () {

      WizardPage[] wp = new WizardPage[ pages.size() ];
      int i = 0;
      for ( CPSWizardPage p : pages ) {
         System.out.println( "adding wizard: " + p.getLongDescription() );
         wp[i++] = p;
      }
      return WizardPage.createWizard( wp,
                                      new WizardResultProducer() {

                                         public Object finish ( Map settings ) throws WizardException {
                                            
                                            return settings;

//                                            System.out.println( settings );
//
//                                            String[] items = new String[settings.size()];
//
//                                            int i = 0;
//
//                                            for ( Object e : settings.entrySet() ) {
//                                               items[i++] = ((Map.Entry) e).getKey() + ": " +
//                                                            ((Map.Entry) e).getValue();
//                                            }
//
//                                            return Summary.create( items, settings );
                                         }

                                         public boolean cancel ( Map settings ) {
                                            cancelled = true;
                                            return true;
                                         }
                                      } );

   }

   public boolean showWizard( CPSGlobalSettings globSet ) {

      // show the wizard and save the settings
      Map wizardSettings = (Map) WizardDisplayer.showWizard( buildWizard() );

      if ( cancelled )
         CPSModule.debug( "FirstRunWizard", "User pressed cancel button in wizard." );
      else
         for ( CPSWizardPage p : pages )
            p.finishWizard( globSet );
      
      return ! cancelled;
   }

   // For testing
    public static void main(String[] args) {
       Map wizardSettings = (Map) WizardDisplayer.showWizard( new WizardManager().buildWizard() );
    }

}
/*
 *
 * Inner Classes
 *
 */

class IntroWizardPage extends CPSWizardPage {

   public IntroWizardPage () {
      super( WizardManager.PAGE_INTRO, getDescription(), CPSWizardPage.WIZ_TYPE_PRE_INIT );

      setLongDescription( getDescription() );

      setPreferredSize( new Dimension( 400, 400 ) );

      JLabel intro = new JLabel( "<html><center><table width=300><tr><td>" +
                                 "<b><font size=large>Welcome to Crop Planning Software</font></b> " +
                                 "<p><p>It appears that this is your first time running this program. " +
                                 "The following steps will help you set up the program before you start using it. "+
                                 "<p><p>If this is not your first time with the program, then it seems that your settings " +
                                 "(but not necessarily your data!) have been lost. " +
                                 "The following steps will get you back up and running." +
                                 "</td></tr></table></center></html>" );
      add( intro );
   }

   public static String getDescription() { return "Introduction"; }

   @Override
   public void finishWizard ( CPSGlobalSettings globSet ) { /* nothing to do for this one */ }

}
