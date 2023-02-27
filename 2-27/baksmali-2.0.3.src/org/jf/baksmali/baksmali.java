/*     */ package org.jf.baksmali;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Ordering;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Writer;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.jf.baksmali.Adaptors.ClassDefinition;
/*     */ import org.jf.dexlib2.analysis.ClassPath;
/*     */ import org.jf.dexlib2.iface.ClassDef;
/*     */ import org.jf.dexlib2.iface.DexFile;
/*     */ import org.jf.dexlib2.util.SyntheticAccessorResolver;
/*     */ import org.jf.util.ClassFileNameHandler;
/*     */ import org.jf.util.IndentingWriter;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ public class baksmali
/*     */ {
/*     */   public static boolean disassembleDexFile(DexFile dexFile, baksmaliOptions options)
/*     */   {
/*  58 */     if ((options.registerInfo != 0) || (options.deodex)) {
/*     */       try
/*     */       {
/*     */         Iterable extraClassPathEntries;
/*     */         Iterable extraClassPathEntries;
/*  61 */         if (options.extraClassPathEntries != null)
/*  62 */           extraClassPathEntries = options.extraClassPathEntries;
/*     */         else {
/*  64 */           extraClassPathEntries = ImmutableList.of();
/*     */         }
/*     */ 
/*  67 */         options.classPath = ClassPath.fromClassPath(options.bootClassPathDirs, Iterables.concat(options.bootClassPathEntries, extraClassPathEntries), dexFile, options.apiLevel);
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*  71 */         System.err.println("\n\nError occurred while loading boot class path files. Aborting.");
/*  72 */         ex.printStackTrace(System.err);
/*  73 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*  77 */     if (options.resourceIdFileEntries != null)
/*     */     {
/* 103 */       for (Map.Entry entry : options.resourceIdFileEntries.entrySet()) {
/*     */         try {
/* 105 */           SAXParser saxp = SAXParserFactory.newInstance().newSAXParser();
/* 106 */           String prefix = (String)entry.getValue();
/* 107 */           saxp.parse((String)entry.getKey(), new DefaultHandler(prefix, options)
/*     */           {
/*     */             public void startElement(String uri, String localName, String qName, Attributes attr)
/*     */               throws SAXException
/*     */             {
/*  87 */               if (qName.equals("public")) {
/*  88 */                 String type = attr.getValue("type");
/*  89 */                 String name = attr.getValue("name").replace('.', '_');
/*  90 */                 Integer public_key = Integer.decode(attr.getValue("id"));
/*  91 */                 String public_val = baksmali.this + "." + type + "." + name;
/*     */ 
/*  98 */                 this.val$options.resourceIds.put(public_key, public_val);
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           });
/*     */         }
/*     */         catch (ParserConfigurationException e)
/*     */         {
/* 109 */           continue;
/*     */         } catch (SAXException e) {
/* 111 */           continue; } catch (IOException e) {
/*     */         }
/* 113 */         continue;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 118 */     File outputDirectoryFile = new File(options.outputDirectory);
/* 119 */     if ((!outputDirectoryFile.exists()) && 
/* 120 */       (!outputDirectoryFile.mkdirs())) {
/* 121 */       System.err.println("Can't create the output directory " + options.outputDirectory);
/* 122 */       return false;
/*     */     }
/*     */ 
/* 130 */     List classDefs = Ordering.natural().sortedCopy(dexFile.getClasses());
/*     */ 
/* 132 */     if (!options.noAccessorComments) {
/* 133 */       options.syntheticAccessorResolver = new SyntheticAccessorResolver(classDefs);
/*     */     }
/*     */ 
/* 136 */     ClassFileNameHandler fileNameHandler = new ClassFileNameHandler(outputDirectoryFile, ".smali");
/*     */ 
/* 138 */     ExecutorService executor = Executors.newFixedThreadPool(options.jobs);
/* 139 */     List tasks = Lists.newArrayList();
/*     */ 
/* 141 */     for (ClassDef classDef : classDefs) {
/* 142 */       tasks.add(executor.submit(new Callable(classDef, fileNameHandler, options) {
/*     */         public Boolean call() throws Exception {
/* 144 */           return Boolean.valueOf(baksmali.access$000(this.val$classDef, this.val$fileNameHandler, this.val$options));
/*     */         }
/*     */       }));
/*     */     }
/* 149 */     boolean errorOccurred = false;
/*     */     try {
/* 151 */       for (Future task : tasks) {
/*     */         try
/*     */         {
/* 154 */           if (!((Boolean)task.get()).booleanValue())
/* 155 */             errorOccurred = true;
/*     */         }
/*     */         catch (InterruptedException ex) {
/* 158 */           break label391;
/*     */         } catch (ExecutionException ex) {
/* 160 */           throw new RuntimeException(ex);
/*     */         }
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 166 */       label391: executor.shutdown();
/*     */     }
/* 168 */     return !errorOccurred;
/*     */   }
/*     */ 
/*     */   private static boolean disassembleClass(ClassDef classDef, ClassFileNameHandler fileNameHandler, baksmaliOptions options)
/*     */   {
/* 180 */     String classDescriptor = classDef.getType();
/*     */ 
/* 183 */     if ((classDescriptor.charAt(0) != 'L') || (classDescriptor.charAt(classDescriptor.length() - 1) != ';'))
/*     */     {
/* 185 */       System.err.println("Unrecognized class descriptor - " + classDescriptor + " - skipping class");
/* 186 */       return false;
/*     */     }
/*     */ 
/* 189 */     File smaliFile = fileNameHandler.getUniqueFilenameForClass(classDescriptor);
/*     */ 
/* 192 */     ClassDefinition classDefinition = new ClassDefinition(options, classDef);
/*     */ 
/* 195 */     Writer writer = null;
/*     */     try
/*     */     {
/* 198 */       File smaliParent = smaliFile.getParentFile();
/*     */       int i;
/* 199 */       if ((!smaliParent.exists()) && 
/* 200 */         (!smaliParent.mkdirs()))
/*     */       {
/* 202 */         if (!smaliParent.exists()) {
/* 203 */           System.err.println("Unable to create directory " + smaliParent.toString() + " - skipping class");
/* 204 */           i = 0;
/*     */           return i;
/*     */         }
/*     */       }
/* 209 */       if ((!smaliFile.exists()) && 
/* 210 */         (!smaliFile.createNewFile())) {
/* 211 */         System.err.println("Unable to create file " + smaliFile.toString() + " - skipping class");
/* 212 */         i = 0;
/*     */         return i;
/*     */       }
/* 216 */       bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(smaliFile), "UTF8"));
/*     */ 
/* 219 */       writer = new IndentingWriter(bufWriter);
/* 220 */       classDefinition.writeTo((IndentingWriter)writer);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*     */       BufferedWriter bufWriter;
/* 222 */       System.err.println("\n\nError occurred while disassembling class " + classDescriptor.replace('/', '.') + " - skipping class");
/* 223 */       ex.printStackTrace();
/*     */ 
/* 225 */       smaliFile.delete();
/* 226 */       int j = 0;
/*     */       return j;
/*     */     }
/*     */     finally
/*     */     {
/* 230 */       if (writer != null) {
/*     */         try {
/* 232 */           writer.close();
/*     */         } catch (Throwable ex) {
/* 234 */           System.err.println("\n\nError occurred while closing file " + smaliFile.toString());
/* 235 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 239 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.baksmali
 * JD-Core Version:    0.6.0
 */