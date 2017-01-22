package jerly.helper;

import java.util.BitSet;
import java.util.Enumeration;
import java.util.Vector;

/** 
 * Processes command-line arguments.
 * 
 * @author  Laszlo Szathmary (<a href="szathmar@loria.fr">szathmar@loria.fr</a>)
 */
public class Arguments
{
   private String rules		 = new String("");
   private String question  = new String("");
   private BitSet verbosity = new BitSet();
   private BitSet extra		 = new BitSet();

   /** 
    * Processes command line arguments.
    * 
    * @param s A String array of command line arguments. 
    * @return A Vector that contains the processed arguments (database, minsupport, verbosity, minsupport in percent).
    */
   public Vector processArguments(String[] s)
   {
      Vector args = getArgs(s);		// convert String array to Vector
      Vector result = new Vector();

      if ((args.size() == 0) || (args.contains("--help")))
      {
         print_help();
         System.exit(-1);
      }
      // else
      if (args.contains("--version") || args.contains("-V"))
      {
         print_version();
         System.exit(-1);
      }
      
      Vector to_delete = new Vector();
      String str;
      for (Enumeration e=args.elements(); e.hasMoreElements(); )
      {
         str = (String)e.nextElement();
         if (str.startsWith("-")) { 			// an argument starting with '-' is a potential option
            process_option(str.substring(1));
            to_delete.add(str);					// after processing delete it from the list of arguments
         }
      }
      args.removeAll(to_delete);				// real deletiion
      // by now we can only have 1 (database) or 2 arguments (database + minsupport)
      if (args.size() != 2) Error.die(C.WRONG_ARGS);
      
      // OK, we have 2 arguments here
      this.rules = (String)args.get(0);
      this.question = (String)args.get(1);
      
      // OK, fill up the result vector and send it back
      result.add(rules);
      result.add(question);
      result.add(verbosity);
      result.add(extra);

      return result;
   }

   /**
    * Prints current version of the program.
 	*/
   private void print_version() {
      System.out.println("Jerly " + C.VERSION);
   }

   /**
    * Processes options (that start with '-'), parses them and sets the necessary verbosity bits.
    * 
    * @param option A String that contains an option. Parses it and sets the verbosity BitSet.
    */
   private void process_option(String option)
   {
   	  if (option.indexOf("=") == -1) processExtraOption(option);
   	  else
   	  {
	      String items[] = option.split("=");
	      if (!((items.length==2) && (items[0].length()>0) && (items[1].length()>0))) Error.die(C.BAD_SWITCH);
	
	      String o = items[1];
	      if (items[0].equals("v"))     // verbosity
	      {
	         if (o.equals("m") || o.equals("mem") || o.equals("memory")) this.verbosity.set(C.V_MEMORY);
	         else if (o.equals("f") || o.equals("fun") || o.equals("func") || o.equals("function") || o.equals("functions")) this.verbosity.set(C.V_FUNCTION);
	         else if (o.equals("t") || o.equals("time") || o.equals("runtime")) this.verbosity.set(C.V_RUNTIME);
	         else { System.err.println("Error: unknown option in -v: "+o); Error.die(C.JUST_EXIT); }
	      }
	      else if (items[0].equals("vc"))     // verbosity combo
	      {
	         char c;
	         int length = o.length();
	         for (int i=0; i<length; ++i)
	         {
	            c = o.charAt(i);
	            if (c == 'm') this.verbosity.set(C.V_MEMORY);
	            else if (c == 'f') this.verbosity.set(C.V_FUNCTION);
	            else if ((c == 't') || (c == 'r')) this.verbosity.set(C.V_RUNTIME);
	            else { System.err.println("Error: unknown option in -vc: "+c); Error.die(C.JUST_EXIT); }
	         }
	      }
	      else
	      {
	         System.err.println("Error: unknown option: -"+items[0]);
	         Error.die(C.JUST_EXIT);
	      }
   	  }
   }
   
   /**
    * Processes extra options. These options look like: -asc , -desc , -all , etc.
    * 
    * @param option An extra option.
 	*/
   private void processExtraOption(String option)
   {
      //if (option.equals("null")) this.extra.set(C.X_NULL);
      // else if ...
/*   	else
      {
         System.err.println("Error: unknown option: -"+option);
         Error.die(C.JUST_EXIT);
      }
*/
      
      // !!! we need to do some post checks on the extra arguments
      this.postCheck(extra);
   }
   
   /**
    * It performs some post checkings on the options. Ex.: sets the default
    * ordering if it was not chosen by the user.
    * 
    * @param extra A BitSet containing the extra options.
 	*/
   private void postCheck(BitSet extra)
   {
   }

   /**
    * Converts a String array to a vector of Strings.
    * 
    * @param args Command line arguments in a String array.
    * @return A vector that contains the command line arguments. 
    */
   private Vector getArgs(String[] args)
   {
      Vector v = new Vector();
      for (int i = 0; i<args.length; ++i)
         v.add(args[i]);
      return v;
   }

   /**
    * Prints some help about the usage of the program.
    */
   private void print_help()
   {
      StringBuffer sb = new StringBuffer();
      sb.append("Jerly, a Java implementation of Earley's algorithm\n");
      sb.append("(c) copyright Laszlo Szathmary, 2004--2005 (szathmar@loria.fr)\n");
      sb.append("\n");
      sb.append("Usage: jerly [switches] <rule_file> <question_file>\n");
      sb.append("Switches:\n");
      sb.append("   --help                          help information (you can see this now)\n");
      sb.append("                                   can be used _alone_\n");
      sb.append("   --version, -V                   version information\n");
      sb.append("                                   can be used _alone_\n");
      sb.append("\n");
      sb.append("   rule_file                       rules of the grammar\n");
      sb.append("   question_file                   question (word)\n");

      System.out.println(sb);
   }
}
