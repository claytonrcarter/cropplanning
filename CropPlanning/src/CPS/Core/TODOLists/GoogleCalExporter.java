/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CPS.Core.TODOLists;

import CPS.Data.CPSComparators;
import CPS.Data.CPSComplexPlantingFilter;
import CPS.Data.CPSPlanting;
import CPS.Module.CPSDataModel;
import CPS.Module.CPSGlobalSettings;
import CPS.ModuleManager;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.GroupingList;
import com.google.gdata.client.GoogleAuthTokenFactory.UserToken;
import com.google.gdata.client.GoogleService.CaptchaRequiredException;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.batch.BatchStatus;
import com.google.gdata.data.batch.BatchUtils;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.data.calendar.ColorProperty;
import com.google.gdata.data.calendar.HiddenProperty;
import com.google.gdata.data.calendar.SelectedProperty;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.prefs.Preferences;

/**
 *
 * @author crcarter
 */
public class GoogleCalExporter {

  // The base URL for a user's calendar metafeed (needs a username appended).
  private static final String METAFEED_URL_BASE = "https://www.google.com/calendar/feeds/";

  // The string to add to the user's metafeedUrl to access the owncalendars
  // feed.
  private static final String OWNCALENDARS_FEED_URL_SUFFIX = "/owncalendars/full";

  // The string to add to the user's metafeedUrl to access the event feed for
  // their primary calendar.
  private static final String EVENT_FEED_URL_SUFFIX = "/private/full";


  // The URL for the owncalendars feed of the specified user.
  // (e.g. http://www.googe.com/feeds/calendar/jdoe@gmail.com/owncalendars/full)
  private static URL owncalendarsFeedUrl = null;

  // The URL for the event feed of the specified user's primary calendar.
  // (e.g. http://www.googe.com/feeds/calendar/jdoe@gmail.com/private/full)
  private static URL eventFeedUrl = null;


  private static final String CALENDAR_TITLE_PREFIX = "Crop Plan: ";

  // The HEX representation of red, blue and green
  private static final String RED = "#A32929";
  private static final String BLUE = "#2952A3";
  private static final String GREEN = "#0D7813";

  /**
   * Utility classes should not have a public or default constructor.
   */
  protected GoogleCalExporter() {}

  /**
   * Creates a new secondary calendar using the owncalendars feed.
   * 
   * @param service An authenticated CalendarService object.
   * @param cropPlan Name of crop plan this calendar will represent
   * @return The newly created calendar entry.
   * @throws IOException If there is a problem communicating with the server.
   * @throws ServiceException If the service is unable to handle the request.
   */
  private static CalendarEntry createCalendar( CalendarService service,
                                              String cropPlan )
      throws IOException, ServiceException {

    System.out.println( "Creating calendar: " + CALENDAR_TITLE_PREFIX + cropPlan );

    // Create the calendar
    CalendarEntry calendar = new CalendarEntry();
    calendar.setTitle(new PlainTextConstruct( CALENDAR_TITLE_PREFIX + cropPlan ));
    calendar.setSummary(new PlainTextConstruct(
        "Contains events representing crop plan: " + cropPlan ));
    calendar.setHidden( HiddenProperty.FALSE );
    calendar.setSelected( SelectedProperty.TRUE );
    calendar.setColor( new ColorProperty( GREEN ) );
    
    // Insert the calendar
    return service.insert(owncalendarsFeedUrl, calendar);

  }


  private static CalendarEntry findCalendarForCropPlan( CalendarService service,
                                                       String cropPlan )
      throws IOException, ServiceException {

    CalendarFeed resultFeed = service.getFeed( owncalendarsFeedUrl,
                                               CalendarFeed.class);

    for ( CalendarEntry entry : resultFeed.getEntries() ) {
      if ( entry.getTitle().getPlainText().equals( CALENDAR_TITLE_PREFIX + cropPlan ) )
        return entry;
    }

    // calendar not found
    return null;

  }

