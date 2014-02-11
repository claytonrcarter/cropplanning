/* CPSDateValidator.java - created: Jan 30, 2008
 * Copyright (C) 2008 Clayton Carter
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
 * 
 */

package CPS.Data;

import CPS.Module.CPSGlobalSettings;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CPSDateValidator {

    /** Example: 11/23 */
    final public static int DATE_FORMAT_SHORT = 1;
    /** Example: Mon 11/23 */
    final public static int DATE_FORMAT_SHORT_DAY_OF_WEEK = 2;
    /** Example: 11/23/13 */
    final public static int DATE_FORMAT_BRIEFYEAR = 3;
    /** Example: 11/23/2013 */
    final public static int DATE_FORMAT_FULLYEAR = 4;
    /** Example: Mon 11/23/2013 */
    final public static int DATE_FORMAT_FULLYEAR_DAY_OF_WEEK = 5;
    /** Example: Nov 23 */
    final public static int DATE_FORMAT_MON_DAY = 6;
    /** Example: Nov 23, 2013 */
    final public static int DATE_FORMAT_MON_DAY_YEAR = 7;
    /** Example: November */
    final public static int DATE_FORMAT_JUSTMONTH = 8;
    /** Example: 2013-11-23 */
    final public static int DATE_FORMAT_SQL = 9;


    private static List<Integer> formatList;
    private static int defaultFormat;
    
    public CPSDateValidator() {

      formatList = new ArrayList<Integer>();
      formatList.addAll( Arrays.asList( DATE_FORMAT_BRIEFYEAR,
                                        DATE_FORMAT_SHORT,
                                        DATE_FORMAT_FULLYEAR ));
        
      defaultFormat = DATE_FORMAT_FULLYEAR;
        
    }
    
    /**
     * @param formatNum - DATE_FORMAT_SHORT, DATE_FORMAT_SHORT_DAY_OF_WEEK, etc
     * @return a string format suitable for passing to format(Date,String)
     */
    public static String getFormat( int formatNum ) {

      switch ( formatNum ) {

        case DATE_FORMAT_SHORT_DAY_OF_WEEK:
          if ( CPSGlobalSettings.useUSDates() )
            return "EEE MM/dd";
          else
            return "EEE dd/MM";

        case DATE_FORMAT_BRIEFYEAR:
          if ( CPSGlobalSettings.useUSDates() )
            return "MM/dd/yy";
          else
            return "dd/MM/yy";

        case DATE_FORMAT_FULLYEAR:
          if ( CPSGlobalSettings.useUSDates() )
            return "MM/dd/yyyy";
          else
            return "dd/MM/yyyy";

        case DATE_FORMAT_FULLYEAR_DAY_OF_WEEK:
          if ( CPSGlobalSettings.useUSDates() )
            return "EEE MM/dd/yyyy";
          else
            return "EEE dd/MM/yyyy";

        case DATE_FORMAT_MON_DAY:
          return "MMM dd";

        case DATE_FORMAT_MON_DAY_YEAR:
          return "MMM dd, yyyy";

        case DATE_FORMAT_JUSTMONTH:
          return "MMMM";

        case DATE_FORMAT_SQL:
          return "yyyy-MM-dd";

        case DATE_FORMAT_SHORT:
        default:
          if ( CPSGlobalSettings.useUSDates() )
            return "MM/dd";
          else
            return "dd/MM";
      }

    }

    /**
     * Add a date format.
     * @param f a format string for SimpleDateFormat
     * @see java.text.SimpleDateFormat
     */
    public void addFormat( Integer f ) {
        formatList.add(f);
    }
    
    /**
     * Sets the default format used for formatting dates.  The default value
     * is DATE_FORMAT_FULLYEAR.
     * @param f Format to use.
     */
    public void setDefaultFormat( Integer f ) {
        defaultFormat = f;
    }
    
    public static String format( Date d ) {
       return format( d, getFormat( defaultFormat ));
    }
    
    public static String format( Date d, Integer f ) {
      return format( d, getFormat( f ));
    }
    public static String format( Date d, String format ) {
        if ( d == null || d.getTime() == 0 )
            return "";
        else
            return new SimpleDateFormat( format ).format( d );
    }


    public static Date simpleParse( String s ) {
      return simpleParse( s, getFormat( defaultFormat ));
    }

    /**
     * @param s A String representation of a date to parse.
     * @param format Date format to parse it against. (Such as the (@link DATE_FORMAT_* } defined in this class.
     * @return the parsed date.
     */
    public static Date simpleParse( String s, String format ) {

        if ( s == null || s.equals( "" ))
            return null;
        else
            try {
                return new SimpleDateFormat( format ).parse( s );
            }
            catch ( Exception e ) {
                e.printStackTrace();
                return null;
            }
    }
    
    public static Date parse( String s ) {
        if ( s.equals( "" ))
            return null;
        
        Date date = new Date(-1);
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setLenient(false);
        
        String dateText = s;
        int dateShift = 0;
        int shiftBy = GregorianCalendar.DAY_OF_YEAR;
        
        // handles addition of negative numbers
        if ( dateText.matches( ".+\\+.+" ) || dateText.matches( ".+-.+" ) ) {
            String[] sp = new String[] { "", "" };
            int shiftFactor = 1;
            
            if ( dateText.matches( ".+\\+.+" )) {
                sp = dateText.split( "\\+" );    
                shiftFactor = 1;
            }
            // does NOT handle subtract of negative numbers
            else if ( dateText.matches( ".+-.+" ) ) {
                sp = dateText.split( "-" );
                shiftFactor = -1;
                // if we split into two, then there was just one -
                if ( sp.length != 2 )
                    System.err.println( "ERROR(CPSDateValidator): Can't understand date:" +
                                        dateText + " [Too many '-'s]" );
            }
            // trim off leading and trailing whitespace
            dateText = sp[0].trim();
            
            String dateShiftString = sp[1].trim();
            if ( dateShiftString.matches( ".+[dwmy]" )) {
                
                // we match on w or m; trailing whitespace was already trimmed
                // d isn't necessary since it's default
                if ( dateShiftString.matches( ".+w" ))
                    shiftBy = GregorianCalendar.WEEK_OF_YEAR;
                else if ( dateShiftString.matches( ".+m" ))
                    shiftBy = GregorianCalendar.MONTH;
                else if ( dateShiftString.matches( ".+y" ))
                    shiftBy = GregorianCalendar.YEAR;
                
                // trim off the trailing character (the d, w or m)
                dateShiftString = dateShiftString.substring( 0, dateShiftString.length() - 1 );
                
            }
                
            dateShift = shiftFactor * Integer.parseInt( dateShiftString );
        }
        
        
        for ( Integer f : formatList ) {
          String format = getFormat(f);
            sdf.applyPattern( format );
            try {
                // if the date parses, then break the for loop
                date = sdf.parse( dateText );
//                System.out.println("DATE MATCHED: " + format );
                // if the matched format doesn't include a year component, 
                // then shift the date into this year.
                if ( format.indexOf("yy") == -1 ) {
                    GregorianCalendar c = new GregorianCalendar();
                    GregorianCalendar now = new GregorianCalendar();
                    c.setTime( date );
                    now.setTime( new Date() );
                    c.set( GregorianCalendar.YEAR, now.get( GregorianCalendar.YEAR ) );
                    date = c.getTime();
                }
                break;
            } 
            // if the date doesn't parse, try the next pattern
            catch ( Exception ignore ) {}
            System.err.println( "ERROR parsing date: " + s );
        }
        
        // if dateShift is not 0, then we should add it to the parsed date
        if ( dateShift != 0 ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( date );
            cal.add( shiftBy, dateShift );
            date = cal.getTime();
        }
        
        return date;
    }

}
