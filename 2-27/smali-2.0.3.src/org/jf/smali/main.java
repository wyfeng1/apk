/*     */ package org.jf.smali;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import org.antlr.runtime.CommonTokenStream;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenSource;
/*     */ import org.antlr.runtime.tree.CommonTree;
/*     */ import org.antlr.runtime.tree.CommonTreeNodeStream;
/*     */ import org.apache.commons.cli.CommandLine;
/*     */ import org.apache.commons.cli.CommandLineParser;
/*     */ import org.apache.commons.cli.Option;
/*     */ import org.apache.commons.cli.OptionBuilder;
/*     */ import org.apache.commons.cli.Options;
/*     */ import org.apache.commons.cli.ParseException;
/*     */ import org.apache.commons.cli.PosixParser;
/*     */ import org.jf.dexlib2.writer.builder.DexBuilder;
/*     */ import org.jf.dexlib2.writer.io.FileDataStore;
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
/*     */   {
/*  96 */     Locale locale = new Locale("en", "US");
/*  97 */     Locale.setDefault(locale);
/*     */ 
/*  99 */     CommandLineParser parser = new PosixParser();
/*     */     CommandLine commandLine;
/*     */     try
/*     */     {
/* 103 */       commandLine = parser.parse(options, args);
/*     */     } catch (ParseException ex) {
/* 105 */       usage();
/* 106 */       return;
/*     */     }
/*     */ 
/* 109 */     int jobs = -1;
/* 110 */     boolean allowOdex = false;
/* 111 */     boolean verboseErrors = false;
/* 112 */     boolean printTokens = false;
/*     */ 
/* 114 */     int apiLevel = 15;
/*     */ 
/* 116 */     String outputDexFile = "out.dex";
/*     */ 
/* 118 */     String[] remainingArgs = commandLine.getArgs();
/*     */ 
/* 120 */     Option[] options = commandLine.getOptions();
/*     */ 
/* 122 */     for (int i = 0; i < options.length; i++) {
/* 123 */       Option option = options[i];
/* 124 */       String opt = option.getOpt();
/*     */ 
/* 126 */       switch (opt.charAt(0)) {
/*     */       case 'v':
/* 128 */         version();
/* 129 */         return;
/*     */       case '?':
/*     */         while (true) { i++; if (i >= options.length) break;
/* 132 */           if (options[i].getOpt().charAt(0) == '?') {
/* 133 */             usage(true);
/* 134 */             return;
/*     */           }
/*     */         }
/* 137 */         usage(false);
/* 138 */         return;
/*     */       case 'o':
/* 140 */         outputDexFile = commandLine.getOptionValue("o");
/* 141 */         break;
/*     */       case 'x':
/* 143 */         allowOdex = true;
/* 144 */         break;
/*     */       case 'a':
/* 146 */         apiLevel = Integer.parseInt(commandLine.getOptionValue("a"));
/* 147 */         break;
/*     */       case 'j':
/* 149 */         jobs = Integer.parseInt(commandLine.getOptionValue("j"));
/* 150 */         break;
/*     */       case 'V':
/* 152 */         verboseErrors = true;
/* 153 */         break;
/*     */       case 'T':
/* 155 */         printTokens = true;
/* 156 */         break;
/*     */       default:
/* 158 */         if ($assertionsDisabled) continue; throw new AssertionError();
/*     */       }
/*     */     }
/*     */ 
/* 162 */     if (remainingArgs.length == 0) {
/* 163 */       usage();
/* 164 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 168 */       LinkedHashSet filesToProcess = new LinkedHashSet();
/*     */ 
/* 170 */       for (String arg : remainingArgs) {
/* 171 */         File argFile = new File(arg);
/*     */ 
/* 173 */         if (!argFile.exists()) {
/* 174 */           throw new RuntimeException("Cannot find file or directory \"" + arg + "\"");
/*     */         }
/*     */ 
/* 177 */         if (argFile.isDirectory())
/* 178 */           getSmaliFilesInDir(argFile, filesToProcess);
/* 179 */         else if (argFile.isFile()) {
/* 180 */           filesToProcess.add(argFile);
/*     */         }
/*     */       }
/*     */ 
/* 184 */       if (jobs <= 0) {
/* 185 */         jobs = Runtime.getRuntime().availableProcessors();
/* 186 */         if (jobs > 6) {
/* 187 */           jobs = 6;
/*     */         }
/*     */       }
/*     */ 
/* 191 */       boolean errors = false;
/*     */ 
/* 193 */       DexBuilder dexBuilder = DexBuilder.makeDexBuilder(apiLevel);
/* 194 */       ExecutorService executor = Executors.newFixedThreadPool(jobs);
/* 195 */       List tasks = Lists.newArrayList();
/*     */ 
/* 197 */       boolean finalVerboseErrors = verboseErrors;
/* 198 */       boolean finalPrintTokens = printTokens;
/* 199 */       boolean finalAllowOdex = allowOdex;
/* 200 */       int finalApiLevel = apiLevel;
/* 201 */       for (File file : filesToProcess) {
/* 202 */         tasks.add(executor.submit(new Callable(file, dexBuilder, finalVerboseErrors, finalPrintTokens, finalAllowOdex, finalApiLevel) {
/*     */           public Boolean call() throws Exception {
/* 204 */             return Boolean.valueOf(main.access$000(this.val$file, this.val$dexBuilder, this.val$finalVerboseErrors, this.val$finalPrintTokens, this.val$finalAllowOdex, this.val$finalApiLevel));
/*     */           }
/*     */         }));
/*     */       }
/*     */ 
/* 210 */       for (Future task : tasks) {
/*     */         while (true) {
/*     */           try {
/* 213 */             if (!((Boolean)task.get()).booleanValue()) {
/* 214 */               errors = true;
/*     */             }
/*     */           }
/*     */           catch (InterruptedException ex)
/*     */           {
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 223 */       executor.shutdown();
/*     */ 
/* 225 */       if (errors) {
/* 226 */         System.exit(1);
/*     */       }
/*     */ 
/* 229 */       dexBuilder.writeTo(new FileDataStore(new File(outputDexFile)));
/*     */     } catch (RuntimeException ex) {
/* 231 */       System.err.println("\nUNEXPECTED TOP-LEVEL EXCEPTION:");
/* 232 */       ex.printStackTrace();
/* 233 */       System.exit(2);
/*     */     } catch (Throwable ex) {
/* 235 */       System.err.println("\nUNEXPECTED TOP-LEVEL ERROR:");
/* 236 */       ex.printStackTrace();
/* 237 */       System.exit(3);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void getSmaliFilesInDir(File dir, Set<File> smaliFiles) {
/* 242 */     File[] files = dir.listFiles();
/* 243 */     if (files != null)
/* 244 */       for (File file : files)
/* 245 */         if (file.isDirectory())
/* 246 */           getSmaliFilesInDir(file, smaliFiles);
/* 247 */         else if (file.getName().endsWith(".smali"))
/* 248 */           smaliFiles.add(file);
/*     */   }
/*     */ 
/*     */   private static boolean assembleSmaliFile(File smaliFile, DexBuilder dexBuilder, boolean verboseErrors, boolean printTokens, boolean allowOdex, int apiLevel)
/*     */     throws Exception
/*     */   {
/* 261 */     FileInputStream fis = new FileInputStream(smaliFile.getAbsolutePath());
/* 262 */     InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
/*     */ 
/* 264 */     LexerErrorInterface lexer = new smaliFlexLexer(reader);
/* 265 */     ((smaliFlexLexer)lexer).setSourceFile(smaliFile);
/* 266 */     CommonTokenStream tokens = new CommonTokenStream((TokenSource)lexer);
/*     */ 
/* 268 */     if (printTokens) {
/* 269 */       tokens.getTokens();
/*     */ 
/* 271 */       for (int i = 0; i < tokens.size(); i++) {
/* 272 */         Token token = tokens.get(i);
/* 273 */         if (token.getChannel() == 99)
/*     */         {
/*     */           continue;
/*     */         }
/* 277 */         System.out.println(smaliParser.tokenNames[token.getType()] + ": " + token.getText());
/*     */       }
/*     */     }
/*     */ 
/* 281 */     smaliParser parser = new smaliParser(tokens);
/* 282 */     parser.setVerboseErrors(verboseErrors);
/* 283 */     parser.setAllowOdex(allowOdex);
/* 284 */     parser.setApiLevel(apiLevel);
/*     */ 
/* 286 */     smaliParser.smali_file_return result = parser.smali_file();
/*     */ 
/* 288 */     if ((parser.getNumberOfSyntaxErrors() > 0) || (lexer.getNumberOfSyntaxErrors() > 0)) {
/* 289 */       return false;
/*     */     }
/*     */ 
/* 292 */     CommonTree t = result.getTree();
/*     */ 
/* 294 */     CommonTreeNodeStream treeStream = new CommonTreeNodeStream(t);
/* 295 */     treeStream.setTokenStream(tokens);
/*     */ 
/* 297 */     smaliTreeWalker dexGen = new smaliTreeWalker(treeStream);
/* 298 */     dexGen.setVerboseErrors(verboseErrors);
/* 299 */     dexGen.setDexBuilder(dexBuilder);
/* 300 */     dexGen.smali_file();
/*     */ 
/* 302 */     return dexGen.getNumberOfSyntaxErrors() == 0;
/*     */   }
/*     */ 
/*     */   private static void usage(boolean printDebugOptions)
/*     */   {
/* 310 */     SmaliHelpFormatter formatter = new SmaliHelpFormatter();
/*     */ 
/* 312 */     int consoleWidth = ConsoleUtil.getConsoleWidth();
/* 313 */     if (consoleWidth <= 0) {
/* 314 */       consoleWidth = 80;
/*     */     }
/*     */ 
/* 317 */     formatter.setWidth(consoleWidth);
/*     */ 
/* 319 */     formatter.printHelp("java -jar smali.jar [options] [--] [<smali-file>|folder]*", "assembles a set of smali files into a dex file", basicOptions, printDebugOptions ? debugOptions : null);
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 324 */     usage(false);
/*     */   }
/*     */ 
/*     */   private static void version()
/*     */   {
/* 331 */     System.out.println("smali " + VERSION + " (http://smali.googlecode.com)");
/* 332 */     System.out.println("Copyright (C) 2010 Ben Gruver (JesusFreke@JesusFreke.com)");
/* 333 */     System.out.println("BSD license (http://www.opensource.org/licenses/bsd-license.php)");
/* 334 */     System.exit(0);
/*     */   }
/*     */ 
/*     */   private static void buildOptions()
/*     */   {
/* 339 */     OptionBuilder.withLongOpt("version"); OptionBuilder.withDescription("prints the version then exits"); Option versionOption = OptionBuilder.create("v");
/*     */ 
/* 343 */     OptionBuilder.withLongOpt("help"); OptionBuilder.withDescription("prints the help message then exits. Specify twice for debug options"); Option helpOption = OptionBuilder.create("?");
/*     */ 
/* 347 */     OptionBuilder.withLongOpt("output"); OptionBuilder.withDescription("the name of the dex file that will be written. The default is out.dex"); OptionBuilder.hasArg(); OptionBuilder.withArgName("FILE"); Option outputOption = OptionBuilder.create("o");
/*     */ 
/* 353 */     OptionBuilder.withLongOpt("allow-odex-instructions"); OptionBuilder.withDescription("allow odex instructions to be compiled into the dex file. Only a few instructions are supported - the ones that can exist in a dead code path and not cause dalvik to reject the class"); Option allowOdexOption = OptionBuilder.create("x");
/*     */ 
/* 359 */     OptionBuilder.withLongOpt("api-level"); OptionBuilder.withDescription("The numeric api-level of the file to generate, e.g. 14 for ICS. If not specified, it defaults to 15 (ICS)."); OptionBuilder.hasArg(); OptionBuilder.withArgName("API_LEVEL"); Option apiLevelOption = OptionBuilder.create("a");
/*     */ 
/* 366 */     OptionBuilder.withLongOpt("jobs"); OptionBuilder.withDescription("The number of threads to use. Defaults to the number of cores available, up to a maximum of 6"); OptionBuilder.hasArg(); OptionBuilder.withArgName("NUM_THREADS"); Option jobsOption = OptionBuilder.create("j");
/*     */ 
/* 373 */     OptionBuilder.withLongOpt("verbose-errors"); OptionBuilder.withDescription("Generate verbose error messages"); Option verboseErrorsOption = OptionBuilder.create("V");
/*     */ 
/* 377 */     OptionBuilder.withLongOpt("print-tokens"); OptionBuilder.withDescription("Print the name and text of each token"); Option printTokensOption = OptionBuilder.create("T");
/*     */ 
/* 381 */     basicOptions.addOption(versionOption);
/* 382 */     basicOptions.addOption(helpOption);
/* 383 */     basicOptions.addOption(outputOption);
/* 384 */     basicOptions.addOption(allowOdexOption);
/* 385 */     basicOptions.addOption(apiLevelOption);
/* 386 */     basicOptions.addOption(jobsOption);
/*     */ 
/* 388 */     debugOptions.addOption(verboseErrorsOption);
/* 389 */     debugOptions.addOption(printTokensOption);
/*     */ 
/* 391 */     for (Iterator i$ = basicOptions.getOptions().iterator(); i$.hasNext(); ) { Object option = i$.next();
/* 392 */       options.addOption((Option)option);
/*     */     }
/*     */ 
/* 395 */     for (Iterator i$ = debugOptions.getOptions().iterator(); i$.hasNext(); ) { Object option = i$.next();
/* 396 */       options.addOption((Option)option);
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  64 */     basicOptions = new Options();
/*  65 */     debugOptions = new Options();
/*  66 */     options = new Options();
/*  67 */     buildOptions();
/*     */ 
/*  69 */     InputStream templateStream = main.class.getClassLoader().getResourceAsStream("smali.properties");
/*  70 */     if (templateStream != null) {
/*  71 */       Properties properties = new Properties();
/*  72 */       String version = "(unknown)";
/*     */       try {
/*  74 */         properties.load(templateStream);
/*  75 */         version = properties.getProperty("application.version");
/*     */       }
/*     */       catch (IOException localIOException) {
/*     */       }
/*  79 */       VERSION = version;
/*     */     } else {
/*  81 */       VERSION = "[unknown version]";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.smali.main
 * JD-Core Version:    0.6.0
 */