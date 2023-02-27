/*    */ package org.jf.baksmali;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.io.Writer;
/*    */ import org.jf.dexlib2.Opcodes;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.raw.RawDexFile;
/*    */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*    */ import org.jf.util.ConsoleUtil;
/*    */ 
/*    */ public class dump
/*    */ {
/*    */   public static void dump(DexBackedDexFile dexFile, String dumpFileName, int apiLevel)
/*    */     throws IOException
/*    */   {
/* 44 */     if (dumpFileName != null) {
/* 45 */       Writer writer = null;
/*    */       try
/*    */       {
/* 48 */         writer = new BufferedWriter(new FileWriter(dumpFileName));
/*    */ 
/* 50 */         int consoleWidth = ConsoleUtil.getConsoleWidth();
/* 51 */         if (consoleWidth <= 0) {
/* 52 */           consoleWidth = 120;
/*    */         }
/*    */ 
/* 55 */         RawDexFile rawDexFile = new RawDexFile(new Opcodes(apiLevel), dexFile);
/* 56 */         DexAnnotator annotator = new DexAnnotator(rawDexFile, consoleWidth);
/* 57 */         annotator.writeAnnotations(writer);
/*    */       } catch (IOException ex) {
/* 59 */         System.err.println("There was an error while dumping the dex file to " + dumpFileName);
/* 60 */         ex.printStackTrace(System.err);
/*    */       } finally {
/* 62 */         if (writer != null)
/*    */           try {
/* 64 */             writer.close();
/*    */           } catch (IOException ex) {
/* 66 */             System.err.println("There was an error while closing the dump file " + dumpFileName);
/* 67 */             ex.printStackTrace(System.err);
/*    */           }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.dump
 * JD-Core Version:    0.6.0
 */