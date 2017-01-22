package jerly.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;

import jerly.datastructure.Cell;
import jerly.datastructure.Question;
import jerly.datastructure.Rule;
import jerly.datastructure.Rules;

/**
 * Produces nice HTML result to see the recognition matrix.
 * 
 * @author  Laszlo Szathmary (<a href="szathmar@loria.fr">szathmar@loria.fr</a>)
 */
public class HtmlResult
{
   private String dirName 		= C.HTML_OUTPUT_DIR;
   private String resultName	= this.dirName + "/result.html";
   private Rules originalRules;
   private BufferedWriter result;
   private Cell[][] table;
   private int tableSize;
   
   /**
    * Constructor.
    * 
    * @param rules
    */
   public HtmlResult(Rules rules)
   {
      this.originalRules = rules;
      //
      boolean ok;
      File dir = new File(dirName);
      
      if (dir.isFile()) Error.die(C.FILE_ALREADY_EXISTS);
      // else, if such a file not yet exists
      if (dir.exists())
      {
         ok = dir.canWrite();
         if (ok == false) Error.die(C.CANNOT_WRITE_DIR);
      }
      else // if the dir. not yet exists
      {
         ok = dir.mkdir();
         if (ok == false) Error.die(C.CANNOT_CREATE_DIR);
      }

      // OK, the output directory is created and writeable
      
      File file = new File(this.resultName);
      if (file.exists())
      {
         ok = file.delete();
         if (ok == false) Error.die(C.CANNOT_DELETE_FILE);
      }
      try
      {
         this.result = new BufferedWriter(new PrintWriter(file));
      }
      catch (FileNotFoundException e) { Error.die(C.CANNOT_CREATE_FILE); }
      
      // OK, we can write the HTML output from here
      
      File style = new File(this.dirName+"/style.css");
      if (style.exists() == false)
      {
         try
         {
            BufferedWriter styleWrite = new BufferedWriter(new PrintWriter(style));
            printStyle(styleWrite);
         }
         catch (FileNotFoundException e) { Error.die(C.CANNOT_CREATE_FILE); }
         catch (IOException ioe) { Error.die(C.IO_WRITE); }
      }
   }

   /**
    * @param file 
    * @throws IOException 
    */
   private void printStyle(BufferedWriter file) throws IOException
   {
      StringBuffer sb = new StringBuffer();
      sb.append(".rule {\n");
      sb.append("   font-family: \"Courier New\", Courier, mono;\n");
      sb.append("   font-weight: bold;\n");
      sb.append("}\n");
      sb.append(".header {\n");
      sb.append("   background-color: #33CCFF;\n");
      sb.append("}\n");
      sb.append(".empty {\n");
      sb.append("   background-color: #CCCCCC;\n");
      sb.append("}\n");
      sb.append(".index {\n");
      sb.append("   font-family: \"Courier New\", Courier, mono;\n");
      sb.append("   color: #33CC33;\n");
      sb.append("}\n");
      sb.append(".section {\n");
      sb.append("   font-family: Arial, Helvetica, sans-serif;\n");
      sb.append("   font-size: x-large;\n");
      sb.append("   //font-weight: bold;\n");
      sb.append("}\n");
      sb.append(".positiv {\n");
      sb.append("   background-color: #66CC33;\n");
      sb.append("   font-family: Arial, Helvetica, sans-serif;\n");
      sb.append("}\n");
      sb.append(".negativ {\n");
      sb.append("   background-color: #FF99CC;\n");
      sb.append("   font-family: Arial, Helvetica, sans-serif;\n");
      sb.append("}\n");
      file.write(sb.toString());
      file.close();
   }

   /**
    * @param table
    * @param question
    * 
    * @return True, if IO write was successful.
    */
   public boolean print(Cell[][] table, Question question)
   {
      this.table = table;
      this.tableSize = question.getLength();
      
      try {
         this.result.write(this.startHtml());   
         printRulesAndQuestion(question);
         this.result.write(this.recognitionMatrix());
         this.result.write(this.endHtml());
         this.result.close();
      }
      catch (IOException e) { Error.die(C.IO_WRITE); }
      
      return true;
   }
   
