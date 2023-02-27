/*     */ package org.apache.commons.cli;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class HelpFormatter
/*     */ {
/*     */ 
/*     */   /** @deprecated */
/*     */   public int defaultWidth;
/*     */ 
/*     */   /** @deprecated */
/*     */   public int defaultLeftPad;
/*     */ 
/*     */   /** @deprecated */
/*     */   public int defaultDescPad;
/*     */ 
/*     */   /** @deprecated */
/*     */   public String defaultSyntaxPrefix;
/*     */ 
/*     */   /** @deprecated */
/*     */   public String defaultNewLine;
/*     */ 
/*     */   /** @deprecated */
/*     */   public String defaultOptPrefix;
/*     */ 
/*     */   /** @deprecated */
/*     */   public String defaultLongOptPrefix;
/*     */ 
/*     */   /** @deprecated */
/*     */   public String defaultArgName;
/*     */   protected Comparator optionComparator;
/*     */ 
/*     */   public HelpFormatter()
/*     */   {
/*  71 */     this.defaultWidth = 74;
/*     */ 
/*  79 */     this.defaultLeftPad = 1;
/*     */ 
/*  88 */     this.defaultDescPad = 3;
/*     */ 
/*  96 */     this.defaultSyntaxPrefix = "usage: ";
/*     */ 
/* 104 */     this.defaultNewLine = System.getProperty("line.separator");
/*     */ 
/* 112 */     this.defaultOptPrefix = "-";
/*     */ 
/* 120 */     this.defaultLongOptPrefix = "--";
/*     */ 
/* 128 */     this.defaultArgName = "arg";
/*     */ 
/* 135 */     this.optionComparator = new OptionComparator(null);
/*     */   }
/*     */ 
/*     */   public void setWidth(int width)
/*     */   {
/* 144 */     this.defaultWidth = width;
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 154 */     return this.defaultWidth;
/*     */   }
/*     */ 
/*     */   public int getLeftPadding()
/*     */   {
/* 174 */     return this.defaultLeftPad;
/*     */   }
/*     */ 
/*     */   public int getDescPadding()
/*     */   {
/* 194 */     return this.defaultDescPad;
/*     */   }
/*     */ 
/*     */   public Comparator getOptionComparator()
/*     */   {
/* 304 */     return this.optionComparator;
/*     */   }
/*     */ 
/*     */   public void printHelp(String cmdLineSyntax, String header, Options options, String footer)
/*     */   {
/* 364 */     printHelp(cmdLineSyntax, header, options, footer, false);
/*     */   }
/*     */ 
/*     */   public void printHelp(String cmdLineSyntax, String header, Options options, String footer, boolean autoUsage)
/*     */   {
/* 381 */     printHelp(this.defaultWidth, cmdLineSyntax, header, options, footer, autoUsage);
/*     */   }
/*     */ 
/*     */   public void printHelp(int width, String cmdLineSyntax, String header, Options options, String footer, boolean autoUsage)
/*     */   {
/* 416 */     PrintWriter pw = new PrintWriter(System.out);
/*     */ 
/* 418 */     printHelp(pw, width, cmdLineSyntax, header, options, this.defaultLeftPad, this.defaultDescPad, footer, autoUsage);
/* 419 */     pw.flush();
/*     */   }
/*     */ 
/*     */   public void printHelp(PrintWriter pw, int width, String cmdLineSyntax, String header, Options options, int leftPad, int descPad, String footer, boolean autoUsage)
/*     */   {
/* 470 */     if ((cmdLineSyntax == null) || (cmdLineSyntax.length() == 0))
/*     */     {
/* 472 */       throw new IllegalArgumentException("cmdLineSyntax not provided");
/*     */     }
/*     */ 
/* 475 */     if (autoUsage)
/*     */     {
/* 477 */       printUsage(pw, width, cmdLineSyntax, options);
/*     */     }
/*     */     else
/*     */     {
/* 481 */       printUsage(pw, width, cmdLineSyntax);
/*     */     }
/*     */ 
/* 484 */     if ((header != null) && (header.trim().length() > 0))
/*     */     {
/* 486 */       printWrapped(pw, width, header);
/*     */     }
/*     */ 
/* 489 */     printOptions(pw, width, options, leftPad, descPad);
/*     */ 
/* 491 */     if ((footer != null) && (footer.trim().length() > 0))
/*     */     {
/* 493 */       printWrapped(pw, width, footer);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void printUsage(PrintWriter pw, int width, String app, Options options)
/*     */   {
/* 509 */     StringBuffer buff = new StringBuffer(this.defaultSyntaxPrefix).append(app).append(" ");
/*     */ 
/* 512 */     Collection processedGroups = new ArrayList();
/*     */ 
/* 517 */     List optList = new ArrayList(options.getOptions());
/* 518 */     Collections.sort(optList, getOptionComparator());
/*     */ 
/* 520 */     for (Iterator i = optList.iterator(); i.hasNext(); )
/*     */     {
/* 523 */       Option option = (Option)i.next();
/*     */ 
/* 526 */       OptionGroup group = options.getOptionGroup(option);
/*     */ 
/* 529 */       if (group != null)
/*     */       {
/* 532 */         if (!processedGroups.contains(group))
/*     */         {
/* 535 */           processedGroups.add(group);
/*     */ 
/* 539 */           appendOptionGroup(buff, group);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 549 */         appendOption(buff, option, option.isRequired());
/*     */       }
/*     */ 
/* 552 */       if (i.hasNext())
/*     */       {
/* 554 */         buff.append(" ");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 560 */     printWrapped(pw, width, buff.toString().indexOf(' ') + 1, buff.toString());
/*     */   }
/*     */ 
/*     */   private void appendOptionGroup(StringBuffer buff, OptionGroup group)
/*     */   {
/* 573 */     if (!group.isRequired())
/*     */     {
/* 575 */       buff.append("[");
/*     */     }
/*     */ 
/* 578 */     List optList = new ArrayList(group.getOptions());
/* 579 */     Collections.sort(optList, getOptionComparator());
/*     */ 
/* 581 */     for (Iterator i = optList.iterator(); i.hasNext(); )
/*     */     {
/* 584 */       appendOption(buff, (Option)i.next(), true);
/*     */ 
/* 586 */       if (!i.hasNext())
/*     */         continue;
/* 588 */       buff.append(" | ");
/*     */     }
/*     */ 
/* 592 */     if (!group.isRequired())
/*     */     {
/* 594 */       buff.append("]");
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void appendOption(StringBuffer buff, Option option, boolean required)
/*     */   {
/* 607 */     if (!required)
/*     */     {
/* 609 */       buff.append("[");
/*     */     }
/*     */ 
/* 612 */     if (option.getOpt() != null)
/*     */     {
/* 614 */       buff.append("-").append(option.getOpt());
/*     */     }
/*     */     else
/*     */     {
/* 618 */       buff.append("--").append(option.getLongOpt());
/*     */     }
/*     */ 
/* 622 */     if ((option.hasArg()) && (option.hasArgName()))
/*     */     {
/* 624 */       buff.append(" <").append(option.getArgName()).append(">");
/*     */     }
/*     */ 
/* 628 */     if (!required)
/*     */     {
/* 630 */       buff.append("]");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void printUsage(PrintWriter pw, int width, String cmdLineSyntax)
/*     */   {
/* 644 */     int argPos = cmdLineSyntax.indexOf(' ') + 1;
/*     */ 
/* 646 */     printWrapped(pw, width, this.defaultSyntaxPrefix.length() + argPos, this.defaultSyntaxPrefix + cmdLineSyntax);
/*     */   }
/*     */ 
/*     */   public void printOptions(PrintWriter pw, int width, Options options, int leftPad, int descPad)
/*     */   {
/* 664 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 666 */     renderOptions(sb, width, options, leftPad, descPad);
/* 667 */     pw.println(sb.toString());
/*     */   }
/*     */ 
/*     */   public void printWrapped(PrintWriter pw, int width, String text)
/*     */   {
/* 679 */     printWrapped(pw, width, 0, text);
/*     */   }
/*     */ 
/*     */   public void printWrapped(PrintWriter pw, int width, int nextLineTabStop, String text)
/*     */   {
/* 692 */     StringBuffer sb = new StringBuffer(text.length());
/*     */ 
/* 694 */     renderWrappedText(sb, width, nextLineTabStop, text);
/* 695 */     pw.println(sb.toString());
/*     */   }
/*     */ 
/*     */   protected StringBuffer renderOptions(StringBuffer sb, int width, Options options, int leftPad, int descPad)
/*     */   {
/* 716 */     String lpad = createPadding(leftPad);
/* 717 */     String dpad = createPadding(descPad);
/*     */ 
/* 723 */     int max = 0;
/*     */ 
/* 725 */     List prefixList = new ArrayList();
/*     */ 
/* 727 */     List optList = options.helpOptions();
/*     */ 
/* 729 */     Collections.sort(optList, getOptionComparator());
/*     */ 
/* 731 */     for (Iterator i = optList.iterator(); i.hasNext(); )
/*     */     {
/* 733 */       Option option = (Option)i.next();
/* 734 */       StringBuffer optBuf = new StringBuffer(8);
/*     */ 
/* 736 */       if (option.getOpt() == null)
/*     */       {
/* 738 */         optBuf.append(lpad).append("   " + this.defaultLongOptPrefix).append(option.getLongOpt());
/*     */       }
/*     */       else
/*     */       {
/* 742 */         optBuf.append(lpad).append(this.defaultOptPrefix).append(option.getOpt());
/*     */ 
/* 744 */         if (option.hasLongOpt())
/*     */         {
/* 746 */           optBuf.append(',').append(this.defaultLongOptPrefix).append(option.getLongOpt());
/*     */         }
/*     */       }
/*     */ 
/* 750 */       if (option.hasArg())
/*     */       {
/* 752 */         if (option.hasArgName())
/*     */         {
/* 754 */           optBuf.append(" <").append(option.getArgName()).append(">");
/*     */         }
/*     */         else
/*     */         {
/* 758 */           optBuf.append(' ');
/*     */         }
/*     */       }
/*     */ 
/* 762 */       prefixList.add(optBuf);
/* 763 */       max = optBuf.length() > max ? optBuf.length() : max;
/*     */     }
/*     */ 
/* 766 */     int x = 0;
/*     */ 
/* 768 */     for (Iterator i = optList.iterator(); i.hasNext(); )
/*     */     {
/* 770 */       Option option = (Option)i.next();
/* 771 */       StringBuffer optBuf = new StringBuffer(prefixList.get(x++).toString());
/*     */ 
/* 773 */       if (optBuf.length() < max)
/*     */       {
/* 775 */         optBuf.append(createPadding(max - optBuf.length()));
/*     */       }
/*     */ 
/* 778 */       optBuf.append(dpad);
/*     */ 
/* 780 */       int nextLineTabStop = max + descPad;
/*     */ 
/* 782 */       if (option.getDescription() != null)
/*     */       {
/* 784 */         optBuf.append(option.getDescription());
/*     */       }
/*     */ 
/* 787 */       renderWrappedText(sb, width, nextLineTabStop, optBuf.toString());
/*     */ 
/* 789 */       if (i.hasNext())
/*     */       {
/* 791 */         sb.append(this.defaultNewLine);
/*     */       }
/*     */     }
/*     */ 
/* 795 */     return sb;
/*     */   }
/*     */ 
/*     */   protected StringBuffer renderWrappedText(StringBuffer sb, int width, int nextLineTabStop, String text)
/*     */   {
/* 812 */     int pos = findWrapPos(text, width, 0);
/*     */ 
/* 814 */     if (pos == -1)
/*     */     {
/* 816 */       sb.append(rtrim(text));
/*     */ 
/* 818 */       return sb;
/*     */     }
/* 820 */     sb.append(rtrim(text.substring(0, pos))).append(this.defaultNewLine);
/*     */ 
/* 822 */     if (nextLineTabStop >= width)
/*     */     {
/* 825 */       nextLineTabStop = 1;
/*     */     }
/*     */ 
/* 830 */     String padding = createPadding(nextLineTabStop);
/*     */     while (true)
/*     */     {
/* 834 */       text = padding + text.substring(pos).trim();
/* 835 */       pos = findWrapPos(text, width, 0);
/*     */ 
/* 837 */       if (pos == -1)
/*     */       {
/* 839 */         sb.append(text);
/*     */ 
/* 841 */         return sb;
/*     */       }
/*     */ 
/* 844 */       if ((text.length() > width) && (pos == nextLineTabStop - 1))
/*     */       {
/* 846 */         pos = width;
/*     */       }
/*     */ 
/* 849 */       sb.append(rtrim(text.substring(0, pos))).append(this.defaultNewLine);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int findWrapPos(String text, int width, int startPos)
/*     */   {
/* 868 */     int pos = -1;
/*     */ 
/* 871 */     if ((((pos = text.indexOf('\n', startPos)) != -1) && (pos <= width)) || (((pos = text.indexOf('\t', startPos)) != -1) && (pos <= width)))
/*     */     {
/* 874 */       return pos + 1;
/*     */     }
/* 876 */     if (startPos + width >= text.length())
/*     */     {
/* 878 */       return -1;
/*     */     }
/*     */ 
/* 883 */     pos = startPos + width;
/*     */     char c;
/* 888 */     while ((pos >= startPos) && ((c = text.charAt(pos)) != ' ') && (c != '\n') && (c != '\r'))
/*     */     {
/* 890 */       pos--;
/*     */     }
/*     */ 
/* 894 */     if (pos > startPos)
/*     */     {
/* 896 */       return pos;
/*     */     }
/*     */ 
/* 901 */     pos = startPos + width;
/*     */     char c;
/* 904 */     while ((pos <= text.length()) && ((c = text.charAt(pos)) != ' ') && (c != '\n') && (c != '\r'))
/*     */     {
/* 906 */       pos++;
/*     */     }
/*     */ 
/* 909 */     return pos == text.length() ? -1 : pos;
/*     */   }
/*     */ 
/*     */   protected String createPadding(int len)
/*     */   {
/* 921 */     StringBuffer sb = new StringBuffer(len);
/*     */ 
/* 923 */     for (int i = 0; i < len; i++)
/*     */     {
/* 925 */       sb.append(' ');
/*     */     }
/*     */ 
/* 928 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   protected String rtrim(String s)
/*     */   {
/* 940 */     if ((s == null) || (s.length() == 0))
/*     */     {
/* 942 */       return s;
/*     */     }
/*     */ 
/* 945 */     int pos = s.length();
/*     */ 
/* 947 */     while ((pos > 0) && (Character.isWhitespace(s.charAt(pos - 1))))
/*     */     {
/* 949 */       pos--;
/*     */     }
/*     */ 
/* 952 */     return s.substring(0, pos);
/*     */   }
/*     */ 
/*     */   private static class OptionComparator
/*     */     implements Comparator
/*     */   {
/*     */     private OptionComparator()
/*     */     {
/*     */     }
/*     */ 
/*     */     public int compare(Object o1, Object o2)
/*     */     {
/* 978 */       Option opt1 = (Option)o1;
/* 979 */       Option opt2 = (Option)o2;
/*     */ 
/* 981 */       return opt1.getKey().compareToIgnoreCase(opt2.getKey());
/*     */     }
/*     */ 
/*     */     OptionComparator(HelpFormatter.1 x0)
/*     */     {
/* 962 */       this();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.apache.commons.cli.HelpFormatter
 * JD-Core Version:    0.6.0
 */