/*     */ package org.jf.baksmali.Adaptors;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.jf.dexlib2.util.SyntheticAccessorResolver.AccessedMember;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ import org.jf.util.IndentingWriter;
/*     */ 
/*     */ public class SyntheticAccessCommentMethodItem extends MethodItem
/*     */ {
/*     */   private final SyntheticAccessorResolver.AccessedMember accessedMember;
/*     */ 
/*     */   public SyntheticAccessCommentMethodItem(SyntheticAccessorResolver.AccessedMember accessedMember, int codeAddress)
/*     */   {
/*  42 */     super(codeAddress);
/*  43 */     this.accessedMember = accessedMember;
/*     */   }
/*     */ 
/*     */   public double getSortOrder()
/*     */   {
/*  48 */     return 99.799999999999997D;
/*     */   }
/*     */ 
/*     */   public boolean writeTo(IndentingWriter writer) throws IOException {
/*  52 */     writer.write("# ");
/*  53 */     switch (this.accessedMember.accessedMemberType) {
/*     */     case 0:
/*  55 */       writer.write("invokes: ");
/*  56 */       break;
/*     */     case 1:
/*  58 */       writer.write("getter for: ");
/*  59 */       break;
/*     */     case 2:
/*  61 */       writer.write("setter for: ");
/*  62 */       break;
/*     */     case 4:
/*  64 */       writer.write("++operator for: ");
/*  65 */       break;
/*     */     case 3:
/*  67 */       writer.write("operator++ for: ");
/*  68 */       break;
/*     */     case 6:
/*  70 */       writer.write("--operator for: ");
/*  71 */       break;
/*     */     case 5:
/*  73 */       writer.write("operator-- for: ");
/*  74 */       break;
/*     */     case 7:
/*  76 */       writer.write("+= operator for: ");
/*  77 */       break;
/*     */     case 8:
/*  79 */       writer.write("-= operator for: ");
/*  80 */       break;
/*     */     case 9:
/*  82 */       writer.write("*= operator for: ");
/*  83 */       break;
/*     */     case 10:
/*  85 */       writer.write("/= operator for: ");
/*  86 */       break;
/*     */     case 11:
/*  88 */       writer.write("%= operator for: ");
/*  89 */       break;
/*     */     case 12:
/*  91 */       writer.write("&= operator for: ");
/*  92 */       break;
/*     */     case 13:
/*  94 */       writer.write("|= operator for: ");
/*  95 */       break;
/*     */     case 14:
/*  97 */       writer.write("^= operator for: ");
/*  98 */       break;
/*     */     case 15:
/* 100 */       writer.write("<<= operator for: ");
/* 101 */       break;
/*     */     case 16:
/* 103 */       writer.write(">>= operator for: ");
/* 104 */       break;
/*     */     case 17:
/* 106 */       writer.write(">>>= operator for: ");
/* 107 */       break;
/*     */     default:
/* 109 */       throw new ExceptionWithContext("Unknown access type: %d", new Object[] { Integer.valueOf(this.accessedMember.accessedMemberType) });
/*     */     }
/*     */     int referenceType;
/*     */     int referenceType;
/* 113 */     if (this.accessedMember.accessedMemberType == 0)
/* 114 */       referenceType = 3;
/*     */     else {
/* 116 */       referenceType = 2;
/*     */     }
/* 118 */     ReferenceFormatter.writeReference(writer, referenceType, this.accessedMember.accessedMember);
/* 119 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.SyntheticAccessCommentMethodItem
 * JD-Core Version:    0.6.0
 */