   /**
    * @param question
    * @throws IOException 
    */
   private void printRulesAndQuestion(Question question) throws IOException
   {
      Cell last = this.table[0][question.getLength()];
      boolean ok = (last.getRulesWithDotLast().isEmpty() == false);
      StringBuffer sb = new StringBuffer();
      Rule rule;
      String ruleStr;
      String color;
      
      sb.append("<span class=\"section\">Rules:</span><br />\n");
      sb.append("<br />\n");
      sb.append("<span class=\"rule\">\n");
      for (Enumeration e = originalRules.getRules().elements(); e.hasMoreElements();)
      {
         rule = (Rule)e.nextElement();
         ruleStr = rule.toString().replaceFirst(">", "&gt;");
         sb.append(ruleStr+"<br />\n");
      }
      sb.append("</span>\n");
      sb.append("<br />\n");
      sb.append("<span class=\"section\">Question:</span><br />\n");
      sb.append("<br />\n");
      sb.append("<span class=\"rule\">"+question.toString()+"</span>").append("<br />\n");
      sb.append("<br />\n");
      sb.append("<span class=\"section\">Result:</span><br />\n");
      sb.append("<br />\n");
      if (ok) color = "<span class=\"positiv\">";
      else    color = "<span class=\"negativ\">";
      sb.append(color+"the word '"+question+"' can"+(ok?"":"NOT")+" be generated with the grammar.</span><br />\n");
      sb.append("<br />\n");
      this.result.write(sb.toString());
   }

   /**
    * @return Beginning of the HTML page.
    */
   private String startHtml()
   {
      StringBuffer sb = new StringBuffer();
      sb.append("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n");
      sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
      sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
      sb.append("<head>\n");
      sb.append("<title>Jerly, (c) Laszlo Szathmary, 2004 (szathmar@loria.fr)</title>\n");
      sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\n");
      sb.append("<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\" />\n");
      sb.append("</head>\n");
      sb.append("<body>\n");
      return sb.toString();
   }

   private String recognitionMatrix()
   {
      StringBuffer sb = new StringBuffer();
      sb.append(buildMainTable());
      return sb.toString();
   }
   
   private String buildMainTable()
   {
      StringBuffer sb = new StringBuffer();
      sb.append("<table width=\"100%\" border=\"1\" cellspacing=\"2\" cellpadding=\"2\">\n");
      sb.append(buildFirstLine());
      for (int i = 0; i < this.table.length; ++i)
      {
         sb.append("  <tr>\n");
         sb.append(buildLine(i));
         sb.append("  </tr>\n");
      }
      sb.append("</table>\n");
      return sb.toString();
   }
   
   private String buildLine(final int line)
   {
      StringBuffer sb = new StringBuffer();
      Cell curr;
      
      sb.append("    <td class=\"header\"><div align=\"center\">"+line+"</div></td>\n");
      for (int i = 0; i < this.table[line].length; ++i)
      {
         curr = this.table[line][i];
         if (curr == null) sb.append("    <td valign=\"top\" class=\"empty\">\n");
         else					
         {
            sb.append("    <td valign=\"top\">\n");
            sb.append(buildCellTable(curr, line, i));
         }
         sb.append("    </td>\n");
      }
      return sb.toString();
   }
   
   private String buildCellTable(Cell curr, final int line, final int column)
   {
      StringBuffer sb = new StringBuffer();
      Rule rule;
      String ruleStr;
      
      sb.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
      sb.append("<tr>\n");
      sb.append("  <td>&nbsp;</td>\n");
      sb.append("  <td><div align=\"right\" class=\"index\"><strong>"+line+","+column+"</strong></div></td>\n");
      sb.append("</tr>\n");
      sb.append("<tr>\n");
      sb.append("  <td valign=\"top\"><span class=\"rule\">\n");
      if (curr!=null)
      for (Iterator it = curr.getRules().iterator(); it.hasNext();)
      {
         rule = (Rule)it.next();
         ruleStr = rule.toString().replaceFirst(">", "&gt;");
         sb.append(ruleStr+"<br />");
      }
      sb.append("  </span></td>\n");
      sb.append("  <td>&nbsp;</td>\n");
      sb.append("</tr>\n");
      sb.append("</table>\n");
      return sb.toString();
   }

   private String buildFirstLine()
   {
      StringBuffer sb = new StringBuffer();
      sb.append("  <tr>\n");
      sb.append("    <td class=\"header\"width=\"1\"><div align=\"center\">Jerly<br />by<br /><em>Jabba&nbsp;Laci</em><br /></div></td>\n");
      for (int i = 0; i <= this.tableSize; ++i)
      {
         sb.append("    <td class=\"header\"><div align=\"center\">"+i+"</div></td>\n");
      }
      sb.append("  </tr>\n");
      return sb.toString();      
   }
   
   /**
    * @return End of the HTML page.
    */
   private String endHtml() {
      return "</body>\n</html>\n";
   }
}
