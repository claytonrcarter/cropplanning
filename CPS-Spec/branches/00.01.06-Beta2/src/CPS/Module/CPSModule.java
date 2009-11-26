package CPS.Module;


public abstract class CPSModule {

    public static final String GLOBAL_DEVEL_VERSION = "0.1.6";
    /// handles UI experience and displays contents of modules
    protected final String MOD_TYPE_UI = "UI";
    /// provides core funtionality
    protected final String MOD_TYPE_CORE = "Core";
    /// provides a datamodel
    protected final String MOD_TYPE_DATAMODEL = "DataModel";
    
   protected String ModuleName;
   protected String ModuleType;
   protected String ModuleDescription;
   protected String ModuleVersion;
   protected int ModuleVersion_Major;
   protected int ModuleVersion_Minor;
   protected int ModuleVersion_Revision;

   public abstract int init();
   protected abstract int saveState();
   public abstract int shutdown();
   
   public String getModuleName() { return ModuleName; }
   public String getModuleType() { return ModuleType; }
   public String getModuleDescription() { return ModuleDescription; }
      
   public String getModuleVersion() { return ModuleVersion; }
   public String getModuleVersionMajor() { return "" + ModuleVersion_Major; }
   public String getModuleVersionMinor() { return "" + ModuleVersion_Minor; }
   public String getModuleVersionRevision() { return "" + ModuleVersion_Revision; }
   public long getModuleVersionAsLongInt() { 
       return versionAsLongInt( ModuleVersion_Major, ModuleVersion_Minor, ModuleVersion_Revision );
   }
   
   public static long versionAsLongInt( String ver ) {
       
       // this is cribbed more or less directly from setModuleVersion()
       if ( ver.matches( "\\d+(\\.\\d(\\.\\d)?)?" )) {
           String[] v = ver.split("\\.");
           if ( v.length > 2 )
               return versionAsLongInt( Integer.parseInt( v[0] ), 
                                        Integer.parseInt( v[1] ),
                                        Integer.parseInt( v[2] ));
           if ( v.length > 1 )
               return versionAsLongInt( Integer.parseInt( v[0] ), 
                                        Integer.parseInt( v[1] ), 
                                        0 );
           if ( v.length > 0 )
               return versionAsLongInt( Integer.parseInt( v[0] ), 0, 0 );
       }
       else
           System.err.println("Module Version error: incorrect version format: " + ver );
   
       return -1;
       
   }
   public static long versionAsLongInt( int maj, int min, int rev ) {
       return Long.parseLong( String.format( "%04d", maj ) +
                              String.format( "%04d", min ) +
                              String.format( "%04d", rev ) );
   }
   
   
   // public abstract String[] getRequires();
   
   protected void setModuleName( String s ) { ModuleName = s; }
   protected void setModuleType( String s ) { ModuleType = s; }
   protected void setModuleDescription( String s ) { ModuleDescription = s; }
   protected void setModuleVersion( String s ) { 
       ModuleVersion = s; 
   
       // this will accept version strings of "1", "1.1" and "1.1.1"
       if ( ModuleVersion.matches( "\\d+(\\.\\d(\\.\\d)?)?" )) {
           String[] v = ModuleVersion.split("\\.");
           // TODO we should perhaps test each of these substrings to see if
           // they match some sort of alpha, beta or RC, then just strip that off
           if ( v.length > 0 )
               ModuleVersion_Major = Integer.parseInt( v[0] );
           if ( v.length > 1 )
               ModuleVersion_Minor = Integer.parseInt( v[1] );
           if ( v.length > 2 )
               ModuleVersion_Revision = Integer.parseInt( v[2] );
       }
       else
           System.err.println("Module Version error: incorrect version format for module " + ModuleName + ": " + ModuleVersion );
   
   }
   

   public boolean verifyVersion( String ver ) {
      return getModuleVersion().equalsIgnoreCase( ver );
   }
    
   
   protected void debug( String message ) {
      debug( this.getModuleName(), message );
   }
   
   public static void debug( String module, String message ) {
      if ( CPSGlobalSettings.getDebug()
//              && ! module.equals( "HSQLQuerier" )
         )
         System.out.println( "DEBUG(" + module + "): " + message );
   }
}
