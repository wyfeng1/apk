/*    */ package org.jf.util;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
/*    */ import org.apache.commons.cli.HelpFormatter;
/*    */ import org.apache.commons.cli.Options;
/*    */ 
/*    */ public class SmaliHelpFormatter extends HelpFormatter
/*    */ {
/*    */   public void printHelp(String cmdLineSyntax, String header, Options options, Options debugOptions)
/*    */   {
/* 38 */     super.printHelp(cmdLineSyntax, header, options, "");
/* 39 */     if (debugOptions != null) {
/* 40 */       System.out.println();
/* 41 */       System.out.println("Debug Options:");
/* 42 */       PrintWriter pw = new PrintWriter(System.out);
/* 43 */       super.printOptions(pw, getWidth(), debugOptions, getLeftPadding(), getDescPadding());
/* 44 */       pw.flush();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.util.SmaliHelpFormatter
 * JD-Core Version:    0.6.0
 */