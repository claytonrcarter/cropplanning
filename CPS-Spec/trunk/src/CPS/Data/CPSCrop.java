package CPS.Data;


/**
 * Crop - data structure to hold information about a crop
 *        NOT for a planting of a crop, but just for the crop
 */

import java.util.*;

public class CPSCrop {

    private CropDatum<String> cropName;
    private CropDatum<String> cropDescription;
    private List varieties;
    

    public List extraCropData;

    int maturityDays;
//     enum SeedingMethod { DS, TP, BOTH, UNKNOWN };
//     SeedingMethod seedingMethod;
    int weeksToTP;
    String flat;
    String flatSuccession[];
    
    int inRowSpacing;
    int rowSpacing;
    int rowsPerBed;

    double yieldPerFoot;
    double yieldPerRow;
    double yieldPerBed;

    public CPSCrop () {
	cropName = new CropDatum<String>( "Crop name", "" );
        cropDescription = new CropDatum( "Crop description", "" );
        flat = "";
	varieties = null;
	weeksToTP = inRowSpacing = rowSpacing = rowsPerBed = 0;
	// seedingMethod = null;
	yieldPerFoot = yieldPerRow = yieldPerBed = 0.0;
    }

    
    
}
