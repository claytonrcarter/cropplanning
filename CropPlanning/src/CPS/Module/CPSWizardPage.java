/* CPSWizardPage.java - created: Dec 5, 2008
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

package CPS.Module;

import javax.swing.BoxLayout;
import org.netbeans.spi.wizard.WizardPage;

/**
 *
 * @author crcarter
 */
public abstract class CPSWizardPage extends WizardPage {

   /** This wizard is run before modules are initialized. */
   public final static int WIZ_TYPE_PRE_INIT = 0;
   /** This wizard is run after modules are initialized by before they are run */
   public final static int WIZ_TYPE_POST_INIT = 1;

   protected int wizardType;

   public CPSWizardPage( String title, String desc, int type ) {
      super( title, desc );
      setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ));
      wizardType = type;
   }

   // all subclasses MUST implement the following method
   // public static String getDescription();

   public abstract void finishWizard( CPSGlobalSettings globSet );

   /**
    * Returns the type of this wizard page.
    * @return Either CPSWizardPage.WIZ_TYPE_PRE_INIT or CPSWizardPage.WIZ_TYPE_PRE_INIT
    */
   public int getWizardType() { return wizardType; }

}
