/* CropDBFilterator.java - Created: Nov 9, 2009
 * Copyright (C) 2009 Clayton Carter
 *
 * This file is part of the project "Crop Planning Software".  For more
 * information:
 *    website: http://cropplanning.googlecode.com
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
 */

package CPS.Core.CropDB;

import CPS.Data.CPSCrop;
import CPS.Data.CPSDateValidator;
import CPS.Data.CPSPlanting;
import ca.odell.glazedlists.TextFilterator;
import java.util.List;

public class CropDBFilterator implements TextFilterator<CPSCrop>{

    public void getFilterStrings( List<String> baseList, CPSCrop crop ) {

        baseList.add( crop.getCropName() );
        baseList.add( crop.getVarietyName() );
        baseList.add( crop.getBotanicalName() );
        baseList.add( crop.getFamilyName() );
        baseList.add( crop.getCropDescription() );
        baseList.add( crop.getNotes() );
        baseList.add( crop.getTPFlatSize() );
        baseList.add( crop.getTPPlantNotes() );
        baseList.add( crop.getDSPlantNotes() );
        baseList.add( crop.getCropYieldUnit() );
        baseList.add( crop.getGroups() );
        baseList.add( crop.getKeywords() );
        baseList.add( crop.getOtherRequirements() );
        baseList.add( crop.getNotes() );

    }
}
