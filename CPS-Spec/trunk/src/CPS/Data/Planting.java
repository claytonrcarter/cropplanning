package CPS.Data;

import java.sql.Date;

/**
 * Planting - information about a particular planting of a crop and/or
 *            variety
 */

public class Planting {

    CPSCrop crop;
    Variety variety;
    
    //FieldLocation location;

    float beds;

    Date plannedSeedingDate;
    Date plannedTPDate;
    Date plannedHarvestDate;

    Date actualSeedindDate;
    Date actualTPDate;
    Date actualHarvestDate;

}
