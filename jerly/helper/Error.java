package jerly.helper;

import jerly.helper.C;

/**
 * Class to handle errors.
 * 
 * @author  Laszlo Szathmary (<a href="szathmar@loria.fr">szathmar@loria.fr</a>)
 */
public class Error
{
   /** 
    * Drops an error message and exits.
    * 
    * @param what An integer (constant from the C class) indicating what kind of error occured.
    */
   public static void die(int what)
   {
      switch (what)
      {
//         case C.ARGS:
//            System.err.println("Error: at least one agument is required!");
//            System.err.println("Usage: java Main <basenum_file> [<minsupport>]");
//            break;
         case C.SECOND_ARG:
            System.err.println("Error: the second argument (minsupport) should be an integer or a double value followed by '%'!");
            System.err.println("       example: 2 or 20% or 1.75%");
            break;
         case C.FILE_NOT_FOUND:
            System.err.println("Error: the input file was not found!");
            break;
         case C.IO:
            System.err.println("Error: I/O problem while reading the input file!");
            break;
         case C.IO_WRITE:
            System.err.println("Error: I/O problem while writing the HTML output file!");
            break;
         case C.BAD_SWITCH:
            System.err.println("Error: wrong switch format (its correct form is like -v=f for instance)!");
            break;
         case C.MANY_ARGS_AT_END:
            System.err.println("Error: too many arguments at the end (allowed: <database> [<minsupport>])!");
            break;
         case C.LESS_ARGS_AT_END:
            System.err.println("Error: not enough arguments at the end (needed: <rules> <question>)!");
            break;
         case C.FILE_ALREADY_EXISTS:
            System.err.println("Error: cannot write HTML output to directory ./output/ , because a file with this name already exists!");
            break;
         case C.CANNOT_CREATE_DIR:
            System.err.println("Error: cannot create directory for the HTML output!");
            break;
         case C.CANNOT_WRITE_DIR:
            System.err.println("Error: the output directory for HTML files is not writeable!");
            break;
         case C.CANNOT_DELETE_FILE:
            System.err.println("The output HTML file already exists, and cannot be deleted!");
            break;
         case C.CANNOT_CREATE_FILE:
            System.err.println("The output HTML file cannot be created for write operation!");
            break;
         case C.NO_S_ON_LEFT_SIDE:
            System.err.println("Error: among the rules there is none with 'S' (start symbol) on the left side!");
            break;
         default:
            break;
      }
      
      System.exit(-1);  // exit in all cases
   }
}

