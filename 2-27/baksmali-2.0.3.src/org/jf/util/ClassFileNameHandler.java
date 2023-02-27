/*     */ package org.jf.util;
/*     */ 
/*     */ import ds.tree.RadixTree;
/*     */ import ds.tree.RadixTreeImpl;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.nio.CharBuffer;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class ClassFileNameHandler
/*     */ {
/*     */   private PackageNameEntry top;
/*     */   private String fileExtension;
/*     */   private boolean modifyWindowsReservedFilenames;
/* 152 */   private static Pattern reservedFileNameRegex = Pattern.compile("^CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9]$", 2);
/*     */ 
/*     */   public ClassFileNameHandler(File path, String fileExtension)
/*     */   {
/*  53 */     this.top = new PackageNameEntry(path);
/*  54 */     this.fileExtension = fileExtension;
/*  55 */     this.modifyWindowsReservedFilenames = testForWindowsReservedFileNames(path);
/*     */   }
/*     */ 
/*     */   public File getUniqueFilenameForClass(String className)
/*     */   {
/*  61 */     if ((className.charAt(0) != 'L') || (className.charAt(className.length() - 1) != ';')) {
/*  62 */       throw new RuntimeException("Not a valid dalvik class name");
/*     */     }
/*     */ 
/*  65 */     int packageElementCount = 1;
/*  66 */     for (int i = 1; i < className.length() - 1; i++) {
/*  67 */       if (className.charAt(i) == '/') {
/*  68 */         packageElementCount++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  73 */     String[] packageElements = new String[packageElementCount];
/*  74 */     int elementIndex = 0;
/*  75 */     int elementStart = 1;
/*  76 */     for (int i = 1; i < className.length() - 1; i++) {
/*  77 */       if (className.charAt(i) != '/') {
/*     */         continue;
/*     */       }
/*  80 */       if (i - elementStart == 0) {
/*  81 */         throw new RuntimeException("Not a valid dalvik class name");
/*     */       }
/*     */ 
/*  84 */       String packageElement = className.substring(elementStart, i);
/*     */ 
/*  86 */       if ((this.modifyWindowsReservedFilenames) && (isReservedFileName(packageElement))) {
/*  87 */         packageElement = packageElement + "#";
/*     */       }
/*     */ 
/*  90 */       if (packageElement.length() > 245) {
/*  91 */         packageElement = shortenPathComponent(packageElement, 245);
/*     */       }
/*     */ 
/*  94 */       packageElements[(elementIndex++)] = packageElement;
/*  95 */       i++; elementStart = i;
/*     */     }
/*     */ 
/* 103 */     if (elementStart >= className.length() - 1) {
/* 104 */       throw new RuntimeException("Not a valid dalvik class name");
/*     */     }
/*     */ 
/* 107 */     String packageElement = className.substring(elementStart, className.length() - 1);
/* 108 */     if ((this.modifyWindowsReservedFilenames) && (isReservedFileName(packageElement))) {
/* 109 */       packageElement = packageElement + "#";
/*     */     }
/*     */ 
/* 112 */     if (packageElement.length() + this.fileExtension.length() > 245) {
/* 113 */       packageElement = shortenPathComponent(packageElement, 245 - this.fileExtension.length());
/*     */     }
/*     */ 
/* 116 */     packageElements[elementIndex] = packageElement;
/*     */ 
/* 118 */     return this.top.addUniqueChild(packageElements, 0);
/*     */   }
/*     */ 
/*     */   static String shortenPathComponent(String pathComponent, int maxLength)
/*     */   {
/* 123 */     int toRemove = pathComponent.length() - maxLength + 1;
/*     */ 
/* 125 */     int firstIndex = pathComponent.length() / 2 - toRemove / 2;
/* 126 */     return pathComponent.substring(0, firstIndex) + "#" + pathComponent.substring(firstIndex + toRemove);
/*     */   }
/*     */ 
/*     */   private static boolean testForWindowsReservedFileNames(File path) {
/* 130 */     String[] reservedNames = { "aux", "con", "com1", "com9", "lpt1", "com9" };
/*     */ 
/* 132 */     for (String reservedName : reservedNames) {
/* 133 */       File f = new File(path, reservedName + ".smali");
/* 134 */       if (f.exists()) {
/*     */         continue;
/*     */       }
/*     */       try
/*     */       {
/* 139 */         FileWriter writer = new FileWriter(f);
/* 140 */         writer.write("test");
/* 141 */         writer.flush();
/* 142 */         writer.close();
/* 143 */         f.delete();
/*     */       }
/*     */       catch (IOException ex) {
/* 146 */         return true;
/*     */       }
/*     */     }
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   private static boolean isReservedFileName(String className)
/*     */   {
/* 155 */     return reservedFileNameRegex.matcher(className).matches();
/*     */   }
/*     */ 
/*     */   private class ClassNameEntry extends ClassFileNameHandler.FileSystemEntry
/*     */   {
/*     */     public ClassNameEntry(File parent, String name)
/*     */     {
/* 380 */       super(new File(parent, name));
/*     */     }
/*     */ 
/*     */     public File addUniqueChild(String[] pathElements, int pathElementsIndex)
/*     */     {
/* 385 */       if (!$assertionsDisabled) throw new AssertionError();
/* 386 */       return this.file;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class VirtualGroupEntry extends ClassFileNameHandler.FileSystemEntry
/*     */   {
/* 237 */     private RadixTree<ClassFileNameHandler.FileSystemEntry> groupEntries = new RadixTreeImpl();
/*     */ 
/* 243 */     private int isCaseSensitive = -1;
/*     */ 
/*     */     public VirtualGroupEntry(ClassFileNameHandler.FileSystemEntry firstChild, File parent) {
/* 246 */       super(parent);
/*     */ 
/* 249 */       this.groupEntries.insert(firstChild.file.getName(), firstChild);
/*     */     }
/*     */ 
/*     */     public File addUniqueChild(String[] pathElements, int pathElementsIndex)
/*     */     {
/* 254 */       String elementName = pathElements[pathElementsIndex];
/*     */ 
/* 256 */       if (pathElementsIndex == pathElements.length - 1) {
/* 257 */         elementName = elementName + ClassFileNameHandler.this.fileExtension;
/*     */       }
/*     */ 
/* 260 */       ClassFileNameHandler.FileSystemEntry existingEntry = (ClassFileNameHandler.FileSystemEntry)this.groupEntries.find(elementName);
/* 261 */       if (existingEntry != null) {
/* 262 */         if (pathElementsIndex == pathElements.length - 1) {
/* 263 */           return existingEntry.file;
/*     */         }
/* 265 */         return existingEntry.addUniqueChild(pathElements, pathElementsIndex + 1);
/*     */       }
/*     */ 
/* 269 */       if (pathElementsIndex == pathElements.length - 1)
/*     */       {
/*     */         String fileName;
/*     */         String fileName;
/* 271 */         if (!isCaseSensitive())
/* 272 */           fileName = pathElements[pathElementsIndex] + "." + (this.groupEntries.getSize() + 1L) + ClassFileNameHandler.this.fileExtension;
/*     */         else {
/* 274 */           fileName = elementName;
/*     */         }
/*     */ 
/* 277 */         ClassFileNameHandler.ClassNameEntry classNameEntry = new ClassFileNameHandler.ClassNameEntry(ClassFileNameHandler.this, this.file, fileName);
/* 278 */         this.groupEntries.insert(elementName, classNameEntry);
/* 279 */         return classNameEntry.file;
/*     */       }
/*     */       String fileName;
/*     */       String fileName;
/* 282 */       if (!isCaseSensitive())
/* 283 */         fileName = pathElements[pathElementsIndex] + "." + (this.groupEntries.getSize() + 1L);
/*     */       else {
/* 285 */         fileName = elementName;
/*     */       }
/*     */ 
/* 288 */       ClassFileNameHandler.PackageNameEntry packageNameEntry = new ClassFileNameHandler.PackageNameEntry(ClassFileNameHandler.this, this.file, fileName);
/* 289 */       this.groupEntries.insert(elementName, packageNameEntry);
/* 290 */       return packageNameEntry.addUniqueChild(pathElements, pathElementsIndex + 1);
/*     */     }
/*     */ 
/*     */     private boolean isCaseSensitive()
/*     */     {
/* 295 */       if (this.isCaseSensitive != -1) {
/* 296 */         return this.isCaseSensitive == 1;
/*     */       }
/*     */ 
/* 299 */       File path = this.file;
/*     */ 
/* 301 */       if ((path.exists()) && (path.isFile())) {
/* 302 */         path = path.getParentFile();
/*     */       }
/*     */ 
/* 305 */       if ((!this.file.exists()) && (!this.file.mkdirs())) {
/* 306 */         return false;
/*     */       }
/*     */       try
/*     */       {
/* 310 */         boolean result = testCaseSensitivity(path);
/* 311 */         this.isCaseSensitive = (result ? 1 : 0);
/* 312 */         return result; } catch (IOException ex) {
/*     */       }
/* 314 */       return false;
/*     */     }
/* 319 */     private boolean testCaseSensitivity(File path) throws IOException { int num = 1;
/*     */       File f;
/*     */       File f2;
/*     */       do { f = new File(path, "test." + num);
/* 323 */         f2 = new File(path, "TEST." + num++); }
/* 324 */       while ((f.exists()) || (f2.exists()));
/*     */       try
/*     */       {
/*     */         try {
/* 328 */           FileWriter writer = new FileWriter(f);
/* 329 */           writer.write("test");
/* 330 */           writer.flush();
/* 331 */           writer.close(); } catch (IOException ex) {
/*     */           try {
/* 333 */             f.delete(); } catch (Exception localException) {
/* 334 */           }throw ex;
/*     */         }
/*     */ 
/* 337 */         if (f2.exists()) {
/* 338 */           ex = 0;
/*     */           return ex;
/*     */         }
/* 341 */         if (f2.createNewFile()) {
/* 342 */           ex = 1;
/*     */           return ex;
/*     */         }
/*     */         try
/*     */         {
/* 349 */           CharBuffer buf = CharBuffer.allocate(32);
/* 350 */           reader = new FileReader(f2);
/*     */ 
/* 352 */           while ((reader.read(buf) != -1) && (buf.length() < 4));
/* 353 */           if ((buf.length() == 4) && (buf.toString().equals("test"))) {
/* 354 */             i = 0;
/*     */             try
/*     */             {
/* 367 */               f.delete(); } catch (Exception localException5) {
/*     */             }try { f2.delete(); } catch (Exception localException6) {  }
/* 368 */             return i;
/*     */           }
/* 360 */           if (!$assertionsDisabled) throw new AssertionError();
/* 361 */           int i = 0;
/*     */           try
/*     */           {
/* 367 */             f.delete(); } catch (Exception localException7) {
/*     */           }try { f2.delete(); } catch (Exception localException8) {  }
/* 368 */           return i;
/*     */         }
/*     */         catch (FileNotFoundException ex)
/*     */         {
/* 364 */           FileReader reader = 1;
/*     */           try
/*     */           {
/* 367 */             f.delete(); } catch (Exception localException13) {
/*     */           }try { f2.delete(); } catch (Exception localException14) {  }
/* 368 */           return reader;
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 367 */           f.delete(); } catch (Exception localException11) {
/*     */         }try { f2.delete(); } catch (Exception localException12) {  }
/* 368 */       }throw localObject;
/*     */     }
/*     */ 
/*     */     public ClassFileNameHandler.FileSystemEntry makeVirtual(File parent)
/*     */     {
/* 374 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class PackageNameEntry extends ClassFileNameHandler.FileSystemEntry
/*     */   {
/* 175 */     private RadixTree<ClassFileNameHandler.FileSystemEntry> children = new RadixTreeImpl();
/*     */ 
/*     */     public PackageNameEntry(File parent, String name) {
/* 178 */       super(new File(parent, name));
/*     */     }
/*     */ 
/*     */     public PackageNameEntry(File path) {
/* 182 */       super(path);
/*     */     }
/*     */ 
/*     */     public synchronized File addUniqueChild(String[] pathElements, int pathElementsIndex)
/*     */     {
/*     */       String elementName;
/* 190 */       if (pathElementsIndex == pathElements.length - 1) {
/* 191 */         String elementName = pathElements[pathElementsIndex];
/* 192 */         elementName = elementName + ClassFileNameHandler.this.fileExtension;
/*     */       } else {
/* 194 */         elementName = pathElements[pathElementsIndex];
/*     */       }
/* 196 */       String elementNameLower = elementName.toLowerCase();
/*     */ 
/* 198 */       ClassFileNameHandler.FileSystemEntry existingEntry = (ClassFileNameHandler.FileSystemEntry)this.children.find(elementNameLower);
/* 199 */       if (existingEntry != null) {
/* 200 */         ClassFileNameHandler.FileSystemEntry virtualEntry = existingEntry;
/*     */ 
/* 203 */         if (!(existingEntry instanceof ClassFileNameHandler.VirtualGroupEntry)) {
/* 204 */           if (existingEntry.file.getName().equals(elementName)) {
/* 205 */             if (pathElementsIndex == pathElements.length - 1) {
/* 206 */               return existingEntry.file;
/*     */             }
/* 208 */             return existingEntry.addUniqueChild(pathElements, pathElementsIndex + 1);
/*     */           }
/*     */ 
/* 211 */           virtualEntry = existingEntry.makeVirtual(this.file);
/* 212 */           this.children.replace(elementNameLower, virtualEntry);
/*     */         }
/*     */ 
/* 216 */         return virtualEntry.addUniqueChild(pathElements, pathElementsIndex);
/*     */       }
/*     */ 
/* 219 */       if (pathElementsIndex == pathElements.length - 1) {
/* 220 */         ClassFileNameHandler.ClassNameEntry classNameEntry = new ClassFileNameHandler.ClassNameEntry(ClassFileNameHandler.this, this.file, elementName);
/* 221 */         this.children.insert(elementNameLower, classNameEntry);
/* 222 */         return classNameEntry.file;
/*     */       }
/* 224 */       PackageNameEntry packageNameEntry = new PackageNameEntry(ClassFileNameHandler.this, this.file, elementName);
/* 225 */       this.children.insert(elementNameLower, packageNameEntry);
/* 226 */       return packageNameEntry.addUniqueChild(pathElements, pathElementsIndex + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private abstract class FileSystemEntry
/*     */   {
/*     */     public final File file;
/*     */ 
/*     */     public FileSystemEntry(File file)
/*     */     {
/* 162 */       this.file = file;
/*     */     }
/*     */     public abstract File addUniqueChild(String[] paramArrayOfString, int paramInt);
/*     */ 
/*     */     public FileSystemEntry makeVirtual(File parent) {
/* 168 */       return new ClassFileNameHandler.VirtualGroupEntry(ClassFileNameHandler.this, this, parent);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.ClassFileNameHandler
 * JD-Core Version:    0.6.0
 */