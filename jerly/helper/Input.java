package jerly.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Vector;

import jerly.datastructure.Elem;
import jerly.datastructure.Nonterminal;
import jerly.datastructure.Question;
import jerly.datastructure.Rule;
import jerly.datastructure.Rules;
import jerly.datastructure.Terminal;

/**
 * Input files handler.
 * 
 * @author  Laszlo Szathmary (<a href="szathmar@loria.fr">szathmar@loria.fr</a>)
 */
public class Input
{
   /**
    * Reads in the input file.
    * 
    * @param file The input file. 
    * @return Rules read from the input file.
    */
   public Rules readRules(String file)
   {
      Rules rules = new Rules();
      String line;
      int lineCnt = 0;
      
      try {
         BufferedReader in = new BufferedReader(new FileReader(file));
         while ( (line = in.readLine()) != null )
         {
            ++lineCnt;
            this.parseRule(line, rules, lineCnt);
         }
         in.close();
      }
      catch (FileNotFoundException fnfe) {
         System.err.println("Error: cannot find the rules file "+file+".");
         System.exit(-1);
      }
      catch (java.io.IOException ioe) {
         System.err.println("Error: I/O error while processing file "+file+".");
         System.exit(-1);
      }
      return rules;
   }
   
   /**
    * Parses a line of the input file.
    * 
    * @param line A line of the input file.
    * @param rules We collect rules in this.
    * @param lineCnt At which line we are at in the input file.
    */
   private void parseRule(String line, Rules rules, int lineCnt) 
   {
      line = line.replaceAll("\\s", "");
      String items[] = line.split("->");
      String left  = items[0], 
      		 right = items[1];
      
      if ((left.length() == 0) || (right.length() == 0)) {
         System.err.println("Error in the format of the rules file at line "+lineCnt+"!");
         System.exit(-1);
      }
      
      Vector leftRule  = parseLeftRight(left);
      Vector rightRule = parseLeftRight(right);
      
      postCheckLeftPart(leftRule);
      
      rules.addNewRule(new Rule(leftRule, rightRule));
   }
   
   /**
    * Left sides can (and must) only have one non-terminal element.
    * Otherwise it drops an error message.
    * 
    * @param leftRule The left part of the rule that we want to check.
    */
   private void postCheckLeftPart(Vector leftRule)
   {
      boolean ok = false;
      if (leftRule.size()==1)
      {
         Elem elem = (Elem) leftRule.get(0);
         if (elem instanceof Nonterminal) ok = true;
      }
      
      if (ok == false) {
         System.err.println("Error in the rules file: left sides can (and must) only have one non-terminal element!");
         System.exit(-1);
      }
   }

   private Vector parseLeftRight(String srt)
   {
      Vector part = new Vector();
      int size = srt.length();
      char c;
      
      for (int i = 0; i<size; ++i)
      {
         c = srt.charAt(i);
         if (Character.isUpperCase(c)) part.add(new Nonterminal(c));
         else									part.add(new Terminal(c));
      }
      return part;  
   }

   /**
    * Read the question from file.
    * 
    * @param file The input file with the question. 
    * @return The question read from the file.
    */
   public Question readQuestion(String file)
   {
      Question question = new Question();
      String line;

      try {
         BufferedReader in = new BufferedReader(new FileReader(file));
         line = in.readLine();
         this.parseQuestion(line, question);
         in.close();
      }
      catch (FileNotFoundException fnfe) {
         System.err.println("Error: cannot find the question file "+file+".");
         System.exit(-1);
      }
      catch (java.io.IOException ioe) {
         System.err.println("Error: I/O error while processing file "+file+".");
         System.exit(-1);
      }
      
      return question;
   }

   /**
    * Parses the first line of the question file.
    * 
    * @param line
    * @param question
    */
   private void parseQuestion(String line, Question question)
   {
      line = line.replaceAll("\\s", "");
      question.setQuestion(line);
   }

}