  /**
   * Helper method to create either single-instance or recurring events. For
   * simplicity, some values that might normally be passed as parameters (such
   * as author name, email, etc.) are hard-coded.
   *
   * @param service An authenticated CalendarService object.
   * @param eventTitle Title of the event to create.
   * @param eventContent Text content of the event to create.
   * @param recurData Recurrence value for the event, or null for
   *        single-instance events.
   * @param isQuickAdd True if eventContent should be interpreted as the text of
   *        a quick add event.
   * @param wc A WebContent object, or null if this is not a web content event.
   * @return The newly-created CalendarEventEntry.
   * @throws ServiceException If the service is unable to handle the request.
   * @throws IOException Error communicating with the server.
   */
  private static CalendarEventEntry createEvent( String eventTitle,
                                                String eventContent,
                                                Date   eventDate )
          throws ServiceException, IOException {
    CalendarEventEntry myEntry = new CalendarEventEntry();

    myEntry.setTitle(new PlainTextConstruct(eventTitle));
    myEntry.setContent(new PlainTextConstruct(eventContent));
    myEntry.setQuickAdd(false);
    
    // If a recurrence was requested, add it. Otherwise, set the
    // time (the current date and time) and duration (30 minutes)
    // of the event.
    DateTime eDate = new DateTime( eventDate, TimeZone.getDefault() );
    eDate.setDateOnly( true );

    When eventTimes = new When();
    eventTimes.setStartTime(eDate);
    eventTimes.setEndTime(eDate);
    myEntry.addTime(eventTimes);

    return myEntry;
    // Send the request and receive the response:
//    return service.insert(eventFeedUrl, myEntry);
  }


  
  private static boolean isAuthenticated( CalendarService service,
                                         URL feedURL ) {
    
    try {
      service.getFeed( feedURL, CalendarFeed.class );
      return true;
    } catch ( Exception e ) {
      System.err.println( "Authentication failed with message: " + e.getMessage() );
      return false;
    }

  }


