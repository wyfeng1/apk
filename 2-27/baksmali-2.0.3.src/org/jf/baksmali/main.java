/*     */ package org.jf.baksmali;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import org.apache.commons.cli.CommandLine;
/*     */ import org.apache.commons.cli.CommandLineParser;
/*     */ import org.apache.commons.cli.Option;
/*     */ import org.apache.commons.cli.OptionBuilder;
/*     */ import org.apache.commons.cli.Options;
/*     */ import org.apache.commons.cli.ParseException;
/*     */ import org.apache.commons.cli.PosixParser;
/*     */ import org.jf.dexlib2.DexFileFactory;
/*     */ import org.jf.dexlib2.analysis.CustomInlineMethodResolver;
/*     */ import org.jf.dexlib2.analysis.InlineMethodResolver;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedOdexFile;
/*     */ import org.jf.util.ConsoleUtil;
/*     */ import org.jf.util.SmaliHelpFormatter;
/*     */ 
/*     */ public class main
/*     */ {
/*     */   public static final String VERSION;
/*     */   private static final Options basicOptions;
/*     */   private static final Options debugOptions;
/*     */   private static final Options options;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException
/*     */   {
/*  89 */     Locale locale = new Locale("en", "US");
/*  90 */     Locale.setDefault(locale);
/*     */ 
/*  92 */     CommandLineParser parser = new PosixParser();
/*     */     CommandLine commandLine;
/*     */     try
/*     */     {
/*  96 */       commandLine = parser.parse(options, args);
/*     */     } catch (ParseException ex) {
/*  98 */       usage();
/*  99 */       return;
/*     */     }
/*     */ 
/* 102 */     baksmaliOptions options = new baksmaliOptions();
/*     */ 
/* 104 */     boolean disassemble = true;
/* 105 */     boolean doDump = false;
/* 106 */     String dumpFileName = null;
/* 107 */     boolean setBootClassPath = false;
/*     */ 
/* 109 */     String[] remainingArgs = commandLine.getArgs();
/* 110 */     Option[] clOptions = commandLine.getOptions();
/*     */ 
/* 112 */     for (int i = 0; i < clOptions.length; i++) {
/* 113 */       Option option = clOptions[i];
/* 114 */       String opt = option.getOpt();
/*     */ 
/* 116 */       switch (opt.charAt(0)) {
/*     */       case 'v':
/* 118 */         version();
/* 119 */         return;
/*     */       case '?':
/*     */         while (true) { i++; if (i >= clOptions.length) break;
/* 122 */           if (clOptions[i].getOpt().charAt(0) == '?') {
/* 123 */             usage(true);
/* 124 */             return;
/*     */           }
/*     */         }
/* 127 */         usage(false);
/* 128 */         return;
/*     */       case 'o':
/* 130 */         options.outputDirectory = commandLine.getOptionValue("o");
/* 131 */         break;
/*     */       case 'p':
/* 133 */         options.noParameterRegisters = true;
/* 134 */         break;
/*     */       case 'l':
/* 136 */         options.useLocalsDirective = true;
/* 137 */         break;
/*     */       case 's':
/* 139 */         options.useSequentialLabels = true;
/* 140 */         break;
/*     */       case 'b':
/* 142 */         options.outputDebugInfo = false;
/* 143 */         break;
/*     */       case 'd':
/* 145 */         options.bootClassPathDirs.add(option.getValue());
/* 146 */         break;
/*     */       case 'f':
/* 148 */         options.addCodeOffsets = true;
/* 149 */         break;
/*     */       case 'r':
/* 151 */         String[] values = commandLine.getOptionValues('r');
/* 152 */         int registerInfo = 0;
/*     */ 
/* 154 */         if ((values == null) || (values.length == 0)) {
/* 155 */           registerInfo = 24;
/*     */         } else {
/* 157 */           for (String value : values) {
/* 158 */             if (value.equalsIgnoreCase("ALL")) {
/* 159 */               registerInfo |= 1;
/* 160 */             } else if (value.equalsIgnoreCase("ALLPRE")) {
/* 161 */               registerInfo |= 2;
/* 162 */             } else if (value.equalsIgnoreCase("ALLPOST")) {
/* 163 */               registerInfo |= 4;
/* 164 */             } else if (value.equalsIgnoreCase("ARGS")) {
/* 165 */               registerInfo |= 8;
/* 166 */             } else if (value.equalsIgnoreCase("DEST")) {
/* 167 */               registerInfo |= 16;
/* 168 */             } else if (value.equalsIgnoreCase("MERGE")) {
/* 169 */               registerInfo |= 32;
/* 170 */             } else if (value.equalsIgnoreCase("FULLMERGE")) {
/* 171 */               registerInfo |= 64;
/*     */             } else {
/* 173 */               usage();
/* 174 */               return;
/*     */             }
/*     */           }
/*     */ 
/* 178 */           if ((registerInfo & 0x40) != 0) {
/* 179 */             registerInfo &= -33;
/*     */           }
/*     */         }
/* 182 */         options.registerInfo = registerInfo;
/* 183 */         break;
/*     */       case 'c':
/* 185 */         String bcp = commandLine.getOptionValue("c");
/* 186 */         if ((bcp != null) && (bcp.charAt(0) == ':')) {
/* 187 */           options.addExtraClassPath(bcp);
/*     */         } else {
/* 189 */           setBootClassPath = true;
/* 190 */           options.setBootClassPath(bcp);
/*     */         }
/* 192 */         break;
/*     */       case 'x':
/* 194 */         options.deodex = true;
/* 195 */         break;
/*     */       case 'm':
/* 197 */         options.noAccessorComments = true;
/* 198 */         break;
/*     */       case 'a':
/* 200 */         options.apiLevel = Integer.parseInt(commandLine.getOptionValue("a"));
/* 201 */         break;
/*     */       case 'j':
/* 203 */         options.jobs = Integer.parseInt(commandLine.getOptionValue("j"));
/* 204 */         break;
/*     */       case 'i':
/* 206 */         String rif = commandLine.getOptionValue("i");
/* 207 */         options.setResourceIdFiles(rif);
/* 208 */         break;
/*     */       case 'N':
/* 210 */         disassemble = false;
/* 211 */         break;
/*     */       case 'D':
/* 213 */         doDump = true;
/* 214 */         dumpFileName = commandLine.getOptionValue("D");
/* 215 */         break;
/*     */       case 'I':
/* 217 */         options.ignoreErrors = true;
/* 218 */         break;
/*     */       case 'T':
/* 220 */         options.inlineResolver = new CustomInlineMethodResolver(options.classPath, new File(commandLine.getOptionValue("T")));
/* 221 */         break;
/*     */       case '@':
/*     */       case 'A':
/*     */       case 'B':
/*     */       case 'C':
/*     */       case 'E':
/*     */       case 'F':
/*     */       case 'G':
/*     */       case 'H':
/*     */       case 'J':
/*     */       case 'K':
/*     */       case 'L':
/*     */       case 'M':
/*     */       case 'O':
/*     */       case 'P':
/*     */       case 'Q':
/*     */       case 'R':
/*     */       case 'S':
/*     */       case 'U':
/*     */       case 'V':
/*     */       case 'W':
/*     */       case 'X':
/*     */       case 'Y':
/*     */       case 'Z':
/*     */       case '[':
/*     */       case '\\':
/*     */       case ']':
/*     */       case '^':
/*     */       case '_':
/*     */       case '`':
/*     */       case 'e':
/*     */       case 'g':
/*     */       case 'h':
/*     */       case 'k':
/*     */       case 'n':
/*     */       case 'q':
/*     */       case 't':
/*     */       case 'u':
/*     */       case 'w':
/*     */       default:
/* 223 */         if ($assertionsDisabled) continue; throw new AssertionError();
/*     */       }
/*     */     }
/*     */ 
/* 227 */     if (remainingArgs.length != 1) {
/* 228 */       usage();
/* 229 */       return;
/*     */     }
/*     */ 
/* 232 */     if (options.jobs <= 0) {
/* 233 */       options.jobs = Runtime.getRuntime().availableProcessors();
/* 234 */       if (options.jobs > 6) {
/* 235 */         options.jobs = 6;
/*     */       }
/*     */     }
/*     */ 
/* 239 */     if (options.apiLevel >= 17) {
/* 240 */       options.checkPackagePrivateAccess = true;
/*     */     }
/*     */ 
/* 243 */     String inputDexFileName = remainingArgs[0];
/*     */ 
/* 245 */     File dexFileFile = new File(inputDexFileName);
/* 246 */     if (!dexFileFile.exists()) {
/* 247 */       System.err.println("Can't find the file " + inputDexFileName);
/* 248 */       System.exit(1);
/*     */     }
/*     */ 
/* 252 */     DexBackedDexFile dexFile = DexFileFactory.loadDexFile(dexFileFile, options.apiLevel);
/*     */ 
/* 254 */     if (dexFile.isOdexFile()) {
/* 255 */       if (!options.deodex) {
/* 256 */         System.err.println("Warning: You are disassembling an odex file without deodexing it. You");
/* 257 */         System.err.println("won't be able to re-assemble the results unless you deodex it with the -x");
/* 258 */         System.err.println("option");
/* 259 */         options.allowOdex = true;
/*     */       }
/*     */     }
/* 262 */     else options.deodex = false;
/*     */ 
/* 265 */     if ((!setBootClassPath) && ((options.deodex) || (options.registerInfo != 0))) {
/* 266 */       if ((dexFile instanceof DexBackedOdexFile))
/* 267 */         options.bootClassPathEntries = ((DexBackedOdexFile)dexFile).getDependencies();
/*     */       else {
/* 269 */         options.bootClassPathEntries = getDefaultBootClassPathForApi(options.apiLevel);
/*     */       }
/*     */     }
/*     */ 
/* 273 */     if ((options.inlineResolver == null) && ((dexFile instanceof DexBackedOdexFile))) {
/* 274 */       options.inlineResolver = InlineMethodResolver.createInlineMethodResolver(((DexBackedOdexFile)dexFile).getOdexVersion());
/*     */     }
/*     */ 
/* 278 */     boolean errorOccurred = false;
/* 279 */     if (disassemble) {
/* 280 */       errorOccurred = !baksmali.disassembleDexFile(dexFile, options);
/*     */     }
/*     */ 
/* 283 */     if (doDump) {
/* 284 */       if (dumpFileName == null) {
/* 285 */         dumpFileName = commandLine.getOptionValue(inputDexFileName + ".dump");
/*     */       }
/* 287 */       dump.dump(dexFile, dumpFileName, options.apiLevel);
/*     */     }
/*     */ 
/* 290 */     if (errorOccurred)
/* 291 */       System.exit(1);
/*     */   }
/*     */ 
/*     */   private static void usage(boolean printDebugOptions)
/*     */   {
/* 299 */     SmaliHelpFormatter formatter = new SmaliHelpFormatter();
/* 300 */     int consoleWidth = ConsoleUtil.getConsoleWidth();
/* 301 */     if (consoleWidth <= 0) {
/* 302 */       consoleWidth = 80;
/*     */     }
/*     */ 
/* 305 */     formatter.setWidth(consoleWidth);
/*     */ 
/* 307 */     formatter.printHelp("java -jar baksmali.jar [options] <dex-file>", "disassembles and/or dumps a dex file", basicOptions, printDebugOptions ? debugOptions : null);
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 312 */     usage(false);
/*     */   }
/*     */ 
/*     */   protected static void version()
/*     */   {
/* 319 */     System.out.println("baksmali " + VERSION + " (http://smali.googlecode.com)");
/* 320 */     System.out.println("Copyright (C) 2010 Ben Gruver (JesusFreke@JesusFreke.com)");
/* 321 */     System.out.println("BSD license (http://www.opensource.org/licenses/bsd-license.php)");
/* 322 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   private static void buildOptions()
/*     */   {
/* 327 */     OptionBuilder.withLongOpt("version"); OptionBuilder.withDescription("prints the version then exits"); Option versionOption = OptionBuilder.create("v");
/*     */ 
/* 331 */     OptionBuilder.withLongOpt("help"); OptionBuilder.withDescription("prints the help message then exits. Specify twice for debug options"); Option helpOption = OptionBuilder.create("?");
/*     */ 
/* 335 */     OptionBuilder.withLongOpt("output"); OptionBuilder.withDescription("the directory where the disassembled files will be placed. The default is out"); OptionBuilder.hasArg(); OptionBuilder.withArgName("DIR"); Option outputDirOption = OptionBuilder.create("o");
/*     */ 
/* 341 */     OptionBuilder.withLongOpt("no-parameter-registers"); OptionBuilder.withDescription("use the v<n> syntax instead of the p<n> syntax for registers mapped to method parameters"); Option noParameterRegistersOption = OptionBuilder.create("p");
/*     */ 
/* 346 */     OptionBuilder.withLongOpt("deodex"); OptionBuilder.withDescription("deodex the given odex file. This option is ignored if the input file is not an odex file"); Option deodexerantOption = OptionBuilder.create("x");
/*     */ 
/* 351 */     OptionBuilder.withLongOpt("use-locals"); OptionBuilder.withDescription("output the .locals directive with the number of non-parameter registers, rather than the .register directive with the total number of register"); Option useLocalsOption = OptionBuilder.create("l");
/*     */ 
/* 356 */     OptionBuilder.withLongOpt("sequential-labels"); OptionBuilder.withDescription("create label names using a sequential numbering scheme per label type, rather than using the bytecode address"); Option sequentialLabelsOption = OptionBuilder.create("s");
/*     */ 
/* 361 */     OptionBuilder.withLongOpt("no-debug-info"); OptionBuilder.withDescription("don't write out debug info (.local, .param, .line, etc.)"); Option noDebugInfoOption = OptionBuilder.create("b");
/*     */ 
/* 365 */     OptionBuilder.withLongOpt("register-info"); OptionBuilder.hasOptionalArgs(); OptionBuilder.withArgName("REGISTER_INFO_TYPES"); OptionBuilder.withValueSeparator(','); OptionBuilder.withDescription("print the specificed type(s) of register information for each instruction. \"ARGS,DEST\" is the default if no types are specified.\nValid values are:\nALL: all pre- and post-instruction registers.\nALLPRE: all pre-instruction registers\nALLPOST: all post-instruction registers\nARGS: any pre-instruction registers used as arguments to the instruction\nDEST: the post-instruction destination register, if any\nMERGE: Any pre-instruction register has been merged from more than 1 different post-instruction register from its predecessors\nFULLMERGE: For each register that would be printed by MERGE, also show the incoming register types that were merged"); Option registerInfoOption = OptionBuilder.create("r");
/*     */ 
/* 379 */     OptionBuilder.withLongOpt("bootclasspath"); OptionBuilder.withDescription("the bootclasspath jars to use, for analysis. Defaults to core.jar:ext.jar:framework.jar:android.policy.jar:services.jar. If the value begins with a :, it will be appended to the default bootclasspath instead of replacing it"); OptionBuilder.hasOptionalArg(); OptionBuilder.withArgName("BOOTCLASSPATH"); Option classPathOption = OptionBuilder.create("c");
/*     */ 
/* 387 */     OptionBuilder.withLongOpt("bootclasspath-dir"); OptionBuilder.withDescription("the base folder to look for the bootclasspath files in. Defaults to the current directory"); OptionBuilder.hasArg(); OptionBuilder.withArgName("DIR"); Option classPathDirOption = OptionBuilder.create("d");
/*     */ 
/* 394 */     OptionBuilder.withLongOpt("code-offsets"); OptionBuilder.withDescription("add comments to the disassembly containing the code offset for each address"); Option codeOffsetOption = OptionBuilder.create("f");
/*     */ 
/* 398 */     OptionBuilder.withLongOpt("no-accessor-comments"); OptionBuilder.withDescription("don't output helper comments for synthetic accessors"); Option noAccessorCommentsOption = OptionBuilder.create("m");
/*     */ 
/* 402 */     OptionBuilder.withLongOpt("api-level"); OptionBuilder.withDescription("The numeric api-level of the file being disassembled. If not specified, it defaults to 15 (ICS)."); OptionBuilder.hasArg(); OptionBuilder.withArgName("API_LEVEL"); Option apiLevelOption = OptionBuilder.create("a");
/*     */ 
/* 409 */     OptionBuilder.withLongOpt("jobs"); OptionBuilder.withDescription("The number of threads to use. Defaults to the number of cores available, up to a maximum of 6"); OptionBuilder.hasArg(); OptionBuilder.withArgName("NUM_THREADS"); Option jobsOption = OptionBuilder.create("j");
/*     */ 
/* 416 */     OptionBuilder.withLongOpt("resource-id-files"); OptionBuilder.withDescription("the resource ID files to use, for analysis. A colon-separated list of prefix=file pairs.  For example R=res/values/public.xml:android.R=$ANDROID_HOME/platforms/android-19/data/res/values/public.xml"); OptionBuilder.hasArg(); OptionBuilder.withArgName("FILES"); Option resourceIdFilesOption = OptionBuilder.create("i");
/*     */ 
/* 424 */     OptionBuilder.withLongOpt("dump-to"); OptionBuilder.withDescription("dumps the given dex file into a single annotated dump file named FILE (<dexfile>.dump by default), along with the normal disassembly"); OptionBuilder.hasOptionalArg(); OptionBuilder.withArgName("FILE"); Option dumpOption = OptionBuilder.create("D");
/*     */ 
/* 431 */     OptionBuilder.withLongOpt("ignore-errors"); OptionBuilder.withDescription("ignores any non-fatal errors that occur while disassembling/deodexing, ignoring the class if needed, and continuing with the next class. The default behavior is to stop disassembling and exit once an error is encountered"); Option ignoreErrorsOption = OptionBuilder.create("I");
/*     */ 
/* 437 */     OptionBuilder.withLongOpt("no-disassembly"); OptionBuilder.withDescription("suppresses the output of the disassembly"); Option noDisassemblyOption = OptionBuilder.create("N");
/*     */ 
/* 441 */     OptionBuilder.withLongOpt("inline-table"); OptionBuilder.withDescription("specify a file containing a custom inline method table to use for deodexing"); OptionBuilder.hasArg(); OptionBuilder.withArgName("FILE"); Option inlineTableOption = OptionBuilder.create("T");
/*     */ 
/* 447 */     basicOptions.addOption(versionOption);
/* 448 */     basicOptions.addOption(helpOption);
/* 449 */     basicOptions.addOption(outputDirOption);
/* 450 */     basicOptions.addOption(noParameterRegistersOption);
/* 451 */     basicOptions.addOption(deodexerantOption);
/* 452 */     basicOptions.addOption(useLocalsOption);
/* 453 */     basicOptions.addOption(sequentialLabelsOption);
/* 454 */     basicOptions.addOption(noDebugInfoOption);
/* 455 */     basicOptions.addOption(registerInfoOption);
/* 456 */     basicOptions.addOption(classPathOption);
/* 457 */     basicOptions.addOption(classPathDirOption);
/* 458 */     basicOptions.addOption(codeOffsetOption);
/* 459 */     basicOptions.addOption(noAccessorCommentsOption);
/* 460 */     basicOptions.addOption(apiLevelOption);
/* 461 */     basicOptions.addOption(jobsOption);
/* 462 */     basicOptions.addOption(resourceIdFilesOption);
/*     */ 
/* 464 */     debugOptions.addOption(dumpOption);
/* 465 */     debugOptions.addOption(ignoreErrorsOption);
/* 466 */     debugOptions.addOption(noDisassemblyOption);
/* 467 */     debugOptions.addOption(inlineTableOption);
/*     */ 
/* 469 */     for (Iterator i$ = basicOptions.getOptions().iterator(); i$.hasNext(); ) { Object option = i$.next();
/* 470 */       options.addOption((Option)option);
/*     */     }
/* 472 */     for (Iterator i$ = debugOptions.getOptions().iterator(); i$.hasNext(); ) { Object option = i$.next();
/* 473 */       options.addOption((Option)option);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static List<String> getDefaultBootClassPathForApi(int apiLevel)
/*     */   {
/* 479 */     if (apiLevel < 9) {
/* 480 */       return Lists.newArrayList(new String[] { "/system/framework/core.jar", "/system/framework/ext.jar", "/system/framework/framework.jar", "/system/framework/android.policy.jar", "/system/framework/services.jar" });
/*     */     }
/*     */ 
/* 486 */     if (apiLevel < 12) {
/* 487 */       return Lists.newArrayList(new String[] { "/system/framework/core.jar", "/system/framework/bouncycastle.jar", "/system/framework/ext.jar", "/system/framework/framework.jar", "/system/framework/android.policy.jar", "/system/framework/services.jar", "/system/framework/core-junit.jar" });
/*     */     }
/*     */ 
/* 495 */     if (apiLevel < 14) {
/* 496 */       return Lists.newArrayList(new String[] { "/system/framework/core.jar", "/system/framework/apache-xml.jar", "/system/framework/bouncycastle.jar", "/system/framework/ext.jar", "/system/framework/framework.jar", "/system/framework/android.policy.jar", "/system/framework/services.jar", "/system/framework/core-junit.jar" });
/*     */     }
/*     */ 
/* 505 */     if (apiLevel < 16) {
/* 506 */       return Lists.newArrayList(new String[] { "/system/framework/core.jar", "/system/framework/core-junit.jar", "/system/framework/bouncycastle.jar", "/system/framework/ext.jar", "/system/framework/framework.jar", "/system/framework/android.policy.jar", "/system/framework/services.jar", "/system/framework/apache-xml.jar", "/system/framework/filterfw.jar" });
/*     */     }
/*     */ 
/* 519 */     return Lists.newArrayList(new String[] { "/system/framework/core.jar", "/system/framework/core-junit.jar", "/system/framework/bouncycastle.jar", "/system/framework/ext.jar", "/system/framework/framework.jar", "/system/framework/telephony-common.jar", "/system/framework/mms-common.jar", "/system/framework/android.policy.jar", "/system/framework/services.jar", "/system/framework/apache-xml.jar" });
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  58 */     options = new Options();
/*  59 */     basicOptions = new Options();
/*  60 */     debugOptions = new Options();
/*  61 */     buildOptions();
/*     */ 
/*  63 */     InputStream templateStream = baksmali.class.getClassLoader().getResourceAsStream("baksmali.properties");
/*  64 */     if (templateStream != null) {
/*  65 */       Properties properties = new Properties();
/*  66 */       String version = "(unknown)";
/*     */       try {
/*  68 */         properties.load(templateStream);
/*  69 */         version = properties.getProperty("application.version");
/*     */       }
/*     */       catch (IOException localIOException) {
/*     */       }
/*  73 */       VERSION = version;
/*     */     } else {
/*  75 */       VERSION = "[unknown version]";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.main
 * JD-Core Version:    0.6.0
 */