package CPS.Data;


/**
 * Crop - data structure to hold information about a crop
 *        NOT for a planting of a crop, but just for the crop
 */

import java.util.*;

public class Crop {

    private CropDatum<String> cropName;
    private CropDatum<String> cropDescription;
    private List varieties;
    
    /* A better place for this would be the end of the instance variable
     * decls */
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

    public Crop () {
	cropName = cropDescription = new CropDatum();
        flat = "";
	varieties = null;
	weeksToTP = inRowSpacing = rowSpacing = rowsPerBed = 0;
	// seedingMethod = null;
	yieldPerFoot = yieldPerRow = yieldPerBed = 0.0;
    }

    public void addVariety ( Variety v ) {}

}
