package CPS.Data;


/**
 * Crop - data structure to hold information about a crop
 *        NOT for a planting of a crop, but just for the crop
 */

import java.util.*;

public class CPSCrop {

   private CropDatum<Integer> cropID;
   
    private CropDatum<String> cropName;
    private CropDatum<String> cropDescription;
    
    private CropDatum<String> varName;
    
    private CropDatum<String> famName;

    private CropDatum<Boolean> ds;
    private CropDatum<Boolean> tp;
    
    private CropDatum<Integer> maturityDays;
    private CropDatum<String> flat;
    
    private CropDatum<Integer> inRowSpacing;
    private CropDatum<Integer> rowSpacing;
    private CropDatum<Integer> rowsPerBed;


    public CPSCrop () {
       
       cropID = new CropDatum<Integer>("Unique ID");
       
	cropName = new CropDatum<String>("Crop name");
        cropDescription = new CropDatum<String>("Crop description");
        
        varName = new CropDatum<String>("Variety");
        
        famName = new CropDatum<String>("Family name");
        
        ds = new CropDatum<Boolean>( "Direct Seed?" );
        tp = new CropDatum<Boolean>( "Transplant?" );
        maturityDays = new CropDatum<Integer>("Days to maturity");
        flat = new CropDatum<String>("Flat size");
	
        inRowSpacing = new CropDatum<Integer>("In-Row spacing");
        rowSpacing = new CropDatum<Integer>("Between rows");
        rowsPerBed = new CropDatum<Integer>("Rows Per Bed");
        
    }

    public <T> T getter( CPSDatum<T> d ) {
       return d.getDatum();
    }
    
    public <T> void setter( CPSDatum<T> d, T v ) {
       d.setDatum(v);
    }
    
   public String getCropName() {
      // I don't understand why this cast is necessary
      return (String) getter( cropName );
   }

   public void setCropName( String s ) {
      setter( cropName, s );
   }

   public String getCropDescription() {
      return (String) getter( cropDescription );
   }

   public void setCropDescription( String s ) {
      setter( cropDescription, s );
   }

   public String getVarietyName() {
      return (String) varName.getDatum();
   }
   
   public void setVarietyName( String s ) {
      setter( varName, s );
   }
   
   public String getFamName() {
      return (String) famName.getDatum();
   }

   public void setFamName( String e) {
      famName.setDatum( e );
   }

   public boolean getDS() {
      return ds.getDatumAsBoolean();
   }
   public void setDS( boolean b ) {
      ds.setDatum( new Boolean(b) );
   }
   
   public boolean getTP() {
      return tp.getDatumAsBoolean();
   }
   public void setTP( boolean b ) {
      tp.setDatum( new Boolean( b ) );
   }
   
   public int getMaturityDays() {
      return maturityDays.getDatumAsInt();
   }

   public void setMaturityDays( int i ) {
      maturityDays.setDatum( new Integer( i ));
   }

   public CropDatum<String> getFlat() {
      return flat;
   }

   public void setFlat(CropDatum<String> flat) {
      this.flat = flat;
   }

   public CropDatum<Integer> getInRowSpacing() {
      return inRowSpacing;
   }

   public void setInRowSpacing(CropDatum<Integer> inRowSpacing) {
      this.inRowSpacing = inRowSpacing;
   }

   public CropDatum<Integer> getRowSpacing() {
      return rowSpacing;
   }

   public void setRowSpacing(CropDatum<Integer> rowSpacing) {
      this.rowSpacing = rowSpacing;
   }

   public CropDatum<Integer> getRowsPerBed() {
      return rowsPerBed;
   }

   public void setRowsPerBed(CropDatum<Integer> rowsPerBed) {
      this.rowsPerBed = rowsPerBed;
   }

   public int getID() { return cropID.getDatumAsInt(); }
   public void setID( int i ) {
      cropID.setDatum( new Integer( i ));
   } 
    
}
