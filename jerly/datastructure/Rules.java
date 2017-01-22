package jerly.datastructure;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Input rules of the grammar.
 * Don't confuse these rules with the rules in the cells!!!
 * 
 * @author  Laszlo Szathmary (<a href="szathmar@loria.fr">szathmar@loria.fr</a>)
 */
public class Rules
{
   /**
    * The input rules that we read from the input file.
    * Don't confuse these rules with the rules in the cells!!!
    */
   private Vector rules;
   
   /**
    * Constructor.
    */
   public Rules() {
      this.rules = new Vector();
   }

   /**
    * Registers a new rule.
    * 
    * @param rule A new rule of the grammar.
    */
   public void addNewRule(Rule rule) {
      this.rules.add(rule);
   }
   
   /**
    * @return String representation of the input rules.
    */
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      Rule rule;
      for (Enumeration e = this.rules.elements(); e.hasMoreElements(); )
      {
         rule = (Rule) e.nextElement();
         sb.append(rule.toString()).append('\n');
      }
      return sb.toString();
   }
   
   /**
    * @return The input rules.
    */
   public Vector getRules() {
      return this.rules;
   }

   /**
    * Find these rules among the input rules:
    *    - left side has size 1
    *    - the left size has the given non-terminal symbol
    * 
    * @param afterDot The non-terminal symbol that we are looking for
    * on the left side.
    * @return All those input rules that have the given non-terminal symbol
    * on their left side (and the left side only contains this).
    */
   public Vector getRulesWithNonterminalLeft(Nonterminal afterDot)
   {
      Rule rule;
      Vector left;
      Elem elem;
      Vector collect = new Vector();
      
      for (Enumeration e = this.rules.elements(); e.hasMoreElements(); )
      {
         rule = (Rule) e.nextElement();
         left = rule.getLeft();
         if (left.size()==1)
         {
            elem = (Elem) left.get(0);
            if (elem instanceof Nonterminal)
               if (elem.equals(afterDot)) collect.add(rule); 
         }
      }
      return collect;
   }
}
