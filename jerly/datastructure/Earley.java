package jerly.datastructure;

import java.util.Enumeration;
import java.util.Vector;

import jerly.helper.Error;
import jerly.helper.C;
import jerly.helper.HtmlResult;
import jerly.helper.Input;

/**
 * Main class of Earley's algorithm.
 * 
 * @author  Laszlo Szathmary (<a href="szathmar@loria.fr">szathmar@loria.fr</a>)
 */
public class Earley
{
   /**
    * The 2D table in which we work.
    */
   private Cell[][] table;
   private Input input;
   private Rules rules;
   private Question question;

   /**
    * Constructor.
    * 
    * @param rulesFilename The file that contains the rules. 
    * @param questionFilename The file that contains the question.
    */
   public Earley(String rulesFilename, String questionFilename)
   {
      input 			= new Input();
      this.rules 		= input.readRules(rulesFilename);
      this.question 	= input.readQuestion(questionFilename);
   }

   /**
    * Controller function.
    */
   public void start()
   {
      int j = 0,
          n = this.question.getLength(),
          i;
      
      initTable();
      step_1();
      while (j < n)
      {
         step_2(j);
         ++j;
         i=j-1;
         while (i >= 0)
         {
            step_4(i,j);
            step_5(i, j);
            --i;
            //break;
         }
         //break;   // debug
      }
      
      //printTable();
      //System.out.println();
      //printRulesAndQuestion();
      //printResult();
   }

   /**
    * Print the result to the stdout.
    */
   public void printResult()
   {
      Cell last = this.table[0][this.question.getLength()];
      boolean ok = (last.getRulesWithDotLast().isEmpty() == false);		
      // OK if there is a rule with dot at the end in the last (upper right) cell
      
      System.out.println();
      System.out.println("Rules:");
      System.out.println("======");
      System.out.println(this.rules);
      System.out.println();
      System.out.println("Question:");
      System.out.println("=========");
      System.out.println(this.question);
      System.out.println();
      System.out.println("The word '"+this.question+"' can"+(ok?"":"NOT")+" be generated with the grammar.");
      System.out.println();
   }

   /**
    * First step.
    */
   private void step_1()
   {
      Rule rule;
      boolean foundS = false;
      
      for (Enumeration e = this.rules.getRules().elements(); e.hasMoreElements(); )
      {
         rule = (Rule) e.nextElement();
         if (rule.isLeftS()) {
            foundS = true;
            table[0][0].add(rule.addDotToRight());
         }
      }
      
      if (foundS == false) 
      {
         System.out.println();
         System.out.println("Rules:");
         System.out.println("======");
         System.out.println(this.rules);
         Error.die(C.NO_S_ON_LEFT_SIDE);
      }
   }
   
   /**
    * Second step.
    * 
    * @param j
    */
   private void step_2(final int j)
   {
      Cell curr,
           dest = this.table[j][j];
      Rule rule, dottedRule, originalRule, insert;
      Vector nonterminalAfterDot, originalRules;
      Enumeration e, in;
      Nonterminal afterDot;
      
      for (int k = 0; k <= j; ++k)
      {
         curr = this.table[k][j];
         curr.openAllRules();
         while (curr.hasOpenRules())
         {
            rule = curr.getFirstOpenRule();
            afterDot = rule.getNonterminalAfterDot();
            
            if (afterDot != null)
            {  
               originalRules = this.rules.getRulesWithNonterminalLeft(afterDot);
               
               for (in = originalRules.elements(); in.hasMoreElements(); )
               {
                  originalRule = (Rule) in.nextElement();
                  dest.add(originalRule.addDotToRight());
               }
            }
            rule.close();
         }
      }
   }
   
