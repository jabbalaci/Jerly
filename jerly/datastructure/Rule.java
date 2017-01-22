package jerly.datastructure;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Rule class for:
 * 1) a rule of the grammar
 * 2) a dotted rule in the recognition matrix
 * 
 * @author  Laszlo Szathmary (<a href="szathmar@loria.fr">szathmar@loria.fr</a>)
 */
public class Rule implements Cloneable
{
   /**
    * Left side of a rule in the cell.
    */
   private Vector left;
   
   /**
    * Right side of a rule in the cell. 
    */
   private Vector right;
   
   /**
    * Is this rule in the cell open?
    */
   private boolean open;

   /**
    * Constructor.
    * 
    * @param leftRule Left side.
    * @param rightRule Right side.
    */
   public Rule(Vector leftRule, Vector rightRule)
   {
      this.left  = leftRule;
      this.right = rightRule;
   }
   
   /**
    * Determines if two rules are the same. 
    * @param obj 
    * @return True if the rule is equal to another one.
    */
   public boolean equals(Object obj)
   {
      if ((obj!=null) && (obj instanceof Rule)) 
      {
         return ((this.left.equals(((Rule) obj).getLeft())) && 
                (this.right.equals(((Rule) obj).getRight()))); 
      }
      return false;
   }
   
   /**
    * @return Hash value for the rule.
    */
   public int hashCode()
   {
      Enumeration e;
      Elem elem;
      int leftValue  = 0,
          rightValue = 0;
      
      for (e=this.left.elements(); e.hasMoreElements(); )
      {
         elem = (Elem) e.nextElement();
         leftValue += (int)elem.getElem();
      }
      leftValue *= 13;
      
      for (e=this.right.elements(); e.hasMoreElements(); )
      {
         elem = (Elem) e.nextElement();
         rightValue += (int)elem.getElem();
      }
      rightValue *= 23;
      
      return (leftValue + rightValue);
   }
   
   /**
    * @return Left side of the rule.
    */
   public Vector getLeft() {
      return this.left;
   }
   
   /**
    * @return Right side of the rule.
    */
   public Vector getRight() {
      return this.right;
   }
   
   /**
    * Sets the right side of the rule.
    * 
    * @param right
    */
   public void setRight(Vector right) {
      this.right = right;
   }
   
   /**
    * @return A copy of the rule.
    */
   public Object clone() 
   {
      Rule copy = null;
      
      try
      {
         copy = (Rule)super.clone();
         copy.left = (Vector) this.left.clone();
         copy.right = (Vector) this.right.clone();
      }
      catch (CloneNotSupportedException e) {
         e.printStackTrace();
      }
      
      return copy;
   }
   
   /**
    * @return True, if the rule is open. False, otherwise.
    */
   public boolean isOpen() {
      return this.open;
   }

   /**
    * @return String representation of the rule.
    */
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      Enumeration e;
      Elem elem;
      
