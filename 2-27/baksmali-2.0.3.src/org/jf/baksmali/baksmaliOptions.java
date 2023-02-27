/*    */ package org.jf.baksmali;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.jf.dexlib2.analysis.ClassPath;
/*    */ import org.jf.dexlib2.analysis.InlineMethodResolver;
/*    */ import org.jf.dexlib2.util.SyntheticAccessorResolver;
/*    */ 
/*    */ public class baksmaliOptions
/*    */ {
/* 54 */   public int apiLevel = 15;
/* 55 */   public String outputDirectory = "out";
/* 56 */   public List<String> bootClassPathDirs = Lists.newArrayList();
/*    */ 
/* 58 */   public List<String> bootClassPathEntries = Lists.newArrayList();
/* 59 */   public List<String> extraClassPathEntries = Lists.newArrayList();
/*    */ 
/* 61 */   public Map<String, String> resourceIdFileEntries = new HashMap();
/* 62 */   public Map<Integer, String> resourceIds = new HashMap();
/*    */ 
/* 64 */   public boolean noParameterRegisters = false;
/* 65 */   public boolean useLocalsDirective = false;
/* 66 */   public boolean useSequentialLabels = false;
/* 67 */   public boolean outputDebugInfo = true;
/* 68 */   public boolean addCodeOffsets = false;
/* 69 */   public boolean noAccessorComments = false;
/* 70 */   public boolean allowOdex = false;
/* 71 */   public boolean deodex = false;
/* 72 */   public boolean ignoreErrors = false;
/* 73 */   public boolean checkPackagePrivateAccess = false;
/* 74 */   public InlineMethodResolver inlineResolver = null;
/* 75 */   public int registerInfo = 0;
/* 76 */   public ClassPath classPath = null;
/* 77 */   public int jobs = -1;
/*    */ 
/* 79 */   public SyntheticAccessorResolver syntheticAccessorResolver = null;
/*    */ 
/*    */   public void setBootClassPath(String bootClassPath) {
/* 82 */     this.bootClassPathEntries = Lists.newArrayList(bootClassPath.split(":"));
/*    */   }
/*    */ 
/*    */   public void addExtraClassPath(String extraClassPath) {
/* 86 */     if (extraClassPath.startsWith(":")) {
/* 87 */       extraClassPath = extraClassPath.substring(1);
/*    */     }
/* 89 */     this.extraClassPathEntries.addAll(Arrays.asList(extraClassPath.split(":")));
/*    */   }
/*    */ 
/*    */   public void setResourceIdFiles(String resourceIdFiles) {
/* 93 */     for (String resourceIdFile : resourceIdFiles.split(":")) {
/* 94 */       String[] entry = resourceIdFile.split("=");
/* 95 */       this.resourceIdFileEntries.put(entry[1], entry[0]);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.baksmaliOptions
 * JD-Core Version:    0.6.0
 */