   /**
    * Fourth step.
    * 
    * @param i
    * @param j
    */
   private void step_4(final int i, final int j)
   {
      Cell curr 		= this.table[i][j],
           cellLeft 	= this.table[i][j-1];
      Vector collect = cellLeft.getRulesWithThisElemAfterDot(this.question.getLetter(j));
      Rule rule;
      
      for (Enumeration e = collect.elements(); e.hasMoreElements(); )
      {
         rule = (Rule) e.nextElement();
         curr.add(rule.moveDotRight());
      }
   }
   
   /**
    * Fifth step.
    * 
    * @param i
    * @param j
    */
   private void step_5(final int i, final int j)
   {
      Cell cellE, cellF, cellDest;
      Vector dotLast;
      Enumeration e, in;
      Rule rule, toAdd;
      Nonterminal leftLetter;
      Vector collect;
      // on
      //boolean first = true;
      // off
      
      for (int k = i; k < j; ++k)
      {
         cellE 	= this.table[k][j];
         cellF 	= this.table[i][k];
         cellDest = this.table[i][j];
         // on
         /*if (j==3) {
            System.out.println("cell E: "+cellE);
            System.out.println("cell F: "+cellF);
            System.out.println("cell dest: "+i+","+j);
            //first = false;
         }*/
         // off
         
         cellE.openAllRules();
         while (cellE.hasOpenRules())
         {
            rule = cellE.getFirstOpenRule();
            if (rule.hasDotLast())
            {
               // on
               //System.out.println("rule . last: "+rule);
               // off
	            leftLetter = rule.getLeftLetter();
	            collect = cellF.getRulesWithThisElemAfterDot(leftLetter);
	            for (in = collect.elements(); in.hasMoreElements(); )
	            {
	               toAdd = (Rule) in.nextElement();
	               cellDest.add(toAdd.moveDotRight());
	            }
            }
            rule.close();
         }
      }
      
      // treat cell E ( table[0][j] ) once again
      cellE = this.table[0][j];
      cellF = this.table[0][0];
      cellDest = cellE;
      
      cellE.openAllRules();
      while (cellE.hasOpenRules())
      {
         rule = cellE.getFirstOpenRule();
         if (rule.hasDotLast())
         {
            // on
            //System.out.println("rule . last: "+rule);
            // off
            leftLetter = rule.getLeftLetter();
            collect = cellF.getRulesWithThisElemAfterDot(leftLetter);
            for (in = collect.elements(); in.hasMoreElements(); )
            {
               toAdd = (Rule) in.nextElement();
               cellDest.add(toAdd.moveDotRight());
            }
         }
         rule.close();
      }
   }
   
   /**
    * Construct the triangular matrix and initialize the cells.
    */
   private void initTable()
   {
      int rows    	= this.question.getLength(),
          nulls   	= 0,				// number of null references at the beginning of rows
          columnsAll = rows + 1,		// won't change
          i, j;
      
      this.table = new Cell[rows][];
      for (i = 0; i < rows; ++i)
      {
         this.table[i] = new Cell[columnsAll];
         for (j = 0; j < nulls; ++j)
            this.table[i][j] = null;  
         
         for (j = nulls; j < columnsAll; ++j)
            this.table[i][j] = new Cell();
         
         ++nulls;
      }
   }
   
   /**
    * Prints the current state of the table.
    */
   private void printTable()
   {
      int rows    = this.question.getLength(),
          columns = rows + 1,
          i, j;
      
      for (i = 0; i < rows; ++i)
      {
         for (j = 0; j < columns; ++j)
            System.out.print(this.table[i][j]+"; ");
         System.out.println();
      }
   }

   /**
    * Prints the rules (grammar) and the question.
    */
   private void printRulesAndQuestion()
   {
      System.out.println("Rules:");
      System.out.println(this.rules);
      System.out.println("Question:");
      System.out.println(this.question);
   }

   /**
    * Prints a nicely formatted HTML result.
    * By default it goes to the "output" directory.
    * 
    * @return True, if IO write was successful.
    */
   public boolean printHtmlResult()
   {
      HtmlResult html = new HtmlResult(this.rules);
      return html.print(this.table, this.question);
   }
}
