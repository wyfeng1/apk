/*     */ package org.jf.dexlib2.analysis;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.jf.dexlib2.DexFileFactory;
/*     */ import org.jf.dexlib2.DexFileFactory.NoClassesDexException;
/*     */ import org.jf.dexlib2.analysis.reflection.ReflectionClassDef;
/*     */ import org.jf.dexlib2.iface.ClassDef;
/*     */ import org.jf.dexlib2.iface.DexFile;
/*     */ import org.jf.dexlib2.immutable.ImmutableDexFile;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class ClassPath
/*     */ {
/*     */   private final TypeProto unknownClass;
/*  59 */   private HashMap<String, ClassDef> availableClasses = Maps.newHashMap();
/*     */   private int api;
/* 125 */   private final CacheLoader<String, TypeProto> classLoader = new CacheLoader() {
/*     */     public TypeProto load(String type) throws Exception {
/* 127 */       if (type.charAt(0) == '[') {
/* 128 */         return new ArrayProto(ClassPath.this, type);
/*     */       }
/* 130 */       return new ClassProto(ClassPath.this, type);
/*     */     }
/* 125 */   };
/*     */ 
/* 135 */   private LoadingCache<String, TypeProto> loadedClasses = CacheBuilder.newBuilder().build(this.classLoader);
/*     */ 
/* 167 */   private static final Pattern dalvikCacheOdexPattern = Pattern.compile("@([^@]+)@classes.dex$");
/*     */ 
/*     */   public ClassPath(Iterable<DexFile> classPath, int api)
/*     */   {
/*  79 */     Iterable dexFiles = Iterables.concat(classPath, Lists.newArrayList(new DexFile[] { getBasicClasses() }));
/*     */ 
/*  81 */     this.unknownClass = new UnknownClassProto(this);
/*  82 */     this.loadedClasses.put(this.unknownClass.getType(), this.unknownClass);
/*  83 */     this.api = api;
/*     */ 
/*  85 */     loadPrimitiveType("Z");
/*  86 */     loadPrimitiveType("B");
/*  87 */     loadPrimitiveType("S");
/*  88 */     loadPrimitiveType("C");
/*  89 */     loadPrimitiveType("I");
/*  90 */     loadPrimitiveType("J");
/*  91 */     loadPrimitiveType("F");
/*  92 */     loadPrimitiveType("D");
/*  93 */     loadPrimitiveType("L");
/*     */ 
/*  95 */     for (DexFile dexFile : dexFiles)
/*  96 */       for (ClassDef classDef : dexFile.getClasses()) {
/*  97 */         ClassDef prev = (ClassDef)this.availableClasses.get(classDef.getType());
/*  98 */         if (prev == null)
/*  99 */           this.availableClasses.put(classDef.getType(), classDef);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void loadPrimitiveType(String type)
/*     */   {
/* 106 */     this.loadedClasses.put(type, new PrimitiveProto(this, type));
/*     */   }
/*     */ 
/*     */   private static DexFile getBasicClasses()
/*     */   {
/* 111 */     return new ImmutableDexFile(ImmutableSet.of(new ReflectionClassDef(Class.class), new ReflectionClassDef(Cloneable.class), new ReflectionClassDef(Object.class), new ReflectionClassDef(Serializable.class), new ReflectionClassDef(String.class), new ReflectionClassDef(Throwable.class), new ReflectionClassDef[0]));
/*     */   }
/*     */ 
/*     */   public TypeProto getClass(CharSequence type)
/*     */   {
/* 122 */     return (TypeProto)this.loadedClasses.getUnchecked(type.toString());
/*     */   }
/*     */ 
/*     */   public ClassDef getClassDef(String type)
/*     */   {
/* 139 */     ClassDef ret = (ClassDef)this.availableClasses.get(type);
/* 140 */     if (ret == null) {
/* 141 */       throw new UnresolvedClassException("Could not resolve class %s", new Object[] { type });
/*     */     }
/* 143 */     return ret;
/*     */   }
/*     */ 
/*     */   public TypeProto getUnknownClass()
/*     */   {
/* 148 */     return this.unknownClass;
/*     */   }
/*     */ 
/*     */   public int getApi() {
/* 152 */     return this.api;
/*     */   }
/*     */ 
/*     */   public static ClassPath fromClassPath(Iterable<String> classPathDirs, Iterable<String> classPath, DexFile dexFile, int api)
/*     */   {
/* 158 */     ArrayList dexFiles = Lists.newArrayList();
/*     */ 
/* 160 */     for (String classPathEntry : classPath) {
/* 161 */       dexFiles.add(loadClassPathEntry(classPathDirs, classPathEntry, api));
/*     */     }
/* 163 */     dexFiles.add(dexFile);
/* 164 */     return new ClassPath(dexFiles, api);
/*     */   }
/*     */ 
/*     */   private static DexFile loadClassPathEntry(Iterable<String> classPathDirs, String bootClassPathEntry, int api)
/*     */   {
/* 172 */     File rawEntry = new File(bootClassPathEntry);
/*     */ 
/* 174 */     String entryName = rawEntry.getName();
/*     */ 
/* 177 */     if (entryName.endsWith("@classes.dex")) {
/* 178 */       Matcher m = dalvikCacheOdexPattern.matcher(entryName);
/*     */ 
/* 180 */       if (!m.find()) {
/* 181 */         throw new ExceptionWithContext(String.format("Cannot parse dependency value %s", new Object[] { bootClassPathEntry }), new Object[0]);
/*     */       }
/*     */ 
/* 184 */       entryName = m.group(1);
/*     */     }
/*     */ 
/* 187 */     int extIndex = entryName.lastIndexOf(".");
/*     */     String baseEntryName;
/*     */     String baseEntryName;
/* 190 */     if (extIndex == -1)
/* 191 */       baseEntryName = entryName;
/*     */     else {
/* 193 */       baseEntryName = entryName.substring(0, extIndex);
/*     */     }
/*     */ 
/* 196 */     for (String classPathDir : classPathDirs) {
/* 197 */       for (String ext : new String[] { "", ".odex", ".jar", ".apk", ".zip" }) {
/* 198 */         File file = new File(classPathDir, baseEntryName + ext);
/*     */ 
/* 200 */         if ((file.exists()) && (file.isFile())) {
/* 201 */           if (!file.canRead())
/* 202 */             System.err.println(String.format("warning: cannot open %s for reading. Will continue looking.", new Object[] { file.getPath() }));
/*     */           else {
/*     */             try
/*     */             {
/* 206 */               return DexFileFactory.loadDexFile(file, api);
/*     */             } catch (DexFileFactory.NoClassesDexException localNoClassesDexException) {
/*     */             }
/*     */             catch (Exception ex) {
/* 210 */               throw ExceptionWithContext.withContext(ex, "Error while reading boot class path entry \"%s\"", new Object[] { bootClassPathEntry });
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 217 */     throw new ExceptionWithContext("Cannot locate boot class path file %s", new Object[] { bootClassPathEntry });
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.ClassPath
 * JD-Core Version:    0.6.0
 */