  public static void main(String[] args) {

    System.out.println( "Opening DM..." );
    CPSGlobalSettings.setDebug( false );
    CPSGlobalSettings.setTempOutputDir( "/Users/crcarter/Documents/Crop Plans/cps-devel" );
    ModuleManager mm = new ModuleManager();
    CPSDataModel dm = mm.getDM();
    System.out.println( "Initing DM..." );
    dm.init();


    String planName = dm.getListOfCropPlans().get(0);
    System.out.println( "Getting crop plan: " + planName );
    EventList<CPSPlanting> planList = GlazedLists.eventList( dm.getCropPlan( planName ) );

    GroupingList<CPSPlanting> groupList =
            new GroupingList<CPSPlanting>( planList,
                                           new CPSComparators.DatePlantComparator() );



    Preferences prefs = Preferences.userNodeForPackage( GoogleCalExporter.class );
    String userName = prefs.get( "GOOGLE_USERNAME", "" );


    try {
      owncalendarsFeedUrl = new URL(METAFEED_URL_BASE + userName + OWNCALENDARS_FEED_URL_SUFFIX );
    } catch ( MalformedURLException e ) {
      e.printStackTrace();
    }


    // Create CalendarService
    CalendarService service = new CalendarService("CropPlanning-GCal-v0.1");

    // attempt to authenticate w/ stored auth token
    System.out.println( "Logging in with auth token for account " + userName );
    service.setUserToken( prefs.get( "GOOGLE_AUTH_TOKEN", null ));


    while ( ! isAuthenticated( service, owncalendarsFeedUrl ) ) {

      System.out.println( "Logging in with user credentials" );
      GoogleCalLoginDialog dia = new GoogleCalLoginDialog(  );
      dia.setVisible( true );

      System.out.println( "Entered " + dia.getEmail() + " : " + new String( dia.getPassword() ) );

      // bail
      if ( dia.isCancelled() ) {
        System.exit( 0 );
        return;
      }


      // if they enter a different username, then rebuild the feed URL
      if ( ! userName.equalsIgnoreCase( dia.getEmail() ) ) {
        userName = dia.getEmail();
        try {
          owncalendarsFeedUrl = new URL(METAFEED_URL_BASE + userName + OWNCALENDARS_FEED_URL_SUFFIX );
        } catch ( MalformedURLException e ) {
          e.printStackTrace();
        }
      }


      char[] p = dia.getPassword();


      // login w/ user name and password
      try {
        service.setUserCredentials( userName, new String( p ) );
      } catch ( CaptchaRequiredException e ) {

        // no support for captcha now
        System.err.println( "Captcha error: " + e.getMessage() );

  //      System.out.println("Please visit " + e.getCaptchaUrl());
  //      System.out.print("Answer to the challenge? ");
  //      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  //      String answer = in.readLine();
  //      service.setUserCredentials(email, password, e.getCaptchaToken(), answer);

      } catch (AuthenticationException e ) {

        System.err.println( "Invalid credentials: " + e.getMessage() );
        
      }

      // clear out the password array for safety
      Arrays.fill( p, '0' );

    }

    
    prefs.put( "GOOGLE_USERNAME", userName );
    prefs.put( "GOOGLE_AUTH_TOKEN",
               ((UserToken) service.getAuthTokenFactory()
                                   .getAuthToken()).getValue());


    HashMap<String, CalendarEventEntry> planMap = new HashMap<String, CalendarEventEntry>();
    HashMap<String, CalendarEventEntry> feedMap = new HashMap<String, CalendarEventEntry>();


    try {

      // look for calendar
      CalendarEntry cal = findCalendarForCropPlan( service, planName );

      // if not found, create calendar
      if ( cal == null ) {
        cal = createCalendar( service, planName );
      } else {
        System.out.println( "Calendar exists." );
      }

      // if found, get ID/href/etc
      String[] s = cal.getEditLink().getHref().split( "/" );
      String calID = s[ s.length - 1 ].replaceAll( "%40", "@" );

      // Create URL for cropplan calendar
      eventFeedUrl = new URL( METAFEED_URL_BASE + calID + EVENT_FEED_URL_SUFFIX );
      

      CalendarEventFeed batchRequest = new CalendarEventFeed();

//****************************************************************************//
//      Planting Events
//****************************************************************************//
      for ( Iterator<List<CPSPlanting>> it = groupList.iterator(); it.hasNext(); ) {
        List<CPSPlanting> dateGroup = it.next();

        StringBuilder dsTitle = new StringBuilder( "Seed in Field: " );
        StringBuilder tpTitle = new StringBuilder( "Seed in GH: " );
        String dsDelim = "";
        String tpDelim = "";
        String dsContent = "";
        String tpContent = "";
        Date d = dateGroup.get(0).getDateToPlant();

        GroupingList<CPSPlanting> cropGroupList =
                new GroupingList<CPSPlanting>( GlazedLists.eventList( dateGroup ),
                                               new CPSComparators.CropNameComparator() );

        // iterate over the grouped crops
        for ( Iterator<List<CPSPlanting>> it2 = cropGroupList.iterator(); it2.
                hasNext(); ) {
          List<CPSPlanting> cropGroup = it2.next();

          // we assume that each group will be all DS or all TP
          if ( cropGroup.get(0).isDirectSeeded() ) {

            dsTitle.append( dsDelim ).append(cropGroup.get(0).getCropName());
            dsDelim = ", ";

            for ( CPSPlanting p : cropGroup ) {
              dsContent += p.getCropName() + ": " +
                           p.getVarietyName() + " - " +
                           p.getBedsToPlantString() + " beds\n";
            }

          } else {

            tpTitle.append( tpDelim ).append(cropGroup.get(0).getCropName());
            tpDelim = ", ";

            for ( CPSPlanting p : cropGroup ) {
              tpContent += p.getCropName() + ": " +
                           p.getVarietyName() + " - " +
                           p.getFlatsNeededString() + " x " +
                           p.getFlatSize() + "\n";
            }

          }

        }


        CalendarEventEntry dsee = null;
        CalendarEventEntry tpee = null;
        if ( ! dsContent.equals( "" ) ) {
          dsee = createEvent( dsTitle.toString(), dsContent, d );
          planMap.put( "ds"+d.getTime(), dsee );

//          BatchUtils.setBatchId( dsee, "ds"+d.getTime() ); // use the time as batch id
//          BatchUtils.setBatchOperationType( dsee, BatchOperationType.INSERT);
//          batchRequest.getEntries().add( dsee );
        }
        if ( ! tpContent.equals( "" ) ) {
          tpee = createEvent( tpTitle.toString(), tpContent, d );
          planMap.put( "gh"+d.getTime(), tpee);
          
//          BatchUtils.setBatchId( tpee, "gh"+d.getTime() ); // use the time as batch id
//          BatchUtils.setBatchOperationType( tpee, BatchOperationType.INSERT);
//          batchRequest.getEntries().add( tpee );
        }

      }


//****************************************************************************//
//      Transplant Events
//****************************************************************************//

      // whittle it down to just TP plantings, then group by TP date
      FilterList<CPSPlanting> fl = new FilterList<CPSPlanting>( planList );
      fl.setMatcher( CPSComplexPlantingFilter.transplantedFilter() );
      groupList = new GroupingList<CPSPlanting>( fl,
                                                 new CPSComparators.DateTPComparator() );
      // iterate over TP dates
      for ( Iterator<List<CPSPlanting>> it = groupList.iterator(); it.hasNext(); ) {
        List<CPSPlanting> dateGroup = it.next();

        StringBuilder tpTitle = new StringBuilder( "TP in Field: " );
        String tpDelim = "";
        String tpContent = "";
        Date d = dateGroup.get(0).getDateToTP();

        GroupingList<CPSPlanting> cropGroupList =
                new GroupingList<CPSPlanting>( GlazedLists.eventList( dateGroup ),
                                               new CPSComparators.CropNameComparator() );

        // iterate over the grouped crops
        for ( Iterator<List<CPSPlanting>> it2 = cropGroupList.iterator(); it2.
                hasNext(); ) {
          List<CPSPlanting> cropGroup = it2.next();

          // we assume that each group will be all DS or all TP
          String c = "";

          // now iterate over the actual plantings
          for ( CPSPlanting p : cropGroup ) {
            c += p.getCropName() + ": " +
                 p.getVarietyName() + " - " +
                 p.getBedsToPlantString() + " beds\n";
          }

          tpTitle.append( tpDelim ).append(cropGroup.get(0).getCropName());
          tpDelim = ", ";
          tpContent += c;
          
        }

        CalendarEventEntry tpee = null;
        tpee = createEvent( tpTitle.toString(), tpContent, d );
        planMap.put( "tp"+d.getTime(), tpee );
        
//        BatchUtils.setBatchId( tpee, "tp"+d.getTime() ); // use the time as batch id
//        BatchUtils.setBatchOperationType( tpee, BatchOperationType.INSERT);
//        batchRequest.getEntries().add( tpee );

      }


      // load the feed for this calendar
      CalendarEventFeed planCalFeed;
      Link batchLink = null;

      Link nextLink = new Link();
      nextLink.setHref( eventFeedUrl.toString() );

      // build a map for the entries which have already been uploaded
      // the feed is paginated, so we have to iterate w/ the getNextLink()
      while( nextLink != null ) {

        planCalFeed = service.getFeed( new URL( nextLink.getHref() ),
                                       CalendarEventFeed.class );

        if ( batchLink == null )
          batchLink = planCalFeed.getLink( Link.Rel.FEED_BATCH, Link.Type.ATOM );

        for ( CalendarEventEntry entry : planCalFeed.getEntries()) {

          List<ExtendedProperty> props = entry.getExtendedProperty();
          ExtendedProperty eventID = null;
          for ( ExtendedProperty extendedProperty : props ) {
            if ( extendedProperty.getName().equals( "cpsEventID" ) ) {
              eventID = extendedProperty;
              break;
            }
          }

          if ( eventID != null )
            feedMap.put( eventID.getValue(), entry );

        }

        nextLink = planCalFeed.getNextLink();

     }




      System.out.println( "PlanMap contains " + planMap.keySet().size() + " keys" );
      System.out.println( "FeedMap contains " + feedMap.keySet().size() + " keys" );


      int updates, inserts, deletes;
      updates = inserts = deletes = 0;


      for ( String key : planMap.keySet() ) {

        CalendarEventEntry cee = planMap.get( key );

        // UPDATE existing entries
        if ( feedMap.containsKey( key ) ) {

          updates++;

          CalendarEventEntry fee = feedMap.get( key );
          fee.setTitle( cee.getTitle() );
          fee.setContent( cee.getContent() );

          BatchUtils.setBatchId( fee, key );
          BatchUtils.setBatchOperationType( fee, BatchOperationType.UPDATE );
          batchRequest.getEntries().add( fee );

          feedMap.remove( key );


        } else {
          // INSERT new entries

          inserts++;

          ExtendedProperty ep = new ExtendedProperty();
          ep.setName( "cpsEventID" );
          ep.setValue( key );
          cee.addExtendedProperty( ep );

          BatchUtils.setBatchId( cee, key );
          BatchUtils.setBatchOperationType( cee, BatchOperationType.INSERT );
          batchRequest.getEntries().add( cee );
        }

      }

      // DELETE entries no longer relevant
      for ( String key : feedMap.keySet() ) {

        deletes++;

        CalendarEventEntry fee = feedMap.get( key );

        BatchUtils.setBatchId( fee, key );
        BatchUtils.setBatchOperationType( fee, BatchOperationType.DELETE );
        batchRequest.getEntries().add( fee );

      }





      
      // do the batch post
      CalendarEventFeed batchResponse = service.batch(new URL(batchLink.getHref()), batchRequest);

      // Ensure that all the operations were successful.
      boolean isSuccess = true;
      for (CalendarEventEntry entry : batchResponse.getEntries()) {
        String batchId = BatchUtils.getBatchId(entry);
        if (!BatchUtils.isSuccess(entry)) {
          isSuccess = false;
          BatchStatus status = BatchUtils.getBatchStatus(entry);
          System.out.println("\n" + batchId + " failed (" + status.getReason() + ") " + status.getContent());
        }
      }
      if (isSuccess) {
        System.out.println( "Updated  " + updates + " entries" );
        System.out.println( "Inserted " + inserts + " entries" );
        System.out.println( "Deleted  " + deletes + " entries" );
      }


    } catch (MalformedURLException e) {
      // Bad URL
      System.err.println("Uh oh - you've got an invalid URL.");
      e.printStackTrace();
    } catch (IOException e) {
      // Communications error
      System.err.println("There was a problem communicating with the service.");
      e.printStackTrace();
    } catch (ServiceException e) {
      // Server side error
      System.err.println("The server had a problem handling your request.");
      e.printStackTrace();
    }

    System.exit( 0 );
  }
  

}
