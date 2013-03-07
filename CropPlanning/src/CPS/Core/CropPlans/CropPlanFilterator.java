/* CropPlanFilterator.java - Created: Nov 9, 2009
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

package CPS.Core.CropPlans;

import CPS.Data.CPSDateValidator;
import CPS.Data.CPSPlanting;
import ca.odell.glazedlists.TextFilterator;
import java.util.List;

public class CropPlanFilterator implements TextFilterator<CPSPlanting>{

    public void getFilterStrings( List<String> baseList, CPSPlanting planting ) {
        baseList.add( planting.getCropName() );
        baseList.add( planting.getVarietyName() );
        baseList.add( planting.getFamilyName() );
        baseList.add( planting.getGroups() );
        baseList.add( planting.getLocation() );
        baseList.add( planting.getKeywords() );
        baseList.add( planting.getOtherRequirements() );
        baseList.add( planting.getNotes() );

        // actual dates, formated numerically (ie, 10/11/2009)
        baseList.add( planting.getDateToHarvestActualString() );
        baseList.add( planting.getDateToHarvestPlannedString() );
        baseList.add( planting.getDateToHarvestString() );

        baseList.add( planting.getDateToPlantActualString() );
        baseList.add( planting.getDateToPlantPlannedString() );
        baseList.add( planting.getDateToPlantString() );

        baseList.add( planting.getDateToTPActualString() );
        baseList.add( planting.getDateToTPPlannedString() );
        baseList.add( planting.getDateToTPString() );

        // reformated dates to just show the month name (ie, October)
        baseList.add( CPSDateValidator.format( planting.getDateToHarvestActual(), CPSDateValidator.DATE_FORMAT_JUSTMONTH ));
        baseList.add( CPSDateValidator.format( planting.getDateToHarvestPlanned(), CPSDateValidator.DATE_FORMAT_JUSTMONTH ));
        baseList.add( CPSDateValidator.format( planting.getDateToHarvest(), CPSDateValidator.DATE_FORMAT_JUSTMONTH ));
        baseList.add( CPSDateValidator.format( planting.getDateToPlantActual(), CPSDateValidator.DATE_FORMAT_JUSTMONTH ));
        baseList.add( CPSDateValidator.format( planting.getDateToPlantPlanned(), CPSDateValidator.DATE_FORMAT_JUSTMONTH ));
        baseList.add( CPSDateValidator.format( planting.getDateToPlant(), CPSDateValidator.DATE_FORMAT_JUSTMONTH ));
        baseList.add( CPSDateValidator.format( planting.getDateToTPActual(), CPSDateValidator.DATE_FORMAT_JUSTMONTH ));
        baseList.add( CPSDateValidator.format( planting.getDateToTPPlanned(), CPSDateValidator.DATE_FORMAT_JUSTMONTH ));
        baseList.add( CPSDateValidator.format( planting.getDateToTP(), CPSDateValidator.DATE_FORMAT_JUSTMONTH ));

        baseList.add( planting.getPlantingNotes() );
        baseList.add( planting.getPlantingNotesInherited() );
    }

}
