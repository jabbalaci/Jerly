package jerly.datastructure;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * Represents a cell in the recognition matrix.
 * 
 * @author  Laszlo Szathmary (<a href="szathmar@loria.fr">szathmar@loria.fr</a>)
 */
public class Cell
{
   /**
    * Rules in a cell.
    */
   private Set rules;

   /**
    * Constructor.
    */
   Cell() {
      this.rules = new HashSet();
   }
   
   /**
    * String representation of the rules in the cell.
    * @return String representation of the rules in the cell.
    */
   public String toString() {
      return this.rules.toString();
   }

   /**
    * Add a rule to the cell and make it open.
    * 
    * @param rule The rule that we want to add to the cell.
    */
   public void add(Rule rule) {
      this.rules.add(rule);
      rule.setOpen();
   }

   /**
    * Set all rules open. 
    */
   public void openAllRules()
   {
      Rule rule;
      for (Iterator it = this.rules.iterator(); it.hasNext(); )
      {
         rule = (Rule) it.next();
         rule.setOpen();
      }
   }

   /**
    * Determines if the cell has open rules.
    * 
    * @return True if the cell has open rules. False, otherwise.
    */
   public boolean hasOpenRules()
   {
      Rule rule;
      for (Iterator it = this.rules.iterator(); it.hasNext(); )
      {
         rule = (Rule) it.next();
         if (rule.isOpen()) return true;
      }
      return false;
   }

   /**
    * Gets the first open rule.
    * 
    * @return The first open rule in the cell.
    */
   public Rule getFirstOpenRule()
   {
      Rule rule;
      for (Iterator it = this.rules.iterator(); it.hasNext(); )
      {
         rule = (Rule) it.next();
         if (rule.isOpen()) return rule;
      }
      return null;      // normally it should never arrive here
   }

   /**
    * Gets all those rules that have a non-terminal after the dot.
    * 
    * @return All those rules that have a non-terminal after the dot.
    */
   public Vector getRulesNonterminalAfterDot()
   {
      Vector v = new Vector();
      Rule rule;
      
      for (Iterator it = this.rules.iterator(); it.hasNext(); )
      {
         rule = (Rule) it.next();
         if (rule.hasNonterminalAfterDot()) v.add(rule);
      }
      return v;
   }

   /**
    * Number of rules in the cell.
    * 
    * @return Number of rules in the cell.
    */
   public int size() {
      return this.rules.size();
   }

   /**
    * Determines if the cell contains a rule.
    * 
    * @param rule The rule that we want to check if it's already in the cell.
    * @return True, if the rule is already in the cell. False, otherwise.
    */
   public boolean contains(Rule rule) {
      return (this.rules.contains(rule));
   }

   /**
    * Gets the rules of the cell.
    * 
    * @return Rules of the cell.
    */
   public Set getRules() {
      return this.rules;
   }

   /**
    * Get all those rules that contain a specific elem after the dot. 
    * 
    * @param letter An elem.
    * @return All those rules that contain the specified elem after the dot.
    */
   public Vector getRulesWithThisElemAfterDot(Elem letter)
   {
      Vector v = new Vector();
      Rule rule;
      
      for (Iterator it = this.getRules().iterator(); it.hasNext(); )
      {
         rule = (Rule) it.next();
         if (rule.hasThisElemAfterDot(letter)) v.add(rule);
      }
      
      return v;
   }

   /**
    * Get all those rules that have a dot at the end.
    * 
    * @return All those rules that have a dot at the end.
    */
   public Vector getRulesWithDotLast()
   {
      Vector v = new Vector();
      Rule rule;
      
      for (Iterator it = this.getRules().iterator(); it.hasNext(); )
      {
         rule = (Rule) it.next();
         if (rule.hasDotLast()) v.add(rule);
      }
      
      return v;
   }
}
