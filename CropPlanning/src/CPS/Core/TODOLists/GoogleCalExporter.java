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
import CPS.Module.CPSModule;
import CPS.ModuleManager;
import CPS.UI.Swing.CPSErrorDialog;
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


  
  private static final String CALENDAR_TITLE_PREFIX = "Crop Plan: ";

  // The HEX representation of red, blue and green
  private static final String RED = "#A32929";
  private static final String BLUE = "#2952A3";
  private static final String GREEN = "#0D7813";

  /**
   * Utility classes should not have a public or default constructor.
   */
  protected GoogleCalExporter() {}
  

  protected static void exportCropPlan( List<CPSPlanting> plantings,
                                        String planName,
                                        boolean changeLogin ) {


    //************************************************************************//
    // Setup the Lists for the crop plan
    //************************************************************************//
    EventList<CPSPlanting> planList = GlazedLists.eventList( plantings );



    CalendarService service = new CalendarService("CropPlanning-GCal-v0.1");

    // load preferences for this class
    Preferences prefs = Preferences.userNodeForPackage( GoogleCalExporter.class );

    //************************************************************************//
    // log in the user and get the authenticated username
    //************************************************************************//
    String userName = authenticateUser( service,
                                        prefs.get( "GOOGLE_USERNAME", "" ),
                                        changeLogin ? null : prefs.get( "GOOGLE_AUTH_TOKEN", null ) );

    if ( userName == null ) {
      new CPSErrorDialog("Failed authentication with Google").setVisible( true );
      return;
    }

    // save the username and auth token for this user
    prefs.put( "GOOGLE_USERNAME", userName );
    prefs.put( "GOOGLE_AUTH_TOKEN",
               ((UserToken) service.getAuthTokenFactory()
                                   .getAuthToken()).getValue());


    //************************************************************************//
    //
    //************************************************************************//
    URL calendarsFeedURL;
    try {
      calendarsFeedURL = new URL(METAFEED_URL_BASE + userName + OWNCALENDARS_FEED_URL_SUFFIX );
    } catch ( MalformedURLException e ) {
      new CPSErrorDialog( "There is a problem communicating with Google<br>" +
                          "Please verify that your username (email) is entered<br>" +
                          "correctly.  If it is, please email us for help.", "Bogus URL").setVisible( true );
      e.printStackTrace();
      return;
    }

    // Maps to hold all of our new (and old) feed entries
    HashMap<String, CalendarEventEntry> planMap = new HashMap<String, CalendarEventEntry>();
    HashMap<String, CalendarEventEntry> feedMap = new HashMap<String, CalendarEventEntry>();


    //************************************************************************//
    // now start doing the real work
    //************************************************************************//
    try {

      // look for this crop plan in the calendars list
      CalendarEntry cal = findCalendarForCropPlan( service, calendarsFeedURL, planName );

      // if not found, create calendar
      if ( cal == null ) {
        cal = createCalendar( service, calendarsFeedURL, planName );
      } else {
        System.out.println( "Calendar exists." );
      }

      // it's been found or created, so find the Calendar ID
      String[] s = cal.getEditLink().getHref().split( "/" );
      String calID = s[ s.length - 1 ].replaceAll( "%40", "@" );

      // Create URL for cropplan calendar and
      URL eventFeedUrl = new URL( METAFEED_URL_BASE + calID + EVENT_FEED_URL_SUFFIX );


//****************************************************************************//
//    Iterate over all of the seeding days
//****************************************************************************//
      GroupingList<CPSPlanting> groupList =
            new GroupingList<CPSPlanting>( planList,
                                           new CPSComparators.DatePlantComparator() );
      for ( Iterator<List<CPSPlanting>> it = groupList.iterator(); it.hasNext(); ) {
        // get the list of plantings that happen on the next date
        List<CPSPlanting> dateGroup = it.next();

        // start the entries for this date
        StringBuilder dsTitle = new StringBuilder( "Seed in Field: " );
        StringBuilder tpTitle = new StringBuilder( "Seed in GH: " );
        String dsDelim = "";
        String tpDelim = "";
        String dsContent = "";
        String tpContent = "";
        // they're all on the same date
        Date d = dateGroup.get(0).getDateToPlant();

        // now group these plantings by crop name ...
        GroupingList<CPSPlanting> cropGroupList =
                new GroupingList<CPSPlanting>( GlazedLists.eventList( dateGroup ),
                                               new CPSComparators.CropNameComparator() );
        // ... and iterate over the grouped crops
        for ( Iterator<List<CPSPlanting>> it2 = cropGroupList.iterator(); it2.
                hasNext(); ) {
          List<CPSPlanting> cropGroup = it2.next();

          // we assume that each group will be all DS or all TP
          if ( cropGroup.get(0).isDirectSeeded() ) {

            // append this crop name to the list of crops happening today
            dsTitle.append( dsDelim ).append( cropGroup.get(0).getCropName() );
            dsDelim = ", ";

            // and for each planting, add some info about it to the
            // description for this event
            for ( CPSPlanting p : cropGroup ) {
              dsContent += p.getCropName() + ": " +
                           p.getVarietyName() + " - " +
                           p.getBedsToPlantString() + " beds\n";
            }

          } else {
            // ... ditto

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


        // if there are DS plantings (we check to see if there is any content
        // created for this event) then we create an entry and add it to our map
        if ( ! dsContent.equals( "" ) ) {
          CalendarEventEntry dsee = createEvent( dsTitle.toString(), dsContent, d );
          planMap.put( "ds"+d.getTime(), dsee );
        }

        // ... ditto
        if ( ! tpContent.equals( "" ) ) {
          CalendarEventEntry tpee = createEvent( tpTitle.toString(), tpContent, d );
          planMap.put( "gh"+d.getTime(), tpee);
        }

      }


//****************************************************************************//
//    Now do the same but for transplanted plantings and transplant dates
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

        CalendarEventEntry tpee = createEvent( tpTitle.toString(), tpContent, d );
        planMap.put( "tp"+d.getTime(), tpee );

      }


//****************************************************************************//
//    done loading all local event, now load all remote events (if any)
//****************************************************************************//
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


//****************************************************************************//
//      Local and remote entries created, now match them up and batch upload
//****************************************************************************//
      CalendarEventFeed batchRequest = new CalendarEventFeed();

      int updates, inserts, deletes;
      updates = inserts = deletes = 0;

      // iterate over all local keys looking for matching remote keys
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

//****************************************************************************//
// do the batch POST
//****************************************************************************//
      CalendarEventFeed batchResponse = service.batch( new URL(batchLink.getHref()),
                                                       batchRequest );

//****************************************************************************//
// Ensure that all the operations were successful.
//****************************************************************************//
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

  }


  private static String authenticateUser( CalendarService service,
                                         String userName,
                                         String authToken ) {

    // attempt to authenticate w/ stored auth token
    System.out.println( "Logging in with auth token for account " + userName );
    service.setUserToken( authToken );

    URL calendarsFeedURL;
    try {
      calendarsFeedURL = new URL(METAFEED_URL_BASE + userName + OWNCALENDARS_FEED_URL_SUFFIX );
    } catch ( MalformedURLException e ) {
      e.printStackTrace();
      return null;
    }

    while ( ! isAuthenticated( service, calendarsFeedURL ) ) {

      CPSModule.debug("GCal", "Logging in with user credentials" );
      GoogleCalLoginDialog loginDia = new GoogleCalLoginDialog( userName );
      loginDia.setVisible( true );

      // bail if cancelled
      if ( loginDia.isCancelled() ) {
        return null;
      }

      // if they enter a different username, then rebuild the feed URL
      if ( ! userName.equalsIgnoreCase( loginDia.getEmail() ) ) {
        userName = loginDia.getEmail();
        try {
          calendarsFeedURL = new URL(METAFEED_URL_BASE + userName + OWNCALENDARS_FEED_URL_SUFFIX );
        } catch ( MalformedURLException e ) {
          e.printStackTrace();
        }
      }


      // login w/ user name and password
      char[] p = loginDia.getPassword();
      try {
        service.setUserCredentials( userName, new String( p ) );
      } catch ( CaptchaRequiredException captchaException ) {

        // we have to handle a captcha
        CPSModule.debug("GCal", "Captcha error: " + captchaException.getMessage() );

        GoogleCaptchaDialog captchaDialog = new GoogleCaptchaDialog();

        try {
          captchaDialog.setCaptchaUrl( new URL( captchaException.getCaptchaUrl() ));
          captchaDialog.setVisible( true );
        } catch ( MalformedURLException f ) {
          CPSModule.debug("GCal", "WTF?! Why did Google give us a bad CAPTCHA URL?" );
          CPSModule.debug("GCal", f.getMessage() );
        }

        if ( ! captchaDialog.getCaptchaAnswer().equals( "" ) ) {
          try {
            service.setUserCredentials( userName,
                                        new String( p ),
                                        captchaException.getCaptchaToken(),
                                        captchaDialog.getCaptchaAnswer() );
          } catch ( AuthenticationException g ) {

            CPSErrorDialog ed = new CPSErrorDialog();
            ed.setHeaderTitle( "Invalid Credentials" );
            ed.setDescription( "Google said that you entered invalid credentials.<br>" +
                               "This could mean that the email address and/or the<br>" +
                               "password you entered were incorrect. Please try again." );
            ed.setVisible( true );
            
          }
        }

      } catch (AuthenticationException e ) {

        CPSErrorDialog ed = new CPSErrorDialog();
        ed.setHeaderTitle( "Invalid Credentials" );
        ed.setDescription( "Google said that you entered invalid credentials.<br>" +
                           "This could mean that the email address and/or the<br>" +
                           "password you entered were incorrect. Please try again." );
        ed.setVisible( true );

      }

      // clear out the password array for safety
      // I believe that this also clears out the password that was saved in
      // the dialog ... which is what we want
      Arrays.fill( p, '0' );

    }

    return userName;
    
  }


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
                                              URL calendarFeed,
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
    return service.insert(calendarFeed, calendar);

  }


  private static CalendarEntry findCalendarForCropPlan( CalendarService service,
                                                       URL calendarFeed,
                                                       String cropPlan )
      throws IOException, ServiceException {

    CalendarFeed resultFeed = service.getFeed( calendarFeed,
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
    
    exportCropPlan( dm.getCropPlan( planName ), planName, false );

    System.exit( 0 );
  }
  

}
