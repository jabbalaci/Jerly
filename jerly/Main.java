package jerly;

import java.util.BitSet;
import java.util.Vector;

import jerly.datastructure.Earley;
import jerly.helper.Arguments;
import jerly.helper.C;

/**
 * Main class, entry point.
 * 
 * @author Laszlo Szathmary (<a href="szathmar@loria.fr">szathmar@loria.fr</a>)
 */
public class Main
{
   private String rules;
   private String question;
   BitSet verbosity;
   BitSet extra;
   
   /**
    * Entry point.
    * 
    * @param args Command line arguments.
    */
   public static void main(String[] args)
   {
      Main main = new Main();
      main.start(args);
   }

   private void start(String[] args)
   {  
      Vector argsVector = (new Arguments()).processArguments(args);
      // assign values to global variables
      extractArgs(argsVector);
      
      Earley earley = new Earley(rules, question);
      earley.start();	// do the calculation
      
      earley.printResult();		// to STDOUT
      if (earley.printHtmlResult()) {
         System.out.println("HTML output was successfully written to dir. '"+C.HTML_OUTPUT_DIR+"'.");
      }
   }
   
   /**
    * Extract arguments from the vector and assigns values to global variables.
    * 
    * @param v A Vector that contains the runtime parameters.
    */
   private void extractArgs(Vector v)
   {
      this.rules       = (String)v.get(0);
      this.question    = (String)v.get(1);
      this.verbosity   = (BitSet)v.get(2);
      this.extra       = (BitSet)v.get(3);
   }
}
