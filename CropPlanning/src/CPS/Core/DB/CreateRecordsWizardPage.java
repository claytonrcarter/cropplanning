package CPS.Core.DB;

import CPS.Module.CPSGlobalSettings;
import CPS.Module.CPSWizardPage;
import java.awt.Component;
import java.util.Map;
import javax.swing.Box;
import javax.swing.JLabel;
import org.netbeans.api.wizard.WizardDisplayer;
import org.netbeans.spi.wizard.Wizard;
import org.netbeans.spi.wizard.WizardException;
import org.netbeans.spi.wizard.WizardPage;

class CreateRecordsWizardPage extends CPSWizardPage {

   static final String PAGE_NEW_PLAN = "createRecords";

   public CreateRecordsWizardPage () {
      super( PAGE_NEW_PLAN, getDescription(), CPSWizardPage.WIZ_TYPE_POST_INIT );

      setLongDescription( getDescription() );
      JLabel intro =
             new JLabel( "<html><body style='width:300px; text-align:right'><center>" +
              "To get you started, we have created two simple entries in " +
              "the CropDB and one planting in your new crop plan.<p><p>" +
              "An entry for cucmbers was created with some basic data " +
              "and another entry was created to represent a variety of cucumbers. " +
              "Some of the fields are different so that you can see " +
              "how data inheritance can work for varieties of a crop." + "<p><p>" +
              "Your new crop plan will have an entry for a planting of cucumbers which " +
              "will inherit some data from the CropDB (the blue fields) and calculate " +
              "others (the green ones.)  Feel free to alter or delete these entries." +
              "<p>" );
      add( intro );
      add( Box.createVerticalGlue() );
   }

   @Override
   protected String validateContents ( Component c, Object e ) { return null; }

   @Override
   public void finishWizard ( CPSGlobalSettings globSet ) {}

   public static String getDescription () {
      return "Crops and Plantings";
   }

   // for testing only
   public static void main ( String[] args ) {
      Wizard wiz = WizardPage.createWizard( new WizardPage[]{ new CreateRecordsWizardPage() },
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
