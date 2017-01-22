package jerly.datastructure;

/**
 * An abstract class for terminals, non-terminals, and the dot.
 * 
 * @author  Laszlo Szathmary (<a href="szathmar@loria.fr">szathmar@loria.fr</a>)
 */
abstract public class Elem
{
   /**
    * A rule in a cell is built up from Elems.
    * In this implementation an Elem is just 1 character.
    */
   private char c;
   
   /**
    * Constructor.
    * 
    * @param c A character.
    */
   Elem(char c) {
      this.c = c;
   }
   
   /**
    * @return String representation of the Elem. 
    */
   public String toString() {
      return ""+this.c;
   }
   
   /**
    * @return The elem.
    */
   public char getElem() {
      return this.c;
   }
   
   /**
    * @param o 
    * @return True, if the elem is equal to another one.
    */
   public boolean equals(Object o)
   {
      if ((o!=null) && (o.getClass().equals(this.getClass())))
      {
         // on
         //System.out.print("comparing elems: ");
         // off
         if (((Elem)o).getElem() == this.getElem()) {
            //System.out.println(""+o+" and "+this+" are same");
            return true;
         }
      }
      //System.out.println(""+o+" and "+this+" are NOT same");
      return false;
   }
}