      for (e = this.left.elements(); e.hasMoreElements(); )
      {
         elem = (Elem) e.nextElement();
         sb.append(elem.toString());
      }
      sb.append("->");
      for (e = this.right.elements(); e.hasMoreElements(); )
      {
         elem = (Elem) e.nextElement();
         sb.append(elem.toString());
      }
      return sb.toString();
   }

   /**
    * @return True, if there is an 'S' symbol on the left side of the rule.
    * False, otherwise.
    */
   public boolean isLeftS()
   {
      if ((this.left != null) && (this.left.size()==1))
      {
         char c = ((Elem) this.left.get(0)).getElem();
         if (c == 'S') return true;
      }
      return false;
   }

   /**
    * Adds a dot to the beginning of the right side of the rule.
    * 
    * @return The new rule in which the right side of the rule has
    * a dot at its beginning.
    */
   public Rule addDotToRight()
   {
      Rule result = (Rule) this.clone();
      
      Vector newRight = new Vector();
      newRight.add(new Dot());
      newRight.addAll(result.getRight());
      result.setRight(newRight);
      
      return result;
   }

   /**
    * Makes the rule open.
    */
   public void setOpen() {
      this.open = true;
   }

   /**
    * @return True, if the rule has a non-terminal after the dot.
    */
   public boolean hasNonterminalAfterDot()
   {
      Vector right = this.getRight();
      
      Elem elem = null;
      int index;
      for (Enumeration e = right.elements(); e.hasMoreElements(); )
      {
         elem = (Elem) e.nextElement();
         if (elem instanceof Dot) break;
      }
      if (elem != null)    // dot was found
      {
         Elem after = null;
         int indexDot = right.indexOf(elem);
         try {
            after = (Elem) right.get(indexDot+1);
         } catch(ArrayIndexOutOfBoundsException e) {};
         if ((after != null) && (after instanceof Nonterminal)) return true; 
      }
      
      return false;
   }

   /**
    * Makes a rule closed.
    */
   public void close() {
      this.open = false;
   }

   /**
    * @return The non-terminal right after the dot.
    */
   public Nonterminal getNonterminalAfterDot()
   {
      Elem elem = this.getElemAfterDot();
      
      if ((elem!=null) && (elem instanceof Nonterminal)) return (Nonterminal) elem;
      
      return null;
   }
   
   /**
    * @return The terminal right after the dot.
    */
   public Terminal getTerminalAfterDot()
   {
      Elem elem = this.getElemAfterDot();
      
      if ((elem!=null) && (elem instanceof Terminal)) return (Terminal) elem;
      
      return null;
   }
   
   /**
    * @return The elem right after the dot.
    */
   public Elem getElemAfterDot()
   {
      Vector right = this.getRight();
      
      Elem elem = null;
      int index;
      for (Enumeration e = right.elements(); e.hasMoreElements(); )
      {
         elem = (Elem) e.nextElement();
         if (elem instanceof Dot) break;
      }
      if (elem != null)    // dot was found
      {
         Elem after = null;
         int indexDot = right.indexOf(elem);
         try {
            after = (Elem) right.get(indexDot+1);
         } catch(ArrayIndexOutOfBoundsException e) {};
         if (after != null) return after; 
      }
      
      return null;
   }

   /**
    * Determines if the rule has the specified elem right
    * after the dot.
    * 
    * @param elem The elem we are looking for right after the dot.
    * @return True, if the rule contains the specified elem 
    * right after the dot.
    */
   public boolean hasThisElemAfterDot(Elem elem)
   {
      Elem afterDot = this.getElemAfterDot();
      
      if ((afterDot != null) && (afterDot.equals(elem))) return true;
      
      return false;
   }

   /**
    * Moves the dot one position right and returns the new 
    * rule.
    * 
    * @return The new rule in which the dot is moved one position right.
    */
   public Rule moveDotRight()
   {
      Rule rule = (Rule) this.clone();
      
      Elem letter = rule.getElemAfterDot();
      if (letter == null) return rule;									// dot is already at the last position
      int  letterIndex = rule.getRight().indexOf(letter);
      
      Elem dot = (Elem) rule.getRight().get(letterIndex-1);
      
      rule.getRight().setElementAt(letter, letterIndex-1);		// write letter to the position of dot
      rule.getRight().setElementAt(dot, letterIndex);				// moving dot one right
      
      return rule;
   }

   /**
    * @return True, if the rule has the dot at the last position. False, otherwise.
    */
   public boolean hasDotLast()
   {
      Vector right = this.getRight();
      
      Elem last = (Elem) right.get(right.size()-1);
      
      return (last instanceof Dot);
   }

   /**
    * @return The first letter (non-terminal) on the left 
    * side of the rule.
    */
   public Nonterminal getLeftLetter() {
      return (Nonterminal) (this.getLeft().get(0));
   }
}
