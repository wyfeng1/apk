package org.apache.commons.cli;

public abstract interface CommandLineParser
{
  public abstract CommandLine parse(Options paramOptions, String[] paramArrayOfString)
    throws ParseException;
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.apache.commons.cli.CommandLineParser
 * JD-Core Version:    0.6.0
 */