package jerly.helper;

/**
 * Some constants.
 * 
 * @author  Laszlo Szathmary (<a href="szathmar@loria.fr">szathmar@loria.fr</a>)
 */
public class C
{
   /**
    * Version information.
    */
	public static final String VERSION = "0.1";
   
   /**
    * Where do we want to write the nicely formatted HTML output?
    */
   public static final String HTML_OUTPUT_DIR = "output";
	
   // constants indicating different errors:
   /**
    * The program should exit without any explication.
    */
   public static final int JUST_EXIT = 1;

   /**
    * Problem with the 2nd argument (minsupport), which should have the
    * form like: 2, or 2.54%, that is: integer or double. If double, followed
    * by a '%' sign.
    */
   public static final int FIRST_ARG = 2;

   /**
    * The input file is not found.
    */
   public static final int SECOND_ARG = 3;

   /**
    * I/O error while reading the input file.
    */
   public static final int IO = 4;

   /**
    * Wrong switch format (its correct form is like -v=f for instance).
    */
   public static final int BAD_SWITCH = 5;

   /**
    * Too many arguments at the end.
    */
   public static final int MANY_ARGS_AT_END = 6;

   /**
    * Not enough arguments at the end.
    */
   public static final int LESS_ARGS_AT_END = 7;
   
   /**
    * File not found.
    */
   public static final int FILE_NOT_FOUND = 8;
   
   /**
    * Problem with the argument. 
    */
   public static final int WRONG_ARGS = 9;
   
   /**
    * File already exists.
    */
   public static final int FILE_ALREADY_EXISTS = 10;
   
   /**
    * Unable to create the HTML_OUTPUT_DIR.
    */
   public static final int CANNOT_CREATE_DIR = 11;
   
   /**
    * The dir. is write protected.
    */
   public static final int CANNOT_WRITE_DIR = 12;
   
   /**
    * Unable to delete a file.
    */
   public static final int CANNOT_DELETE_FILE = 13;
   
   /**
    * Unable to create a file.
    */
   public static final int CANNOT_CREATE_FILE = 14;
   
   /**
    * I/O error.
    */
   public static final int IO_WRITE = 15;
   
   /**
    * If there is no rule with S on the left side.
    */
   public static final int NO_S_ON_LEFT_SIDE = 16;

/////////////////////////////////////////////////////////////////////////////
   
   // constants for monitoring memory usage:
   /**
    * Memory info at the beginning of the loop.
    */
   public static final int MEM_START = 2;

   /**
    * Memory info at the end of the loop.
    */
   public static final int MEM_END = 3;

   /**
    * Maximal memory usage.
    */
   public static final int MEM_MAX = 4;

/////////////////////////////////////////////////////////////////////////////
   
   // constants for different verbosity levels:
   /**
    * Print memory usage.
    */
   public static final int V_MEMORY = 1;

   /**
    * Print information about function calls.
    */
   public static final int V_FUNCTION = 2;

   /**
    * Measure and print information about running time of the program.
    */
   public static final int V_RUNTIME = 3;
}
