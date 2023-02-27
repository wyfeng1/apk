/*    */ package org.jf.baksmali.Adaptors;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class CommentingIndentingWriter extends IndentingWriter
/*    */ {
/*    */   public CommentingIndentingWriter(Writer writer)
/*    */   {
/* 41 */     super(writer);
/*    */   }
/*    */ 
/*    */   protected void writeIndent() throws IOException {
/* 45 */     this.writer.write("# ");
/* 46 */     super.writeIndent();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.CommentingIndentingWriter
 * JD-Core Version:    0.6.0
 */