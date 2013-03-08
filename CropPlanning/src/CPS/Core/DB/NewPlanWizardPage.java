package CPS.Core.DB;

import CPS.Data.CPSCrop;
import CPS.Data.CPSDateValidator;
import CPS.Data.CPSPlanting;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSGlobalSettings;
import CPS.Module.CPSWizardPage;
import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import org.netbeans.api.wizard.WizardDisplayer;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage;

class NewPlanWizardPage extends CPSWizardPage {

   static final String PAGE_NEW_PLAN = "newPlan";
   static final String SETTING_PLAN_NAME = "planName";
   static final String SETTING_PLAN_YEAR = "planYear";
   private JTextField tfldPlanName;
   private JSpinner spnYear;
   private GregorianCalendar tempCal;

   private CPSDataModel dm = null;

   public NewPlanWizardPage ( CPSDataModel dm ) {
      super( PAGE_NEW_PLAN, getDescription(), CPSWizardPage.WIZ_TYPE_POST_INIT );

      this.dm = dm;
      
      setLongDescription( getDescription() );
      JPanel jpl;
      JLabel intro =
             new JLabel( "<html><center><table width=300><tr><td>" + 
              "<b><font size=large>" + getDescription() + "</font></b> " +
              "<p><p>" +
              "This step will create your first crop plan so that you can start " +
              "entering information right away. " + "<p>" +
              "Two ideas: 1) create a \"real\" plan now and get right to work, or <br>" +
              "2) create a \"dummy\" plan to play around with while you familiaze " +
              "yourself with the program. " +
              "<p><p>" +
              "You can create, rename and delete crop plans at any time by " +
              "clicking the \"Change Plans\" button in the \"CropPlans\" window. " +
              "<p><p>" +
              "If you do not want to create a crop plan right now, simply press \"Cancel\"." +
              "</td></tr></table></center></html>" );
      add( intro );
      tfldPlanName = new JTextField( 15 );
      tfldPlanName.setName( SETTING_PLAN_NAME );
      jpl = new JPanel();
      jpl.add( new JLabel( "New plan name:" ) );
      jpl.add( tfldPlanName );
      add( Box.createVerticalGlue() );
      add( jpl );
      spnYear = new JSpinner();
      tempCal = new GregorianCalendar();
      SpinnerDateModel spnMod = new SpinnerDateModel();
      spnMod.setCalendarField( Calendar.YEAR );
      spnYear.setModel( spnMod );
      spnYear.setEditor( new JSpinner.DateEditor( spnYear, "yyyy" ) );
      spnYear.setPreferredSize( new Dimension( 70, spnYear.getPreferredSize().height ) );
      spnYear.setValue( new Date() );
      spnYear.setName( SETTING_PLAN_YEAR );
      jpl = new JPanel();
      jpl.add( new JLabel( "Plan is for (year):" ) );
      jpl.add( spnYear );
      add( jpl );
      add( Box.createVerticalGlue() );
   }

   @Override
   protected String validateContents ( Component component, Object event ) {
      if ( tfldPlanName.getText().trim().length() == 0 ) {
         return "You must enter a plan name.";
      }
      // else no problems
      return null;
   }

   @Override
   public void finishWizard ( CPSGlobalSettings globSet ) {
      if ( dm != null ) {
         tempCal.setTime( (Date) spnYear.getValue() );
         dm.createCropPlan( tfldPlanName.getText(),
                            tempCal.get( Calendar.YEAR ),
                            "" );

         // only if Cukes DNE
         CPSCrop c = dm.getCropInfo("Cucumbers");

         if ( c.getID() == -1 ) {

           c = new CPSCrop();
           c.setCropName("Cucumbers");
           c.setMaturityDays(50);
           c.setTransplanted(Boolean.TRUE);
           c.setTPRowsPerBed(1);
           c.setTPSpaceInRow(24);
           c.setTPFlatSize("72");
           c.setTPTimeInGH(3);
           dm.createCrop(c);

           c = new CPSCrop();
           c.setCropName("Cucumbers");
           c.setVarietyName("Marketmore");
           c.setMaturityDays(55);
           dm.createCrop(c);

           CPSPlanting p = new CPSPlanting();
           p.setCropName(c.getCropName());
           p.setVarietyName(c.getVarietyName());
           p.setDateToTPPlanned( "6/1/" +
                                 new SimpleDateFormat( "yyyy" ).format( new Date() ));
           p.setBedsToPlant(1);
           dm.createPlanting( tfldPlanName.getText(), p );
         }

      }
   }

   public static String getDescription () {
      return "Create New Crop Plan";
   }

   // for testing only
   public static void main ( String[] args ) {
      Wizard wiz = WizardPage.createWizard( new WizardPage[]{ new NewPlanWizardPage( null ) },
                                                              new WizardResultProducer() {

                                                                 public Object finish ( Map settings ) throws WizardException {
                                                                    System.out.println( settings );
                                                                    return settings;
                                                                 }

                                                                 public boolean cancel ( Map settings ) {
                                                                    return true;
                                                                 }
                                                              } );

      WizardDisplayer.showWizard( wiz );
   }
}
